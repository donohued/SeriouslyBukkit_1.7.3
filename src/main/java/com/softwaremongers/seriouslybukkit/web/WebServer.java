// WebServer.java
package com.softwaremongers.seriouslybukkit.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServlet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {
    private Server server;
    private ServletContextHandler context;
    private ExecutorService executorService;

    public WebServer() {
        server = new Server(8282);

        context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        executorService = Executors.newSingleThreadExecutor();
    }

    public void start() {
        executorService.submit(() -> {
            try {
                server.start();
                server.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addEndpoint(String path, HttpServlet servlet) {
        context.addServlet(new ServletHolder(servlet), path);
    }
}