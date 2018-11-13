package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import client.Model.Missile;
import network.DatagramUpdateClient;
import network.DatagramUpdateServer;

/**
 * Classe de gestion des joueurs par le serveur
 * 
 * @author Sébastien Gonzalez
 *
 */
public class GestionnaireJoueur {

	Map<Integer, ServeurJoueur> listePlayers = new HashMap<Integer, ServeurJoueur>();
	

	public void addJoueur(int id) {
		ServeurJoueur newPlayer = new ServeurJoueur(id);
		listePlayers.put(id, newPlayer);
	}

	/**
	 * Mets à jour les informations reçues du client et prépare la réponse
	 * 
	 * @param idJoueur
	 * @param datagram
	 * @return
	 */
	public DatagramUpdateServer updateJoueur(int idJoueur, DatagramUpdateClient datagram) {

		boolean nouveauJoueur = true;
		
		DatagramUpdateServer datagramReponse = new DatagramUpdateServer();

		checkCollision(datagram.listeMissile, idJoueur);
		checkCollisionJoueur(datagramReponse);
		
		
		/* CEST ICI QUE CA COUILLE, JE CROIS QUE ACCELERATION SE MET TROP VITE A JOUR POUR PRENDRE EN COMPTE LE *= -1*/
		
		datagramReponse.accelerationX = datagram.accelerationX;
		datagramReponse.accelerationY = datagram.accelerationY;
		
		System.out.println();

		for (Entry<Integer, ServeurJoueur> entry : listePlayers.entrySet()) {
			int cle = entry.getKey();
			if (cle == idJoueur) {
				ServeurJoueur player = entry.getValue();
				player.setX(datagram.x);
				player.setY(datagram.y);
				player.setR(datagram.r);
				player.setAccelerationX(datagram.accelerationX);
				player.setAccelerationY(datagram.accelerationY);
				
				player.setListeMissile(datagram.listeMissile);
				nouveauJoueur = false;
			} else {
				datagramReponse.listeAdversaire.add(entry.getValue());
			}
		}
		
		if(nouveauJoueur) {
			ServeurJoueur player = new ServeurJoueur(idJoueur);
			player.setX(datagram.x);
			player.setY(datagram.y);
			player.setR(datagram.r);
			player.setAccelerationX(datagram.accelerationX);
			player.setAccelerationY(datagram.accelerationY);
			player.setListeMissile(datagram.listeMissile);
			listePlayers.put(idJoueur, player);
		}

		return datagramReponse;
	}

	public void checkCollision(ArrayList<Missile> listeMissile, int idC) {
		
		if (listeMissile.size() > 0) {
			Iterator<Entry<Integer, ServeurJoueur>> entryIt2 = listePlayers.entrySet().iterator();
			while (entryIt2.hasNext()) {
				Entry<Integer, ServeurJoueur> entry2 = entryIt2.next();
				ServeurJoueur joueur = entry2.getValue();
				if (idC != joueur.getId()) {

					for (Missile m : listeMissile) {
						if (m.collision(joueur)) {
							
							if(!joueur.getBonusState(3)) {
							System.out.println("MORT");
							Serveur.server.sendToTCP(joueur.getId(), "ko");
							entryIt2.remove();
							
							}
							else if (joueur.getBonusState(3)) System.out.println("joueur " + joueur.getId() + "shielded");
						}
					}
				}
			}
		}
	}
	

	public void checkCollisionJoueur(DatagramUpdateServer datagram) {
		
			Iterator<Entry<Integer, ServeurJoueur>> listejoueur = listePlayers.entrySet().iterator();
		
		while ( listejoueur.hasNext() ) {
			Entry<Integer, ServeurJoueur> entry1 = listejoueur.next();
			ServeurJoueur joueur1 = entry1.getValue();
		while ( listejoueur.hasNext() ) {
			Entry<Integer, ServeurJoueur> entry2 = listejoueur.next();
			ServeurJoueur joueur2 = entry2.getValue();
			
			joueur1.JoueurCollide(joueur2,datagram);
			
			}
		}
	}
	

	/**
	 * Supprime un joueur de la liste
	 * 
	 * @param id
	 */
	public void removeJoueur(int id) {
		for (Entry<Integer, ServeurJoueur> entry : listePlayers.entrySet()) {
			if (id == entry.getKey()) {
				listePlayers.remove(entry.getKey());
				break;
			}

		}
	}

}
