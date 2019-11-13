package general;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author herme
 */
public class RechercheURIService {
    private static final String CHEMIN_FIC_CHANTEURS = "src/main/java/database/Chanteurs.csv";
    private static final String CHEMIN_FIC_COMPOSITEURS = "src/main/java/database/Compositeurs.csv";
     private static final String CHEMIN_FIC_GROUPES = "src/main/java/database/Groupes.csv";
    private static final String CHEMIN_FIC_ALBUMS = "src/main/java/database/Albums.csv";
    private static final String CHEMIN_FIC_CHANSONS = "src/main/java/database/Chansons.csv";
    
    public static Map<String, String> getListeURI(String recherche) throws FileNotFoundException{
        
        Map<String, String> hash = new HashMap<>();
        remplirHashmap(hash, "Chanteur", recherche);
        remplirHashmap(hash, "Groupe", recherche);
        remplirHashmap(hash, "Compositeur", recherche);
        remplirHashmap(hash, "Album", recherche);
        remplirHashmap(hash, "Chanson", recherche);
        
        return hash;
    }
    
    
    protected static void remplirHashmap( Map<String,String> hash, String categorie, String recherche) throws FileNotFoundException{
        
        String nomFichier = "";
        switch (categorie){
            case "Chanteur" :
                nomFichier = CHEMIN_FIC_CHANTEURS;
                break;
            case "Groupe" :
                nomFichier = CHEMIN_FIC_GROUPES;
                break;
            case "Compositeur" :
                nomFichier = CHEMIN_FIC_COMPOSITEURS;
                break;
            case "Album" :
                nomFichier = CHEMIN_FIC_ALBUMS;
                break;
            case "Chanson" :
                nomFichier = CHEMIN_FIC_CHANSONS;
                break;
        }
        
        try{
            FileInputStream fip = new FileInputStream(nomFichier);
            BufferedReader br = new BufferedReader(new InputStreamReader( fip, StandardCharsets.UTF_8)); 
            String line;
            while ((line = br.readLine()) != null) {
              String[] URIDecoupee = line.split("/"); //pour récupérer les differentes etapes du chemin
              String pageAChercher = URIDecoupee[2]; //On récupère le nom de la page au bout de l'URI
              pageAChercher.toLowerCase();
              recherche.toLowerCase();
              String[] rechercheDecoupee = recherche.split(" ");
              boolean aSelectionner = true; //true si l'URL mérite d'être sélectionnée
              
              for (int i = 0; i < rechercheDecoupee.length; i++) {
                  if (!pageAChercher.contains(rechercheDecoupee[i])){ //Check si chaque mot de la recherche est bien présent dans le nom de la page
                      aSelectionner = false;
                      break;
                  }
              }
              if(aSelectionner){ //Si tous les mots sont bien présents on ajoute l'URI et la catégorie à la map
                  hash.put(line, categorie);
              }
            }
        }catch (Exception e) {
            System.err.println("Error in Afficher Contenu:" + e); 
        }
    }
}
