package com.cimne.tic.oko.server.ws.BLL;

import com.cimne.tic.oko.server.ws.BLL.Push.INotificacionesPush;
import com.cimne.tic.oko.server.ws.BLL.Push.NotificacionesPushFactory;
import com.cimne.tic.oko.server.ws.DTO.Marco.MensajeEliminarContenido;
import com.cimne.tic.oko.server.ws.DTO.Marco.MensajeMultimedia;
import com.cimne.tic.oko.server.ws.DTO.Marco.MensajeTipoWrapper;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWSContenidoMultimedia;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWSMensajeConfirmacion;
import com.cimne.tic.oko.server.ws.DTO.RespuestaWSUploadFile;
import com.cimne.tic.oko.server.ws.model.Keys;
import com.cimne.tic.oko.server.ws.model.Tables;
import com.cimne.tic.oko.server.ws.model.tables.records.*;
import com.cimne.tic.oko.server.ws.types.*;
import com.cimne.tic.oko.server.ws.util.CriptografiaSimetrica;
import com.cimne.tic.oko.server.ws.util.Fechas;
import com.cimne.tic.oko.server.ws.util.Seguridad;
import com.google.gson.Gson;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.cimne.tic.oko.server.ws.model.Tables.CONTENIDOSMULTIMEDIA;
import static com.cimne.tic.oko.server.ws.model.Tables.ESTADOCONTENIDOMULTIMEDIA;
import static com.cimne.tic.oko.server.ws.model.tables.Contenidosfisicos.CONTENIDOSFISICOS;

public class GestionContenido {

