/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialization;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ysimiandco
 */
public class SerializationGetSearchedTerm extends Serialization {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        PrintWriter out = this.getWriterWithJsonHeader(response);
        out.println("terme de la recherche: " + session.getAttribute("recherche"));
    }

}
