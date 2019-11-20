package serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * SerializationGetSearchedTerm
 * Serialize the response to GetSearchedTerm request
 */
/**
 *
 * @author Antony MARTIN
 */

public class SerializationGetSearchedTerm extends Serialization {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        PrintWriter out = this.getWriterWithJsonHeader(response);
        
        //Get attributes to return
        String recherche = (String) session.getAttribute("recherche");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        //Serialize the result
        gson.toJson(recherche, out);
    }

}
