package server;

import java.util.ArrayList;

import client.ModeJeu;
import client.Model.Joueur;
import network.DatagramUpdateClient;
import network.DatagramUpdateServer;
import network.SegmentCreationPartie;
import network.SegmentListeParties;
import network.SegmentNouveauJoueur;
import network.SegmentRejoindrePartie;

/**
 * Classe qui gère le déroulement de l'ensemble des parties du serveur
 * @author gonzo
 *
 */
public class GestionnairePartie {
	
	private static int idPartie = 0;
	private ArrayList<Partie> listePartie;
	
	public GestionnairePartie() {
		listePartie = new ArrayList<>();
	}
	
	/**
	 * Méthode appelé lorsqu'un joueur créé une nouvelle partie
	 * @param le segment contenant les informations de la partie
	 * @param idClient
	 * @return
	 */
	public SegmentNouveauJoueur creationPartie(SegmentCreationPartie creationPartie, int idClient) {
		Partie partie;
		if(creationPartie.optionsPartie.getModeJeu() == ModeJeu.DEATHMATCH)
			partie = new Partie(idPartie, creationPartie.optionsPartie);
		else if(creationPartie.optionsPartie.getModeJeu() == ModeJeu.CAPTURE)
			partie = new PartieCapture(idPartie, creationPartie.optionsPartie);
		else {
			partie = new Partie(idPartie, creationPartie.optionsPartie); //course
		}
		
		listePartie.add(partie);
		
		ServeurJoueur nouveauJoueur = new ServeurJoueur(idClient);
		partie.addJoueur(nouveauJoueur);
		nouveauJoueur.setName(creationPartie.pseudo);
		
		SegmentNouveauJoueur reponse = new SegmentNouveauJoueur();
		reponse.idPartie = idPartie;
		reponse.team = nouveauJoueur.getTeam();
		
		idPartie++;
		
		return reponse; 
	}
	
	/**
	 * Méthode appelé lorsqu'un joueur rejoind une partie
	 * @param le segment contenant les informations de la partie
	 * @param idClient
	 * @return le segment reponse envoyé au client contenant le numéro de sa team
	 */
	public SegmentNouveauJoueur rejoindrePartie(SegmentRejoindrePartie segment, int idClient) {
		Partie partie = getPartie(segment.idPartie);
		
		ServeurJoueur nouveauJoueur = new ServeurJoueur(idClient);
		nouveauJoueur.setName(segment.pseudo);
		partie.addJoueur(nouveauJoueur);
		
		
		SegmentNouveauJoueur reponse = new SegmentNouveauJoueur();
		reponse.idPartie = partie.getId();
		reponse.team = nouveauJoueur.getTeam();	
		
		return reponse;
	}
	
	/**
	 * Retourne l'instance d'une partie en fonction de son id
	 * @param id
	 * @return
	 */
	public Partie getPartie(int id) {
		
		for(Partie p : listePartie) {
			if(p.getId() == id) return p;
		}
		
		return null;
	}
	
	public ArrayList<Partie> getListePartie(){
		return listePartie;
	}
	
	/**
	 * Créer un segment contenant la liste des parties disponible
	 * Appelé lorsque le joueur arrive dans le menu "WindowListePartie"
	 * @return
	 */
	public SegmentListeParties sendListePartie(){
		SegmentListeParties segment = new SegmentListeParties();
		
		for(Partie p : listePartie) {
			segment.listeIdParties.add(p.getId());
			segment.listeOptionsParties.add(p.optionsPartie);
		}
		return segment;
	}
	
	/**
	 * Méthode appelé lorsque le serveur reçoit un paquet du client
	 * - Mets à jour les informations du client
	 * - Vérifie les collisions des missiles du clients
	 * - Vérifie si le client obtient un bonus
	 * - Prépare la réponse
	 * @param idJoueur
	 * @param datagram reçu
	 * @return
	 */
	public DatagramUpdateServer updateClient(int idJoueur, DatagramUpdateClient datagram) {
		
		Partie p = getPartie(datagram.idPartie);
		DatagramUpdateServer datagramReponse = p.updateClient(idJoueur, datagram);
		datagramReponse.health = p.gestionnaireJoueur.getJoueur(idJoueur).getHealth();
		return datagramReponse;
	}

	/**
	 * Supprime le joueur dans la partie dans laquelle il est
	 * @param id
	 * @return
	 */
	public Partie removeJoueur(int id) {
		for(Partie p : listePartie) {
			if(p.joueurDeco(id))
				return p;
		}
		
		return null;
	}
	
	
	
	
}
