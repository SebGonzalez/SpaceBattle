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
	public double accelerationX;
	public double accelerationY;
	private float r;
	private ArrayList<Missile> listeMissile;
	public Boolean bonus[] = new Boolean[4];
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
	


	public void enableBonus(int indice) {
		bonus[indice] = true;
		bonusTimer[indice] = System.currentTimeMillis();
	}
	
	public void disableBonus(int indice) {
		bonus[indice] = false;
		bonusTimer[indice] = 0;
	}
	
	public void JoueurCollide(ServeurJoueur player,DatagramUpdateServer datagram) {
		
	if ( this.x > player.getX()-25 && this.getX() < player.getX()+25 && this.getY() > player.getY()-25 && this.getY() < player.getY()+25 ) {
		
	
		//setCollide(datagram);
		System.out.println("collide entre joueur " + id + "et joueur : " + player.getId());
	}
			
	}
		
	

	public void setCollide(DatagramUpdateServer datagram) {
		
		
		
		if(this.x < 950 || this.x > 2550) {
			datagram.accelerationX *= -1;
			if(this.accelerationX < -2 || this.accelerationX > 2) {
				datagram.accelerationX /= 1.5;
				}
			}
		
		if(this.y < 650 || this.y > 2400) {
			datagram.accelerationY *= -1;
			if(this.accelerationY < -2 || this.accelerationY > 2) {
				datagram.accelerationY /= 1.5;
				}
			}
		
		
		
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

	public void setAccelerationX(double X) {
		this.accelerationX = X;
	}
	
	public void setAccelerationY(double Y) {
		this.accelerationY = Y;
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
