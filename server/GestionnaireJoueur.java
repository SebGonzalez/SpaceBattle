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

		for (Entry<Integer, ServeurJoueur> entry : listePlayers.entrySet()) {
			int cle = entry.getKey();
			if (cle == idJoueur) {
				ServeurJoueur player = entry.getValue();
				player.setX(datagram.x);
				player.setY(datagram.y);
				player.setR(datagram.r);
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
							System.out.println("MORT");
							Serveur.server.sendToTCP(joueur.getId(), "ko");
							entryIt2.remove();
						}
					}

					// for bonus
				}
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
