package com.cimne.tic.oko.server.ws.DTO.Marco;

public class MensajeAlerta {

	public String alerta_id;
	public String user_id;
	public String user_thumb;
	public String user_name;
	public String mensaje;
	public String fecha;


	public MensajeAlerta(String alerta_id, String user_id, String user_thumb, String user_name, String mensaje, String fecha)
	{
		this.alerta_id = alerta_id;
		this.user_id = user_id;
		this.user_thumb = user_thumb;
		this.user_name = user_name;
		this.mensaje = mensaje;
		this.fecha = fecha;
	}
}
