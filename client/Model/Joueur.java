package client.Model;

import java.awt.geom.Line2D;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.tiled.TiledMap;

import client.IHM.WindowGame;
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
		x = (int) ((Math.random() * ((2500 - 1050) + 1)) + 1050); // rand.nextInt(2500) + 1050;
		y = (int) ((Math.random() * ((2300 - 650) + 1)) + 650);
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
	
	public String getNom() {
		return nom;
	}
	

	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Renvoie la position X du joueur
	 * @author Amine Boudraa
	 */
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}
	
	/**
	 * Renvoie la position Y du joueur
	 * @author Amine Boudraa
	 */
	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * Renvoie la composante x courante de l'acceleration du joueur
	 * @author Amine Boudraa
	 */
	public float getaccelerationX() {
		return accelerationX;
	}
	
	/**
	 * Renvoie la composante y courante de l'acceleration du joueur
	 * @author Amine Boudraa
	 */
	public float getaccelerationY() {
		return accelerationY;
	}

	/**
	 * Renvoie la composante x de la direction vers laquelle se dirige le joueur
	 * @author Amine Boudraa
	 */
	public float getdirectionX() {
		return directionX;
	}

	/**
	 * Renvoie la composante y de la direction vers laquelle se dirige le joueur
	 * @author Amine Boudraa
	 */
	public float getdirectionY() {
		return directionY;
	}
	
	
	public void setdirectionX() {
		directionX = (float) Math.cos(rotation);
	}

	public void setdirectionY() {
		directionY = (float) Math.sin(rotation);
	}
	
	/**
	 * Augmente la composante de x de l'acceleration du joueur
	 * @author Amine Boudraa
	 */
	public void addaccelerationX(float value) {
		accelerationX += value;
	}
	
	/**
	 * Modifie la composante de x de l'acceleration du joueur
	 * @author Amine Boudraa
	 */
	public void setaccelerationX(float value) {
		accelerationX = value;
	}
	
	/**
	 * Modifie la composante de x de l'acceleration du joueur
	 * @author Amine Boudraa
	 */
	public void setaccelerationY(float value) {
		accelerationY = value;
	}
	
	/**
	 * Augmente la composante de y de l'acceleration du joueur
	 * @author Amine Boudraa
	 */
	public void addaccelerationY(float value) {
		accelerationY += value;
	}
	
	/**
	 * Renvoie le numéro de l'équipe dans laquelle se trouve le joueur
	 * @author Amine Boudraa
	 */
	public int getTeam() {
		return team;
	}
	
	/**
	 * Affecte le joueur à une équipe
	 * @author Amine Boudraa
	 */
	public void setTeam(int team) {
		this.team = team;
	}
	

	/**
	 * Augmente les composantes x et y de l'acceleration du joueur en la bornant 
	 * @author Amine Boudraa
	 */
	public void accelerate(int delta) {

		if (getaccelerationX() + (delta * (getdirectionX() / 200)) > -4 && getaccelerationX() + (delta * (getdirectionX() / 200)) < 4)
			addaccelerationX(delta * (getdirectionX() / 200));

		if (getaccelerationY() + (delta * (getdirectionY() / 200)) > -4 && getaccelerationY() + (delta * (getdirectionY() / 200)) < 4)
			addaccelerationY(delta * (getdirectionY() / 200));

	}
	
	public float getRotation() {
		return rotation;
	}

	/**
	 * Effectue une rotation gauche du joueur
	 * Et par conséquent modifie la direction et l'affichage du joueur et des textures de dégats si elles sont affichées
	 * @author Amine Boudraa
	 */
	public void rotationGauche(int delta) {
		this.rotation -= (float) delta / 250;
		setdirectionX();
		setdirectionY();
		WindowGame.shipJoueur.setRotation((float) -(Math.toDegrees(Math.atan2(Math.cos(getRotation()), Math.sin(getRotation())))));
		WindowGame.damage2Joueur.setRotation((float) -(Math.toDegrees(Math.atan2(Math.cos(getRotation()), Math.sin(getRotation())))));
		WindowGame.damage3Joueur.setRotation((float) -(Math.toDegrees(Math.atan2(Math.cos(getRotation()), Math.sin(getRotation())))));
	}
	
	/**
	 * Effectue une rotation droite du joueur
	 * Et par conséquent modifie la direction et l'affichage du joueur et des textures de dégats si elles sont affichées
	 * @author Amine Boudraa
	 */
	public void rotationDroite(int delta) {
		this.rotation += (float) delta / 250;
		setdirectionX();
		setdirectionY();
		WindowGame.shipJoueur.setRotation((float) -(Math.toDegrees(Math.atan2(Math.cos(getRotation()), Math.sin(getRotation())))));
		WindowGame.damage2Joueur.setRotation((float) -(Math.toDegrees(Math.atan2(Math.cos(getRotation()), Math.sin(getRotation())))));
		WindowGame.damage3Joueur.setRotation((float) -(Math.toDegrees(Math.atan2(Math.cos(getRotation()), Math.sin(getRotation())))));
	}
	
	
	/**
	 * Affiche le joueur en fonction de ses déplacement
	 * Affiche les textures de dégats en fonctions des points de vies du joueur
	 * Affiche les différents bonus ramassés 
	 * @author Amine Boudraa
	 */
	public void render(Graphics g) {

		WindowGame.shipJoueur.draw(getX() - 56, getY() - 37);

		if (health == 2)
			WindowGame.damage2Joueur.draw(x - 56, y - 37);
		if (health == 1)
			WindowGame.damage3Joueur.draw(x - 56, y - 37);

		if (bonus[0])
			WindowGame.bonusVitesse.draw(x + 30, y + 50, 20, 20);
		//if (bonus[1])
			//WindowGame.bonusTeteChercheuse.draw(x - 50, y + 50, 20, 20);
		if (bonus[2])
			WindowGame.bonusTripleMissile.draw(x + 30, y - 60, 20, 20);
		if (bonus[3])
			WindowGame.bonusShield.draw(x - 50, y - 60, 20, 20);
	}

	/**
	 * Met à jour les informations du joueur
	 * Teste si le bonus de vitesse est activé 
	 * Effectue les rotations en fonction des touches pressées
	 * Met à jour la position en fonction de l'acceleration du joueur
	 * @author Amine Boudraa
	 */
	public void update(GameContainer container, int delta, TiledMap map) {

		//System.out.println(bonus[0] + " " +  bonus[1] + " " + bonus[2] + " " + bonus[3]);

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

	}

	/**
	 * Parcourt toute les tiles de la map(particules SOLID) et teste la collision entre le joueur et ces dernières
	 * @author Amine Boudraa
	 */
	public void collide(float futurX, float futurY, TiledMap map) {

		Image tile = map.getTileImage((int) futurX / map.getTileWidth(), (int) futurY / map.getTileHeight(), map.getLayerIndex("logic"));

		boolean collision = tile != null;
		if (!collision) {
			setX(futurX);
			setY(futurY);
		}

		else {
			int xTexture = (int) (map.getTileWidth() * (int)(futurX/map.getTileWidth()));
			int yTexture = (int) (map.getTileHeight() * (int)(futurY/map.getTileHeight()));
			Line2D.Float coteGauche = new Line2D.Float(xTexture, yTexture, xTexture, yTexture+tile.getHeight());
			Line2D.Float coteDroit = new Line2D.Float(xTexture+tile.getWidth(), yTexture, xTexture+tile.getWidth(), yTexture+tile.getHeight());
			Line2D.Float coteHaut = new Line2D.Float(xTexture, yTexture, xTexture+tile.getWidth(), yTexture);
			Line2D.Float coteBas = new Line2D.Float(xTexture, yTexture+tile.getHeight(), xTexture+tile.getWidth(), yTexture+tile.getHeight());

			
			Line2D.Float ligneJoueur = new Line2D.Float(futurX, futurY, x, y);
			
			if(coteGauche.intersectsLine(ligneJoueur) || coteDroit.intersectsLine(ligneJoueur)) {
				accelerationX *= -1;
				if (accelerationX < -2 || accelerationX > 2) {
					accelerationX /= 1.5;
				}
			}
			if(coteHaut.intersectsLine(ligneJoueur) || coteBas.intersectsLine(ligneJoueur)) {
				accelerationY *= -1;
				if (accelerationY < -2 || accelerationY > 2) {
					accelerationY /= 1.5;
				}
				
				
			}
		}
	}

	/**
	 * Détecte les collisions entre chaques joueurs 
	 * @author Amine Boudraa
	 */
	public void collidePlayer(ArrayList<ServeurJoueur> listeAdversaire) {

		for (ServeurJoueur joueur : listeAdversaire) {
			if (x > joueur.getX() - 25 && x < joueur.getX() + 25 && y > joueur.getY() - 25 && y < joueur.getY() + 25) {

				accelerationX *= -1;
				accelerationY *= -1;
			}
		}
	}
}
