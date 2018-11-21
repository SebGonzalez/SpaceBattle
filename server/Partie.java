package server;

import client.ModeJeu;
import network.DatagramUpdateClient;
import network.DatagramUpdateServer;

public class Partie {

	public GestionnaireJoueur gestionnaireJoueur;
	public GestionnaireBonusServeur gestionnaireBonus;
	private int id;
	
	private ModeJeu modeJeu;
	
	public Partie(int id, ModeJeu modeJeu) {
		this.id = id;
		this.modeJeu = modeJeu;
		gestionnaireJoueur = new GestionnaireJoueur();
		gestionnaireBonus = new GestionnaireBonusServeur(gestionnaireJoueur);
	}

	public int getId() {
		return id;
	}
	
	public DatagramUpdateServer updateClient(int idJoueur, DatagramUpdateClient datagram) {
		DatagramUpdateServer datagramReponse = gestionnaireJoueur.updateJoueur(idJoueur, datagram);
		gestionnaireBonus.updateBonus(idJoueur, datagramReponse);
		
		return datagramReponse;
	}
	
	
}
