
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
//        if (args.length < 1) {
//            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
//            System.exit(1);
//        }
		//String nomfichier = "cartes/carteSujet.map";
		//String nomfichier = "cartes/desertOfDeath-20x20.map";
		String nomfichier = "cartes/mushroomOfHell-20x20.map";
		//String nomfichier = "cartes/spiralOfMadness-50x50.map";
		//System.out.println("Working Directory = " + System.getProperty("user.dir")); 
        try {
            DonneesSimulation d = LecteurDonnees.creeDonnees(nomfichier);
            //d.ranger();
            d.getIncendies().forEach(incendie -> System.out.println(incendie.toString()+"\n"));
            d.getRobots().forEach(incendie -> System.out.println(incendie.toString()+"\n"));
            ChefPompier chefPompier = new ChefPompier(d);
            GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
            Simulateur sim = new Simulateur(gui, d);
//            sim.ajouteEvenement(new DeplacementRobot(0,d.getCarte().getCase(7, 1), d.getRobots().get(0),d.getCarte()));
//            sim.ajouteEvenement(new VerserEau(1,d.getIncendies().get(1),d.getRobots().get(0)));
//            sim.ajouteEvenement(new DeplacementRobot(2,d.getCarte().getCase(0, 0), d.getRobots().get(0),d.getCarte()));
//            Chemin chemin = new Chemin(d.getRobots().get(0),d.getCarte().getCase(0, 0),d.getCarte());
//            System.out.println("Temps optimal: "+chemin.getCheminOptimal());
//            for(Case cas: chemin.getChemin()) {
//            	System.out.println(cas);
//            }
//            for (long name: sim.getEvents().keySet()) {
//                String value = sim.getEvents().get(name).toString();
//                System.out.println(name + " " + value);
//            }
            System.out.println("1");
            chefPompier.parcourListeIncendie();
            System.out.println("2");
            chefPompier.getListeEvenement().forEach(evenement -> evenement.toString());
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
