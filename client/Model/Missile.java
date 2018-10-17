package client.Model;

import org.lwjgl.Sys;
import org.newdawn.slick.Graphics;

import client.IHM.WindowGame;

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

	public void update(int delta) {
		if(System.currentTimeMillis() - time >= 3000) {
			autoDestruction = true;
		}
		
		x += - .1f * delta * directionX * 4;
		y += - .1f * delta * directionY * 4;			
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
