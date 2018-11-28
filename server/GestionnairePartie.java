package server;

import java.util.ArrayList;

import client.ModeJeu;
import client.Model.Joueur;
import network.DatagramUpdateClient;
import network.DatagramUpdateServer;
import network.SegmentCreationPartie;
import network.SegmentNouveauJoueur;
import network.SegmentRejoindrePartie;

public class GestionnairePartie {
	
	private static int idPartie = 0;
	private ArrayList<Partie> listePartie;
	
	public GestionnairePartie() {
		listePartie = new ArrayList<>();
	}
	
	public SegmentNouveauJoueur creationPartie(SegmentCreationPartie creationPartie, int idClient) {
		Partie partie;
		if(creationPartie.modeJeu == ModeJeu.DEATHMATCH)
			partie = new Partie(idPartie, creationPartie.modeJeu);
		else if(creationPartie.modeJeu == ModeJeu.CAPTURE)
			partie = new PartieCapture(idPartie, creationPartie.modeJeu);
		else {
			partie = new Partie(idPartie, creationPartie.modeJeu); //course
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
	
	public Partie getPartie(int id) {
		
		for(Partie p : listePartie) {
			if(p.getId() == id) return p;
		}
		
		return null;
	}
	public DatagramUpdateServer updateClient(int idJoueur, DatagramUpdateClient datagram) {
		
		Partie p = getPartie(datagram.idPartie);
		DatagramUpdateServer datagramReponse = p.updateClient(idJoueur, datagram);
		datagramReponse.health = p.gestionnaireJoueur.getJoueur(idJoueur).getHealth();
		return datagramReponse;
	}

	public void removeJoueur(int id) {
		for(Partie p : listePartie) {
			p.joueurDeco(id);
		}
	}
	
	
	
	
}
