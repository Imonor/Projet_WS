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
 * SerializationInfos
 * Serialize the response to GetInfos request
 */
/**
 *
 * @author Guilhem HERMET
 */

public class SerializationInfos extends Serialization{
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse reponse) throws IOException {
        
        //Get attributes to return
        Map<String, List<String>> infos = (Map)request.getAttribute("infos");
        PrintWriter out = this.getWriterWithJsonHeader(reponse);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        //Serialize the result
        gson.toJson(infos, out);
    }
}
