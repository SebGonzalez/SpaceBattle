package server;

import java.util.ArrayList;

import client.Model.Missile;
import network.DatagramUpdateServer;

/**
 * Instance d'un joueur niveau serveur
 * @author Sébastien Gonzalez
 *
 */
public class ServeurJoueur {

	private int id;
	private double x;
	private double y;
	private double accelerationX;
	private double accelerationY;
	private float r;
	private ArrayList<Missile> listeMissile;
	private Boolean bonus[] = new Boolean[4];
	private long bonusTimer[] = new long[4];
	
	public ServeurJoueur() {
		this.id = -1;
		x = 0;
		y = 0;
		r = (float) Math.PI/2;
		listeMissile = new ArrayList<>();
		
		for(int i=0;i<4;i++) {
			bonus[i] = false;
		}
		
		for( int i = 0; i < 4 ; i++)
			bonusTimer[i] = 0;
		
	}
	
	public ServeurJoueur(int id) {
		this.id = id;
		x = 0;
		y = 0;
		r = (float) Math.PI/2;
		listeMissile = new ArrayList<>();
		for(int i=0;i<4;i++) {
			bonus[i] = false;
		}
		
		for( int i = 0; i < 4 ; i++)
			bonusTimer[i] = 0;


	}
	
	public ServeurJoueur(int id, double x, double y, float r, ArrayList<Missile> listeMissile) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.r = r;
		this.listeMissile = listeMissile;
		
		for(int i=0;i<4;i++) {
			bonus[i] = false;
		}
		
		for( int i = 0; i < 4 ; i++)
			bonusTimer[i] = 0;
	}
	
	public void bonusVitesseUP(DatagramUpdateServer datagram) {
			//datagram.vitesseBonus = true;
	}

	public void enableBonus(int indice) {
		bonus[indice] = true;
		bonusTimer[indice] = System.currentTimeMillis();
	}
	
	public void disableBonus(int indice) {
		bonus[indice] = false;
		bonusTimer[indice] = 0;
	}
	
	
	public boolean getBonusState(int indice) {
		return bonus[indice];
	}
	

	public long getTimerBonus(int indice) {
		return bonusTimer[indice];
	}
	
	public void bonusTeteChercheuseMissile() {
		
	}
		
	
	public void bonusShield() {
		
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
