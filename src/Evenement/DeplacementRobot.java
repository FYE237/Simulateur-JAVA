package Evenement;

import robot.Robot;
import tpl.Carte;
import tpl.Case;

public class DeplacementRobot extends Evenement {
	
	
	protected Case caseCible;
	
	/**
	 * 
	 * @param date	: La date à laquelle l'éévènement s'execute
	 * @param dest  : La case destination
	 * @param robot : robot sur lequel le deplacement s'effectue
	 * @param carte 
	 */
	public DeplacementRobot(long date,Case dest,Robot robot,Carte carte ) {
		// TODO Auto-generated constructor stub
		super(robot,date);
		this.carte =carte;
		this.caseCible = carte.getCase(dest.getLigne(), dest.getColonne());
	}

	@Override
	public long getDate() {
		// TODO Auto-generated method stub
		return this.date;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		this.robot.setPosition(this.carte,this.caseCible);
	}

	@Override
	public String toString() {
		return super.toString() + "caseCible=" + caseCible;
	}
	
	
}
