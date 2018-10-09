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
	private GestionnaireAdversaire gestionnaireAdversaire;
	
	private ConnectionClient connexionClient;

	public static Animation[] animations = new Animation[8];
	
	public WindowGame(int state) {
	}

	public void init(GameContainer container, StateBasedGame sgb) throws SlickException {
		this.container = container;
		container.setAlwaysRender(true);
		this.map = new TiledMap("ressources/map/exemple-collision.tmx");
		SpriteSheet spriteSheet = new SpriteSheet("ressources/sprites/character.png", 64, 64);
		this.animations[0] = loadAnimation(spriteSheet, 0, 1, 0);
		this.animations[1] = loadAnimation(spriteSheet, 0, 1, 1);
		this.animations[2] = loadAnimation(spriteSheet, 0, 1, 2);
		this.animations[3] = loadAnimation(spriteSheet, 0, 1, 3);
		this.animations[4] = loadAnimation(spriteSheet, 1, 9, 0);
		this.animations[5] = loadAnimation(spriteSheet, 1, 9, 1);
		this.animations[6] = loadAnimation(spriteSheet, 1, 9, 2);
		this.animations[7] = loadAnimation(spriteSheet, 1, 9, 3);

		joueur = new Joueur();
		gestionnaireAdversaire = new GestionnaireAdversaire();
		
		connexionClient = new ConnectionClient(joueur, gestionnaireAdversaire);
		connexionClient.connect();
	}

	public void render(GameContainer container, StateBasedGame sgb, Graphics g) throws SlickException {
		g.translate(container.getWidth() / 2 - (int) joueur.getxCamera(), container.getHeight() / 2 - (int)joueur.getyCamera());

		this.map.render(0, 0, 0);
		this.map.render(0, 0, 1);
		this.map.render(0, 0, 2);
		
		g.setColor(new Color(0, 0, 0, .5f));
		g.fillOval(joueur.getX() - 16, joueur.getY() - 8, 32, 16);
		g.drawAnimation(animations[joueur.getDirection() + (joueur.isMoving() ? 4 : 0)], joueur.getX() - 32, joueur.getY() - 60);
		
		gestionnaireAdversaire.render(g);
		
		
		this.map.render(0, 0, 3);
	    this.map.render(0, 0, 4);
	}

	public void update(GameContainer container, StateBasedGame sgb, int delta) throws SlickException {
		if (joueur.isMoving()) {
			float futurX = joueur.getX();
			float futurY = joueur.getY();
			switch (joueur.getDirection()) {
			case 0:
				futurY = joueur.getY() - .1f * delta;
				break;
			case 1:
				futurX = joueur.getX() - .1f * delta;
				break;
			case 2:
				futurY = joueur.getY() + .1f * delta;
				break;
			case 3:
				futurX = joueur.getX() + .1f * delta;
				break;
			}
			Image tile = this.map.getTileImage((int) futurX / this.map.getTileWidth(),
					(int) futurY / this.map.getTileHeight(), this.map.getLayerIndex("logic"));
			// il y a colision si la tuile "tuture" existe
			boolean collision = tile != null;
			if (collision) {
				Color color = tile.getColor((int) futurX % this.map.getTileWidth(),
						(int) futurY % this.map.getTileHeight());
				collision = color.getAlpha() > 0;
			}

			if (collision) {
				joueur.setMoving(false);
			} else {
				joueur.setX(futurX);
				joueur.setY(futurY);
			}

		}

		int w = container.getWidth() / 4;
		if (joueur.getX() > joueur.getxCamera() + w)
			joueur.setxCamera(joueur.getX() - w);
		if (joueur.getX() < joueur.getxCamera() - w)
			joueur.setxCamera(joueur.getX() + w);
		int h = container.getHeight() / 4;
		if (joueur.getY() >joueur.getyCamera() + h)
			joueur.setyCamera(joueur.getY() - h);
		if (joueur.getY() < joueur.getyCamera() - h)
			joueur.setyCamera(joueur.getY() + h);
		
		gestionnaireAdversaire.update();
		connexionClient.sendInformation(joueur);

	}

	public void keyReleased(int key, char c) {
		joueur.setMoving(false);
		if (Input.KEY_ESCAPE == key) {
			container.exit();
		}
	}

	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_UP:
			joueur.setDirection(0);
			joueur.setMoving(true);
			break;
		case Input.KEY_LEFT:
			joueur.setDirection(1);
			joueur.setMoving(true);
			break;
		case Input.KEY_DOWN:
			joueur.setDirection(2);
			joueur.setMoving(true);
			break;
		case Input.KEY_RIGHT:
			joueur.setDirection(3);
			joueur.setMoving(true);
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
