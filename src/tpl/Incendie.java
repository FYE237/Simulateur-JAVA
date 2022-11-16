package tpl;



public class Incendie implements Comparable<Incendie>{
	private Case position;
	private int intensite;
	private StatutIncendie statut; 
	
	public Incendie(Case position, int intensite) {
		this.position = position;
		this.intensite = intensite;
		this.statut = StatutIncendie.allume;
	}

	public Case getPosition() {
		return position;
	}
	
	public int getIntensite() {
		return intensite;
	}

	public void setIntensite(int intensite) {
		this.intensite = intensite;
	} 
	
	public StatutIncendie getStatut() {
		return statut;
	}

	public void setStatut(StatutIncendie statut) {
		this.statut = statut;
	}

	@Override
	public String toString() {
		return "Incendie [position=" + position + ", intensite=" + intensite + "]\n";
	}

	@Override
	public int compareTo(Incendie o) {
		// TODO Auto-generated method stub
		return o.intensite-this.intensite;
	}
	
}
