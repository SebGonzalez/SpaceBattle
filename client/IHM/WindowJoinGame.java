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
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;

import client.ConnectionClient;
import client.Game;
import client.GameOptions;
import client.ModeJeu;
import client.Gestionnaire.GestionnairePartie;
import client.Gestionnaire.GestionnairePartieCapture;
import client.Gestionnaire.GestionnairePartieCourse;

import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.Font;

public class WindowJoinGame extends BasicGameState implements KeyListener{

	private GameContainer container;
	private TextField gameID;
	private TextField playerName;
	private String input = "";
	private String input2 = "";
	private UnicodeFont font;
	private int resX = Game.res.getX(), resY = Game.res.getY();
	
	boolean nameUse = true;
	
	public WindowJoinGame(int state) {
		input="";
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		
		resX = Game.res.getX();
		resY = Game.res.getY();
		
		font = new UnicodeFont(new Font("Arial", Font.PLAIN, 20));
		font.addAsciiGlyphs(); font.addGlyphs(400,600); font.getEffects().add(new ColorEffect(java.awt.Color.white));
		try {
			font.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		this.container = container;

		container.setAlwaysRender(true);
		GestionnaireImagesIHM.loadJoinGame();
		
		gameID = new TextField(container, font, resX/2 - 200, resY/2 - 10, 400, 20);
		gameID.setTextColor(Color.white);
		
		playerName = new TextField(container, font, resX/2 - 200, resY/3 - 10, 400, 20);
		playerName.setTextColor(Color.white);
		playerName.setText(input2);
	}
	
	

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		resX = Game.res.getX(); resY = Game.res.getY();
		GestionnaireImagesIHM.getRessource("background").draw(0,0,container.getWidth(), container.getHeight());
		GestionnaireImagesIHM.getRessource("buttonJoin").draw(resX/2+100, resY/2 + 50);
		GestionnaireImagesIHM.getRessource("buttonBack").draw(resX/2-200, resY/2+50);
		g.drawString("Entrez l'ID de la partie: ", resX/2 - 200, resY/2 - 35);
		g.drawString("Entrez votre nom : ", resX/2 - 200, resY/3 - 35);
		
		

		gameID.render(container, g);
		playerName.render(container, g);
	}
		
	public void keyReleased(int key, char c) {
		if(key == Input.KEY_ENTER)
			nameUse = !nameUse;
		
		
		if(nameUse) input2+=c;
		else {
			if( Character.isDigit(c) ) 
			input+=c;
		}
			if(key == Input.KEY_BACK && input2.length() > 0)
				input2 = input2.substring(0, input2.length()-1);
		
		
			
			if(key == Input.KEY_BACK && input.length() > 0)
				input = input.substring(0, input.length()-1);
		
		}
	

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		gameID.setText(input);
		playerName.setText(input2);
		Input input = container.getInput();
		int xpos = Mouse.getX(); int ypos = Mouse.getY();
		
		if((xpos > resX/2 + 100 && xpos < resX/2 + 200) && ( ypos > resY - (resY/2 + 100) && ypos < resY - (resY/2 + 55)))
			if(input.isMouseButtonDown(0)) {
				ModeJeu selectedMode = ModeJeu.DEATHMATCH; //A recup dans la liste de partie
				if(selectedMode == ModeJeu.DEATHMATCH) //remplacer par gameOptions.getModeJeu() == ModeJeu.DEATHMATCH
					Game.gestionnairePartie = new GestionnairePartie(new GameOptions());
				else if(selectedMode == ModeJeu.CAPTURE)
					Game.gestionnairePartie = new GestionnairePartieCapture(new GameOptions());
				else
					Game.gestionnairePartie = new GestionnairePartieCourse(new GameOptions()); //sprint
				
				Game.connexionClient = new ConnectionClient(Game.gestionnairePartie,Game.adresseipserveur,Game.portTCP,Game.portUDP);
				Game.connexionClient.connect();
				Game.gestionnairePartie.setIdPartie(Integer.parseInt(gameID.getText()));
				Game.connexionClient.joinGame(playerName.getText());
				
				if(!Game.gestionnairePartie.getOptionsPartie().getLobby())
					sbg.enterState(1, new EmptyTransition(), new FadeInTransition(Color.black));
				else {
					WindowLobby.hote = false;
					sbg.enterState(Game.lobby, new EmptyTransition(), new FadeInTransition(Color.black));
				}
			}
		if((xpos > resX/2 - 200 && xpos < resX/2 -100) && (ypos > resY - (resY/2 + 100) && ypos < resY - (resY/2 + 55)))
			if(input.isMouseButtonDown(0)) {
				sbg.enterState(0, new EmptyTransition(), new FadeInTransition(Color.black));
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
