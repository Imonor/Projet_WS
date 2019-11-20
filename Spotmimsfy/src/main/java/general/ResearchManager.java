package general;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.apache.jena.query.QuerySolution;

/*
 * ResearchManager
 * Treats the research of the user to provide him pertinent content
 */
/**
 *
 * @author Ilan PIPERNO
 */
 
public class ResearchManager {

/**
 * This function returns all the Artists that match the name the user searcehes.
 * 
 * @param recherche
 * @return
 */
    public static List<String> getArtistes(String recherche) {
        String query = "PREFIX dbr:<http://dbpedia.org/resource/>\n"
                + "PREFIX dbo:<http://dbpedia.org/ontology/>\n"
                + "PREFIX foaf:<http://xmlns.com/foaf/0.1/>\n"
                + "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#/>\n"
                + "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "SELECT distinct ?artist (count(DISTINCT ?album) as ?count) WHERE {\n"
                + "{?artist rdf:type dbo:MusicalArtist.}\n"
                + "UNION\n"
                + "{?artist rdf:type dbo:Band.}\n"
                + "UNION\n"
                + "{?artist rdf:type dbo:Musician.}\n"
                + "UNION\n"
                + "{?artist rdf:type dbo:Rappers.}\n"
                + "UNION\n"
                + "{?artist rdf:type dbo:Composer.}\n"
                + "?artist foaf:name ?n.\n"
                + "?album rdf:type dbo:Album.\n"
                + "?album dbo:artist ?artist.\n"
                + "FILTER CONTAINS(UCASE(?n), UCASE(\"" + recherche + "\"))\n"
                + "}GROUP BY ?artist\n"
                + "ORDER BY DESC(?count)";

        List<QuerySolution> results = SPARQLService.executeQuery(query);
        List<String> lines = new ArrayList<>();
        for (QuerySolution res : results) {
            String artisteURI = res.getResource("artist").getURI();
            lines.add(artisteURI);
        }
        return lines;
    }

    /**
     * This function returns all the Albums that match the name the user searcehes.
     * @param recherche
     * @return 
     */
    public static List<String> getAlbums(String recherche) {
        String query = "PREFIX dbr:<http://dbpedia.org/resource/>\n"
                + "PREFIX dbo:<http://dbpedia.org/ontology/>\n"
                + "PREFIX foaf:<http://xmlns.com/foaf/0.1/>\n"
                + "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#/>\n"
                + "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "SELECT DISTINCT ?album (count(DISTINCT ?albumcount) as ?count) WHERE {\n"
                + "   ?album rdf:type dbo:Album;\n"
                + "   dbo:artist ?artist;\n"
                + "   foaf:name ?n.\n"
                + "   ?albumcount rdf:type dbo:Album;\n"
                + "   dbo:artist ?artist.\n"
                + "   FILTER CONTAINS(UCASE(?n), UCASE(\"" + recherche + "\"))\n"
                + "} GROUP BY ?album\n"
                + "ORDER BY DESC(?count)\n"
                + "LIMIT 50";

        List<QuerySolution> results = SPARQLService.executeQuery(query);
        List<String> lines = new ArrayList<>();
        for (QuerySolution res : results) {
            String albumURI = res.getResource("album").getURI();
            lines.add(albumURI);
        }
        return lines;
    }
    
     /**
     * This function returns all the Songs or Singles that match the name the user searcehes.
     * @param recherche
     * @return 
     */  
    public static List<String> getChansons(String recherche) {
        String query = "PREFIX dbr:<http://dbpedia.org/resource/>\n"
                + "PREFIX dbo:<http://dbpedia.org/ontology/>\n"
                + "PREFIX foaf:<http://xmlns.com/foaf/0.1/>\n"
                + "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#/>\n"
                + "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "SELECT DISTINCT ?chanson (count(DISTINCT ?album) as ?count) WHERE {\n"
                + "   {?chanson rdf:type dbo:Single.}\n"
                + "   UNION\n"
                + "   {?chanson rdf:type dbo:Song.}\n"
                + "   ?chanson dbo:musicalArtist ?artist;\n"
                + "   foaf:name ?n.\n"
                + "   ?album rdf:type dbo:Album;\n"
                + "   dbo:artist ?artist.\n"
                + "   FILTER CONTAINS(UCASE(?n), UCASE(\"" + recherche + "\"))\n"
                + "} GROUP BY ?chanson\n"
                + "ORDER BY DESC(?count)\n"
                + "LIMIT 50";

        List<QuerySolution> results = SPARQLService.executeQuery(query);
        List<String> lines = new ArrayList<>();
        for (QuerySolution res : results) {
            String albumURI = res.getResource("chanson").getURI();
            lines.add(albumURI);
        }
        return lines;
    }

// For test purposes    
//    public static void main(String[] args) throws FileNotFoundException {
//        List<String> results = getChansons("like a love song");
//
//        for (String elem : results) {
//            System.out.println(elem);
//        }
//    }

}
