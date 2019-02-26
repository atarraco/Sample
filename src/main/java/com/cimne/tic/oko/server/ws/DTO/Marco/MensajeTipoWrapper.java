package com.cimne.tic.oko.server.ws.DTO.Marco;

public class MensajeTipoWrapper<T> {

	public int tipoMensaje;
	public T mensaje;

	public MensajeTipoWrapper(int tipo, T t1) {
		this.tipoMensaje = tipo;
		this.mensaje = t1;
	}
}
