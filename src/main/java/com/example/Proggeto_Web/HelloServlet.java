package com.example.Proggeto_Web;


import DAO.*;
import com.fasterxml.jackson.core.type.TypeReference;
import myBeans.JSONManager;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import static DAO.DAO.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;



    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws  ServletException, IOException{
        //Basis structure
        HttpSession session= req.getSession();

        try {
            PrintWriter out = resp.getWriter();
            JSONManager JSONMan = new JSONManager();
            String func = req.getParameter("action");
            if(func.equals("connection")){
                String account = req.getParameter("user");
                String pass = req.getParameter("password");
                System.out.println("Aqui llega");
                if (correctPass(account, pass)){
                    if(account!=null && pass != null){
                        session.setAttribute("account", account);
                        session.setAttribute("password", pass);
                    }

                    if (getRole(account)){
                        out.println(JSONMan.serializeJson("admin"));
                    }
                    else {
                        out.println(JSONMan.serializeJson("notAdmin"));
                    }
                } else {
                    out.println(JSONMan.serializeJson("fail"));
                }
            }

            else if(func.equals("justLogin")){
                String account = (String) session.getAttribute("account");
                String pass = (String) session.getAttribute("password");
                if (account != null && pass != null) {
                    if (correctPass(account,pass)) {
                        Utente u = new Utente(account, pass, (Boolean) getRole(account));
                        out.println(JSONMan.serializeJson(u));
                    } else {
                        out.print("fail");
                    }
                } else {
                    out.print("fail");
                }
            }

            else if (func.equals("logout")){
                session.invalidate();
            }

            else if (func.equals("freeHour")) {
                ArrayList<Cell> cells = new ArrayList<>();
                String[] giorni = {"LUNEDI","MARTEDI","MERCOLEDI","GIOVEDI","VENERDI"};
                for (int i = 15; i < 19; i++){
                    for (String g:giorni) {
                        cells.add(gethourfree(i, g));
                    }
                }
                out.println(JSONMan.serializeJson(cells));
            }

            else if (func.equals("reserveList")){
                String account = req.getParameter("user");
                ArrayList<Prenotazione> reserves;
                if (account.equals("admin"))
                    reserves = getPrenotazioni();
                else{
                    reserves = getPrenotazioni(account);
                }

                out.println(JSONMan.serializeJson(reserves));
            }

            else if (func.equals("teacherList")){
                out.println(JSONMan.serializeJson(mostrareDoc()));
            }

            else if (func.equals("associationList")){
                ArrayList<Imparte> i = showImpart();  //A ver la que me has liado juanito
                out.println(JSONMan.serializeJson(i));
            }

            else if (func.equals("courseList")){
                out.println(JSONMan.serializeJson(mostrareCor()));
            }

            else if (func.equals("make")){
                Prenotazione p = JSONMan.parseJson(req.getParameter("reserve"), Prenotazione.class);
                achieve(p);
            }

            else if (func.equals("cancel")){
                Prenotazione p = JSONMan.parseJson(req.getParameter("reserve"), Prenotazione.class);
                delete(p);
            }

            else if (func.equals("reserve")) {
                Prenotazione[] pre = JSONMan.parseJson(req.getParameter("reserve"), Prenotazione[].class);
                for (Prenotazione p : pre) {
                    reservate(p);
                }

            }
            else if (func.equals("addDoc")){
                String name = req.getParameter("name");
                String surName = req.getParameter("surName");
                Docente p = new Docente(name, surName);
                if (addDocenteToDB(p)) {
                    out.print("true");
                }
                else {
                    out.print("false");
                }
            }
            else if (func.equals("removeDoc")){
                Docente p = JSONMan.parseJson(req.getParameter("teacher"), Docente.class);
                if (deleteDocenteofDB(p)) {
                    out.print("true");
                }
                else {
                    out.print("false");
                }
            }
            else if (func.equals("addAso")){
                Docente doc = JSONMan.parseJson(req.getParameter("teacher"), Docente.class);
                Corso c = JSONMan.parseJson(req.getParameter("course"), Corso.class);

                Associazione p = new Associazione(doc,c);
                if (create(p)) {
                    out.print("true");
                }
                else {
                    out.print("false");
                }
            }
            else if (func.equals("removeAso")){
                Corso c = JSONMan.parseJson(req.getParameter("course"), Corso.class);
                Docente d = JSONMan.parseJson(req.getParameter("teacher"), Docente.class);
                Associazione a = new Associazione(d,c);
                if (eliminate(a)) {
                    out.print("true");
                }
                else {
                    out.print("false");
                }
            }
            else if (func.equals("addCourse")){
                String title = req.getParameter("course");

                Corso p = new Corso(title);
                if (addCoursetoDB(p)) {
                    out.print("true");
                }
                else {
                    out.print("false");
                }
            }
            else if (func.equals("removeCourse")){
                Corso p = JSONMan.parseJson(req.getParameter("course"), Corso.class);
                if (deleteCoursetoDB(p)) {
                    out.print("true");
                }
                else {
                    out.print("false");
                }
            }



        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws ServletException {
        System.out.println("Juan eres un mierdas");
        super.init();
        registerDriver();
        System.out.println("Juan eres un ");

    }

    public void destroy() {
    }
}