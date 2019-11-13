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

        infosAlbum.put("title", getLiteral(albumURI, "dbp:thisAlbum"));
        infosAlbum.put("resume", getResume(albumURI));
        infosAlbum.put("artist", getResourceURI(albumURI, "dbo:artist"));
        infosAlbum.put("recordLabel", getResourceText(albumURI, "dbo:recordLabel"));
        infosAlbum.put("releaseDate", getLiteral(albumURI, "dbo:releaseDate"));
        infosAlbum.put("runtime", getLiteral(albumURI, "dbo:runtime"));
        infosAlbum.put("thumbnail", getResourceURI(albumURI, "dbo:thumbnail"));
        infosAlbum.put("producer", getResourceText(albumURI, "dbo:producer"));
        infosAlbum.put("awrads", getLiteral(albumURI, "dbp:award"));
        infosAlbum.put("songTitles", getSongTitles(albumURI));
        infosAlbum.put("genres", getResourceText(albumURI, "dbo:genre"));

        return infosAlbum;
    }

    private static List<String> getLiteral(String albumURI, String literalName) {
        String query = "PREFIX dbr:<http://dbpedia.org/resource/>\n"
                + "PREFIX dbo:<http://dbpedia.org/ontology/>\n"
                + "PREFIX dbp: <http://dbpedia.org/property/>\n"
                + "PREFIX foaf:<http://xmlns.com/foaf/0.1/>\n"
                + "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#/>\n"
                + "PREFIX dct:<http://purl.org/dc/terms/>\n"
                + "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "SELECT  ?value\n"
                + "WHERE {\n"
                + "       <" + albumURI + ">\n"
                + "              " + literalName + " ?value.\n"
                + "}";
        List<QuerySolution> results = SPARQLService.executeQuery(query);
        List<String> values = new ArrayList<>();
        for (QuerySolution line : results) {
            values.add(line.getLiteral("value").getString());
        }
        return values;
    }

    private static List<String> getResourceText(String albumURI, String resourceName) {
        String query = "PREFIX dbr:<http://dbpedia.org/resource/>\n"
                + "PREFIX dbo:<http://dbpedia.org/ontology/>\n"
                + "PREFIX dbp: <http://dbpedia.org/property/>\n"
                + "PREFIX foaf:<http://xmlns.com/foaf/0.1/>\n"
                + "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#/>\n"
                + "PREFIX dct:<http://purl.org/dc/terms/>\n"
                + "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "SELECT  ?value\n"
                + "WHERE {\n"
                + "       <" + albumURI + ">\n"
                + "              " + resourceName + " ?value.\n"
                + "}";
        List<QuerySolution> results = SPARQLService.executeQuery(query);
        List<String> values = new ArrayList<>();
        for (QuerySolution line : results) {
            String[] yay = line.getResource("value").toString().split("/");
            System.out.println(yay[yay.length-1].replace("_", " "));
            values.add(line.getResource("value").getLocalName().replace("_", " "));
        }
        return values;
    }

    private static List<String> getResourceURI(String albumURI, String resourceName) {
        String query = "PREFIX dbr:<http://dbpedia.org/resource/>\n"
                + "PREFIX dbo:<http://dbpedia.org/ontology/>\n"
                + "PREFIX dbp: <http://dbpedia.org/property/>\n"
                + "PREFIX foaf:<http://xmlns.com/foaf/0.1/>\n"
                + "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#/>\n"
                + "PREFIX dct:<http://purl.org/dc/terms/>\n"
                + "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "SELECT  ?value\n"
                + "WHERE {\n"
                + "       <" + albumURI + ">\n"
                + "              " + resourceName + " ?value.\n"
                + "}";
        List<QuerySolution> results = SPARQLService.executeQuery(query);
        List<String> values = new ArrayList<>();
        for (QuerySolution line : results) {
            values.add(line.getResource("value").getURI());
        }
        return values;
    }
    
    private static List<String> getResume(String albumURI) {
        String query = "PREFIX dbo:<http://dbpedia.org/ontology/>\n"
                + "SELECT  ?value\n"
                + "WHERE {\n"
                + "       <" + albumURI + ">\n"
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
    

    public static void main(String[] args) {
        Map<String, List<String>> albumInfos = getInfosAlbum("");

        for (String key : albumInfos.keySet()) {
            System.out.println(key + " : ");
            List<String> values = albumInfos.get(key);
            for (String val : values) {
                System.out.print(val + ", ");
            }
            System.out.println("\n");
        }
    }

}
