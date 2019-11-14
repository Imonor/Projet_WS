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
    
    private static final double SEUIL_ERREUR = 0.7;
    
    
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
    
    public static int minDistance(String word1, String word2) {
	int len1 = word1.length();
	int len2 = word2.length();
 
	// len1+1, len2+1, because finally return dp[len1][len2]
	int[][] dp = new int[len1 + 1][len2 + 1];
 
	for (int i = 0; i <= len1; i++) {
		dp[i][0] = i;
	}
 
	for (int j = 0; j <= len2; j++) {
		dp[0][j] = j;
	}
 
	//iterate though, and check last char
	for (int i = 0; i < len1; i++) {
		char c1 = word1.charAt(i);
		for (int j = 0; j < len2; j++) {
			char c2 = word2.charAt(j);
 
			//if last two chars equal
			if (c1 == c2) {
				//update dp value for +1 length
				dp[i + 1][j + 1] = dp[i][j];
			} else {
				int replace = dp[i][j] + 1;
				int insert = dp[i][j + 1] + 1;
				int delete = dp[i + 1][j] + 1;
 
				int min = replace > insert ? insert : replace;
				min = delete > min ? min : delete;
				dp[i + 1][j + 1] = min;
			}
		}
	}
 
	return dp[len1][len2];
    }
    
    protected static void remplirHashmapV2( Map<String, Paire> hash, String categorie, String recherche) throws FileNotFoundException{
        
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
              String pageAChercher = URIDecoupee[4]; //On récupère le nom de la page au bout de l'URI
              String lowerCasePageAChercher = pageAChercher.toLowerCase();
              String lowerCaseRecherche = recherche.toLowerCase();
              String[] rechercheDecoupee = lowerCaseRecherche.split(" ");
              boolean aSelectionner = true; //true si l'URL mérite d'être sélectionnée
              
              double distanceRel = 0;
              for (int i = 0; i < rechercheDecoupee.length; i++) {
                  distanceRel += 1.0*minDistance(lowerCasePageAChercher, rechercheDecoupee[i] )/rechercheDecoupee[i].length();
              }
              
              if( distanceRel <= SEUIL_ERREUR ){ //Si tous les mots sont bien présents on ajoute l'URI et la catégorie à la map

                  Paire paire = new Paire(categorie, distanceRel);   
                  System.out.println( lowerCasePageAChercher + " : " + paire.getDistance() );
                  hash.put(line, paire);
                  
              }
            }
        }catch (Exception e) {
            System.err.println("Error in Afficher Contenu:" + e); 
        }
    }
    
    public static Map<String, Paire> getListeURIV2(String recherche) throws FileNotFoundException{
        
        Map<String, Paire> hash = new HashMap<>();
        remplirHashmapV2(hash, "Chanteur", recherche);
        remplirHashmapV2(hash, "Groupe", recherche);
        remplirHashmapV2(hash, "Compositeur", recherche);
        remplirHashmapV2(hash, "Album", recherche);
        remplirHashmapV2(hash, "Chanson", recherche);
        
        return hash;
    }
    
    
    
    public static void main(String[] args) throws FileNotFoundException{ //args[1]: recherche
        /*
        Map h = getListeURI("Californication");
        for(Object name: h.keySet()){
            String key = name.toString();
            String value = h.get(name).toString();
            System.out.println(key + " , " + value);
        }
        */
        
        Map h = getListeURIV2("Mob");
        for(Object name: h.keySet()){
            String key = name.toString();
            String value1 = ((Paire)(h.get(name))).getText();
            double value2 = ((Paire)(h.get(name))).getDistance();
            System.out.println(key + " , " + value1 + " , " + value2 );
        }
        
    }
}
