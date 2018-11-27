package client;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.Graphics;

import client.Model.Joueur;
import client.Model.Missile;

/**
 * Classe qui gere les missiles (client et adversaire)
 * @author Sebastien Gonzalez
 *
 */
public class GestionnaireMissile {

	private ArrayList<Missile> listeMissileClient;
	private Joueur joueur;
	private long lastTir;

	public GestionnaireMissile(Joueur joueur) {
		listeMissileClient = new ArrayList<>();
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

	/**
	 * Mets à jour les missiles
	 * 	Client : déplacement du missile et suppression si le délai d'apparition est dépassé
	 * 	Adversaire : 
	 * @param delta
	 */
	public void update(int delta) {
		for (Iterator<Missile> it = listeMissileClient.iterator(); it.hasNext();) {
			Missile m = it.next();
			if(m.isAutoDestruction()) it.remove();
			else {
				m.update(delta);
			}
		}
	}
}
