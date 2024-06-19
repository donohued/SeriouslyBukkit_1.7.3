// WebManager.java
package com.softwaremongers.seriouslybukkit.web;

import com.softwaremongers.seriouslybukkit.SeriouslyBeta;

public class WebManager {
    private SeriouslyBeta plugin;
    private WebServer webServer;
    public WebServer getWebServer() {
        return this.webServer;
    }

    public WebManager(SeriouslyBeta instance) {
        this.plugin = instance;

        this.webServer = new WebServer();

        // Add endpoints.
        //this.webServer.addEndpoint("/stats", new PluginServlet());
        this.webServer.addEndpoint("/stats/*", new PluginServlet());
    }
}