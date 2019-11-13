/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import javax.servlet.http.HttpServletRequest;
import general.RechercheURIService;
import java.util.Map;


/**
 *
 * @author herme
 */
public class ActionTraiterRecherche extends Action{
    @Override
    public boolean executer(HttpServletRequest request){
        try{
            String recherche = request.getParameter("recherche");
            Map<String, String> URIs = RechercheURIService.getListeURI(recherche);
            request.setAttribute("listeURIs", URIs);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
