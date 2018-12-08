package client.Gestionnaire;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;

import client.Game;
import client.GameOptions;
import client.IHM.WindowGame;
import client.Model.Joueur;
import client.Model.Missile;
import network.DatagramUpdateServer;

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

	/**
	 * Renvoie la liste de missile du gestionnaireMissile
	 * @author Amine Boudraa
	 */
	public ArrayList<Missile> getListeMissileClient() {
		return gestionnaireMissile.getListeMissileClient();
	}
	
	/**
	 * Renvoie l'id de la partie courante
	 * @author Amine Boudraa
	 */
	public int getIdPartie() { return idPartie; }
	
	/**
	 * Modifie l'id de la partie
	 * @param id id que l'on veut pour la partie
	 * @author Amine Boudraa
	 */
	public void setIdPartie(int idPartie) {
		this.idPartie = idPartie;
	}
	
	/**
	 * Renvoie les options de la partie
	 * @author Amine Boudraa
	 */
	public GameOptions getOptionsPartie() {
		return options;
	}
	
	/**
	 * Attribue à un joueur une équipe
	 * @param team numéro de l'équipe que l'on veut pour le joueur
	 * @author Amine Boudraa
	 */
	public void setTeamJoueur(int team) {
		joueur.setTeam(team);
	}

	/**
	 * Reçoit le paquet serveur
	 * Envoie au gestionnaireAdversaire la liste des adversaire du serveur
	 * Envoie au gestionnaireBonus la liste des bonus du serveur
	 * Envoie au joueurs localement la liste des bonus 
	 * Envoie au joueurs localement leurs points de vie
	 * @param datagram datagram reçu du serveur
	 * @author Amine Boudraa
	 */
	public void setReception(DatagramUpdateServer datagram) {
		
		gestionnaireAdversaire.setReception(datagram.listeAdversaire);
		gestionnaireBonus.setReception(datagram.listeBonus);
				
		for(int i = 0;i<4;i++) {
			joueur.bonus[i] = datagram.bonus[i];
		}

		joueur.health = datagram.health;
		
	}
	
	/**
	 * Appelle toute les méthodes de render pour 
	 * Afficher les missiles
	 * Afficher les adversaires
	 * Afficher les bonus
	 * Afficher le joueur courant
	 * @author Amine Boudraa
	 */
	public void renderAll(Graphics g) {
		gestionnaireMissile.render(g);
		gestionnaireAdversaire.render(g);
		gestionnaireBonus.render(g);
		joueur.render(g);
	}
	
	/**
	 * Appelle les différentes méthodes de mise à jour de : 
	 * joueur 
	 * gestionnaireAdversaire
	 * gestionaireMissile
	 * gestionnaireBonus
	 * 
	 * Teste les collisions joueur/missile
	 * @author Amine Boudraa
	 */
	public void update(GameContainer container, int delta) {
		joueur.update(container, delta, map);
		if(options.getCollisions()) joueur.collidePlayer(gestionnaireAdversaire.getListeAdversaire());
		gestionnaireAdversaire.update();
		gestionnaireMissile.update(delta);
		gestionnaireMissile.removeMissileonCollide(gestionnaireAdversaire.getListeAdversaire());
		gestionnaireBonus.update();
		
		Game.connexionClient.sendInformationGame(joueur);
	}

	/**
	 * Initialisation des différents gestionnaires
	 * @author Amine Boudraa
	 */
	public void nouvellePartie() {
		joueur = new Joueur();
		gestionnaireAdversaire = new GestionnaireAdversaire();
		gestionnaireMissile = new GestionnaireMissile(joueur);
		gestionnaireBonus = new GestionnaireBonusClient();
	}
	
	/**
	 * Ajoute un missile à la liste de missile après un tir
	 * @author Amine Boudraa
	 */
	public void tirer() {
		if(options.getTir())
			gestionnaireMissile.addMissileClient(joueur);
		
	}

	public void drawMap() {
		map.render(0, 0);
	}

	/**
	 * Autorise le déplacement via la touche "haut"
	 * @author Amine Boudraa
	 */
	public void avancer() {
		joueur.keys_pressed[0] = true;
	}
}
