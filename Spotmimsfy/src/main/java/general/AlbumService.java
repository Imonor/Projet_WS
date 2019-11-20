package general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;

/*
 * AlbumService
 * Provides functions to get all information relative to an album
 */
/**
 *
 * @author Ilan PIPERNO
 */
 
public class AlbumService {

    /**
     * This function returns a map containing all the informations we want about an album.
     * @param albumURI
     * @return 
     */
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
        infosAlbum.put("name", SPARQLService.getLiteral(albumURI, "foaf:name"));

        return infosAlbum;
    }
    
    
    /**
     * This function returns the list of the song in the album.
     * @param albumURI
     * @return 
     */
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
            // Some songs don't have a dbpedia page, so we have to test if it is a resource, and if not we just take the name of the song.
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
