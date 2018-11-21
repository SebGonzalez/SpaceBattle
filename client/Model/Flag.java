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
	
	public void render(Graphics g) {
		if(numTeam == 1)
			WindowGame.flag1.draw(x-16, y-16);
		else
			WindowGame.flag2.draw(x-16, y-16);
	}
}
