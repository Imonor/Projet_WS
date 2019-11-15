/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import general.Paire;
import javax.servlet.http.HttpServletRequest;
import general.RechercheURIService;
import general.ResearchManager;
import java.util.HashMap;
import java.util.List;
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
    
    public ActionTraiterRecherche(String path){
        super(path);
    }
}
