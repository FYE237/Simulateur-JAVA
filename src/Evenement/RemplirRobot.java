/**
 * 
 */
package Evenement;

import robot.Robot;
import tpl.Carte;

/**
 * @author fezeuyoe
 *
 */
public class RemplirRobot extends Evenement {

	/**
	 * 
	 */
		
	
	
	public RemplirRobot(long date , Robot robot,Carte carte) {
		// TODO Auto-generated constructor stub
		super(robot,date);
		this.carte = carte;
	}

	@Override
	public long getDate() {
		// TODO Auto-generated method stub
		return date;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		robot.remplirReservoir(carte);
	}

}
