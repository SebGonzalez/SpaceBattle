package client.Gestionnaire;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;

import client.Game;
import client.ModeJeu;
import client.Model.Joueur;
import client.Model.Missile;
import network.DatagramUpdateServer;
import server.ServeurJoueur;

public class GestionnairePartie {
	
	public GestionnaireAdversaire gestionnaireAdversaire;
	public GestionnaireMissile gestionnaireMissile;
	public GestionnaireBonusClient gestionnaireBonus;
	public Joueur joueur;
	
	int idPartie = -1;
	ModeJeu modeJeu = null;
	
	public GestionnairePartie() {
		joueur = new Joueur();
		gestionnaireAdversaire = new GestionnaireAdversaire();
		gestionnaireMissile = new GestionnaireMissile(joueur);
		gestionnaireBonus = new GestionnaireBonusClient();
	}

	public ArrayList<Missile> getListeMissileClient() {
		return gestionnaireMissile.getListeMissileClient();
	}
	
	public int getIdPartie() { return idPartie; }
	
	public void setIdPartie(int idPartie) {
		this.idPartie = idPartie;
	}
	
	public ModeJeu getModeJeu() { return modeJeu; }
	
	public void setModeJeu(ModeJeu modeJeu) {
		this.modeJeu = modeJeu;
	}
	
	public void setTeamJoueur(int team) {
		joueur.setTeam(team);
	}

	public void setReception(DatagramUpdateServer datagram) {
		
		gestionnaireAdversaire.setReception(datagram.listeAdversaire);
		gestionnaireBonus.setReception(datagram.listeBonus,datagram.bonus);
		
		for(int i = 0;i<4;i++) {
			joueur.bonus[i] = gestionnaireBonus.bonus[i];
			gestionnaireMissile.bonus[i] = gestionnaireBonus.bonus[i];
		}
		joueur.collidePlayer(datagram.listeAdversaire);
		
	}
	
	public void renderAll(Graphics g) {
		gestionnaireMissile.render(g);
		gestionnaireAdversaire.render(g);
		gestionnaireBonus.render(g);
		joueur.render(g);
		
			

	}
	
	public void update(GameContainer container, int delta, TiledMap map) {
		joueur.update(container, delta, map);
		gestionnaireAdversaire.update();
		gestionnaireMissile.update(delta);
		gestionnaireBonus.update();
		
		Game.connexionClient.sendInformationGame(joueur);
	}

	public void nouvellePartie() {
		joueur = new Joueur();
		gestionnaireAdversaire = new GestionnaireAdversaire();
		gestionnaireMissile = new GestionnaireMissile(joueur);
		gestionnaireBonus = new GestionnaireBonusClient();
	}
	
	
}
