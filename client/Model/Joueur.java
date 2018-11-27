package client.Model;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import client.Gestionnaire.GestionnaireBonusClient;
import client.IHM.WindowGame;

import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.Iterator;

import server.ServeurJoueur;

public class Joueur {
	
	

	private String nom;
	private float x = 900, y = 900;
	public boolean keys_pressed[] = new boolean[3];
	private float accelerationX = 0;
	private float accelerationY = 0;
	private float rotation = (float) (Math.PI / 2);
	private float directionX = (float) Math.cos(rotation);
	private float directionY = (float) Math.sin(rotation);
	public boolean bonus[] = new boolean[4];
	public float boost = 1;
	public int health;
	
	private int team = -1;

	public Joueur() {
		//x = (int) ((Math.random() * ((2500 - 1050) + 1)) + 1050); // rand.nextInt(2500) + 1050; 
		//y = (int) ((Math.random() * ((2300 - 650 ) + 1)) + 650);
		x=846;
		y =1344;
		nom = "test23";
		for (int i = 0; i < 3; i++) {
			keys_pressed[i] = false;
		}
		for (int i = 0; i < 4; i++) {
			bonus[i] = false;
		}

	}

	public Joueur(String nom, float x, float y) {
		this.nom = nom;
		this.x = x;
		this.y = y;
	}

	public void render_bonus() {

	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
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

	public float getaccelerationX() {
		return accelerationX;
	}

	public float getaccelerationY() {
		return accelerationY;
	}

	public float getdirectionX() {
		return directionX;
	}

	public float getdirectionY() {
		return directionY;
	}

	public void setdirectionX() {
		directionX = (float) Math.cos(rotation);
	}

	public void setdirectionY() {
		directionY = (float) Math.sin(rotation);
	}

	public void addaccelerationX(float value) {
		accelerationX += value;
	}

	public void setaccelerationX(float value) {
		accelerationX = value;
	}

	public void setaccelerationY(float value) {
		accelerationY = value;
	}

	public void addaccelerationY(float value) {
		accelerationY += value;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	public void accelerate(int delta) {

		if (getaccelerationX() + (delta * (getdirectionX() / 200)) > -4
				&& getaccelerationX() + (delta * (getdirectionX() / 200)) < 4)
			addaccelerationX(delta * (getdirectionX() / 200));

		if (getaccelerationY() + (delta * (getdirectionY() / 200)) > -4
				&& getaccelerationY() + (delta * (getdirectionY() / 200)) < 4)
			addaccelerationY(delta * (getdirectionY() / 200));

	}
	


	public float getRotation() {
		return rotation;
	}

	public void rotationGauche(int delta) {
		this.rotation -= (float) delta / 250;
		setdirectionX();
		setdirectionY();
		WindowGame.shipJoueur.setRotation((float) -(Math.toDegrees(Math.atan2(Math.cos(getRotation()), Math.sin(getRotation())))));
	}

	public void rotationDroite(int delta) {
		this.rotation += (float) delta / 250;
		setdirectionX();
		setdirectionY();
		WindowGame.shipJoueur.setRotation((float) -(Math.toDegrees(Math.atan2(Math.cos(getRotation()), Math.sin(getRotation())))));
	}

	public void render(Graphics g) {

		WindowGame.shipJoueur.draw(getX() - 56, getY() - 37);
	
		
	}

	public void update(GameContainer container, int delta, TiledMap map) {
		if (bonus[0])
			boost = (float) 1.75;
		else if (!bonus[0])
			boost = 1;

		if (keys_pressed[0] == true)
			accelerate(delta);
		if (keys_pressed[1] == true)
			rotationGauche(delta);
		if (keys_pressed[2] == true)
			rotationDroite(delta);

		float futurX = getX() - .1f * delta * accelerationX * boost;
		float futurY = getY() - .1f * delta * accelerationY * boost;

		collide(futurX, futurY, map);
		// collidePlayer(futurX,futurY);
	}

	public void collide(float futurX, float futurY, TiledMap map) {

		Image tile = map.getTileImage((int) futurX / map.getTileWidth(), (int) futurY / map.getTileHeight(),
				map.getLayerIndex("logic"));

		boolean collision = tile != null;
		if (!collision) {
			setX(futurX);
			setY(futurY);
		}

		else {
			if (x < 950 || x > 2550) {
				accelerationX *= -1;
				if (accelerationX < -2 || accelerationX > 2) {
					accelerationX /= 1.5;
				}
			}

			if (y < 650 || y > 2400) {
				accelerationY *= -1;
				if (accelerationY < -2 || accelerationY > 2) {
					accelerationY /= 1.5;
				}
			}
		}
	}

	public void collidePlayer(ArrayList<ServeurJoueur> listeAdversaire) {

		for (ServeurJoueur joueur : listeAdversaire) {
			if (x > joueur.getX() - 25 && x < joueur.getX() + 25 && y > joueur.getY() - 25 && y < joueur.getY() + 25) {

				accelerationX *= -1;
				accelerationY *= -1;
				System.out.println("collide");

			}
		}
	}
}
