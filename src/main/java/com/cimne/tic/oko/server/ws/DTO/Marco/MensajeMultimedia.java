package com.cimne.tic.oko.server.ws.DTO.Marco;

public class MensajeMultimedia {

	public String imagen_id;
	public String user_id;
	public String user_thumb;
	public String user_name;
	public String mensaje;
	public String url_medio;
	public String fecha;

	public MensajeMultimedia(String imagen_id, String user_id, String user_thumb, String user_name, String mensaje, String url_medio, String fecha)
	{
		this.imagen_id = imagen_id;
		this.user_id = user_id;
		this.user_thumb = user_thumb;
		this.user_name = user_name;
		this.mensaje = mensaje;
		this.url_medio = url_medio;
		this.fecha = fecha;
	}
}
