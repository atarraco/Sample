package com.cimne.tic.oko.server.ws.DTO;

import com.cimne.tic.oko.server.ws.types.EstadoNotificacionType;

public class RespuestaWSUsuario {

	public String urlAvatar;
	public String username;
	public String telefono;
	public String nombreCompleto;
	public EstadoNotificacionType estado;
	//public int primeraVez;  // 1: PrimeraVez;; 2:: Directo SEND ;; 3:: ListaUsuario
	// Es opcional ..... estos parametros dependen de PRIMERAVEZ
	//public String idDispositivo;
	//public String nombreDispositivo;
	//public int visibilidad;


	public RespuestaWSUsuario( String urlAvatar, String username, String nombreCompleto,String telef)
	{

		this.urlAvatar = urlAvatar;
		this.username = username;
		this.nombreCompleto = nombreCompleto;
		this.telefono = telef;

	}

	public RespuestaWSUsuario( String urlAvatar, String username, String nombreCompleto, EstadoNotificacionType estado, String telef)
	{

		this.urlAvatar = urlAvatar;
		this.username = username;
		this.nombreCompleto = nombreCompleto;
		this.estado = estado;
		this.telefono = telef;

	}


}
