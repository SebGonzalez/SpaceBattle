package client.Gestionnaire;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;

import client.Game;
import client.GameOptions;
import client.ModeJeu;
import client.IHM.WindowGame;
import client.Model.Joueur;
import client.Model.Missile;
import network.DatagramUpdateServer;
import server.ServeurJoueur;

public class GestionnairePartie {
	
	public GestionnaireAdversaire gestionnaireAdversaire;
	public GestionnaireMissile gestionnaireMissile;
	public GestionnaireBonusClient gestionnaireBonus;
	public Joueur joueur;
	private GameOptions options;
	
	int idPartie = -1;
	
	protected TiledMap map;
	
	public GestionnairePartie(GameOptions options) {
		joueur = new Joueur();
		gestionnaireAdversaire = new GestionnaireAdversaire();
		gestionnaireMissile = new GestionnaireMissile(joueur);
		gestionnaireBonus = new GestionnaireBonusClient();
		this.options = options;
		map = WindowGame.map1;
	}

	public ArrayList<Missile> getListeMissileClient() {
		return gestionnaireMissile.getListeMissileClient();
	}
	
	public int getIdPartie() { return idPartie; }
	
	public void setIdPartie(int idPartie) {
		this.idPartie = idPartie;
	}
	
	public GameOptions getOptionsPartie() {
		return options;
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

		if(options.getCollisions()) joueur.collidePlayer(gestionnaireAdversaire.getListeAdversaire());

		joueur.health = datagram.health;

		gestionnaireMissile.removeMissileonCollide(gestionnaireAdversaire.getListeAdversaire());
		
	}
	
	public void renderAll(Graphics g) {
		gestionnaireMissile.render(g);
		gestionnaireAdversaire.render(g);
		gestionnaireBonus.render(g);
		joueur.render(g);
	}
	
	public void update(GameContainer container, int delta) {
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

	public void tirer() {
		if(options.getTir())
			gestionnaireMissile.addMissileClient();
		
	}

	public void drawMap() {
		map.render(0, 0);
	}

	public void avancer() {
		joueur.keys_pressed[0] = true;
	}
}
