package client.IHM;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import client.Game;
import client.Resolution;

public class WindowOptions extends BasicGameState{

	GameContainer container;
	
	private int resX = Game.res.getX(), resY = Game.res.getY();
	private int t = 0;
	
	public WindowOptions(int state) {
		
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		this.container = container;		
		container.setAlwaysRender(true);		
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		Image background = new Image("ressources/menu/space_background.jpg");
		background.draw(0, 0, container.getWidth(), container.getHeight());
		
		Image buttonLowRes = new Image("ressources/menu/options/button_LOWRES.png");
		Image buttonMedRes = new Image("ressources/menu/options/button_MIDRES.png");
		Image buttonHighRes = new Image("ressources/menu/options/button_HIGHRES.png");
		Image buttonRetour = new Image("ressources/menu/options/buttonBack.png");
		
		Image buttonVolUp = new Image("ressources/menu/options/button_VOLUP.png");
		Image buttonVolDown = new Image("ressources/menu/options/button_VOLDOWN.png");

		
		g.drawString("Selectionnez une resolution: ", resX/12, resY/12);

		buttonLowRes.draw(resX/12 + 25, resY/12 + 25);
		buttonMedRes.draw(resX/12 + 150, resY/12 + 25);
		buttonHighRes.draw(resX/12 + 275, resY/12 + 25);		
		buttonRetour.draw((float) (resX/1.2),(float) (resY/1.2));
		g.drawString("Volume de la musique: ", resX/1.5f, resY/12);
		g.drawString("" + Game.getMusicVolume(), resX/1.5f + 80, resY/12+40);
		buttonVolUp.draw(resX/1.5f, resY/12 + 25);
		buttonVolDown.draw(resX/1.5f+140, resY/12 + 25);

		
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		Input input = container.getInput();
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		AppGameContainer gc = (AppGameContainer) container;
		
		t += delta; // Pour ne pas qu'un click incrémente le volume trop vite
		
		//LOWRES
		if((xpos > resX/12 + 25 && xpos < resX/12 +125) && (ypos > resY - (resY/12 + 25) -45 && ypos < resY - (resY/12 + 25)))
			if(input.isMouseButtonDown(0)) {
				Game.setResolution(Resolution.LOW);
				gc.setDisplayMode(Game.res.getX(), Game.res.getY(), false);
				resX = Game.res.getX();
				resY = Game.res.getY();
			}
		
		//MEDRES
		if((xpos > resX/12 + 150 && xpos < resX/12 +250) && (ypos > resY - (resY/12 + 25) -45 && ypos < resY - (resY/12 + 25)))
			if(input.isMouseButtonDown(0)) {
				Game.setResolution(Resolution.MED);
				gc.setDisplayMode(Game.res.getX(), Game.res.getY(), false);
				resX = Game.res.getX();
				resY = Game.res.getY();
			}
		
		//HIGHRES
		if((xpos > resX/12 + 275 && xpos < resX/12 +375) && (ypos > resY - (resY/12 + 25) -45 && ypos < resY - (resY/12 + 25)))
			if(input.isMouseButtonDown(0)) {
				Game.setResolution(Resolution.HIGH);
				gc.setDisplayMode(Game.res.getX(), Game.res.getY(), false);
				resX = Game.res.getX();
				resY = Game.res.getY();
			}
		
		//Bouton Retour
		if((xpos > resX/1.2 && xpos < resX/1.2 +100) && (ypos > resY - resY/1.2 -45 && ypos < resY - resY/1.2 ))
			if(input.isMouseButtonDown(0)) {
				sbg.enterState(0);
			}
		
		//Bouton Volume UP
		if((xpos > resX/1.5 && xpos < resX/1.5 +50) && (ypos > resY - (resY/12 + 25) - 50 && ypos < resY - (resY/12 + 25) ))
			if(input.isMouseButtonDown(0)) {
				if(t > 75) {
					Game.UPVolume();
					t = 0;
				}
			}
		
		
		//Bouton Volume DOWN
		
		if((xpos > resX/1.5 + 140 && xpos < resX/1.5 +190) && (ypos > resY - (resY/12 + 25) - 50 && ypos < resY - (resY/12 + 25) ))
			if(input.isMouseButtonDown(0)) {
				if(t > 75) {
					Game.DOWNVolume();
					t = 0;
				}
			}
	}

	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 3;
	}

}
