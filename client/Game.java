package client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.esotericsoftware.minlog.Log;

import client.IHM.*;

	
public class Game extends StateBasedGame{
	
	public static final int menu = 0;
	public static final int jeu = 1;
	public static final int lobby = 2;
	public static final int options = 3;
	public static final int joinGame = 4;
	public static final int createGame = 5;
	public static Resolution res = Resolution.LOW;
	public static Music ambiance;
	public static float musicVolume = 0.0f;
	
	public static ConnectionClient connexionClient;
	
	public Game() {
		super("SpaceBattle");
		
		connexionClient = new ConnectionClient();
		connexionClient.connect();
	}
	
	public static void setResolution(Resolution r) {
		res = r;
	}
	
	public static void playMusic() throws SlickException {
		ambiance = new Music ("ressources/sounds/ambiance.ogg");
		ambiance.play(1, musicVolume);
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
		this.addState(new WindowMainMenu(menu));
		this.addState(new WindowGame(jeu));
		this.addState(new WindowLobby(lobby));
		this.addState(new WindowOptions(options));
		this.addState(new WindowJoinGame(joinGame));
		this.addState(new WindowCreateGame(createGame));
	}
	
	public static void main(String[] args) {
		
		AppGameContainer appgc;
		try {
			if(Game.settingsFileExists())
				Game.loadSettings();
			else
				Game.createSettingsFile();
			appgc = new AppGameContainer(new Game());
			appgc.setDisplayMode(res.getX(), res.getY(), false);
			appgc.start();
		}catch(SlickException e) {
			System.out.println(e);
		}

	}
	
	public static void loadSettings() {
		String resolution, volume;
		Properties settings = new Properties();
		InputStream is = null;
		
		try {
			File f = new File("settings.cfg");
			is = new FileInputStream(f);
		} catch(Exception e) {
			e.printStackTrace();
		}
		try {
			settings.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		resolution = settings.getProperty("resolution", "LOW");
		volume = settings.getProperty("musicVolume", "0");
		
		musicVolume = Float.parseFloat(volume);
		switch (resolution) {
		case "LOW":
			res = Resolution.LOW;
			break;
		case "MED":
			res = Resolution.MED;
			break;
		case "HIGH":
			res = Resolution.HIGH;
			break;
		case "EXTRAHIGH":
			res = Resolution.EXTRAHIGH;
			break;
		}
		
		System.out.println("Parametres chargés");
	}
	
	public static boolean settingsFileExists() {
		File f = new File("settings.cfg");
		if(f.isFile())
			return true;
		else return false;
	}
	
	public static void saveSettings() {
		if(settingsFileExists()) {
			Properties settings = new Properties();
			settings.setProperty("musicVolume", String.valueOf(ambiance.getVolume()));

			switch (res) {
			case LOW:
				settings.setProperty("resolution", "LOW");
				break;
			case MED:
				settings.setProperty("resolution", "MED");
				break;
			case HIGH:
				settings.setProperty("resolution", "HIGH");
				break;
			case EXTRAHIGH:
				settings.setProperty("resolution", "EXTRAHIGH");
				break;
			}
		
			File f = new File("settings.cfg");
			try {
				OutputStream out = new FileOutputStream(f);
				try {
					settings.store(out, "Parametres SpaceBattle");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Parametres sauvegard�s");
		}
	}
	
	
	public static void createSettingsFile() {
		File f = new File("settings.cfg");
		Properties settings = new Properties();
		
		settings.setProperty("resolution", "LOW");
		settings.setProperty("musicVolume", "0");
		
		try {
			OutputStream out = new FileOutputStream(f);
			
				try {
					settings.store(out, "Parametres SpaceBattle");
					System.out.println("Fichier parametres cr��");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
