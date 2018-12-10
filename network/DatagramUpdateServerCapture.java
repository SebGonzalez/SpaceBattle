package network;

import client.Model.Flag;

/**
 * Datagram permettant aux serveur d'envoyer les informations de chaque joueurs aux clients (hérité de DatagramUpdateServer)
 * Envoie des informations des drapeaux et des scores de la partie
 * Utilisé pour le mode de jeu Capture de drapeau
 * 
 *
 */
public class DatagramUpdateServerCapture extends DatagramUpdateServer {
	public Flag flag1;
	public Flag flag2;
	public int scoreTeam1;
	public int scoreTeam2;
}
