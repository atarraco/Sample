/*
 * This file is generated by jOOQ.
*/
package com.cimne.tic.oko.server.ws.model.tables.records;


import com.cimne.tic.oko.server.ws.model.tables.Empresa;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


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
public class EmpresaRecord extends UpdatableRecordImpl<EmpresaRecord> implements Record2<Integer, String> {

    private static final long serialVersionUID = 182694374;

    /**
     * Setter for <code>oko.Empresa.idEmpresa</code>.
     */
    public void setIdempresa(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>oko.Empresa.idEmpresa</code>.
     */
    public Integer getIdempresa() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>oko.Empresa.nombre</code>.
     */
    public void setNombre(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>oko.Empresa.nombre</code>.
     */
    public String getNombre() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Integer, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Integer, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Empresa.EMPRESA.IDEMPRESA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Empresa.EMPRESA.NOMBRE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getIdempresa();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getNombre();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmpresaRecord value1(Integer value) {
        setIdempresa(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmpresaRecord value2(String value) {
        setNombre(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmpresaRecord values(Integer value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached EmpresaRecord
     */
    public EmpresaRecord() {
        super(Empresa.EMPRESA);
    }

    /**
     * Create a detached, initialised EmpresaRecord
     */
    public EmpresaRecord(Integer idempresa, String nombre) {
        super(Empresa.EMPRESA);

        set(0, idempresa);
        set(1, nombre);
    }
}