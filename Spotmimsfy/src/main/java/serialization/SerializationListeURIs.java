/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import general.Paire;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author herme
 */
public class SerializationListeURIs extends Serialization{
    
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse reponse) throws IOException {
        Map <String, Paire> listeURIs = (Map)request.getAttribute("listeURIs");
        JsonObject jsonListeResultats = new JsonObject();
        
        if(listeURIs.isEmpty()){
            jsonListeResultats.addProperty("listeVide", true);
        } else {
            jsonListeResultats.addProperty("listeVide", false);
            JsonArray jsonArrayResultats = new JsonArray();
            
            int compteurResultats = 0;
            for(Object mapElement: listeURIs.keySet()){
                JsonObject resultat = new JsonObject();
                String key = mapElement.toString();
                Paire value = listeURIs.get(mapElement);
                resultat.addProperty("URI", key);
                resultat.addProperty("categorie", value.getCategorie());
                resultat.addProperty("distance", value.getDistance());
                resultat.addProperty("reputation", value.getReputation());
                jsonArrayResultats.add(resultat);
                compteurResultats++;
            }
            jsonListeResultats.add("resultats", jsonArrayResultats);
            jsonListeResultats.addProperty("nbrResultats", compteurResultats);
        }
        PrintWriter out = this.getWriterWithJsonHeader(reponse);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(jsonListeResultats, out);
    }
}
