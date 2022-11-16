package robot;


import tpl.Carte;
import tpl.Case;
import tpl.Direction;
import tpl.NatureTerrain;

public class RobotAChenille extends Robot {
	
	public RobotAChenille (Case position) {
		super();
		this.position = new  Case(position.getLigne(), position.getColonne(), position.getNature());
		this.vitesse = 60;
		this.volumeReservoir=2000;
		this.debit = 100;
	}
	
	public RobotAChenille (Case position, double vitesse) {
		super();
		this.position = new  Case(position.getLigne(), position.getColonne(), position.getNature());
		if (vitesse > 80 ) {
			this.vitesse = 80 ;
		}
		else {
				this.vitesse = vitesse;
			}
		this.volumeReservoir=2000;
		this.debit = 100;
	}
	
	//fye
	@Override
	public void setPosition(Carte carte,Case position) {
		// TODO Auto-generated method stub
		if(checkPosition(carte, position)) {
			if(position.getNature() != NatureTerrain.EAU && position.getNature() != NatureTerrain.ROCHE	) {
				this.position = carte.getCase(position.getLigne(), position.getColonne());
			}
		}
	}

	@Override
	public double getVitesse(NatureTerrain nature) {
		// TODO Auto-generated method stub
		if(position.getNature() == NatureTerrain.FORET) {
			return this.vitesse*0.5;
		}
		if(nature == NatureTerrain.EAU || nature == NatureTerrain.ROCHE	) {
			return 0.0;
		}
		return this.vitesse;
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


		if(carte.voisinExiste(this.getPosition(), Direction.NORD)) 
		{
			System.out.println("AAAAAZD");
		   if(carte.getVoisin(this.position, Direction.NORD).getNature() == NatureTerrain.EAU ) 
			   this.volumeReservoir = 2000;
		}
		if(carte.voisinExiste(this.getPosition(), Direction.SUD)) {
			System.out.println("AAAAAZC");
			if(carte.getVoisin(this.position, Direction.SUD).getNature() == NatureTerrain.EAU )
				this.volumeReservoir = 2000;
		}
		if(carte.voisinExiste(this.getPosition(), Direction.OUEST)) {
			System.out.println("AAAAAZB");
			if(carte.getVoisin(this.position, Direction.OUEST).getNature() == NatureTerrain.EAU )
				this.volumeReservoir = 2000;
		}
		if(carte.voisinExiste(this.getPosition(), Direction.EST)) {
			System.out.println("AAAAAZA");
			if(carte.getVoisin(this.position, Direction.EST).getNature() == NatureTerrain.EAU )
				this.volumeReservoir = 2000;
			
		}
		// TODO Auto-generated method stub
		System.out.println("***** " + this);
	}

}