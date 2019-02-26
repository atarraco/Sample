package com.cimne.tic.oko.server.ws.DTO;

/**
 * Created by lorddarks on 7/2/17.
 * Respuesta al WS update
 */
public class RespuestaWSUpdate {

    public int idUpdate;
    public String md5hash;
    public String changeLog;
    public int canUpdate;

    public RespuestaWSUpdate() {
    }
}
