package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.GestionDispositivos;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWS;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWSDispositivo;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/{ListarOKOs : (?i)listarokos}")
public class ListarOKOs {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public RespuestaWS<List<RespuestaWSDispositivo>> listarOKOs (@FormParam("apikey") String apiKey) {

        RespuestaWS<List<RespuestaWSDispositivo>> response = new RespuestaWS<>();
        try {
            response.responseStatus = 0;
            response.message = GestionDispositivos.ListarOKOsEnviar(apiKey);
            response.error = "";
            return response;
        } catch (OKOBusinessException ex) {
            response.responseStatus = ex.getErrorNumber();
            response.message = null;
            response.error = ex.getMessage();
            return response;
        }
    }

}
