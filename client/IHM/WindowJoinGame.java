package client.IHM;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import client.Game;

import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.Font;

public class WindowJoinGame extends BasicGameState implements KeyListener{

	private GameContainer container;
	private TextField test;
	private String input;
	private UnicodeFont font;
	private int resX = Game.res.getX(), resY = Game.res.getY();
	
	public WindowJoinGame(int state) {
		input="";
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		
		
		font = new UnicodeFont(new Font("Arial", Font.PLAIN, 20));
		font.addAsciiGlyphs(); font.addGlyphs(400,600); font.getEffects().add(new ColorEffect(java.awt.Color.white));
		try {
			font.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		this.container = container;
		test = new TextField(container, font, resX/2 - 200, resY/2 - 10, 400, 20);
		test.setTextColor(Color.white);
		test.setText(input);
		container.setAlwaysRender(true);
		GestionnaireImagesIHM.loadJoinGame();
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		GestionnaireImagesIHM.getRessource("background").draw(0,0,container.getWidth(), container.getHeight());
		GestionnaireImagesIHM.getRessource("buttonJoin").draw(resX/2+100, resY/2 + 50);
		GestionnaireImagesIHM.getRessource("buttonBack").draw(resX/2-200, resY/2+50);
		g.drawString("Entrez l'ID de la partie: ", resX/2 - 200, resY/2 - 35);
		test.render(container, g);
	}
		
	public void keyReleased(int key, char c) {
		if(Character.isDigit(c)) {
			input+=c;
			test.setText(input);
		}
		if(key == Input.KEY_BACK && input.length() > 0)
			input = input.substring(0, input.length()-1);
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		test.setText(input);
		
		Input input = container.getInput();
		int xpos = Mouse.getX(); int ypos = Mouse.getY();
		
		if((xpos > resX/2 + 100 && xpos < resX/2 + 200) && ( ypos > resY - (resY/2 + 100) && ypos < resY - (resY/2 + 55)))
			if(input.isMouseButtonDown(0)) {
				//sendGameID();
				//POUR TESTER
				sbg.enterState(1);
			}
		if((xpos > resX/2 - 200 && xpos < resX/2 -100) && (ypos > resY - (resY/2 + 100) && ypos < resY - (resY/2 + 55)))
			if(input.isMouseButtonDown(0)) {
				sbg.enterState(0);
			}
	}
	
	public void sendGameID() {
		//TODO
		System.out.println(input);
	}

	@Override
	public int getID() {
		return 4;
	}

}
