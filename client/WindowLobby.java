package client;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class WindowLobby extends BasicGameState{

	public WindowLobby(int state) {
		
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		Image background = new Image("ressources/menu/space_background.jpg");
		background.draw();
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame arg1, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		return 2;
	}

}
