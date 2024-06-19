package com.softwaremongers.seriouslybukkit.web;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PluginServlet extends HttpServlet{

    public PluginServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        if (path.startsWith("/getuser")) {
            handleGetUserStats(req, resp);
        } else if (path.startsWith("/server")) {
            handleGetServerStats(req, resp);
        } else {
            handleDefault(req, resp);
        }
    }

    /**
     * Handle stats/getuser endpoint
     * @param req yeah
     * @param resp yep
     * @throws IOException error
     */
    private void handleGetUserStats(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        String[] args = pathInfo.split("/");

        JSONObject json = new JSONObject();

        if (args.length < 3) {
            json.put("err", "Invalid Request");
            sendResponse(resp, json.toString());
            return;
        }

        json.put("arg2", args[2]);
        json.put("arg1", args[1]);
        json.put("arg0", args[0]);
        json.put("message", "User stats endpoint");
        sendResponse(resp, json);
    }

    /**
     * Handle stats/serverstats endpoint
     * @param req yeah
     * @param resp yep
     * @throws IOException error
     */
    private void handleGetServerStats(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        String[] args = pathInfo.split("/");

        JSONObject json = new JSONObject();
        json.put("message", "Stats endpoint");
        sendResponse(resp, json);
    }

    private void handleTest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject json = new JSONObject();
        json.put("message", "Test endpoint");
        sendResponse(resp, json);
    }

    private void handleDefault(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject json = new JSONObject();
        json.put("message", "Default endpoint");
        sendResponse(resp, json);
    }

    private void sendResponse(HttpServletResponse resp, JSONObject message) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();
        out.println(message);
        out.flush();
    }

    private void sendResponse(HttpServletResponse resp, String message) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();
        out.println(message);
        out.flush();
    }
}