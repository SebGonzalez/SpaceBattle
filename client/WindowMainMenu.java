package client;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class WindowMainMenu extends BasicGameState {

	private GameContainer container;
	
	public WindowMainMenu(int state) {
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		Image background = new Image("ressources/menu/space_background.jpg");
		Image buttonPlay = new Image("ressources/menu/mainmenu/buttonPlay.jpg");
		Image buttonHost = new Image("ressources/menu/mainmenu/buttonHost.jpg");
		Image buttonQuit = new Image("ressources/menu/mainmenu/buttonQuit.jpg");
		Image buttonOptions = new Image("ressources/menu/mainmenu/buttonOptions.jpg");

		background.draw(0,0);
		g.drawString("Spacebattle: Alpha -1", 300, 50);
		buttonPlay.draw(250, 200);
		buttonHost.draw(250, 300);
		buttonOptions.draw(250, 400);
		buttonQuit.draw(400, 400);
	}

	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		Font font = new UnicodeFont(new java.awt.Font("Arial", java.awt.Font.ITALIC, 26));
		this.container = container;		
		container.setAlwaysRender(true);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		Input input = container.getInput();
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		
		if((xpos > 250 && xpos  < 500) && (ypos > 325 && ypos < 400)) {
			if(input.isMouseButtonDown(0)) {
				sbg.enterState(1);
			}
		}
		
		if((xpos > 400 && xpos  < 500) && (ypos > 155 && ypos < 200)) {
			if(input.isMouseButtonDown(0)) {
				container.exit();
			}
		}
	}
	
	@Override
	public int getID() {
		return 0;
	}

}
