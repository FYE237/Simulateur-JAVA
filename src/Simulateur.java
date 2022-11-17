import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;
import gui.ImageElement;
import gui.Oval;
import robot.Robot;
import Evenement.*;
import tpl.*;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


/**
 * Un mini-simulateur...
 * cet objet est associé à une fenêtre graphique GUISimulator, dans laquelle
 * il peut se dessiner.
 * De plus il hérite de Simulable, donc il définit deux méthodes next() et
 * restart() invoquées par la fenêtre graphique de simulation selon les
 * commandes entrées par l'utilisateur.
 */
public class Simulateur implements Simulable {

	/** L'interface graphique associée */
	private GUISimulator gui;


	/** Données de simulation */
	private DonneesSimulation donneesSimulation;
	
	/** Données de sauvegarde*/
	private DonneesSimulation copie;

	/** Taille de la case */
	private int tailleCase ;


	/**Date ou étape courante de la simulation**/
	private long dateSimulation = 0;

	/**Dictionnaire d'évènements à exécuter*/
	private HashMap<Long, List<Evenement>> events = new HashMap<>();
	
	/*Permet de charger les images*/
	private ImageObserver obs = new ImageObserver() {
		public boolean imageUpdate(Image image, int flags, int x, int y, int width, int height) {
			if ((flags & HEIGHT) != 0)
				//System.out.println("Image height = " + height);
			if ((flags & WIDTH) != 0)
				//System.out.println("Image width = " + width);
			if ((flags & FRAMEBITS) != 0)
				System.out.println("Another frame finished.");
			if ((flags & SOMEBITS) != 0)
				//System.out.println("Image section :" + new Rectangle(x, y, width, height));
			if ((flags & ALLBITS) != 0)
				//System.out.println("Image finished!");
			if ((flags & ABORT) != 0)
				System.out.println("Image load aborted...");
			return true;
		}
	};

	/**
	 * Crée un robot et le dessine.
	 * @param gui l'interface graphique associée, dans laquelle se fera le
	 * dessin et qui enverra les messages via les méthodes héritées de
	 * Simulable.
	 */
	public  Simulateur(GUISimulator gui, DonneesSimulation d) {
		this.gui = gui;
		gui.setSimulable(this);				// association a la gui!
		this.donneesSimulation = d;
		this.copie = d.copy();

		draw();
	}


	public HashMap<Long, List<Evenement>> getEvents() {
		return events;
	}
	
	
	/**
	 * Ajoute un évenement au simulateur
	 * @param e
	 */
	public void ajouteEvenement(Evenement e){
		//System.out.println(e.toString());//fye
		if(!this.events.containsKey(e.getDate())) {
			this.events.put(e.getDate(), new ArrayList<Evenement>());
		}
		this.events.get(e.getDate()).add(e);
		
	}


	/**
	 * Dessiner l'environnement
	 */
	private void draw(){
		gui.reset();
		setTailleCase();
		drawCarte(donneesSimulation.getCarte());
		drawListeIncendies(donneesSimulation.getIncendies());
		drawListeRobot(donneesSimulation.getRobots());
	}

	
	/**
	 * Dessiner la liste des incendies
	 * @param incendies
	 */
	private void drawListeIncendies(ArrayList<Incendie> incendies){
		for(Incendie incendie : incendies){
			if(incendie.getIntensite() != 0) {
				gui.addGraphicalElement(new gui.ImageElement( (incendie.getPosition().getColonne()+1) * this.tailleCase  + this.tailleCase/16,(incendie.getPosition().getLigne()+1) * this.tailleCase  + this.tailleCase/16, "ressources/feu.png",7*this.tailleCase/8,7*this.tailleCase/8,this.obs));

			}
		}
	}

	/**
	 * Dessiner la carte
	 * @param carte
	 */
	private void drawCarte(Carte carte){
		for(int i = 0; i < carte.getNbLignes(); i++){
			for(int j = 0; j< carte.getNbColonnes(); j++){
				gui.addGraphicalElement(new gui.ImageElement( this.tailleCase*(j+1),this.tailleCase*(i+1), getFileNameTerrain(carte.getCase(i,j).getNature()),this.tailleCase,this.tailleCase,this.obs));

			}
		}
	}
	
	/**
	 * Si la simulation n'est pas terminée, on effectue tous les évènements de
	 * la date courante et on incrémente la date
	 */
	@Override
	public void next() {
		if(!simulationTerminee()){
			if(this.events.containsKey(this.dateSimulation)) {
				System.out.println("Next - ok ok");
				for(Evenement event : this.events.get(this.dateSimulation)) {
					System.out.println("Exécution");
					System.out.println(event);
					event.execute();
					for(Robot robot: this.donneesSimulation.getRobots()) {
						System.out.println(robot);
					}
				}
			}
			incrementeDate();
		}
		draw();

	}

