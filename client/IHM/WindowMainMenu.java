package client.IHM;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;

import client.Game;

public class WindowMainMenu extends BasicGameState {

	private int resX = Game.res.getX(), resY = Game.res.getY();

	
	public WindowMainMenu(int state) {
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		
		resX = Game.res.getX();
		resY = Game.res.getY();

		//background.draw(0,0);
		GestionnaireImagesIHM.getRessource("background").draw(0, 0, container.getWidth(), container.getHeight());
		GestionnaireImagesIHM.getRessource("title").draw((resX/2 - 375), (resY/12));
		GestionnaireImagesIHM.getRessource("buttonPlay").draw((resX/2) - 125, resY/3);
		GestionnaireImagesIHM.getRessource("buttonHost").draw((resX/2) - 125, resY/3 + 125);
		GestionnaireImagesIHM.getRessource("buttonOptions").draw((resX/2) - 125, resY/3 + 250);
		GestionnaireImagesIHM.getRessource("buttonQuit").draw(resX/2 + 25, resY/3 + 250);
	}

	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {	
		container.setAlwaysRender(true);
		GestionnaireImagesIHM.loadMainMenu();
		
		Game.playMusic();
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
				Game.connexionClient.askForGameList();

				sbg.enterState(6, new EmptyTransition(), new FadeInTransition(Color.black));

			}
		}
		
		//Bouton Host
		if((xpos > resX/2-125 && xpos  < (resX/2)+125) && (ypos > resY - (resY/3)-200 && ypos < resY - (resY/3)-125)) {
			if(input.isMouseButtonDown(0)) {
				sbg.enterState(5, new EmptyTransition(), new FadeInTransition(Color.black));
			}
		}
		
		//Bouton Options
		if((xpos > resX/2-150 && xpos  < (resX/2)-25) && (ypos > resY - (resY/3)-325 && ypos < resY - (resY/3)-250)) {
			if(input.isMouseButtonDown(0)) {
				sbg.enterState(3, new EmptyTransition(), new FadeInTransition(Color.black));
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
