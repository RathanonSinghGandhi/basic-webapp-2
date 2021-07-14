package io.muic.ooc.webapp;

import java.io.File;
import javax.servlet.ServletException;

import io.muic.ooc.webapp.service.DatabaseConnectionService;
import io.muic.ooc.webapp.service.SecurityService;
import io.muic.ooc.webapp.service.UserService;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class Webapp {

    public static void main(String[] args) {

        File docBase = new File("src/main/webapp/");
        docBase.mkdirs();
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8082);


        SecurityService securityService = new SecurityService();
        securityService.setUserService(UserService.getInstance());

        ServletRouter servletRouter = new ServletRouter();
        servletRouter.setSecurityService(securityService);

        Context ctx;
        try {
            ctx = tomcat.addWebapp("", docBase.getAbsolutePath());
            servletRouter.init(ctx);

            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException ex) {
            ex.printStackTrace();
        }

    }
}
