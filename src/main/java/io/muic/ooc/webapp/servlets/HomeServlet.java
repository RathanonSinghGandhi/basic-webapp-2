package io.muic.ooc.webapp.servlets;

import io.muic.ooc.webapp.Routable;
import io.muic.ooc.webapp.service.SecurityService;
import io.muic.ooc.webapp.service.UserService;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gigadot
 */
public class HomeServlet extends HttpServlet implements Routable {

    private SecurityService securityService;


    @Override
    public String getMapping() {
        return "/index.jsp";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean authorized = securityService.isAuthorized(request);
        if (authorized) {
            // do MVC in here
            String username = (String) request.getSession().getAttribute("username");
            request.setAttribute("username", username);

            UserService userService = UserService.getInstance();
            request.setAttribute("users", userService.findALl());

            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/home.jsp");
            rd.include(request, response);
        } else {
            response.sendRedirect("/login");
        }
    }
}