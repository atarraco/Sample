package com.cimne.tic.oko.server.ws.DTO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RespuestaWSUploadFile {

	public int contenidoMultimediaID;
	public String emisorGuid;
	public String url;
	public String thumb;
	public int tipo;
	public String fecha;
	public String emisor_name;
	public String mensaje;
	public int estado;
	public int feedback;

	public RespuestaWSUploadFile() { }

	public RespuestaWSUploadFile(int contenidoMultimediaId, String emisorGuid, String url, String thumb, int tipo, Date fecha, String emisor, String mensaje, int estado,int feedback) {

		//Random r = new Random();

		this.contenidoMultimediaID = contenidoMultimediaId;
		this.emisorGuid = emisorGuid;
		this.url = url;
		//this.thumb = thumb + "?id=" + r.Next(0, 100000);
		this.thumb = thumb;

		this.tipo = tipo;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		this.fecha = df.format(fecha);
		//this.fecha = fecha.ToString("yyyy-MM-dd'T'HH:mm:ss");
		this.emisor_name = emisor;
		this.mensaje = mensaje;

		this.estado = estado;
		this.feedback = feedback;
	}
}
