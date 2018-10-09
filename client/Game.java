package client;

import java.io.File;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame{

	public static final int menu = 0;
	public static final int jeu = 1;
	public static final int lobby = 2;
	
	public Game() {
		super("SpaceBattle");
		this.addState(new WindowMainMenu(menu));
		this.addState(new WindowGame(jeu));
		this.addState(new WindowLobby(lobby));
	}
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.getState(menu).init(container, this);
		this.getState(jeu).init(container, this);
		this.getState(lobby).init(container, this);
		this.enterState(menu);
	}
	
	public static void main(String[] args) {
		System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
		System.setProperty("java.library.path", new File("natives").getAbsolutePath());

		AppGameContainer appgc;
		try {
			appgc = new AppGameContainer(new Game());
			appgc.setDisplayMode(800, 600, false);
			appgc.start();
		}catch(SlickException e) {
			System.out.println(e);
		}
	}
}
