package com.cimne.tic.oko.server.ws.util;

import com.cimne.tic.oko.server.ws.BLL.OKOBusinessException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CriptografiaSimetrica {

	//Establecemos las variables privadas que contendrán
	//la llave de encripción así como el vector de inicialización.
	private String _key;
	private String _IV;
	//Acción a ejecutar dentro de los métodos.
	private CryptoProvider algorithm;

	public enum CryptoProvider {
		DES,
		TripleDES,
		RC2,
		AES,
	};
	public enum CryptoAction {
		Encrypt,
		Desencrypt
	};

	//Constructor por defecto.
	//Establece el algoritmo de Encripción a utilizar.</param>
	public CriptografiaSimetrica(CryptoProvider alg)
	{
		this.algorithm = alg;
	}

	public String getKey(){
		return _key;
	}
	public void setKey (String value)
	{
		_key = value;
	}

	public String getIV() {
		return _IV;
	}

	public void setIV(String value) {
		_IV = value;
	}

	//<summary>Cifra la cadena usando el proveedor especificado.</summary>
	//<param name="CadenaOriginal">Cadena que será cifrada.</param>
	//<returns>Devuelve la cadena cifrada.</returns>

	public byte[] encriptar (String cadenaOriginal) throws OKOBusinessException{

		//verificamos que la llave y el VI han sido proporcionados.
		try {
			if((_key != null) && (_IV != null))
			{
				return CriptoServiceProvider.encripta(cadenaOriginal.getBytes(), makeKeyByteArray(), makeIVByteArray(), algorithm);
			} else {
				throw new OKOBusinessException("Error al encryptar: Error al inicializar la clave y el vector");
			}
		} catch (Exception e) {
			throw new OKOBusinessException("Error al encryptar");
		}
	}
	public String desencriptar (byte[] cadenaOriginal) throws OKOBusinessException{

		//verificamos que la llave y el VI han sido proporcionados.
		try {
			if((_key != null) && (_IV != null))
			{
				return new  String(CriptoServiceProvider.desencripta(cadenaOriginal, makeKeyByteArray(), makeIVByteArray(), algorithm));
			} else {
				throw new OKOBusinessException("Error al encryptar: Error al inicializar la clave y el vector");
			}
		} catch (Exception e) {
			throw new OKOBusinessException("Error al encryptar");
		}
	}

    public void cifrarDescifrarArchivo(String inFileName, String outFileName, CryptoAction action) throws OKOBusinessException {
        //si el archivo especificado no existe,
        if (!Files.exists(Paths.get(inFileName))) {
            // generamos una excepción informándolo.
            throw new OKOBusinessException("No se ha encontrado el archivo.", -33);
        }
        try {
            //si la llave de cifrado y el VI están establecidos
            if (!_key.isEmpty() && !_IV.isEmpty()) {

                //creamos el flujo de entrada, desde el archivo original.
                FileInputStream inputStream = new FileInputStream(new File(inFileName));
                byte[] inputBytes = new byte[(int) new File(inFileName).length()];
                inputStream.read(inputBytes);

                switch (action) {
                    case Encrypt: {
                        byte[] outputBytes = CriptoServiceProvider.encripta(inputBytes, makeKeyByteArray(), makeIVByteArray(), algorithm);

                        //creamos el flujo de salida, hacía el archivo de salida especificado.
                        FileOutputStream outputStream = new FileOutputStream(new File(outFileName));
                        outputStream.write(outputBytes);

                        inputStream.close();
                        outputStream.close();
                    }
                    break;
                    case Desencrypt: {
                        byte[] outputBytes = CriptoServiceProvider.desencripta(inputBytes, makeKeyByteArray(), makeIVByteArray(), algorithm);

                        //creamos el flujo de salida, hacía el archivo de salida especificado.
                        FileOutputStream outputStream = new FileOutputStream(new File(outFileName));
                        outputStream.write(outputBytes);

                        inputStream.close();
                        outputStream.close();
                    }
                    break;
                }
            } else {
                throw new OKOBusinessException("error llave no valida");
            }
        } catch (OKOBusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OKOBusinessException("Error al Encriptar");
        }
    }


    private String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    private String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

    private byte[] makeIVByteArray() {
        //dependiendo del algoritmo utilizado.
        switch (algorithm) {
            //para los algoritmos
            case DES:
            case RC2:
            case TripleDES:
                if (_IV.length() < 8) {
                    //'verificamos que la longitud no sea menor que 8 bytes,
                    //de ser así, completamos la cadena hasta un valor válido
                    _IV = padRight(_IV, 8);
                } else if (_IV.length() > 8) {
                    //si la cadena supera los 8 bytes,
                    //truncamos la cadena dejándola en 8 bytes.
                    _IV = _IV.substring(8);
                }
                break;
            case AES:
                //verificamos que la longitud no sea menor que 16 bytes,
                if (_IV.length() < 16) {
                    //de ser así, completamos la cadena hasta un valor válido
                    _IV = padRight(_IV, 16);
                } else if (_IV.length() > 16) {
                    //si la cadena supera los 16 bytes,
                    //truncamos la cadena dejándola en 16 bytes.
                    _IV = _IV.substring(16);
                }
                break;
        }
        //utilizando los métodos del namespace System.Text,
        //convertimos la cadena de caracteres en un arreglo de bytes
        //mediante el método GetBytes() del sistema de codificación UTF.
        return _IV.getBytes();
    }

    private byte[] makeKeyByteArray() {
        //dependiendo del algoritmo utilizado.
        switch (algorithm) {
            //para los algoritmos
            case DES:
            case RC2:
                if (_key.length() < 8) {
                    //'verificamos que la longitud no sea menor que 8 bytes,
                    //de ser así, completamos la cadena hasta un valor válido
                    _key = padRight(_key, 8);
                } else if (_key.length() > 8) {
                    //si la cadena supera los 8 bytes,
                    //truncamos la cadena dejándola en 8 bytes.
                    _key = _key.substring(8);
                }
                break;
            case TripleDES:
            case AES:
                //verificamos que la longitud no sea menor que 16 bytes,
                if (_key.length() < 16) {
                    //de ser así, completamos la cadena hasta un valor válido
                    _key = padRight(_key, 16);
                } else if (_key.length() > 16) {
                    //si la cadena supera los 16 bytes,
                    //truncamos la cadena dejándola en 16 bytes.
                    _key = _key.substring(16);
                }
                break;
        }
        //utilizando los métodos del namespace System.Text,
        //convertimos la cadena de caracteres en un arreglo de bytes
        //mediante el método GetBytes() del sistema de codificación UTF.
        return _key.getBytes();
    }


}

