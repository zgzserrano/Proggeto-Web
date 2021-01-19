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

    @Override
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
                ArrayList<Celda> cells = new ArrayList<>();
                String[] giorni = {"LUNEDI","MARTEDI","MERCOLEDI","GIOVEDI","VENERDI"};
                for (int i = 15; i < 19; i++){
                    for (String g:giorni) {
                        cells.add(oraLibera(g, i));
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
                ArrayList<Imparte> i = mostrareAsso();
                out.println(JSONMan.serializeJson(i));
            }

            else if (func.equals("courseList")){
                out.println(JSONMan.serializeJson(mostrareCor()));
            }

            else if (func.equals("make")){
                Prenotazione p = JSONMan.parseJson(req.getParameter("reserve"), Prenotazione.class);
                effetuata(p);
            }

            else if (func.equals("cancel")){
                Prenotazione p = JSONMan.parseJson(req.getParameter("reserve"), Prenotazione.class);
                disdire(p);
            }

            else if (func.equals("reserve")) {
                Prenotazione[] pre = JSONMan.parseJson(req.getParameter("reserve"), Prenotazione[].class);
                for (Prenotazione p : pre) {
                    prenotare(p);
                }
            }



            } catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void init() throws ServletException {
        super.init();
        registerDriver();
    }

    public void destroy() {
    }
}