/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import general.AlbumService;
import general.ArtisteService;
import general.ChansonService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author herme
 */
public class ActionGetInfos extends Action {
        @Override
    public boolean executer(HttpServletRequest request){
        try{
            HttpSession session = request.getSession(true);
            String URI = (String) session.getAttribute("URI");
            String categorie = (String) session.getAttribute("categorie");
            
            Map<String, List<String>> infos = null;
            
            switch (categorie){
                case "Album": 
                    infos = AlbumService.getInfosAlbum(URI);
                    break;
                case "Artiste": 
                    infos = ArtisteService.getInfosArtiste(URI);
                    break;
                case "Chanson": 
                    infos = ChansonService.getInfosChanson(URI);
                    break;
                default:
                    System.err.println("erreur catégorie invalide");
                    return false;
            }
            request.setAttribute("infos", infos);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
