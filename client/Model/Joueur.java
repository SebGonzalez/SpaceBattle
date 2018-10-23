package client.Model;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Joueur {
	
	private String nom;
	private float x = 900, y = 900;
	private float xCamera = 900, yCamera = 900;
	public boolean keys_pressed[] = new boolean[3]; 
	private float accelerationX = 0;
	private float accelerationY = 0;
	private float rotation = (float) (Math.PI/2);
	private float directionX = (float) Math.cos(rotation);
	private float directionY = (float) Math.sin(rotation);
	private Image ship;
	
	
	private boolean moving = false;
	
	public Joueur() {
		x = 900;
		y = 900;
		nom = "test23";
		for(int i=0;i<3;i++) {
			keys_pressed[i] = false;
		}
	}
	
	public Joueur(String nom, float x, float y) {
		this.nom = nom;
		this.x = x;
		this.y = y;
	}
	
	public void loadImage() {
		try {
			ship = new Image("ressources/sprites/sprite2.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	
	public boolean isMoving() {
		return moving;
	}
	
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	

	public float getxCamera() {
		return xCamera;
	}

	public void setxCamera(float xCamera) {
		this.xCamera = xCamera;
	}

	public float getyCamera() {
		return yCamera;
	}

	public void setyCamera(float yCamera) {
		this.yCamera = yCamera;
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
	
	public void setdirectionX(float value) {
		directionX = (float) Math.cos(value);
	}
	
	public void setdirectionY(float value) {
		directionY = (float) Math.sin(value);
	}
	
	
	public void addaccelerationX(float value) {
    	accelerationX += value;
	}
	
	public void addaccelerationY(float value) {
		accelerationY += value;
	}
	
	public void accelerate() {
		
		if(getaccelerationX() + getdirectionX()/85 > -4 && getaccelerationX() + getdirectionX()/85 < 4)
			addaccelerationX(getdirectionX()/85);
		
		if(getaccelerationY() + getdirectionY()/85 > -4 && getaccelerationY() + getdirectionY()/85 < 4)
		addaccelerationY(getdirectionY()/85);
		
	}

	public float getRotation() {
		return rotation;
	}

	public void rotationGauche() {
		this.rotation -= 0.02;
		setdirectionX(getRotation());
		setdirectionY(getRotation());
		ship.setRotation((float) -(Math.toDegrees(Math.atan2(Math.cos(getRotation()), Math.sin(getRotation())))));
	}
	
	public void rotationDroite() {
		this.rotation += 0.02;
		setdirectionX(getRotation());
		setdirectionY(getRotation());
		ship.setRotation((float) -(Math.toDegrees(Math.atan2(Math.cos(getRotation()), Math.sin(getRotation())))));
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(0, 0, 0, .5f));
		g.fillOval(getX() - 16,getY() + 37, 32, 16);
		ship.draw(getX() - 56, getY() - 37);
	}
	
	public void update(GameContainer container, int delta, TiledMap map) {
		updatePosition(delta, map);
		updateCamera(container);
	}
	
	
	public void updatePosition(int delta, TiledMap map) {
		
		
		if(keys_pressed[0] == true) accelerate();
		if(keys_pressed[1] == true) rotationGauche();
		if(keys_pressed[2] == true) rotationDroite();
		
			
			float futurX = getX() - .1f * delta * accelerationX;
			float futurY = getY() - .1f * delta * accelerationY;	
			
			setX(futurX);
			setY(futurY);
			
			Image tile = map.getTileImage((int) futurX / map.getTileWidth(),
					(int) futurY / map.getTileHeight(),map.getLayerIndex("logic"));
			// il y a colision si la tuile existe
			boolean collision = tile != null;
			if (!collision) {
				setX(futurX);
				setY(futurY);
			}
			else {
				System.out.println(futurX + " " + futurY);
				if(x < 900 || x > 2600)
					accelerationX *= -1;
				if(y < 650 || y > 2300)
					accelerationY *= -1;
			}


	}
	
	public void updateCamera(GameContainer container) {
		int w = container.getWidth() / 4;
		if (getX() > getxCamera() + w)
			setxCamera(getX() - w);
		if (getX() < getxCamera() - w)
			setxCamera(getX() + w);
		int h = container.getHeight() / 4;
		if (getY() > getyCamera() + h)
			setyCamera(getY() - h);
		if (getY() < getyCamera() - h)
			setyCamera(getY() + h);
	}
	
	
}
