package Evenement;

import robot.Robot;
import tpl.Carte;
import tpl.Case;

public class DeplacementRobot extends Evenement {
	
	
	protected Case caseCible;
	
	public DeplacementRobot(long date,Case dest,Robot robot,Carte carte ) {
		// TODO Auto-generated constructor stub
		this.robot = robot;
		this.date = date;
		this.carte =carte;
		this.caseCible = new Case(dest.getLigne(), dest.getColonne(), dest.getNature());
	}

	@Override
	public long getDate() {
		// TODO Auto-generated method stub
		return this.date;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		this.robot.setPosition(carte,caseCible);
	}

}
