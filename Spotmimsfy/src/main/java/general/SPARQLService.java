package general;

import java.util.ArrayList;
import java.util.List;
import org.apache.jena.query.*;

/*
 * SPARQL Service
 * Provides functions to create and execute queries with Jena library
 */
/**
 *
 * @author Ilan PIPERNO
 */
public class SPARQLService {

    /**
     * This function will execute the SPARQL query in parameter to dbpedia then return all the results.
     * 
     * @param stringQuery
     * @return List<QuerySolution
     */
    public static List<QuerySolution> executeQuery(String stringQuery) {
        List<QuerySolution> solutions = new ArrayList<>();
        Query query = QueryFactory.create(stringQuery);
        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
        try {
            ResultSet results = qexec.execSelect();
            for (; results.hasNext();) {
                solutions.add(results.next());
            }
        } finally {
            qexec.close();
        }
        return solutions;
    }

    public static List<String> getLiteral(String URI, String literalName) {
        return getLiteral(URI, literalName, "");
    }
    /**
     * This function will execute the request to get the Literal property "literalName" of the resource "URI".
     * @param URI
     * @param literalName
     * @param addOn (for example a LIMIT)
     * @return 
     */
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
    
    /**
     * This function will execute the request to get the name of the Resource property "resourceName" of the resource "URI".
     * @param URI
     * @param resourceName
     * @param addOn (for example LIMIT)
     * @return 
     */
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
    
    /**
     * This function will execute the request to get the URI of the Resource property "resourceName" of the resource "URI".
     * @param URI
     * @param resourceName
     * @param addOn (for example LIMIT)
     * @return 
     */
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
    
    /**
     * This function will return a description of the artist, album or song.
     * We made a function for that because we had to check the language was English.
     * @param URI
     * @return 
     */
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

    // For test purposes
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
