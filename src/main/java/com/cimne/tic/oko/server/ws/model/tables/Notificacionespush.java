/*
 * This file is generated by jOOQ.
*/
package com.cimne.tic.oko.server.ws.model.tables;


import com.cimne.tic.oko.server.ws.model.Keys;
import com.cimne.tic.oko.server.ws.model.Oko;
import com.cimne.tic.oko.server.ws.model.tables.records.NotificacionespushRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Notificacionespush extends TableImpl<NotificacionespushRecord> {

    private static final long serialVersionUID = -1622284537;

    /**
     * The reference instance of <code>oko.NotificacionesPush</code>
     */
    public static final Notificacionespush NOTIFICACIONESPUSH = new Notificacionespush();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<NotificacionespushRecord> getRecordType() {
        return NotificacionespushRecord.class;
    }

    /**
     * The column <code>oko.NotificacionesPush.idNotificacionPush</code>.
     */
    public final TableField<NotificacionespushRecord, Integer> IDNOTIFICACIONPUSH = createField("idNotificacionPush", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>oko.NotificacionesPush.token</code>.
     */
    public final TableField<NotificacionespushRecord, String> TOKEN = createField("token", org.jooq.impl.SQLDataType.VARCHAR.length(500).nullable(false), this, "");

    /**
     * The column <code>oko.NotificacionesPush.mensaje</code>.
     */
    public final TableField<NotificacionespushRecord, String> MENSAJE = createField("mensaje", org.jooq.impl.SQLDataType.VARCHAR.length(500).nullable(false), this, "");

    /**
     * The column <code>oko.NotificacionesPush.idMensaje</code>.
     */
    public final TableField<NotificacionespushRecord, String> IDMENSAJE = createField("idMensaje", org.jooq.impl.SQLDataType.VARCHAR.length(500).nullable(false), this, "");

    /**
     * The column <code>oko.NotificacionesPush.tipoDispositivo</code>.
     */
    public final TableField<NotificacionespushRecord, Integer> TIPODISPOSITIVO = createField("tipoDispositivo", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>oko.NotificacionesPush.plataforma</code>.
     */
    public final TableField<NotificacionespushRecord, Integer> PLATAFORMA = createField("plataforma", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>oko.NotificacionesPush.estado</code>.
     */
    public final TableField<NotificacionespushRecord, Integer> ESTADO = createField("estado", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>oko.NotificacionesPush.fechaInsercion</code>.
     */
    public final TableField<NotificacionespushRecord, Timestamp> FECHAINSERCION = createField("fechaInsercion", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>oko.NotificacionesPush.fechaEjecucion</code>.
     */
    public final TableField<NotificacionespushRecord, Timestamp> FECHAEJECUCION = createField("fechaEjecucion", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>oko.NotificacionesPush.fechaLanzamiento</code>.
     */
    public final TableField<NotificacionespushRecord, Timestamp> FECHALANZAMIENTO = createField("fechaLanzamiento", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>oko.NotificacionesPush</code> table reference
     */
    public Notificacionespush() {
        this("NotificacionesPush", null);
    }

    /**
     * Create an aliased <code>oko.NotificacionesPush</code> table reference
     */
    public Notificacionespush(String alias) {
        this(alias, NOTIFICACIONESPUSH);
    }

    private Notificacionespush(String alias, Table<NotificacionespushRecord> aliased) {
        this(alias, aliased, null);
    }

    private Notificacionespush(String alias, Table<NotificacionespushRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Oko.OKO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<NotificacionespushRecord, Integer> getIdentity() {
        return Keys.IDENTITY_NOTIFICACIONESPUSH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<NotificacionespushRecord> getPrimaryKey() {
        return Keys.KEY_NOTIFICACIONESPUSH_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<NotificacionespushRecord>> getKeys() {
        return Arrays.<UniqueKey<NotificacionespushRecord>>asList(Keys.KEY_NOTIFICACIONESPUSH_PRIMARY, Keys.KEY_NOTIFICACIONESPUSH_IDNOTIFICACIONPUSH_UNIQUE, Keys.KEY_NOTIFICACIONESPUSH_IDMENSAJE_UNIQUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Notificacionespush as(String alias) {
        return new Notificacionespush(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Notificacionespush rename(String name) {
        return new Notificacionespush(name, null);
    }
}