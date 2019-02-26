package com.cimne.tic.oko.server.ws.types;

public enum RolUsuarioDispositivoType {

	None(0),
	Propietario(1),
	Administrador(2),
	User(3);

	private final int code;

	RolUsuarioDispositivoType(int code) {
		this.code = code;
	}

	public int toInt() {
		return code;
	}
}
