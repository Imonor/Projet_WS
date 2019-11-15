/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import general.Paire;
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
            Map<String, Paire> URIs = RechercheURIService.getListeURIV2(recherche, this.path);
            for(Object name: URIs.keySet()){
                String key = name.toString();
                String value1 = ((Paire)(URIs.get(name))).getCategorie();
                double value2 = ((Paire)(URIs.get(name))).getDistance();
                double value3 = ((Paire)(URIs.get(name))).getReputation();
                System.out.println(key + " , " + value1 + " , " + value2 + " , " + value3);
            }
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
