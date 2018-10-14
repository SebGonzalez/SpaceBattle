package client;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Joueur {
	
	private String nom;
	private float x = 300, y = 300;
	private float xCamera = 300, yCamera = 300;
	public boolean keys_pressed[] = new boolean[3]; 
	private float rotation = (float) (Math.PI/2);
	
	private Image ship;
	
	
	private boolean moving = false;
	
	public Joueur() {
		x = 300;
		y = 300;
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

	public float getRotation() {
		return rotation;
	}

	public void rotationGauche() {
		this.rotation -= 0.005;
	}
	
	public void rotationDroite() {
		this.rotation += 0.005;
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(0, 0, 0, .5f));
		g.fillOval(getX() - 16,getY()+70, 32, 16);
		ship.draw(getX() - 56, getY() - 37);
	}
	
	public void update(GameContainer container, int delta, TiledMap map) {
		updatePosition(delta, map);
		updateCamera(container);
		ship.setRotation((float) -(Math.toDegrees(Math.atan2(Math.cos(getRotation()), Math.sin(getRotation())))));

	}
	
	public void updatePosition(int delta, TiledMap map) {
		if(keys_pressed[1] == true) rotationGauche();
		if(keys_pressed[2] == true) rotationDroite();
		
		if (keys_pressed[0]) {
			
			float directionX = (float) Math.cos(getRotation());
			float directionY = (float) Math.sin(getRotation());
			
			float futurX = getX() - .1f * delta * directionX * 2;
			float futurY = getY() - .1f * delta * directionY * 2;			
			
			/*Image tile = map.getTileImage((int) futurX / map.getTileWidth(),
					(int) futurY / map.getTileHeight(),map.getLayerIndex("logic"));
			// il y a colision si la tuile "tuture" existe
			boolean collision = tile != null;
			if (collision) {
				Color color = tile.getColor((int) futurX % map.getTileWidth(),(int) futurY % map.getTileHeight());
				collision = color.getAlpha() > 0;
			}*/

			//if (!collision) {
				setX(futurX);
				setY(futurY);
			//}

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
