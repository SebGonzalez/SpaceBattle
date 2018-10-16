package server;

import java.util.ArrayList;

import client.Model.Missile;

/**
 * Instance d'un joueur niveau serveur
 * @author Sébastien Gonzalez
 *
 */
public class ServeurJoueur {

	private int id;
	private double x;
	private double y;
	private float r;
	private ArrayList<Missile> listeMissile;
	
	public ServeurJoueur() {
		this.id = -1;
		x = 0;
		y = 0;
		r = (float) Math.PI/2;
		listeMissile = new ArrayList<>();
	}
	
	public ServeurJoueur(int id) {
		this.id = id;
		x = 0;
		y = 0;
		r = (float) Math.PI/2;
		listeMissile = new ArrayList<>();

	}
	
	public ServeurJoueur(int id, double x, double y, float r, ArrayList<Missile> listeMissile) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.r = r;
		this.listeMissile = listeMissile;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public float getR() {
		return r;
	}
	
	public void setR(float r) {
		this.r = r;
	}

	public ArrayList<Missile> getListeMissile() {
		return listeMissile;
	}

	public void setListeMissile(ArrayList<Missile> listeMissile) {
		this.listeMissile = listeMissile;
	}
	
}
