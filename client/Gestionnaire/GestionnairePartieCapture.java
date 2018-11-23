package client.Gestionnaire;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;

import client.Model.Flag;
import network.DatagramUpdateServer;
import network.DatagramUpdateServerCapture;

public class GestionnairePartieCapture extends GestionnairePartie {

	Flag flagTeam;
	Flag flagEnemy;
	
	Flag flagTeamReception;
	Flag flagEnemyReception;
	
	public GestionnairePartieCapture() {
		super();
	}
	
	public void renderAll(Graphics g) {
		super.renderAll(g);
		if(flagTeam != null) {
			flagTeam.render(g, joueur.getTeam());
			flagEnemy.render(g, joueur.getTeam());
		}
	}
	
	public void setReception(DatagramUpdateServer datagram) {
		
		super.setReception(datagram);
		flagTeamReception = ((DatagramUpdateServerCapture)datagram).flag1;
		flagEnemyReception = ((DatagramUpdateServerCapture)datagram).flag2;
	}
	
	public void update(GameContainer container, int delta, TiledMap map) {
		super.update(container, delta, map);
		if(flagEnemyReception != null) {
			flagTeam = flagTeamReception;
			flagEnemy = flagEnemyReception;
			flagTeamReception = null;
			flagEnemyReception = null;
		}
	}
}
