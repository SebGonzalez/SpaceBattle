package client;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.Graphics;

import client.Model.Joueur;
import client.Model.Missile;

public class GestionnaireMissile {

	private ArrayList<Missile> listeMissileClient;
	private Joueur joueur;
	private long lastTir;

	public GestionnaireMissile(Joueur joueur) {
		listeMissileClient = new ArrayList<>();
		lastTir = System.currentTimeMillis();
		
		this.joueur = joueur;
	}

	public void addMissile() {
		if (System.currentTimeMillis() - lastTir > 200) {
			lastTir = System.currentTimeMillis();
			Missile m = new Missile(joueur.getX(), joueur.getY(), joueur.getRotation());
			listeMissileClient.add(m);
		}
	}

	public void render(Graphics g) {
		for (Missile m : listeMissileClient) {
			m.render(g);
		}
	}

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
