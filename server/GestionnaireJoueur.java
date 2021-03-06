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
 * 
 *
 */
public class GestionnaireJoueur {

	Map<Integer, ServeurJoueur> listePlayers = new HashMap<Integer, ServeurJoueur>();

	public void addJoueur(int id) {
		ServeurJoueur newPlayer = new ServeurJoueur(id);
		listePlayers.put(id, newPlayer);
	}

	public void addJoueur(ServeurJoueur nouveauJoueur) {
		listePlayers.put(nouveauJoueur.getId(), nouveauJoueur);
	}

	public ServeurJoueur getJoueur(int idJoueur) {
		return listePlayers.get(idJoueur);
	}

	/**
	 * Mets à jour les informations reçues du client et prépare la réponse
	 * 
	 * @param idJoueur
	 *            du joueur courant
	 * @param datagram
	 *            reçu du client
	 * @return un datagram reponse qui sera envoyé au serveur
	 * 
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
				player.setaccelerationX(datagram.accelerationX);
				player.setaccelerationY(datagram.accelerationY);

				player.setListeMissile(datagram.listeMissile);
				nouveauJoueur = false;
			} else {
				datagramReponse.listeAdversaire.add(entry.getValue());
			}
		}

		if (nouveauJoueur) {
			ServeurJoueur player = new ServeurJoueur(idJoueur);
			player.setX(datagram.x);
			player.setY(datagram.y);
			player.setR(datagram.r);
			player.setaccelerationX(datagram.accelerationX);
			player.setaccelerationY(datagram.accelerationY);
			player.setListeMissile(datagram.listeMissile);
			listePlayers.put(idJoueur, player);
		}

		return datagramReponse;
	}

	/**
	 * Méthode détectant une collision entre un joueur et un missile
	 * 
	 * @param liste
	 *            des missiles présents sur la carte
	 * @param id
	 *            du client nous servant à parcourir la liste de joueur
	 * 
	 */
	public void checkCollision(ArrayList<Missile> listeMissile, int idC) {

		if (listeMissile.size() > 0) {
			Iterator<Entry<Integer, ServeurJoueur>> entryIt2 = listePlayers.entrySet().iterator();
			while (entryIt2.hasNext()) {
				Entry<Integer, ServeurJoueur> entry2 = entryIt2.next();
				ServeurJoueur joueur = entry2.getValue();
				if (idC != joueur.getId()) {

					for (Missile m : listeMissile) {
						if (m.collision(joueur)) {
							if (!joueur.getBonusState(3)) {
								joueur.damage();
								System.out.println(joueur.getHealth());
								if (joueur.getHealth() == 0) {
									System.out.println("MORT");
									joueur.setHealth(3);
									Serveur.server.sendToTCP(joueur.getId(), "ko");
									// entryIt2.remove();
								}

							} else if (joueur.getBonusState(3))
								System.out.println("joueur " + joueur.getId() + "shielded");
						}
					}
				}
			}
		}
	}

	/**
	 * Supprime un joueur de la liste
	 * 
	 * @param id
	 *            du joueur que la méthode veut supprimer
	 * 
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
