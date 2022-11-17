

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

public class TestFinal {
	
	public static void main(String[] args) {
		if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }
		
		String nomFichier = args[0];
        try {
            DonneesSimulation d = LecteurDonnees.creeDonnees(nomFichier);
            d.ranger();
            d.getIncendies().forEach(incendie -> System.out.println(incendie.toString()+"\n"));
            d.getRobots().forEach(incendie -> System.out.println(incendie.toString()+"\n"));
            ChefPompier chefPompier = new ChefPompier(d);
            GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
            Simulateur sim = new Simulateur(gui, d);
            chefPompier.parcourListeIncendie();
            chefPompier.getListeEvenement().forEach(evenement -> evenement.toString());
            System.out.println("Generation d'évenements terminée");
            
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + nomFichier + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + nomFichier + " invalide: " + e.getMessage());
        }
    }
	
}
