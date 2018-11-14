package network;

import java.util.ArrayList;

import client.Model.Bonus;
import server.ServeurJoueur;

/**
 * Datagram permettant aux serveur d'envoyer les informations de chaque joueurs aux clients
 * @author Sébastien Gonzalez
 *
 */
public class DatagramUpdateServer {
	public ArrayList<ServeurJoueur> listeAdversaire = new ArrayList<>();
	public ArrayList<Bonus> listeBonus = new ArrayList<>();
	public Boolean bonus[] = new Boolean[4];
	
	
	
}
