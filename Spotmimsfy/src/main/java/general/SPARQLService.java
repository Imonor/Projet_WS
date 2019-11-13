package general;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DC;
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
    private static List<String> executeQuery(String stringQuery) {
        // now creating query object
        List<String> youpi = new ArrayList<>();
        Query query = QueryFactory.create(stringQuery);
        // initializing queryExecution factory with remote service.
        // **this actually was the main problem I couldn't figure out.**
        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
        //after it goes standard query execution and result processing which can
        // be found in almost any Jena/SPARQL tutorial.
        try {
            ResultSet results = qexec.execSelect();
            for(; results.hasNext();) {
                youpi.add(results.next().toString());
            }
            // Result processing is done here.
        }
        finally {
           qexec.close();
        }
        
        return youpi;
    }
    
    public static void main(String[] args) {
        String query = "PREFIX dbr:<http://dbpedia.org/resource/>\n" +
        "PREFIX dbo:<http://dbpedia.org/ontology/>\n" +
        "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
        "PREFIX foaf:<http://xmlns.com/foaf/0.1/>\n" +
        "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#/>\n" +
        "SELECT ?x WHERE {\n" +
        "{?x rdf:type dbo:MusicalArtist.}\n" +
        "UNION\n" +
        "{?x rdf:type dbo:Band.}\n" +
        "UNION\n" +
        "{?x rdf:type dbo:Album.}\n" +
        "UNION\n" +
        "{?x rdf:type dbo:Single.}\n" +
        "?x foaf:name ?n.\n" +
        "FILTER (regex(?n, \".*Queen.*\"))\n" +
        "}";
        
        List<String> yes = executeQuery(query);
        
        for(String str : yes) {
            System.out.println(str + ", ");
        }
        System.out.println("SIZE : " + yes.size());
    }
}
