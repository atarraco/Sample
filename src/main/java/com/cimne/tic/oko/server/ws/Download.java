package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.GestionDispositivos;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by lorddarks on 7/2/17.
 * Descargar los APK
 */
@Path("Download")
public class Download {

    @POST
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@HeaderParam("md5") String md5,
                             @HeaderParam("tipo") int tipo) {
        try {
            if (md5 == null) return Response.status(Response.Status.FORBIDDEN).build();
            final String urlDescarga = GestionDispositivos.ObtenerPathDescargaTipo(md5, tipo);
            if (urlDescarga == null) return Response.status(Response.Status.FORBIDDEN).build();
            final java.nio.file.Path path = Paths.get(urlDescarga);

            StreamingOutput fileStream = output -> {
                try {
                    byte[] data = Files.readAllBytes(path);
                    output.write(data);
                    output.flush();
                } catch (Exception e) {
                    throw new WebApplicationException("File Not Found !!");
                }
            };
            return Response.ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition", "attachment; filename =" + path.getFileName().toString())
                    .header("Content-Length", path.toFile().length())
                    .build();
        } catch (OKOBusinessException ex) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }
}
