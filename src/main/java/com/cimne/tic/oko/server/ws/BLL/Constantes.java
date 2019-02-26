package com.cimne.tic.oko.server.ws.BLL;

public final class Constantes {

	public static final String SRV_EMAIL_FROM_ACCOUNT = "hello@okosmartframe.com";
	public static final String SRV_EMAIL_HOST = "authsmtp.okosmartframe.com";

	public static final String SRV_EMAIL_USR = "smtp@okosmartframe.com";
	public static final String SRV_EMAIL_PWD = "OKO_Proj3ct";
	public static final int SRV_EMAIL_PORT = 587;
	public static final Boolean SRV_EMAIL_ENABLE_SSL = false;


	public static final String CLAVE_ENCRIPTACION = "Fabiano Caruana";
	public static final String VI_ENCRIPTACION = "123";
	public static final String PATHERN_SPLIT= "ABCDE";

	public static final String DB_USER = "ownersitib";
	public static final String DB_PASS = "bitisrenwo";
	public static final String DB_URL = "jdbc:mysql://147.83.143.45:3306/oko?serverTimezone=UTC";
	public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";


	public static final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

	public static final String EMAIL_SUBJECT_ES = "OKO - Recuperar Contraseña";
	public static final String EMAIL_SUBJECT_EN = "OKO - Recover Password";
	public static final String EMAIL_SUBJECT2_ES = "OKO - Activar Usuario";
	public static final String EMAIL_SUBJECT2_EN = "OKO - Activate User";

	public static final String IPHONE_PWD_CERTIFICADO = "OHzuuge7pT";
	public static final String IPHONE_CERTIFICATE_PATH = null;
	public static final String IPHONE_SERVERPUSH_HOSTNAME = null;

	public static final String UPLOAD_FILES_PATH = "/oko/upload/";

    public static final String AUTHY_API_KEY = "FKhhTP1craZTB2tICQKGCCLOTa4BIx9o";



	private Constantes(){
		//this prevents even the native class from
		//calling this ctor as well :
		throw new AssertionError();
	}
}
