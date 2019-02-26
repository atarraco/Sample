package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.Constantes;
import com.cimne.tic.oko.server.ws.BLL.GestionContenido;
import com.cimne.tic.oko.server.ws.util.CriptografiaSimetrica;
import com.cimne.tic.oko.server.ws.util.MediaStreamer;
import com.cimne.tic.oko.server.ws.util.Seguridad;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Created by lorddarks on 3/2/17.
 * Proveedor de ficheros multimedia.
 */
@Path("/{media : (?i)media}")
public class Media {

    @GET
    @Produces({"image/jpeg", "video/mp4", "audio/mp4", "audio/aac", "audio/ogg", "audio/wav", "audio/mp3", "video/ogg", "video/webm", "video/quicktime", MediaType.TEXT_PLAIN})
    @Path("/Content/{uuid}/{filename}")
    public Response getMedia(@PathParam("uuid") String uuid,
                             @PathParam("filename") String filename,
                             @HeaderParam("range") String range) { //@HeaderParam("/{Range : (?i)range}") String range
        //get archivo
        // 1 desencripta 2 sirve
        String pathFichero = Constantes.UPLOAD_FILES_PATH + "Content/" + uuid + "/" + filename;
        final String pathFicheroTemp = Constantes.UPLOAD_FILES_PATH + "Content/" + uuid + "/temp" + Seguridad.GenerateSecureRandomString() + filename;
        CriptografiaSimetrica criptografiaSimetrica = new CriptografiaSimetrica(CriptografiaSimetrica.CryptoProvider.AES);
        criptografiaSimetrica.setKey(Constantes.CLAVE_ENCRIPTACION);
        criptografiaSimetrica.setIV(Constantes.VI_ENCRIPTACION);
        try {
            criptografiaSimetrica.cifrarDescifrarArchivo(pathFichero, pathFicheroTemp, CriptografiaSimetrica.CryptoAction.Desencrypt);
            String extension = filename.split("\\.")[1].toLowerCase();
            //if (!GestionContenido.IsImagen(extension)) {
            String contentType = GestionContenido.GetMimeTypeFromExtension(extension);
            if (contentType == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Media not found")
                        .type(MediaType.TEXT_PLAIN)
                        .build();
            }
            File file = new File(pathFicheroTemp);
            final int chunk_size = 1024 * 1024;

            int _from = 0;
            int _to = 0;
            if (range != null) {
                String[] ranges = range.split("=")[1].split("-");

                _from = Integer.parseInt(ranges[0]);
                _to = chunk_size + _from;
                if (_to >= file.length()) {
                    _to = (int) (file.length() - 1);
                }
                if (ranges.length == 2) {
                    _to = Integer.parseInt(ranges[1]);
                }
            } else {
                _from = 0;
                _to = (int) file.length() - 1;
            }
            final int from = _from;
            final int to = _to;

            final String responseRange = String.format("bytes %d-%d/%d", from, to, file.length());
            final RandomAccessFile raf = new RandomAccessFile(file, "r");
            raf.seek(from);
            final int len = to - from + 1;
            final MediaStreamer streamer = new MediaStreamer(len, raf);
            Files.deleteIfExists(Paths.get(pathFicheroTemp));
            if (range != null) {
                Response.ResponseBuilder res = Response.ok(streamer).status(206)
                        .type(contentType)
                        .header("Accept-Ranges", "bytes")
                        .header("Content-Range", responseRange)
                        .header(HttpHeaders.CONTENT_LENGTH, streamer.getLenth())
                        .header(HttpHeaders.LAST_MODIFIED, new Date(file.lastModified()));
                return res.build();
            } else {
                Response.ResponseBuilder res = Response.ok(streamer).status(200)
                        .type(contentType)
                        //.header("Accept-Ranges", "bytes")
                        //.header("Content-Range", responseRange)
                        .header(HttpHeaders.CONTENT_LENGTH, streamer.getLenth())
                        .header(HttpHeaders.LAST_MODIFIED, new Date(file.lastModified()));
                return res.build();
            }


        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Media not found")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

    }

    @GET
    @Produces({"image/jpeg"})
    @Path("/Content/{uuid}/Thumb/{filename}")
    public Response getThumbnails(@PathParam("uuid") String uuid,
                                  @PathParam("filename") String filename) {
        String pathFichero = Constantes.UPLOAD_FILES_PATH + "Content/" + uuid + "/Thumb/" + filename;
        final String pathFicheroTemp = Constantes.UPLOAD_FILES_PATH + "Content/" + uuid + "/Thumb/temp_" + Seguridad.GenerateSecureRandomString() + filename;
        CriptografiaSimetrica criptografiaSimetrica = new CriptografiaSimetrica(CriptografiaSimetrica.CryptoProvider.AES);
        criptografiaSimetrica.setKey(Constantes.CLAVE_ENCRIPTACION);
        criptografiaSimetrica.setIV(Constantes.VI_ENCRIPTACION);
        try {
            criptografiaSimetrica.cifrarDescifrarArchivo(pathFichero, pathFicheroTemp, CriptografiaSimetrica.CryptoAction.Desencrypt);
            String extension = filename.split("\\.")[1].toLowerCase();
            String contentType = GestionContenido.GetMimeTypeFromExtension(extension);
            if (contentType == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Media not found")
                        .type(MediaType.TEXT_PLAIN)
                        .build();
            }
            StreamingOutput fileStream = new StreamingOutput() {
                @Override
                public void write(java.io.OutputStream out) throws IOException, WebApplicationException {

                    try {
                        java.nio.file.Path path = Paths.get(pathFicheroTemp);
                        byte[] data = Files.readAllBytes(path);
                        out.write(data);
                        out.flush();
                        Files.deleteIfExists(path);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new WebApplicationException("File Not Found! rascando! Path: " + pathFicheroTemp + " Mesaje: " + e.getMessage() + " XZXAAAA", -2);
                        //e.printStackTrace();
                    }
                }
            };

            return Response
                    .ok(fileStream)
                    .type(contentType)
                    .build();

        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Media not found")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }
}
