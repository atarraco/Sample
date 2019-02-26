package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.Constantes;
import com.cimne.tic.oko.server.ws.BLL.GestionContenido;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.util.CriptografiaSimetrica;
import com.cimne.tic.oko.server.ws.util.Seguridad;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.io.InputStream;

/**
 * Created by lorddarks on 31/1/17.
 * Test de upload file
 */
@Path("/{UploadFileTest : (?i)uploadfiletest}")
public class UploadFileTest {

    @Context
    private UriInfo uriInfo;

    @POST
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces(MediaType.TEXT_PLAIN)
    public Response upload(@FormDataParam("mensaje") String mensaje,
                           @FormDataParam("file") InputStream fileInputStream,
                           @FormDataParam("file") FormDataContentDisposition fileMetaData) {

        String uploadedFileLocation = Constantes.UPLOAD_FILES_PATH + fileMetaData.getFileName();
        String uploadedFileLocationThumb = Constantes.UPLOAD_FILES_PATH + "thumb" + fileMetaData.getFileName();
        String extension = fileMetaData.getFileName().split("\\.")[1];
        String uploadedFileLocationENC = Constantes.UPLOAD_FILES_PATH + "ENC" + Seguridad.GenerarRandomFileName() + "." + extension;
        String uploadedFileLocationDENC = Constantes.UPLOAD_FILES_PATH + "DENC" + Seguridad.GenerarRandomFileName() + "." + extension;
        String uploadedFileLocationThumbEnc = Constantes.UPLOAD_FILES_PATH + "ENC" + Seguridad.GenerarRandomFileName() + "Thumb.jpg";
        GestionContenido.WriteToFile(fileInputStream, uploadedFileLocation);
        CriptografiaSimetrica criptografiaSimetrica = new CriptografiaSimetrica(CriptografiaSimetrica.CryptoProvider.AES);
        criptografiaSimetrica.setKey(Constantes.CLAVE_ENCRIPTACION);
        criptografiaSimetrica.setIV(Constantes.VI_ENCRIPTACION);
        try {
            criptografiaSimetrica.cifrarDescifrarArchivo(uploadedFileLocation, uploadedFileLocationENC, CriptografiaSimetrica.CryptoAction.Encrypt);
            criptografiaSimetrica.cifrarDescifrarArchivo(uploadedFileLocationENC, uploadedFileLocationDENC, CriptografiaSimetrica.CryptoAction.Desencrypt);
            javax.imageio.ImageIO.write(GestionContenido.CreateThumbnail(uploadedFileLocation, 100, 100), "JPG", new File(uploadedFileLocationThumb));
            criptografiaSimetrica.cifrarDescifrarArchivo(uploadedFileLocationThumb, uploadedFileLocationThumbEnc, CriptografiaSimetrica.CryptoAction.Encrypt);
        } catch (OKOBusinessException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(404).build();
        }
        String salida = "Done! " + mensaje;
        return Response.status(200).entity(salida).build();

    }
}
