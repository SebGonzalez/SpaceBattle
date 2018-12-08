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
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;

import client.ConnectionClient;
import client.Game;
import client.GameOptions;
import client.ModeJeu;
import client.Gestionnaire.GestionnairePartie;
import client.Gestionnaire.GestionnairePartieCapture;
import client.Gestionnaire.GestionnairePartieCourse;

public class WindowCreateGame extends BasicGameState implements KeyListener {

	private int temps = 0;

	private int currentField;

	private boolean optionsScreen = false;

	String hostname = "";
	
	boolean permit = true;
	
	private GameOptions options;

	private TextField passwordField, maxPlayersField, nameField;

	private int resX = Game.res.getX(), resY = Game.res.getY();

	private UnicodeFont font;

	public WindowCreateGame(int state) {
		options = new GameOptions();
	}

	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		GestionnaireImagesIHM.loadCreateGame();

		font = new UnicodeFont(new Font("Arial", Font.PLAIN, 20));
		font.addAsciiGlyphs();
		font.addGlyphs(400, 600);
		font.getEffects().add(new ColorEffect(java.awt.Color.white));

		try {
			font.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}

		container.setAlwaysRender(true);
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		GestionnaireImagesIHM.getRessource("background").draw(0, 0, container.getWidth(), container.getHeight());

		resX = Game.res.getX();
		resY = Game.res.getY();

		if (!optionsScreen) {
			g.drawString("Creation de la partie: ", resX / 2 - 100, resY / 4 - 30);
			g.drawString("Mode de jeu: ", resX / 2 - 200, resY / 4);

			g.drawString("Nombre maximum de joueurs (2-64): ", resX / 2 - 200, resY / 3 + 70);
			maxPlayersField = new TextField(container, font, resX / 2 - 200, resY / 3 + 100, 400, 20);
			maxPlayersField.setTextColor(Color.white);
			maxPlayersField.setText(String.valueOf(options.getMaxPlayers()));
			maxPlayersField.render(container, g);

			g.drawString("Veuillez entrer votre nom", resX / 2 - 200, resY / 2 + 15);
			nameField = new TextField(container, font, resX / 2 - 200, resY / 2 + 40, 400, 20);
			nameField.setText(hostname);
			nameField.setTextColor(Color.white);
			nameField.render(container, g);
			
			if(!permit) g.drawString(" N'oubliez pas d'entrer votre pseudo !  ", resX / 2 - 200, resY  - 50);

			g.drawString("Partie avec mot de passe", resX / 2 - 200, resY / 3 + 190);
			GestionnaireImagesIHM.getRessource("buttonOui").draw(resX / 2 - 200, resY / 3 + 220);
			GestionnaireImagesIHM.getRessource("buttonNon").draw(resX / 2 - 100, resY / 3 + 220);

			GestionnaireImagesIHM.getRessource("buttonOptions").draw(resX / 2 + 250, resY / 4 + 30);

			if (options.getPrivateGame()) {
				passwordField = new TextField(container, font, resX / 2, resY / 3 + 220, 400, 20);
				passwordField.setTextColor(Color.white);
				passwordField.setText(options.getPassword());
				passwordField.render(container, g);
			}

			GestionnaireImagesIHM.getRessource("buttonBack").draw(resX / 2 + 300, resY / 3 + 275);
			GestionnaireImagesIHM.getRessource("buttonCreer").draw(resX / 2 + 150, resY / 3 + 275);

			switch (options.getModeJeu()) {
			case DEATHMATCH:
				GestionnaireImagesIHM.getRessource("buttonMode1Selec").draw(resX / 2 - 300, resY / 4 + 30);
				GestionnaireImagesIHM.getRessource("buttonMode2").draw(resX / 2 - 150, resY / 4 + 30);
				GestionnaireImagesIHM.getRessource("buttonMode3").draw(resX / 2, resY / 4 + 30);
				break;
			case CAPTURE:
				GestionnaireImagesIHM.getRessource("buttonMode1").draw(resX / 2 - 300, resY / 4 + 30);
				GestionnaireImagesIHM.getRessource("buttonMode2Selec").draw(resX / 2 - 150, resY / 4 + 30);
				GestionnaireImagesIHM.getRessource("buttonMode3").draw(resX / 2, resY / 4 + 30);
				break;
			case COURSE:
				GestionnaireImagesIHM.getRessource("buttonMode1").draw(resX / 2 - 300, resY / 4 + 30);
				GestionnaireImagesIHM.getRessource("buttonMode2").draw(resX / 2 - 150, resY / 4 + 30);
				GestionnaireImagesIHM.getRessource("buttonMode3Selec").draw(resX / 2, resY / 4 + 30);
				break;
			default:
				GestionnaireImagesIHM.getRessource("buttonMode1").draw(resX / 2 - 300, resY / 4 + 30);
				GestionnaireImagesIHM.getRessource("buttonMode2").draw(resX / 2 - 150, resY / 4 + 30);
				GestionnaireImagesIHM.getRessource("buttonMode3").draw(resX / 2, resY / 4 + 30);
				break;
			}

			g.drawString("Lobby", resX / 5 - 50, resY / 2);
			if (options.getLobby())
				GestionnaireImagesIHM.getRessource("buttonOui").draw(resX / 5 - 50, resY / 2 + 35);
			else
				GestionnaireImagesIHM.getRessource("buttonNon").draw(resX / 5 - 50, resY / 2 + 35);

		}

