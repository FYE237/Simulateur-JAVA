package robot;

import tpl.Carte;
import tpl.Case;
import tpl.Direction;
import tpl.NatureTerrain;

/**
 * @author 
 * Il s'agit de la classe drole sous de la classe robot 
 */

public class Drone extends Robot{

	
	/**
	 * 
	 * @param position
	 */
	public Drone(Case position) {
		super();
		this.position = new  Case(position.getLigne(), position.getColonne(), position.getNature());
		this.vitesse = 100 ;
		this.volumeReservoir=10000;
		this.debit = 10000;
	}
	
	/**
	 * 
	 * @param position 
	 * @param vitesse
	 */
	public Drone(Case position, double vitesse) {
		super();
		this.position = new  Case(position.getLigne(), position.getColonne(), position.getNature());
		if (vitesse > 150 ) {
			this.vitesse = 150 ;
		}
		else {
				this.vitesse = vitesse;
			}
		this.volumeReservoir=10000;
		this.debit = 10000;
	}
	
	/**
	 * @param carte 
	 * @param position : C'est la position à laquelle on va deplacer le drone
	 */
	public void setPosition(Carte carte , Case position) {
		
		if(checkPosition(carte, position)) //On recupere la position sur la carte  qui correspond à la positon sur la carte
			this.position = carte.getCase(position.getLigne(), position.getColonne());

	}
	
	/**
	 * @param nature : C'est la nature du terrain 
	 */
	@Override
	public double getVitesse(NatureTerrain nature) {
		// TODO Auto-generated method stub
		return this.vitesse;
	}
	

	//Il vide le reservoir
	/**
	 * Il vide son reservoir
	 */
	@Override
	public void deverserEau(){
		// TODO Auto-generated method stub
		this.volumeReservoir= 0;
	}
	
	//On initialise la valeur du réservoir à la valeur initiale par défaut
	@Override
	public void setVolumeReservoir(){
		// TODO Auto-generated method stub
		this.volumeReservoir = 10000;
	}

	/**
	 * @param carte
	 */
	@Override 
	public void remplirReservoir(Carte carte) {
		// Le drone se remplit sur une case de nature eau
		if(this.getPosition().getNature() == NatureTerrain.EAU)
			this.volumeReservoir = 10000;
	}
	
	
}