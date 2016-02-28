package org.jboss.training.testds;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(urlPatterns = "/test")
public class TestDataSourceServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath());
    }

    @Override
    protected void doPost(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String jndi = getJndiNameParam(httpServletRequest);
        if (jndi != null) {
            PrintWriter writer = httpServletResponse.getWriter();
            try {
                writer.write("<h1>Results of Test</h1>");
                DataSource ds = getDataSource(jndi);
                writer.write("<p>Successfully looked up DataSource named " + jndi + "</p>");
                String table = getTableParam(httpServletRequest);
                if (table != null) {
                    printTable(table, ds, writer);
                }
            } catch(Throwable throwable) {
                handleError(throwable, writer);
            } finally {
                String ctx = httpServletRequest.getContextPath();
                writer.write("<br/>");
                writer.write("<a href=\"" + ctx + "\">Back to a query form.</a>");
                writer.flush();
                writer.close();
            }
        }
    }

    private String getJndiNameParam(final HttpServletRequest httpServletRequest) {
        return httpServletRequest.getParameter("jndiName");
    }

    private String getTableParam(final HttpServletRequest httpServletRequest) {
        return httpServletRequest.getParameter("tableName");
    }

    private DataSource getDataSource(final String jndi) {
        try {
            InitialContext ctx = new InitialContext();
            return (DataSource) ctx.lookup(jndi);
        } catch (NamingException ex) {
            throw new IllegalArgumentException("No DataSource found for JNDI name " + jndi, ex);
        }
    }

    private void printTable(final String table, final DataSource ds, final PrintWriter writer) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = ds.getConnection();
            write(writer, "<p>Successfully connected to database.</p>");
            stmt = conn.createStatement();
            String query = "SELECT * FROM " + table;
            write(writer, "<p>Attempting query \"" + query + "\"</p>");
            ResultSet results = stmt.executeQuery(query);
            ResultSetMetaData rsMetaData = results.getMetaData();
            int numberOfColumns = rsMetaData.getColumnCount();
            write(writer, "<table><tr>");
            //Display the header row of column names
            for (int i = 1; i <= numberOfColumns; i++) {
                int columnType = rsMetaData.getColumnType(i);
                String columnName = rsMetaData.getColumnName(i);
                if (columnType == Types.VARCHAR) {
                    write(writer, "<td>" + columnName + "</td>");
                }
            }
            write(writer, "</tr>");
            while (results.next()) {
                write(writer, "<tr>");
                for (int i = 1; i <= numberOfColumns; i++) {
                    int columnType = rsMetaData.getColumnType(i);
                    String columnName = rsMetaData.getColumnName(i);
                    if (columnType == Types.VARCHAR) {
                        write(writer, "<td>" + results.getString(columnName) + "</td>");
                    }
                }
                write(writer, "</tr>");
            }
            write(writer, "</table>");
        } catch (SQLException ex) {
            handleError(ex, writer);
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                handleError(ex, writer);
            }
            try {
                conn.close();
            } catch (SQLException ex) {
                handleError(ex, writer);
            }
        }
    }

    private void handleError(final Throwable throwable, final PrintWriter writer) {
        write(writer, "An exception was thrown: " + throwable.getMessage() + "<br>");
        throwable.printStackTrace(writer);
    }

    private void write(final Writer writer, String message) {
        try {
            writer.write(message);
        } catch (IOException ex) {
            throw new RuntimeException("Unable to write message.", ex);
        }
    }
}
