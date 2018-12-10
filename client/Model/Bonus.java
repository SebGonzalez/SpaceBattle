package client.Model;

import org.newdawn.slick.Graphics;

import client.IHM.GestionnaireImagesIHM;

public class Bonus {

	public static final int lifetime = 5000;

	private TypeBonus type; // TripleM - VitesseM -TeteC - Shield
	private int x;
	private int y;

	public Bonus() {
		this.type = TypeBonus.randomValue();
		this.x = (int) ((Math.random() * ((2500 - 1050) + 1)) + 1050); // rand.nextInt(2500) + 1050;
		this.y = (int) ((Math.random() * ((2300 - 650) + 1)) + 650);

	}
	
	/**
	 * Affiche les différents bonus 
	 * 
	 */
	public void render(Graphics g) {
		switch (type) {
		case TripleMissile:
			GestionnaireImagesIHM.getRessource("bonusTripleMissile").draw(getX(), getY());
			break;
		case VitesseUp:
			GestionnaireImagesIHM.getRessource("bonusVitesse").draw(getX(), getY());
			break;
		// case TeteChercheuse : WindowGame.bonusTeteChercheuse.draw(getX(), getY());
		 //	break;
	    case Bouclier:
	    	GestionnaireImagesIHM.getRessource("bonusShield").draw(getX(), getY());
			break;
		default:
			break;
		}

	}
	
	/**
	 * Renvoie la coordonnée x de l'objet Bonus
	 * 
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Renvoie la coordonnée y de l'objet Bonus
	 * 
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Renvoie le type de l'objet Bonus
	 * 
	 */
	public TypeBonus getType() {
		return type;
	}

	public int getlifetime() {
		return lifetime;
	}
	
	/**
	 * Modifie au hasard les coordonnées et le type du Bonus
	 * 
	 */
	public void disappear() {
		this.type = TypeBonus.randomValue();
		this.x = (int) ((Math.random() * ((2500 - 1050) + 1)) + 1050);
		this.y = (int) ((Math.random() * ((2300 - 650) + 1)) + 650);

	}
}
