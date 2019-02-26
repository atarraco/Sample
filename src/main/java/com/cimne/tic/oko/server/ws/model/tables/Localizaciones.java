/*
 * This file is generated by jOOQ.
*/
package com.cimne.tic.oko.server.ws.model.tables;


import com.cimne.tic.oko.server.ws.model.Keys;
import com.cimne.tic.oko.server.ws.model.Oko;
import com.cimne.tic.oko.server.ws.model.tables.records.LocalizacionesRecord;

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
public class Localizaciones extends TableImpl<LocalizacionesRecord> {

    private static final long serialVersionUID = 331533154;

    /**
     * The reference instance of <code>oko.Localizaciones</code>
     */
    public static final Localizaciones LOCALIZACIONES = new Localizaciones();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<LocalizacionesRecord> getRecordType() {
        return LocalizacionesRecord.class;
    }

    /**
     * The column <code>oko.Localizaciones.idLocalizacion</code>.
     */
    public final TableField<LocalizacionesRecord, Integer> IDLOCALIZACION = createField("idLocalizacion", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>oko.Localizaciones.nombre</code>.
     */
    public final TableField<LocalizacionesRecord, String> NOMBRE = createField("nombre", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

    /**
     * The column <code>oko.Localizaciones.telefono</code>.
     */
    public final TableField<LocalizacionesRecord, String> TELEFONO = createField("telefono", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

    /**
     * The column <code>oko.Localizaciones.email</code>.
     */
    public final TableField<LocalizacionesRecord, String> EMAIL = createField("email", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

    /**
     * The column <code>oko.Localizaciones.idEmpresa</code>.
     */
    public final TableField<LocalizacionesRecord, Integer> IDEMPRESA = createField("idEmpresa", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>oko.Localizaciones</code> table reference
     */
    public Localizaciones() {
        this("Localizaciones", null);
    }

    /**
     * Create an aliased <code>oko.Localizaciones</code> table reference
     */
    public Localizaciones(String alias) {
        this(alias, LOCALIZACIONES);
    }

    private Localizaciones(String alias, Table<LocalizacionesRecord> aliased) {
        this(alias, aliased, null);
    }

    private Localizaciones(String alias, Table<LocalizacionesRecord> aliased, Field<?>[] parameters) {
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
    public Identity<LocalizacionesRecord, Integer> getIdentity() {
        return Keys.IDENTITY_LOCALIZACIONES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<LocalizacionesRecord> getPrimaryKey() {
        return Keys.KEY_LOCALIZACIONES_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<LocalizacionesRecord>> getKeys() {
        return Arrays.<UniqueKey<LocalizacionesRecord>>asList(Keys.KEY_LOCALIZACIONES_PRIMARY, Keys.KEY_LOCALIZACIONES_IDLOCALIZACION_UNIQUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<LocalizacionesRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<LocalizacionesRecord, ?>>asList(Keys.FK_EMPRESALOC);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Localizaciones as(String alias) {
        return new Localizaciones(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Localizaciones rename(String name) {
        return new Localizaciones(name, null);
    }
}