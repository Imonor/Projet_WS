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
    
    public static Map<String, String> getListeURI(String recherche, String path) throws FileNotFoundException{
        
        Map<String, String> hash = new HashMap<>();
        remplirHashmap(hash, "Chanteur", recherche, path);
        remplirHashmap(hash, "Groupe", recherche, path);
        remplirHashmap(hash, "Compositeur", recherche, path);
        remplirHashmap(hash, "Album", recherche, path);
        remplirHashmap(hash, "Chanson", recherche, path);
        
        return hash;
    }
    
    
    protected static void remplirHashmap( Map<String,String> hash, String categorie, String recherche, String path) throws FileNotFoundException{
        
        String nomFichier = "";
        switch (categorie){
            case "Chanteur" :
                nomFichier = path + "Chanteurs.csv";
                break;
            case "Groupe" :
                nomFichier = path + "Groupes.csv";
                break;
            case "Compositeur" :
                nomFichier = path + "Compositeurs.csv";
                break;
            case "Album" :
                nomFichier = path + "Albums.csv";
                break;
            case "Chanson" :
                nomFichier = path + "Chansons.csv";
                break;
        }
        
        try{
            FileInputStream fip = new FileInputStream(nomFichier);
            BufferedReader br = new BufferedReader(new InputStreamReader( fip, StandardCharsets.UTF_8)); 
            String line;
            while ((line = br.readLine()) != null) {
              String[] URIDecoupee = line.split("/"); //pour récupérer les differentes etapes du chemin
              String pageAChercher = URIDecoupee[4]; //On récupère le nom de la page au bout de l'URI
              String lowerCasePageAChercher = pageAChercher.toLowerCase();
              String lowerCaseRecherche = recherche.toLowerCase();
              String[] rechercheDecoupee = lowerCaseRecherche.split(" ");
              boolean aSelectionner = true; //true si l'URL mérite d'être sélectionnée
              
              for (int i = 0; i < rechercheDecoupee.length; i++) {
                  if (!lowerCasePageAChercher.contains(rechercheDecoupee[i])){ //Check si chaque mot de la recherche est bien présent dans le nom de la page
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
    
    public static void main(String[] args) throws FileNotFoundException{ //args[1]: recherche
        Map h = getListeURI("Californication", "");
        for(Object name: h.keySet()){
            String key = name.toString();
            String value = h.get(name).toString();
            System.out.println(key + " , " + value);
        }
    }
}
