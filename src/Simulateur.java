

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;
import tpl.DonneesSimulation;
import tpl.NatureTerrain;

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

	/** La couleur de dessin du robot */
	private Color robotColor;

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


	/**
	 * Crée un robot et le dessine.
	 * @param gui l'interface graphique associée, dans laquelle se fera le
	 * dessin et qui enverra les messages via les méthodes héritées de
	 * Simulable.
	 */
	public  Simulateur(GUISimulator gui, Color robotColor) {
		this.gui = gui;
		gui.setSimulable(this);				// association a la gui!
		this.robotColor = robotColor;

		planCoordinates(5,1,7,2,75);
		draw();
	}

	/**
	 * Programme les déplacements de le robot.
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
		planCoordinates(5,1,7,2,75);
		draw();
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
	 * Dessine le robot.
	 */
	private void draw(){
		gui.reset();
		drawCase(80,getColor(NatureTerrain.EAU),10,10);
		drawIncendie(40,Color.red,30,30);
		drawRobot();
	}

	private Color getColor(NatureTerrain nature){
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
				return Color.white;

		}
	}

	private void drawCase(int cote, Color colors, int abscisse, int ordonnee){
		//gui.reset();
		for(int j=0; j<cote;  j+=10) {
			for (int i = 0; i < cote; i += 10) {
				gui.addGraphicalElement(new gui.Rectangle(abscisse + i, ordonnee + j, colors, colors, 10));
			}
		}

	}

	private void drawIncendie(int cote, Color colors, int abscisse, int ordonnee){
		//gui.reset();
		for(int j=0; j<cote;  j+=10) {
			for (int i = 0; i < cote; i += 10) {
				gui.addGraphicalElement(new Rectangle(abscisse + i, ordonnee + j, colors, colors, 10));
			}
		}
	}

	/**
	 * Dessine le robot.
	 */
	private void drawRobot() {
		gui.addGraphicalElement(new Rectangle(x + 30, y, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 40, y, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 50, y, robotColor, robotColor, 10));

		gui.addGraphicalElement(new Rectangle(x + 20, y + 10, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 30, y + 10, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 40, y + 10, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 50, y + 10, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 60, y + 10, robotColor, robotColor, 10));

		gui.addGraphicalElement(new Rectangle(x + 10, y + 20, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 20, y + 20, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 30, y + 20, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 40, y + 20, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 50, y + 20, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 60, y + 20, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 70, y + 20, robotColor, robotColor, 10));

		gui.addGraphicalElement(new Rectangle(x, y + 30, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 10, y + 30, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 40, y + 30, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 70, y + 30, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 80, y + 30, robotColor, robotColor, 10));

		gui.addGraphicalElement(new Rectangle(x, y + 40, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 10, y + 40, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 40, y + 40, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 70, y + 40, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 80, y + 40, robotColor, robotColor, 10));

		gui.addGraphicalElement(new Rectangle(x, y + 50, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 10, y + 50, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 20, y + 50, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 30, y + 50, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 40, y + 50, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 50, y + 50, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 60, y + 50, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 70, y + 50, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 80, y + 50, robotColor, robotColor, 10));

		gui.addGraphicalElement(new Rectangle(x + 20, y + 60, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 30, y + 60, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 40, y + 60, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 50, y + 60, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 60, y + 60, robotColor, robotColor, 10));

		gui.addGraphicalElement(new Rectangle(x + 10, y + 70, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 20, y + 70, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 30, y + 70, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 40, y + 70, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 50, y + 70, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 60, y + 70, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 70, y + 70, robotColor, robotColor, 10));

		gui.addGraphicalElement(new Rectangle(x + 10, y + 80, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 20, y + 80, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 30, y + 80, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 40, y + 80, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 50, y + 80, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 60, y + 80, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 70, y + 80, robotColor, robotColor, 10));

		gui.addGraphicalElement(new Rectangle(x, y + 90, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 20, y + 90, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 60, y + 90, robotColor, robotColor, 10));
		gui.addGraphicalElement(new Rectangle(x + 80, y + 90, robotColor, robotColor, 10));

		gui.addGraphicalElement(new Text(x + 40, y + 120, robotColor, "L'INVADER"));


	}

}