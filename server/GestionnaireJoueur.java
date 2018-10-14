package server;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import newtork.DatagramUpdateClient;
import newtork.DatagramUpdateServer;

/**
 * Classe de gestion des joueurs par le serveur
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
	 * @param idJoueur
	 * @param datagram
	 * @return
	 */
	public DatagramUpdateServer updateJoueur(int idJoueur, DatagramUpdateClient datagram) {
		
		DatagramUpdateServer datagramReponse = new DatagramUpdateServer();
		
		for(Entry<Integer, ServeurJoueur> entry : listePlayers.entrySet()) {
		    int cle = entry.getKey();
		    if(cle == idJoueur) {
		    		ServeurJoueur player = entry.getValue();
		    		player.setX(datagram.x);
		    		player.setY(datagram.y);
		    		player.setR(datagram.r);
		    }
		    else {
		    		datagramReponse.listeAdversaire.add(entry.getValue());
		    }
		}
		
		return datagramReponse;
	}
	
	/**
	 * Supprime un joueur de la liste
	 * @param id
	 */
	public void removeJoueur(int id) {
		for(Entry<Integer, ServeurJoueur> entry : listePlayers.entrySet()) {
		   if(id == entry.getKey()) {
			   listePlayers.remove(entry.getKey());
			   break;
		   }
		   
		}
	}
	
}
