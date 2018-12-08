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

import client.Gestionnaire.GestionnairePartie;
import client.IHM.WindowCreateGame;
import client.IHM.WindowGame;
import client.IHM.WindowGameList;
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
	public static final int createGame = 5;
	public static final int gameList = 6;
	public static Resolution res = Resolution.LOW;
	public static Music ambiance;
	public static float musicVolume = 0.0f;
	public static String adresseipserveur; 
	public static int portTCP;
	public static int portUDP;
	
	public static ConnectionClient connexionClient;

	public static GestionnairePartie gestionnairePartie;
	
	public Game() {
		super("SpaceBattle");
		
		//gestionnairePartie = new GestionnairePartie();
		connexionClient = new ConnectionClient(gestionnairePartie,adresseipserveur,portTCP,portUDP);
		connexionClient.connect();
	}
	/**
	 * Modifie la résolution
	 * @param r résolution voulue 
	 * @author Amine Boudraa
	 */
	public static void setResolution(Resolution r) {
		res = r;
	}
	
	/**
	 * Lance la musique du jeu stockée dans les ressources
	 * @author Amine Boudraa
	 */
	public static void playMusic() throws SlickException {
		ambiance = new Music ("ressources/sounds/ambiance.ogg");
		ambiance.play(1, musicVolume);
	}
	
	/**
	 * Retourne le volume 
	 * @return volume courant
	 * @author Amine Boudraa
	 */
	
	public static int getMusicVolume() {
		return (int) (ambiance.getVolume()*100);
	}

	/**
	 * Augmente le volume
	 * @author Amine Boudraa
	 */
	public static void UPVolume() {
		ambiance.setVolume(ambiance.getVolume() + 0.01f);
	}
	
	/**
	 * Diminue le volume
	 * @author Amine Boudraa
	 */
	public static void DOWNVolume() {
		ambiance.setVolume(ambiance.getVolume() - 0.01f);
	}
	
	@Override
	/**
	 * Initialise la table des états du jeu (IHM)
	 * @author Amine Boudraa
	 */
	public void initStatesList(GameContainer container) throws SlickException {
		this.addState(new WindowMainMenu(menu));
		this.addState(new WindowGame(jeu));
		this.addState(new WindowLobby(lobby));
		this.addState(new WindowOptions(options));
		this.addState(new WindowJoinGame(joinGame));
		this.addState(new WindowCreateGame(createGame));
		this.addState(new WindowGameList(gameList));
	}
	
	public static void main(String[] args) {
		if(Game.settingsFileExists("settings.cfg"))
			Game.loadSettings();
		else
			Game.createSettingsFileParameters();
		
		if(Game.settingsFileExists("serveur.cfg"))
			Game.loadSettingsServer();
		else
			Game.createSettingsFileServer();	
		
		
		AppGameContainer appgc;
		try {
			appgc = new AppGameContainer(new Game());
			appgc.setDisplayMode(res.getX(), res.getY(), false);
			appgc.start();
		}catch(SlickException e) {
			System.out.println(e);
		}

	}
	
	/**
	 * Charge les paramètres qui sont dans le fichiers settings.cfg
	 * @author Amine Boudraa
	 */
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
	
	
	public static void loadSettingsServer() {
	
		Properties settings_serveur = new Properties();
		
		InputStream input_serveur = null;
		OutputStream output_serveur = null;
		String portudp;
		String porttcp;
		
		try {
			
		
			File f1 = new File("serveur.cfg");
			input_serveur = new FileInputStream(f1);
			settings_serveur.load(input_serveur);
			
			if ( settings_serveur.getProperty("adresseIPserveur") == null || settings_serveur.getProperty("portUDP") == null || settings_serveur.getProperty("portTCP") == null )
			{
				
				output_serveur = new FileOutputStream(f1);
			
				if(settings_serveur.getProperty("adresseIPserveur") == null)
					settings_serveur.setProperty("adresseIPserveur", "localhost");
				if(settings_serveur.getProperty("portUDP") == null)
					settings_serveur.setProperty("portUDP", "19000");
				if(settings_serveur.getProperty("portTCP") == null)
					settings_serveur.setProperty("portTCP", "18000");
				
				settings_serveur.store(output_serveur,"Parametres manquants crees");
				
			}
		}
			
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
	
		
		
		
		
	
		adresseipserveur = settings_serveur.getProperty("adresseIPserveur");
		portudp = settings_serveur.getProperty("portUDP");
		porttcp = settings_serveur.getProperty("portTCP");
		
		portUDP = Integer.parseInt(portudp);
		portTCP = Integer.parseInt(porttcp);
		
		
		System.out.println("Parametres serveur chargés");
	}
	
	/**
	 * Teste si le fichier settings.cfg existe 
	 * @return vrai si existe sinon faux
	 * @author Amine Boudraa
	 */
	public static boolean settingsFileExists(String file) {
		File f = new File(file);
		
		if(f.isFile() )
			return true;
		else return false;
	}
	
	
	
	/**
	 * Sauvegarde les paramètres qui ont été chargés à partir du fichier config
	 * @author Amine Boudraa
	 */
	public static void saveSettings() {
		if(settingsFileExists("settings.cfg")) {
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
	
	/**
	 * Initialisation des champs du fichier de configuration avant chargement
	 * @author Amine Boudraa
	 */
	public static void createSettingsFileParameters() {
		File f = new File("settings.cfg");
		Properties settings = new Properties();
		
		
		settings.setProperty("resolution", "HIGH");
		settings.setProperty("musicVolume", "0");
		
	  try {
			OutputStream out = new FileOutputStream(f);
			
			
				try {
					settings.store(out, "Parametres SpaceBattle");
					System.out.println("Fichier parametres crées");
				} catch (IOException e) {
					e.printStackTrace();
				}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void createSettingsFileServer() {
		
		
		File f2 = new File("serveur.cfg");
		Properties settings2 = new Properties();
		
		
		settings2.setProperty("adresseIPserveur", "localhost");
		settings2.setProperty("portUDP", "19000");
		settings2.setProperty("portTCP", "18000");
		
		adresseipserveur = "localhost";
		portUDP = 19000;
		portTCP = 18000;
		
		
		try {
			
			OutputStream out2 = new FileOutputStream(f2);
			
				try {
					
					settings2.store(out2, "Parametres serveur SpaceBattle");
					System.out.println("Fichier parametres serveur crée");
				} catch (IOException e) {
					e.printStackTrace();
				}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
