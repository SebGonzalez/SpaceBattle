package network;

/**
 * Segment permettant au client d'informer le serveur qu'une partie doit être lancé
 * Ce segment est envoyé que par l'hôte d'une partie et seulement lorsque celle ci n'est pas encore démarré
 * Contient l'id de la partie à démarrer
 * @author Sébastien Gonzalez
 *
 */
public class SegmentStartPartie {
	public int idPartie;
}
