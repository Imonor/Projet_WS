package general;

import java.util.ArrayList;
import java.util.List;
import org.apache.jena.query.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author herme
 */
public class SPARQLService {

    public static List<QuerySolution> executeQuery(String stringQuery) {
        // now creating query object
        List<QuerySolution> solutions = new ArrayList<>();
        Query query = QueryFactory.create(stringQuery);
        // initializing queryExecution factory with remote service.
        // **this actually was the main problem I couldn't figure out.**
        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
        //after it goes standard query execution and result processing which can
        // be found in almost any Jena/SPARQL tutorial.
        try {
            ResultSet results = qexec.execSelect();
            for (; results.hasNext();) {
                solutions.add(results.next());
            }
            // Result processing is done here.
        } finally {
            qexec.close();
        }
        return solutions;
    }

    public static List<String> getLiteral(String URI, String literalName) {
        return getLiteral(URI, literalName, "");
    }
    public static List<String> getLiteral(String URI, String literalName, String addOn) {
        String query = "PREFIX dbr:<http://dbpedia.org/resource/>\n"
                + "PREFIX dbo:<http://dbpedia.org/ontology/>\n"
                + "PREFIX dbp: <http://dbpedia.org/property/>\n"
                + "PREFIX foaf:<http://xmlns.com/foaf/0.1/>\n"
                + "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#/>\n"
                + "PREFIX dct:<http://purl.org/dc/terms/>\n"
                + "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "SELECT  ?value\n"
                + "WHERE {\n"
                + "       <" + URI + ">\n"
                + "              " + literalName + " ?value.\n"
                + "}" + addOn;
        List<QuerySolution> results = SPARQLService.executeQuery(query);
        List<String> values = new ArrayList<>();
        for (QuerySolution line : results) {
            values.add(line.getLiteral("value").getString());
        }
        return values;
    }

    public static List<String> getResourceText(String URI, String resourceName) {
        return getResourceText(URI, resourceName, "");
    }
    public static List<String> getResourceText(String URI, String resourceName, String addOn) {
        String query = "PREFIX dbr:<http://dbpedia.org/resource/>\n"
                + "PREFIX dbo:<http://dbpedia.org/ontology/>\n"
                + "PREFIX dbp: <http://dbpedia.org/property/>\n"
                + "PREFIX foaf:<http://xmlns.com/foaf/0.1/>\n"
                + "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#/>\n"
                + "PREFIX dct:<http://purl.org/dc/terms/>\n"
                + "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "SELECT  ?value\n"
                + "WHERE {\n"
                + "       <" + URI + ">\n"
                + "              " + resourceName + " ?value.\n"
                + "}" + addOn;
        List<QuerySolution> results = SPARQLService.executeQuery(query);
        List<String> values = new ArrayList<>();
        for (QuerySolution line : results) {
            String[] yay = line.getResource("value").toString().split("/");
            values.add(yay[yay.length-1].replace("_", " "));
        }
        return values;
    }

    public static List<String> getResourceURI(String URI, String resourceName) {
        return getResourceURI(URI, resourceName, "");
    }
    public static List<String> getResourceURI(String URI, String resourceName, String addOn) {
        String query = "PREFIX dbr:<http://dbpedia.org/resource/>\n"
                + "PREFIX dbo:<http://dbpedia.org/ontology/>\n"
                + "PREFIX dbp: <http://dbpedia.org/property/>\n"
                + "PREFIX foaf:<http://xmlns.com/foaf/0.1/>\n"
                + "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#/>\n"
                + "PREFIX dct:<http://purl.org/dc/terms/>\n"
                + "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "SELECT  ?value\n"
                + "WHERE {\n"
                + "       <" + URI + ">\n"
                + "              " + resourceName + " ?value.\n"
                + "}" + addOn;
        List<QuerySolution> results = SPARQLService.executeQuery(query);
        List<String> values = new ArrayList<>();
        for (QuerySolution line : results) {
            values.add(line.getResource("value").getURI());
        }
        return values;
    }
    
    
    public static List<String> getResume(String URI) {
        String query = "PREFIX dbo:<http://dbpedia.org/ontology/>\n"
                + "SELECT  ?value\n"
                + "WHERE {\n"
                + "       <" + URI + ">\n"
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

//    public static void main(String[] args) {
//        String query = "PREFIX dbr:<http://dbpedia.org/resource/>\n"
//                + "PREFIX dbo:<http://dbpedia.org/ontology/>\n"
//                + "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
//                + "PREFIX foaf:<http://xmlns.com/foaf/0.1/>\n"
//                + "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#/>\n"
//                + "SELECT ?x WHERE {\n"
//                + "{?x rdf:type dbo:MusicalArtist.}\n"
//                + "UNION\n"
//                + "{?x rdf:type dbo:Band.}\n"
//                + "UNION\n"
//                + "{?x rdf:type dbo:Album.}\n"
//                + "UNION\n"
//                + "{?x rdf:type dbo:Single.}\n"
//                + "?x foaf:name ?n.\n"
//                + "FILTER (regex(?n, \".*Queen.*\"))\n"
//                + "}";
//
//        List<String> yes = executeQuery(query);
//
//        for (String str : yes) {
//            System.out.println(str + ", ");
//        }
//        System.out.println("SIZE : " + yes.size());
//    }
}
