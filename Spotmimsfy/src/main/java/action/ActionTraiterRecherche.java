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


/*
 * ActionGetItemSelected
 * Execute the HTTP request in order to treat the research and return pertinent content
 */
/**
 *
 * @author Guilhem HERMET
 */

public class ActionTraiterRecherche extends Action{
    @Override
    public boolean executer(HttpServletRequest request){
        try{
            HttpSession session = request.getSession(true);
            //recherche: term researched by the user
            String recherche = (String) session.getAttribute("recherche");
            
            //Store the result URIs in 3 lists (artiste, album & chanson)
            Map<String, List<String>> URIs = new HashMap<>();
            
            //Get list of URIs corresponding to artists
            List<String> artistes =  ResearchManager.getArtistes(recherche);
            //Get list of URIs corresponding to albums
            List<String> albums =  ResearchManager.getAlbums(recherche);
            //Get list of URIs corresponding to musics
            List<String> chansons =  ResearchManager.getChansons(recherche);
            
            URIs.put("artiste", artistes);
            URIs.put("album", albums);
            URIs.put("chanson", chansons);
            
            //returns all the information into the request
            request.setAttribute("listeURIs", URIs);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
