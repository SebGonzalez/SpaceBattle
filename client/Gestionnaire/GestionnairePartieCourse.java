package client.Gestionnaire;

import client.GameOptions;
import client.IHM.WindowGame;

public class GestionnairePartieCourse extends GestionnairePartie {

	public GestionnairePartieCourse(GameOptions options) {
		super(options);
		options.setTir(false);
		joueur.setX(846);
		joueur.setY(1344);
		map = WindowGame.map2;
		
	}

}
