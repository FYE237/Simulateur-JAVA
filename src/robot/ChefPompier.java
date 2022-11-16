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

import Evenement.*;
import GestionChemin.Chemin;
import tpl.*;


/**
 * @author fezeuyoe
 *
 */
public class ChefPompier {

	
	
	protected DonneesSimulation donneesSimulation;
	protected long date;
	protected Map<Robot, Long> ListeRobot = new HashMap<Robot, Long>();
	protected List<Evenement> ListeEvenement = new ArrayList<Evenement>();
	protected DonneesSimulation copie;
	protected List<Case> pointsEau = new ArrayList<Case>();
	
	//La carte doit contenir une liste des incendies , une liste des robots
	//Il faut ajouter une methode update carte qui change l'état de  la liste des robots ou celles des incendies
	//Il faut aussi ajouter un champ attribué à la classe incendie
	//Il faut aussi ajouter un champ  occupe à la classe robot qui permet de savoir s'il est en train de realiser un evenement ou pas
	
	/**
	 * 
	 * @param donnee
	 */
	public ChefPompier(DonneesSimulation donnee) {
		// TODO Auto-generated constructor stub
		this.donneesSimulation = donnee;
		this.date =0;
		copie = donneesSimulation.copy();
		pointsEau = donneesSimulation.getPointsEau();
		//fye //copie.getRobots().forEach(robot -> System.out.println(robot.statut));
		for (Robot robot : copie.getRobots()) {
			ListeRobot.put(robot, (long)0);
		}
	}
	
	static Case pointEauOptimal;
	
	public List<Case> getPointEauPProche(Robot robot) {
		double min = Double.MAX_VALUE;
		// !!!!!
		Chemin cheminmin= null;
		double tmp;
		for (Case dest :pointsEau) {
			Chemin chem = new Chemin(robot, dest, copie.getCarte());
			tmp = chem.getCheminOptimal();
			if(tmp < min) {
				min =tmp;
				cheminmin = chem;
				pointEauOptimal = dest;
			}
		}
		System.out.println("Chemin opt pour eau"+cheminmin.getChemin());
		return cheminmin.getChemin();
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
			
		for (Incendie incendie : copie.getIncendies()) {
			if(incendie.getStatut() == StatutIncendie.allume) {						
				int positionIncendie = copie.getIncendies().indexOf(incendie);
				
				int n = copie.getRobots().size(),i=0;
					//for(Robot robot : copie.getRobots()) {
					while(incendie.getIntensite() != 0) {
					this.date = minTime();
					Robot robot = copie.getRobots().get(i);
					if(ListeRobot.containsKey(robot)) {
						date = this.date;
						int position = i;
						System.out.println("11");
						System.out.println(robot);
						System.out.println(this.date);
						System.out.println(ListeRobot.get(robot));
						System.out.println(this.date >= ListeRobot.get(robot));
						System.out.println(robot.getStatut() == StatutRobot.disponible);
						if(this.date >= ListeRobot.get(robot) /*&& robot.getStatut() == StatutRobot.disponible*/)  {
							System.out.println("22");
							if(robot.volumeReservoir != 0) {
								System.out.println("33");
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
										}

											while(robot.volumeReservoir != 0 && incendie.getIntensite() != 0) {
												new VerserEau(date, incendie, robot).execute();
												ListeEvenement.add(new VerserEau(date, donneesSimulation.getIncendies().get(positionIncendie),donneesSimulation.getRobots().get(position)));//On verse de l'eau 
												date = date+ 1;//robot.timeViderReservoir();
											}
											ListeRobot.put(robot, date);

//										ListeRobot.put(robot, date);
										if(robot.volumeReservoir != 0) {
											donneesSimulation.getRobots().get(position).statut =StatutRobot.disponible;
											robot.statut = StatutRobot.disponible;
										}else {
											//ListeRobot.remove(robot);
											List<Case> cheminPointEauPProche = this.getPointEauPProche(robot); 
											for (Case c : cheminPointEauPProche) {
												new DeplacementRobot(date, c, robot,donneesSimulation.getCarte() ).execute();
												ListeEvenement.add(new DeplacementRobot(date, c, donneesSimulation.getRobots().get(position),donneesSimulation.getCarte() ));
												date += 1;//robot.getDureeDeplacement(c, donneesSimulation.getCarte());
											}
											if(robot.getClass() == Drone.class) {
												new DeplacementRobot(date, pointEauOptimal, robot,donneesSimulation.getCarte() ).execute();
												ListeEvenement.add(new DeplacementRobot(date, pointEauOptimal, donneesSimulation.getRobots().get(position),donneesSimulation.getCarte() ));
												date += 1;
											}
											new RemplirRobot(date, robot, donneesSimulation.getCarte()).execute();
											ListeEvenement.add(new RemplirRobot(date, donneesSimulation.getRobots().get(position), donneesSimulation.getCarte()));
											date +=1;
											ListeRobot.put(robot, date);	
										}
									}
								else {
									ListeRobot.put(robot, this.date+1);
									System.out.println(robot + "ne peut pas atteindre "+ incendie.getPosition());
								}
							}else {
								List<Case> cheminPointEauPProche = this.getPointEauPProche(robot); 
								for (Case c : cheminPointEauPProche) {
									new DeplacementRobot(date, c, robot,donneesSimulation.getCarte() ).execute();
									ListeEvenement.add(new DeplacementRobot(date, c, donneesSimulation.getRobots().get(position),donneesSimulation.getCarte() ));
									date += 1;//robot.getDureeDeplacement(c, donneesSimulation.getCarte());
								}
								if(robot.getClass() == Drone.class) {
									new DeplacementRobot(date, pointEauOptimal, robot,donneesSimulation.getCarte() ).execute();
									ListeEvenement.add(new DeplacementRobot(date, pointEauOptimal, donneesSimulation.getRobots().get(position),donneesSimulation.getCarte() ));
									date += 1;
								}
								new RemplirRobot(date, robot, donneesSimulation.getCarte()).execute();
								ListeEvenement.add(new RemplirRobot(date, donneesSimulation.getRobots().get(position), donneesSimulation.getCarte()));
								date +=1;
								ListeRobot.put(robot, date);
							}
					}
				}
				if(incendie.getIntensite() == 0) {
					break;
				}
				i = (i+1)%n;
			 }
			}
		}	
	}
	

}
