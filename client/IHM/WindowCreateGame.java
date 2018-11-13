package client.IHM;

import java.awt.Font;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import client.Game;

public class WindowCreateGame extends BasicGameState implements KeyListener{
	
	private GameContainer container;
	
	private boolean privateGame;
	private String password;
	private int maxPlayers;
	private int currentField, selectedMode;
	
	private TextField passwordField, maxPlayersField;
	
	private int resX = Game.res.getX(), resY = Game.res.getY();
	
	private UnicodeFont font;
	
	public WindowCreateGame(int state) {
		privateGame = false;
		password = ""; maxPlayers = 8;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		GestionnaireImagesIHM.loadCreateGame();	
		
		font = new UnicodeFont(new Font("Arial", Font.PLAIN, 20));
		font.addAsciiGlyphs(); font.addGlyphs(400,600); font.getEffects().add(new ColorEffect(java.awt.Color.white));
		
		try {
			font.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		this.container = container;
		
		container.setAlwaysRender(true);
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		GestionnaireImagesIHM.getRessource("background").draw(0,0,container.getWidth(), container.getHeight());
		
		resX = Game.res.getX();
		resY = Game.res.getY();
		
		g.drawString("Creation de la partie: ", resX/2 - 100, resY/4 - 30);
		g.drawString("Mode de jeu: ", resX/2 - 200, resY/4);
		
		g.drawString("Nombre maximum de joueurs (2-64): ", resX/2 - 200, resY/3 + 70);
		maxPlayersField = new TextField(container, font, resX/2 - 200, resY/3 + 100, 400, 20);
		maxPlayersField.setTextColor(Color.white);
		maxPlayersField.setText(String.valueOf(maxPlayers));
		maxPlayersField.render(container, g);
		
		g.drawString("Partie avec mot de passe", resX/2 - 200, resY/3 + 190);
		GestionnaireImagesIHM.getRessource("passwordOUI").draw(resX/2 - 200, resY/3 + 220);
		GestionnaireImagesIHM.getRessource("passwordNON").draw(resX/2 - 100, resY/3 + 220);
		

		
		if(privateGame) {
			passwordField = new TextField(container, font, resX/2, resY/3 + 220, 400, 20);
			passwordField.setTextColor(Color.white);
			passwordField.setText(password);
			passwordField.render(container, g);
		}
		
		GestionnaireImagesIHM.getRessource("buttonBack").draw(resX/2 + 300, resY/3 + 275);
		GestionnaireImagesIHM.getRessource("buttonCreer").draw(resX/2 + 150, resY/3 + 275);
		
		switch(selectedMode) {
		case 1:
			GestionnaireImagesIHM.getRessource("buttonMode1Selec").draw(resX/2 - 300, resY/4 + 30);
			GestionnaireImagesIHM.getRessource("buttonMode2").draw(resX/2 - 150, resY/4 + 30);
			GestionnaireImagesIHM.getRessource("buttonMode3").draw(resX/2, resY/4 + 30);
			break;
		case 2:
			GestionnaireImagesIHM.getRessource("buttonMode1").draw(resX/2 - 300, resY/4 + 30);
			GestionnaireImagesIHM.getRessource("buttonMode2Selec").draw(resX/2 - 150, resY/4 + 30);
			GestionnaireImagesIHM.getRessource("buttonMode3").draw(resX/2, resY/4 + 30);
			break;
		case 3:
			GestionnaireImagesIHM.getRessource("buttonMode1").draw(resX/2 - 300, resY/4 + 30);
			GestionnaireImagesIHM.getRessource("buttonMode2").draw(resX/2 - 150, resY/4 + 30);
			GestionnaireImagesIHM.getRessource("buttonMode3Selec").draw(resX/2, resY/4 + 30);
			break;
		default:
			GestionnaireImagesIHM.getRessource("buttonMode1").draw(resX/2 - 300, resY/4 + 30);
			GestionnaireImagesIHM.getRessource("buttonMode2").draw(resX/2 - 150, resY/4 + 30);
			GestionnaireImagesIHM.getRessource("buttonMode3").draw(resX/2, resY/4 + 30);
			break;
		}
		
		System.out.println(selectedMode);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		Input input = container.getInput();
		int xpos = Mouse.getX(); int ypos = Mouse.getY();
		
		//MaxPlayers
		if((xpos > resX/2 - 200 && xpos < resX/2 + 200) && (ypos > resY - (resY/3 + 120) && ypos < resY - (resY/3 + 100)))		
			if(input.isMouseButtonDown(0))
				currentField = 1;
		
		//Password: OUI
		if((xpos > resX/2 - 200 && xpos < resX/2 + -125) && (ypos > resY - (resY/3 + 265) && ypos < resY - (resY/3 + 220)))		
			if(input.isMouseButtonDown(0))
				privateGame = true;
		
		//Password: NON
		if((xpos > resX/2 - 100 && xpos < resX/2 + -25) && (ypos > resY - (resY/3 + 265) && ypos < resY - (resY/3 + 220)))		
			if(input.isMouseButtonDown(0))
				privateGame = false;
		
		//Password field
		if((xpos > resX/2 && xpos < resX/2 + 400) && (ypos > resY - (resY/3 + 240) && ypos < resY - (resY/3 + 220)))		
			if(input.isMouseButtonDown(0))
				currentField = 2;
		
		//Bouton retour
		if((xpos > resX/2 + 300 && xpos < resX/2 + 400) && (ypos > resY - (resY/3 + 320) && ypos < resY - (resY/3 + 275)))		
			if(input.isMouseButtonDown(0))
				sbg.enterState(0);
		
		//Bouton creer
		if((xpos > resX/2 + 300 && xpos < resX/2 + 400) && (ypos > resY - (resY/3 + 320) && ypos < resY - (resY/3 + 275)))		
			if(input.isMouseButtonDown(0))
				createGame();
		
		//Boutons Modes
		if((xpos > resX/2 - 300 && xpos < resX/2 - 200) && (ypos > resY - (resY/4 + 75) && ypos < resY - (resY/4 + 30)))		
			if(input.isMouseButtonDown(0))
				selectedMode = 1;
		if((xpos > resX/2 - 150 && xpos < resX/2 - 50) && (ypos > resY - (resY/4 + 75) && ypos < resY - (resY/4 + 30)))		
			if(input.isMouseButtonDown(0))
				selectedMode = 2;
		if((xpos > resX/2 && xpos < resX/2 + 100) && (ypos > resY - (resY/4 + 75) && ypos < resY - (resY/4 + 30)))		
			if(input.isMouseButtonDown(0))
				selectedMode = 3;
		
		switch (currentField) {
			case 1:
				maxPlayersField.setText(String.valueOf(maxPlayers));
				break;
			case 2:
				passwordField.setText(password);
				break;
			
		}
	}
	
	public void keyReleased(int key, char c) {
		String input;
		
		if(currentField == 1) {
			input = String.valueOf(maxPlayers);
			
			if(Character.isDigit(c)) {
				input+= String.valueOf(c);
			}
			if(input != "")
				if(Integer.parseInt(input) > 64)
					input = "64";

			if(key == Input.KEY_BACK && input.length() > 0)
				if(input.length() == 1)
					input = "0";
				else
					input = input.substring(0, input.length()-1);
			
			maxPlayers = Integer.parseInt(input);
		}
		
		if(currentField == 2) {
			input = password;
			
			if(input.length() < 64)
				input+= c;
			
			if(key == Input.KEY_BACK && input.length() > 0)
				input = input.substring(0, input.length()-1);
			
			password = input;
		}
	}

	@Override
	public int getID() {
		return 5;
	}
	
	public String getPassword() {
		return password;
	}
	
	public boolean getPrivateGame() {
		return privateGame;
	}
	
	public void createGame() {
		//TODO
	}
}
