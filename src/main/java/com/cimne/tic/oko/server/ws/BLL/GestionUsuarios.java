package com.cimne.tic.oko.server.ws.BLL;

import com.cimne.tic.oko.server.ws.DTO.RespuestaWSAutentificacion;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWSContactos;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWSNotificacion;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWSUsuario;
import com.cimne.tic.oko.server.ws.model.Keys;
import com.cimne.tic.oko.server.ws.model.Tables;
import com.cimne.tic.oko.server.ws.model.tables.records.*;
import com.cimne.tic.oko.server.ws.types.EstadoNotificacionType;
import com.cimne.tic.oko.server.ws.types.OKOErrorType;
import com.cimne.tic.oko.server.ws.types.RolUsuarioDispositivoType;
import com.cimne.tic.oko.server.ws.util.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

import static com.cimne.tic.oko.server.ws.model.Tables.*;

public class GestionUsuarios {

    private static int MIN_LEN_PWD = 8;

    public static RespuestaWSAutentificacion AutentificarUsuario(String username, String password) throws OKOBusinessException {

        username = username.toLowerCase();
        //conectamos al jooq
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            UsuariosRecord usuario = create.select()
                    .from(USUARIOS)
                    .where(USUARIOS.EMAIL.equal(username))
                    .fetchAnyInto(UsuariosRecord.class);
            if (usuario == null) throw new OKOBusinessException(OKOErrorType.USER_NOT_FOUND);
            String pwd = usuario.getPwd().toLowerCase();
            String salt = usuario.getSalt().toLowerCase();
            String hashCompletodesdeBD = Seguridad.GenerarSHA56(pwd + salt);
            if (password.toLowerCase().equals(hashCompletodesdeBD))
                throw new OKOBusinessException(OKOErrorType.LOGIN_ERROR);
            if (usuario.getEmailvalidado() == null || usuario.getEmailvalidado() == 0
                    || usuario.getTelefonovalidado() == null || usuario.getTelefonovalidado() == 0)
                throw new OKOBusinessException(OKOErrorType.USER_NOT_FOUND);
            //hay notificacion pendiente
            List<UsuariosRecord> usuarios0 = create.select().from(NOTIFICACIONES)
                    .join(USUARIOS).on(NOTIFICACIONES.IDUSUARIOEMISOR.equal(USUARIOS.IDUSUARIO))
                    .where(NOTIFICACIONES.IDUSUARIORECEPTOR.equal(usuario.getIdusuario()))
                    .and(NOTIFICACIONES.STATUS.equal(EstadoNotificacionType.PENDING.toInt()))
                    .fetchInto(UsuariosRecord.class);
            RespuestaWSAutentificacion respuestaWSUsuario = new RespuestaWSAutentificacion(usuario.getApikey(), usuario.getUrlavatar(), usuario.getEmail(), usuario.getNombre());
            respuestaWSUsuario.notificacionPendiente = !(usuarios0 == null || usuarios0.size() == 0);
            create.close();
            conn.close();
            return respuestaWSUsuario;
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException ex) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    public static String InsertarUsuario(String email, String password, String nombreUsuario, String salt, String idioma, String telefono, String url) throws OKOBusinessException {
        email = email.trim();
        password = password.trim();
        nombreUsuario = nombreUsuario.trim();
        ValidacionParametros(email, password, nombreUsuario, salt);
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            //miramos si existe ya el mail o el telefono y si existe se le dice q repetido!
            String telefonoEnc = EncriptarTelefono(telefono);
            UsuariosRecord usuario = create.select()
                    .from(USUARIOS)
                    .where(USUARIOS.EMAIL.equal(email))
                    .or(USUARIOS.TELEFONO.equal(telefonoEnc))
                    .fetchAnyInto(UsuariosRecord.class);
            if (usuario != null) throw new OKOBusinessException(OKOErrorType.USER_ALREADY_EXISTS);
            UsuariosRecord usr = create.newRecord(USUARIOS);
            usr.setEmail(email.toLowerCase());
            usr.setApikey(UUID.randomUUID().toString());
            usr.setNombre(nombreUsuario);
            usr.setUrlavatar("/Upload/User/" + Seguridad.GenerateSecureRandomString() + "/" + Seguridad.GenerarRandomFileName() + "_thumb.jpg");
            usr.setPwd(password);
            usr.setSalt(Seguridad.GenerarSHA56(String.valueOf(Integer.parseInt(salt) * 8)));
            usr.setIdioma(idioma);
            usr.setUserguid(UUID.randomUUID().toString());
            usr.setTelefono(telefonoEnc);
            usr.setTelefonovalidado(1);
            usr.setFechainsert(Fechas.GetCurrentTimestampLong());
            usr.store();

            String subjectCorreoEstablecimiento;
            if (idioma != null) {
                if (idioma.toLowerCase().equals("es")) {
                    subjectCorreoEstablecimiento = Constantes.EMAIL_SUBJECT2_ES;
                } else {
                    // By default language
                    subjectCorreoEstablecimiento = Constantes.EMAIL_SUBJECT2_EN;
                }
            } else {
                // By default language
                subjectCorreoEstablecimiento = "ccc"; //TODO: constantes mail HttpContext.GetGlobalResourceObject("Literales", "subject2", System.Globalization.CultureInfo.CreateSpecificCulture("en")).ToString();
            }
            String enlace = url + "Activate?enlace=" + URLEncoder.encode(EncriptarEnlace(usr.getApikey()), java.nio.charset.StandardCharsets.UTF_8.toString());
            Email correo = new Email(Constantes.SRV_EMAIL_FROM_ACCOUNT, Constantes.SRV_EMAIL_HOST, Constantes.SRV_EMAIL_PORT, Constantes.SRV_EMAIL_USR, Constantes.SRV_EMAIL_PWD, Constantes.SRV_EMAIL_ENABLE_SSL);
            //TODO: arreglar plantilla
            String textoEmail = "Prueba";//TextoMail(enlace + "&lang=" + idioma, "mail/Activate.aspx", url, "&lang=" + idioma);
            correo.sendEmail(usr.getEmail(), subjectCorreoEstablecimiento, textoEmail, true);

            create.close();
            conn.close();
            return usr.getApikey();
        } catch (InstantiationException | SQLException | IllegalAccessException | UnsupportedEncodingException | ClassNotFoundException ex) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    public static void RecuperarContrasenya(String email, String url) throws OKOBusinessException {
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            //miramos si existe ya el mail o el telefono y si existe se le dice q repetido!
            UsuariosRecord usuario = create.select().from(USUARIOS)
                    .where(USUARIOS.EMAIL.equal(email))
                    .fetchAnyInto(UsuariosRecord.class);
            if (usuario == null) throw new OKOBusinessException(OKOErrorType.USER_NOT_FOUND);
            String subjectCorreoEstablecimiento;
            if (usuario.getIdioma() != null) {
                if (usuario.getIdioma().toLowerCase().equals("es")) {
                    subjectCorreoEstablecimiento = Constantes.EMAIL_SUBJECT_ES;
                } else {
                    // By default language
                    subjectCorreoEstablecimiento = Constantes.EMAIL_SUBJECT_EN;
                }
            } else {
                // By default language
                subjectCorreoEstablecimiento = Constantes.EMAIL_SUBJECT_EN;
            }
            String enlace = url + "recuperarpwd.aspx?enlace=" + URLEncoder.encode(EncriptarEnlace(usuario.getApikey()), java.nio.charset.StandardCharsets.UTF_8.toString());
            //TODO GENERAR PLANTILLA HTML
            Email correo = new Email(Constantes.SRV_EMAIL_FROM_ACCOUNT, Constantes.SRV_EMAIL_HOST, Constantes.SRV_EMAIL_PORT, Constantes.SRV_EMAIL_USR, Constantes.SRV_EMAIL_PWD, Constantes.SRV_EMAIL_ENABLE_SSL);
            String textoEmail = TextoMail(enlace + "&lang=" + usuario.getIdioma(), "/mail/RecoverPWD.aspx", url, "&lang=" + usuario.getIdioma());
            correo.sendEmail(email, subjectCorreoEstablecimiento, textoEmail, true);
        } catch (UnsupportedEncodingException | ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    public static void CambiarPwdDesdeApp(String email, String oldPassword, String newPassword, String saltNewPassword) throws OKOBusinessException {
        try {
            if (Integer.parseInt(saltNewPassword) <= (GestionUsuarios.MIN_LEN_PWD - 1))
                throw new OKOBusinessException(OKOErrorType.PASSWORD_TOO_SHORT);
            // ¿Login correcto?
            AutentificarUsuario(email, oldPassword);
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            //miramos si existe ya el mail o el telefono y si existe se le dice q repetido!
            UsuariosRecord usuario = create.select()
                    .from(USUARIOS)
                    .where(USUARIOS.EMAIL.equal(email))
                    .fetchAnyInto(UsuariosRecord.class);
            if (usuario == null) throw new OKOBusinessException(OKOErrorType.USER_NOT_FOUND);
            usuario.setPwd(newPassword);
            usuario.setSalt(Seguridad.GenerarSHA56(String.valueOf(Integer.parseInt(saltNewPassword) * 8)));
            usuario.store();
            create.close();
            conn.close();
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException ex) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    public static List<RespuestaWSContactos> UploadPhoneContact(String apiKey, String stringArray) throws OKOBusinessException {
        //Se suben los telefonos se encriptan y se crean Los contactos del usuario
        // y se comprueba si esxiste el telefono en oko.
        UsuariosRecord usuario = GetUserfromApiKey(apiKey);
        if (usuario == null) throw new OKOBusinessException(OKOErrorType.USER_NOT_FOUND);
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonArray telefonosJson = (JsonArray) jsonParser.parse(stringArray);
        Type collectionType = new TypeToken<String>() {
        }.getType();
        List<String> telefonos = gson.fromJson(telefonosJson, collectionType);
        List<RespuestaWSContactos> listaContactos = new ArrayList<>();
        String telefonoEnc;
        for (String telefono : telefonos) {
            //encryptamos el telefono
            telefonoEnc = EncriptarTelefono(telefono);
            //miramos si ya existe para el usuairo
            UsuarioscontactosRecord contacto = GetContactoFromTelefonoENC(usuario.getIdusuario(), telefonoEnc);
            if (contacto == null) // si no existe lo creamos
            {
                contacto = CreaUsuarioContacto(usuario.getIdusuario(), telefonoEnc);
            }
            if (contacto.getIdusuariocontacto() != null) // si ya lo tiene se lo devolvemos
                listaContactos.add(new RespuestaWSContactos(contacto.fetchParent(Keys.FK_USUARIOSCONTACTOS_USUARIOS1).getUrlavatar(), contacto.fetchParent(Keys.FK_USUARIOSCONTACTOS_USUARIOS1).getNombre(), telefono));
            else {
                //sino miramos si ya existe en oko
                UsuariosRecord usuarioContacto = GetUserFromTelefonoENC(telefonoEnc);
                if (usuarioContacto != null) {
                    contacto.setIdusuariocontacto(usuarioContacto.getIdusuario());
                    contacto.store();
                    //añadimos a la respuesta, se pasa el telefono desencriptado
                    listaContactos.add(new RespuestaWSContactos(usuarioContacto.getUrlavatar(), usuarioContacto.getNombre(), telefono));
                    //actualizamos la info al usuario receptor si estamos en su agenda
                    UpdateTelefonoDelContacto(usuarioContacto.getIdusuario(), usuario.getIdusuario(), usuario.getTelefono());
                }
            }
        }
        return listaContactos;
    }

    public static void RegistroPushMovil(String apiKey, String token, int tipo) throws OKOBusinessException {
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            UsuariosRecord usuario = GetUserfromApiKey(apiKey);
            if (usuario == null) throw new OKOBusinessException(OKOErrorType.USER_NOT_FOUND);
            SmartphonesRecord smartphone = usuario.fetchChild(Keys.FK_USUARIOSMARTPHONE);
            if (smartphone != null) {
                smartphone.setFechainsert(Fechas.GetCurrentTimestampLong());
                smartphone.setIdusuario(usuario.getIdusuario());
                smartphone.store();
            } else {
                SmartphonesRecord nSmartphone = create.newRecord(Tables.SMARTPHONES);
                nSmartphone.setTipo(tipo);
                nSmartphone.setToken(token);
                nSmartphone.setFechainsert(Fechas.GetCurrentTimestampLong());
                nSmartphone.setIdusuario(usuario.getIdusuario());
                nSmartphone.store();
            }
            create.close();
            conn.close();
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException ex) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    public static boolean AutentificarUsuarioFrame(String uuid_marco, String password_user) throws OKOBusinessException {
        DispositivosvirtualesRecord dispositivo = GestionDispositivos.ObtenerDispositivoVirtual(uuid_marco);
        if (dispositivo == null) throw new OKOBusinessException(OKOErrorType.INVALID_DEVICE);
        UsuariosRecord usuario = dispositivo.fetchParent(Keys.FK_USUARIOSPROPIETARIOS);
        if (usuario == null) throw new OKOBusinessException(OKOErrorType.USER_NOT_FOUND);
        if (usuario.getTelefonovalidado() != 1 || usuario.getEmailvalidado() != 1)
            throw new OKOBusinessException(OKOErrorType.USER_NOT_VALIDATED);
        String pwd = usuario.getPwd();
        String salt = usuario.getSalt();
        String hashCompletodesdeBD = Seguridad.GenerarSHA56(pwd + salt);
        if (!password_user.equals(hashCompletodesdeBD)) throw new OKOBusinessException(OKOErrorType.LOGIN_ERROR);
        return true;
    }

    private static void ValidacionParametros(String email, String password, String nombreUsuario, String salt) throws OKOBusinessException {

        if (!ValidarTipos.EsEmailValido(email))
            throw new OKOBusinessException(OKOErrorType.INVALID_MAIL_FORMAT);

        if (!ValidarTipos.ValidatePassword(password))
            throw new OKOBusinessException(OKOErrorType.INVALID_PASSWORD_FORMAT);

        if ((nombreUsuario == null) || (nombreUsuario.isEmpty()))
            throw new OKOBusinessException(OKOErrorType.INVALID_NAME);

        if (Integer.parseInt(salt) <= (GestionUsuarios.MIN_LEN_PWD - 1))
            throw new OKOBusinessException(OKOErrorType.PASSWORD_TOO_SHORT);
    }

    private static String EncriptarEnlace(String apiKey) throws OKOBusinessException {
        CriptografiaSimetrica criptografiaSimetrica = new CriptografiaSimetrica(CriptografiaSimetrica.CryptoProvider.AES);
        criptografiaSimetrica.setKey(Constantes.CLAVE_ENCRIPTACION);
        criptografiaSimetrica.setIV(Constantes.VI_ENCRIPTACION);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String enlace = String.valueOf(calendar.get(Calendar.YEAR)) + Constantes.PATHERN_SPLIT +
                apiKey + Constantes.PATHERN_SPLIT +
                String.valueOf(calendar.get(Calendar.MONTH)) + Constantes.PATHERN_SPLIT +
                String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + Constantes.PATHERN_SPLIT +
                String.valueOf(calendar.get(Calendar.HOUR)) + Constantes.PATHERN_SPLIT +
                UUID.randomUUID().toString() + Constantes.PATHERN_SPLIT +
                String.valueOf(calendar.get(Calendar.MINUTE));

        return Base64.getEncoder().encodeToString(criptografiaSimetrica.encriptar(enlace));
    }

    private static String TextoMail(String enlace, String localizacionPlantillaEmail, String url, String idioma) throws OKOBusinessException {
        //Util40.ClienteWebPost post = new Util40.ClienteWebPost(url + localizacionPlantillaEmail + "?enlace=" + System.Web.HttpUtility.UrlEncode(enlace));
        //List<KeyValuePair<string, string>> listaParametros = new List<KeyValuePair<string, string>>();
        //listaParametros.Add(new KeyValuePair<string, string>("enlace", enlace));
        //return post.EnviarDatosServidorGet();
        //TODO PLANTILLA HTML MAIL
        url = url.replace("https://", "http://");

        //Util40.CriptografiaSimetrica criptografiaSimetrica = new Util40.CriptografiaSimetrica(Util40.CriptografiaSimetrica.CryptoProvider.AES);
        //criptografiaSimetrica.Key = Constantes.CLAVE_ENCRIPTACION;
        //criptografiaSimetrica.IV = Constantes.VI_ENCRIPTACION;
        //String x = Convert.ToBase64String(criptografiaSimetrica.Encriptar(System.Web.HttpUtility.UrlEncode(enlace)), Base64FormattingOptions.None);

        try {
            return GetURLContent(url + localizacionPlantillaEmail + "?enlace=" + URLEncoder.encode(enlace, java.nio.charset.StandardCharsets.UTF_8.toString()) + idioma);
        } catch (UnsupportedEncodingException e) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }

    }

    private static String GetURLContent(String laURL) {

        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(laURL);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            //TODO CHECK EXCEPTION
            e.printStackTrace();
        }
        return content.toString();

    }

