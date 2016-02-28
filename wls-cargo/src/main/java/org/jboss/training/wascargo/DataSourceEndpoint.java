package org.jboss.training.wascargo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/ds")
public class DataSourceEndpoint {

    @GET
    @Path("/info")
    @Produces(MediaType.TEXT_PLAIN)
    public String getInfo() {
        return "For testing DS, provide JNDI and table name.";
    }

    @GET
    @Path("/{jndi}/{table}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getContent(@PathParam("jndi") final String jndi, @PathParam("table") final String table) {
        DataSource ds = getDataSource(jndi);
        StringBuilder sb = new StringBuilder();
        serializeTable(table, ds, sb);
        return sb.toString();
    }


    private DataSource getDataSource(final String jndi) {
        try {
            InitialContext ctx = new InitialContext();
            return (DataSource) ctx.lookup(jndi);
        } catch (NamingException ex) {
            throw new IllegalArgumentException("No DataSource found for JNDI name " + jndi, ex);
        }
    }

    private void serializeTable(final String table, final DataSource ds, final StringBuilder sb) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = ds.getConnection();

            stmt = conn.createStatement();
            String query = "SELECT * FROM " + table;
            ResultSet results = stmt.executeQuery(query);
            ResultSetMetaData rsMetaData = results.getMetaData();
            int numberOfColumns = rsMetaData.getColumnCount();

            while (results.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    int columnType = rsMetaData.getColumnType(i);
                    String columnName = rsMetaData.getColumnName(i);
                    if (columnType == Types.VARCHAR) {
                        if(i > 1) {
                            sb.append(";");
                        }
                        sb.append(results.getString(columnName));
                    }
                }
            }
        } catch (SQLException ex) {
            handleError(ex);
        } finally {
            try {
                if(stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                handleError(ex);
            }
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                handleError(ex);
            }
        }
    }

    private void handleError(final Throwable throwable) {
        throw new RuntimeException(throwable);
    }
}
