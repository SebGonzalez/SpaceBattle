package client.Gestionnaire;

import java.awt.Font;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.tiled.TiledMap;

import client.Game;
import client.GameOptions;
import client.Model.Flag;
import network.DatagramUpdateServer;
import network.DatagramUpdateServerCapture;

public class GestionnairePartieCapture extends GestionnairePartie {

	Flag flagTeam;
	Flag flagEnemy;
	
	Flag flagTeamReception;
	Flag flagEnemyReception;
	
	int scoreTeam1 = 0;
	int scoreTeam2 = 0;
	
	Font font = new Font("Verdana", Font.BOLD, 30);
	TrueTypeFont font2 = new TrueTypeFont(font, true);
	
	
	public GestionnairePartieCapture(GameOptions options) {
		super(options);
	}
	
	public void renderAll(Graphics g) {
		super.renderAll(g);
		if(flagTeam != null) {
			flagTeam.render(g, joueur.getTeam());
			flagEnemy.render(g, joueur.getTeam());
		}
		
		g.popTransform();
		org.newdawn.slick.AngelCodeFont fontBase = (AngelCodeFont) g.getFont();
		g.setFont(font2);
		g.drawString("Team Allié :              Team Adverse :", Game.res.getX()/2-300, 20);
		g.drawString("        " + (joueur.getTeam()==1 ? scoreTeam1 : scoreTeam2) + "                                 " + (joueur.getTeam()==1 ? scoreTeam2 : scoreTeam1), Game.res.getX()/2-300, 60);
		g.setFont(fontBase);
		
		
	}
	
	public void setReception(DatagramUpdateServer datagram) {
		
		super.setReception(datagram);
		flagTeamReception = ((DatagramUpdateServerCapture)datagram).flag1;
		flagEnemyReception = ((DatagramUpdateServerCapture)datagram).flag2;
		scoreTeam1 = ((DatagramUpdateServerCapture)datagram).scoreTeam1;
		scoreTeam2 = ((DatagramUpdateServerCapture)datagram).scoreTeam2;
	}
	
	public void update(GameContainer container, int delta) {
		super.update(container, delta);
		if(flagEnemyReception != null) {
			flagTeam = flagTeamReception;
			flagEnemy = flagEnemyReception;
			flagTeamReception = null;
			flagEnemyReception = null;
		}
	}
}
