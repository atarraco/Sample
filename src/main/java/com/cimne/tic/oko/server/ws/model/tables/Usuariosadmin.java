/*
 * This file is generated by jOOQ.
*/
package com.cimne.tic.oko.server.ws.model.tables;


import com.cimne.tic.oko.server.ws.model.Keys;
import com.cimne.tic.oko.server.ws.model.Oko;
import com.cimne.tic.oko.server.ws.model.tables.records.UsuariosadminRecord;

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
public class Usuariosadmin extends TableImpl<UsuariosadminRecord> {

    private static final long serialVersionUID = -272564984;

    /**
     * The reference instance of <code>oko.UsuariosAdmin</code>
     */
    public static final Usuariosadmin USUARIOSADMIN = new Usuariosadmin();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UsuariosadminRecord> getRecordType() {
        return UsuariosadminRecord.class;
    }

    /**
     * The column <code>oko.UsuariosAdmin.idUsuariosAdmin</code>.
     */
    public final TableField<UsuariosadminRecord, Integer> IDUSUARIOSADMIN = createField("idUsuariosAdmin", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>oko.UsuariosAdmin.login</code>.
     */
    public final TableField<UsuariosadminRecord, String> LOGIN = createField("login", org.jooq.impl.SQLDataType.VARCHAR.length(45), this, "");

    /**
     * The column <code>oko.UsuariosAdmin.pwd</code>.
     */
    public final TableField<UsuariosadminRecord, String> PWD = createField("pwd", org.jooq.impl.SQLDataType.VARCHAR.length(45), this, "");

    /**
     * The column <code>oko.UsuariosAdmin.salt</code>.
     */
    public final TableField<UsuariosadminRecord, String> SALT = createField("salt", org.jooq.impl.SQLDataType.VARCHAR.length(45), this, "");

    /**
     * The column <code>oko.UsuariosAdmin.fechaInsert</code>.
     */
    public final TableField<UsuariosadminRecord, Timestamp> FECHAINSERT = createField("fechaInsert", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>oko.UsuariosAdmin.rol</code>.
     */
    public final TableField<UsuariosadminRecord, Integer> ROL = createField("rol", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>oko.UsuariosAdmin.lastLogin</code>.
     */
    public final TableField<UsuariosadminRecord, Timestamp> LASTLOGIN = createField("lastLogin", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>oko.UsuariosAdmin.idEmpresa</code>.
     */
    public final TableField<UsuariosadminRecord, Integer> IDEMPRESA = createField("idEmpresa", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * Create a <code>oko.UsuariosAdmin</code> table reference
     */
    public Usuariosadmin() {
        this("UsuariosAdmin", null);
    }

    /**
     * Create an aliased <code>oko.UsuariosAdmin</code> table reference
     */
    public Usuariosadmin(String alias) {
        this(alias, USUARIOSADMIN);
    }

    private Usuariosadmin(String alias, Table<UsuariosadminRecord> aliased) {
        this(alias, aliased, null);
    }

    private Usuariosadmin(String alias, Table<UsuariosadminRecord> aliased, Field<?>[] parameters) {
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
    public Identity<UsuariosadminRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USUARIOSADMIN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UsuariosadminRecord> getPrimaryKey() {
        return Keys.KEY_USUARIOSADMIN_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UsuariosadminRecord>> getKeys() {
        return Arrays.<UniqueKey<UsuariosadminRecord>>asList(Keys.KEY_USUARIOSADMIN_PRIMARY, Keys.KEY_USUARIOSADMIN_IDUSUARIOSADMIN_UNIQUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<UsuariosadminRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<UsuariosadminRecord, ?>>asList(Keys.FK_EMPRESA);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Usuariosadmin as(String alias) {
        return new Usuariosadmin(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Usuariosadmin rename(String name) {
        return new Usuariosadmin(name, null);
    }
}
