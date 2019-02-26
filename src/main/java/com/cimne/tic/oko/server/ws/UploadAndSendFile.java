package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.GestionContenido;
import com.cimne.tic.oko.server.ws.BLL.GestionDispositivos;
import com.cimne.tic.oko.server.ws.BLL.GestionUsuarios;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWS;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWSUploadFile;
import com.cimne.tic.oko.server.ws.model.tables.records.DispositivosvirtualesRecord;
import com.cimne.tic.oko.server.ws.model.tables.records.UsuariosRecord;
import com.cimne.tic.oko.server.ws.types.OKOErrorType;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.io.InputStream;

/**
 * Created by lorddarks on 30/1/17.
 * Upload media files to server and send them to okos
 */
@Path("UploadAndSendFile")
public class UploadAndSendFile {

    @Context
    private UriInfo uriInfo;

    @POST
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaWS<RespuestaWSUploadFile> uploadAndSendFile(
            @FormDataParam("apikey") String apiKey,
            @FormDataParam("uuid") String uuid,
            @FormDataParam("tipo") String extension,
            @FormDataParam("tipoMultimedia") String tipoMultimediaString,
            @FormDataParam("mensaje") String mensaje,
            @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData) {

        int tipoMultimedia = Integer.parseInt(tipoMultimediaString); // 1: IMAGENES // 2: VIDEO // 3: AUDIO // 4: ALERT
        RespuestaWS<RespuestaWSUploadFile> respuestaWS = new RespuestaWS<>();
        if (mensaje == null) mensaje = "";
        if (extension.toLowerCase().equals("exe") || extension.toLowerCase().equals(".bat") || extension.toLowerCase().equals("apk") || extension.toLowerCase().equals(".apk")) {
            respuestaWS.responseStatus = -1;
            respuestaWS.message = null;
            respuestaWS.error = "file type not allowed";
            return respuestaWS;
        }
        try {
            UsuariosRecord usuario = GestionUsuarios.GetUserfromApiKey(apiKey);
            if (usuario == null) throw new OKOBusinessException(OKOErrorType.USER_NOT_FOUND);
            // SEGURIDAD ¿HayPermisosParaEnviar? antes de hacer UPLOAD
            if (!GestionDispositivos.HayPermisosParaEnviar(usuario.getIdusuario(), uuid))
                throw new OKOBusinessException(OKOErrorType.NOT_ALLOWED);
            DispositivosvirtualesRecord dispVirtual = GestionDispositivos.ObtenerDispositivoVirtual(uuid);
            if (dispVirtual == null) throw new OKOBusinessException(OKOErrorType.INVALID_DEVICE);
            // UPLOAD File
            RespuestaWSUploadFile respuestaWSUploadFile = GestionContenido.SubirContenido(fileInputStream, fileMetaData, usuario, dispVirtual, extension, tipoMultimedia, mensaje);
            if (respuestaWSUploadFile == null) throw new OKOBusinessException(OKOErrorType.ERROR_UPLOADING);
            // SEGURIDAD ¿HayConfirmacionPorParteDelPropietarioDelMarco?
            if (dispVirtual.getControlcontenido() != 1) {
                // No pues ... enviamos PUSH al MARCO
                GestionContenido.Enviar(usuario, dispVirtual, mensaje, respuestaWSUploadFile.url, String.valueOf(respuestaWSUploadFile.contenidoMultimediaID), false);//(create, apiKey, usuario, dispVirtual, mensaje, respuestaWSUploadFile.url, respuestaWSUploadFile.contenidoMultimediaID.ToString());
                // Serializamos SALIDA
                respuestaWS.responseStatus = 0;
                respuestaWS.message = respuestaWSUploadFile;
            } else {
                // Se requiere confirmacion por parte del usuario
                // TODO comprobar si solo si, el IDRECEPTOR es <> IDEMISOR
                // No pues ... enviamos PUSH al MARCO
                GestionContenido.Enviar(usuario, dispVirtual, mensaje, respuestaWSUploadFile.url, String.valueOf(respuestaWSUploadFile.contenidoMultimediaID), true);
                // Serializamos SALIDA
                respuestaWS.responseStatus = 0;
                respuestaWS.message = respuestaWSUploadFile;
            }
            return respuestaWS;
        } catch (OKOBusinessException ex) {
            respuestaWS.responseStatus = ex.getErrorNumber();
            respuestaWS.message = null;
            respuestaWS.error = ex.getMessage();
            return respuestaWS;
        }
    }
}
