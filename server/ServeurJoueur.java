package server;

/**
 * Instance d'un joueur niveau serveur
 * @author Sébastien Gonzalez
 *
 */
public class ServeurJoueur {

	private int id;
	private double x;
	private double y;
	
	public ServeurJoueur() {
		this.id = -1;
		x = 0;
		y = 0;
	}
	
	public ServeurJoueur(int id) {
		this.id = id;
		x = 0;
		y = 0;
	}
	
	public ServeurJoueur(int id, double x, double y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
}