		else {
			g.drawString("Options de la partie: ", resX / 2 - 100, 40);

			g.drawString("Reapparition perso.", resX / 4 - 100, resY / 4 - 50);
			if (options.getReapparitions())
				GestionnaireImagesIHM.getRessource("buttonOui").draw(resX / 4 - 50, resY / 4);
			else
				GestionnaireImagesIHM.getRessource("buttonNon").draw(resX / 4 - 50, resY / 4);

			g.drawString("Tirs", resX / 4 + 200, resY / 4 - 50);
			if (options.getTir())
				GestionnaireImagesIHM.getRessource("buttonOui").draw(resX / 4 + 200, resY / 4);
			else
				GestionnaireImagesIHM.getRessource("buttonNon").draw(resX / 4 + 200, resY / 4);

			g.drawString("Collisions", resX / 4 + 450, resY / 4 - 50);
			if (options.getCollisions())
				GestionnaireImagesIHM.getRessource("buttonOui").draw(resX / 4 + 450, resY / 4);
			else
				GestionnaireImagesIHM.getRessource("buttonNon").draw(resX / 4 + 450, resY / 4);

			GestionnaireImagesIHM.getRessource("buttonBack").draw(resX / 2 + 300, resY / 3 + 275);
		}

	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		Input input = container.getInput();
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();

		temps += delta;

