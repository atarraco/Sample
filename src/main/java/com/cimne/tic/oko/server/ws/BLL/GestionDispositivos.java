package com.cimne.tic.oko.server.ws.BLL;

import com.cimne.tic.oko.server.ws.BLL.Push.INotificacionesPush;
import com.cimne.tic.oko.server.ws.BLL.Push.NotificacionesPushFactory;
import com.cimne.tic.oko.server.ws.DTO.Marco.MensajeAlerta;
import com.cimne.tic.oko.server.ws.DTO.Marco.MensajeTipoWrapper;
import com.cimne.tic.oko.server.ws.DTO.*;
import com.cimne.tic.oko.server.ws.model.Keys;
import com.cimne.tic.oko.server.ws.model.Tables;
import com.cimne.tic.oko.server.ws.model.tables.records.*;
import com.cimne.tic.oko.server.ws.types.*;
import com.cimne.tic.oko.server.ws.util.Fechas;
import com.google.gson.Gson;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.cimne.tic.oko.server.ws.model.Tables.*;
import static com.cimne.tic.oko.server.ws.model.tables.Dispositivosfisicos.DISPOSITIVOSFISICOS;

/**
 * Created by lorddarks on 28/1/17.
 * Gestion de los Dispositivos(Marcos)
 */
public class GestionDispositivos {


