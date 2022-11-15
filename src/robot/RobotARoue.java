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

	
	public RobotARoue (Case position) {
		super();
		this.position = new  Case(position.getLigne(), position.getColonne(), position.getNature());
		this.vitesse = 80;
		this.volumeReservoir=5000;
		this.debit = 100;
	}
	
	public RobotARoue (Case position, double vitesse) {
		super();
		this.position = new  Case(position.getLigne(), position.getColonne(), position.getNature());
		this.vitesse = vitesse;
		this.volumeReservoir=5000;
		this.debit = 100;
	}
	
	//fye
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
	public double getVitesse(NatureTerrain nature) {
		// TODO Auto-generated method stub
		if(nature == NatureTerrain.TERRAIN_LIBRE || nature == NatureTerrain.HABITAT	) {
			return this.vitesse;
		}
		else {
			return 0.0;
		}
	}

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

	//fye
	@Override
	public void remplirReservoir(Carte carte) {
		// TODO Auto-generated method stub
		if(carte.voisinExiste(this.getPosition(), Direction.NORD)) 
		{
			if(carte.getVoisin(this.position, Direction.NORD).getNature() == NatureTerrain.EAU )
				this.volumeReservoir = 5000;
		}
		else if(carte.voisinExiste(this.getPosition(), Direction.SUD)) {
			if(carte.getVoisin(this.position, Direction.NORD).getNature() == NatureTerrain.EAU )
				this.volumeReservoir = 5000;
		}
		else if(carte.voisinExiste(this.getPosition(), Direction.OUEST)) {
			if(carte.getVoisin(this.position, Direction.NORD).getNature() == NatureTerrain.EAU )
				this.volumeReservoir = 5000;
		}
		else if(carte.voisinExiste(this.getPosition(), Direction.EST)) {
			if(carte.getVoisin(this.position, Direction.NORD).getNature() == NatureTerrain.EAU )
				this.volumeReservoir = 5000;
		}
	}

}