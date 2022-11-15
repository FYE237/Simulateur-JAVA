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

	/** Taille de la case */
	private int tailleCase ;


	/**Date ou étape courante de la simulation**/
	private long dateSimulation = 0;

	/*Liste d'évènements à exécuter*/
	private HashMap<Long, List<Evenement>> events = new HashMap<>();

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

		draw();
	}


	/**
	 * Programme les déplacements de le robot.
	 * @param abscisseInitial
	 * @param abscisseFinal
	 * @param ordonneeInitial
	 * @param ordonneeFinal
	 * @param taillecase
	 */
	@Override
	public void next() {
		System.out.println("Next");
		if(!simulationTerminee()){
			System.out.println("Next - ok");
			if(this.events.containsKey(this.dateSimulation)) {
				System.out.println("Next - ok ok");
				for(Evenement event : this.events.get(this.dateSimulation)) {
					System.out.println("Exécution");
					System.out.println(this.donneesSimulation.getIncendies().get(1).getIntensite());
					event.execute();
					System.out.println(this.donneesSimulation.getIncendies().get(1).getIntensite());
				}
			}
			incrementeDate();
		}
		draw();

	}

	@Override
	public void restart() {
		draw();
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
	
	

	public HashMap<Long, List<Evenement>> getEvents() {
		return events;
	}


	/*Ajouter un evènement*/
	public void ajouteEvenement(Evenement e){
		if(!this.events.containsKey(e.getDate())) {
			this.events.put(e.getDate(), new ArrayList<Evenement>());
		}
		this.events.get(e.getDate()).add(e);
	}

	/*Verifie si la simulation est terminée*/
	private boolean simulationTerminee(){
		boolean flag = true;
		List<Long> dates = new ArrayList<Long>(this.events.keySet());
		for(long date : dates) {
			flag = flag && (this.dateSimulation>date);
		}
		return flag;
	}

	/*Incremente la date de la simulation*/
	private void incrementeDate(){
		if(!simulationTerminee()) {
			this.dateSimulation += 1;
		}
	}


	
	ImageObserver obs = new ImageObserver() {
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
				//gui.addGraphicalElement(new gui.Rectangle( this.tailleCase*(j+1) ,this.tailleCase*(i+1) , Color.black, getColorTerrain(carte.getCase(i,j).getNature()),this.tailleCase));
				gui.addGraphicalElement(new gui.ImageElement( this.tailleCase*(j+1),this.tailleCase*(i+1), getFileNameTerrain(carte.getCase(i,j).getNature()),this.tailleCase,this.tailleCase,this.obs));

			}
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



	/** calcul de la taille d'une case */
	public void setTailleCase(){
		if(800 / donneesSimulation.getCarte().getNbColonnes() < 600 / donneesSimulation.getCarte().getNbLignes()){
			this.tailleCase = 800 / (donneesSimulation.getCarte().getNbColonnes() +1);
		}
		else{
			this.tailleCase = 600 /( donneesSimulation.getCarte().getNbLignes() +1);
		}
	}


	/**
	 * rechercher la couleur d'un terrain
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


}