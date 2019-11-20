package serialization;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Serialization
 * Serialize the response to a request into Json format
 */
/**
 *
 * @author Guilhem HERMET
 */

public abstract class Serialization {
    
    //Configure the HTTP response headers to indicate the returned returned format
    protected PrintWriter getWriterWithJsonHeader(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        return out;
    }
    
    //Serialize the response
    public abstract void serialiser(HttpServletRequest request, HttpServletResponse reponse) throws IOException;
}
