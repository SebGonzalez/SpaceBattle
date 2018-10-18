package client.IHM;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import client.Game;

public class WindowMainMenu extends BasicGameState {

	private GameContainer container;
	private int resX = Game.res.getX(), resY = Game.res.getY();

	
	public WindowMainMenu(int state) {
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		
		resX = Game.res.getX();
		resY = Game.res.getY();
		Image background = new Image("ressources/menu/space_background.jpg");
		Image buttonPlay = new Image("ressources/menu/mainmenu/buttonPlay.jpg");
		Image buttonHost = new Image("ressources/menu/mainmenu/buttonHost.jpg");
		Image buttonQuit = new Image("ressources/menu/mainmenu/buttonQuit.jpg");
		Image buttonOptions = new Image("ressources/menu/mainmenu/buttonOptions.jpg");

		//background.draw(0,0);
		background.draw(0, 0, container.getWidth(), container.getHeight());
		g.drawString("Spacebattle: Alpha", resX/2 - 100, resY/12);
		buttonPlay.draw((resX/2) - 125, resY/3);
		buttonHost.draw((resX/2) - 125, resY/3 + 125);
		buttonOptions.draw((resX/2) - 125, resY/3 + 250);
		buttonQuit.draw(resX/2 + 25, resY/3 + 250);
	}

	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		this.container = container;		
		container.setAlwaysRender(true);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		Input input = container.getInput();
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		//System.out.println("x: " + xpos + " y: " + ypos);
		
		//Bouton Jouer
		if((xpos > resX/2-125 && xpos  < (resX/2)+125) && (ypos > resY - (resY/3)-75 && ypos < resY - (resY/3))) {
			if(input.isMouseButtonDown(0)) {
				sbg.enterState(1);
			}
		}
		
		//Bouton Host
		if((xpos > resX/2-125 && xpos  < (resX/2)+125) && (ypos > resY - (resY/3)-200 && ypos < resY - (resY/3)-125)) {
			if(input.isMouseButtonDown(0)) {
				sbg.enterState(2);
			}
		}
		
		//Bouton Options
		if((xpos > resX/2-150 && xpos  < (resX/2)-25) && (ypos > resY - (resY/3)-325 && ypos < resY - (resY/3)-250)) {
			if(input.isMouseButtonDown(0)) {
				sbg.enterState(3);
			}
		}
		
		//Bouton Quitter
		if((xpos > resX/2+25 && xpos  < (resX/2)+125) && (ypos > resY - (resY/3)-325 && ypos < resY - (resY/3)-250)) {
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
