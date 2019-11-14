/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;

/**
 *
 * @author Ilan
 */
public class AlbumService {

    public static Map<String, List<String>> getInfosAlbum(String albumURI) {
        Map<String, List<String>> infosAlbum = new HashMap<>();

        infosAlbum.put("title", SPARQLService.getLiteral(albumURI, "dbp:thisAlbum"));
        infosAlbum.put("resume", SPARQLService.getResume(albumURI));
        infosAlbum.put("artist", SPARQLService.getResourceURI(albumURI, "dbo:artist"));
        infosAlbum.put("recordLabel", SPARQLService.getResourceText(albumURI, "dbo:recordLabel"));
        infosAlbum.put("releaseDate", SPARQLService.getLiteral(albumURI, "dbo:releaseDate"));
        infosAlbum.put("runtime", SPARQLService.getLiteral(albumURI, "dbo:runtime"));
        infosAlbum.put("thumbnail", SPARQLService.getResourceURI(albumURI, "dbo:thumbnail"));
        infosAlbum.put("producer", SPARQLService.getResourceText(albumURI, "dbo:producer"));
        infosAlbum.put("awrads", SPARQLService.getLiteral(albumURI, "dbp:award"));
        infosAlbum.put("songTitles", getSongTitles(albumURI));
        infosAlbum.put("genres", SPARQLService.getResourceText(albumURI, "dbo:genre", " LIMIT 5"));

        return infosAlbum;
    }
    
    private static List<String> getSongTitles(String albumURI) {
        String query = "PREFIX dbp: <http://dbpedia.org/property/>\n"
                + "SELECT  ?value\n"
                + "WHERE {\n"
                + "       <" + albumURI + ">\n"
                + "              dbp:title ?value.\n"
                + "}";
        List<QuerySolution> results = SPARQLService.executeQuery(query);
        List<String> values = new ArrayList<>();
        for (QuerySolution line : results) {
            try {
                Resource resource = line.getResource("value");
                values.add(resource.getURI());
            } catch(Exception e) {
                Literal lit = line.getLiteral("value");
                values.add(lit.getString());
            }
        }
        return values;
    }
    
// For test purposes
//    public static void main(String[] args) {
//        Map<String, List<String>> albumInfos = getInfosAlbum("http://dbpedia.org/resource/Thriller_25");
//
//        for (String key : albumInfos.keySet()) {
//            System.out.println(key + " : ");
//            List<String> values = albumInfos.get(key);
//            for (String val : values) {
//                System.out.print(val + ", ");
//            }
//            System.out.println("\n");
//        }
//    }

}
