package com.cimne.tic.oko.server.ws.DTO;

public class RespuestaWSDispositivo {

	public String uuid;
	public String name;
    public String urlAvatar;
    public int estado;
    public int rol;
    public int tipoEspejo;

	public RespuestaWSDispositivo()
	{
	}

    public RespuestaWSDispositivo(String uuid, String name, int estado, String urlAvatar, int aRol, int tipoEspejo) {
        this.uuid = uuid;
        this.name = name;
        this.estado = estado;
        //this.visibilidad = visibilidad;
        this.tipoEspejo = tipoEspejo;
        this.rol = aRol;
        this.urlAvatar = urlAvatar;
    }
}
