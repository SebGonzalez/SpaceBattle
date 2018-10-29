package client;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.esotericsoftware.minlog.Log;

import client.IHM.WindowGame;
import client.IHM.WindowJoinGame;
import client.IHM.WindowLobby;
import client.IHM.WindowMainMenu;
import client.IHM.WindowOptions;

public class Game extends StateBasedGame{
	
	public static final int menu = 0;
	public static final int jeu = 1;
	public static final int lobby = 2;
	public static final int options = 3;
	public static final int joinGame = 4;
	public static Resolution res = Resolution.LOW;
	public static Music ambiance;
	
	public Game() {
		super("SpaceBattle");
		this.addState(new WindowMainMenu(menu));
		this.addState(new WindowGame(jeu));
		this.addState(new WindowLobby(lobby));
		this.addState(new WindowOptions(options));
		this.addState(new WindowJoinGame(joinGame));
	}
	
	public static void setResolution(Resolution r) {
		res = r;
	}
	
	public static void playMusic() throws SlickException {
		ambiance = new Music ("ressources/sounds/ambiance.ogg");
		ambiance.play(1, 0.2f);
	}
	
	public static int getMusicVolume() {
		return (int) (ambiance.getVolume()*100);
	}

	public static void UPVolume() {
		ambiance.setVolume(ambiance.getVolume() + 0.01f);
	}
	
	public static void DOWNVolume() {
		ambiance.setVolume(ambiance.getVolume() - 0.01f);
	}
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
	/*	this.getState(menu).init(container, this);
		this.getState(jeu).init(container, this);
		this.getState(lobby).init(container, this);
		this.enterState(menu);*/
	}
	
	public static void main(String[] args) {
		
		Log.set(Log.LEVEL_DEBUG);
		
		AppGameContainer appgc;
		try {
			appgc = new AppGameContainer(new Game());
			appgc.setDisplayMode(res.getX(), res.getY(), false);
			appgc.start();
		}catch(SlickException e) {
			System.out.println(e);
		}
	}
}
