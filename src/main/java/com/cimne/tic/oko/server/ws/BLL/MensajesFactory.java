package com.cimne.tic.oko.server.ws.BLL;


import com.cimne.tic.oko.server.ws.DTO.RespuestaWSMensajeConfirmacion;
import com.cimne.tic.oko.server.ws.DTO.Marco.MensajeAlerta;
import com.cimne.tic.oko.server.ws.DTO.Marco.MensajeDesvincular;
import com.cimne.tic.oko.server.ws.DTO.Marco.MensajeEliminarContenido;
import com.cimne.tic.oko.server.ws.DTO.Marco.MensajeMultimedia;
import com.cimne.tic.oko.server.ws.DTO.Marco.MensajeTipoWrapper;

public class MensajesFactory {

	public MensajesFactory(){ }

	public MensajeTipoWrapper<MensajeAlerta> CrearAlerta(String alerta_id, String idUsuarioEmisor, String user_thumb, String user_name, String mensaje, String fecha)
	{
		return new MensajeTipoWrapper<MensajeAlerta>(1, new MensajeAlerta(alerta_id, idUsuarioEmisor, user_thumb, user_name, mensaje, fecha));
	}

	public MensajeTipoWrapper<MensajeMultimedia> CrearMensajeEnviarContenido(String contenidoMultimedia_id, String idUsuarioEmisor, String user_thumb, String user_name, String mensaje, String url_medio, String fecha)
	{
		return new MensajeTipoWrapper<MensajeMultimedia>(2, new MensajeMultimedia(contenidoMultimedia_id, idUsuarioEmisor, user_thumb, user_name, mensaje, url_medio, fecha));
	}

	public MensajeTipoWrapper<MensajeDesvincular> CrearDesvincular(String uuid, String apiKey)
	{
		return new MensajeTipoWrapper<MensajeDesvincular>(3, new MensajeDesvincular(uuid, apiKey));
	}

	public MensajeTipoWrapper<RespuestaWSMensajeConfirmacion> CrearConfirmacion(String contenidoMultimediaID, String apiKeyUsuarioEmisor, String apiKeyUsuarioReceptor, String nombreUsuarioEmisor, String nombreMarcoEmisor, String url_medio, String uuid)
	{
		return new MensajeTipoWrapper<RespuestaWSMensajeConfirmacion>(4, new RespuestaWSMensajeConfirmacion(contenidoMultimediaID, apiKeyUsuarioEmisor, apiKeyUsuarioReceptor, nombreUsuarioEmisor, nombreMarcoEmisor, url_medio, uuid));
	}

	public MensajeTipoWrapper<MensajeEliminarContenido> CrearEliminarContenido(int contenidoMultimediaID)
	{
		return new MensajeTipoWrapper<MensajeEliminarContenido>(5, new MensajeEliminarContenido(contenidoMultimediaID));
	}


	public String CrearMensajeParaIPhone_SerAmigo(String titulo)
	{
		return Mensaje(titulo, "TITULO_SER_TU_AMIGO", "");
	}

	public String CrearMensajeParaIPhone_NuevoOKO(String titulo, String nombreMarco)
	{
		return Mensaje(titulo, "TITULO_TIENE_NUEVO_OKO", nombreMarco);
	}

	public String CrearMensajeParaIPhone_AceptadaAmistad(String titulo)
	{
		return Mensaje(titulo, "TITULO_ACEPTADO_AMISTAD", "");
	}

	private String Mensaje(String titulo, String claveRecurso, String arg)
	{
		String msg = "\"title\":\"" + titulo + "\"";
		msg += ",\"loc-key\":\"" + claveRecurso + "\"";
		if (!arg.isEmpty())
		{
			msg += ",\"loc-args\":[\"" + arg + "\"]";
		}
		return msg;

	}
}
