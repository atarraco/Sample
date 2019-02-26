package com.cimne.tic.oko.server.ws.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

public class Email {
	private static String _userFrom;
	private static String _host;
	private static int _port;
	private static String _userServer;
	private static String _userPwd;
	private static Boolean _enableSSL;
	private static String _pickupDirectory;
	public Email(String nombreUsuarioRemitente, String host, int puerto, String nombreUsuarioServidor,
			String pwdServidor,Boolean enableSSL) {
		_userFrom = nombreUsuarioRemitente;
		_host = host;
		_port = puerto;
		_userServer = nombreUsuarioServidor;
		_userPwd = pwdServidor;
		_enableSSL = enableSSL;
	}

	public String getUserForm() {
		return _userFrom;
	}

	public String getHost() {
		return _host;
	}

	public int getPort() {
		return _port;
	}

	public String getUserServer() {
		return _userServer;
	}

	public String getUserPwd() {
		return _userPwd;
	}

	public Boolean getEnableSSL() {
		return _enableSSL;
	}

	public String getPickupDirectory () {
		return _pickupDirectory;
	}

	public void setPickupDirectory (String directory) {
		_pickupDirectory = directory;
	}

	public void sendEmail( String toEmail, String subject, String body, Boolean isHtml) {

		//creamos la session

		Properties props = System.getProperties();

		props.put("mail.smtp.host", _host);
		props.put("mail.smtp.port", String.valueOf(_port)); //TLS Port
		props.put("mail.smtp.auth", String.valueOf(_enableSSL));

		Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(_userServer, _userPwd);
			}
		};


		Session session = Session.getInstance(props, auth);

		MimeMessage msg = new MimeMessage(session);

		try {
			//set message headers
			if(isHtml)
			{
				msg.addHeader("Content-type", "text/HTML; charset=UTF-8");

			}
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress(_userFrom, "OKO SmartFrame"));

			msg.setReplyTo(InternetAddress.parse(_userFrom, false));

			msg.setSubject(subject, "UTF-8");

			msg.setText(body, "UTF-8");

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

			Transport.send(msg, _userServer, _userPwd);
			//Transport.send(msg);
		} catch (MessagingException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public enum TipoFormatoFichero {
		None,
		Binario,
		Plano;
	}

}
