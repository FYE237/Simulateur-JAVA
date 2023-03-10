/**
 * 
 */
package robot;

import tpl.Carte;
import tpl.Case;
import tpl.Direction;
import tpl.NatureTerrain;

/**
 * @author fezeuyoe
 *
 */
public class RobotARoue  extends Robot{

	/**
	 * 
	 * @param position
	 */
	public RobotARoue (Case position) {
		super();
		this.position = new  Case(position.getLigne(), position.getColonne(), position.getNature());
		this.vitesse = 80;
		this.volumeReservoir=5000;
		this.debit = 100;
	}

	/**
	 * 
	 * @param position
	 * @param vitesse
	 */
	public RobotARoue (Case position, double vitesse) {
		super();
		this.position = new  Case(position.getLigne(), position.getColonne(), position.getNature());
		this.vitesse = vitesse;
		this.volumeReservoir=5000;
		this.debit = 100;
	}

	/**
	 * @param carte
	 * @param position 
	 */
	@Override
	public void setPosition(Carte carte,Case position) {
		// TODO Auto-generated method stub
		if(checkPosition(carte, position)) {
			if(position.getNature() == NatureTerrain.TERRAIN_LIBRE ||
					position.getNature() == NatureTerrain.HABITAT	) {
				this.position = carte.getCase(position.getLigne(), position.getColonne());
			}
		}
	}

	@Override
	public void setVolumeReservoir(){
		// TODO Auto-generated method stub
		this.volumeReservoir = 5000;
	}

	@Override
	public double getVitesse(NatureTerrain nature) {
		// TODO Auto-generated method stub
		if(nature == NatureTerrain.TERRAIN_LIBRE || nature == NatureTerrain.HABITAT	) {
			return this.vitesse;
		}
		else {
			return 0.0;
		}
	}

	/*
	 *Le robot de l'eau. Si la quantité versée est < au volume restant on passe le volume à 0 
	 */
	@Override
	public void deverserEau() {
		// TODO Auto-generated method stub
		if(this.volumeReservoir < this.debit) {
			this.volumeReservoir = 0;
		}
		else {
			this.volumeReservoir -=this.debit;
		}
	}

	/**
	 * @param carte  La carte 
	 */
	@Override
	public void remplirReservoir(Carte carte) {

		/*
		 * Pour ce robot il se met à côté d'une case proche de l'eau pour se remplir. On vérifie si le voisin exite ensuite on verifie
		 * si ce voisin est un terrain d'eau. Puis on change la valeur du réservoir
		 */
		if(carte.voisinExiste(this.getPosition(), Direction.NORD)) 
		{
			if(carte.getVoisin(this.position, Direction.NORD).getNature() == NatureTerrain.EAU )
				this.volumeReservoir = 5000;
		}
		if(carte.voisinExiste(this.getPosition(), Direction.SUD)) {
			if(carte.getVoisin(this.position, Direction.SUD).getNature() == NatureTerrain.EAU )
				this.volumeReservoir = 5000;
		}
		if(carte.voisinExiste(this.getPosition(), Direction.OUEST)) {
			if(carte.getVoisin(this.position, Direction.OUEST).getNature() == NatureTerrain.EAU )
				this.volumeReservoir = 5000;
		}
		if(carte.voisinExiste(this.getPosition(), Direction.EST)) {
			if(carte.getVoisin(this.position, Direction.EST).getNature() == NatureTerrain.EAU )
				this.volumeReservoir = 5000;
		}
	}

}