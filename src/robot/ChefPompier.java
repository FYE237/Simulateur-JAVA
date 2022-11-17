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


	//Variable qui contient les données de simulation
	protected DonneesSimulation donneesSimulation;

	//Variable date
	protected long date;

	//Map qui contient les robots ainsi que la date de son dernier evenement
	protected Map<Robot, Long> ListeRobot = new HashMap<Robot, Long>();

	//Une liste des evements qui doivent s'executer
	protected List<Evenement> ListeEvenement = new ArrayList<Evenement>();

	//Une copie des données de simulation sur laquelle nous effectuons nos différents calculs
	protected DonneesSimulation copie;

	//Une liste des différents poitns d'eau présents sur notre carte
	protected List<Case> pointsEau = new ArrayList<Case>();



	/**
	 * 
	 * @param donnee : Donnée de simulation
	 */
	public ChefPompier(DonneesSimulation donnee) {
		// TODO Auto-generated constructor stub
		this.donneesSimulation = donnee;
		this.date =0;

		//On crée une copie des données de simulation
		copie = donneesSimulation.copy();

		//On stocke l'ensemble de tous les points d'eau de notre carte
		pointsEau = donneesSimulation.getPointsEau();

		//Onn initialise notre Map avec des couples (Robot, 0) 
		for (Robot robot : copie.getRobots()) {
			ListeRobot.put(robot, (long)0);
		}
	}


	/**
	 * Cette variable stocke pour un robot à une date t  le point d'eau le plus proche de lui
	 */
	static Case pointEauOptimal; 

	/**
	 * 
	 * @param robot
	 * @return Le chemin optimal vers la source d'eau la plus proche pour un robot
	 */
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
				pointEauOptimal = dest;//la case du point d'eau le plus proche
			}
		}

		//On retourne un chemin vers cette case
		return cheminmin.getChemin();
	}

	/**
	 * 
	 * @return  List<Evenement>
	 * Retourne la liste des evenements à executer
	 */
	public List<Evenement> getListeEvenement() {
		return this.ListeEvenement;
	}


	/**
	 * Methode qui calcule le plus petit temps parmi les differents temps des evenements effectués par nos robots dans 
	 * la Map listeRobot
	 */
	public long minTime() {
		if(ListeRobot.size() == 0) return 0;
		else {
			long min = Collections.min(ListeRobot.values());
			return min;
		}

	}


	/**
	 * Methode qui parcour la liste des incendies, et definie une stratégie d'affectation des robots pour pouvoir 
	 * éteindre ces incendies de facon optimale 
	 */
	public void parcourListeIncendie() {
		//Un compteur de temps 
		long date = 0;

		//Un chemin
		Chemin chemin;

		//Durée d'un deplacement
		double timedeplacement;


		//Parcours de la liste des incendies
		for (Incendie incendie : copie.getIncendies()) {

			//Lorsqu'on trouve un feu allumé
			if(incendie.getStatut() == StatutIncendie.allume) {	

				//On stocke la position de ce feu dans la liste des incendie
				int positionIncendie = copie.getIncendies().indexOf(incendie);

				// n =nombre de robots, i : compteur sur la liste de robots 
				int n = copie.getRobots().size(),i=0;

				//Tant que l'incendie n'est pas éteint
				while(incendie.getIntensite() != 0) {

					//La date courante est le min de toutes les dates des evenements effectué par les robots à l'étape précedente
					this.date = minTime();

					Robot robot = copie.getRobots().get(i);//On recuperer le robot i da la liste des robots
					date = this.date;
					int position = i;

					/*
					 * On s'assure qu'un robot est disponible. Donc il n'est pas en train d'être utilisé par un évenement. Donc la date de son dernier evenement
					 * doit être <= à la date courante
					 *	
					 */

					if(this.date >= ListeRobot.get(robot) )  {

						//Si le reservoi du robot n'est pas vide
						if(robot.volumeReservoir != 0) { 

							chemin = new Chemin(robot, incendie.getPosition(), donneesSimulation.getCarte());

							//Calcul du chemin optimal pour deplacer notre robot de sa case jusqu'à la position de l'incendie
							timedeplacement = chemin.getCheminOptimal();

							//Si on a pu trouvé un chemin optimal (Si le robot a pu se déplacer)
							if(timedeplacement != Double.MAX_VALUE) {

								date = this.executeSuiteEvenementPeauPProche(chemin, position, robot, incendie, positionIncendie);
								//Mise à jour date de dernier évenement de notre robot
								ListeRobot.put(robot, date);


								if(robot.volumeReservoir != 0) {

									donneesSimulation.getRobots().get(position).statut =StatutRobot.disponible;
									robot.statut = StatutRobot.disponible;

								}
								else {

									/*
									 * Si son réservoir est vide, notre robot doit aller se remplir
66									 * Calcul de chemin à parcourir pour aller au point d'eau le plus proche
									 */

									List<Case> cheminPointEauPProche = this.getPointEauPProche(robot); 

									date = this.executeSerieEvenement(date, cheminPointEauPProche, robot, position);
									ListeRobot.put(robot, date);	
								}
							}
							else {
								ListeRobot.put(robot, this.date+1);
							}
						}
						else { //Si le reservoir du robot est vide
							List<Case> cheminPointEauPProche = this.getPointEauPProche(robot); 

							date = executeSerieEvenement(date, cheminPointEauPProche, robot, position);
							ListeRobot.put(robot, date);
						}
					}

					if(incendie.getIntensite() == 0) {
						break;
					}
					//On passe au prochain robot;
					i = (i+1)%n;
				}
			}
		}	
	}

	/**
	 * 
	 * @param date
	 * @param cheminPointEauPProche :Chemin vers les points d'eau les plus proches d'un robot
	 * @param robot
	 * @param position : position du robot dans la copie de notre liste des robots
	 * @return date : La date à laquelle cette série d'evenement se termine
	 */
	public long executeSerieEvenement(long date,List<Case> cheminPointEauPProche,Robot robot,int position) {
		for (Case c : cheminPointEauPProche) {
			//On execute la suite de deplacement pour suivre le chemin calculé
			new DeplacementRobot(date, c, robot,donneesSimulation.getCarte() ).execute();
			ListeEvenement.add(new DeplacementRobot(date, c, donneesSimulation.getRobots().get(position),donneesSimulation.getCarte() ));
			date += 1;

		}
		//Si c'est un drone il y a déplacement supplémentaire pour se mettre au dessus de la case destination
		if(robot.getClass() == Drone.class) {

			new DeplacementRobot(date, pointEauOptimal, robot,donneesSimulation.getCarte() ).execute();
			ListeEvenement.add(new DeplacementRobot(date, pointEauOptimal, donneesSimulation.getRobots().get(position),donneesSimulation.getCarte() ));
			date += 1;

		}

		//Le robot se remplit
		new RemplirRobot(date, robot, donneesSimulation.getCarte()).execute();
		ListeEvenement.add(new RemplirRobot(date, donneesSimulation.getRobots().get(position), donneesSimulation.getCarte()));
		date +=1;

		return date;
	}

	/**
	 * 
	 * @param chemin : Un chemin ie une suite de case 
	 * @param position
	 * @param robot
	 * @param incendie
	 * @param positionIncendie
	 * @return date : La date à laquelle cette série d'evenement se termine
	 */
	public long executeSuiteEvenementPeauPProche(Chemin chemin,int position, Robot robot,Incendie incendie,int positionIncendie) {
		for (Case c : chemin.getChemin()) { // Parcours de la liste des cases de notre chemin

			new DeplacementRobot(date, c, robot,donneesSimulation.getCarte() ).execute();
			ListeEvenement.add(new DeplacementRobot(date, c, donneesSimulation.getRobots().get(position),donneesSimulation.getCarte() ));
			date += 1;

		}

		if(robot.getClass() == Drone.class) {

			new DeplacementRobot(date, incendie.getPosition(), robot,donneesSimulation.getCarte() ).execute();
			ListeEvenement.add(new DeplacementRobot(date, donneesSimulation.getIncendies().get(positionIncendie).getPosition(), donneesSimulation.getRobots().get(position),donneesSimulation.getCarte() ));
			date += 1;

		}
		/*
		 * Tant que son reservoir n'est pas vide ou que le feu n'a pas été eteint	
		 */
		while(robot.volumeReservoir != 0 && incendie.getIntensite() != 0) { 
			new VerserEau(date, incendie, robot).execute();
			ListeEvenement.add(new VerserEau(date, donneesSimulation.getIncendies().get(positionIncendie),donneesSimulation.getRobots().get(position)));//On verse de l'eau 
			date = date+ 1;
		}

		return date;
	}


}
