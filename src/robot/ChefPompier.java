/**
 * 
 */
package robot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import Evenement.DeplacementRobot;
import Evenement.Evenement;
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
	protected Map<Robot, Long> ListeRobot = new HashMap<Robot, Long>();
	protected List<Evenement> ListeEvenement = new ArrayList<Evenement>();
	protected DonneesSimulation copie;
	
	//La carte doit contenir une liste des incendies , une liste des robots
	//Il faut ajouter une methode update carte qui change l'état de  la liste des robots ou celles des incendies
	//Il faut aussi ajouter un champ attribué à la classe incendie
	//Il faut aussi ajouter un champ  occupe à la classe robot qui permet de savoir s'il est en train de realiser un evenement ou pas
	
	public ChefPompier(DonneesSimulation donnee/*, Set<Incendie> listeIncendie , Set<Robot> listeRobot*/) {
		// TODO Auto-generated constructor stub
		this.donneesSimulation = donnee;
		this.date =0;
		copie = donneesSimulation.copy();
		//fye //copie.getRobots().forEach(robot -> System.out.println(robot.statut));
		for (Robot robot : copie.getRobots()) {
			ListeRobot.put(robot, (long)0);
		}
	}
	
	public List<Evenement> getListeEvenement() {
		return this.ListeEvenement;
	}
	
	public long minTime() {
		if(ListeRobot.size() == 0) return 0;
		else {
			long min = Collections.min(ListeRobot.values());
			return min;
		}
		
	}
	
	public void parcourListeIncendie() {
		long date = 0;
		Chemin chemin;
		double timedeplacement;
		Boolean checkIncendie = true;
		//fye
//		while(checkIncendie) {
//			checkIncendie = false;
			
				for (Incendie incendie : copie.getIncendies()) {
					if(incendie.getStatut() == StatutIncendie.allume) {
						//fye
//						checkIncendie = true;
						
						int positionIncendie = copie.getIncendies().indexOf(incendie);
						this.date = minTime();
						for(Robot robot : copie.getRobots()) {
							date = this.date;
							int position = copie.getRobots().indexOf(robot);
							 //System.out.println(position+"\n");
		
							if(this.date >= ListeRobot.get(robot) && robot.getStatut() == StatutRobot.disponible)  {
								if(robot.volumeReservoir != 0) {
									chemin = new Chemin(robot, incendie.getPosition(), donneesSimulation.getCarte());
									timedeplacement = chemin.getCheminOptimal();
									if(timedeplacement != Double.MAX_VALUE) {
											for (Case c : chemin.getChemin()) {
												new DeplacementRobot(date, c, robot,donneesSimulation.getCarte() ).execute();
												ListeEvenement.add(new DeplacementRobot(date, c, donneesSimulation.getRobots().get(position),donneesSimulation.getCarte() ));
												date += 1;//robot.getDureeDeplacement(c, donneesSimulation.getCarte());
											}
											if(robot.getClass() == Drone.class) {
												new DeplacementRobot(date, incendie.getPosition(), robot,donneesSimulation.getCarte() ).execute();
												ListeEvenement.add(new DeplacementRobot(date, donneesSimulation.getIncendies().get(positionIncendie).getPosition(), donneesSimulation.getRobots().get(position),donneesSimulation.getCarte() ));
												date += 1;//robot.getDureeDeplacement(incendie.getPosition(), donneesSimulation.getCarte());
												while(incendie.getIntensite() != 0) {
													new VerserEau(date, incendie, robot).execute();
													ListeEvenement.add(new VerserEau(date, donneesSimulation.getIncendies().get(positionIncendie),donneesSimulation.getRobots().get(position)));//On verse de l'eau 
													date = date+ 1;//robot.timeViderReservoir();
												}
											}
											else {
												while(robot.volumeReservoir != 0 || incendie.getIntensite() != 0) {
													new VerserEau(date, incendie, robot).execute();
													ListeEvenement.add(new VerserEau(date, donneesSimulation.getIncendies().get(positionIncendie),donneesSimulation.getRobots().get(position)));//On verse de l'eau 
													date = date+ 1;//robot.timeViderReservoir();
												}
											}
											ListeRobot.put(robot, date);
											if(robot.volumeReservoir != 0) {
												donneesSimulation.getRobots().get(position).statut =StatutRobot.disponible;
												robot.statut = StatutRobot.disponible;
											}
										}
								}
						}
						if(incendie.getIntensite() == 0) {
							break;
						}
					 }
					}
				}
	//fye	
//		}
	}
	

}
