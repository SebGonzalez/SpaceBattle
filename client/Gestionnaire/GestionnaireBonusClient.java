package client.Gestionnaire;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import client.Model.Bonus;

public class GestionnaireBonusClient {

	private ArrayList<Bonus> listeBonus;
	private ArrayList<Bonus> reception;

	public GestionnaireBonusClient() {
		listeBonus = new ArrayList<>();
		reception = new ArrayList<>();
	}
	/**
	 * Affiche les différents bonus présents sur la carte
	 * @author Amine Boudraa
	 */
	public void render(Graphics g) {
		for (Bonus a : listeBonus) {
			a.render(g);
		}
	}
	/**
	 * Stocke dans une variable temporaire le tableau de bonus reçu par le serveur avant de le stocker localement
	 * @author Amine Boudraa
	 */
	public void setReception(ArrayList<Bonus> reception) {
		this.reception = reception;
	}
	
	/**
	 * Mise à jour de la liste de bonus reçue par le serveur
	 * @author Amine Boudraa
	 */
	public void update() {
		if (reception != null) {
			listeBonus.clear();
			listeBonus.addAll(reception);
			reception = null;
			}
	}
}
