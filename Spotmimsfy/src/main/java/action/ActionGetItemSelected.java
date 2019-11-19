
package action;

import javax.servlet.http.HttpServletRequest;
import general.ResearchManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;


/**
 *
 * @author herme
 */
public class ActionGetItemSelected extends Action{
    @Override
    public boolean executer(HttpServletRequest request){
        try{
            HttpSession session = request.getSession(true);
            String URI = request.getParameter("URI");   
            String categorie = request.getParameter("categorie");            
            session.setAttribute("URI", URI);       
            session.setAttribute("categorie", categorie);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}