package client.Gestionnaire;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.Graphics;

import client.Model.Joueur;
import client.Model.Missile;
import server.ServeurJoueur;

/**
 * Classe qui gere les missiles (client et adversaire)
 * @author Sebastien Gonzalez
 *
 */
public class GestionnaireMissile {

	private ArrayList<Missile> listeMissileClient;
	private ArrayList<Missile> reception;
	public ArrayList<ServeurJoueur> listeAdversaire;
	private Joueur joueur;
	private long lastTir;

	public Boolean bonus[] = new Boolean[4];
	
	public GestionnaireMissile(Joueur joueur) {
		listeMissileClient = new ArrayList<>();
		listeAdversaire = new ArrayList<>();
		lastTir = System.currentTimeMillis();
		
		this.joueur = joueur;
	}

	public ArrayList<Missile> getListeMissileClient() {
		return listeMissileClient;
	}

	/**
	 * Méthode appelé lorsque le client effectue un tir
	 * On vérifie que le délai entre deux tir est passé et on ajoute le missile à la liste
	 */
	public void addMissileClient() {
		if (System.currentTimeMillis() - lastTir > 200) {
			lastTir = System.currentTimeMillis();
			Missile m = new Missile(joueur.getX(), joueur.getY(), joueur.getRotation());
			listeMissileClient.add(m);
			
			if(bonus[2]) {
			Missile i = new Missile(joueur.getX(), joueur.getY(), joueur.getRotation() + 1 );
			Missile j = new Missile(joueur.getX(), joueur.getY(), joueur.getRotation() - 1);
			listeMissileClient.add(i);
			listeMissileClient.add(j);
			}
		}
	}

	/**
	 * Affiche l'ensemble des missiles
	 * @param g
	 */
	public void render(Graphics g) {
		for (Missile m : listeMissileClient) {
			m.render(g);
		}
	}
	
	public void setReception(ArrayList<Missile> reception) {
		this.reception = reception;
	}

	/**
	 * Mets à jour les missiles
	 * 	Client : déplacement du missile et suppression si le délai d'apparition est dépassé
	 * 	Adversaire : 
	 * @param delta
	 */
	
	public void removeMissileonCollide(ArrayList<ServeurJoueur> listeAdversaire) {
		
		for(ServeurJoueur joueur : listeAdversaire) {
			for(Missile m : listeMissileClient)
				if(m.collision(joueur)) m.setAutoDestruction(true);
			}
		}
	
	public void update(int delta) {
	
		for (Iterator<Missile> it = listeMissileClient.iterator(); it.hasNext();) {
			Missile m = it.next();

			if( m.getX() < 900 || m.getX() > 2600 || m.getY() < 650 || m.getY() > 2500)
				m.setAutoDestruction(true);
			
				if(m.isAutoDestruction()) it.remove();
			
				else m.update(delta,joueur);
			
		}
	}
}
