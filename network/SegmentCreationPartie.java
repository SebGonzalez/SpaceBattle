package network;

import client.GameOptions;

/**
 * Segment permettant au client d'informer le serveur de la création d'une nouvelle partie
 * Contient le pseudo de l'hôte et les options de la partie
 * 
 *
 */
public class SegmentCreationPartie {
	public String pseudo;
	public GameOptions optionsPartie;
}
