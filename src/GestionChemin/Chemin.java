/**
 * 
 */
package GestionChemin;

import java.util.HashMap;

import tpl.Carte;
import tpl.Case;

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
	protected HashMap<Long, Case> chemin;
	protected Carte carte;
	
	public Chemin(Robot robot, Case destination , Carte carte) {
		// TODO Auto-generated constructor stub
		this.robot = robot;
		this.destination = destination;
		this.carte = carte;
	}
	
	
	

}
