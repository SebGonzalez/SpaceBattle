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

public class GestionnairePartie {
	
	private static int idPartie = 0;
	private ArrayList<Partie> listePartie;
	
	public GestionnairePartie() {
		listePartie = new ArrayList<>();
	}
	
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
	
	public ArrayList<Partie> getListePartie(){
		return listePartie;
	}
	
	public SegmentListeParties sendListePartie(){
		SegmentListeParties segment = new SegmentListeParties();
		
		for(Partie p : listePartie) {
			segment.listeIdParties.add(p.getId());
			segment.listeOptionsParties.add(p.optionsPartie);
		}
		return segment;
	}
	
	public DatagramUpdateServer updateClient(int idJoueur, DatagramUpdateClient datagram) {
		
		Partie p = getPartie(datagram.idPartie);
		DatagramUpdateServer datagramReponse = p.updateClient(idJoueur, datagram);
		
		return datagramReponse;
	}

	public void removeJoueur(int id) {
		for(Partie p : listePartie) {
			p.joueurDeco(id);
		}
	}
	
	
	
	
}
