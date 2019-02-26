package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.GestionUsuarios;
import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWS;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/{CambiarContrassenya : (?i)cambiarcontrassenya}")
public class CambiarContrassenya {

      @POST
      @Produces(MediaType.APPLICATION_JSON)
      public RespuestaWS<String> cambiarContrasseya(
              @FormParam("email") String email,
              @FormParam("oldPassword") String oldPassword,
              @FormParam("newPassword") String newPassword,
              @FormParam("saltnew") String saltnew) {
            try {
                  GestionUsuarios.CambiarPwdDesdeApp(email, oldPassword, newPassword, saltnew);
                  // Si OK
                  RespuestaWS<String> response = new RespuestaWS<>();
                  response.responseStatus = 0;
                  response.message = "OK";
                  response.error = "";
                  return response;
            } catch (OKOBusinessException ex) {
                  // Si error OKOBussinessException -1
                  RespuestaWS<String> response = new RespuestaWS<>();
                  response.responseStatus = ex.getErrorNumber();
                  response.message = "";
                  response.error = ex.getMessage();
                  return response;
            }
      }
}
