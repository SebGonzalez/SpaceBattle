package network;

import java.util.ArrayList;

/**
 * Segment permettant au serveur d'envoyer la liste des joueur présent dans le lobby
 * @author Sébastien Gonzalez
 *
 */
public class SegmentLobby {
	public boolean start = false;
	public ArrayList<String> listeJoueurLobby = new ArrayList<>();
}
