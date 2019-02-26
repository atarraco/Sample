package com.cimne.tic.oko.server.ws.DTO;

/**
 * Created by lorddarks on 13/2/17.
 * Respuesta del WS de contactos
 */
public class RespuestaWSContactos {

    public String urlAvatar;
    public String nombre;
    public String telefono;

    public RespuestaWSContactos( String urlAvatar, String username,String telef)
    {

        this.urlAvatar = urlAvatar;
        this.nombre = username;
        this.telefono = telef;

    }

}
