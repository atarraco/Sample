package com.cimne.tic.oko.server.ws.util;

import com.cimne.tic.oko.server.ws.util.CriptografiaSimetrica.CryptoProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

final class CriptoServiceProvider {


	private CriptoServiceProvider(){
		//this prevents even the native class from
		//calling this ctor as well :
		throw new AssertionError();
	}

	static byte[] encripta(byte[] datosAEncriptar, byte[] key, byte[] IV, CryptoProvider cProv)
	{
		switch (cProv)
		{
		case DES:
			break;
		case RC2:
			break;
		case TripleDES:
			break;
		case AES:
			try {
				Cipher aesCipherForEncryption = Cipher.getInstance("AES/CBC/PKCS5Padding");
				SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");

				aesCipherForEncryption.init(Cipher.ENCRYPT_MODE, secretKeySpec,	new IvParameterSpec(IV));

				return aesCipherForEncryption.doFinal(datosAEncriptar);
			} catch (IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException e) {
				e.printStackTrace();
			}
            break;
		default:
			break;

		}
		return null;
	}

	static byte[] desencripta(byte[] datosEncriptados, byte[] key, byte[] IV, CryptoProvider cProv)
	{
		switch (cProv)
		{
		case DES:
			break;
		case RC2:
			break;
		case TripleDES:
			break;
		case AES:
			try {
				Cipher aesCipherForEncryption = Cipher.getInstance("AES/CBC/PKCS5Padding");
				SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
				aesCipherForEncryption.init(Cipher.DECRYPT_MODE, secretKeySpec,	new IvParameterSpec(IV));
				return aesCipherForEncryption.doFinal(datosEncriptados);
			} catch (IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
				e.printStackTrace();
			}
            break;
		default:
			break;

		}
		return null;
	}
}
