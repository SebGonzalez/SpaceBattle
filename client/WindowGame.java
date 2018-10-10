package client;


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

public class WindowGame extends BasicGameState {

	private GameContainer container;
	private TiledMap map;

	private Joueur joueur;
	public static Image ship;
	
	private GestionnaireAdversaire gestionnaireAdversaire;
	
	private ConnectionClient connexionClient;
	
	public WindowGame(int state) {
	}

	public void init(GameContainer container, StateBasedGame sgb) throws SlickException {
		this.container = container;
		container.setAlwaysRender(true);
		this.map = new TiledMap("ressources/map/petit.tmx");
		
		try {
			ship = new Image("ressources/sprites/sprite2.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		joueur = new Joueur();
		joueur.loadImage();
		gestionnaireAdversaire = new GestionnaireAdversaire();
		
		connexionClient = new ConnectionClient(joueur, gestionnaireAdversaire);
		connexionClient.connect();
	}

	public void render(GameContainer container, StateBasedGame sgb, Graphics g) throws SlickException {
		g.translate(container.getWidth() / 2 - (int) joueur.getxCamera(), container.getHeight() / 2 - (int)joueur.getyCamera());

		this.map.render(0, 0);
		//this.map.render(0, 0, 1);
		//this.map.render(0, 0, 2);
		
		joueur.render(g);
		gestionnaireAdversaire.render(g);
		
		
		//this.map.render(0, 0, 3);
	    //this.map.render(0, 0, 4);
	}

	public void update(GameContainer container, StateBasedGame sgb, int delta) throws SlickException {
		
		joueur.update(container,delta, map);
		
		gestionnaireAdversaire.update();
		connexionClient.sendInformation(joueur);

	}

	public void keyReleased(int key, char c) {
		
		switch(key) {
		case Input.KEY_UP:
		joueur.keys_pressed[0] = false;
		break;
		case Input.KEY_LEFT:
		joueur.keys_pressed[1] = false;
		break;
		case Input.KEY_RIGHT:
		joueur.keys_pressed[2] = false;
		break;
		case Input.KEY_ESCAPE: 
			container.exit();
		}
	}

	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_UP:
			joueur.keys_pressed[0] = true;
			break;
		case Input.KEY_LEFT:
			joueur.keys_pressed[1] = true;
			break;
		case Input.KEY_RIGHT:
			joueur.keys_pressed[2] = true;
			break;
		}
	}

	private Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
		Animation animation = new Animation();
		for (int x = startX; x < endX; x++) {
			animation.addFrame(spriteSheet.getSprite(x, y), 100);
		}
		return animation;
	}

	@Override
	public int getID() {
		return 1;
	}
}
