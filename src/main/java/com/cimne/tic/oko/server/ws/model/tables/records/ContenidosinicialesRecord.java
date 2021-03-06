/*
 * This file is generated by jOOQ.
*/
package com.cimne.tic.oko.server.ws.model.tables.records;


import com.cimne.tic.oko.server.ws.model.tables.Contenidosiniciales;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record5;
import org.jooq.Row5;
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
public class ContenidosinicialesRecord extends UpdatableRecordImpl<ContenidosinicialesRecord> implements Record5<Integer, String, String, Integer, Integer> {

    private static final long serialVersionUID = -1157677994;

    /**
     * Setter for <code>oko.ContenidosIniciales.idContenidoInicial</code>.
     */
    public void setIdcontenidoinicial(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>oko.ContenidosIniciales.idContenidoInicial</code>.
     */
    public Integer getIdcontenidoinicial() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>oko.ContenidosIniciales.url</code>.
     */
    public void setUrl(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>oko.ContenidosIniciales.url</code>.
     */
    public String getUrl() {
        return (String) get(1);
    }

    /**
     * Setter for <code>oko.ContenidosIniciales.texto</code>.
     */
    public void setTexto(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>oko.ContenidosIniciales.texto</code>.
     */
    public String getTexto() {
        return (String) get(2);
    }

    /**
     * Setter for <code>oko.ContenidosIniciales.tipo</code>.
     */
    public void setTipo(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>oko.ContenidosIniciales.tipo</code>.
     */
    public Integer getTipo() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>oko.ContenidosIniciales.idLocalizacion</code>.
     */
    public void setIdlocalizacion(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>oko.ContenidosIniciales.idLocalizacion</code>.
     */
    public Integer getIdlocalizacion() {
        return (Integer) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record2<Integer, Integer> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, String, String, Integer, Integer> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, String, String, Integer, Integer> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Contenidosiniciales.CONTENIDOSINICIALES.IDCONTENIDOINICIAL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Contenidosiniciales.CONTENIDOSINICIALES.URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Contenidosiniciales.CONTENIDOSINICIALES.TEXTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return Contenidosiniciales.CONTENIDOSINICIALES.TIPO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return Contenidosiniciales.CONTENIDOSINICIALES.IDLOCALIZACION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getIdcontenidoinicial();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getTexto();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getTipo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getIdlocalizacion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContenidosinicialesRecord value1(Integer value) {
        setIdcontenidoinicial(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContenidosinicialesRecord value2(String value) {
        setUrl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContenidosinicialesRecord value3(String value) {
        setTexto(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContenidosinicialesRecord value4(Integer value) {
        setTipo(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContenidosinicialesRecord value5(Integer value) {
        setIdlocalizacion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContenidosinicialesRecord values(Integer value1, String value2, String value3, Integer value4, Integer value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ContenidosinicialesRecord
     */
    public ContenidosinicialesRecord() {
        super(Contenidosiniciales.CONTENIDOSINICIALES);
    }

    /**
     * Create a detached, initialised ContenidosinicialesRecord
     */
    public ContenidosinicialesRecord(Integer idcontenidoinicial, String url, String texto, Integer tipo, Integer idlocalizacion) {
        super(Contenidosiniciales.CONTENIDOSINICIALES);

        set(0, idcontenidoinicial);
        set(1, url);
        set(2, texto);
        set(3, tipo);
        set(4, idlocalizacion);
    }
}
