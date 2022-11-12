import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;
import robot.Robot;
import tpl.*;

import java.awt.*;
import java.util.ArrayList;
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

	/** abscisses courant  */
	private int x;

	/** ordonnee courant */
	private int y;


	/** Itérateur sur les abcisses du robot au cours du temps */
	private Iterator<Integer> xIterator;

	/** Itérateur sur les ordonnées du robot au cours du temps */
	private Iterator<Integer> yIterator;

	/**Date ou étape courante de la simulation**/
	private long dateSimuation = 0;

	/*Liste d'évènements à exéccuter*/
	private ArrayList<Evenement> events = new ArrayList<Evenement>();

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
	private void planCoordinates(int abscisseInitial, int abscisseFinal, int ordonneeInitial, int ordonneeFinal, int taillecase) {
		// panel must be large enough... unchecked here!
		// total invader size: height == 120, width == 80

		int xInitial = abscisseInitial * taillecase;
		int yInitial = ordonneeInitial * taillecase;
		int xFinal = abscisseFinal * taillecase;
		int yFinal = ordonneeFinal * taillecase;

		// let's plan the invader displacement!
		java.util.List<Integer> xCoords = new ArrayList<Integer>();
		List<Integer> yCoords = new ArrayList<Integer>();

		// going right
		if(xInitial < xFinal){
			for (int x = xInitial; x <= xFinal; x += taillecase/10) {
				xCoords.add(x);
				yCoords.add(yInitial);
			}
		}

		// going left
		if(xInitial > xFinal){
			for (int x = xInitial; x >= xFinal; x -= taillecase/10) {
				xCoords.add(x);
				yCoords.add(yInitial);
			}
		}

		// going down
		if(yInitial < yFinal){
			for (int y = yInitial; y <= yFinal; y += taillecase/10) {
				xCoords.add(xFinal);
				yCoords.add(y);
			}
		}

		// going up
		if(yInitial > yFinal){
			for (int y = yInitial; y >= yFinal; y -= taillecase/10) {
				xCoords.add(xFinal);
				yCoords.add(y);
			}
		}


		this.xIterator = xCoords.iterator();
		this.yIterator = yCoords.iterator();
		// current position
		this.x = xInitial;
		this.y = yInitial;
	}
	@Override
	public void next() {
		if (this.xIterator.hasNext())
			this.x = this.xIterator.next();
		if (this.yIterator.hasNext())
			this.y = this.yIterator.next();
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




	/**
	 * Dessiner une case
	 * @param cote
	 * @param colors
	 * @param abscisse
	 * @param ordonnee
	 */
	private void drawCase(int cote, Color colors, int abscisse, int ordonnee){
		for(int j=0; j<cote;  j+=10) {
			for (int i = 0; i < cote; i += 10) {
				gui.addGraphicalElement(new gui.Rectangle(abscisse + i, ordonnee + j, colors, colors, 10));
			}
		}

	}

	/*Ajouter un evènement*/
	private void ajouteEvenement(Evenement e){
		this.events.add(e);
	}

	/*Verifie si la simulation est terminée*/
	private boolean simulationTerminee(){
		return this.dateSimuation == this.events.size();
	}

	/*Incremente la date de la simulation*/
	private void incrementeDate(){
		if(!simulationTerminee()) {
			this.dateSimuation += 1;
		}
	}


	/**
	 * Dessiner la liste des incendies
	 * @param incendies
	 */
	private void drawListeIncendies(ArrayList<Incendie> incendies){
		for(Incendie incendie : incendies){
			if(incendie.getIntensite() != 0) {
				drawIncendie(this.tailleCase / 2, Color.red, incendie.getPosition().getLigne() * this.tailleCase, incendie.getPosition().getColonne() * this.tailleCase);
			}
		}
	}


	/**
	 * Dessiner un incendie
	 * @param cote
	 * @param colors
	 * @param abscisse
	 * @param ordonnee
	 */

	private void drawIncendie(int cote, Color colors, int abscisse, int ordonnee){
		//gui.reset();
		for(int j=0; j<cote;  j+=10) {
			for (int i = 0; i < cote; i += 10) {
				gui.addGraphicalElement(new Rectangle(abscisse + i + this.tailleCase/4, ordonnee + j + this.tailleCase/4, colors, colors, 10));
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
				drawCase(this.tailleCase, getColorTerrain(carte.getCase(i,j).getNature()),this.tailleCase*i, this.tailleCase*j );
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
					drawRobot(robot,Color.cyan, "DR");
					break;
				case "robot.RobotAChenille":
					drawRobot(robot, Color.pink, "RAC");
					break;
				case "robot.RobotAPatte":
					drawRobot(robot, Color.magenta, "RAP");
					break;
				case "robot.RobotARoue":
					drawRobot(robot, Color.lightGray, "RAR");
					break;
				default:
					drawRobot(robot, Color.black,"RAS");

			}
		}
	}


	/**
	 * Dessine un robot.
	 * @param robot
	 * @param color
	 * @param name
	 */
	private void drawRobot(Robot robot, Color color, String name) {

		for(int j=0; j<this.tailleCase/2;  j+=10) {
			for (int i = 0; i < this.tailleCase/2; i += 10) {
				gui.addGraphicalElement(new Rectangle(robot.getPosition().getLigne()*this.tailleCase + i + this.tailleCase/4, robot.getPosition().getColonne()*this.tailleCase + j + this.tailleCase/4, color, color, 10));
			}
		}
		gui.addGraphicalElement(new Text(robot.getPosition().getLigne()*this.tailleCase + tailleCase/2 , robot.getPosition().getColonne()*this.tailleCase  + this.tailleCase/2 , Color.black, name));

	}

	/** calcul de la taille d'une case */
	public void setTailleCase(){
		if(800 / donneesSimulation.getCarte().getNbColonnes() < 600 / donneesSimulation.getCarte().getNbLignes()){
			this.tailleCase = 800 / donneesSimulation.getCarte().getNbColonnes();
		}
		else{
			this.tailleCase = 600 / donneesSimulation.getCarte().getNbLignes();
		}
	}

	/**
	 * rechercher la couleur d'un terrain
	 * @param nature
	 * @return
	 */
	private Color getColorTerrain(NatureTerrain nature){
		switch (nature){
			case EAU:
				return Color.BLUE;
			case FORET:
				return Color.green;
			case ROCHE:
				return Color.gray;
			case HABITAT:
				return Color.orange;
			case TERRAIN_LIBRE:
				return Color.white;
			default:
				return Color.black;

		}
	}


}
