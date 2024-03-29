package general;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * ChansonService
 * Provides functions to get all information relative to a music
 */
/**
 * This class contains static functions to get informations about a Song
 *
 * @author Ilan PIPERNO
 */
 
public class ChansonService {
    
    /**
     * This function returns a map containing all the informations we want about a song.
     * @param albumURI
     * @return 
     */
    public static Map<String, List<String>> getInfosChanson(String albumURI) {
        Map<String, List<String>> infosChanson = new HashMap<>();
        
        infosChanson.put("title", SPARQLService.getLiteral(albumURI, "dbp:thisSingle"));
        infosChanson.put("resume", SPARQLService.getResume(albumURI));
        infosChanson.put("artist", SPARQLService.getResourceURI(albumURI, "dbo:musicalArtist"));
        infosChanson.put("album", SPARQLService.getResourceURI(albumURI, "dbo:album"));
        infosChanson.put("recordLabel", SPARQLService.getResourceText(albumURI, "dbo:recordLabel"));
        infosChanson.put("releaseDate", SPARQLService.getLiteral(albumURI, "dbo:releaseDate"));
        infosChanson.put("genres", SPARQLService.getLiteral(albumURI, "dbp:award", " LIMIT 5"));
        infosChanson.put("writer", SPARQLService.getResourceText(albumURI, "dbo:writer"));
        infosChanson.put("runtime", SPARQLService.getLiteral(albumURI, "dbo:runtime"));
        
        return infosChanson;
    }
// For test purposes
//    public static void main(String[] args) {
//        Map<String, List<String>> albumInfos = getInfosChanson("http://dbpedia.org/resource/Toxic_(song)");
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
