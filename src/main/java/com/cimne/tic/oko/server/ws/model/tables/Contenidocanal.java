/*
 * This file is generated by jOOQ.
*/
package com.cimne.tic.oko.server.ws.model.tables;


import com.cimne.tic.oko.server.ws.model.Keys;
import com.cimne.tic.oko.server.ws.model.Oko;
import com.cimne.tic.oko.server.ws.model.tables.records.ContenidocanalRecord;

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
public class Contenidocanal extends TableImpl<ContenidocanalRecord> {

    private static final long serialVersionUID = 1805863803;

    /**
     * The reference instance of <code>oko.ContenidoCanal</code>
     */
    public static final Contenidocanal CONTENIDOCANAL = new Contenidocanal();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ContenidocanalRecord> getRecordType() {
        return ContenidocanalRecord.class;
    }

    /**
     * The column <code>oko.ContenidoCanal.idcontenidoCanal</code>.
     */
    public final TableField<ContenidocanalRecord, Integer> IDCONTENIDOCANAL = createField("idcontenidoCanal", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>oko.ContenidoCanal.url</code>.
     */
    public final TableField<ContenidocanalRecord, String> URL = createField("url", org.jooq.impl.SQLDataType.VARCHAR.length(500).nullable(false), this, "");

    /**
     * The column <code>oko.ContenidoCanal.thumbnail</code>.
     */
    public final TableField<ContenidocanalRecord, String> THUMBNAIL = createField("thumbnail", org.jooq.impl.SQLDataType.VARCHAR.length(500).nullable(false), this, "");

    /**
     * The column <code>oko.ContenidoCanal.tipo</code>.
     */
    public final TableField<ContenidocanalRecord, Integer> TIPO = createField("tipo", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>oko.ContenidoCanal.textoImagen</code>.
     */
    public final TableField<ContenidocanalRecord, String> TEXTOIMAGEN = createField("textoImagen", org.jooq.impl.SQLDataType.VARCHAR.length(500), this, "");

    /**
     * The column <code>oko.ContenidoCanal.fechaInsert</code>.
     */
    public final TableField<ContenidocanalRecord, Timestamp> FECHAINSERT = createField("fechaInsert", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>oko.ContenidoCanal.idcanal</code>.
     */
    public final TableField<ContenidocanalRecord, Integer> IDCANAL = createField("idcanal", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>oko.ContenidoCanal</code> table reference
     */
    public Contenidocanal() {
        this("ContenidoCanal", null);
    }

    /**
     * Create an aliased <code>oko.ContenidoCanal</code> table reference
     */
    public Contenidocanal(String alias) {
        this(alias, CONTENIDOCANAL);
    }

    private Contenidocanal(String alias, Table<ContenidocanalRecord> aliased) {
        this(alias, aliased, null);
    }

    private Contenidocanal(String alias, Table<ContenidocanalRecord> aliased, Field<?>[] parameters) {
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
    public Identity<ContenidocanalRecord, Integer> getIdentity() {
        return Keys.IDENTITY_CONTENIDOCANAL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ContenidocanalRecord> getPrimaryKey() {
        return Keys.KEY_CONTENIDOCANAL_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ContenidocanalRecord>> getKeys() {
        return Arrays.<UniqueKey<ContenidocanalRecord>>asList(Keys.KEY_CONTENIDOCANAL_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ContenidocanalRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ContenidocanalRecord, ?>>asList(Keys.FK_CONTENIDOCANAL_CANALES);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Contenidocanal as(String alias) {
        return new Contenidocanal(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Contenidocanal rename(String name) {
        return new Contenidocanal(name, null);
    }
}