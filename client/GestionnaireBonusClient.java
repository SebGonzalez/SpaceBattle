package client;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import client.Model.Bonus;
import client.Model.Joueur;

public class GestionnaireBonusClient {

	private ArrayList<Bonus> listeBonus;
	private ArrayList<Bonus> reception;
	public Boolean bonus[] = new Boolean[4];


	public GestionnaireBonusClient() {
		listeBonus = new ArrayList<>();
		reception = new ArrayList<>();
	}

	public void render(Graphics g) {
		for (Bonus a : listeBonus) {
			a.render(g);
		}
	}

	public void setReception(ArrayList<Bonus> reception, Boolean bonus[]) {
		this.reception = reception;

		for(int i = 0 ; i < 4 ; i++)
			this.bonus[i] = bonus[i];

	}

	public void update() {
		if (reception != null) {
			listeBonus.clear();
			listeBonus.addAll(reception);
			reception = null;
			}
		
	}
}
