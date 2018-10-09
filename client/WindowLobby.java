package client;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class WindowLobby extends BasicGameState{

	private final int maxPlayer = 8;
	private String hostName = "defaultName";
	private ArrayList<Joueur> playersInLobby;
	
	GameContainer container;
	
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

	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		Image background = new Image("ressources/menu/space_background.jpg");
		Image buttonPret = new Image("ressources/menu/lobby/buttonPret.jpg");
		Image buttonRetour = new Image("ressources/menu/lobby/buttonRetour.jpg");
		background.draw(0,0);
		buttonRetour.draw(525, 525);
		buttonPret.draw(650, 525);

		
		
		g.drawRect(50, 100, 450, 450);
		g.drawRect(525, 125, 250, 375);
		
		g.drawString("Partie de : " + hostName, 300, 50);
		g.drawString("Options de la partie:" , 525, 100);
		
		
		
		for(int i = 0; (i < playersInLobby.size() && i < 16); i++){
			g.drawString((i+1) + ".	  " + playersInLobby.get(i).getNom(), 75, 125 + (i*25));
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame arg1, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
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