    public static UsuariosRecord GetUserfromApiKey(String apiKey) throws OKOBusinessException {
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            return create.select()
                    .from(USUARIOS)
                    .where(USUARIOS.APIKEY.equal(apiKey))
                    .fetchAnyInto(UsuariosRecord.class);
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException ex) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    private static UsuarioscontactosRecord GetContactoFromTelefonoENC(int userID, String telefono) throws OKOBusinessException {
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            return create.select()
                    .from(USUARIOSCONTACTOS)
                    .where(USUARIOSCONTACTOS.IDUSUARIO.equal(userID))
                    .and(USUARIOSCONTACTOS.TELEFOO.equal(telefono))
                    .fetchAnyInto(UsuarioscontactosRecord.class);
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException ex) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    private static UsuariosRecord GetUserFromTelefonoENC(String telefono) throws OKOBusinessException {
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            return create.select()
                    .from(USUARIOS)
                    .where(USUARIOS.TELEFONO.equal(telefono))
                    .fetchAnyInto(UsuariosRecord.class);
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException ex) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    private static void UpdateTelefonoDelContacto(int idUsuarioContacto, int idUsuario, String telefono) throws OKOBusinessException {
        //telefono pasado ya esta encryptado
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            UsuarioscontactosRecord contact = create.select()
                    .from(USUARIOSCONTACTOS)
                    .where(USUARIOSCONTACTOS.IDUSUARIO.equal(idUsuarioContacto))
                    .and(USUARIOSCONTACTOS.TELEFOO.equal(telefono))
                    .fetchAnyInto(UsuarioscontactosRecord.class);
            if (contact != null) {
                contact.setIdusuariocontacto(idUsuario);
                contact.store();
            }
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException ex) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    private static UsuarioscontactosRecord CreaUsuarioContacto(int usuarioID, String telefono) throws OKOBusinessException {
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            UsuarioscontactosRecord contacto = create.newRecord(USUARIOSCONTACTOS);
            contacto.setTelefoo(telefono);
            contacto.setIdusuario(usuarioID);
            contacto.store();
            return contacto;
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException ex) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    static void CrearUsuarioPropietarioDispositivo(UsuariosRecord usuario, DispositivosvirtualesRecord dispVirtual) throws OKOBusinessException {
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            UsuariosdispositivosvirtualesRecord usuariosDispVirtuales = create.newRecord(Tables.USUARIOSDISPOSITIVOSVIRTUALES);
            usuariosDispVirtuales.setIdusuario(usuario.getIdusuario());
            usuariosDispVirtuales.setIddispositivovirtual(dispVirtual.getIddispositivovirtual());
            usuariosDispVirtuales.setRol(RolUsuarioDispositivoType.Propietario.toInt());
            usuariosDispVirtuales.store();
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException ex) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    public static RespuestaWSUsuario InformacionUsuario(String apiKey) throws OKOBusinessException {

        UsuariosRecord usuario = GetUserfromApiKey(apiKey);
        if (usuario == null) throw new OKOBusinessException(OKOErrorType.USER_NOT_FOUND);
        String urlThumbnail = usuario.getUrlavatar();
        String urlAvatar = "";
        if ((urlThumbnail != null) && (!urlThumbnail.isEmpty())) {
            urlAvatar = urlThumbnail;
        }
        usuario.setLastlogin(Fechas.GetCurrentTimestampLong());
        usuario.store();
        return new RespuestaWSUsuario(urlAvatar, usuario.getEmail(), usuario.getNombre(), usuario.getTelefono());
    }

