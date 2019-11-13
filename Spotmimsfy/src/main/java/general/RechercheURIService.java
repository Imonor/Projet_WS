package general;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public static Map<String, String> getListeURI(String recherche){
        
        Map<String, String> hash = new HashMap<>();
        
        
        
        return hash;
    }
    
    protected void remplirHashmap( Map<String,String> hash, String categorie) throws FileNotFoundException{
        
        String nomFichier;
        switch (categorie){
            case "Chanteur" :
                nomFichier = "Chanteurs.txt";
                break;
            case "Groupe" :
                nomFichier = "Groupes.txt";
                break;
            case "Compositeur" :
                nomFichier = "Compositeurs.txt";
                break;
            case "Album" :
                nomFichier = "Albums.txt";
                break;
            case "Chanson" :
                nomFichier = "Chansons.txt";
                break;
        }
        
        try{
            FileInputStream fip = new FileInputStream(nomFichier);
            BufferedReader br = new BufferedReader(new InputStreamReader( fip, StandardCharsets.UTF_8)); 
            String line;
            while ((line = br.readLine()) != null) {
//                if(line.equals())
            }
        }catch (Exception e) {
                System.err.println("Error in Afficher Contenu:" + e); 
        }
    }
}
