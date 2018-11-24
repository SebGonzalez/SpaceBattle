package server;

import client.ModeJeu;
import client.Model.Flag;
import network.DatagramUpdateClient;
import network.DatagramUpdateServer;
import network.DatagramUpdateServerCapture;

public class PartieCapture extends Partie {

	Flag flag1; //flag team1
	Flag flag2; //flag team2
	
	ServeurJoueur joueurFlag1; //joueur qui porte le flag de la team 1 (donc joueur de team 2)
	ServeurJoueur joueurFlag2; //joueur qui porte le flag de la team 2 (donc joueur de team 1)
	
	public PartieCapture(int id, ModeJeu modeJeu) {
		super(id, modeJeu);
		
		flag1 = new Flag(1200, 1200, 1);
		flag2 = new Flag(2000, 1200, 2);
	}
	
	public DatagramUpdateServer updateClient(int idJoueur, DatagramUpdateClient datagram) {
		DatagramUpdateServer datagramReponse = super.updateClient(idJoueur, datagram);
		
		checkCollisionFlag(idJoueur);
		
		System.out.println("Joueur  : "+ joueurFlag1 + " " + joueurFlag2);
		
		DatagramUpdateServerCapture datagramReponse2 = new DatagramUpdateServerCapture();
		datagramReponse2.listeAdversaire = datagramReponse.listeAdversaire;
		datagramReponse2.listeBonus = datagramReponse.listeBonus;
		datagramReponse2.bonus = datagramReponse.bonus;
		datagramReponse2.flag1 = flag1;
		datagramReponse2.flag2 = flag2;
		
		return datagramReponse2;
	}
	
	public void checkCollisionFlag(int idJoueur) {
		ServeurJoueur joueur = gestionnaireJoueur.getJoueur(idJoueur);
		
		if(joueur.getTeam() == 1) {
			if(joueurFlag2 == null) {
				if(flag2.collision(joueur.getX(), joueur.getY())) {
					joueurFlag2 = joueur;
				}
			}
			else {
				flag2.setX(joueurFlag2.getX());
				flag2.setY(joueurFlag2.getY());
			}
			
			if(flag1.getX() != flag1.getxBase() || flag1.getY() != flag1.getyBase()) {
				if(flag1.collision(joueur.getX(), joueur.getY())) {
					joueurFlag1 = null;
					flag1.resetPos();
				}
			}
		}
		else {
			if(joueurFlag1 == null) {
				if(flag1.collision(joueur.getX(), joueur.getY())) {
					joueurFlag1 = joueur;
				}
			}
			else {
				flag1.setX(joueurFlag1.getX());
				flag1.setY(joueurFlag1.getY());
			}
			
			if(flag2.getX() != flag2.getxBase() || flag2.getY() != flag2.getyBase()) {
				if(flag2.collision(joueur.getX(), joueur.getY())) {
					joueurFlag2 = null;
					flag2.resetPos();
				}
			}
		}
		
		/*Flag flagAdverseJoueur = joueur.getTeam() == 1 ? flag2 : flag1;
		ServeurJoueur joueurFlag = joueur.getTeam() == 1 ? joueurFlag2 : joueurFlag1;
		
		if(joueurFlag == null) {
			System.out.println("Drapeau adverse non recup");
			if(flagAdverseJoueur.collision(joueur.getX(), joueur.getY())) {
				joueurFlag = gestionnaireJoueur.getJoueur(idJoueur);;
				System.out.println("Collision");
				System.out.println("Joueur flag 1 : "+ joueurFlag);
				System.out.println("Joueur flag 2 : "+ (joueur.getTeam() == 1 ? joueurFlag2 : joueurFlag1));
			}
		}
		else {
			System.out.println("Update");
			flagAdverseJoueur.setX(joueurFlag.getX());
			flagAdverseJoueur.setY(joueurFlag.getY());
		}
		
		ServeurJoueur joueurAdverseFlag = joueur.getTeam() == 1 ? joueurFlag1 : joueurFlag2;
		Flag flagTeamJoueur = joueur.getTeam() == 1 ? flag1 : flag2;
		
		if(joueurAdverseFlag != null) {
			if(flagTeamJoueur.collision(joueur.getX(), joueur.getY())) {
				joueurAdverseFlag = null;
				flagTeamJoueur.resetPos();
			}
		}*/
		
	}
	
	public void joueurDeco(int id) {
		ServeurJoueur joueurDeco = gestionnaireJoueur.getJoueur(id);
		if(joueurDeco != null) {
			if(joueurDeco.equals(joueurFlag1)) {
				joueurFlag1 = null;	
			}
			else if(joueurDeco.equals(joueurFlag2)) {
				joueurFlag2 = null;
			}
			
			gestionnaireJoueur.removeJoueur(id);
		}
	}

}
