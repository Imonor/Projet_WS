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
public class ActionTraiterRecherche extends Action{
    @Override
    public boolean executer(HttpServletRequest request){
        try{
            HttpSession session = request.getSession(true);
            String recherche = (String) session.getAttribute("recherche");
            Map<String, List<String>> URIs = new HashMap<>();
            List<String> artistes =  ResearchManager.getArtistes(recherche);
            List<String> albums =  ResearchManager.getAlbums(recherche);
            List<String> chansons =  ResearchManager.getChansons(recherche);
            
            URIs.put("artiste", artistes);
            URIs.put("album", albums);
            URIs.put("chanson", chansons);
            
            request.setAttribute("listeURIs", URIs);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
