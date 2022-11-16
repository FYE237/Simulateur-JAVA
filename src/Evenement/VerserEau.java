/**
 * 
 */
package Evenement;

import robot.Robot;
import tpl.Incendie;


public class VerserEau extends Evenement {

	/*
	 * Incendie c'est l'incendie à eteindre
	 * 
	 */

	private Incendie incendie;


	/**
	 * 
	 * @param date
	 * @param destination
	 * @param robot
	 */
	public VerserEau(long date , Incendie destination, Robot robot) {
		// TODO Auto-generated constructor stub
		super(robot,date);
		this.incendie = destination;

	}



	@Override
	public long getDate() {
		// TODO Auto-generated method stub
		return date;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		robot.deverserEau();
		int tmp =  (int) (this.incendie.getIntensite() - this.robot.getDebit());
		if( tmp < 0) {
			this.incendie.setIntensite(0);
		}
		else {
			this.incendie.setIntensite(tmp);
		}
	}


}
