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

	/**
	 * Méthode appelé lorsqu'un paquet d'un client est reçu
	 * Vérifie si le joueur n'entre pas en collision avec un bonus
	 * Vérifie si un bonus n'a pas expiré
	 * @param idJoueur
	 * @param datagram
	 */
	public void updateBonus(int idJoueur, DatagramUpdateServer datagram) {

		for (Bonus bonus : listeBonus) {
			datagram.listeBonus.add(bonus);
		}

		collideBonus(idJoueur);
		isExpired(idJoueur);

		ServeurJoueur player = gestionnaireJoueur.getJoueur(idJoueur);

		for (int i = 0; i < 4; i++)
			datagram.bonus[i] = player.getBonusState(i);

	}

	/**
	 * Méthode qui vérifie si le joueur n'entre pas en collision avec un bonus
	 * Si oui on active le bonus correspondant au joueur et le regénère
	 * @param idJoueur
	 */
	public void collideBonus(int idJoueur) {
		ServeurJoueur player = gestionnaireJoueur.getJoueur(idJoueur);
		for (Bonus bonus : listeBonus) {

			if (bonus.getX() > player.getX() - 56 && bonus.getX() < player.getX() + 56
					&& bonus.getY() + 30 > player.getY() - 37 && bonus.getY() + 30 < player.getY() + 37) {
				switch (bonus.getType()) {

				case VitesseUp:
					if (player.getBonusState(0) == false) {
						player.enableBonus(0);
						bonus.disappear();
						//System.out.println("étoile ramassé");
					}
					break;

				case TeteChercheuse:
					if (player.getBonusState(1) == false) {
						player.enableBonus(1);
						bonus.disappear();
						System.out.println("éclair ramassé");
					}

					break;

				case TripleMissile:
					if (player.getBonusState(2) == false) {
						player.enableBonus(2);
						bonus.disappear();
						
					}

					break;

				case Bouclier:
					if (player.getBonusState(3) == false) {
						player.enableBonus(3);
						bonus.disappear();
						System.out.println("shield ramassé");
					}

					break;
				}

			}
		}

	}

	/**
	 * On vérifie que les bonus du joueur passé en paramètre sont toujours actif
	 * Sinon on désactive le bonus correponsdant
	 * @param id
	 */
	public void isExpired(int id) {
		ServeurJoueur player = gestionnaireJoueur.getJoueur(id);
		for (int i = 0; i < 4; i++) {
			if (player.getTimerBonus(i) != 0 && System.currentTimeMillis() - player.getTimerBonus(i) > Bonus.lifetime) {
				player.disableBonus(i);
			}
		}
	}
}
