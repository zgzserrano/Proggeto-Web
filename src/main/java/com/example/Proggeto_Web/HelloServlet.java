package com.example.Proggeto_Web;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import DAO.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    @Override
    public void init() throws ServletException {
        //super.init();
        //DAO.registerDriver();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws  ServletException, IOException{
        //Basis structure
        HttpSession session= req.getSession();

        try {
            PrintWriter out = resp.getWriter();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void destroy() {
    }
}