/**
 * 
 */
package Evenement;

import robot.Robot;
import tpl.Incendie;

/**
 * @author fezeuyoe
 *	Il s'agit de l'évenement verser de l'eau
 */
public class VerserEau extends Evenement {

	/**
	 * Il y a l'attribut date de la superclasse
	 * Robot position du robot qui doit verser de l'eau
	 * Incendie c'est l'incendie à eteindre
	 * 
	 */
	

	private Incendie incendie;
	
	
	
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
		int tmp =  (int) (this.incendie.getIntensite() - robot.getDebit());
		if( tmp < 0) this.incendie.setIntensite(0);
		else this.incendie.setIntensite(tmp);
	}
	

}
