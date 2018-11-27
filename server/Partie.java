package server;

import java.util.Map.Entry;

import client.GameOptions;
import network.DatagramUpdateClient;
import network.DatagramUpdateServer;
import network.SegmentLobby;

public class Partie {

	public GestionnaireJoueur gestionnaireJoueur;
	public GestionnaireBonusServeur gestionnaireBonus;
	private int id;
	
	private boolean start = true;
	
	protected GameOptions optionsPartie;
	
	public Partie(int id, GameOptions optionsPartie) {
		this.id = id;
		this.optionsPartie = optionsPartie;
		gestionnaireJoueur = new GestionnaireJoueur();
		gestionnaireBonus = new GestionnaireBonusServeur(gestionnaireJoueur);
		
		if(optionsPartie.getLobby()) start = false;
	}

	public int getId() {
		return id;
	}
	
	public DatagramUpdateServer updateClient(int idJoueur, DatagramUpdateClient datagram) {
		DatagramUpdateServer datagramReponse = gestionnaireJoueur.updateJoueur(idJoueur, datagram);
		gestionnaireBonus.updateBonus(idJoueur, datagramReponse);
		
		return datagramReponse;
	}

	public void addJoueur(ServeurJoueur nouveauJoueur) {
		gestionnaireJoueur.addJoueur(nouveauJoueur);
	}
	
	public void joueurDeco(int id) {
		if(gestionnaireJoueur.getJoueur(id) != null) {
			gestionnaireJoueur.removeJoueur(id);
		}
	}
	
	public boolean getStart() {
		return start;
	}
	
	public void startPartie() {
		start = true;
	}

	public SegmentLobby getSegmentLobby() {
		SegmentLobby segment = new SegmentLobby();
		
		for(Entry<Integer,ServeurJoueur> e : gestionnaireJoueur.listePlayers.entrySet()) {
			segment.listeJoueurLobby.add(e.getValue().getName());
		}
		return segment;
	}
	
	
}
