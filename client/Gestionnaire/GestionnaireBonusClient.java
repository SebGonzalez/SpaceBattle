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

	public void render(Graphics g) {
		for (Bonus a : listeBonus) {
			a.render(g);
		}
	}

	public void setReception(ArrayList<Bonus> reception) {
		this.reception = reception;
	}

	public void update() {
		if (reception != null) {
			listeBonus.clear();
			listeBonus.addAll(reception);
			reception = null;
			}
	}
}
