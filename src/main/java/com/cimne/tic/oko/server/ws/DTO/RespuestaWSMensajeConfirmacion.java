package com.cimne.tic.oko.server.ws.DTO;

public class RespuestaWSMensajeConfirmacion {

	public String idContenidoMultimedia;
	public String apiKeyUsuarioEmisor;
	public String apiKeyUsuarioReceptor;
	public String nombreUsuarioEmisor;
	public String nombreMarcoReceptor;
	public String url_medio;
	public String uuid;

	public RespuestaWSMensajeConfirmacion(String idContenidoMultimedia, String apiKeyUsuarioEmisor, String apiKeyUsuarioReceptor, String nombreUsuarioEmisor, String nombreMarcoReceptor, String url_medio, String uuid)
	{
		this.idContenidoMultimedia = idContenidoMultimedia;
		this.apiKeyUsuarioEmisor = apiKeyUsuarioEmisor;
		this.apiKeyUsuarioReceptor = apiKeyUsuarioReceptor;
		this.nombreUsuarioEmisor = nombreUsuarioEmisor;
		this.nombreMarcoReceptor = nombreMarcoReceptor;
		this.url_medio = url_medio;
		this.uuid = uuid;
	}
}
