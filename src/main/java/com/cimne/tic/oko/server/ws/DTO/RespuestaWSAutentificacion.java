package com.cimne.tic.oko.server.ws.DTO;

import com.cimne.tic.oko.server.ws.types.EstadoNotificacionType;

public class RespuestaWSAutentificacion {

	public String apiKey;
	public String urlAvatar;
	public String username;
	public String nombreCompleto;
	public EstadoNotificacionType estado;
	public Boolean notificacionPendiente;
	public int primeraVez;  // 1: PrimeraVez;; 2:: Directo SEND ;; 3:: ListaUsuario
	// Es opcional ..... estos parametros dependen de PRIMERAVEZ
	public String idDispositivo;
	public String nombreDispositivo;
	public int visibilidad;


	public RespuestaWSAutentificacion(String apiKey, String urlAvatar, String username, String nombreCompleto)
	{
		this.apiKey = apiKey;
		this.urlAvatar = urlAvatar;
		this.username = username;
		this.nombreCompleto = nombreCompleto;
	}

	public RespuestaWSAutentificacion(String apiKey, String urlAvatar, String username, String nombreCompleto, EstadoNotificacionType estado)
	{
		this.apiKey = apiKey;
		this.urlAvatar = urlAvatar;
		this.username = username;
		this.nombreCompleto = nombreCompleto;
		this.estado = estado;
	}
}
