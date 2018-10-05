package main;

public class Personnage {
	
	private String nom;
	private float x = 300, y = 300;
	private float xCamera = 300, yCamera = 300;
	private int direction = 0;
	private boolean moving = false;
	
	public Personnage() {
		x = 300;
		y = 300;
		nom = "test23";
	}
	
	public Personnage(String nom, float x, float y) {
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
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
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
	
	
}
