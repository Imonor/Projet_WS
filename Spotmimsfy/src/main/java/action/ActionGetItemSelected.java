
package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*
 * ActionGetItemSelected
 * Execute the HTTP request in order to get all the URI and category of the last result consulted by the user
 */
/**
 *
 * @author Nada LASRI
 */

public class ActionGetItemSelected extends Action{
    @Override
    public boolean executer(HttpServletRequest request){
        try{
            HttpSession session = request.getSession(true);
            //URI: the URI of the result chose by the user
            String URI = request.getParameter("URI");   
            //categorie: the type of the result (artist, album or music)
            String categorie = request.getParameter("categorie"); 
            
            //returns all the information into the request
            session.setAttribute("URI", URI);       
            session.setAttribute("categorie", categorie);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}