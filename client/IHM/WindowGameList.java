package client.IHM;

import java.util.ArrayList;

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
import client.GameOptions;
import client.ModeJeu;
import server.Partie;

public class WindowGameList extends BasicGameState{
	
	GameContainer container;
	int selectedGame = -1;
	private static ArrayList<Partie> listeParties;
	
	private int resX = Game.res.getX(), resY = Game.res.getY();
	
	public WindowGameList(int state) {
		listeParties = new ArrayList<Partie>();
	}
	
	public static void setListeParties(ArrayList<Partie> parties) {
		listeParties = parties;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		this.container = container;		
		container.setAlwaysRender(true);
		Game.connexionClient.askForGameList();
		//test();
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		resX = Game.res.getX();
		resY = Game.res.getY();
		
		GestionnaireImagesIHM.getRessource("background").draw(0, 0, container.getWidth(), container.getHeight());
		
		g.drawString("Liste des parties:", resX/2 - 75, resY/12);
		
		GestionnaireImagesIHM.getRessource("buttonBack").draw((float) ((resX/18) + (resX/1.6) + 25), (float) (resY/1.15));
		if(selectedGame >= 0)
			GestionnaireImagesIHM.getRessource("buttonJoin").draw((float) ((resX/18) + (resX/1.6) + (resX/3.6) - 75), (float) (resY/1.15));

		g.drawRect((float) (resX/18.0),(float) (resY/6.0),(float) (resX/1.6),(float) (resY/1.3));
		g.drawRect((float) ((resX/18) + (resX/1.6) + 25), (float) (resY/6.0) + 25, (float) (resX/3.6), (float) (resY/1.6));
		
		for(int i = 0; (i < listeParties.size() && i < 16); i++){
			g.drawString("Partie " + (i+1) + " ID: " + listeParties.get(i).getId(), (resX/18) + 25, (float) ((resY/6.0) + 15 + (i*25)));
		}
		
		g.drawString("Options de la partie:" , (float) ((resX/18) + (resX/1.6) + 50), resY/6);
		if(selectedGame >= 0) {
			if(listeParties.get(selectedGame).getOptions().getPrivateGame())
				g.drawString("Mot de passe: Oui" , (float) ((resX/18) + (resX/1.6) + 40), resY/6 + 30);
			else g.drawString("Mot de passe: Non" , (float) ((resX/18) + (resX/1.6) + 40), resY/6 + 30);
			g.drawString("Joueurs max: " + listeParties.get(selectedGame).getOptions().getMaxPlayers(), (float) ((resX/18) + (resX/1.6) + 40), resY/6 + 60);
			
			if(listeParties.get(selectedGame).getOptions().getModeJeu() == ModeJeu.CAPTURE)
				g.drawString("Mode de jeu: Capture du drapeau", (float) ((resX/18) + (resX/1.6) + 40), resY/6 + 90	);
			else if(listeParties.get(selectedGame).getOptions().getModeJeu() == ModeJeu.COURSE)
				g.drawString("Mode de jeu: Course", (float) ((resX/18) + (resX/1.6) + 40), resY/6 + 90	);
			else if(listeParties.get(selectedGame).getOptions().getModeJeu() == ModeJeu.DEATHMATCH)
				g.drawString("Mode de jeu: Deathmatch", (float) ((resX/18) + (resX/1.6) + 40), resY/6 + 90	);
			
			if(listeParties.get(selectedGame).getOptions().getCollisions())
				g.drawString("Collisions: Oui" , (float) ((resX/18) + (resX/1.6) + 40), resY/6 + 120);
			else g.drawString("Collisions: Non" , (float) ((resX/18) + (resX/1.6) + 40), resY/6 + 120);
			if(listeParties.get(selectedGame).getOptions().getTir())
				g.drawString("Tirs: Oui" , (float) ((resX/18) + (resX/1.6) + 40), resY/6 + 150);
			else g.drawString("Tirs: Non" , (float) ((resX/18) + (resX/1.6) + 40), resY/6 + 150);
			if(listeParties.get(selectedGame).getOptions().getReapparitions())
				g.drawString("Reapparition: Oui" , (float) ((resX/18) + (resX/1.6) + 40), resY/6 + 180);
			else g.drawString("Reapparition: Non" , (float) ((resX/18) + (resX/1.6) + 40), resY/6 + 180);
		}

		
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		Input input = container.getInput();
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();	
		
		// Bouton quitter
		if(((xpos > (resX/18) + (resX/1.6) + 25 && xpos <  (resX/18) + (resX/1.6) + 125)&& (ypos > resY - (resY/1.15) - 45 && ypos < resY - (resY/1.15)))) {
			if(input.isMouseButtonDown(0)) {
				sbg.enterState(0, new EmptyTransition(), new FadeInTransition(Color.black));
			}
		}
		
		
		
		
		if(listeParties.size() > 0)
			if((xpos > (resX/18 + 25) && xpos < (resX/18 + 225)) && (ypos < resY - (resY/6 + 15) && ypos > resY - (resY/6+15+25)))
				if(input.isMouseButtonDown(0)) {
					selectedGame = 0;
				}
		if(listeParties.size() > 1)
			if((xpos > (resX/18 + 25) && xpos < (resX/18 + 225)) && (ypos < resY - (resY/6 + 15 + 25) && ypos > resY - (resY/6+15+50)))
				if(input.isMouseButtonDown(0)) {
					selectedGame = 1;
				}
		if(listeParties.size() > 2)
			if((xpos > (resX/18 + 25) && xpos < (resX/18 + 225)) && (ypos < resY - (resY/6 + 15 + 50) && ypos > resY - (resY/6+15+75)))
				if(input.isMouseButtonDown(0)) {
					selectedGame = 2;
				}
		if(listeParties.size() > 3)
			if((xpos > (resX/18 + 25) && xpos < (resX/18 + 225)) && (ypos < resY - (resY/6 + 15 + 75) && ypos > resY - (resY/6+15+100)))
				if(input.isMouseButtonDown(0)) {
					selectedGame = 3;
				}
		if(listeParties.size() > 4)
			if((xpos > (resX/18 + 25) && xpos < (resX/18 + 225)) && (ypos < resY - (resY/6 + 15 + 100) && ypos > resY - (resY/6+15+125)))
				if(input.isMouseButtonDown(0)) {
					selectedGame = 4;
				}
		if(listeParties.size() > 5)
			if((xpos > (resX/18 + 25) && xpos < (resX/18 + 225)) && (ypos < resY - (resY/6 + 15 + 125) && ypos > resY - (resY/6+15+150)))
				if(input.isMouseButtonDown(0)) {
					selectedGame = 5;
				}
		if(listeParties.size() > 6)
			if((xpos > (resX/18 + 25) && xpos < (resX/18 + 225)) && (ypos < resY - (resY/6 + 15 + 150) && ypos > resY - (resY/6+15+175)))
				if(input.isMouseButtonDown(0)) {
					selectedGame = 6;
				}
		if(listeParties.size() > 7)
			if((xpos > (resX/18 + 25) && xpos < (resX/18 + 225)) && (ypos < resY - (resY/6 + 15 + 175) && ypos > resY - (resY/6+15+200)))
				if(input.isMouseButtonDown(0)) {
					selectedGame = 7;
				}
		if(listeParties.size() > 8)
			if((xpos > (resX/18 + 25) && xpos < (resX/18 + 225)) && (ypos < resY - (resY/6 + 15 + 200) && ypos > resY - (resY/6+15+225)))
				if(input.isMouseButtonDown(0)) {
					selectedGame = 8;
				}
		if(listeParties.size() > 9)
			if((xpos > (resX/18 + 25) && xpos < (resX/18 + 225)) && (ypos < resY - (resY/6 + 15 + 225) && ypos > resY - (resY/6+15+250)))
				if(input.isMouseButtonDown(0)) {
					selectedGame = 9;
				}
		if(listeParties.size() > 10)
			if((xpos > (resX/18 + 25) && xpos < (resX/18 + 225)) && (ypos < resY - (resY/6 + 15 + 250) && ypos > resY - (resY/6+15+275)))
				if(input.isMouseButtonDown(0)) {
					selectedGame = 10;
				}
		if(listeParties.size() > 11)
			if((xpos > (resX/18 + 25) && xpos < (resX/18 + 225)) && (ypos < resY - (resY/6 + 15 + 275) && ypos > resY - (resY/6+15+300)))
				if(input.isMouseButtonDown(0)) {
					selectedGame = 11;
				}
		if(listeParties.size() > 12)
			if((xpos > (resX/18 + 25) && xpos < (resX/18 + 225)) && (ypos < resY - (resY/6 + 15 + 300) && ypos > resY - (resY/6+15+325)))
				if(input.isMouseButtonDown(0)) {
					selectedGame = 12;
				}
		if(listeParties.size() > 13)
			if((xpos > (resX/18 + 25) && xpos < (resX/18 + 225)) && (ypos < resY - (resY/6 + 15 + 325) && ypos > resY - (resY/6+15+350)))
				if(input.isMouseButtonDown(0)) {
					selectedGame = 13;
				}
		if(listeParties.size() > 14)
			if((xpos > (resX/18 + 25) && xpos < (resX/18 + 225)) && (ypos < resY - (resY/6 + 15 + 350) && ypos > resY - (resY/6+15+375)))
				if(input.isMouseButtonDown(0)) {
					selectedGame = 14;
				}
		if(listeParties.size() > 15)
			if((xpos > (resX/18 + 25) && xpos < (resX/18 + 225)) && (ypos < resY - (resY/6 + 15 + 375) && ypos > resY - (resY/6+15+400)))
				if(input.isMouseButtonDown(0)) {
					selectedGame = 15;
				}
		
		
		
		//System.out.println(selectedGame);
	}

	@Override
	public int getID() {
		return 6;
	}
	
	public void test() {
		for(int i = 0; i < 16; i++)
			listeParties.add(new Partie(i, new GameOptions()));
		

	}
}
	
