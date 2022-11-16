package tpl;

import Evenement.Evenement;

public class Carte {
	private int nbLignes;
	private int nbColonnes;
	private int tailleCases;
	private Case[][] grille;
	
	public Carte(int nbLignes, int nbColonnes, int tailleCases) {
		this.nbLignes = nbLignes;
		this.nbColonnes = nbColonnes;
		this.tailleCases = tailleCases;
		this.grille = new Case[nbLignes][nbColonnes];
	}

	public int getNbLignes() {
		return nbLignes;
	}

	public int getNbColonnes() {
		return nbColonnes;
	}

	public int getTailleCases() {
		return tailleCases;
	}
	
	public Case getCase(int lig, int col) {
		return grille[lig][col];
	}
	
	public void setCase(int lig, int col, Case cas) {
		this.grille[lig][col] = cas;
	}
	
	public boolean voisinExiste(Case src, Direction dir) {
		switch(dir) {
			case NORD:
				return src.getLigne() > 0;
			case SUD:
				return src.getLigne() < this.nbLignes -1;
			case EST:
				return src.getColonne() < this.nbColonnes -1;
			case OUEST:
				return src.getColonne() > 0;
			default:
				System.out.println("Default");
				return false;
		}
	}
	
	public Case getVoisin(Case src, Direction dir) {
		switch(dir) {
			case NORD:
				return this.getCase(src.getLigne()-1, src.getColonne());
			case SUD:
				return this.getCase(src.getLigne()+1, src.getColonne());
			case EST:
				return this.getCase(src.getLigne(), src.getColonne()+1);
			case OUEST:
				return this.getCase(src.getLigne(), src.getColonne()-1);
			default:
				return src;
		}
	}

	public Carte copy() {
		Carte c = new Carte(this.nbLignes,this.nbColonnes,this.tailleCases);
		for(int i = 0; i<this.nbLignes; i++) {
			for(int j = 0; j<this.nbLignes; j++) {
				c.setCase(i, j, new Case(i,j,this.getCase(i, j).getNature()));
			}
		}
		return c;
	}
	
	@Override
	public String toString() {
		return "Carte [nbLignes=" + nbLignes + ", nbColonnes=" + nbColonnes + ", tailleCases=" + tailleCases + "]";
	}
	
	
	
}
