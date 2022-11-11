
import io.LecteurDonnees;
import tpl.DonneesSimulation;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import gui.GUISimulator;

public class MainProject {
	
	public static void main(String[] args) {
//        if (args.length < 1) {
//            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
//            System.exit(1);
//        }
		String nomfichier = "codeEtudiants/cartes/carteSujet.map";
		//System.out.println("Working Directory = " + System.getProperty("user.dir")); 
        try {
            DonneesSimulation d = LecteurDonnees.creeDonnees(nomfichier);
            GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
            Simulateur sim = new Simulateur(gui, d);
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + nomfichier + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + nomfichier + " invalide: " + e.getMessage());
        }
    }
	
}
