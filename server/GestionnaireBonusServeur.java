package server;

import java.util.ArrayList;

import server.ServeurJoueur;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import client.Model.Bonus;
import client.Model.Missile;
import network.DatagramUpdateClient;
import network.DatagramUpdateServer;

public class GestionnaireBonusServeur {

	private static final int NOMBRE_BONUS = 10;

	private GestionnaireJoueur gestionnaireJoueur;
	private ArrayList<Bonus> listeBonus;

	public GestionnaireBonusServeur(GestionnaireJoueur gestionnaireJoueur) {

		this.gestionnaireJoueur = gestionnaireJoueur;
		listeBonus = new ArrayList<>();

		for (int i = 0; i < NOMBRE_BONUS; i++) {
			listeBonus.add(new Bonus());
		}
	}

	public void updateBonus(int idJoueur, DatagramUpdateServer datagram) {

		for (Bonus bonus : listeBonus) {
			datagram.listeBonus.add(bonus);
		}

		collideBonus(idJoueur, datagram);
		isExpired(idJoueur);

		ServeurJoueur player = gestionnaireJoueur.getJoueur(idJoueur);

		for (int i = 0; i < 4; i++)
			datagram.bonus[i] = player.getBonusState(i);

	}

	public void collideBonus(int idJoueur, DatagramUpdateServer datagram) {
		ServeurJoueur player = gestionnaireJoueur.getJoueur(idJoueur);
		for (Bonus bonus : listeBonus) {

			if (bonus.getX() > player.getX() - 56 && bonus.getX() < player.getX() + 56
					&& bonus.getY() + 30 > player.getY() - 37 && bonus.getY() + 30 < player.getY() + 37) {
				switch (bonus.getType()) {

				case TripleMissile:
					if (player.getBonusState(0) == false) {
						player.enableBonus(0);
						bonus.disappear();
					}
					break;

				case VitesseUp:
					if (player.getBonusState(1) == false) {
						player.enableBonus(1);
						bonus.disappear();
					}

					break;

				case TeteChercheuse:
					if (player.getBonusState(2) == false) {
						player.enableBonus(2);
						bonus.disappear();
					}

					break;

				case Bouclier:
					if (player.getBonusState(3) == false) {
						player.enableBonus(3);
						bonus.disappear();
					}

					break;
				}

			}
		}

	}

	public void isExpired(int id) {
		ServeurJoueur player = gestionnaireJoueur.getJoueur(id);
		for (int i = 0; i < 4; i++) {
			if (player.getTimerBonus(i) != 0 && System.currentTimeMillis() - player.getTimerBonus(i) > Bonus.lifetime) {
				player.disableBonus(i);
			}
		}
	}
}
