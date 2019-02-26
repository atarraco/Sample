package com.cimne.tic.oko.server.ws.DTO;

/**
 * Created by lorddarks on 22/2/17.
 * Respuesta WS validar codigo telefono
 */
public class RespuestaWSTelefono {

    public String mensaje;
    public int newUser;

    public RespuestaWSTelefono(String message, int nuevoUsuario) {
        this.mensaje = message;
        this.newUser = nuevoUsuario;
    }
}
