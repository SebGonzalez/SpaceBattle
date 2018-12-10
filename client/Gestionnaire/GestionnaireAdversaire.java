package client.Gestionnaire;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import client.Game;
import client.Model.Missile;
import server.ServeurJoueur;
import client.IHM.GestionnaireImagesIHM;

/**
 * Classe qui g�re les adversaires
 * 
 *
 */
public class GestionnaireAdversaire {

	private ArrayList<ServeurJoueur> listeAdversaire;
	
	private ArrayList<ServeurJoueur> reception;
	
	public GestionnaireAdversaire() {
		listeAdversaire = new ArrayList<>();
	}

	/**
	 * Renvoie la liste des joueurs(adverses) reçues par le serveur
	 * @return liste des adversaires
	 * 
	 */
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
	 * Affiche les différents adversaire à partir de la liste des joueurs localement
	 * Affiche les nouvelles textures dues au dégats causés aux adversaires
	 * Affiche la liste des missiles
	 * 
	 */
	public void render(Graphics g) {
		

		for(ServeurJoueur adversaire : listeAdversaire) {
			
			g.fillOval((float)adversaire.getX() - 16, (float)adversaire.getY() - 8, 32, 16);
			
			g.drawString(adversaire.getName(), adversaire.getX() - 26, adversaire.getY() - 76);
			GestionnaireImagesIHM.getRessource("ship").setRotation((float) -(Math.toDegrees(Math.atan2(Math.cos(adversaire.getR()), Math.sin(adversaire.getR())))));
			GestionnaireImagesIHM.getRessource("ship2").setRotation((float) -(Math.toDegrees(Math.atan2(Math.cos(adversaire.getR()), Math.sin(adversaire.getR())))));
			GestionnaireImagesIHM.getRessource("damage2").setRotation((float) -(Math.toDegrees(Math.atan2(Math.cos(adversaire.getR()), Math.sin(adversaire.getR())))));
			GestionnaireImagesIHM.getRessource("damage3").setRotation((float) -(Math.toDegrees(Math.atan2(Math.cos(adversaire.getR()), Math.sin(adversaire.getR())))));
			
			if(Game.gestionnairePartie.joueur.getTeam() == adversaire.getTeam())
				GestionnaireImagesIHM.getRessource("ship").draw((float)adversaire.getX() - 56, (float)adversaire.getY() - 37);
			else
				GestionnaireImagesIHM.getRessource("ship2").draw((float)adversaire.getX() - 56, (float)adversaire.getY() - 37);
			if( adversaire.getHealth() ==  2) GestionnaireImagesIHM.getRessource("damage2").draw((float)adversaire.getX() - 56, (float)adversaire.getY() - 37);
			if( adversaire.getHealth() ==  1) GestionnaireImagesIHM.getRessource("damage3").draw((float)adversaire.getX() - 56, (float)adversaire.getY() - 37);
			
			for(Missile m : adversaire.getListeMissile()) {
				m.render(g);
			}
		}
	}
}
