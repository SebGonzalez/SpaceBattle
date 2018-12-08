package server;

import client.GameOptions;
import client.Model.Flag;
import network.DatagramUpdateClient;
import network.DatagramUpdateServer;
import network.DatagramUpdateServerCapture;

/**
 * Partie qui gère le mode capture de drapeau
 * 
 * @author Sébastien Gonzalez
 *
 */
public class PartieCapture extends Partie {

	private Flag flag1; // flag team1
	private Flag flag2; // flag team2

	private ServeurJoueur joueurFlag1; // joueur qui porte le flag de la team 1 (donc joueur de team 2)
	private ServeurJoueur joueurFlag2; // joueur qui porte le flag de la team 2 (donc joueur de team 1)

	private int scoreTeam1 = 0;
	private int scoreTeam2 = 0;

	public PartieCapture(int id, GameOptions optionsPartie) {
		super(id, optionsPartie);

		flag1 = new Flag(1200, 1200, 1);
		flag2 = new Flag(2000, 1200, 2);
	}

	/**
	 * Méthode appelé lorsque l'on reçoit un paquet du client
	 */
	public DatagramUpdateServer updateClient(int idJoueur, DatagramUpdateClient datagram) {
		DatagramUpdateServer datagramReponse = super.updateClient(idJoueur, datagram);

		checkCollisionFlag(idJoueur);

		DatagramUpdateServerCapture datagramReponse2 = new DatagramUpdateServerCapture();
		datagramReponse2.listeAdversaire = datagramReponse.listeAdversaire;
		datagramReponse2.listeBonus = datagramReponse.listeBonus;
		datagramReponse2.bonus = datagramReponse.bonus;
		datagramReponse2.flag1 = flag1;
		datagramReponse2.flag2 = flag2;
		datagramReponse2.scoreTeam1 = scoreTeam1;
		datagramReponse2.scoreTeam2 = scoreTeam2;

		return datagramReponse2;
	}

	/**
	 * Méthode qui vérifie la collision avec les drapeaux du joueur passé en paramètre
	 * 
	 * @param idJoueur
	 */
	public void checkCollisionFlag(int idJoueur) {
		ServeurJoueur joueur = gestionnaireJoueur.getJoueur(idJoueur);

		if (joueurFlag1 != null && flag2.collisionBase(joueurFlag1.getX(), joueurFlag1.getY())) { //si le drapeau arrive à sa base on incrémente le score de la team et on le remet à sa place initiale
			scoreTeam2++;
			joueurFlag1 = null;
			flag1.resetPos();
		}
		if (joueurFlag2 != null && flag1.collisionBase(joueurFlag2.getX(), joueurFlag2.getY())) {
			scoreTeam1++;
			joueurFlag2 = null;
			flag2.resetPos();
		}

		//en fonction de la team du joueur
		if (joueur.getTeam() == 1) {
			if (joueurFlag2 == null) {
				if (flag2.collision(joueur.getX(), joueur.getY())) { //si personne porte le drapeau et que le joueur arrive dessus on lui attribue le drapeau
					joueurFlag2 = joueur;
				}
			} else {
				flag2.setX(joueurFlag2.getX()); //sinon si le drapeau est porté par qq1 on modifie les coordonnées
				flag2.setY(joueurFlag2.getY());
			}

			if (flag1.getX() != flag1.getxBase() || flag1.getY() != flag1.getyBase()) { //si le drapeau allié n'est pas à sa base
				if (flag1.collision(joueur.getX(), joueur.getY())) { //si le joueur est dessus on le remet à sa place
					joueurFlag1 = null;
					flag1.resetPos();
				}
			}
		} else { //idem si le joueur est dans la team 2
			if (joueurFlag1 == null) {
				if (flag1.collision(joueur.getX(), joueur.getY())) {
					joueurFlag1 = joueur;
				}
			} else {
				flag1.setX(joueurFlag1.getX());
				flag1.setY(joueurFlag1.getY());
			}

			if (flag2.getX() != flag2.getxBase() || flag2.getY() != flag2.getyBase()) {
				if (flag2.collision(joueur.getX(), joueur.getY())) {
					joueurFlag2 = null;
					flag2.resetPos();
				}
			}
		}
	}

	/**
	 * Méthode appelé lorsque le joueur se deconnecte
	 * On le supprime de la liste, et s'il était en train de porter le drapeau on reset les variables
	 * @return true si le joueur était dans la partie et qu'il a était supprimé et false sinon
	 */
	public boolean joueurDeco(int id) {
		ServeurJoueur joueurDeco = gestionnaireJoueur.getJoueur(id);
		if (joueurDeco != null) {
			if (joueurDeco.equals(joueurFlag1)) {
				joueurFlag1 = null;
			} else if (joueurDeco.equals(joueurFlag2)) {
				joueurFlag2 = null;
			}

			if (gestionnaireJoueur.getJoueur(id) != null) {
				gestionnaireJoueur.removeJoueur(id);
				return true;
			}
		}

		return false;
	}
}
