/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class ActionGetSearchedTerm extends Action{
    @Override
    public boolean executer(HttpServletRequest request){
        try{
            HttpSession session = request.getSession(true);
            String recherche = request.getParameter("recherche");            
            session.setAttribute("recherche", recherche);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
