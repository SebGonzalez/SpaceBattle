package client.Model;

import org.newdawn.slick.Graphics;

import client.IHM.WindowGame;

public class Flag {
	
	private float xBase;
	private float yBase;
	
	private float x;
	private float y;
	
	private int numTeam;
	private boolean pris = false;
	
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

	public void render(Graphics g, int teamJoueur) {
		if(numTeam == teamJoueur) {
			WindowGame.baseFlag1.draw(xBase-64, yBase);
			WindowGame.flag1.draw(x-32, y-32);
		}
		else {
			WindowGame.baseFlag2.draw(xBase-64, yBase);
			WindowGame.flag2.draw(x-32, y-32);
		}
	}
	
	public boolean collision(double d, double e) {
		if(x > d-56 && x < d+56 && y > e-37 && y < e+37) return true;
		return false;
	}
	
	public boolean collisionBase(double xJoueur, double yJoueur) {
		if(xBase > xJoueur-56 && xBase < xJoueur+56 && yBase+64 > yJoueur-37 && yBase < yJoueur+37) return true;
		return false;
	}

	public void resetPos() {
		x = xBase;
		y = yBase;
	}
}
