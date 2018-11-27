package newtork;

import java.util.ArrayList;
import server.Bonus;
import server.ServeurJoueur;

/**
 * Datagram permettant aux serveur d'envoyer les informations de chaque joueurs aux clients
 * @author Sébastien Gonzalez
 *
 */
public class DatagramUpdateServer {
	public ArrayList<ServeurJoueur> listeAdversaire = new ArrayList<>();
	public ArrayList<Bonus> listeBonus = new ArrayList<>();
	public boolean vitesseBonus;
}
