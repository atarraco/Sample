/*
 * This file is generated by jOOQ.
*/
package com.cimne.tic.oko.server.ws.model.tables;


import com.cimne.tic.oko.server.ws.model.Keys;
import com.cimne.tic.oko.server.ws.model.Oko;
import com.cimne.tic.oko.server.ws.model.tables.records.CanalesRecord;

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
public class Canales extends TableImpl<CanalesRecord> {

    private static final long serialVersionUID = -899381068;

    /**
     * The reference instance of <code>oko.Canales</code>
     */
    public static final Canales CANALES = new Canales();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CanalesRecord> getRecordType() {
        return CanalesRecord.class;
    }

    /**
     * The column <code>oko.Canales.idcanal</code>.
     */
    public final TableField<CanalesRecord, Integer> IDCANAL = createField("idcanal", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>oko.Canales.nombre_ES</code>.
     */
    public final TableField<CanalesRecord, String> NOMBRE_ES = createField("nombre_ES", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * The column <code>oko.Canales.nombre_DE</code>.
     */
    public final TableField<CanalesRecord, String> NOMBRE_DE = createField("nombre_DE", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * The column <code>oko.Canales.nombre_EN</code>.
     */
    public final TableField<CanalesRecord, String> NOMBRE_EN = createField("nombre_EN", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * The column <code>oko.Canales.nombre_FR</code>.
     */
    public final TableField<CanalesRecord, String> NOMBRE_FR = createField("nombre_FR", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * The column <code>oko.Canales.nombre_IT</code>.
     */
    public final TableField<CanalesRecord, String> NOMBRE_IT = createField("nombre_IT", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * The column <code>oko.Canales.thumbnail</code>.
     */
    public final TableField<CanalesRecord, String> THUMBNAIL = createField("thumbnail", org.jooq.impl.SQLDataType.VARCHAR.length(500).nullable(false), this, "");

    /**
     * The column <code>oko.Canales.available</code>.
     */
    public final TableField<CanalesRecord, Integer> AVAILABLE = createField("available", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>oko.Canales.idLocalizacion</code>.
     */
    public final TableField<CanalesRecord, Integer> IDLOCALIZACION = createField("idLocalizacion", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>oko.Canales.idEmpresa</code>.
     */
    public final TableField<CanalesRecord, Integer> IDEMPRESA = createField("idEmpresa", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * Create a <code>oko.Canales</code> table reference
     */
    public Canales() {
        this("Canales", null);
    }

    /**
     * Create an aliased <code>oko.Canales</code> table reference
     */
    public Canales(String alias) {
        this(alias, CANALES);
    }

    private Canales(String alias, Table<CanalesRecord> aliased) {
        this(alias, aliased, null);
    }

    private Canales(String alias, Table<CanalesRecord> aliased, Field<?>[] parameters) {
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
    public Identity<CanalesRecord, Integer> getIdentity() {
        return Keys.IDENTITY_CANALES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CanalesRecord> getPrimaryKey() {
        return Keys.KEY_CANALES_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CanalesRecord>> getKeys() {
        return Arrays.<UniqueKey<CanalesRecord>>asList(Keys.KEY_CANALES_PRIMARY, Keys.KEY_CANALES_IDCANAL_UNIQUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<CanalesRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<CanalesRecord, ?>>asList(Keys.FK_LOCALIZACIONCANALES, Keys.FK_EMPRESACANALES);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Canales as(String alias) {
        return new Canales(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Canales rename(String name) {
        return new Canales(name, null);
    }
}