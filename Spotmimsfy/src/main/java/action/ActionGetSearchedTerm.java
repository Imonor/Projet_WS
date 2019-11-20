package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/*
 * ActionGetSearchedTerm
 * Execute the HTTP request in order to get the last term searched by the user
 */
/**
 *
 * @author Antony MARTIN
 */

public class ActionGetSearchedTerm extends Action{
    @Override
    public boolean executer(HttpServletRequest request){
        try{
            HttpSession session = request.getSession(true);
            //recherche: term researched by the user
            String recherche = request.getParameter("recherche");    
            
            //returns all the information into the request
            session.setAttribute("recherche", recherche);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
