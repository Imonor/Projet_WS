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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author herme
 */
public class SerializationListeURIs extends Serialization{
    
    public void serialiser(HttpServletRequest request, HttpServletResponse reponse) throws IOException {
        Map <String, String> listeURIs = (Map)request.getAttribute("listeURIs");
        JsonObject jsonListeResultats = new JsonObject();
        
        if(listeURIs.isEmpty()){
            jsonListeResultats.addProperty("listeVide", true);
        } else {
            jsonListeResultats.addProperty("listeVide", false);
            JsonArray jsonArrayResultats = new JsonArray();
            
            for(Object mapElement: listeURIs.keySet()){
                JsonObject resultat = new JsonObject();
                String key = mapElement.toString();
                String value = listeURIs.get(mapElement);
                resultat.addProperty("URI", key);
                resultat.addProperty("categorie", value);
                jsonArrayResultats.add(resultat);
            }
            jsonListeResultats.add("resultats", jsonArrayResultats);
        }
        PrintWriter out = this.getWriterWithJsonHeader(reponse);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(jsonListeResultats, out);
    }
}