    public static RespuestaWSContenidoMultimedia ObtenerContenidosMultimedia(DispositivosvirtualesRecord dispositivoControlador, DispositivosvirtualesRecord dispositivoEspejo, UsuariosRecord usuario, ListaContenidoMultimediaType tipo) throws OKOBusinessException {

        try {
            RespuestaWSContenidoMultimedia respesuestaWSCONTENIDOSMULTIMEDIA = new RespuestaWSContenidoMultimedia();
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            List<ContenidosmultimediaRecord> contenidos = null;
            switch (tipo) {
                case ContenidoRecibido:
                    //TODO: falta saber que estado devolveremos!
                    // buscamos el contenido que se le ha enviado al controlador i el estado de quien?????
                    contenidos = create.select()
                            .from(CONTENIDOSMULTIMEDIA)
                            .join(ESTADOCONTENIDOMULTIMEDIA)
                            .on(ESTADOCONTENIDOMULTIMEDIA.IDCONTENIDOMULTIMEDIA.equal(CONTENIDOSMULTIMEDIA.IDCONTENIDOMULTIMEDIA))
                            .and(ESTADOCONTENIDOMULTIMEDIA.IDDISPOSITIVOVIRTUAL.equal(dispositivoEspejo.getIddispositivovirtual()))
                            .where(CONTENIDOSMULTIMEDIA.IDDISPOSITIVOVIRTUAL.equal(dispositivoControlador.getIddispositivovirtual()))
                            .and(ESTADOCONTENIDOMULTIMEDIA.ESTADO.equal(EstadoContenidoMultimediaType.Push_Sent_To_Device.toInt())
                                    .or(ESTADOCONTENIDOMULTIMEDIA.ESTADO.equal(EstadoContenidoMultimediaType.Ack_From_Google.toInt())))
                            .orderBy(CONTENIDOSMULTIMEDIA.FECHAINSERT.desc())
                            .fetchInto(ContenidosmultimediaRecord.class);
                    break;
                case ContenidoEnviado:
                    //contenidos = create.select().from(CONTENIDOSMULTIMEDIA).where(CONTENIDOSMULTIMEDIA.IDDISPOSITIVOVIRTUAL.equal(dispositivoControlador.getIddispositivovirtual())).and(Tables.CONTENIDOSMULTIMEDIA.IDUSUARIOEMISOR.equal(usuario.getIdusuario())).and(Tables.CONTENIDOSMULTIMEDIA.ESTADO.equal(EstadoCONTENIDOSMULTIMEDIAType.Push_Sent_To_Device.toInt()).or(Tables.CONTENIDOSMULTIMEDIA.ESTADO.equal(EstadoCONTENIDOSMULTIMEDIAType.Ack_From_Google.toInt()))).orderBy(Tables.CONTENIDOSMULTIMEDIA.FECHAINSERT.desc()).fetchInto(CONTENIDOSMULTIMEDIARecord.class);
                    contenidos = create.select()
                            .from(CONTENIDOSMULTIMEDIA)
                            .join(ESTADOCONTENIDOMULTIMEDIA)
                            .on(ESTADOCONTENIDOMULTIMEDIA.IDCONTENIDOMULTIMEDIA.equal(CONTENIDOSMULTIMEDIA.IDCONTENIDOMULTIMEDIA))
                            .where(CONTENIDOSMULTIMEDIA.IDDISPOSITIVOVIRTUAL.equal(dispositivoControlador.getIddispositivovirtual()))
                            .and(CONTENIDOSMULTIMEDIA.IDUSUARIOEMISOR.equal(usuario.getIdusuario()))
                            .and(ESTADOCONTENIDOMULTIMEDIA.ESTADO.equal(EstadoContenidoMultimediaType.Push_Sent_To_Device.toInt())
                                    .or(ESTADOCONTENIDOMULTIMEDIA.ESTADO.equal(EstadoContenidoMultimediaType.Ack_From_Google.toInt())))
                            .orderBy(CONTENIDOSMULTIMEDIA.FECHAINSERT.desc()).fetchInto(ContenidosmultimediaRecord.class);
                    break;
                case ContenidoOrdenaoPorTipo:
                    //TODO: arreglar pq hemos cambiado DB
                    //contenidos = create.select().from(Tables.CONTENIDOSMULTIMEDIA).where(Tables.CONTENIDOSMULTIMEDIA.IDDISPOSITIVOVIRTUAL.equal(dispositivo.getIddispositivovirtual())).and(Tables.CONTENIDOSMULTIMEDIA.ESTADO.equal(EstadoCONTENIDOSMULTIMEDIAType.Push_Sent_To_Device.toInt()).or(Tables.CONTENIDOSMULTIMEDIA.ESTADO.equal(EstadoCONTENIDOSMULTIMEDIAType.Ack_From_Google.toInt()))).orderBy(Tables.CONTENIDOSMULTIMEDIA.TIPO.desc()).fetchInto(CONTENIDOSMULTIMEDIARecord.class);
                    contenidos = create.select()
                            .from(CONTENIDOSMULTIMEDIA)
                            .join(ESTADOCONTENIDOMULTIMEDIA)
                            .on(ESTADOCONTENIDOMULTIMEDIA.IDCONTENIDOMULTIMEDIA.equal(CONTENIDOSMULTIMEDIA.IDCONTENIDOMULTIMEDIA))
                            .where(CONTENIDOSMULTIMEDIA.IDDISPOSITIVOVIRTUAL.equal(dispositivoControlador.getIddispositivovirtual()))
                            .and(ESTADOCONTENIDOMULTIMEDIA.ESTADO.equal(EstadoContenidoMultimediaType.Push_Sent_To_Device.toInt())
                                    .or(ESTADOCONTENIDOMULTIMEDIA.ESTADO.equal(EstadoContenidoMultimediaType.Ack_From_Google.toInt())))
                            .orderBy(CONTENIDOSMULTIMEDIA.TIPO.desc()).fetchInto(ContenidosmultimediaRecord.class);
                default:
                    break;
            }
            if (contenidos != null) {
                for (ContenidosmultimediaRecord conten : contenidos) {
                    switch (conten.getTipo()) {
                        // IMAGENES
                        case 1:
                            respesuestaWSCONTENIDOSMULTIMEDIA.cntImagenes++;
                            break;

                        // VIDEO
                        case 2:
                            respesuestaWSCONTENIDOSMULTIMEDIA.cntVideos++;
                            break;

                        // VIDEO
                        case 3:
                            respesuestaWSCONTENIDOSMULTIMEDIA.cntAudio++;
                            break;

                        // ALERT
                        case 4:
                            respesuestaWSCONTENIDOSMULTIMEDIA.cntAlert++;
                            break;
                    }

                    if (tipo.toInt() == ListaContenidoMultimediaType.ContenidoOrdenaoPorTipo.toInt()) {
                        respesuestaWSCONTENIDOSMULTIMEDIA.ls.add(new RespuestaWSUploadFile(conten.getIdcontenidomultimedia(), conten.fetchParent(Keys.FK_USUARIOSEMISOR).getUserguid(), conten.getUrl(), conten.fetchParent(Keys.FK_USUARIOSEMISOR).getUrlavatar(), conten.getTipo(), conten.getFechainsert(), conten.fetchParent(Keys.FK_USUARIOSEMISOR).getNombre(), conten.getTexto(), conten.fetchChild(Keys.FK_CONTENIDOMULTIMEDIAESTADO).getEstado(), conten.fetchChild(Keys.FK_CONTENIDOMULTIMEDIAESTADO).getFeedback()));
                    } else {

                        respesuestaWSCONTENIDOSMULTIMEDIA.ls.add(new RespuestaWSUploadFile(conten.getIdcontenidomultimedia(), conten.fetchParent(Keys.FK_USUARIOSEMISOR).getUserguid(), conten.getUrl(), conten.getUrlthumbnail(), conten.getTipo(), conten.getFechainsert(), conten.fetchParent(Keys.FK_USUARIOSEMISOR).getNombre(), conten.getTexto(), conten.fetchChild(Keys.FK_CONTENIDOMULTIMEDIAESTADO).getEstado(), conten.fetchChild(Keys.FK_CONTENIDOMULTIMEDIAESTADO).getFeedback()));
                    }
                }
                create.close();
                conn.close();
                return respesuestaWSCONTENIDOSMULTIMEDIA;
            } else {
                return null;
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    public static RespuestaWSUploadFile SubirContenido(InputStream uploadedInputStream, FormDataContentDisposition fileDetail, UsuariosRecord usuario, DispositivosvirtualesRecord dispositivo, String extension, int tipoMultiMedia, String mensaje) throws OKOBusinessException {
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            String PATH_UPLOAD = "/Media/Content/" + dispositivo.getUuid() + "/";
            String PATH_UPLOAD_THUMB = "/Media/Content/" + dispositivo.getUuid() + "/Thumb/";

            String pathCarpeta = Constantes.UPLOAD_FILES_PATH + "Content/" + dispositivo.getUuid() + "/";
            String pathCarpetaThumb = Constantes.UPLOAD_FILES_PATH + "Content/" + dispositivo.getUuid() + "/Thumb/";

            // Si la carpeta no existe
            if (!Files.exists(Paths.get(pathCarpeta))) {
                // la creamos ...
                Files.createDirectories(Paths.get(pathCarpeta));
            }
            // Si la carpeta no existe
            if (!Files.exists(Paths.get(pathCarpetaThumb))) {
                // la creamos ...
                Files.createDirectories(Paths.get(pathCarpetaThumb));
            }
            String nombreFicheroAleatorio = Seguridad.GenerarRandomFileName();
            if (!extension.isEmpty()) {
                extension = "." + extension;
            }
            String rutaArchivo = pathCarpeta + nombreFicheroAleatorio + extension;
            String rutaArchivoThumb = pathCarpetaThumb + nombreFicheroAleatorio + "jpg";

            WriteToFile(uploadedInputStream, rutaArchivo);

            String rutaArchivoENC = pathCarpeta + "ENC" + nombreFicheroAleatorio + extension;
            String rutaArchivoThumbENC = pathCarpetaThumb + "ENC" + nombreFicheroAleatorio + "jpg";

            CriptografiaSimetrica criptografiaSimetrica = new CriptografiaSimetrica(CriptografiaSimetrica.CryptoProvider.AES);
            criptografiaSimetrica.setKey(Constantes.CLAVE_ENCRIPTACION);
            criptografiaSimetrica.setIV(Constantes.VI_ENCRIPTACION);

            String urlArchivoENC = PATH_UPLOAD + "ENC" + nombreFicheroAleatorio + extension;
            String urlThumbnailENC = PATH_UPLOAD_THUMB + "ENC" + nombreFicheroAleatorio + extension;

            switch (tipoMultiMedia) {
                case 1: {
                    //IMAGEN
                    // THUMBNAIL;; Reescalamoa 100 x 100 pero manteniendo las proporciones
                    javax.imageio.ImageIO.write(CreateThumbnail(rutaArchivo, 100, 100), "JPG", new File(rutaArchivoThumb));
                    //encriptar
                    criptografiaSimetrica.cifrarDescifrarArchivo(rutaArchivo, rutaArchivoENC, CriptografiaSimetrica.CryptoAction.Encrypt);
                    // THUMB
                    criptografiaSimetrica.cifrarDescifrarArchivo(rutaArchivoThumb, rutaArchivoThumbENC, CriptografiaSimetrica.CryptoAction.Encrypt);
                    try {
                        Files.deleteIfExists(Paths.get(rutaArchivo));
                        Files.deleteIfExists(Paths.get(rutaArchivoThumb));
                    } catch (Exception ex) {
                        throw new OKOBusinessException(OKOErrorType.ERROR_DELETING_UNECRYPTED_FILES);
                    }
                }
                break;

                case 2: {
                    //VIDEO
                    javax.imageio.ImageIO.write(CreateThumbnailFromVideo(rutaArchivo), "JPG", new File(rutaArchivoThumb));
                    //encriptar
                    criptografiaSimetrica.cifrarDescifrarArchivo(rutaArchivo, rutaArchivoENC, CriptografiaSimetrica.CryptoAction.Encrypt);
                    //thumb
                    // THUMB
                    criptografiaSimetrica.cifrarDescifrarArchivo(rutaArchivoThumb, rutaArchivoThumbENC, CriptografiaSimetrica.CryptoAction.Encrypt);
                    try {
                        Files.deleteIfExists(Paths.get(rutaArchivo));
                        Files.deleteIfExists(Paths.get(rutaArchivoThumb));
                    } catch (Exception ex) {
                        throw new OKOBusinessException(OKOErrorType.ERROR_DELETING_UNECRYPTED_FILES);
                    }
                }
                break;

                case 3: {
                    // AUDIO
                    criptografiaSimetrica.cifrarDescifrarArchivo(rutaArchivo, rutaArchivoENC, CriptografiaSimetrica.CryptoAction.Encrypt);
                    try {
                        Files.deleteIfExists(Paths.get(rutaArchivo));
                    } catch (Exception ex) {
                        throw new OKOBusinessException(OKOErrorType.ERROR_DELETING_UNECRYPTED_FILES);
                    }
                }
                break;

                case 4:
                    // ALERT
                    break;
            }
            //insertamos el Contenido Multimedia a la db
            ContenidosmultimediaRecord contenidoMultimedia = create.newRecord(CONTENIDOSMULTIMEDIA);
            contenidoMultimedia.setIdusuarioemisor(usuario.getIdusuario());
            contenidoMultimedia.setIddispositivovirtual(dispositivo.getIddispositivovirtual());
            contenidoMultimedia.setTipo(tipoMultiMedia);
            contenidoMultimedia.setUrl(urlArchivoENC);
            contenidoMultimedia.setUrlthumbnail(urlThumbnailENC);
            contenidoMultimedia.setFechainsert(Fechas.GetCurrentTimestampLong());
            contenidoMultimedia.setTexto(mensaje);

            //creamos el Contenido fisico
            ContenidosfisicosRecord contenidoFisico = create.newRecord(CONTENIDOSFISICOS);
            contenidoFisico.setPathcontenidofisico(rutaArchivoENC);
            contenidoFisico.setPaththumbnail(rutaArchivoThumbENC);
            contenidoFisico.store();

            //lo insertamos
            contenidoMultimedia.setIdcontenidofisico(contenidoFisico.getIdcontenidofisico());
            contenidoMultimedia.store();

            //miramos si es espejo para crear los estados del cm

            if (dispositivo.getTipoespejo() == DispositivoEspejoType.NORMAL.toInt()) {
                //normal solo creamos un Estado del Contenido Multimedia
                CreaEstadoContenidoMultimedia(create, contenidoMultimedia, dispositivo, EstadoContenidoMultimediaType.Upload.toInt());
            } else if (dispositivo.getTipoespejo() == DispositivoEspejoType.CONTROLADOR.toInt()) {
                //hay que crear un estado para cada espejo
                List<DispositivosvirtualesRecord> dispositivosEspejos = dispositivo.fetchChildren(Keys.FK_PADRE);
                for (DispositivosvirtualesRecord dispEspejo : dispositivosEspejos) {
                    CreaEstadoContenidoMultimedia(create, contenidoMultimedia, dispEspejo, EstadoContenidoMultimediaType.Upload.toInt());
                }
            }
            return new RespuestaWSUploadFile(contenidoMultimedia.getIdcontenidomultimedia(),
                    usuario.getUserguid(), urlArchivoENC, urlThumbnailENC, tipoMultiMedia,
                    contenidoMultimedia.getFechainsert(), usuario.getNombre(), "", EstadoContenidoMultimediaType.Upload.toInt(), 0);

        } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    private static void CreaEstadoContenidoMultimedia(DSLContext create, ContenidosmultimediaRecord contenidoMultimedia, DispositivosvirtualesRecord dispositivo, int estado) {
        EstadocontenidomultimediaRecord estadoCM = create.newRecord(ESTADOCONTENIDOMULTIMEDIA);
        estadoCM.setEstado(estado);
        estadoCM.setFeedback(0);
        estadoCM.setIdcontenidomultimedia(contenidoMultimedia.getIdcontenidomultimedia());
        estadoCM.setIddispositivovirtual(dispositivo.getIddispositivovirtual());
        estadoCM.store();
    }

    public static Boolean EliminarContenido(String apiKey, int contenido_id, Boolean enviarPush) throws OKOBusinessException {
        try {
            Gson gson = new Gson();
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            ContenidosmultimediaRecord contenido = create.select()
                    .from(CONTENIDOSMULTIMEDIA)
                    .where(CONTENIDOSMULTIMEDIA.IDCONTENIDOMULTIMEDIA.equal(contenido_id))
                    .fetchAnyInto(ContenidosmultimediaRecord.class);
            if (contenido == null) throw new OKOBusinessException(OKOErrorType.NOT_FOUND);
            DispositivosvirtualesRecord dispVirtual = contenido.fetchParent(Keys.FK_CONTENIDOMULTIMEDIA);
            if (dispVirtual.fetchParent(Keys.FK_USUARIOSPROPIETARIOS).getApikey().equals(apiKey)) {
                String pathUpload = "/Upload/Content/" + dispVirtual.getUuid() + "/";
                //miramos si hay mas CM que tienen el mismo CF
                List<ContenidosmultimediaRecord> contenidosMultimedia = create.select()
                        .from(CONTENIDOSMULTIMEDIA)
                        .where(CONTENIDOSMULTIMEDIA.IDCONTENIDOFISICO.equal(contenido.getIdcontenidofisico()))
                        .fetchInto(ContenidosmultimediaRecord.class);
                if (contenidosMultimedia.size() == 1) {
                    ContenidosfisicosRecord contentFisico = contenido.fetchParent(Keys.FK_CONTENIDOFISICO);
                    BorrarArchivo(contentFisico.getPathcontenidofisico(), contentFisico.getPaththumbnail());
                    contentFisico.delete();
                }
                if (dispVirtual.getTipoespejo() == DispositivoEspejoType.CONTROLADOR.toInt()
                        || dispVirtual.getTipoespejo() == DispositivoEspejoType.ESPEJO.toInt()) {
                    // es espejo
                    //Borrar el CM el CF si hace falta y todos los ESTADOS DEL CM
                    List<EstadocontenidomultimediaRecord> estadosCM = contenido.fetchChildren(Keys.FK_CONTENIDOMULTIMEDIAESTADO);
                    for (EstadocontenidomultimediaRecord estadoCM : estadosCM) {
                        if (enviarPush)
                            EnviarPushBorraralMarco(estadoCM.fetchParent(Keys.FK_DISPOSITIVOVIRTUALESTADO)
                                    .fetchParent(Keys.FK_DISPOSITIVOFISICO)
                                    .getToken(), contenido_id);
                        estadoCM.delete();
                    }
                } else {
                    String tokenDestinatario = contenido.fetchParent(Keys.FK_CONTENIDOMULTIMEDIA).fetchParent(Keys.FK_DISPOSITIVOFISICO).getToken();
                    //BorrarArchivo(pathUpload, urlImagen, urlThumb);
                    // ENVIAR PUSH al marco
                    if (enviarPush && tokenDestinatario != null) {
                        MensajesFactory mensaje = new MensajesFactory();
                        MensajeTipoWrapper<MensajeEliminarContenido> eliminarContenido = mensaje.CrearEliminarContenido(contenido_id);
                        INotificacionesPush notificacionPush = NotificacionesPushFactory.Crear(NotificacionesPushFactoryType.ANDROID_PUSH_FRAME);
                        notificacionPush.EnviarPush(tokenDestinatario, gson.toJson(eliminarContenido), 0, 0);
                        EnviarPushBorraralMarco(tokenDestinatario, contenido_id);
                    }
                }
                contenido.delete();
                create.close();
                conn.close();
                return true;
            } else {
                if (contenido.fetchParent(Keys.FK_USUARIOSEMISOR).getApikey().equals(apiKey)) {
                    //contenido.setEliminadoporemisor(1);
                    contenido.store();
                    create.close();
                    conn.close();
                    return true;
                } else {
                    return false;
                }
            }

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    public static Boolean EliminarTodoElContenido(String uuid) throws OKOBusinessException {
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            DispositivosvirtualesRecord dispVirtual = GestionDispositivos.ObtenerDispositivoVirtual(uuid);
            if (dispVirtual == null) throw new OKOBusinessException(OKOErrorType.INVALID_DEVICE);
            //miramos si es tipo espejo para obtener el controlador
            if (dispVirtual.getTipoespejo() == DispositivoEspejoType.ESPEJO.toInt())
                dispVirtual = dispVirtual.fetchParent(Keys.FK_PADRE);
            //se borra en todos los espejos
            List<ContenidosmultimediaRecord> contenidosMultimedia = dispVirtual.fetchChildren(Keys.FK_CONTENIDOMULTIMEDIA);
            ContenidosfisicosRecord cotnenidoFisico;
            for (ContenidosmultimediaRecord contenido : contenidosMultimedia) {
                //si es el ultimo CM con el Fisico asociado borramos el fisico
                if (create.select().from(CONTENIDOSMULTIMEDIA)
                        .where(CONTENIDOSMULTIMEDIA.IDCONTENIDOFISICO.equal(contenido.getIdcontenidofisico()))
                        .fetchInto(ContenidosmultimediaRecord.class).size() == 1) {
                    cotnenidoFisico = contenido.fetchParent(Keys.FK_CONTENIDOFISICO);
                    BorrarArchivo(cotnenidoFisico.getPathcontenidofisico(), cotnenidoFisico.getPaththumbnail());
                    cotnenidoFisico.delete();
                }
                //Borramos los estados
                for (EstadocontenidomultimediaRecord estado : contenido.fetchChildren(Keys.FK_CONTENIDOMULTIMEDIAESTADO)) {
                    estado.delete();
                }
                contenido.delete();
            }
            return true;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    public static void WriteToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
        try {
            OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
            int read;
            byte[] bytes = new byte[1024];
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static Boolean Enviar(UsuariosRecord usuario, DispositivosvirtualesRecord dispositivo, String mensaje, String url_medio, String contenidoMultimediaId, Boolean alPropietario) throws OKOBusinessException {
        try {
            Gson gson = new Gson();
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            if (!GestionDispositivos.HayPermisosParaEnviar(usuario.getIdusuario(), dispositivo.getUuid()))
                throw new OKOBusinessException(OKOErrorType.NOT_ALLOWED);

            Date fecha = new Date();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String stringFecha = df.format(fecha);
            ContenidosmultimediaRecord contenidoMultimedia = create.select()
                    .from(CONTENIDOSMULTIMEDIA)
                    .where(CONTENIDOSMULTIMEDIA.IDCONTENIDOMULTIMEDIA.equal(Integer.parseInt(contenidoMultimediaId)))
                    .fetchAnyInto(ContenidosmultimediaRecord.class);
            if (contenidoMultimedia == null) throw new OKOBusinessException(OKOErrorType.CONTENT_NOT_FOUND);
            MensajesFactory mensajeImagen = new MensajesFactory();
            List<EstadocontenidomultimediaRecord> estadosCM = contenidoMultimedia.fetchChildren(Keys.FK_CONTENIDOMULTIMEDIAESTADO);
            for (EstadocontenidomultimediaRecord estado : estadosCM) {
                estado.setEstado(EstadoContenidoMultimediaType.Request_Launch_Push.toInt());
                estado.store();
                if (!alPropietario) {
                    // PUSH al marco
                    MensajeTipoWrapper<MensajeMultimedia> imagen = mensajeImagen.CrearMensajeEnviarContenido(contenidoMultimediaId, String.valueOf(usuario.getIdusuario()), usuario.getUrlavatar(), usuario.getNombre(), mensaje, url_medio, stringFecha);
                    INotificacionesPush notificacionPush = NotificacionesPushFactory.Crear(NotificacionesPushFactoryType.ANDROID_PUSH_FRAME);
                    DispositivosvirtualesRecord dispVirtual = estado.fetchParent(Keys.FK_DISPOSITIVOVIRTUALESTADO);
                    DispositivosfisicosRecord dispFisico = dispVirtual.fetchParent(Keys.FK_DISPOSITIVOFISICO);
                    String token = dispFisico.getToken();
                    notificacionPush.EnviarPush(token, gson.toJson(imagen), Integer.parseInt(contenidoMultimediaId), estado.getIdestadocontenidomultimedia());
                }
            }
            if (mensaje == null) {
                mensaje = "";
            }
            contenidoMultimedia.setTexto(mensaje);
            contenidoMultimedia.store();

            if (alPropietario) {
                // PUSH al usuario, para que confirme el envio
                MensajesFactory mensajeConfirmacion = new MensajesFactory();
                MensajeTipoWrapper<RespuestaWSMensajeConfirmacion> confirmacion = mensajeConfirmacion.CrearConfirmacion(contenidoMultimediaId, usuario.getUserguid(), dispositivo.fetchParent(Keys.FK_USUARIOSPROPIETARIOS).getApikey(), usuario.getNombre(), dispositivo.getNombre(), contenidoMultimedia.getUrl(), dispositivo.getUuid());

                INotificacionesPush notificacionPush;
                if (usuario.fetchChild(Keys.FK_USUARIOSMARTPHONE).getTipo() == 1)
                    notificacionPush = NotificacionesPushFactory.Crear(NotificacionesPushFactoryType.ANDROID_PUSH_APP);
                else
                    notificacionPush = NotificacionesPushFactory.Crear(NotificacionesPushFactoryType.IPHONE_PUSH_APP);

                notificacionPush.EnviarPush(usuario.fetchChild(Keys.FK_USUARIOSMARTPHONE).getToken(), gson.toJson(confirmacion), Integer.parseInt(contenidoMultimediaId), 0);
            }
            return true;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException ex) {
            // Si error GENERICO -1
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    public static BufferedImage CreateThumbnail(String sourceImage, int thumbWidth, int thumbHeight) throws OKOBusinessException {
        try {
            Image image = javax.imageio.ImageIO.read(new File(sourceImage));
            double thumbRatio = (double) thumbWidth / (double) thumbHeight;
            int imageWidth = image.getWidth(null);
            int imageHeight = image.getHeight(null);
            double imageRatio = (double) imageWidth / (double) imageHeight;
            if (thumbRatio < imageRatio) {
                thumbHeight = (int) (thumbWidth / imageRatio);
            } else {
                thumbWidth = (int) (thumbHeight * imageRatio);
            }
            if (imageWidth < thumbWidth && imageHeight < thumbHeight) {
                thumbWidth = imageWidth;
                thumbHeight = imageHeight;
            } else if (imageWidth < thumbWidth)
                thumbWidth = imageWidth;
            else if (imageHeight < thumbHeight)
                thumbHeight = imageHeight;
            BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = thumbImage.createGraphics();
            graphics2D.setBackground(Color.WHITE);
            graphics2D.setPaint(Color.WHITE);
            graphics2D.fillRect(0, 0, thumbWidth, thumbHeight);
            graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);

            return thumbImage;
        } catch (IOException e) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    static void BorrarArchivo(String rutaArchivo, String rutaArchivoThumb) throws OKOBusinessException {

        File file = new File(rutaArchivo);
        if (file.exists()) {
            if (!file.delete()) throw new OKOBusinessException(OKOErrorType.ERROR_DELETING_FILE);
        }
        file = new File(rutaArchivoThumb);
        if (file.exists()) {
            if (!file.delete()) throw new OKOBusinessException(OKOErrorType.ERROR_DELETING_FILE);
        }
    }

    private static BufferedImage CreateThumbnailFromVideo(String videoPath) throws OKOBusinessException {
        try {
            FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(videoPath);
            frameGrabber.start();
            org.bytedeco.javacv.Frame i;
            i = frameGrabber.grabImage();
            frameGrabber.stop();
            return new Java2DFrameConverter().convert(i);
        } catch (FrameGrabber.Exception e) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    public static int NumContenidosMultimedia(DSLContext create, int dispositivoControladorID, int dispositivoEspejoID) {
        return create.select()
                .from(CONTENIDOSMULTIMEDIA)
                .innerJoin(ESTADOCONTENIDOMULTIMEDIA)
                .on(CONTENIDOSMULTIMEDIA.IDCONTENIDOMULTIMEDIA.equal(ESTADOCONTENIDOMULTIMEDIA.IDCONTENIDOMULTIMEDIA))
                .where(CONTENIDOSMULTIMEDIA.IDDISPOSITIVOVIRTUAL.equal(dispositivoControladorID))
                .and(ESTADOCONTENIDOMULTIMEDIA.IDDISPOSITIVOVIRTUAL.equal(dispositivoEspejoID))
                .and(ESTADOCONTENIDOMULTIMEDIA.ESTADO.equal(EstadoContenidoMultimediaType.Push_Sent_To_Device.toInt())
                        .or(ESTADOCONTENIDOMULTIMEDIA.ESTADO.equal(EstadoContenidoMultimediaType.Ack_From_Google.toInt())))
                .fetchInto(ContenidosmultimediaRecord.class).size();
    }

    public static Boolean IsImagen(String extension) {
        return (extension.equals("jpg") || extension.equals("jpeg"));
    }

    public static String GetMimeTypeFromExtension(String extension) {
        String contentType;
        switch (extension) {
            case "mp4":
            case "m4v":
                contentType = "video/mp4";
                break;

            case "ogv":
                contentType = "video/ogg";
                break;

            case "webm":
                contentType = "video/webm";
                break;

            case "mov":
                contentType = "video/quicktime";
                break;

            case "aac":
                contentType = "audio/aac";
                break;

            case "oga":
            case "ogg":
                contentType = "audio/ogg";
                break;

            case "wav":
                contentType = "audio/wav";
                break;

            case "m4a":
                contentType = "audio/mp4";
                break;

            case "mp3":
                contentType = "audio/mp3";
                break;
            case "jpg":
            case "jpeg":
                contentType = "image/jpeg";
                break;

            default:
                return null;
        }
        return contentType;
    }

    public static void UpdatePushMessageIDinCM(String messageID, int contenidoMultimediaID, int estadoContenidoMultimediaID) throws OKOBusinessException {
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            EstadocontenidomultimediaRecord estadoCM = create.select()
                    .from(ESTADOCONTENIDOMULTIMEDIA)
                    .where(ESTADOCONTENIDOMULTIMEDIA.IDCONTENIDOMULTIMEDIA.equal(contenidoMultimediaID))
                    .and(ESTADOCONTENIDOMULTIMEDIA.IDESTADOCONTENIDOMULTIMEDIA.equal(estadoContenidoMultimediaID))
                    .fetchAnyInto(EstadocontenidomultimediaRecord.class);
            if (estadoCM != null) {
                estadoCM.setIdmessage(messageID);
                estadoCM.store();
            }
            create.close();
            conn.close();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    static Boolean CopiarContenido(String uuidOrigen, String uuidDestino) throws OKOBusinessException {

        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            DispositivosvirtualesRecord dispVirtualOrigen = GestionDispositivos.ObtenerDispositivoVirtual(uuidOrigen);
            DispositivosvirtualesRecord dispVirtualDest = GestionDispositivos.ObtenerDispositivoVirtual(uuidDestino);

            if ((dispVirtualOrigen == null) || (dispVirtualDest == null))
                throw new OKOBusinessException(OKOErrorType.INVALID_DEVICE);
            //miramos si el origen es un espejo si lo es cogemos el contenido multimedia del padre/controlador
            if (dispVirtualOrigen.getTipoespejo() == DispositivoEspejoType.ESPEJO.toInt()) {
                dispVirtualOrigen = dispVirtualOrigen.fetchParent(Keys.FK_PADRE);
            }

            List<ContenidosmultimediaRecord> contenidos = create.select()
                    .from(CONTENIDOSMULTIMEDIA)
                    .innerJoin(ESTADOCONTENIDOMULTIMEDIA)
                    .on(CONTENIDOSMULTIMEDIA.IDCONTENIDOMULTIMEDIA.equal(ESTADOCONTENIDOMULTIMEDIA.IDCONTENIDOMULTIMEDIA))
                    .where(CONTENIDOSMULTIMEDIA.IDDISPOSITIVOVIRTUAL.equal(dispVirtualOrigen.getIddispositivovirtual()))
                    .and(ESTADOCONTENIDOMULTIMEDIA.ESTADO.equal(EstadoContenidoMultimediaType.Push_Sent_To_Device.toInt()))
                    .fetchInto(ContenidosmultimediaRecord.class);
            ContenidosmultimediaRecord newContent;
            for (ContenidosmultimediaRecord content : contenidos) {
                newContent = create.newRecord(CONTENIDOSMULTIMEDIA);
                newContent.setTipo(content.getTipo());
                newContent.setTexto(content.getTexto());
                newContent.setUrl(content.getUrl());
                newContent.setUrlthumbnail(content.getUrlthumbnail());
                newContent.setFechainsert(Fechas.GetCurrentTimestampLong());
                newContent.setIdcontenidofisico(content.getIdcontenidofisico());
                newContent.setIddispositivovirtual(dispVirtualDest.getIddispositivovirtual());
                newContent.setIdusuarioemisor(content.getIdusuarioemisor());
                newContent.store();

                CreaEstadoContenidoMultimedia(create, newContent, dispVirtualDest, EstadoContenidoMultimediaType.Push_Sent_To_Device.toInt());
            }
            return true;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    private static void EnviarPushBorraralMarco(String tokenDestinatario, int contenido_id) throws OKOBusinessException {
        Gson gson = new Gson();
        MensajesFactory mensaje = new MensajesFactory();
        MensajeTipoWrapper<MensajeEliminarContenido> eliminarContenido = mensaje.CrearEliminarContenido(contenido_id);
        INotificacionesPush notificacionPush = NotificacionesPushFactory.Crear(NotificacionesPushFactoryType.ANDROID_PUSH_FRAME);
        //TODO enviar a todos los espejos!
        notificacionPush.EnviarPush(tokenDestinatario, gson.toJson(eliminarContenido), 0, 0);
    }

    static EstadocontenidomultimediaRecord CreaEstadoCM(int contenidoMultimediaID, int dispositivoVirtualID) throws OKOBusinessException {
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            EstadocontenidomultimediaRecord estadoCM = create.newRecord(Tables.ESTADOCONTENIDOMULTIMEDIA);
            estadoCM.setEstado(EstadoContenidoMultimediaType.Request_Launch_Push.toInt());
            estadoCM.setFeedback(0);
            estadoCM.setIdcontenidomultimedia(contenidoMultimediaID);
            estadoCM.setIddispositivovirtual(dispositivoVirtualID);
            estadoCM.store();
            return estadoCM;
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException ex) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    public static RespuestaWSContenidoMultimedia ListarContenidoMultimedia(String apiKey, String uuid, ListaContenidoMultimediaType tipo) throws OKOBusinessException {
        UsuariosRecord usuario = GestionUsuarios.GetUserfromApiKey(apiKey);
        if (usuario == null) throw new OKOBusinessException(OKOErrorType.USER_NOT_FOUND);
        DispositivosvirtualesRecord dispositivo = GestionDispositivos.ObtenerDispositivoVirtual(uuid);
        if (dispositivo == null) throw new OKOBusinessException(OKOErrorType.INVALID_DEVICE);
        if (tipo == ListaContenidoMultimediaType.ContenidoRecibido && !usuario.getIdusuario().equals(dispositivo.getIdpropietario()))
            throw new OKOBusinessException(OKOErrorType.NOT_OWNER);
        if (dispositivo.getEstado() != EstadoVinculacionType.Vinculado.toInt())
            throw new OKOBusinessException(OKOErrorType.NOT_LINKED);
        if (dispositivo.getTipoespejo() == DispositivoEspejoType.NORMAL.toInt()) {
            return ObtenerContenidosMultimedia(dispositivo, dispositivo, usuario, tipo);
        } else {
            return ObtenerContenidosMultimedia(dispositivo.fetchParent(Keys.FK_PADRE), dispositivo, usuario, tipo);
        }

    }
}