package com.cimne.tic.oko.server.ws.DTO.Marco;

public class MensajeDesvincular {

	public String apiKey;
	public String uuid;

	public MensajeDesvincular(String uuid, String apiKey)
	{
		this.apiKey = apiKey;
		this.uuid = uuid;
	}
}
