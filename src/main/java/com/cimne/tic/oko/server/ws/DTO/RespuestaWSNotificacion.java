package com.cimne.tic.oko.server.ws.DTO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RespuestaWSNotificacion {

	public int id;
	public String userGuid;
	public String email;
	public String nombreCompleto1;
	public String urlAvatar;
	public int tipoNotificacion;
	public String fecha;
	public String uuid;
	public String nombreMarco;
	public int visibilidad;

	public RespuestaWSNotificacion(int id, String userGuid, String nombreCompleto, String iconoUrl, int tipoNotificacion, Date fecha, String nombreMarco, String uuid, int visibilidad)
	{
		this.id = id;
		this.userGuid = userGuid;

		this.nombreCompleto1 = nombreCompleto;
		this.urlAvatar = iconoUrl;
		this.tipoNotificacion = tipoNotificacion;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		this.fecha = df.format(fecha);//fecha. .ToString("yyyy-MM-dd'T'HH:mm:ss");

		//--------------------------
		this.nombreMarco = nombreMarco;
		this.uuid = uuid;
		this.visibilidad = visibilidad;

	}
}
