package server;

import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import client.IHM.WindowGame;
public class Bonus {
	
	private int type; //TripleM - VitesseM -TeteC - Shield
	private int lifetime;
	private int x;
	private int y;
	private boolean taken = false;
	
	public Bonus() {
		type = 1;
		lifetime = 200;
		x = 0;
		y = 0;
		taken = false;
	}
	
	
	public Bonus(int lifetime) {
		
		Random rand = new Random();
		this.type = rand.nextInt(4) + 0;
		this.lifetime = lifetime;
		this.x = rand.nextInt(2600) + 900;
		this.y = rand.nextInt(2300) + 900;
		
	}

	
	public void render(Graphics g) {
		switch(type) {
		case 0 : WindowGame.bonus1.draw(getX(), getY());
			break;
		case 1 : WindowGame.bonus2.draw(getX(), getY());
		break;
		case 2 : WindowGame.bonus3.draw(getX(), getY());
		break;
		case 3 : WindowGame.bonus4.draw(getX(), getY());
		break;
		}
		
	}
	
	public int getX() {
		return x;
	}
	
	
	public int getY() {
		return y;
	}
	
	public void take() {
		taken = true;
		}
	
	public void release() {
		taken = false;
	}
	
	public int getType() {
		return type;
	}
	
	public int getlifetime() {
		return lifetime;
	}
	
	public void disappear() {
		Random rand = new Random();
		this.type = rand.nextInt(4) + 0;
		this.x = rand.nextInt(2600) + 900;
		this.y = rand.nextInt(2300) + 900;
		
	}
}
