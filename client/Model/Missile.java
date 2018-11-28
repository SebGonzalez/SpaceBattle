package client.Model;

import org.lwjgl.Sys;
import org.newdawn.slick.Graphics;

import client.IHM.WindowGame;
import server.ServeurJoueur;

public class Missile {
	private float x;
	private float y;
	
	private float directionX;
	private float directionY;
	
	private long time;
	private boolean autoDestruction = false;
	
	public Missile(float x, float y, float rotation) {
		this.x = x;
		this.y = y;
		directionX = (float) Math.cos(rotation);
		directionY = (float) Math.sin(rotation);
		time = System.currentTimeMillis();
	}
	
	public Missile(float x, float y, float directionX, float directionY) {
		this.x = x;
		this.y = y;
		this.directionX = directionX;
		this.directionY = directionY;
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

	public void update(int delta, Joueur joueur) {
		if(System.currentTimeMillis() - time >= 10000) {
			autoDestruction = true;
		}
		
		x += - .1f * delta * directionX * 7;
		y += - .1f * delta * directionY * 7;			
	}
	
	public void updateTeteChercheuse(int delta,ServeurJoueur joueur) {
		if(System.currentTimeMillis() - time >= 10000) {
			autoDestruction = true;
		}
	
		x += - .1f * delta * joueur.accelerationX * 4;
		y += - .1f * delta * joueur.accelerationY * 4;		
		
	}
	
	public boolean collision(ServeurJoueur joueur) {
		if(x > joueur.getX()-56 && x < joueur.getX()+56 && y > joueur.getY()-37 && y < joueur.getY()+37) return true;
		return false;
	}
	
	public void render(Graphics g) {
		g.pushTransform();
		g.rotate(x, y, (float) -(Math.toDegrees(Math.atan2( (double)directionX, (double)directionY))));
		WindowGame.missileJoueur.draw(x-6, y-18);
		g.popTransform();
	}

	public float getDirectionX() {
		return directionX;
	}

	public void setDirectionX(float directionX) {
		this.directionX = directionX;
	}

	public float getDirectionY() {
		return directionY;
	}

	public void setDirectionY(float directionY) {
		this.directionY = directionY;
	}

	public boolean isAutoDestruction() {
		return autoDestruction;
	}

	public void setAutoDestruction(boolean autoDestruction) {
		this.autoDestruction = autoDestruction;
	}
	
	
}
