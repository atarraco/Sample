/*
 * This file is generated by jOOQ.
*/
package com.cimne.tic.oko.server.ws.model.tables.records;


import com.cimne.tic.oko.server.ws.model.tables.Dispositivosfisicos;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
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
public class DispositivosfisicosRecord extends UpdatableRecordImpl<DispositivosfisicosRecord> implements Record9<Integer, String, String, String, Timestamp, Timestamp, Integer, Integer, Integer> {

    private static final long serialVersionUID = 1719056798;

    /**
     * Setter for <code>oko.DispositivosFisicos.idDispositivosFisico</code>.
     */
    public void setIddispositivosfisico(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>oko.DispositivosFisicos.idDispositivosFisico</code>.
     */
    public Integer getIddispositivosfisico() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>oko.DispositivosFisicos.nombre</code>.
     */
    public void setNombre(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>oko.DispositivosFisicos.nombre</code>.
     */
    public String getNombre() {
        return (String) get(1);
    }

    /**
     * Setter for <code>oko.DispositivosFisicos.token</code>.
     */
    public void setToken(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>oko.DispositivosFisicos.token</code>.
     */
    public String getToken() {
        return (String) get(2);
    }

    /**
     * Setter for <code>oko.DispositivosFisicos.version</code>.
     */
    public void setVersion(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>oko.DispositivosFisicos.version</code>.
     */
    public String getVersion() {
        return (String) get(3);
    }

    /**
     * Setter for <code>oko.DispositivosFisicos.fechaInsert</code>.
     */
    public void setFechainsert(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>oko.DispositivosFisicos.fechaInsert</code>.
     */
    public Timestamp getFechainsert() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>oko.DispositivosFisicos.fechaModificacion</code>.
     */
    public void setFechamodificacion(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>oko.DispositivosFisicos.fechaModificacion</code>.
     */
    public Timestamp getFechamodificacion() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>oko.DispositivosFisicos.idNumeroSerie</code>.
     */
    public void setIdnumeroserie(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>oko.DispositivosFisicos.idNumeroSerie</code>.
     */
    public Integer getIdnumeroserie() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>oko.DispositivosFisicos.idLocalizacion</code>.
     */
    public void setIdlocalizacion(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>oko.DispositivosFisicos.idLocalizacion</code>.
     */
    public Integer getIdlocalizacion() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>oko.DispositivosFisicos.idEmpresa</code>.
     */
    public void setIdempresa(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>oko.DispositivosFisicos.idEmpresa</code>.
     */
    public Integer getIdempresa() {
        return (Integer) get(8);
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
    // Record9 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, String, String, String, Timestamp, Timestamp, Integer, Integer, Integer> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, String, String, String, Timestamp, Timestamp, Integer, Integer, Integer> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Dispositivosfisicos.DISPOSITIVOSFISICOS.IDDISPOSITIVOSFISICO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Dispositivosfisicos.DISPOSITIVOSFISICOS.NOMBRE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Dispositivosfisicos.DISPOSITIVOSFISICOS.TOKEN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Dispositivosfisicos.DISPOSITIVOSFISICOS.VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return Dispositivosfisicos.DISPOSITIVOSFISICOS.FECHAINSERT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return Dispositivosfisicos.DISPOSITIVOSFISICOS.FECHAMODIFICACION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return Dispositivosfisicos.DISPOSITIVOSFISICOS.IDNUMEROSERIE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return Dispositivosfisicos.DISPOSITIVOSFISICOS.IDLOCALIZACION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return Dispositivosfisicos.DISPOSITIVOSFISICOS.IDEMPRESA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getIddispositivosfisico();
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
    public String value3() {
        return getToken();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getFechainsert();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getFechamodificacion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getIdnumeroserie();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getIdlocalizacion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value9() {
        return getIdempresa();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispositivosfisicosRecord value1(Integer value) {
        setIddispositivosfisico(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispositivosfisicosRecord value2(String value) {
        setNombre(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispositivosfisicosRecord value3(String value) {
        setToken(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispositivosfisicosRecord value4(String value) {
        setVersion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispositivosfisicosRecord value5(Timestamp value) {
        setFechainsert(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispositivosfisicosRecord value6(Timestamp value) {
        setFechamodificacion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispositivosfisicosRecord value7(Integer value) {
        setIdnumeroserie(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispositivosfisicosRecord value8(Integer value) {
        setIdlocalizacion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispositivosfisicosRecord value9(Integer value) {
        setIdempresa(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispositivosfisicosRecord values(Integer value1, String value2, String value3, String value4, Timestamp value5, Timestamp value6, Integer value7, Integer value8, Integer value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached DispositivosfisicosRecord
     */
    public DispositivosfisicosRecord() {
        super(Dispositivosfisicos.DISPOSITIVOSFISICOS);
    }

    /**
     * Create a detached, initialised DispositivosfisicosRecord
     */
    public DispositivosfisicosRecord(Integer iddispositivosfisico, String nombre, String token, String version, Timestamp fechainsert, Timestamp fechamodificacion, Integer idnumeroserie, Integer idlocalizacion, Integer idempresa) {
        super(Dispositivosfisicos.DISPOSITIVOSFISICOS);

        set(0, iddispositivosfisico);
        set(1, nombre);
        set(2, token);
        set(3, version);
        set(4, fechainsert);
        set(5, fechamodificacion);
        set(6, idnumeroserie);
        set(7, idlocalizacion);
        set(8, idempresa);
    }
}
