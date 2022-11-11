package robot;

import tpl.Carte;
import tpl.Case;
import tpl.NatureTerrain;

/**
 * @author fezeuyoe
 * Il s'agit de la classe drole sous de la classe robot 
 */

public class Drone extends Robot{

	
	
	public Drone(Case position) {
		this.position = new  Case(position.getLigne(), position.getColonne(), position.getNature());
		this.debit = 10000;
		this.vitesse = 100 ;
		this.volumeReservoir=10000;
	}
	
	public Drone(Case position, double vitesse) {
		this.position = new  Case(position.getLigne(), position.getColonne(), position.getNature());
		if (vitesse > 150 ) {
			this.vitesse = 150 ;
		}
		else {
				this.vitesse = vitesse;
			}
		this.volumeReservoir=10000;
	}
	
	public void setPosition(Case position) {
		this.position = new  Case(position.getLigne(), position.getColonne(), position.getNature());

	}
	
	@Override
	public double getVitesse(NatureTerrain nature) {
		// TODO Auto-generated method stub
		return this.vitesse;
	}
	

	//Il vide le reservoir
	@Override
	public void deverserEau() {
		// TODO Auto-generated method stub
		this.volumeReservoir= 0;
	}

	@Override
	public void remplirReservoir(Carte carte) {
		// TODO Auto-generated method stub
		if(this.getPosition().getNature() == NatureTerrain.EAU) {
			this.volumeReservoir = 10000;
		}
		
	}
	
	
}
