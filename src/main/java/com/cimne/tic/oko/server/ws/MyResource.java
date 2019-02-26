package com.cimne.tic.oko.server.ws;

import com.cimne.tic.oko.server.ws.BLL.Constantes;
import com.cimne.tic.oko.server.ws.model.Tables;
import com.cimne.tic.oko.server.ws.model.tables.records.UsuariosRecord;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/{myresource : (?i)myresource}")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() throws InstantiationException, IllegalAccessException, ClassNotFoundException {


        Class.forName(Constantes.DB_DRIVER).newInstance();
        try (Connection conn = DriverManager.getConnection(Constantes.DB_URL, Constantes.DB_USER, Constantes.DB_PASS)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

            List<UsuariosRecord> result = create.select().from(Tables.USUARIOS).fetchInto(UsuariosRecord.class);
            //       	 UsuariosRecord result = create.select().from(Tables.USUARIOS).fetchAnyInto(UsuariosRecord.class);

            for (UsuariosRecord r : result) {
                return "Hola " + r.getNombre();

            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Fail!";
        }
        return "dbTable Empty :) OKO";
    }
}
