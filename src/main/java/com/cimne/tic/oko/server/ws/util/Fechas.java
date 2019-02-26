package com.cimne.tic.oko.server.ws.util;

import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.util.TimeZone;

/**
 * Created by lorddarks on 10/2/17.
 * <p>
 * para gestionar las fechas
 */
public final class Fechas {

    public static Timestamp GetCurrentTimestampLong() {
        return new Timestamp(new DateTime().getMillis() + TimeZone.getDefault().getRawOffset());
    }
}
