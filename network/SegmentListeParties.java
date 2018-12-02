package network;
import java.util.ArrayList;

import client.GameOptions;
import server.Partie;

/**
 * Segment permettant au serveur d'envoyer la liste des parties au client
 * Contient une liste pour les id des parties et une autre pour les options
 * @author Sébastien Gonzalez
 *
 */
public class SegmentListeParties {
	public ArrayList<Integer> listeIdParties = new ArrayList<>();
	public ArrayList<GameOptions> listeOptionsParties = new ArrayList<>();
}
