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
			listeBonus.add(new Bonus(5000));
		}		
	}

	public ServeurJoueur getPlayerfromID(int id) {
			
		ServeurJoueur player = new ServeurJoueur();
		
		for ( Entry<Integer, ServeurJoueur> entry : gestionnaireJoueur.listePlayers.entrySet() ) {	
			int cle = entry.getKey();
			if ( cle == id )  
			{
				player =  entry.getValue();
			}
		}
		
		return player;
	}
	
	public void updateBonus(int idjoueur, DatagramUpdateServer datagram) {

		for (Bonus bonus : listeBonus) {
			datagram.listeBonus.add(bonus);
		}
		
		
		collideBonus(idjoueur,datagram);
	 	isExpired(idjoueur);
	 	
	 	
	 	ServeurJoueur player = getPlayerfromID(idjoueur);
	 	
		for(int i = 0 ; i < 4 ; i++) 
			datagram.bonus[i] = player.getBonusState(i);
				
	 	
	}

	public void collideBonus(int id,DatagramUpdateServer datagram) {
		int indice = 0;
		ServeurJoueur player = getPlayerfromID(id);
			for (Bonus bonus : listeBonus) {
					if ( bonus.getX() > player.getX()-45 && bonus.getX() < player.getX()+45 && bonus.getY() > player.getY()-45 && bonus.getY() < player.getY()+45) {
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
									System.out.println("shield ramass�");
								}
								
								break;
							}
						
						}
							indice ++;
					}

				}
		
	
	
		public void isExpired(int id) {
			ServeurJoueur player = getPlayerfromID(id);
			for( int i = 0 ; i < 4 ; i ++) {
				if( System.currentTimeMillis() - player.getTimerBonus(i) > 5000 && player.getTimerBonus(i) != 0 ) {
								player.disableBonus(i);
								System.out.println("bonus" +i+"du joueur :  "+id+" a expir�");
						}
					}
				}
	
	
	
	
	
					
}
			
	

		
	
	
	

