package com.cimne.tic.oko.server.ws.util;

import com.cimne.tic.oko.server.ws.BLL.Constantes;

public final class ValidarTipos {


	public final static Boolean EsEmailValido(String mail){

		return mail.matches(Constantes.EMAIL_REGEX);
	}

	public final static Boolean ValidatePassword(String password)
	{
		if ((password == null)||(password.isEmpty())) return false;
		return password.matches("^[a-zA-Z0-9]*$");
	}
	private ValidarTipos(){
		//this prevents even the native class from calling this constructor as well :
		throw new AssertionError();
	}
}
