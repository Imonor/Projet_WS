package general;


import action.Action;
import action.ActionGetInfos;
import action.ActionGetItemSelected;
import action.ActionGetSearchedTerm;
import action.ActionTraiterRecherche;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import serialization.Serialization;
import serialization.SerializationGetItemSelected;
import serialization.SerializationGetSearchedTerm;
import serialization.SerializationInfos;
import serialization.SerializationListeURIs;

/*
 * Controller
 * Manage HTTP requests to execute the corresponding action and return the adapted view and result
 */
/**
 *
 * @author Guilhem HERMET
 */
 
@WebServlet(urlPatterns = {"/Controller"})
public class Controller extends HttpServlet {
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
			//todo is the main request parameter, which indicates the action to implement
            String todo = request.getParameter("todo");
			//Action class execute the service called by the user
            Action action = null;
			//Serialization class serializes the result of the action in Json format
            Serialization serialization = null;
            
			//traiterRecherche returns the list of results corresponding to the research
            if("traiterRecherche".equals(todo)){
                action = new ActionTraiterRecherche();
                action.executer(request);
                serialization = new SerializationListeURIs();
                serialization.serialiser(request, response);
            
			//getInfos provides all the information relative to the result the user chose to visualise			
            }else if("getInfos".equals(todo)){
                action = new ActionGetInfos();
                action.executer(request);
                serialization = new SerializationInfos();
                serialization.serialiser(request, response);
            
			//getSearchedTerm returns the last term researched by the user
            }else if("getSearchedTerm".equals(todo)){
                action = new ActionGetSearchedTerm();
                action.executer(request);
                serialization = new SerializationGetSearchedTerm();
                serialization.serialiser(request, response);
            
			//getItemSelected returns the URI and category of the result the user chose to visualise
            }else if("getItemSelected".equals(todo)){
                action = new ActionGetItemSelected();
                action.executer(request);
                serialization = new SerializationGetItemSelected();
                serialization.serialiser(request, response);
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }

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
    }

}
