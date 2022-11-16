/**
 * 
 */
package Evenement;

import robot.Robot;
import tpl.Carte;


public class RemplirRobot extends Evenement {

	/**
	 * 
	 * @param date
	 * @param robot
	 * @param carte
	 */
	public RemplirRobot(long date , Robot robot,Carte carte) {
		// TODO Auto-generated constructor stub
		super(robot,date);
		this.carte = carte;
	}

	@Override
	public long getDate() {
		// TODO Auto-generated method stub
		return this.date;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		this.robot.remplirReservoir(carte);
	}

}
