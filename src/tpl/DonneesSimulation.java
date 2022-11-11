package tpl;

import java.util.ArrayList;

import robot.Robot;

public class DonneesSimulation {
	private Carte carte ;
	private ArrayList<Incendie> incendies = new ArrayList<Incendie>();
	private ArrayList<Robot> robots = new ArrayList<Robot>();

	public DonneesSimulation(Carte carte) {
		this.carte = carte;
	}

	public Carte getCarte() {
		return carte;
	}

	public ArrayList<Incendie> getIncendies() {
		return incendies;
	}

	public ArrayList<Robot> getRobots() {
		return robots;
	}

	public void setCarte(Carte carte) {
		this.carte = carte;
	}

	public void addIncendie(Incendie incendie) {
		this.incendies.add(incendie);
	}

	public void addRobots(Robot robot) {
		this.robots.add(robot);
	}

	@Override
	public String toString() {
		return "DonneesSimulation [carte=\n" + carte.toString() + "\n, incendies=" + incendies.toString() + "\n, robots=" + robots.toString() + "]";
	}
	
	
}
