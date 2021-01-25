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
public class HelloServlet extends javax.servlet.http.HttpServlet {



    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws  ServletException, IOException{
        //Basis structure
        resp.setContentType("application/json");
        HttpSession session= req.getSession() ;

        try {
            PrintWriter out = resp.getWriter();
            JSONManager JSONMan = new JSONManager();
            String func = req.getParameter("action");
            switch (func) {
                case "connection": {
                    String account = req.getParameter("user");
                    String pass = req.getParameter("password");
                    if (correctPass(account, pass)) {
                        if (account != null && pass != null) {
                            session.setAttribute("account", account);
                            session.setAttribute("password", pass);
                        }

                        if (getRole(account)) {
                            out.println(JSONMan.serializeJson("ad"));
                        } else {
                            out.println(JSONMan.serializeJson("a"));
                        }
                    } else {
                        out.println(JSONMan.serializeJson("fail"));
                    }
                    break;
                }
                case "justLogin": {
                    String account = (String) session.getAttribute("account");
                    String pass = (String) session.getAttribute("password");
                    if (account != null && pass != null) {
                        if (correctPass(account, pass)) {
                            Utente u = new Utente(account, pass,  getRole(account));
                            out.println(JSONMan.serializeJson(u));
                        } else {
                            out.print("fail");
                        }
                    } else {
                        out.print("fail");
                    }
                    break;
                }
                case "logout":
                    session.invalidate();
                    break;
                case "freeHour":
                    ArrayList<Cell> cells = new ArrayList<>();
                    String[] giorni = {"LUNEDI", "MARTEDI", "MERCOLEDI", "GIOVEDI", "VENERDI"};
                    for (int i = 15; i < 19; i++) {
                        for (String g : giorni) {
                            cells.add(gethourfree(i, g));
                        }
                    }
                    out.println(JSONMan.serializeJson(cells));
                    break;
                case "reserveList": {
                    String account = req.getParameter("user");
                    boolean admin = getRole(account);
                    ArrayList<Prenotazione> reserves;
                    if (account.equals(admin))
                        reserves = getPrenotazioni();
                    else {
                        reserves = getPrenotazioni(account);
                    }

                    out.println(JSONMan.serializeJson(reserves));
                    break;
                }
                case "teacherList":
                    out.println(JSONMan.serializeJson(showTeacher()));
                    break;
                case "associationList":
                    ArrayList<Imparte> i = showImpart();  //A ver la que me has liado juanito
                    out.println(JSONMan.serializeJson(i));
                    break;
                case "courseList":
                    out.println(JSONMan.serializeJson(showCourses()));
                    break;
                case "make": {
                    Prenotazione p = JSONMan.parseJson(req.getParameter("reserve"), Prenotazione.class);
                    achieve(p);
                    break;
                }
                case "cancel": {
                    Prenotazione p = JSONMan.parseJson(req.getParameter("reserve"), Prenotazione.class);
                    delete(p);
                    break;
                }
                case "reserve":
                    Prenotazione[] pre = JSONMan.parseJson(req.getParameter("reserve"), Prenotazione[].class);
                    for (Prenotazione p : pre) {
                        reservate(p);
                    }

                    break;
                case "addDoc": {
                    String name = req.getParameter("name");
                    String surName = req.getParameter("surname");
                    Docente p = new Docente(name, surName);
                    if (addDocenteToDB(p)) {
                        out.print("true");
                    } else {
                        out.print("false");
                    }
                    break;
                }
                case "removeDoc": {
                    Docente p = JSONMan.parseJson(req.getParameter("teacher"), Docente.class);
                    if (deleteDocenteofDB(p)) {
                        out.print("true");
                    } else {
                        out.print("false");
                    }
                    break;
                }
                case "addAso": {
                    Docente doc = JSONMan.parseJson(req.getParameter("teacher"), Docente.class);
                    Corso c = JSONMan.parseJson(req.getParameter("course"), Corso.class);

                    Associazione p = new Associazione(doc, c);
                    if (create(p)) {
                        out.print("true");
                    } else {
                        out.print("false");
                    }
                    break;
                }
                case "removeAso": {
                    Corso c = JSONMan.parseJson(req.getParameter("course"), Corso.class);
                    Docente d = JSONMan.parseJson(req.getParameter("teacher"), Docente.class);
                    Associazione a = new Associazione(d, c);
                    if (eliminate(a)) {
                        out.print("true");
                    } else {
                        out.print("false");
                    }
                    break;
                }
                case "addCourse": {
                    String title = req.getParameter("course");
                    Corso c = new Corso(title);
                    if (addCoursetoDB(c)) {
                        out.print("true");
                    } else {
                        out.print("false");
                    }
                    break;
                }
                case "removeCourse": {
                    Corso p = JSONMan.parseJson(req.getParameter("course"), Corso.class);
                    if (deleteCoursetoDB(p)) {
                        out.print("true");
                    } else {
                        out.print("false");
                    }
                    break;
                }
            }



        } catch (Exception e){
            e.printStackTrace();
        }
    }



    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        registerDriver();
    }

}