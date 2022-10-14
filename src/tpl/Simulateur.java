package tpl;

import java.util.ArrayList;

public class Simulateur {
	private long dateSimulation;
	private ArrayList<Evenement> evenements;

	public Simulateur(long dateSimulation) {
		this.dateSimulation = dateSimulation;
	}

	public long getDateSimulation() {
		return dateSimulation;
	}
	
	public void ajouteEvenement(Evenement e) {	
		this.evenements.add(e);
	}
	
	private void incrementeDate() {
		this.dateSimulation += 1;
	}
	
	private boolean simulationTerminee() {
		return this.evenements.size() == this.dateSimulation;
	}
	
}
