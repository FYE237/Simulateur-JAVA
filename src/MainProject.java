
import io.LecteurDonnees;
import robot.ChefPompier;
import robot.Drone;
import tpl.Case;
import tpl.DonneesSimulation;
import tpl.Incendie;
import Evenement.*;
import GestionChemin.Chemin;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.zip.DataFormatException;

import gui.GUISimulator;

public class MainProject {
	
	public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }
        
        String nomfichier = args[0];
        
		//String nomfichier = "cartes/carteSujet.map";
		//String nomfichier = "cartes/desertOfDeath-20x20.map";
		//String nomfichier = "cartes/mushroomOfHell-20x20.map";
		//String nomfichier = "cartes/spiralOfMadness-50x50.map";

        
        try {
        	//Creation des données de Simulation
            DonneesSimulation d = LecteurDonnees.creeDonnees(nomfichier);
            
            /*
             * On range les feux dans l'ordre décroissant de leur intensité. 
             * On range les robots dans l'ordre décroissant de leur debit
             */
           
            d.ranger();
            
            ChefPompier chefPompier = new ChefPompier(d);
            
            GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
            
            Simulateur sim = new Simulateur(gui, d);

            //Il parcour la liste des incendie et définie une strategie pour éteindre les incendie
            chefPompier.parcourListeIncendie();
            
            
            //On ajoute les evenements définis par le chef pompier dans la liste des evenements que doit executer notre simulateur
            chefPompier.getListeEvenement().forEach(evenement -> sim.ajouteEvenement(evenement));
            for(long key: sim.getEvents().keySet()) {
            	List<Evenement> list = sim.getEvents().get(key);
                for(Evenement e: list) {
                	if(e.getRobot().getClass() == Drone.class) {
                		System.out.println(e);
                	}
                }
            }
            
           System.out.println("String:"+d.getRobots().get(0).getClass());
            
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + nomfichier + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + nomfichier + " invalide: " + e.getMessage());
        }
    }
	
}
