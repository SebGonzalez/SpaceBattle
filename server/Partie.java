package server;

import java.util.Map.Entry;

import client.GameOptions;
import network.DatagramUpdateClient;
import network.DatagramUpdateServer;
import network.SegmentLobby;

/**
 * Classe qui gère le déroulement d'une partie
 * Pour créer un nouveau mode de jeu qui bénéficie de comportement particulier (par exemple capture de drapeau) créer une nouvelle classe qui hérite de celle-ci
 * 
 *
 */
public class Partie {

	public GestionnaireJoueur gestionnaireJoueur;
	public GestionnaireBonusServeur gestionnaireBonus;
	private int id;
	
	protected GameOptions optionsPartie;
	
	public Partie(int id, GameOptions optionsPartie) {
		this.id = id;
		this.optionsPartie = optionsPartie;
		gestionnaireJoueur = new GestionnaireJoueur();
		gestionnaireBonus = new GestionnaireBonusServeur(gestionnaireJoueur);
	}

	public int getId() {
		return id;
	}
	
	public GameOptions getOptions() {
		return optionsPartie;
	}
	
	/**
	 * Méthode appelé lorsque l'on reçoit un paquet du client
	 * - Mets à jour les informations du client
	 * - Vérifie les collisions des missiles du clients
	 * - Vérifie si le client obtient un bonus
	 * - Prépare la réponse
	 * @param idJoueur
	 * @param datagram reçu
	 * @return
	 */
	public DatagramUpdateServer updateClient(int idJoueur, DatagramUpdateClient datagram) {
		DatagramUpdateServer datagramReponse = gestionnaireJoueur.updateJoueur(idJoueur, datagram);
		gestionnaireBonus.updateBonus(idJoueur, datagramReponse);
		
		return datagramReponse;
	}

	/**
	 * Ajout un joueur au gestionnaire du joueur
	 * Appelé lorsqu'un joueur se connecte
	 * @param nouveauJoueur
	 */
	public void addJoueur(ServeurJoueur nouveauJoueur) {
		gestionnaireJoueur.addJoueur(nouveauJoueur);
	}
	
	/**
	 * Suppression d'un joueur au gestionnaire du joueur
	 * Appelé lorsqu'un joueur se deconnecte
	 * @param id du joueur
	 * @return true si le joueur a été supprimé et false dans le cas contraire (si le joueur n'était pas dans cette partie)
	 */
	public boolean joueurDeco(int id) {
		if(gestionnaireJoueur.getJoueur(id) != null) {
			gestionnaireJoueur.removeJoueur(id);
			return true;
		}
		return false;
	}
	
	public boolean getStart() {
		return optionsPartie.isStart();
	}
	
	public void startPartie() {
		optionsPartie.setStart(true);
	}

	/**
	 * Préparation du segment qui contient la liste des joueurs du lobby
	 * Appelé lorsqu'un nouveau joueur arrive dans le lobby ou qu'un joueur le quitte
	 * @return le segment
	 */
	public SegmentLobby getSegmentLobby() {
		SegmentLobby segment = new SegmentLobby();
		
		for(Entry<Integer,ServeurJoueur> e : gestionnaireJoueur.listePlayers.entrySet()) {
			segment.listeJoueurLobby.add(e.getValue().getName());
		}
		return segment;
	}
	
	
}
