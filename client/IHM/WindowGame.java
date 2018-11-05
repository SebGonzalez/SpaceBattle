package client.IHM;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.Music;

import client.ConnectionClient;
import client.Game;
import client.GestionnaireAdversaire;
import client.GestionnaireBonusClient;
import client.GestionnaireMissile;
import client.Model.Bonus;
import client.Model.Joueur;
import client.Model.Missile;

public class WindowGame extends BasicGameState {

	private GameContainer container;
	private TiledMap map;

	private Joueur joueur;
	public static Image ship;
	public static Image missileJoueur;
	public static Image missileEnnemies;
	public static Image bonus1;
	public static Image bonus2;
	public static Image bonus3;
	public static Image bonus4;

	private GestionnaireAdversaire gestionnaireAdversaire;
	private GestionnaireMissile gestionnaireMissile;
	private GestionnaireBonusClient gestionnaireBonus;

	private ConnectionClient connexionClient;

	public WindowGame(int state) {
	}

	public void init(GameContainer container, StateBasedGame sgb) throws SlickException {
		this.container = container;
		container.setAlwaysRender(true);
		this.map = new TiledMap("ressources/map/SpaceBattle.tmx");

		Game.playMusic();

		try {
			ship = new Image("ressources/sprites/sprite2.png");
			missileJoueur = new Image("ressources/sprites/missile.png");
			missileEnnemies = new Image("ressources/sprites/missile2.png");
			bonus1 = new Image("ressources/sprites/PW/bolt_gold.png");
			bonus2 = new Image("ressources/sprites/PW/powerupGreen_star.png");
			bonus3 = new Image("ressources/sprites/PW/shield_gold.png");
			bonus4 = new Image("ressources/sprites/PW/star_gold.png");

		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		joueur = new Joueur();
		joueur.loadImage();
		gestionnaireAdversaire = new GestionnaireAdversaire();
		gestionnaireMissile = new GestionnaireMissile(joueur);
		gestionnaireBonus = new GestionnaireBonusClient();

		connexionClient = new ConnectionClient(joueur, gestionnaireAdversaire, gestionnaireMissile, gestionnaireBonus);
		connexionClient.connect();
	}

	public void render(GameContainer container, StateBasedGame sgb, Graphics g) throws SlickException {
		g.translate(container.getWidth() / 2 - (int) joueur.getX(),
				container.getHeight() / 2 - (int) joueur.getY());

		// Background
		this.map.render(0, 0, 0);
		// Foreground
		this.map.render(0, 0, 1);
		// Logic
		this.map.render(0, 0, 2);
		// Fore-Foreground
		// this.map.render(0, 0, 3);
		// this.map.render(0, 0, 4);

		gestionnaireMissile.render(g);
		gestionnaireAdversaire.render(g);
		gestionnaireBonus.render(g);
		joueur.render(g);

	}

	public void update(GameContainer container, StateBasedGame sgb, int delta) throws SlickException {
		joueur.update(container, delta, map);

		/*try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		gestionnaireAdversaire.update();
		gestionnaireMissile.update(delta);
		gestionnaireBonus.update();
		connexionClient.sendInformation(joueur);

		// gestionnaireMissile.addMissileClient();

	}

	public void keyReleased(int key, char c) {

		if (key == Input.KEY_UP || key == Input.KEY_W)
			joueur.keys_pressed[0] = false;
		if (key == Input.KEY_LEFT || key == Input.KEY_A)
			joueur.keys_pressed[1] = false;
		if (key == Input.KEY_RIGHT || key == Input.KEY_D)
			joueur.keys_pressed[2] = false;
		if (key == Input.KEY_ESCAPE)
			container.exit();
	}

	public void keyPressed(int key, char c) {
		if (key == Input.KEY_UP || key == Input.KEY_W)
			joueur.keys_pressed[0] = true;
		if (key == Input.KEY_LEFT || key == Input.KEY_A)
			joueur.keys_pressed[1] = true;
		if (key == Input.KEY_RIGHT || key == Input.KEY_D)
			joueur.keys_pressed[2] = true;
		if (key == Input.KEY_SPACE)
			gestionnaireMissile.addMissileClient();
	}

	@Override
	public int getID() {
		return 1;
	}
}
