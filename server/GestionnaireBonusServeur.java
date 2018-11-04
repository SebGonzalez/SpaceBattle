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
	private long bonusStarts[] = new long[4];

	public GestionnaireBonusServeur() {

		listeBonus = new ArrayList<>();

		for (int i = 0; i < 60; i++) {
			listeBonus.add(new Bonus(5000));
		}
		
		for( int i = 0; i < 4 ; i++)
			bonusStarts[i] = 0;

	}

	public void updateBonus(DatagramUpdateServer datagram, int idjoueur) {

		for (Bonus bonus : listeBonus) {
			datagram.listeBonus.add(bonus);
		}

		collideBonus(idjoueur,datagram);
		isExpired(bonusStarts,idjoueur);
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
								if ( player.getBonusState(0) == false) {
									bonusStarts[0] = System.currentTimeMillis();
									player.enableBonus(0);
									listeBonus.get( indice ).disappear();
								}
								break;
								
							case 1:
								if ( player.getBonusState(1) == false) {
									bonusStarts[1] = System.currentTimeMillis();
									player.enableBonus(1);
									listeBonus.get( indice ).disappear();
								}
								break;
								
							case 2:
								if ( player.getBonusState(2) == false) {
									bonusStarts[2] = System.currentTimeMillis();
									player.enableBonus(2);
									listeBonus.get( indice ).disappear();
								}
								break;
								
							case 3:
								if ( player.getBonusState(3) == false) {
									bonusStarts[3] = System.currentTimeMillis();
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
	
	public void isExpired(long startTime[],int id) {
			
			for( int i = 0 ; i < 4 ; i ++) {
				if( System.currentTimeMillis() - startTime[i] > 5000 && startTime[i] != 0 ) {
					for ( Entry<Integer, ServeurJoueur> entry : Serveur.gestionnaireJoueur.listePlayers.entrySet() ) {	
						int cle = entry.getKey();
							if ( cle == id ) { 
								ServeurJoueur player = entry.getValue();
								player.disableBonus(i);
								startTime[i] = 0;
								System.out.println("bonus" +i+ " a expiré");
							}
						}
					}
				}
			}
	
	
	
}
