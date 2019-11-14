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

/**
 *
 * @author Ilan
 */
public class ArtisteService {

    public static Map<String, List<String>> getInfosArtiste(String artistURI) {
        Map<String, List<String>> infosArtiste = new HashMap<>();

        infosArtiste.put("description", SPARQLService.getLiteral(artistURI, "dct:description"));
        infosArtiste.put("birthDate", SPARQLService.getLiteral(artistURI, "dbp:birthDate"));
        infosArtiste.put("deathDate", SPARQLService.getLiteral(artistURI, "dbp:deathDate"));
        infosArtiste.put("thumbnail", SPARQLService.getResourceURI(artistURI, "dbo:thumbnail"));
        infosArtiste.put("resume",getResume(artistURI));
        infosArtiste.put("genres", SPARQLService.getResourceText(artistURI, "dbo:genre", " LIMIT 5"));
        infosArtiste.put("bands", SPARQLService.getResourceURI(artistURI, "dbo:associatedBand"));
        infosArtiste.put("occupations", SPARQLService.getLiteral(artistURI, "dbp:occupation", " LIMIT 3"));
        infosArtiste.put("albums", getAlbums(artistURI));

        return infosArtiste;
    }
    private static List<String> getResume(String artisteURI) {
        String query = "PREFIX dbo:<http://dbpedia.org/ontology/>\n"
                + "SELECT  ?value\n"
                + "WHERE {\n"
                + "       <" + artisteURI + ">\n"
                + "              dbo:abstract ?value.\n"
                + "FILTER langMatches(lang(?value), 'en')\n"
                + "}";
        List<QuerySolution> results = SPARQLService.executeQuery(query);
        List<String> values = new ArrayList<>();
        for (QuerySolution line : results) {
            values.add(line.getLiteral("value").getString());
        }
        return values;
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
//        Map<String, List<String>> albumInfos = getInfosArtiste("http://dbpedia.org/resource/Michael_Jackson");
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
