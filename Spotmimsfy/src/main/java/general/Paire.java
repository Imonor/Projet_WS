/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general;

/**
 *
 * @author herme
 */
public class Paire {
    private String categorie;
    private double distance;
    private double reputation;

    public Paire(String categorie, double distance, double reputation) {
        this.categorie = categorie;
        this.distance = distance;
        this.reputation = reputation;
    }

    public String getCategorie() {
        return categorie;
    }

    public double getDistance() {
        return distance;
    }
    
        public double getReputation() {
        return reputation;
    }
    
    
}
