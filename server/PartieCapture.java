package server;

import client.ModeJeu;
import client.Model.Flag;
import network.DatagramUpdateClient;
import network.DatagramUpdateServer;
import network.DatagramUpdateServerCapture;

public class PartieCapture extends Partie {

	Flag flag1;
	Flag flag2;
	
	public PartieCapture(int id, ModeJeu modeJeu) {
		super(id, modeJeu);
		
		flag1 = new Flag(1200, 1200, 1);
		flag2 = new Flag(2000, 1200, 2);
	}
	
	public DatagramUpdateServer updateClient(int idJoueur, DatagramUpdateClient datagram) {
		DatagramUpdateServer datagramReponse = super.updateClient(idJoueur, datagram);
		
		DatagramUpdateServerCapture datagramReponse2 = new DatagramUpdateServerCapture();
		datagramReponse2.listeAdversaire = datagramReponse.listeAdversaire;
		datagramReponse2.listeBonus = datagramReponse.listeBonus;
		datagramReponse2.bonus = datagramReponse.bonus;
		datagramReponse2.flag1 = flag1;
		datagramReponse2.flag2 = flag2;
		
		return datagramReponse2;
	}

}
