/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author herme
 */
public class SerializationInfos extends Serialization{
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse reponse) throws IOException {
        
        Map<String, List<String>> infos = (Map)request.getAttribute("infos");
        PrintWriter out = this.getWriterWithJsonHeader(reponse);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(infos, out);
    }
}
