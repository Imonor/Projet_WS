package serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ysimiandco
 */
public class SerializationGetItemSelected extends Serialization {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        PrintWriter out = this.getWriterWithJsonHeader(response);
        String URI = (String) session.getAttribute("URI");
        String categorie = (String) session.getAttribute("categorie");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<String> URIAndCategorie = new ArrayList();
        URIAndCategorie.add(URI);
        URIAndCategorie.add(categorie);
        gson.toJson(URIAndCategorie, out);
    }

}