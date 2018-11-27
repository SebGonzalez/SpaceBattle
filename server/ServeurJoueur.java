package server;

import java.util.ArrayList;
import java.util.Random;

import client.Model.Missile;
import network.DatagramUpdateServer;

/**
 * Instance d'un joueur niveau serveur
 * @author Sébastien Gonzalez
 *
 */
public class ServeurJoueur {
	
	private String name;
	private int id;
	private float x;
	private float y;
	public double accelerationX;
	public double accelerationY;
	private float r;
	private ArrayList<Missile> listeMissile;
	public Boolean bonus[] = new Boolean[4];
	private long bonusTimer[] = new long[4];
	private int team;
	 
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

		team = new Random().nextInt(2) + 1;
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

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setaccelerationX(double X) {
		this.accelerationX = X;
	}
	
	public void setaccelerationY(double Y) {
		this.accelerationY = Y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getR() {
		return r;
	}
	
	public void setR(float r) {
		this.r = r;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	public ArrayList<Missile> getListeMissile() {
		return listeMissile;
	}

	public void setListeMissile(ArrayList<Missile> listeMissile) {
		this.listeMissile = listeMissile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
