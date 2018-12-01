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
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.Music;

import client.ConnectionClient;
import client.Game;
import client.Gestionnaire.GestionnaireAdversaire;
import client.Gestionnaire.GestionnaireBonusClient;
import client.Gestionnaire.GestionnaireMissile;
import client.Gestionnaire.GestionnairePartie;
import client.Model.Bonus;
import client.Model.Joueur;
import client.Model.Missile;

public class WindowGame extends BasicGameState {

	private GameContainer container;

	public static Image ship;
	public static Image ship2;
	public static Image shipJoueur;
	public static Image missileJoueur;
	public static Image missileEnnemies;
	public static Image bonus1;
	public static Image bonus2;
	public static Image bonus3;
	public static Image bonus4;
	public static Image damage1;
	public static Image damage2Joueur;
	public static Image damage3Joueur;
	public static Image damage2;
	public static Image damage3;
	
	public static Image flag1;
	public static Image flag2;
	public static Image baseFlag1;
	public static Image baseFlag2;
	
	public static Image feuRouge;
	public static Image feuJaune;
	public static Image feuVert;

	public static boolean loop = true;
	
	public static TiledMap map1;
	public static TiledMap map2;

	public WindowGame(int state) {
	}

	public void init(GameContainer container, StateBasedGame sgb) throws SlickException {
		this.container = container;
		container.setAlwaysRender(true);
		map1 = new TiledMap("ressources/map/SpaceBattle.tmx");
		map2 = new TiledMap("ressources/map/Race.tmx");

		try {
			ship = new Image("ressources/sprites/ship_allie.png");
			ship2 = new Image("ressources/sprites/ship_adversaire.png");
			shipJoueur = new Image("ressources/sprites/ship_joueur.png");
			missileJoueur = new Image("ressources/sprites/missile.png");
			missileEnnemies = new Image("ressources/sprites/missile2.png");
			bonus1 = new Image("ressources/sprites/PW/bolt_gold.png");
			bonus2 = new Image("ressources/sprites/PW/powerupGreen_star.png");
			bonus4 = new Image("ressources/sprites/PW/shield_gold.png");
			bonus3 = new Image("ressources/sprites/PW/star_gold.png");
			flag1 = new Image("ressources/sprites/flag1.png");
			flag2 = new Image("ressources/sprites/flag2.png");
			baseFlag1 = new Image("ressources/sprites/baseFlag1.png");
			baseFlag2 = new Image("ressources/sprites/baseFlag2.png");
			
			feuRouge = new Image("ressources/sprites/feu_rouge.png");
			feuJaune = new Image("ressources/sprites/feu_jaune.png");
			feuVert = new Image("ressources/sprites/feu_vert.png");

			damage1 = new Image("ressources/sprites/playerShip2_damage1.png");
			damage2 =  new Image("ressources/sprites/playerShip2_damage2.png");
			damage3 =  new Image("ressources/sprites/playerShip2_damage3.png");
			damage2Joueur =  new Image("ressources/sprites/playerShip2_damage2.png");
			damage3Joueur =  new Image("ressources/sprites/playerShip2_damage3.png");


		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//Game.gestionnairePartie.joueur.loadImage();
		Game.connexionClient.addData(Game.gestionnairePartie);
	}

	public void render(GameContainer container, StateBasedGame sgb, Graphics g) throws SlickException {
		g.pushTransform();
		g.translate(container.getWidth() / 2 - (int) Game.gestionnairePartie.joueur.getX(),
				container.getHeight() / 2 - (int)  Game.gestionnairePartie.joueur.getY());

		Game.gestionnairePartie.drawMap();
		Game.gestionnairePartie.renderAll(g);
		
		try {
			g.popTransform();
		}catch(RuntimeException r) {
			
		}
		g.drawString("Id partie : " + Game.gestionnairePartie.getIdPartie(), container.getWidth()-200,10);

	}

	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		Game.gestionnairePartie.update(container, delta);
		
		if(!loop) {
			loop = true;
			Game.connexionClient = new ConnectionClient(Game.gestionnairePartie);
			Game.connexionClient.connect();
			sbg.enterState(0, new EmptyTransition(), new FadeInTransition(Color.black));
			
			System.out.println("end");
		}

		// gestionnaireMissile.addMissileClient();

	}

	public void keyReleased(int key, char c) {

		if (key == Input.KEY_UP || key == Input.KEY_W)
			Game.gestionnairePartie.joueur.keys_pressed[0] = false;
		if (key == Input.KEY_LEFT || key == Input.KEY_A)
			Game.gestionnairePartie.joueur.keys_pressed[1] = false;
		if (key == Input.KEY_RIGHT || key == Input.KEY_D)
			Game.gestionnairePartie.joueur.keys_pressed[2] = false;
		if (key == Input.KEY_ESCAPE)
			container.exit();
	}

	public void keyPressed(int key, char c) {
		if (key == Input.KEY_UP || key == Input.KEY_W)
			Game.gestionnairePartie.avancer();
		if (key == Input.KEY_LEFT || key == Input.KEY_A)
			Game.gestionnairePartie.joueur.keys_pressed[1] = true;
		if (key == Input.KEY_RIGHT || key == Input.KEY_D)
			Game.gestionnairePartie.joueur.keys_pressed[2] = true;
		if (key == Input.KEY_SPACE)
			Game.gestionnairePartie.tirer();
	}

	@Override
	public int getID() {
		return 1;
	}
}
