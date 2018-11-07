package client.IHM;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;

import client.Game;
import client.Model.Joueur;

public class WindowLobby extends BasicGameState{

	private final int maxPlayer = 8;
	private String hostName = "defaultName";
	private ArrayList<Joueur> playersInLobby;
	
	GameContainer container;
	
	private int resX = Game.res.getX(), resY = Game.res.getY();
	
	public WindowLobby(int state) {
		playersInLobby = new ArrayList<Joueur>();
		testAddPlayers(16);
	}
	
	public void setHostName(String name) {
		hostName = name;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		this.container = container;		
		container.setAlwaysRender(true);
		GestionnaireImagesIHM.loadLobby();
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		resX = Game.res.getX();
		resY = Game.res.getY();

		GestionnaireImagesIHM.getRessource("background").draw(0, 0, container.getWidth(), container.getHeight());
		
		GestionnaireImagesIHM.getRessource("buttonBack").draw((float) ((resX/18) + (resX/1.6) + 25), (float) (resY/1.15));
		GestionnaireImagesIHM.getRessource("buttonReady").draw((float) ((resX/18) + (resX/1.6) + (resX/3.6) - 75), (float) (resY/1.15));

		g.drawRect((float) (resX/18.0),(float) (resY/6.0),(float) (resX/1.6),(float) (resY/1.3));
		g.drawRect((float) ((resX/18) + (resX/1.6) + 25), (float) (resY/6.0) + 25, (float) (resX/3.6), (float) (resY/1.6));
		
		g.drawString("Partie de : " + hostName, resX/2 - 75, resY/12);
		g.drawString("Options de la partie:" , (float) ((resX/18) + (resX/1.6) + 50), resY/6);
		
		
		
		for(int i = 0; (i < playersInLobby.size() && i < 16); i++){
			g.drawString((i+1) + ".	  " + playersInLobby.get(i).getNom(), (resX/18) + 25, (float) ((resY/6.0) + 15 + (i*25)));
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		Input input = container.getInput();
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();	
		
		// Bouton quitter
		if(((xpos > (resX/18) + (resX/1.6) + 25 && xpos <  (resX/18) + (resX/1.6) + 125)&& (ypos > resY - (resY/1.15) - 45 && ypos < resY - (resY/1.15)))) {
			if(input.isMouseButtonDown(0)) {
				sbg.enterState(0, new EmptyTransition(), new FadeInTransition(Color.black));
			}
		}
		
		if( xpos > (resX/18) + (resX/1.6) + (resX/3.6) - 75 && xpos <  (resX/18) + (resX/1.6) + (resX/3.6) - 75 + 125) {
			if(input.isMouseButtonDown(0)) {
				Game.connexionClient.createGame();
				sbg.enterState(Game.jeu, new EmptyTransition(), new FadeInTransition(Color.black));
			}
		}

		GestionnaireImagesIHM.getRessource("buttonReady").draw((float) ((resX/18) + (resX/1.6) + (resX/3.6) - 75), (float) (resY/1.15));
	}

	@Override
	public int getID() {
		return 2;
	}
	
	private void testAddPlayers(int n) {
		for(int i =0; i<n; i++) {
			playersInLobby.add(new Joueur("Joueur" + i, 0, 0));
		}
	}

}
