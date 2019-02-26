package com.cimne.tic.oko.server.ws.DTO;

import java.util.ArrayList;
import java.util.List;

public class RespuestaWSDispositivos {

    public String uuid;
    public String nombre;
    public String urlAvatar;
    public int estado;
    public int visibilidad;
    public int rol;
    public int tipoEspejo;
    public List<RespuestaWSDispositivo> ls = new ArrayList<RespuestaWSDispositivo>();

    public RespuestaWSDispositivos() {
    }

	// Constructor
	public RespuestaWSDispositivos(String nombre, String avatar, List<RespuestaWSDispositivo> values, String aUUID)
	{
        this.nombre = nombre;
        this.urlAvatar = avatar;
        ls.addAll(values);
        this.uuid = aUUID;
    }
}
