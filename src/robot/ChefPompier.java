/**
 * 
 */
package robot;

import java.util.Iterator;
import java.util.List;

import Evenement.DeplacementRobot;
import Evenement.VerserEau;
import GestionChemin.Chemin;
import tpl.*;


/**
 * @author fezeuyoe
 *
 */
public class ChefPompier {

	/**
	 * 
	 */
	
	protected DonneesSimulation donneesSimulation;
	protected long date;
	//protected List<Incendie> listeIncedie;
	//protected Set<Robot> listeRobot;
	
	//La carte doit contenir une liste des incendies , une liste des robots
	//Il faut ajouter une methode update carte qui change l'état de  la liste des robots ou celles des incendies
	//Il faut aussi ajouter un champ attribué à la classe incendie
	//Il faut aussi ajouter un champ  occupe à la classe robot qui permet de savoir s'il est en train de realiser un evenement ou pas
	
	public ChefPompier(DonneesSimulation donnee/*, Set<Incendie> listeIncendie , Set<Robot> listeRobot*/) {
		// TODO Auto-generated constructor stub
		this.donneesSimulation = donnee;
		this.date =0;

	}
	
	public void parcourListeIncendie() {
		for (Incendie incendie : donneesSimulation.getIncendies()) {
			if(incendie.getStatut() == StatutIncendie.allume) {
				for(Robot robot : donneesSimulation.getRobots()) {
					if(robot.getStatut() == StatutRobot.disponible)  {
						Chemin chemin = new Chemin(robot, incendie.getPosition(), donneesSimulation.getCarte());
						chemin.getCheminOptimal();
						for (Case c : chemin.getChemin()) {
							
						}
						
						
					}
				}
			}
		}
		
	}
	

}
