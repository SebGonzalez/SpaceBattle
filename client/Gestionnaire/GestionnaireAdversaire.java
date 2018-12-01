package client.Gestionnaire;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import client.Game;
import client.IHM.WindowGame;
import client.Model.Missile;
import server.ServeurJoueur;

/**
 * Classe qui g�re les adversaires
 * @author gonzo
 *
 */
public class GestionnaireAdversaire {

	private ArrayList<ServeurJoueur> listeAdversaire;
	
	private ArrayList<ServeurJoueur> reception;
	
	public GestionnaireAdversaire() {
		listeAdversaire = new ArrayList<>();
	}

	public ArrayList<ServeurJoueur> getListeAdversaire() {
		return listeAdversaire;
	}
	
	public void addAdversaire() {
		
	}
	
	/**
	 * Méthode appelé par le thread de connexion qui sauvegarde dans une variable temporaire les informations reçu par le serveur
	 * @param reception
	 */
	public void setReception(ArrayList<ServeurJoueur> reception) {
		this.reception = reception;
	}
	
	/**
	 * Méthode appelé par le thread principal qui met à jour les informations reçu par le serveur
	 */
	public void update() {
		if(reception != null) {
			listeAdversaire.clear();
			listeAdversaire.addAll(reception);
			reception = null;
		//	System.out.println("recu");
		}
	//	else System.out.println("adv pas re�u");
	}
	
	/**
	 * Affichage de tous les adversaire et de leur missile
	 * Note : A optimiser (voir trello)
	 * @param g
	 */
	public void render(Graphics g) {
		

		for(ServeurJoueur adversaire : listeAdversaire) {
			
			g.fillOval((float)adversaire.getX() - 16, (float)adversaire.getY() - 8, 32, 16);
			
			g.drawString(adversaire.getName(), adversaire.getX() - 26, adversaire.getY() - 76);
			WindowGame.ship.setRotation((float) -(Math.toDegrees(Math.atan2(Math.cos(adversaire.getR()), Math.sin(adversaire.getR())))));
			WindowGame.ship2.setRotation((float) -(Math.toDegrees(Math.atan2(Math.cos(adversaire.getR()), Math.sin(adversaire.getR())))));
			WindowGame.damage2.setRotation((float) -(Math.toDegrees(Math.atan2(Math.cos(adversaire.getR()), Math.sin(adversaire.getR())))));
			WindowGame.damage3.setRotation((float) -(Math.toDegrees(Math.atan2(Math.cos(adversaire.getR()), Math.sin(adversaire.getR())))));
			
			if(Game.gestionnairePartie.joueur.getTeam() == adversaire.getTeam())
				WindowGame.ship.draw((float)adversaire.getX() - 56, (float)adversaire.getY() - 37);
			else
				WindowGame.ship2.draw((float)adversaire.getX() - 56, (float)adversaire.getY() - 37);
			if( adversaire.getHealth() ==  2) WindowGame.damage2.draw((float)adversaire.getX() - 56, (float)adversaire.getY() - 37);
			if( adversaire.getHealth() ==  1) WindowGame.damage3.draw((float)adversaire.getX() - 56, (float)adversaire.getY() - 37);
			
			for(Missile m : adversaire.getListeMissile()) {
				m.render(g);
			}
		}
	}
}
