package client.IHM;

import java.util.Hashtable;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class GestionnaireImagesIHM {
	
	private static String[] ressourcesJoinGame = {"ressources/menu/joinGame/buttonJoin.png", "ressources/menu/joinGame/buttonBack.png"};
	private static String[] ressourcesMainMenu = {"ressources/menu/mainmenu/spaceBattle.png","ressources/menu/mainmenu/buttonPlay.png", "ressources/menu/mainmenu/buttonHost.png", "ressources/menu/mainmenu/buttonQuit.png", "ressources/menu/mainmenu/buttonOptions.png"};
	private static String[] ressourcesLobby = {"ressources/menu/lobby/buttonReady.png","ressources/menu/lobby/buttonBack.png"};
	private static String[] ressourcesOptions = {"ressources/menu/options/button_LOWRES.png", "ressources/menu/options/button_MIDRES.png", "ressources/menu/options/button_HIGHRES.png", "ressources/menu/options/button_HIGHRES.png","ressources/menu/options/buttonBack.png","ressources/menu/options/button_VOLUP.png","ressources/menu/options/button_VOLDOWN.png"};
	
	private static Hashtable<String, Image> imageList = new Hashtable<String, Image>();
	
	public static Image getRessource(String name) {
		return imageList.get(name);
	}
	
	private static void initLoad() throws SlickException {
		if(!imageList.contains("background"))
			imageList.put("background", new Image("ressources/menu/space_background.jpg"));
	}
	
	public static void loadJoinGame() throws SlickException {
		initLoad();
		imageList.put("buttonJoin", new Image(ressourcesJoinGame[0]));
		imageList.put("buttonBack", new Image(ressourcesJoinGame[1]));			
	}
	
	public static void loadMainMenu() throws SlickException{
		initLoad();
		imageList.put("title", new Image(ressourcesMainMenu[0]));
		imageList.put("buttonPlay", new Image(ressourcesMainMenu[1]));
		imageList.put("buttonHost", new Image(ressourcesMainMenu[2]));
		imageList.put("buttonQuit", new Image(ressourcesMainMenu[3]));
		imageList.put("buttonOptions", new Image(ressourcesMainMenu[4]));
	}
	
	public static void loadLobby() throws SlickException{
		initLoad();
		imageList.put("buttonReady", new Image(ressourcesLobby[0]));	
		imageList.put("buttonBack", new Image(ressourcesLobby[1]));
	}
	
	public static void loadOptions() throws SlickException{
		initLoad();
		imageList.put("buttonLOWRES", new Image(ressourcesOptions[0]));
		imageList.put("buttonMIDRES", new Image(ressourcesOptions[1]));
		imageList.put("buttonHIGHRES", new Image(ressourcesOptions[2]));
		imageList.put("buttonVERYHIGHRES", new Image(ressourcesOptions[3]));
		imageList.put("buttonBack", new Image(ressourcesOptions[4]));
		imageList.put("buttonVOLUP", new Image(ressourcesOptions[5]));
		imageList.put("buttonVOLDOWN", new Image(ressourcesOptions[6]));
	}
}