		if (!optionsScreen) {

			// MaxPlayers
			if ((xpos > resX / 2 - 200 && xpos < resX / 2 + 200) && (ypos > resY - (resY / 3 + 120) && ypos < resY - (resY / 3 + 100)))
				if (input.isMouseButtonDown(0))
					currentField = 1;

			// Password: OUI
			if ((xpos > resX / 2 - 200 && xpos < resX / 2 + -125) && (ypos > resY - (resY / 3 + 265) && ypos < resY - (resY / 3 + 220)))
				if (input.isMouseButtonDown(0))
					options.setPrivateGame(true);

			// Password: NON
			if ((xpos > resX / 2 - 100 && xpos < resX / 2 + -25) && (ypos > resY - (resY / 3 + 265) && ypos < resY - (resY / 3 + 220)))
				if (input.isMouseButtonDown(0))
					options.setPrivateGame(false);

			// Password field
			if ((xpos > resX / 2 && xpos < resX / 2 + 400) && (ypos > resY - (resY / 3 + 240) && ypos < resY - (resY / 3 + 220)))
				if (input.isMouseButtonDown(0))
					currentField = 2;

			// Bouton retour
			if ((xpos > resX / 2 + 300 && xpos < resX / 2 + 400) && (ypos > resY - (resY / 3 + 320) && ypos < resY - (resY / 3 + 275)))
				if (input.isMouseButtonDown(0))
					if (temps > 100) {
						temps = 0;
						sbg.enterState(0);
					}

			// nameField
			if ((xpos > resX / 2 - 200 && xpos < resX / 2 + 200) && (ypos < resY - (resY / 2 + 40) && ypos > resY - (resY / 2 + 60)))
				if (input.isMouseButtonDown(0)) {
					currentField = 3;
				}

			// Bouton creer
			if ((xpos > resX / 2 + 150 && xpos < resX / 2 + 250) && (ypos > resY - (resY / 3 + 320) && ypos < resY - (resY / 3 + 275)))
				if (input.isMouseButtonDown(0)) {
					permit = true;
					if ( !(hostname.length() == 0 ) ) {
					if (options.getModeJeu() == ModeJeu.DEATHMATCH)
						Game.gestionnairePartie = new GestionnairePartie(options);
					else if (options.getModeJeu() == ModeJeu.CAPTURE)
						Game.gestionnairePartie = new GestionnairePartieCapture(options);
					else
						Game.gestionnairePartie = new GestionnairePartieCourse(options); // sprint

					Game.connexionClient = new ConnectionClient(Game.gestionnairePartie, Game.adresseipserveur, Game.portTCP, Game.portUDP);
					Game.connexionClient.connect();
					Game.connexionClient.createGame(hostname);
					Game.gestionnairePartie.joueur.setNom("Hôte");

					if (options.getLobby() == false)
						sbg.enterState(Game.jeu, new EmptyTransition(), new FadeInTransition(Color.black));
					
					else {
						
						WindowLobby.playersInLobby.add(Game.gestionnairePartie.joueur.getNom());
						WindowLobby.hote = true;
						sbg.enterState(Game.lobby, new EmptyTransition(), new FadeInTransition(Color.pink));
						
						}
					}
					
					else permit = false;
					
				}

			// Boutons Modes
			if ((xpos > resX / 2 - 300 && xpos < resX / 2 - 200) && (ypos > resY - (resY / 4 + 75) && ypos < resY - (resY / 4 + 30)))
				if (input.isMouseButtonDown(0))
					options.setModeJeu(ModeJeu.DEATHMATCH);
			if ((xpos > resX / 2 - 150 && xpos < resX / 2 - 50) && (ypos > resY - (resY / 4 + 75) && ypos < resY - (resY / 4 + 30)))
				if (input.isMouseButtonDown(0))
					options.setModeJeu(ModeJeu.CAPTURE);
			if ((xpos > resX / 2 && xpos < resX / 2 + 100) && (ypos > resY - (resY / 4 + 75) && ypos < resY - (resY / 4 + 30)))
				if (input.isMouseButtonDown(0))
					options.setModeJeu(ModeJeu.COURSE);

			switch (currentField) {
			case 1:
				maxPlayersField.setText(String.valueOf(options.getMaxPlayers()));
				break;
			case 2:
				passwordField.setText(options.getPassword());
				break;
			case 3:
				nameField.setText(hostname);
				break;

			}
			// Bouton options
			if ((xpos > resX / 2 + 250 && xpos < resX / 2 + 350) && (ypos > resY - (resY / 4 + 75) && ypos < resY - (resY / 4 + 30)))
				if (input.isMouseButtonDown(0))
					if (temps > 100) {
						temps = 0;
						optionsScreen = !optionsScreen;
					}

			if ((xpos > resX / 5 - 50 && xpos < resX / 5 + 25) && (ypos > resY - (resY / 2 + 85) && ypos < resY - (resY / 2 + 35)))
				if (input.isMouseButtonDown(0))
					if (temps > 300) {
						temps = 0;
						options.setLobby(!options.getLobby());
					}
		} else {
			// Bouton retour
			if ((xpos > resX / 2 + 300 && xpos < resX / 2 + 400) && (ypos > resY - (resY / 3 + 320) && ypos < resY - (resY / 3 + 275)))
				if (input.isMouseButtonDown(0))
					if (temps > 100) {
						temps = 0;
						optionsScreen = !optionsScreen;
					}

			if ((xpos > resX / 4 - 50 && xpos < resX / 4 + 25) && (ypos > resY - (resY / 4 + 50) && ypos < resY - (resY / 4)))
				if (input.isMouseButtonDown(0))
					if (temps > 300) {
						temps = 0;
						options.setReapparitions(!options.getReapparitions());
					}

			if ((xpos > resX / 4 + 200 && xpos < resX / 4 + 275) && (ypos > resY - (resY / 4 + 50) && ypos < resY - (resY / 4)))
				if (input.isMouseButtonDown(0))
					if (temps > 300) {
						temps = 0;
						options.setTir(!options.getTir());
					}

			if ((xpos > resX / 4 + 450 && xpos < resX / 4 + 525) && (ypos > resY - (resY / 4 + 50) && ypos < resY - (resY / 4)))
				if (input.isMouseButtonDown(0))
					if (temps > 300) {
						temps = 0;
						options.setCollisions(!options.getCollisions());
					}
		}

	}

	public void keyReleased(int key, char c) {
		String input;

		if (currentField == 1) {
			input = String.valueOf(options.getMaxPlayers());

			if (Character.isDigit(c)) {
				input += String.valueOf(c);
			}
			if (input != "")
				if (Integer.parseInt(input) > 64)
					input = "64";

			if (key == Input.KEY_BACK && input.length() > 0)
				if (input.length() == 1)
					input = "0";
				else
					input = input.substring(0, input.length() - 1);

			options.setMaxPlayers(Integer.parseInt(input));
		}

		if (currentField == 2) {
			input = options.getPassword();

			if (input.length() < 64)
				input += c;

			if (key == Input.KEY_BACK && input.length() > 0)
				input = input.substring(0, input.length() - 1);

			options.setPassword(input);
		}

		if (currentField == 3) {
			
			if (key == Input.KEY_BACK && hostname.length() > 0)
				hostname = hostname.substring(0, hostname.length() - 1);
			else if (key != Input.KEY_BACK)
				hostname += c;
			
		}
	}

	@Override
	public int getID() {
		return 5;
	}

	public GameOptions getOptions() {
		return options;
	}

	public void createGame() {
		// TODO
	}
}
