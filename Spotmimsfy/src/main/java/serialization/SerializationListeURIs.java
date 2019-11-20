package serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * SerializationListeURIs
 * Serialize the response to TraiterRecherche request
 */
/**
 *
 * @author Guilhem HERMET
 */

public class SerializationListeURIs extends Serialization{
    
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse reponse) throws IOException {
        
        //Get attributes to return
        Map <String, List<String>> listeURIs = (Map)request.getAttribute("listeURIs");
        PrintWriter out = this.getWriterWithJsonHeader(reponse);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        //Serialize the result
        gson.toJson(listeURIs, out);
    }
}
