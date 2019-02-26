package com.cimne.tic.oko.server.ws.DTO;

import com.cimne.tic.oko.server.ws.model.Keys;
import com.cimne.tic.oko.server.ws.model.tables.records.DispositivosvirtualesRecord;

/**
 * Created by lorddarks on 1/2/17.
 */
public class RespuestaWSFrameInfo {

    public String nombreFrame;
    public String nombreUsuario;
    public int visibilidad; //Publico o privado
    public int privacidad;  //Control de contenido o no

    public RespuestaWSFrameInfo(String nombreFrame, String nombreUsuario, int visibilidad, int privacidad) {
        this.nombreFrame = nombreFrame;
        this.nombreUsuario = nombreUsuario;
        this.visibilidad = visibilidad;
        this.privacidad = privacidad;
    }

    public RespuestaWSFrameInfo(String nombreFrame, String nombreUsuario, int visibilidad) {
        this.nombreFrame = nombreFrame;
        this.nombreUsuario = nombreUsuario;
        this.visibilidad = visibilidad;
        this.privacidad = 0;
    }

    public RespuestaWSFrameInfo(DispositivosvirtualesRecord disp) {
        this.nombreFrame = disp.getNombre();
        this.nombreUsuario = disp.fetchParent(Keys.FK_USUARIOSPROPIETARIOS).getNombre();
        this.visibilidad = disp.getVisibilidad();
        this.privacidad = disp.getControlcontenido();
    }
}
