/*
 * This file is generated by jOOQ.
*/
package com.cimne.tic.oko.server.ws.model.tables.records;


import com.cimne.tic.oko.server.ws.model.tables.Dispositivosvirtuales;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record14;
import org.jooq.Row14;
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
public class DispositivosvirtualesRecord extends UpdatableRecordImpl<DispositivosvirtualesRecord> implements Record14<Integer, String, String, Integer, Integer, Integer, String, String, Timestamp, Timestamp, Integer, Integer, Integer, Integer> {

    private static final long serialVersionUID = 1626540152;

    /**
     * Setter for <code>oko.DispositivosVirtuales.idDispositivoVirtual</code>.
     */
    public void setIddispositivovirtual(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>oko.DispositivosVirtuales.idDispositivoVirtual</code>.
     */
    public Integer getIddispositivovirtual() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>oko.DispositivosVirtuales.nombre</code>.
     */
    public void setNombre(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>oko.DispositivosVirtuales.nombre</code>.
     */
    public String getNombre() {
        return (String) get(1);
    }

    /**
     * Setter for <code>oko.DispositivosVirtuales.uuid</code>.
     */
    public void setUuid(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>oko.DispositivosVirtuales.uuid</code>.
     */
    public String getUuid() {
        return (String) get(2);
    }

    /**
     * Setter for <code>oko.DispositivosVirtuales.visibilidad</code>.
     */
    public void setVisibilidad(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>oko.DispositivosVirtuales.visibilidad</code>.
     */
    public Integer getVisibilidad() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>oko.DispositivosVirtuales.estado</code>.
     */
    public void setEstado(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>oko.DispositivosVirtuales.estado</code>.
     */
    public Integer getEstado() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>oko.DispositivosVirtuales.controlContenido</code>.
     */
    public void setControlcontenido(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>oko.DispositivosVirtuales.controlContenido</code>.
     */
    public Integer getControlcontenido() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>oko.DispositivosVirtuales.urlAvatar</code>.
     */
    public void setUrlavatar(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>oko.DispositivosVirtuales.urlAvatar</code>.
     */
    public String getUrlavatar() {
        return (String) get(6);
    }

    /**
     * Setter for <code>oko.DispositivosVirtuales.link</code>.
     */
    public void setLink(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>oko.DispositivosVirtuales.link</code>.
     */
    public String getLink() {
        return (String) get(7);
    }

    /**
     * Setter for <code>oko.DispositivosVirtuales.fechaInsert</code>.
     */
    public void setFechainsert(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>oko.DispositivosVirtuales.fechaInsert</code>.
     */
    public Timestamp getFechainsert() {
        return (Timestamp) get(8);
    }

    /**
     * Setter for <code>oko.DispositivosVirtuales.fechaModificacion</code>.
     */
    public void setFechamodificacion(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>oko.DispositivosVirtuales.fechaModificacion</code>.
     */
    public Timestamp getFechamodificacion() {
        return (Timestamp) get(9);
    }

    /**
     * Setter for <code>oko.DispositivosVirtuales.idDispositivoFisico</code>.
     */
    public void setIddispositivofisico(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>oko.DispositivosVirtuales.idDispositivoFisico</code>.
     */
    public Integer getIddispositivofisico() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>oko.DispositivosVirtuales.idPropietario</code>.
     */
    public void setIdpropietario(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>oko.DispositivosVirtuales.idPropietario</code>.
     */
    public Integer getIdpropietario() {
        return (Integer) get(11);
    }

    /**
     * Setter for <code>oko.DispositivosVirtuales.tipoEspejo</code>.
     */
    public void setTipoespejo(Integer value) {
        set(12, value);
    }

    /**
     * Getter for <code>oko.DispositivosVirtuales.tipoEspejo</code>.
     */
    public Integer getTipoespejo() {
        return (Integer) get(12);
    }

    /**
     * Setter for <code>oko.DispositivosVirtuales.idPadre</code>.
     */
    public void setIdpadre(Integer value) {
        set(13, value);
    }

    /**
     * Getter for <code>oko.DispositivosVirtuales.idPadre</code>.
     */
    public Integer getIdpadre() {
        return (Integer) get(13);
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
    // Record14 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row14<Integer, String, String, Integer, Integer, Integer, String, String, Timestamp, Timestamp, Integer, Integer, Integer, Integer> fieldsRow() {
        return (Row14) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row14<Integer, String, String, Integer, Integer, Integer, String, String, Timestamp, Timestamp, Integer, Integer, Integer, Integer> valuesRow() {
        return (Row14) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Dispositivosvirtuales.DISPOSITIVOSVIRTUALES.IDDISPOSITIVOVIRTUAL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Dispositivosvirtuales.DISPOSITIVOSVIRTUALES.NOMBRE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Dispositivosvirtuales.DISPOSITIVOSVIRTUALES.UUID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return Dispositivosvirtuales.DISPOSITIVOSVIRTUALES.VISIBILIDAD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return Dispositivosvirtuales.DISPOSITIVOSVIRTUALES.ESTADO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return Dispositivosvirtuales.DISPOSITIVOSVIRTUALES.CONTROLCONTENIDO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return Dispositivosvirtuales.DISPOSITIVOSVIRTUALES.URLAVATAR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return Dispositivosvirtuales.DISPOSITIVOSVIRTUALES.LINK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return Dispositivosvirtuales.DISPOSITIVOSVIRTUALES.FECHAINSERT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field10() {
        return Dispositivosvirtuales.DISPOSITIVOSVIRTUALES.FECHAMODIFICACION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field11() {
        return Dispositivosvirtuales.DISPOSITIVOSVIRTUALES.IDDISPOSITIVOFISICO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field12() {
        return Dispositivosvirtuales.DISPOSITIVOSVIRTUALES.IDPROPIETARIO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field13() {
        return Dispositivosvirtuales.DISPOSITIVOSVIRTUALES.TIPOESPEJO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field14() {
        return Dispositivosvirtuales.DISPOSITIVOSVIRTUALES.IDPADRE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getIddispositivovirtual();
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
        return getUuid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getVisibilidad();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getEstado();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getControlcontenido();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getUrlavatar();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getLink();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value9() {
        return getFechainsert();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value10() {
        return getFechamodificacion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value11() {
        return getIddispositivofisico();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value12() {
        return getIdpropietario();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value13() {
        return getTipoespejo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value14() {
        return getIdpadre();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispositivosvirtualesRecord value1(Integer value) {
        setIddispositivovirtual(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispositivosvirtualesRecord value2(String value) {
        setNombre(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispositivosvirtualesRecord value3(String value) {
        setUuid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispositivosvirtualesRecord value4(Integer value) {
        setVisibilidad(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispositivosvirtualesRecord value5(Integer value) {
        setEstado(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispositivosvirtualesRecord value6(Integer value) {
        setControlcontenido(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispositivosvirtualesRecord value7(String value) {
        setUrlavatar(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispositivosvirtualesRecord value8(String value) {
        setLink(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispositivosvirtualesRecord value9(Timestamp value) {
        setFechainsert(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispositivosvirtualesRecord value10(Timestamp value) {
        setFechamodificacion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispositivosvirtualesRecord value11(Integer value) {
        setIddispositivofisico(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispositivosvirtualesRecord value12(Integer value) {
        setIdpropietario(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispositivosvirtualesRecord value13(Integer value) {
        setTipoespejo(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispositivosvirtualesRecord value14(Integer value) {
        setIdpadre(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispositivosvirtualesRecord values(Integer value1, String value2, String value3, Integer value4, Integer value5, Integer value6, String value7, String value8, Timestamp value9, Timestamp value10, Integer value11, Integer value12, Integer value13, Integer value14) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        value14(value14);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached DispositivosvirtualesRecord
     */
    public DispositivosvirtualesRecord() {
        super(Dispositivosvirtuales.DISPOSITIVOSVIRTUALES);
    }

    /**
     * Create a detached, initialised DispositivosvirtualesRecord
     */
    public DispositivosvirtualesRecord(Integer iddispositivovirtual, String nombre, String uuid, Integer visibilidad, Integer estado, Integer controlcontenido, String urlavatar, String link, Timestamp fechainsert, Timestamp fechamodificacion, Integer iddispositivofisico, Integer idpropietario, Integer tipoespejo, Integer idpadre) {
        super(Dispositivosvirtuales.DISPOSITIVOSVIRTUALES);

        set(0, iddispositivovirtual);
        set(1, nombre);
        set(2, uuid);
        set(3, visibilidad);
        set(4, estado);
        set(5, controlcontenido);
        set(6, urlavatar);
        set(7, link);
        set(8, fechainsert);
        set(9, fechamodificacion);
        set(10, iddispositivofisico);
        set(11, idpropietario);
        set(12, tipoespejo);
        set(13, idpadre);
    }
}
