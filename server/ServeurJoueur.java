package server;

import java.util.ArrayList;

import client.Model.Missile;

/**
 * Instance d'un joueur niveau serveur
 * 
 *
 */
public class ServeurJoueur {
	
	public static int generateurTeam = 1;
	
	private String name;
	private int id;
	private float x;
	private float y;
	public double accelerationX;
	public double accelerationY;
	private float r;
	private ArrayList<Missile> listeMissile;
	public Boolean bonus[] = new Boolean[4];
	private long bonusTimer[] = new long[4];
	private int team;
	private int health = 3;
	 
	public ServeurJoueur() {		
	}
	
	public ServeurJoueur(int id) {
		this.id = id;
		x = 0;
		y = 0;
		r = (float) Math.PI/2;
		listeMissile = new ArrayList<>();
		for(int i=0;i<4;i++) {
			bonus[i] = false;
		}
		
		for( int i = 0; i < 4 ; i++)
			bonusTimer[i] = 0;

		team = generateurTeam;
		generateurTeam++;
		if(generateurTeam == 3) generateurTeam = 1;
	}

	/**
	 * Activation du bonus lorsqu'il est ramassé
	 * @param indice
	 */
	public void enableBonus(int indice) {
		bonus[indice] = true;
		bonusTimer[indice] = System.currentTimeMillis();
	}
	
	/**
	 * Désactivation du bonus lorsqu'il est expiré
	 * @param indice
	 */
	public void disableBonus(int indice) {
		bonus[indice] = false;
		bonusTimer[indice] = 0;
	}
	
	/**
	 * Retourne l'état du bonus (true si le bonus est actif est false dans le cas contraire)
	 * @param indice
	 * @return
	 */
	public boolean getBonusState(int indice) {
		return bonus[indice];
	}

	/**
	 * Retourne le timer du bonus
	 * @param indice
	 * @return
	 */
	public long getTimerBonus(int indice) {
		return bonusTimer[indice];
	}
	
	/**
	 * Méthode appelé lorsque le joueur se fait tirer dessus
	 */
	public void damage() {
		health -= 1;
	}
	
	/**
	 * Méthode appelé lorsque le joueur se soigne
	 */
	public void heal() {
		if(health <= 2)
		health += 1;
	}
	
	/**
	 * Retourne la vie du joueur
	 */
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public void setaccelerationX(double X) {
		this.accelerationX = X;
	}
	
	public void setaccelerationY(double Y) {
		this.accelerationY = Y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getR() {
		return r;
	}
	
	public void setR(float r) {
		this.r = r;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	public ArrayList<Missile> getListeMissile() {
		return listeMissile;
	}

	public void setListeMissile(ArrayList<Missile> listeMissile) {
		this.listeMissile = listeMissile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
