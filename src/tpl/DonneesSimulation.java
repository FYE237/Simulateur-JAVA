package tpl;

import java.util.ArrayList;
import java.util.Collections;

import robot.Drone;
import robot.Robot;
import robot.RobotAChenille;
import robot.RobotAPatte;
import robot.RobotARoue;

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
	
	public void ranger() {
		Collections.sort(this.incendies);
		Collections.sort(this.robots);
	}
	
	public DonneesSimulation copy() {
		DonneesSimulation d = new DonneesSimulation(this.carte.copy());
		for(Incendie incendie: this.incendies) {
			d.addIncendie(new Incendie(d.getCarte().getCase(incendie.getPosition().getLigne(), incendie.getPosition().getColonne()),incendie.getIntensite()));
		}
		for(Robot robot: this.robots) {

			if(robot.getClass() == Drone.class) d.addRobots(new Drone(d.getCarte().getCase(robot.getPosition().getLigne(), robot.getPosition().getColonne()),robot.getVitesse(NatureTerrain.TERRAIN_LIBRE)));
			if(robot.getClass() == RobotAChenille.class) d.addRobots(new RobotAChenille(d.getCarte().getCase(robot.getPosition().getLigne(), robot.getPosition().getColonne()),robot.getVitesse(NatureTerrain.TERRAIN_LIBRE)));
			if(robot.getClass() == RobotAPatte.class) d.addRobots(new RobotAPatte(d.getCarte().getCase(robot.getPosition().getLigne(), robot.getPosition().getColonne()),robot.getVitesse(NatureTerrain.TERRAIN_LIBRE)));
			if(robot.getClass() == RobotARoue.class) d.addRobots(new RobotARoue(d.getCarte().getCase(robot.getPosition().getLigne(), robot.getPosition().getColonne()),robot.getVitesse(NatureTerrain.TERRAIN_LIBRE)));

		}

		return d; 
	}
	
	@Override
	public String toString() {
		return "DonneesSimulation [carte=\n" + carte.toString() + "\n, incendies=" + incendies.toString() + "\n, robots=" + robots.toString() + "]";
	}
	
	
}
