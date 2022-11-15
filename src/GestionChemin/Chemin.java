/**
 * 
 */
package GestionChemin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import tpl.Carte;
import tpl.Case;
import tpl.Direction;

import robot.Robot;

/**
 * @author fezeuyoe
 * Il s'agit d'une classe qui calcule le chemin optimal pour qu'un robot se deplace d'une case à une autre
 *
 */
public class Chemin {

	/**
	 * 
	 */
	protected Robot robot;
	protected Case  destination;
	protected ArrayList<Case> chemin;
	protected Carte carte;
	
	public Chemin(Robot robot, Case destination , Carte carte) {
		// TODO Auto-generated constructor stub
		this.robot = robot;
		this.destination = destination;
		this.carte = carte;
		
	}
	
	public ArrayList<Case> getChemin() {
		return chemin;
	}
	
	public double getCheminOptimal() {
		
		ArrayList<Case> P = new ArrayList<Case>();
		int nbl = this.carte.getNbLignes(); 
		int nbc = this.carte.getNbColonnes();
		double t[] = new double[nbl*nbc];
		int pred[] = new int[nbl*nbc];
		ArrayList<Case> chemininverse = new ArrayList<Case>();
		double min,tim;
		Case c = null;
		
		Direction[] directions = {Direction.NORD,Direction.SUD,Direction.EST,Direction.OUEST};
		Arrays.fill(t, Double.MAX_VALUE);
		t[this.robot.getPosition().getLigne()*nbc+this.robot.getPosition().getColonne()] = 0;
		
		while(P.size() < nbc*nbl) {
			min = Double.MAX_VALUE;
			for(int i = 0;i<nbc*nbl;i++) {
				if(!P.contains(this.carte.getCase(i/nbc, i%nbc))) {
					if(t[i]<=min) {
						min = t[i];
						c = this.carte.getCase(i/nbc, i%nbc);
					}
				}
			}
			P.add(c);
			if(this.robot.getVitesse(c.getNature())>0){
				tim = this.carte.getTailleCases()/this.robot.getVitesse(c.getNature());
				for(Direction dir : directions) {
					if(this.carte.voisinExiste(c, dir)) {
						if(!P.contains(this.carte.getVoisin(c, dir))) {
							if(t[this.carte.getVoisin(c, dir).getLigne()*nbc+this.carte.getVoisin(c, dir).getColonne()]>t[c.getLigne()*nbc+c.getColonne()] + tim) {
								t[this.carte.getVoisin(c, dir).getLigne()*nbc+this.carte.getVoisin(c, dir).getColonne()] = t[c.getLigne()*nbc+c.getColonne()] + tim;
								pred[this.carte.getVoisin(c, dir).getLigne()*nbc+this.carte.getVoisin(c, dir).getColonne()] = c.getLigne()*nbc+c.getColonne();
							}
						}
					}
				}
			}
		}
		int i = this.destination.getLigne()*nbc+this.destination.getColonne();
		while(i!=this.robot.getPosition().getLigne()*nbc+this.robot.getPosition().getColonne()) {
			chemininverse.add(this.carte.getCase(i/nbc, i%nbc));
			i = pred[i];
		}
		Collections.reverse(chemininverse);
		chemininverse.remove(chemininverse.size()-1);
		this.chemin= chemininverse;
		return t[this.destination.getLigne()*nbc+this.destination.getColonne()];
	}
	
	

}