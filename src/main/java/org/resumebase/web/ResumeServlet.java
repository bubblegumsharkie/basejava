package org.resumebase.web;

import org.resumebase.config.Config;
import org.resumebase.model.Resume;
import org.resumebase.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = response.getWriter();

        writer.println("<table>");
        writer.write("<tr><th>UUID</th>");
        writer.write("<th>Name</th></tr>");

        for (Resume r : storage.getAllSorted()) {
            writer.write("<tr><td>" + r.getUuid() + "</td>");
            writer.write("<td>" + r.getFullName() + "</td></tr>");
        }
        writer.println("</table>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
