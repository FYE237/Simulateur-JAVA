package robot;

import tpl.Case;
import tpl.NatureTerrain;

public class RobotAChenille extends Robot {
	
	public RobotAChenille (Case position) {
		this.position = new  Case(position.getLigne(), position.getColonne(), position.getNature());
		this.vitesse = 60;
		this.volumeReservoir=2000;
	}
	
	public RobotAChenille (Case position, double vitesse) {
		this.position = new  Case(position.getLigne(), position.getColonne(), position.getNature());
		if (vitesse > 80 ) {
			this.vitesse = 80 ;
		}
		else {
				this.vitesse = vitesse;
			}
		this.volumeReservoir=2000;
	}
	
	@Override
	public void setPosition(Case position) {
		// TODO Auto-generated method stub
		if(position.getNature() != NatureTerrain.EAU &&
				position.getNature() != NatureTerrain.ROCHE	) {
			this.position = new Case(position.getLigne(),position.getColonne(),
										position.getNature());
		}
		if(position.getNature() == NatureTerrain.FORET) {
			this.vitesse= this.vitesse*0.5;
		}
		
	}

	@Override
	public double getVitesse(NatureTerrain nature) {
		// TODO Auto-generated method stub
		return this.vitesse;
	}

	@Override
	public void deverserEau(int vol) {
		// TODO Auto-generated method stub
		if(this.volumeReservoir < 100) {
			this.volumeReservoir = 0;
		}
		else {
			this.volumeReservoir -=100;
		}
	}

	@Override
	public void remplirReservoir() {
		// TODO Auto-generated method stub
		this.volumeReservoir = 5000;
	}

}
