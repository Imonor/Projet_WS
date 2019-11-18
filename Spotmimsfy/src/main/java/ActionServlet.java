/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import action.Action;
import action.ActionGetInfos;
import action.ActionGetSearchedTerm;
import action.ActionTraiterRecherche;
import serialization.Serialization;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import serialization.SerializationGetSearchedTerm;
import serialization.SerializationListeURIs;

/**
 *
 * @author ysimiandco
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");

        request.setCharacterEncoding("UTF-8");

        String todo = request.getParameter("todo");

        Action action = null;
        Serialization serialization = null;

        if ("traiterRecherche".equals(todo)) {
            action = new ActionTraiterRecherche();
            serialization = new SerializationListeURIs();
        } else if ("chargerDonneesArtistes".equals(todo)) {
            action = new ActionGetInfos();
            serialization = new SerializationListeURIs();
        } else if ("getSearchedTerm".equals(todo)) {
            action = new ActionGetSearchedTerm();
            serialization = new SerializationGetSearchedTerm();
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
