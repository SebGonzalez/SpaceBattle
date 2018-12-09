package client.IHM;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.tiled.TiledMap;

import client.ConnectionClient;
import client.Game;

public class WindowGame extends BasicGameState {

	private GameContainer container;

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

		GestionnaireImagesIHM.loadGame();


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
			Game.connexionClient = new ConnectionClient(Game.gestionnairePartie,Game.adresseipserveur,Game.portTCP,Game.portUDP);
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
