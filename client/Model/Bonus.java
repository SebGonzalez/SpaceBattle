package client.Model;

import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import client.IHM.WindowGame;
public class Bonus {
	
	private TypeBonus type; //TripleM - VitesseM -TeteC - Shield
	private int lifetime;
	private int x;
	private int y;

	public Bonus() {
		type = TypeBonus.VitesseUp;
		lifetime = 200;
		x = 0;
		y = 0;
	}
	
	
	public Bonus(int lifetime) {
		Random rand = new Random();
		this.type = TypeBonus.randomValue();
		this.lifetime = lifetime;
		this.x = (int) ((Math.random() * ((2500 - 1050) + 1)) + 1050); // rand.nextInt(2500) + 1050; 
		this.y = (int) ((Math.random() * ((2300 - 650 ) + 1)) + 650);
		
	}

	
	public void render(Graphics g) {
		switch(type) {
		case TripleMissile : WindowGame.bonus1.draw(getX(), getY());
			break;
	//	case VitesseUp : WindowGame.bonus2.draw(getX(), getY());
	//	break;
		case TeteChercheuse : WindowGame.bonus3.draw(getX(), getY());
		break;
		case Bouclier : WindowGame.bonus4.draw(getX(), getY());
		break;
		}
		
	}
	
	public int getX() {
		return x;
	}
	
	
	public int getY() {
		return y;
	}
	
	public TypeBonus getType() {
		return type;
	}
	
	public int getlifetime() {
		return lifetime;
	}
	
	public void disappear() {
		Random rand = new Random();
		this.type = TypeBonus.randomValue();
			this.x = (int) ((Math.random() * ((2500 - 1050) + 1)) + 1050); // rand.nextInt(2500) + 1050; 
			this.y = (int) ((Math.random() * ((2300 - 650 ) + 1)) + 650);
		
	}
}
