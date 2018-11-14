package server;

import java.util.ArrayList;

import client.Model.Joueur;
import network.DatagramUpdateClient;
import network.DatagramUpdateServer;
import network.SegmentIDPartie;

public class GestionnairePartie {
	
	private static int idPartie = 0;
	private ArrayList<Partie> listePartie;
	
	public GestionnairePartie() {
		listePartie = new ArrayList<>();
	}
	
	public SegmentIDPartie creationPartie() {
		Partie partie = new Partie(idPartie);
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
		DatagramUpdateServer datagramReponse = p.gestionnaireJoueur.updateJoueur(idJoueur, datagram);
		p.gestionnaireBonus.updateBonus(idJoueur, datagramReponse);
		
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
