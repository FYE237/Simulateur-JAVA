package Evenement;

import robot.Robot;
import tpl.Carte;

public abstract class Evenement {

	protected long date;
	protected Robot robot;
	protected  Carte carte;
	
//	public Evenement(long date) {
//		// TODO Auto-generated constructor stub
//	}

	public abstract long getDate() ;

	public  abstract  void execute();
}
