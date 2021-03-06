package client.Gestionnaire;

import org.newdawn.slick.Graphics;

import client.Game;
import client.GameOptions;
import client.ModeJeu;
import client.IHM.GestionnaireImagesIHM;
import client.IHM.WindowGame;

public class GestionnairePartieCourse extends GestionnairePartie {

	long debutPartie;
	
	public GestionnairePartieCourse(GameOptions options) {
		super(options);
		options.setTir(false);
		options.setModeJeu(ModeJeu.COURSE);
		options.setCollisions(false);
		joueur.setX(846);
		joueur.setY(1344);
		map = WindowGame.map2;
		
	}
	
	/**
	 * @see gestionnairePartie.renderAll()
	 * Affiche le système de feu rouge pour avertir du début de la course
	 * 
	 */
	public void renderAll(Graphics g) {
		super.renderAll(g);
	
		g.popTransform();
		
		if(System.currentTimeMillis() - debutPartie < 6000) {
			if(System.currentTimeMillis() - debutPartie < 3000)
				GestionnaireImagesIHM.getRessource("feuRouge").draw(Game.res.getX()/2 - 128, Game.res.getY()/2 - 256);
			else if(System.currentTimeMillis() - debutPartie < 4000)
				GestionnaireImagesIHM.getRessource("feuJaune").draw(Game.res.getX()/2 - 128, Game.res.getY()/2 - 256);
			else
				GestionnaireImagesIHM.getRessource("feuVert").draw(Game.res.getX()/2 - 128, Game.res.getY()/2 - 256);
		}
	}
	
	public void update() {
		
	}
	
	public void debut() {
		debutPartie = System.currentTimeMillis();
	}
	
	/**
	 * @see gestionnairePartie.avancer()
	 * 
	 */
	public void avancer() {
		if(System.currentTimeMillis() - debutPartie > 4000)
			super.avancer();
	}

}
