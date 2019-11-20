package general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.jena.query.QuerySolution;

/*
 * ArtisteService
 * Provides functions to get all information relative to an artist
 */
/**
 * This class contains static functions to get informations about an Artist
 *
 * @author Ilan PIPERNO
 */
 
public class ArtisteService {

    /**
     * This function returns a map containing all the informations we want about an artist.
     * 
     * @param artistURI
     * @return Map<String, List<String>>
     */
    public static Map<String, List<String>> getInfosArtiste(String artistURI) {
        Map<String, List<String>> infosArtiste = new HashMap<>();

        infosArtiste.put("description", SPARQLService.getLiteral(artistURI, "dct:description"));
        infosArtiste.put("birthDate", SPARQLService.getLiteral(artistURI, "dbo:birthDate", " LIMIT 1"));
        infosArtiste.put("deathDate", SPARQLService.getLiteral(artistURI, "dbo:deathDate", " LIMIT 1"));
        infosArtiste.put("thumbnail", SPARQLService.getResourceURI(artistURI, "dbo:thumbnail"));
        infosArtiste.put("resume",SPARQLService.getResume(artistURI));
        infosArtiste.put("genres", SPARQLService.getResourceText(artistURI, "dbo:genre", " LIMIT 5"));
        infosArtiste.put("bands", SPARQLService.getResourceURI(artistURI, "dbo:associatedBand"));
        infosArtiste.put("occupations", SPARQLService.getLiteral(artistURI, "dbp:occupation", " LIMIT 3"));
        infosArtiste.put("albums", getAlbums(artistURI));
        infosArtiste.put("name", SPARQLService.getLiteral(artistURI, "foaf:name"));

        return infosArtiste;
    }

    private static List<String> getAlbums(String artisteURI) {
        String query = "PREFIX dbr:<http://dbpedia.org/resource/>\n"
                + "PREFIX dbo:<http://dbpedia.org/ontology/>\n"
                + "PREFIX dbp: <http://dbpedia.org/property/>\n"
                + "PREFIX foaf:<http://xmlns.com/foaf/0.1/>\n"
                + "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#/>\n"
                + "PREFIX dct:<http://purl.org/dc/terms/>\n"
                + "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "SELECT ?album\n"
                + "WHERE {\n"
                + "    ?album rdf:type dbo:Album.\n"
                + "    ?album dbo:artist <" + artisteURI + ">.\n"
                + "}";

        List<QuerySolution> results = SPARQLService.executeQuery(query);
        List<String> albums = new ArrayList<>();
        for (QuerySolution line : results) {
            albums.add(line.getResource("album").getURI());
        }

        return albums;
    }

// For Test purposes
//    public static void main(String[] args) {
//        Map<String, List<String>> albumInfos = getInfosArtiste("http://dbpedia.org/resource/Kanye_West");
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
