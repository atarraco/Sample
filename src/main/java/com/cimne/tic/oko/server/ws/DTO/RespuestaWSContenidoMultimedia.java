package com.cimne.tic.oko.server.ws.DTO;

import java.util.ArrayList;
import java.util.List;

public class RespuestaWSContenidoMultimedia {

	public int cntImagenes;
	public int cntVideos;
	public int cntAudio;
	public int cntAlert;
	public List<RespuestaWSUploadFile> ls = new ArrayList<RespuestaWSUploadFile>();

	public RespuestaWSContenidoMultimedia()	{}
}
