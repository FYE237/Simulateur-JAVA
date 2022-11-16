package Evenement;

import robot.Robot;
import tpl.Carte;
import tpl.StatutRobot;

public abstract class Evenement {

	protected long date;
	protected Robot robot;
	protected  Carte carte;
	
	public Evenement(Robot robot,long date) {
		// TODO Auto-generated constructor stub
		this.robot = robot;
		this.date = date;
		//this.robot.setStatut(StatutRobot.occupe);
	}
		
	public Robot getRobot() {
		return robot;
	}

	public abstract long getDate() ;

	public  abstract  void execute();
	
	@Override
	public String toString() {
		return this.getClass().toString()+" "+ this.robot.toString() + " date : " + this.date; 
	}
}
