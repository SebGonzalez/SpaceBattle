package server;

import java.util.ArrayList;

import client.ModeJeu;
import client.Model.Joueur;
import network.DatagramUpdateClient;
import network.DatagramUpdateServer;
import network.SegmentCreationPartie;
import network.SegmentIDPartie;

public class GestionnairePartie {
	
	private static int idPartie = 0;
	private ArrayList<Partie> listePartie;
	
	public GestionnairePartie() {
		listePartie = new ArrayList<>();
	}
	
	public SegmentIDPartie creationPartie(SegmentCreationPartie creationPartie) {
		Partie partie;
		if(creationPartie.modeJeu == ModeJeu.DEATHMATCH)
			partie = new Partie(idPartie, creationPartie.modeJeu);
		else if(creationPartie.modeJeu == ModeJeu.CAPTURE)
			partie = new PartieCapture(idPartie, creationPartie.modeJeu);
		else {
			partie = new Partie(idPartie, creationPartie.modeJeu); //course
		}
		
		listePartie.add(partie);
		
		SegmentIDPartie reponse = new SegmentIDPartie();
		reponse.idPartie = idPartie;
		
		idPartie++;
		
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
		
		return datagramReponse;
	}

	public void removeJoueur(int id) {
		for(Partie p : listePartie) {
			if(p.gestionnaireJoueur.listePlayers.get(id) != null) {
				p.gestionnaireJoueur.listePlayers.remove(id);
			}
		}
	}
	
	
}
