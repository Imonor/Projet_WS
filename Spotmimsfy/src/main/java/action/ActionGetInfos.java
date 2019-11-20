package action;

import general.AlbumService;
import general.ArtisteService;
import general.ChansonService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*
 * ActionGetInfos
 * Execute the HTTP request in order to get all the information relative to the result chose by the user
 */
/**
 *
 * @author Ilan PIPERNO
 */

public class ActionGetInfos extends Action {
        @Override
    public boolean executer(HttpServletRequest request){
        try{
            HttpSession session = request.getSession(true);
            //URI: the URI of the result chose by the user
            String URI = (String) session.getAttribute("URI");
            //categorie: the type of the result (artist, album or music)
            String categorie = (String) session.getAttribute("categorie");
            
            //To store the information 
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
            
            //returns all the information into the request
            request.setAttribute("infos", infos);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
