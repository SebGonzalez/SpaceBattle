package server;

import java.util.ArrayList;

import server.Bonus;
import server.ServeurJoueur;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import client.Model.Missile;
import newtork.DatagramUpdateClient;
import newtork.DatagramUpdateServer;

public class GestionnaireBonusServeur {

	private ArrayList<Bonus> listeBonus;

	public GestionnaireBonusServeur() {

		listeBonus = new ArrayList<>();

		for (int i = 0; i < 60; i++) {
			listeBonus.add(new Bonus(5000));
		}

	}

	public void updateBonus(DatagramUpdateServer datagram, int idjoueur) {

		for (Bonus bonus : listeBonus) {
			datagram.listeBonus.add(bonus);
		}

		//collideBonus(idjoueur,datagram);
	}

	public void collideBonus(int id,DatagramUpdateServer datagram) {
		int indice = 0;
	
		for (Entry<Integer, ServeurJoueur> entry : Serveur.gestionnaireJoueur.listePlayers.entrySet()) {	
			int cle = entry.getKey();
			if (cle == id) { //(cle == id de connexion du client)
				ServeurJoueur player = entry.getValue();
				for (Bonus bonus : listeBonus) {
					if ( bonus.getX() > player.getX()-25 && bonus.getX() < player.getX()+25 && bonus.getY() > player.getY()-25 && bonus.getY() < player.getY()+25) {
						switch ( bonus.getType() ) {
						
							case 0:
								if( !player.bonus[0] ) {
									listeBonus.get( indice ).disappear();
									player.bonusVitesseUP( datagram );
									player.bonus[0] = true;
								}
								break;
							case 1:
								// bonus 2
								break;
							case 2:
								// bonus 3
								break;
							case 3:
								// bonus 4
								break;
							}
						
					}
						indice ++;
				}

			}
		}
		
	}
	
	
}