    public static String LogOut(String apiKey) throws OKOBusinessException {
        //Habria que borrar el APIKEY a uno distinto o algo
        UsuariosRecord usuario = GetUserfromApiKey(apiKey);
        if (usuario == null) throw new OKOBusinessException(OKOErrorType.USER_NOT_FOUND);

        List<SmartphonesRecord> smrtphns = usuario.fetchChildren(Keys.FK_USUARIOSMARTPHONE);
        for (SmartphonesRecord r : smrtphns) {
            r.delete();
        }
        return "OK";
    }

    public static List<RespuestaWSNotificacion> ListarNofitifaciones(String apiKey) throws OKOBusinessException {
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            UsuariosRecord usuario = GetUserfromApiKey(apiKey);
            if (usuario == null) throw new OKOBusinessException(OKOErrorType.USER_NOT_FOUND);
            List<RespuestaWSNotificacion> respuestaWSNotificacion = new ArrayList<>();
            List<NotificacionesRecord> listaNotifs = create.select()
                    .from(NOTIFICACIONES)
                    .where(NOTIFICACIONES.IDUSUARIOEMISOR.equal(usuario.getIdusuario()))
                    .and(NOTIFICACIONES.STATUS.equal(EstadoNotificacionType.PENDING.toInt()))
                    .orderBy(NOTIFICACIONES.FECHAINSERT.desc())
                    .fetchInto(NotificacionesRecord.class);
            String nombreMarco = "";
            int visibilidad = 0;
            String uuid = "";
            for (NotificacionesRecord notif : listaNotifs) {
                if (notif.getNuevookoid() != null) {
                    DispositivosvirtualesRecord disp = create.select()
                            .from(DISPOSITIVOSVIRTUALES)
                            .where(DISPOSITIVOSVIRTUALES.IDDISPOSITIVOVIRTUAL.equal(notif.getNuevookoid()))
                            .fetchAnyInto(DispositivosvirtualesRecord.class);
                    if (disp != null) {
                        nombreMarco = disp.getNombre();
                        visibilidad = disp.getVisibilidad();
                        uuid = disp.getUuid();
                        respuestaWSNotificacion.add(new RespuestaWSNotificacion(notif.getIdnotificacion(),
                                notif.fetchParent(Keys.FK_EMISOR).getUserguid(),
                                notif.fetchParent(Keys.FK_EMISOR).getNombre(),
                                notif.fetchParent(Keys.FK_EMISOR).getUrlavatar(),
                                notif.getTipo(),
                                new Date(notif.getFechainsert().getTime()), nombreMarco, uuid, visibilidad));
                    } else {
                        // Si la notficacion era sobr eun nuevo OKO y este ya no existe pues ... no lo mostramos!!!!
                        nombreMarco = "";
                        visibilidad = 0;
                        uuid = "";
                    }
                } else {
                    respuestaWSNotificacion.add(new RespuestaWSNotificacion(notif.getIdnotificacion(),
                            notif.fetchParent(Keys.FK_EMISOR).getUserguid(),
                            notif.fetchParent(Keys.FK_EMISOR).getNombre(),
                            notif.fetchParent(Keys.FK_EMISOR).getUrlavatar(),
                            notif.getTipo(),
                            new Date(notif.getFechainsert().getTime()), nombreMarco, uuid, visibilidad));
                }
            }
            return respuestaWSNotificacion;
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException ex) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    public static Boolean ExisteTelefonoUsuario(String telefono) throws OKOBusinessException {
        try {
            String telefonoEnc = EncriptarTelefono(telefono);
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            UsuariosRecord usuario = create.select()
                    .from(USUARIOS)
                    .where(USUARIOS.TELEFONO.equal(telefonoEnc))
                    .fetchAnyInto(UsuariosRecord.class);
            return usuario != null;
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException ex) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    private static String EncriptarTelefono(String telefono) throws OKOBusinessException {
        CriptografiaSimetrica criptografiaSimetrica = new CriptografiaSimetrica(CriptografiaSimetrica.CryptoProvider.AES);
        criptografiaSimetrica.setKey(Constantes.CLAVE_ENCRIPTACION);
        criptografiaSimetrica.setIV(Constantes.VI_ENCRIPTACION);
        return Base64.getEncoder().encodeToString(criptografiaSimetrica.encriptar(telefono));
    }

    private static String DesencriptarTelefono(String telefonoEnc) throws OKOBusinessException {
        CriptografiaSimetrica criptografiaSimetrica = new CriptografiaSimetrica(CriptografiaSimetrica.CryptoProvider.AES);
        criptografiaSimetrica.setKey(Constantes.CLAVE_ENCRIPTACION);
        criptografiaSimetrica.setIV(Constantes.VI_ENCRIPTACION);
        return criptografiaSimetrica.desencriptar(Base64.getDecoder().decode(telefonoEnc));
    }
}
