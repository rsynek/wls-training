package org.jboss.training.wls;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.training.wls.api.Calculator;

@WebServlet(urlPatterns = "/test")
public class ExampleServlet extends HttpServlet {

    @EJB
    private Calculator<Integer> calculator;

    @Override
    protected void doGet(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) throws ServletException, IOException {
        PrintWriter out = httpServletResponse.getWriter();
        out.write("<h1>Testing page</h1>");
        out.write("<h2>EJB instance:" + calculator + "</h2>");
        out.write("<p>");
        out.write("1 + 1 is " + calculator.add(1, 1));
        out.write("</p>");
        out.flush();
        out.close();
    }
}
