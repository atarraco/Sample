/*
 * This file is generated by jOOQ.
*/
package com.cimne.tic.oko.server.ws.model.tables;


import com.cimne.tic.oko.server.ws.model.Keys;
import com.cimne.tic.oko.server.ws.model.Oko;
import com.cimne.tic.oko.server.ws.model.tables.records.UsuariosadminlocalizacionesRecord;

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
public class Usuariosadminlocalizaciones extends TableImpl<UsuariosadminlocalizacionesRecord> {

    private static final long serialVersionUID = 1947787401;

    /**
     * The reference instance of <code>oko.UsuariosAdminLocalizaciones</code>
     */
    public static final Usuariosadminlocalizaciones USUARIOSADMINLOCALIZACIONES = new Usuariosadminlocalizaciones();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UsuariosadminlocalizacionesRecord> getRecordType() {
        return UsuariosadminlocalizacionesRecord.class;
    }

    /**
     * The column <code>oko.UsuariosAdminLocalizaciones.idUsuarioAdminLocalizacion</code>.
     */
    public final TableField<UsuariosadminlocalizacionesRecord, Integer> IDUSUARIOADMINLOCALIZACION = createField("idUsuarioAdminLocalizacion", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>oko.UsuariosAdminLocalizaciones.idUsuarioAdmin</code>.
     */
    public final TableField<UsuariosadminlocalizacionesRecord, Integer> IDUSUARIOADMIN = createField("idUsuarioAdmin", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>oko.UsuariosAdminLocalizaciones.idLocalizacion</code>.
     */
    public final TableField<UsuariosadminlocalizacionesRecord, Integer> IDLOCALIZACION = createField("idLocalizacion", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>oko.UsuariosAdminLocalizaciones</code> table reference
     */
    public Usuariosadminlocalizaciones() {
        this("UsuariosAdminLocalizaciones", null);
    }

    /**
     * Create an aliased <code>oko.UsuariosAdminLocalizaciones</code> table reference
     */
    public Usuariosadminlocalizaciones(String alias) {
        this(alias, USUARIOSADMINLOCALIZACIONES);
    }

    private Usuariosadminlocalizaciones(String alias, Table<UsuariosadminlocalizacionesRecord> aliased) {
        this(alias, aliased, null);
    }

    private Usuariosadminlocalizaciones(String alias, Table<UsuariosadminlocalizacionesRecord> aliased, Field<?>[] parameters) {
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
    public Identity<UsuariosadminlocalizacionesRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USUARIOSADMINLOCALIZACIONES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UsuariosadminlocalizacionesRecord> getPrimaryKey() {
        return Keys.KEY_USUARIOSADMINLOCALIZACIONES_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UsuariosadminlocalizacionesRecord>> getKeys() {
        return Arrays.<UniqueKey<UsuariosadminlocalizacionesRecord>>asList(Keys.KEY_USUARIOSADMINLOCALIZACIONES_PRIMARY, Keys.KEY_USUARIOSADMINLOCALIZACIONES_IDUSUARIOADMINLOCALIZACION_UNIQUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<UsuariosadminlocalizacionesRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<UsuariosadminlocalizacionesRecord, ?>>asList(Keys.FK_USUARIOSADMINLOCALIZACIONES, Keys.FK_LOCALIZACIONESUSUARIOSADMIN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Usuariosadminlocalizaciones as(String alias) {
        return new Usuariosadminlocalizaciones(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Usuariosadminlocalizaciones rename(String name) {
        return new Usuariosadminlocalizaciones(name, null);
    }
}
