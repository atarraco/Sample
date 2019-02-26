/*
 * This file is generated by jOOQ.
*/
package com.cimne.tic.oko.server.ws.model.tables;


import com.cimne.tic.oko.server.ws.model.Keys;
import com.cimne.tic.oko.server.ws.model.Oko;
import com.cimne.tic.oko.server.ws.model.tables.records.DispositivosvirtualesRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
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
public class Dispositivosvirtuales extends TableImpl<DispositivosvirtualesRecord> {

    private static final long serialVersionUID = 1322003329;

    /**
     * The reference instance of <code>oko.DispositivosVirtuales</code>
     */
    public static final Dispositivosvirtuales DISPOSITIVOSVIRTUALES = new Dispositivosvirtuales();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DispositivosvirtualesRecord> getRecordType() {
        return DispositivosvirtualesRecord.class;
    }

    /**
     * The column <code>oko.DispositivosVirtuales.idDispositivoVirtual</code>.
     */
    public final TableField<DispositivosvirtualesRecord, Integer> IDDISPOSITIVOVIRTUAL = createField("idDispositivoVirtual", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>oko.DispositivosVirtuales.nombre</code>.
     */
    public final TableField<DispositivosvirtualesRecord, String> NOMBRE = createField("nombre", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * The column <code>oko.DispositivosVirtuales.uuid</code>.
     */
    public final TableField<DispositivosvirtualesRecord, String> UUID = createField("uuid", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * The column <code>oko.DispositivosVirtuales.visibilidad</code>.
     */
    public final TableField<DispositivosvirtualesRecord, Integer> VISIBILIDAD = createField("visibilidad", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>oko.DispositivosVirtuales.estado</code>.
     */
    public final TableField<DispositivosvirtualesRecord, Integer> ESTADO = createField("estado", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>oko.DispositivosVirtuales.controlContenido</code>.
     */
    public final TableField<DispositivosvirtualesRecord, Integer> CONTROLCONTENIDO = createField("controlContenido", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>oko.DispositivosVirtuales.urlAvatar</code>.
     */
    public final TableField<DispositivosvirtualesRecord, String> URLAVATAR = createField("urlAvatar", org.jooq.impl.SQLDataType.VARCHAR.length(500), this, "");

    /**
     * The column <code>oko.DispositivosVirtuales.link</code>.
     */
    public final TableField<DispositivosvirtualesRecord, String> LINK = createField("link", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

    /**
     * The column <code>oko.DispositivosVirtuales.fechaInsert</code>.
     */
    public final TableField<DispositivosvirtualesRecord, Timestamp> FECHAINSERT = createField("fechaInsert", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>oko.DispositivosVirtuales.fechaModificacion</code>.
     */
    public final TableField<DispositivosvirtualesRecord, Timestamp> FECHAMODIFICACION = createField("fechaModificacion", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>oko.DispositivosVirtuales.idDispositivoFisico</code>.
     */
    public final TableField<DispositivosvirtualesRecord, Integer> IDDISPOSITIVOFISICO = createField("idDispositivoFisico", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>oko.DispositivosVirtuales.idPropietario</code>.
     */
    public final TableField<DispositivosvirtualesRecord, Integer> IDPROPIETARIO = createField("idPropietario", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>oko.DispositivosVirtuales.tipoEspejo</code>.
     */
    public final TableField<DispositivosvirtualesRecord, Integer> TIPOESPEJO = createField("tipoEspejo", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>oko.DispositivosVirtuales.idPadre</code>.
     */
    public final TableField<DispositivosvirtualesRecord, Integer> IDPADRE = createField("idPadre", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * Create a <code>oko.DispositivosVirtuales</code> table reference
     */
    public Dispositivosvirtuales() {
        this("DispositivosVirtuales", null);
    }

    /**
     * Create an aliased <code>oko.DispositivosVirtuales</code> table reference
     */
    public Dispositivosvirtuales(String alias) {
        this(alias, DISPOSITIVOSVIRTUALES);
    }

    private Dispositivosvirtuales(String alias, Table<DispositivosvirtualesRecord> aliased) {
        this(alias, aliased, null);
    }

    private Dispositivosvirtuales(String alias, Table<DispositivosvirtualesRecord> aliased, Field<?>[] parameters) {
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
    public Identity<DispositivosvirtualesRecord, Integer> getIdentity() {
        return Keys.IDENTITY_DISPOSITIVOSVIRTUALES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<DispositivosvirtualesRecord> getPrimaryKey() {
        return Keys.KEY_DISPOSITIVOSVIRTUALES_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<DispositivosvirtualesRecord>> getKeys() {
        return Arrays.<UniqueKey<DispositivosvirtualesRecord>>asList(Keys.KEY_DISPOSITIVOSVIRTUALES_PRIMARY, Keys.KEY_DISPOSITIVOSVIRTUALES_IDDISPOSITIVOSVIRTUAL_UNIQUE, Keys.KEY_DISPOSITIVOSVIRTUALES_UUID_UNIQUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<DispositivosvirtualesRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<DispositivosvirtualesRecord, ?>>asList(Keys.FK_DISPOSITIVOFISICO, Keys.FK_USUARIOSPROPIETARIOS, Keys.FK_PADRE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dispositivosvirtuales as(String alias) {
        return new Dispositivosvirtuales(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Dispositivosvirtuales rename(String name) {
        return new Dispositivosvirtuales(name, null);
    }
}