	/**
	 * Restaure les données initiales et remet la date à 0
	 */
	@Override
	public void restart() {
		RestaureDonnées(this.copie);
		this.dateSimulation = 0;
		draw();
	}
	
	/**
	 * Verifie si la simulation est terminée
	 * */
	private boolean simulationTerminee(){
		boolean flag = true;
		List<Long> dates = new ArrayList<Long>(this.events.keySet());
		for(long date : dates) {
			flag = flag && (this.dateSimulation>date);
		}
		return flag;
	}

	/**
	 * Incremente la date de la simulation si la simulation n'est pas terminée
	 * */
	private void incrementeDate(){
		if(!simulationTerminee()) {
			this.dateSimulation += 1;
		}
	}

	/**
	 * Dessiner les robots
	 * @param robots
	 */
	private void drawListeRobot(ArrayList<Robot> robots){
		for(Robot robot : robots){

			switch(robot.getClass().getName()){
				case "robot.Drone":
					gui.addGraphicalElement(new gui.ImageElement( (robot.getPosition().getColonne()+1) * this.tailleCase  + this.tailleCase/8,(robot.getPosition().getLigne()+1) * this.tailleCase  + this.tailleCase/8, "ressources/drone.png",6*this.tailleCase/8,6*this.tailleCase/8,this.obs));
					//drawRobot(robot,Color.cyan, "DR");
					break;
				case "robot.RobotAChenille":
					gui.addGraphicalElement(new gui.ImageElement( (robot.getPosition().getColonne()+1) * this.tailleCase  + this.tailleCase/8,(robot.getPosition().getLigne()+1) * this.tailleCase  + this.tailleCase/8, "ressources/robotAchenille.png",6*this.tailleCase/8,6*this.tailleCase/8,this.obs));
					break;
				case "robot.RobotAPatte":
					gui.addGraphicalElement(new gui.ImageElement( (robot.getPosition().getColonne()+1) * this.tailleCase  + this.tailleCase/8,(robot.getPosition().getLigne()+1) * this.tailleCase  + this.tailleCase/8, "ressources/robotApatte.png",6*this.tailleCase/8,6*this.tailleCase/8,this.obs));
					break;
				case "robot.RobotARoue":
					gui.addGraphicalElement(new gui.ImageElement( (robot.getPosition().getColonne()+1) * this.tailleCase  + this.tailleCase/8,(robot.getPosition().getLigne()+1) * this.tailleCase  + this.tailleCase/8, "ressources/robotAroue.png",6*this.tailleCase/8,6*this.tailleCase/8,this.obs));
					break;
				default:
					gui.addGraphicalElement(new gui.ImageElement( (robot.getPosition().getColonne()+1) * this.tailleCase  + this.tailleCase/8,(robot.getPosition().getLigne()+1) * this.tailleCase  + this.tailleCase/8, "ressources/robotAroue.png",6*this.tailleCase/8,6*this.tailleCase/8,this.obs));

			}
		}
	}



	/**
	 * Calcule la taille d'une case pour que toute la carte tienne à l'écran
	 */
	private void setTailleCase(){
		if(800 / donneesSimulation.getCarte().getNbColonnes() < 600 / donneesSimulation.getCarte().getNbLignes()){
			this.tailleCase = 800 / (donneesSimulation.getCarte().getNbColonnes() +1);
		}
		else{
			this.tailleCase = 600 /( donneesSimulation.getCarte().getNbLignes() +1);
		}
	}


	/**
	 * Retourne le chemin de l'image correspondant à une nature de terrain
	 * @param nature
	 * @return
	 */
	private String getFileNameTerrain(NatureTerrain nature){
		switch (nature){
			case EAU:
				return "ressources/eau.png";
			case FORET:
				return "ressources/foret.png";
			case ROCHE:
				return "ressources/roche.png";
			case HABITAT:
				return "ressources/habitat.png";
			case TERRAIN_LIBRE:
				return "ressources/terrain_libre.png";
			default:
				return "ressources/terrain_libre.png";

		}
	}
	
	/**
	 * Recrée les données de configuration initiales à partir de la sauvegarde
	 * @param copieDonnées
	 */
	private void RestaureDonnées(DonneesSimulation copieDonnées){
		for(int i=0; i<this.donneesSimulation.getIncendies().size(); i++){
			this.donneesSimulation.getIncendies().get(i).setStatut(this.copie.getIncendies().get(i).getStatut());
			this.donneesSimulation.getIncendies().get(i).setIntensite(this.copie.getIncendies().get(i).getIntensite());
		}
		/*mise à jour des robot*/
		for(int i=0; i<this.donneesSimulation.getRobots().size(); i++){
			this.donneesSimulation.getRobots().get(i).setPosition(this.copie.getCarte(),this.copie.getRobots().get(i).getPosition());
			this.donneesSimulation.getRobots().get(i).setStatut(this.copie.getRobots().get(i).getStatut());
			this.donneesSimulation.getRobots().get(i).setVolumeReservoir();

		}
	}



}