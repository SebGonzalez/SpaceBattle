package client.Model;

import org.newdawn.slick.Graphics;

import client.IHM.GestionnaireImagesIHM;

public class Flag {
	
	private float xBase;
	private float yBase;
	
	private float x;
	private float y;
	
	private int numTeam;
	
	public Flag() {
		
	}
	
	public Flag(float x, float y, int numTeam) {
		this.xBase = x;
		this.yBase = y;
		this.x = x;
		this.y = y;
		this.numTeam = numTeam;
	}
	
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public float getxBase() {
		return xBase;
	}

	public float getyBase() {
		return yBase;
	}
	/**
	 * Affiche le drapeau en fonction de ses déplacements
	 * @author Amine Boudraa
	 */
	public void render(Graphics g, int teamJoueur) {
		if(numTeam == teamJoueur) {
			GestionnaireImagesIHM.getRessource("baseFlag1").draw(xBase-64, yBase);
			GestionnaireImagesIHM.getRessource("flag1").draw(x-32, y-32);
		}
		else {
			GestionnaireImagesIHM.getRessource("baseFlag2").draw(xBase-64, yBase);
			GestionnaireImagesIHM.getRessource("flag2").draw(x-32, y-32);
		}
	}
	
	/**
	 * Détecte la collision entre joueur et flag
	 * @author Amine Boudraa
	 * @return vrai si collision faux si non
	 */
	public boolean collision(double d, double e) {
		if(x > d-56 && x < d+56 && y > e-37 && y < e+37) return true;
		return false;
	}
	
	/**
	 * Détecte collision entre flag et une base
	 * @return vrai si oui faux si non
	 * @author Amine Boudraa
	 */
	public boolean collisionBase(double xJoueur, double yJoueur) {
		if(xBase > xJoueur-56 && xBase < xJoueur+56 && yBase+64 > yJoueur-37 && yBase < yJoueur+37) return true;
		return false;
	}
	
	/**
	 * Remet le flag à sa position initiale(celle de sa base)
	 * @author Amine Boudraa
	 */
	public void resetPos() {
		x = xBase;
		y = yBase;
	}
}
