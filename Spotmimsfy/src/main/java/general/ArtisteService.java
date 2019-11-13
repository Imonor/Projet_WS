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
import org.apache.jena.query.ResultSet;

/**
 *
 * @author Ilan
 */
public class ArtisteService {

    public static Map<String, List<String>> getInfosArtiste(String artistURI) {
        Map<String, List<String>> infosArtiste = new HashMap<>();
        Map<String, String> infosSimples = getInfosSimplesArtiste(artistURI);
        for (String key : infosSimples.keySet()) {
            List<String> list = new ArrayList<>();
            list.add(infosSimples.get(key));
            infosArtiste.put(key, list);
        }
        infosArtiste.put("genres", getGenres(artistURI));
        infosArtiste.put("bands", getBands(artistURI));
        infosArtiste.put("occupations", getOccupations(artistURI));
        infosArtiste.put("albums", getAlbums(artistURI));

        return infosArtiste;
    }

    private static Map<String, String> getInfosSimplesArtiste(String artisteURI) {
        String query = "PREFIX dbr:<http://dbpedia.org/resource/>\n"
                + "PREFIX dbo:<http://dbpedia.org/ontology/>\n"
                + "PREFIX dbp: <http://dbpedia.org/property/>\n"
                + "PREFIX foaf:<http://xmlns.com/foaf/0.1/>\n"
                + "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#/>\n"
                + "PREFIX dct:<http://purl.org/dc/terms/>\n"
                + "SELECT ?resume\n"
                + "       ?description\n"
                + "       ?birthDate\n"
                + "       ?deathDate\n"
                + "       ?lienImage\n"
                + "WHERE {\n"
                + "       <" + artisteURI + ">n"
                + "              dbo:abstract ?resume;\n"
                + "              dct:description ?description;\n"
                + "              dbp:birthDate ?birthDate;\n"
                + "              dbp:deathDate ?deathDate;\n"
                + "              dbo:thumbnail ?lienImage.\n"
                + "FILTER langMatches(lang(?resume), 'en').\n"
                + "}";
        QuerySolution result = SPARQLService.executeQuery(query).get(0);
        Map<String, String> simpleInfos = new HashMap<>();
        simpleInfos.put("resume", result.getLiteral("resume").getString());
        simpleInfos.put("description", result.getLiteral("description").getString());
        simpleInfos.put("birthDate", result.getLiteral("birthDate").getString());
        simpleInfos.put("deathDate", result.getLiteral("deathDate").getString());
        simpleInfos.put("lienImage", result.getResource("lienImage").getURI());
        return simpleInfos;
    }

    private static List<String> getGenres(String artisteURI) {
        String query = "PREFIX dbr:<http://dbpedia.org/resource/>\n"
                + "PREFIX dbo:<http://dbpedia.org/ontology/>\n"
                + "PREFIX dbprop: <http://dbpedia.org/property/>\n"
                + "PREFIX foaf:<http://xmlns.com/foaf/0.1/>\n"
                + "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#/>\n"
                + "PREFIX dct:<http://purl.org/dc/terms/>\n"
                + "SELECT ?genre\n"
                + "WHERE {\n"
                + "       <" + artisteURI + ">\n"
                + "              dbo:genre ?genre.\n"
                + "} LIMIT 5";
        List<QuerySolution> results = SPARQLService.executeQuery(query);
        List<String> genres = new ArrayList<>();
        for (QuerySolution line : results) {
            genres.add(line.getResource("genre").getLocalName().replace("_", " "));
        }

        return genres;
    }

    private static List<String> getBands(String artisteURI) {
        String query = "PREFIX dbr:<http://dbpedia.org/resource/>\n"
                + "PREFIX dbo:<http://dbpedia.org/ontology/>\n"
                + "PREFIX dbprop: <http://dbpedia.org/property/>\n"
                + "PREFIX foaf:<http://xmlns.com/foaf/0.1/>\n"
                + "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#/>\n"
                + "PREFIX dct:<http://purl.org/dc/terms/>\n"
                + "SELECT ?band\n"
                + "WHERE {\n"
                + "       <" + artisteURI + ">\n"
                + "               dbo:associatedBand ?band.\n"
                + "}";

        List<QuerySolution> results = SPARQLService.executeQuery(query);
        List<String> bands = new ArrayList<>();
        for (QuerySolution line : results) {
            bands.add(line.getResource("band").getURI());
        }

        return bands;
    }

    private static List<String> getOccupations(String artisteURI) {
        String query = "PREFIX dbr:<http://dbpedia.org/resource/>\n"
                + "PREFIX dbo:<http://dbpedia.org/ontology/>\n"
                + "PREFIX dbp: <http://dbpedia.org/property/>\n"
                + "PREFIX foaf:<http://xmlns.com/foaf/0.1/>\n"
                + "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#/>\n"
                + "PREFIX dct:<http://purl.org/dc/terms/>\n"
                + "SELECT ?occupation\n"
                + "WHERE {\n"
                + "       <" + artisteURI + ">\n"
                + "               dbp:occupation ?occupation.\n"
                + "} LIMIT 3";

        List<QuerySolution> results = SPARQLService.executeQuery(query);
        List<String> occupations = new ArrayList<>();
        for (QuerySolution line : results) {
            occupations.add(line.getLiteral("occupation").getString());
        }

        return occupations;
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
//        getAlbums("http://dbpedia.org/resource/Michael_Jackson");
//    }

}
