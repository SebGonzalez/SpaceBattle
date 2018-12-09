package client.IHM;

import java.util.Hashtable;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class GestionnaireImagesIHM {
	
	private static String[] ressourcesJoinGame = {"ressources/menu/joinGame/buttonJoin.png", "ressources/menu/joinGame/buttonBack.png"};
	private static String[] ressourcesMainMenu = {"ressources/menu/mainmenu/spaceBattle.png","ressources/menu/mainmenu/buttonPlay.png", "ressources/menu/mainmenu/buttonHost.png", "ressources/menu/mainmenu/buttonQuit.png", "ressources/menu/mainmenu/buttonOptions.png"};
	private static String[] ressourcesLobby = {"ressources/menu/lobby/buttonReady.png","ressources/menu/lobby/buttonBack.png"};
	private static String[] ressourcesOptions = {"ressources/menu/options/buttonLOWRES.png", "ressources/menu/options/buttonMIDRES.png", "ressources/menu/options/buttonHIGHRES.png", "ressources/menu/options/buttonEXTRAHIGHRES.png","ressources/menu/options/buttonBack.png","ressources/menu/options/button_VOLUP.png","ressources/menu/options/button_VOLDOWN.png"};
	private static String[] ressourcesCreateGame = {"ressources/menu/createGame/buttonOui.png", "ressources/menu/createGame/buttonNon.png", "ressources/menu/createGame/buttonBack.png", "ressources/menu/createGame/buttonCreer.png", 
													"ressources/menu/createGame/buttonMode1.png", "ressources/menu/createGame/buttonMode2.png", "ressources/menu/createGame/buttonMode3.png",
													"ressources/menu/createGame/buttonMode1Selec.png", "ressources/menu/createGame/buttonMode2Selec.png", "ressources/menu/createGame/buttonMode3Selec.png", "ressources/menu/createGame/buttonOptions.png"};
	private static String[] ressourcesGameList = {"ressources/menu/gameList/buttonBack.png"};
	private static String[] ressourcesGame = {"ressources/sprites/ship_allie.png","ressources/sprites/ship_adversaire.png","ressources/sprites/ship_joueur.png", "ressources/sprites/missile.png", "ressources/sprites/missile2.png"
												,"ressources/sprites/PW/bolt_gold.png", "ressources/sprites/PW/powerupGreen_star.png", "ressources/sprites/PW/shield_gold.png", "ressources/sprites/PW/star_gold.png"
												,"ressources/sprites/flag1.png", "ressources/sprites/flag2.png", "ressources/sprites/baseFlag1.png", "ressources/sprites/baseFlag2.png"
												,"ressources/sprites/feu_rouge.png","ressources/sprites/feu_jaune.png","ressources/sprites/feu_vert.png"
												,"ressources/sprites/playerShip2_damage1.png", "ressources/sprites/playerShip2_damage2.png", "ressources/sprites/playerShip2_damage3.png"
												, "ressources/sprites/playerShip2_damage2.png","ressources/sprites/playerShip2_damage3.png"};

	
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
		imageList.put("buttonEXTRAHIGHRES", new Image(ressourcesOptions[3]));
		imageList.put("buttonBack", new Image(ressourcesOptions[4]));
		imageList.put("buttonVOLUP", new Image(ressourcesOptions[5]));
		imageList.put("buttonVOLDOWN", new Image(ressourcesOptions[6]));
	}
	
	public static void loadCreateGame() throws SlickException{
		initLoad();
		imageList.put("buttonOui",  new Image(ressourcesCreateGame[0]));
		imageList.put("buttonNon",  new Image(ressourcesCreateGame[1]));
		imageList.put("buttonBack",  new Image(ressourcesCreateGame[2]));
		imageList.put("buttonCreer",  new Image(ressourcesCreateGame[3]));
		imageList.put("buttonMode1", new Image(ressourcesCreateGame[4]));
		imageList.put("buttonMode2", new Image(ressourcesCreateGame[5]));
		imageList.put("buttonMode3", new Image(ressourcesCreateGame[6]));
		imageList.put("buttonMode1Selec", new Image(ressourcesCreateGame[7]));
		imageList.put("buttonMode2Selec", new Image(ressourcesCreateGame[8]));
		imageList.put("buttonMode3Selec", new Image(ressourcesCreateGame[9]));
		imageList.put("buttonOptions", new Image(ressourcesCreateGame[10]));
	}
	
	public static void loadGameList() throws SlickException{
		initLoad();
		imageList.put("buttonBack", new Image(ressourcesGameList[0]));
	}
	
	public static void loadGame() throws SlickException{
		initLoad();
		imageList.put("ship", new Image(ressourcesGame[0]));
		imageList.put("ship2", new Image(ressourcesGame[1]));
		imageList.put("shipJoueur", new Image(ressourcesGame[2]));
		imageList.put("missileJoueur", new Image(ressourcesGame[3]));
		imageList.put("missileEnnemies", new Image(ressourcesGame[4]));
		imageList.put("bonusVitesse", new Image(ressourcesGame[5]));
		imageList.put("bonusTeteChercheuse", new Image(ressourcesGame[6]));
		imageList.put("bonusShield", new Image(ressourcesGame[7]));
		imageList.put("bonusTripleMissile", new Image(ressourcesGame[8]));
		imageList.put("flag1", new Image(ressourcesGame[9]));
		imageList.put("flag2", new Image(ressourcesGame[10]));
		imageList.put("baseFlag1", new Image(ressourcesGame[11]));
		imageList.put("baseFlag2", new Image(ressourcesGame[12]));
		imageList.put("feuRouge", new Image(ressourcesGame[13]));
		imageList.put("feuJaune", new Image(ressourcesGame[14]));
		imageList.put("feuVert", new Image(ressourcesGame[15]));
		imageList.put("damage1", new Image(ressourcesGame[16]));
		imageList.put("damage2", new Image(ressourcesGame[17]));
		imageList.put("damage3", new Image(ressourcesGame[18]));
		imageList.put("damage2Joueur", new Image(ressourcesGame[19]));
		imageList.put("damage3Joueur", new Image(ressourcesGame[20]));
	}
}