    public static RespuestaWS<RespuestaWSDispositivos> registrarDispositivo(String apiKey, String macAddress) throws OKOBusinessException {

        try {
            RespuestaWS<RespuestaWSDispositivos> response;
            UsuariosRecord usuario = GestionUsuarios.GetUserfromApiKey(apiKey);
            if (usuario == null) throw new OKOBusinessException(OKOErrorType.USER_NOT_FOUND);
            macAddress = macAddress.replace("-", "");
            RespuestaWSDispositivos estadoDispositivo;
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            DispositivosfisicosRecord dispFisico = create.select()
                    .from(Tables.DISPOSITIVOSFISICOS)
                    .join(Tables.NUMEROSSERIE)
                    .on(Tables.NUMEROSSERIE.NUMEROSERIE.equal(macAddress))
                    .fetchAnyInto(DispositivosfisicosRecord.class);
            if (dispFisico == null) throw new OKOBusinessException(OKOErrorType.NO_TOKEN);
            if (dispFisico.getIdempresa() == 0) {
                // no es empresa con lo que 1 fisico 1 virtual! miramos si tiene ya uno
                DispositivosvirtualesRecord dispVirtual = dispFisico.fetchChild(Keys.FK_DISPOSITIVOFISICO);
                if (dispVirtual == null) {
                    //Si no existe entonces lo creamos
                    dispVirtual = create.newRecord(DISPOSITIVOSVIRTUALES);
                    dispVirtual.setNombre("Marco");
                    dispVirtual.setUuid(UUID.randomUUID().toString());
                    dispVirtual.setVisibilidad(VisibilidadDispositivoType.Privado.toInt());
                    dispVirtual.setEstado(EstadoVinculacionType.None.toInt());
                    dispVirtual.setTipoespejo(DispositivoEspejoType.NORMAL.toInt());
                    dispVirtual.setIdpropietario(usuario.getIdusuario());
                    dispVirtual.setControlcontenido(0);
                    dispVirtual.store();

                    GestionUsuarios.CrearUsuarioPropietarioDispositivo(usuario, dispVirtual);

                    estadoDispositivo = VinculaDispositivoVirtual(usuario, dispVirtual, dispFisico);
                    response = HandShake(usuario, dispVirtual, estadoDispositivo);
                    create.close();
                    conn.close();
                    return response;
                }
                // miramos si ya esta vinculado!
                if (dispVirtual.getEstado() == EstadoVinculacionType.Vinculado.toInt()) {
                    //TODO: que hacer añadir otro usuario?
                    return null;
                } else if ((dispVirtual.getEstado() == EstadoVinculacionType.Desvinculado.toInt()) || (dispVirtual.getEstado() == EstadoVinculacionType.DesvinculadoCompleted.toInt())) {
                    //Esta desvinculado lo vinculamos de nuevo y cambiamos el propietario //LIMBO TO ACTIVE CAMBIAR PROPIETARIO
                    estadoDispositivo = VinculaDispositivoVirtual(usuario, dispVirtual, dispFisico);
                    response = HandShake(usuario, dispVirtual, estadoDispositivo);
                    create.close();
                    conn.close();
                    return response;
                } else {
                    //TODO Que hacer si esta en otro estado?
                    return null;
                }
            } else {
                //es empresa!
                //hay que buscar si el usuario tiene ya un dispositivo virtual de esa empresa
                DispositivosvirtualesRecord dispVirtual = create.select().from(DISPOSITIVOSVIRTUALES)
                        .join(DISPOSITIVOSFISICOS)
                        .on(DISPOSITIVOSVIRTUALES.IDDISPOSITIVOFISICO.equal(DISPOSITIVOSFISICOS.IDDISPOSITIVOSFISICO))
                        .where(DISPOSITIVOSVIRTUALES.IDPROPIETARIO.equal(usuario.getIdusuario()))
                        .and(DISPOSITIVOSFISICOS.IDEMPRESA.equal(dispFisico.getIdempresa()))
                        .fetchAnyInto(DispositivosvirtualesRecord.class);
                if (dispVirtual == null) //no lo tenia -> se le crea.
                {
                    dispVirtual = create.newRecord(DISPOSITIVOSVIRTUALES);
                    dispVirtual.setNombre("Marco");
                    dispVirtual.setUuid(UUID.randomUUID().toString());
                    dispVirtual.setVisibilidad(VisibilidadDispositivoType.Privado.toInt());
                    dispVirtual.setEstado(EstadoVinculacionType.None.toInt());
                    dispVirtual.setTipoespejo(DispositivoEspejoType.NORMAL.toInt());
                    dispVirtual.setIdpropietario(usuario.getIdusuario());
                    dispVirtual.setControlcontenido(0);
                    dispVirtual.store();
                }
                //existe! hay que canviar el id de disp fisico i el estado
                estadoDispositivo = VinculaDispositivoVirtual(usuario, dispVirtual, dispFisico);
                response = HandShake(usuario, dispVirtual, estadoDispositivo);
                create.close();
                conn.close();
                return response;
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    public static String ConfigurarNuevoOKO(String apiKey, String uuid, int tipo, String uuidDispExistente) throws OKOBusinessException {
        UsuariosRecord usuario = GestionUsuarios.GetUserfromApiKey(apiKey);
        if (usuario == null) throw new OKOBusinessException(OKOErrorType.USER_NOT_FOUND);
        DispositivosvirtualesRecord dispVirtual = null;
        dispVirtual = ObtenerDispositivoVirtual(uuid);
        if (dispVirtual == null) throw new OKOBusinessException(OKOErrorType.INVALID_DEVICE);
        //devuelve nuevo si el numero no es conocido.
        switch (TiposConfiguracionNuevoOKO.getTipoFromInt(tipo)) {
            case NUEVO:
                //como es nuevo no se hace nada! se le da el ok al frame i acorrer
                break;
            case ESPEJO:
                //se confiugra el espejo
                if (GestionDispositivos.ConfigurarEspejo(uuidDispExistente, uuid))
                    return "OK";
                break;
            case COPIAR_CONTENIDO:
                //se copia el contenido multimedia!
                if (GestionContenido.CopiarContenido(uuidDispExistente, uuid))
                    return "OK";
                break;
        }
        throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
    }

    public static List<RespuestaWSDispositivo> ListarDispositivosUsuario(String apiKey) throws OKOBusinessException {
        List<RespuestaWSDispositivo> objetoSalida = new ArrayList<>();
        UsuariosRecord usuario = GestionUsuarios.GetUserfromApiKey(apiKey);
        if (usuario == null) throw new OKOBusinessException(OKOErrorType.USER_NOT_FOUND);
        List<DispositivosvirtualesRecord> dispositivos = usuario.fetchChildren(Keys.FK_USUARIOSPROPIETARIOS);
        for (DispositivosvirtualesRecord disp : dispositivos) {
            if (disp.getEstado() == EstadoVinculacionType.Vinculado.toInt()) {
                objetoSalida.add(new RespuestaWSDispositivo(disp.getUuid(), disp.getNombre(), disp.getEstado(), disp.getUrlavatar(), RolUsuarioDispositivoType.Propietario.toInt(), disp.getTipoespejo()));
            }
        }
        return objetoSalida;
    }

    public static List<RespuestaWSDispositivo> ListarOKOsEnviar(String apiKey) throws OKOBusinessException {
        List<RespuestaWSDispositivo> objetoSalida = new ArrayList<>();
        UsuariosRecord usuario = GestionUsuarios.GetUserfromApiKey(apiKey);
        if (usuario == null) throw new OKOBusinessException(OKOErrorType.USER_NOT_FOUND);
        List<UsuariosdispositivosvirtualesRecord> usuariosDispositivos = usuario.fetchChildren(Keys.FK_USUARIOSDISPVIRTUALES);
        DispositivosvirtualesRecord disp;
        for (UsuariosdispositivosvirtualesRecord usuarioDisp : usuariosDispositivos) {
            disp = usuarioDisp.fetchParent(Keys.FK_DISPOSITIVOSVIRTUALES);
            objetoSalida.add(new RespuestaWSDispositivo(disp.getUuid(), disp.getNombre(), disp.getEstado(), disp.getUrlavatar(), usuarioDisp.getRol(), disp.getTipoespejo()));
        }
        return objetoSalida;
    }

    public static RespuestaWSFrameInfo GetFrame(String uuid) throws OKOBusinessException {
        DispositivosvirtualesRecord dispVirtual = ObtenerDispositivoVirtual(uuid);
        if (dispVirtual == null) throw new OKOBusinessException(OKOErrorType.INVALID_DEVICE);
        return new RespuestaWSFrameInfo(dispVirtual);
    }

    public static Boolean EliminarMarco(String uuid) throws OKOBusinessException {
        try {
            DispositivosvirtualesRecord dispVirtual = ObtenerDispositivoVirtual(uuid);
            if (dispVirtual == null) throw new OKOBusinessException(OKOErrorType.INVALID_DEVICE);
            //mirar si es tipoEspejo, si es tipo espejo quitar del espejo y dejarlo como esta si hay minimo 2 marcos sino desmontar espejo!
            if (dispVirtual.getTipoespejo() == DispositivoEspejoType.ESPEJO.toInt()) {
                // miramos cuantos mas hay en espejo
                DispositivosvirtualesRecord dispPadre = dispVirtual.fetchParent(Keys.FK_PADRE);
                if (dispPadre.fetchChildren(Keys.FK_PADRE).size() > 2) {
                    // AL borrar este quedaran mas de uno por lo tanto borramos este y ya esta
                    if (BorrarDispositivoEspejo(dispVirtual) == 1) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    // solo quedara uno al borrar con lo que hay que desmontar el espejo!
                    // Al dispositivo que queda se le copiara el CM del controlador y se borrara el controlador
                    // primero borramos el que tenemos
                    if (BorrarDispositivoEspejo(dispVirtual) == 1) {
                        //ya no tenemos nuestro dispositivo, ahora hay que cambiar el id del contenido virtual al id del espejo que queda
                        DispositivosvirtualesRecord dispEspejo = dispPadre.fetchChild(Keys.FK_PADRE);
                        List<ContenidosmultimediaRecord> contenidosMultimedia = dispPadre.fetchChildren(Keys.FK_CONTENIDOMULTIMEDIA);
                        for (ContenidosmultimediaRecord contenidoM : contenidosMultimedia) {
                            contenidoM.setIddispositivovirtual(dispEspejo.getIddispositivovirtual());
                            contenidoM.store();
                        }
                        dispEspejo.setTipoespejo(DispositivoEspejoType.NORMAL.toInt());
                        dispEspejo.store();
                        //borramos los usuarios del dispEspejo y le metemos los del controlador
                        List<UsuariosdispositivosvirtualesRecord> usuariosDispositivo = dispEspejo.fetchChildren(Keys.FK_DISPOSITIVOSVIRTUALES);
                        for (UsuariosdispositivosvirtualesRecord usuarioDispositivo : usuariosDispositivo) {
                            usuarioDispositivo.delete();
                        }
                        usuariosDispositivo = dispPadre.fetchChildren(Keys.FK_DISPOSITIVOSVIRTUALES);
                        for (UsuariosdispositivosvirtualesRecord usuarioDispositivo : usuariosDispositivo) {
                            usuarioDispositivo.setIddispositivovirtual(dispEspejo.getIddispositivovirtual());
                            usuarioDispositivo.store();
                        }
                        dispEspejo.setIdpropietario(dispPadre.getIdpropietario());
                        dispEspejo.setIdpadre(0);
                        dispEspejo.store();
                        if (dispPadre.delete() == 0) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
                return true;
            } else if (dispVirtual.getTipoespejo() == DispositivoEspejoType.NORMAL.toInt()) {
                // hay que borrar el dispVirtual, el CM, el estado CM, los usuarios y mirar si el CFisico tb.
                Class.forName(Constantes.DB_DRIVER).newInstance();
                Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
                DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
                List<ContenidosmultimediaRecord> contenidosMultimedia = dispVirtual.fetchChildren(Keys.FK_CONTENIDOMULTIMEDIA);
                for (ContenidosmultimediaRecord contenidoM : contenidosMultimedia) {
                    if (create.select().from(CONTENIDOSMULTIMEDIA)
                            .where(CONTENIDOSMULTIMEDIA.IDCONTENIDOFISICO.equal(contenidoM.getIdcontenidofisico()))
                            .fetchInto(ContenidosmultimediaRecord.class).size() == 1) {
                        //solo hay 1 borrarlo
                        ContenidosfisicosRecord contenidoFisico = contenidoM.fetchParent(Keys.FK_CONTENIDOFISICO);
                        GestionContenido.BorrarArchivo(contenidoFisico.getPathcontenidofisico(), contenidoFisico.getPaththumbnail());
                        if (contenidoFisico.delete() == 0) {
                            create.close();
                            conn.close();
                            return false;
                        }
                    }
                    EstadocontenidomultimediaRecord estadoCM = contenidoM.fetchChild(Keys.FK_CONTENIDOMULTIMEDIAESTADO);
                    if (estadoCM.delete() == 0 || contenidoM.delete() == 0) {
                        create.close();
                        conn.close();
                        return false;
                    }
                }
                //borramos los users
                List<UsuariosdispositivosvirtualesRecord> usuariosGrupo = dispVirtual.fetchChildren(Keys.FK_DISPOSITIVOSVIRTUALES);
                for (UsuariosdispositivosvirtualesRecord usuarioGrupo : usuariosGrupo) {
                    if (usuarioGrupo.delete() == 0) {
                        create.close();
                        conn.close();
                        return false;
                    }
                }
                //borramos el dispositivo virtual
                if (dispVirtual.delete() == 0) {
                    create.close();
                    conn.close();
                    return false;
                } else {
                    create.close();
                    conn.close();
                    return true;
                }
            } else {
                // si es controlador no se puede borrar
                return false;
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    private static RespuestaWS<RespuestaWSDispositivos> HandShake(UsuariosRecord usuario, DispositivosvirtualesRecord dispVirtual, RespuestaWSDispositivos estadoDispositivo) throws OKOBusinessException {
        Boolean salirOK = false;
        int NUM_TRY_MAX = 12;
        int i = 0;
        while (i < NUM_TRY_MAX && !salirOK) {
            try {
                dispVirtual.refresh();

                if (dispVirtual.getEstado() == EstadoVinculacionType.FinishOK_Frame.toInt()) {
                    salirOK = true;
                } else {
                    // Esperamos 2 sec
                    Thread.sleep(2000);
                }

                i++;
            } catch (InterruptedException e) {
                dispVirtual.setEstado(EstadoVinculacionType.DesvinculadoCompleted.toInt());
                dispVirtual.store();
            }
        }
        RespuestaWS<RespuestaWSDispositivos> response = new RespuestaWS<RespuestaWSDispositivos>();
        if (salirOK) {
            dispVirtual.setEstado(EstadoVinculacionType.Vinculado.toInt());
            dispVirtual.store();
            //vinculado ok buscamos la lista de disp q pueda restaurar o copiar /sincronizar
            // Listamos todos los virtuales del usuario menos el que acaba de crear.
            // todos los vinculados, no vinculados menos los que son espejo ya que solo se muestra el controlador
            List<DispositivosvirtualesRecord> dispositivosVirtuales = ListarDispositivosVirtualesMenosActual(usuario, dispVirtual);
            RespuestaWSDispositivo respuesta;
            for (DispositivosvirtualesRecord dispTemp : dispositivosVirtuales) {
                respuesta = new RespuestaWSDispositivo(dispTemp.getUuid(), dispTemp.getNombre(), dispTemp.getEstado(), dispTemp.getUrlavatar(), RolUsuarioDispositivoType.Propietario.toInt(), dispTemp.getTipoespejo());
                estadoDispositivo.ls.add(respuesta);
            }
            // Reepuesta OK
            response.responseStatus = 0;
            response.message = estadoDispositivo;
            response.error = "";
            return response;
        } else {

            dispVirtual.setEstado(EstadoVinculacionType.DesvinculadoCompleted.toInt());
            dispVirtual.store();
            response.responseStatus = -56;
            response.message = null;
            response.error = "TIME OUT";
            return response;
        }
    }

    public static Boolean HayPermisosParaEnviar(int usuarioID, String uuid) throws OKOBusinessException {

        Boolean permisoParaEnviar = false;
        // ¿Quien es el propietario del dispositivo
        DispositivosvirtualesRecord dispositivo = ObtenerDispositivoVirtual(uuid);
        if (dispositivo.getEstado() == EstadoVinculacionType.Vinculado.toInt()) {
            // ¿El propietario es el mismo que el llamador?
            if (dispositivo.getIdpropietario() == usuarioID) {
                // OK, entonces tiene permisos para ENVIAR
                permisoParaEnviar = true;
            } else {
                // NO...

                // ¿Es un MARCO privado?
                if (dispositivo.getVisibilidad() == VisibilidadDispositivoType.Privado.toInt()) {
                    // Marco PRIVADO, imposible enviar contenido
                    permisoParaEnviar = false;
                } else if (dispositivo.getVisibilidad() == VisibilidadDispositivoType.Grupo.toInt()) {
                    // ¿esta el usuario en el grupo de los que pueden enviar
                    permisoParaEnviar = EstaUsuarioGrupoMarco(dispositivo.getIddispositivovirtual(), usuarioID);
                } else if (dispositivo.getVisibilidad() == VisibilidadDispositivoType.Publico.toInt()) {
                    // Superpublico
                    permisoParaEnviar = true;
                } else if (dispositivo.getTipoespejo() == DispositivoEspejoType.NORMAL.toInt() || dispositivo.getTipoespejo() == DispositivoEspejoType.CONTROLADOR.toInt()) {
                    permisoParaEnviar = true;
                }
            }
        }
        return permisoParaEnviar;
    }

    public static DispositivosvirtualesRecord ObtenerDispositivoVirtual(String uuid) throws OKOBusinessException {
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            return create.select().from(DISPOSITIVOSVIRTUALES)
                    .where(DISPOSITIVOSVIRTUALES.UUID.equal(uuid))
                    .fetchAnyInto(DispositivosvirtualesRecord.class);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    private static List<DispositivosvirtualesRecord> ListarDispositivosVirtualesMenosActual(UsuariosRecord usuario, DispositivosvirtualesRecord dispVirtual) throws OKOBusinessException {
        // Listamos todos los virtuales del usuario menos el que acaba de crear.
        // todos los vinculados, no vinculados menos los que son espejo ya que solo se muestra el controlador
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            return create.select().from(DISPOSITIVOSVIRTUALES)
                    .where(DISPOSITIVOSVIRTUALES.IDPROPIETARIO.equal(usuario.getIdusuario()))
                    .and(DISPOSITIVOSVIRTUALES.IDDISPOSITIVOVIRTUAL.notEqual(dispVirtual.getIddispositivovirtual()))
                    .and(DISPOSITIVOSVIRTUALES.TIPOESPEJO.notEqual(DispositivoEspejoType.ESPEJO.toInt()))
                    .and(DISPOSITIVOSVIRTUALES.ESTADO.notEqual(EstadoVinculacionType.FinishOK_Frame.toInt()))
                    .and(DISPOSITIVOSVIRTUALES.ESTADO.notEqual(EstadoVinculacionType.Insert_APP.toInt()))
                    .fetchInto(DispositivosvirtualesRecord.class);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    private static RespuestaWSDispositivos VinculaDispositivoVirtual(UsuariosRecord usuario, DispositivosvirtualesRecord dispVirtual, DispositivosfisicosRecord dispFisico) throws OKOBusinessException {

        dispVirtual.setEstado(EstadoVinculacionType.Insert_APP.toInt());
        dispVirtual.setIdpropietario(usuario.getIdusuario());
        dispVirtual.setFechamodificacion(Fechas.GetCurrentTimestampLong());
        dispVirtual.setIddispositivofisico(dispFisico.getIddispositivosfisico());
        dispVirtual.store();

        //añadimos el propietario en la tabla de usuarios del dispositivo Virtual
        GestionUsuarios.CrearUsuarioPropietarioDispositivo(usuario, dispVirtual);

        //preparamos la respuesta
        RespuestaWSDispositivos estadoDispositivo = new RespuestaWSDispositivos();
        estadoDispositivo.uuid = dispVirtual.getUuid();
        estadoDispositivo.nombre = dispVirtual.getNombre();
        estadoDispositivo.estado = dispVirtual.getEstado();
        estadoDispositivo.visibilidad = dispVirtual.getVisibilidad();
        estadoDispositivo.urlAvatar = dispVirtual.getUrlavatar();
        return estadoDispositivo;
    }

    private static Boolean EstaUsuarioGrupoMarco(int dispositivoID, int usuarioID) throws OKOBusinessException {
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            UsuariosdispositivosvirtualesRecord usuarioGrupo = create.select().from(USUARIOSDISPOSITIVOSVIRTUALES)
                    .where(USUARIOSDISPOSITIVOSVIRTUALES.IDUSUARIO.equal(usuarioID))
                    .and(USUARIOSDISPOSITIVOSVIRTUALES.IDDISPOSITIVOVIRTUAL.equal(dispositivoID))
                    .fetchAnyInto(UsuariosdispositivosvirtualesRecord.class);
            return usuarioGrupo != null;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    private static int BorrarDispositivoEspejo(DispositivosvirtualesRecord dispEspejo) {
        //se borra este dispositivo del controlador, y se borra su estadoCM.
        List<EstadocontenidomultimediaRecord> estadosCM = dispEspejo.fetchChildren(Keys.FK_DISPOSITIVOVIRTUALESTADO);
        for (EstadocontenidomultimediaRecord estadoCM : estadosCM) {
            if (estadoCM.delete() == 0) return 0; //si no borra sal
        }

        //se borran los usarios del dispositivo (Grupo)
        List<UsuariosdispositivosvirtualesRecord> grupoUsuarios = dispEspejo.fetchChildren(Keys.FK_DISPOSITIVOSVIRTUALES);
        for (UsuariosdispositivosvirtualesRecord usuarioGrupo : grupoUsuarios) {
            if (usuarioGrupo.delete() == 0) return 0; //si no borra sal
        }
        return dispEspejo.delete();
    }

    private static Boolean CheckSerialNumber(String serialNumber) throws OKOBusinessException {
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            NumerosserieRecord numSerie = create.select().from(NUMEROSSERIE)
                    .where(NUMEROSSERIE.NUMEROSERIE.equal(serialNumber))
                    .and(NUMEROSSERIE.ACTIVO.equal(1))
                    .fetchAnyInto(NumerosserieRecord.class);
            return numSerie != null;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    public static RespuestaWS<RespuestaWSUpdate> CheckUpdates(String versionUpdater, String currentVersion, String versionCode, String serialNumber, int tipo) throws OKOBusinessException {
        RespuestaWS<RespuestaWSUpdate> response = new RespuestaWS<>();
        RespuestaWSUpdate respuestaUpdate = null;
        try {
            if (!GestionDispositivos.CheckSerialNumber(serialNumber))
                throw new OKOBusinessException(OKOErrorType.INVALID_SERIAL);
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            List<ActualizacionesRecord> updates = create.select().from(ACTUALIZACIONES)
                    .where(ACTUALIZACIONES.VERSIONNAMEUPDATER.equal(versionUpdater))
                    .and(ACTUALIZACIONES.VERSIONNAME.equal(currentVersion))
                    .and(ACTUALIZACIONES.VERSIONCODE.greaterThan(Integer.parseInt(versionCode)))
                    .and(ACTUALIZACIONES.TIPO.equal(tipo))
                    .and(ACTUALIZACIONES.PERMITIRACTUALIZAR.equal(1))
                    .orderBy(ACTUALIZACIONES.IDACTUALIZACION.desc())
                    .fetchInto(ActualizacionesRecord.class);
            if (updates != null) {
                if (updates.size() > 0) {
                    ActualizacionesRecord update = updates.get(0);
                    respuestaUpdate = new RespuestaWSUpdate();
                    respuestaUpdate.md5hash = update.getMd5hash();
                    respuestaUpdate.canUpdate = update.getPermitiractualizar();
                    respuestaUpdate.changeLog = update.getChangelog();
                    respuestaUpdate.idUpdate = update.getIdactualizacion();
                }
            }
            if (respuestaUpdate == null) throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
            response.responseStatus = 0;
            response.message = respuestaUpdate;
            response.error = "";
            return response;
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException ex) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    public static String ObtenerPathDescargaTipo(String md5, int tipo) throws OKOBusinessException {

        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            ActualizacionesRecord actualizacon = create.select().from(ACTUALIZACIONES)
                    .where(ACTUALIZACIONES.MD5HASH.equal(md5))
                    .and(ACTUALIZACIONES.TIPO.equal(tipo))
                    .fetchAnyInto(ActualizacionesRecord.class);
            if (actualizacon == null) throw new OKOBusinessException(OKOErrorType.FORBIDDEN);
            return actualizacon.getPathupdate();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    private static Boolean ConfigurarEspejo(String uuidOrigen, String uuidDestino) throws OKOBusinessException {
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            DispositivosvirtualesRecord dispVirtualOrigen = ObtenerDispositivoVirtual(uuidOrigen);
            DispositivosvirtualesRecord dispVirtualDest = ObtenerDispositivoVirtual(uuidDestino);
            if ((dispVirtualOrigen == null) || (dispVirtualDest == null))
                throw new OKOBusinessException(OKOErrorType.INVALID_DEVICE);

            dispVirtualDest.setTipoespejo(DispositivoEspejoType.ESPEJO.toInt());
            //miramos si ya era espejo o controlador
            if (dispVirtualOrigen.getTipoespejo() == DispositivoEspejoType.ESPEJO.toInt() || dispVirtualOrigen.getTipoespejo() == DispositivoEspejoType.CONTROLADOR.toInt()) {
                //añadimos el futuro como otro mas en la lista de espejo
                if (dispVirtualOrigen.getTipoespejo() == DispositivoEspejoType.ESPEJO.toInt())
                    //es otro espejo busco el controlador
                    dispVirtualOrigen = dispVirtualOrigen.fetchParent(Keys.FK_PADRE);

                dispVirtualDest.setIdpadre(dispVirtualOrigen.getIddispositivovirtual());
                //listamos todos sus CM para añadir los estados de nuestro dispositivo.
                List<ContenidosmultimediaRecord> contenidos = create.select()
                        .from(CONTENIDOSMULTIMEDIA)
                        .innerJoin(ESTADOCONTENIDOMULTIMEDIA)
                        .on(CONTENIDOSMULTIMEDIA.IDCONTENIDOMULTIMEDIA.equal(ESTADOCONTENIDOMULTIMEDIA.IDCONTENIDOMULTIMEDIA))
                        .where(CONTENIDOSMULTIMEDIA.IDDISPOSITIVOVIRTUAL.equal(dispVirtualOrigen.getIddispositivovirtual()))
                        .and(ESTADOCONTENIDOMULTIMEDIA.ESTADO.equal(EstadoContenidoMultimediaType.Push_Sent_To_Device.toInt()))
                        .fetchInto(ContenidosmultimediaRecord.class);
                EstadocontenidomultimediaRecord estado;
                for (ContenidosmultimediaRecord contemp : contenidos) {
                    estado = create.newRecord(ESTADOCONTENIDOMULTIMEDIA);
                    estado.setEstado(EstadoContenidoMultimediaType.Push_Sent_To_Device.toInt());
                    estado.setFeedback(0);
                    estado.setIdcontenidomultimedia(contemp.getIdcontenidomultimedia());
                    estado.setIddispositivovirtual(dispVirtualDest.getIddispositivovirtual());
                    estado.store();
                }
            } else {
                //creamos el controlador y movemos el CM a el
                DispositivosvirtualesRecord dispVirtualControlador = create.newRecord(DISPOSITIVOSVIRTUALES);
                CopiarDispositivoVirtual(dispVirtualOrigen, dispVirtualControlador);
                dispVirtualControlador.setIdpropietario(dispVirtualOrigen.getIdpropietario());
                dispVirtualControlador.setTipoespejo(DispositivoEspejoType.CONTROLADOR.toInt());
                dispVirtualControlador.store();
                dispVirtualOrigen.setIdpadre(dispVirtualControlador.getIddispositivovirtual());
                dispVirtualOrigen.setTipoespejo(DispositivoEspejoType.ESPEJO.toInt());
                dispVirtualOrigen.store();
                //actualizamos la clase UsuariosDispositivosVirtuales
                List<UsuariosdispositivosvirtualesRecord> usuarios = dispVirtualOrigen.fetchChildren(Keys.FK_DISPOSITIVOSVIRTUALES);
                for (UsuariosdispositivosvirtualesRecord usuario : usuarios) {
                    usuario.setIddispositivovirtual(dispVirtualControlador.getIddispositivovirtual());
                    usuario.store();
                }
                //Añadimos el nuevo al espejo
                dispVirtualDest.setIdpadre(dispVirtualControlador.getIddispositivovirtual());
                dispVirtualDest.setTipoespejo(DispositivoEspejoType.ESPEJO.toInt());
                dispVirtualDest.store();

                //actualizamos el CM y creamos el estadoCM
                List<ContenidosmultimediaRecord> contenidos = dispVirtualOrigen.fetchChildren(Keys.FK_CONTENIDOMULTIMEDIA);
                EstadocontenidomultimediaRecord estado;
                for (ContenidosmultimediaRecord contenido : contenidos) {
                    contenido.setIddispositivovirtual(dispVirtualControlador.getIddispositivovirtual());
                    contenido.store();
                    estado = create.newRecord(ESTADOCONTENIDOMULTIMEDIA);
                    estado.setEstado(EstadoContenidoMultimediaType.Push_Sent_To_Device.toInt());
                    estado.setFeedback(0);
                    estado.setIdcontenidomultimedia(contenido.getIdcontenidomultimedia());
                    estado.setIddispositivovirtual(dispVirtualDest.getIddispositivovirtual());
                    estado.store();
                }
            }
            return true;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    private static void CopiarDispositivoVirtual(DispositivosvirtualesRecord dispVirtualOrigen, DispositivosvirtualesRecord dispVirtualDest) {
        dispVirtualDest.setNombre(dispVirtualOrigen.getNombre());
        dispVirtualDest.setUuid(UUID.randomUUID().toString());
        dispVirtualDest.setVisibilidad(dispVirtualOrigen.getVisibilidad());
        dispVirtualDest.setEstado(dispVirtualOrigen.getEstado());
        dispVirtualDest.setControlcontenido(dispVirtualOrigen.getControlcontenido());
        dispVirtualDest.setUrlavatar(dispVirtualOrigen.getUrlavatar());
        dispVirtualDest.setLink(dispVirtualOrigen.getLink());
        dispVirtualDest.setFechainsert(Fechas.GetCurrentTimestampLong());
        dispVirtualDest.setFechamodificacion(Fechas.GetCurrentTimestampLong());
    }

    public static String EnviarAlerta(String apiKey, String uuid, String mensaje) throws OKOBusinessException {
        try {
            Date fecha = new Date();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String stringFecha = df.format(fecha);
            Gson gson = new Gson();
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            UsuariosRecord usuario = GestionUsuarios.GetUserfromApiKey(apiKey);
            if (usuario == null) throw new OKOBusinessException(OKOErrorType.USER_NOT_FOUND);
            if (!GestionDispositivos.HayPermisosParaEnviar(usuario.getIdusuario(), uuid))
                throw new OKOBusinessException(OKOErrorType.NOT_ALLOWED);
            DispositivosvirtualesRecord dispVirtual = GestionDispositivos.ObtenerDispositivoVirtual(uuid);
            if (dispVirtual == null) throw new OKOBusinessException(OKOErrorType.INVALID_DEVICE);
            ContenidosmultimediaRecord contenidomultimedia = create.newRecord(Tables.CONTENIDOSMULTIMEDIA);
            contenidomultimedia.setIdusuarioemisor(usuario.getIdusuario());
            contenidomultimedia.setIddispositivovirtual(dispVirtual.getIddispositivovirtual());
            contenidomultimedia.setTipo(ContenidoMultimediaType.Alert.toInt());
            contenidomultimedia.setTexto(mensaje);
            contenidomultimedia.setFechainsert(Fechas.GetCurrentTimestampLong());
            contenidomultimedia.store();
            MensajesFactory mensajeAlerta = new MensajesFactory();
            MensajeTipoWrapper<MensajeAlerta> alerta = mensajeAlerta.CrearAlerta(contenidomultimedia.getIdcontenidomultimedia().toString(), usuario.getIdusuario().toString(), usuario.getUrlavatar(), usuario.getNombre(), mensaje, stringFecha);
            INotificacionesPush notificacionPush = NotificacionesPushFactory.Crear(NotificacionesPushFactoryType.ANDROID_PUSH_FRAME);
            if (dispVirtual.getTipoespejo() == DispositivoEspejoType.NORMAL.toInt()) {
                EstadocontenidomultimediaRecord estado = GestionContenido.CreaEstadoCM(contenidomultimedia.getIdcontenidomultimedia(), dispVirtual.getIddispositivovirtual());
                notificacionPush.EnviarPush(dispVirtual.fetchParent(Keys.FK_DISPOSITIVOFISICO).getToken(), gson.toJson(alerta), contenidomultimedia.getIdcontenidomultimedia(), estado.getIdestadocontenidomultimedia());

            } else if (dispVirtual.getTipoespejo() == DispositivoEspejoType.CONTROLADOR.toInt()) {
                List<DispositivosvirtualesRecord> dispositivosEspejos = dispVirtual.fetchChildren(Keys.FK_PADRE);
                EstadocontenidomultimediaRecord estado;
                for (DispositivosvirtualesRecord dispEspejo : dispositivosEspejos) {
                    estado = GestionContenido.CreaEstadoCM(contenidomultimedia.getIdcontenidomultimedia(), dispEspejo.getIddispositivovirtual());
                    notificacionPush.EnviarPush(dispEspejo.fetchParent(Keys.FK_DISPOSITIVOFISICO).getToken(), gson.toJson(alerta), contenidomultimedia.getIdcontenidomultimedia(), estado.getIdestadocontenidomultimedia());
                }
            }
            return "OK";
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException ex) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    public static RespuestaWSFEstaDispositivoRegistrado EstaDispositivoRegistrado(String macAddress, String nombreMarco, int visibilidad, int localizacionID) throws OKOBusinessException {
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            DispositivosfisicosRecord dispFisico = create.select()
                    .from(DISPOSITIVOSFISICOS)
                    .innerJoin(NUMEROSSERIE)
                    .on(DISPOSITIVOSFISICOS.IDNUMEROSERIE.equal(NUMEROSSERIE.IDNUMEROSERIE))
                    .where(NUMEROSSERIE.NUMEROSERIE.equal(macAddress))
                    .fetchAnyInto(DispositivosfisicosRecord.class);
            if (dispFisico == null) throw new OKOBusinessException(OKOErrorType.NO_TOKEN);
            DispositivosvirtualesRecord dispVirtual = dispFisico.fetchChild(Keys.FK_DISPOSITIVOFISICO);
            if (dispVirtual == null) throw new OKOBusinessException(OKOErrorType.INVALID_DEVICE);
            if (dispVirtual.getEstado() != EstadoVinculacionType.Insert_APP.toInt())
                throw new OKOBusinessException(OKOErrorType.NOT_LINKED);
            dispVirtual.setNombre(nombreMarco);
            dispVirtual.setVisibilidad(visibilidad);
            dispVirtual.setEstado(EstadoVinculacionType.FinishOK_Frame.toInt());
            dispVirtual.store();
            UsuariosRecord usuario = dispVirtual.fetchParent(Keys.FK_USUARIOSPROPIETARIOS);
            if (usuario == null) throw new OKOBusinessException(OKOErrorType.USER_NOT_FOUND);
            RespuestaWSFEstaDispositivoRegistrado respuestaWSFEstaDispositivoRegistrado = new RespuestaWSFEstaDispositivoRegistrado();
            respuestaWSFEstaDispositivoRegistrado.uuid = dispVirtual.getUuid();
            respuestaWSFEstaDispositivoRegistrado.apiKey = usuario.getApikey();
            respuestaWSFEstaDispositivoRegistrado.fullname = usuario.getNombre();
            respuestaWSFEstaDispositivoRegistrado.username = usuario.getEmail();
            respuestaWSFEstaDispositivoRegistrado.avatarUrl = usuario.getUrlavatar();
            respuestaWSFEstaDispositivoRegistrado.nombreMarco = dispVirtual.getNombre();
            respuestaWSFEstaDispositivoRegistrado.contenidos = 0;
            create.close();
            conn.close();
            return respuestaWSFEstaDispositivoRegistrado;
        } catch (InstantiationException | SQLException | IllegalAccessException | ClassNotFoundException ex) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }

    public static void RegistrarMarco(String macAddress, String token, String version) throws OKOBusinessException {
        try {
            Class.forName(Constantes.DB_DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS);
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            if (!CheckSerialNumber(macAddress)) throw new OKOBusinessException(OKOErrorType.INVALID_SERIAL);
            DispositivosfisicosRecord dispFisico = create.select()
                    .from(DISPOSITIVOSFISICOS)
                    .innerJoin(NUMEROSSERIE)
                    .on(DISPOSITIVOSFISICOS.IDNUMEROSERIE.equal(NUMEROSSERIE.IDNUMEROSERIE))
                    .where(NUMEROSSERIE.NUMEROSERIE.equal(macAddress))
                    .and(DISPOSITIVOSFISICOS.TOKEN.notEqual(token))
                    .fetchAnyInto(DispositivosfisicosRecord.class);
            if (dispFisico != null) {
                //guardamos el token
                dispFisico.setToken(token);
                dispFisico.setVersion(version);
                dispFisico.setFechamodificacion(Fechas.GetCurrentTimestampLong());
                dispFisico.store();
            } else {
                //no existe: lo creamos
                NumerosserieRecord numserie = create.select()
                        .from(NUMEROSSERIE)
                        .where(NUMEROSSERIE.NUMEROSERIE.equal(macAddress))
                        .fetchAnyInto(NumerosserieRecord.class);
                if (numserie == null) throw new OKOBusinessException(OKOErrorType.INVALID_SERIAL);
                dispFisico = create.newRecord(DISPOSITIVOSFISICOS);
                dispFisico.setToken(token);
                dispFisico.setVersion(version);
                dispFisico.setIdnumeroserie(numserie.getIdnumeroserie());
                dispFisico.setFechainsert(Fechas.GetCurrentTimestampLong());
                dispFisico.setFechamodificacion(Fechas.GetCurrentTimestampLong());
                dispFisico.setIdempresa(numserie.getIdempresa());
                dispFisico.store();
                dispFisico.refresh();
            }
        } catch (InstantiationException | SQLException | IllegalAccessException | ClassNotFoundException ex) {
            throw new OKOBusinessException(OKOErrorType.GENERIC_ERROR);
        }
    }
}
