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
import newtork.DatagramUpdateClient;
import newtork.DatagramUpdateServer;

public class GestionnaireBonusServeur {

	private static final int NOMBRE_BONUS = 60;
	
	private ArrayList<Bonus> listeBonus;
	

	public GestionnaireBonusServeur() {

		listeBonus = new ArrayList<>();

		for (int i = 0; i < NOMBRE_BONUS; i++) {
			listeBonus.add(new Bonus(5000));
		}
		
		
	}

	public void updateBonus(DatagramUpdateServer datagram, int idjoueur) {

		for (Bonus bonus : listeBonus) {
			datagram.listeBonus.add(bonus);
		}

		collideBonus(idjoueur,datagram);
		isExpired(idjoueur);
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
						case TripleMissile:
								if ( player.getBonusState(0) == false) {
									player.enableBonus(0);
									listeBonus.get( indice ).disappear();
								}
								break;
								
							case VitesseUp:
								if ( player.getBonusState(1) == false) {
									player.enableBonus(1);
									listeBonus.get( indice ).disappear();
								}
								break;
								
							case TeteChercheuse:
								if ( player.getBonusState(2) == false) {
									player.enableBonus(2);
									listeBonus.get( indice ).disappear();
								}
								break;
								
							case Bouclier:
								if ( player.getBonusState(3) == false) {
									player.enableBonus(3);
									listeBonus.get( indice ).disappear();
								}
								break;
							}
						
					}
						indice ++;
				}

			}
		}
		
	}
	
	public void isExpired(int id) {
			
			for( int i = 0 ; i < 4 ; i ++) {
				for ( Entry<Integer, ServeurJoueur> entry : Serveur.gestionnaireJoueur.listePlayers.entrySet() ) {	
					int cle = entry.getKey();
							if ( cle == id ) { 
								ServeurJoueur player = entry.getValue();
								if( System.currentTimeMillis() - player.getTimerBonus(i) > 5000 && player.getTimerBonus(i) != 0 ) {
								player.disableBonus(i);
								System.out.println("bonus" +i+"du joueur :  "+id+" a expiré");
								}
								
								
							}
						}
					}
				}
			}
	
	
	

