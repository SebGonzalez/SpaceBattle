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
	
	public void render(Graphics g, int teamJoueur) {
		if(numTeam == teamJoueur)
			WindowGame.flag1.draw(x-32, y-32);
		else
			WindowGame.flag2.draw(x-32, y-32);
	}
}
