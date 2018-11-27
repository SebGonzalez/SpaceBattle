package server;

import java.io.IOException;
import java.util.Map.Entry;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import client.GameOptions;
import client.ModeJeu;
import client.Model.Bonus;
import client.Model.Flag;
import client.Model.Missile;
import client.Model.TypeBonus;
import network.DatagramUpdateClient;
import network.DatagramUpdateServer;
import network.DatagramUpdateServerCapture;
import network.MissileSerializer;
import network.SegmentCreationPartie;
import network.SegmentListeParties;
import network.SegmentLobby;
import network.SegmentNouveauJoueur;
import network.SegmentRejoindrePartie;
import network.SegmentStartPartie;

/**
 * Classe principale du serveur
 * @author Sébastien Gonzalez
 *
 */
public class Serveur extends Listener {

	static Server server;
	static final int portTCP = 18000;
	static final int portUDP = 19000;
	
	public static GestionnairePartie gestionnairePartie;
 
	public static void main(String[] args) throws IOException {
		server = new Server();
		server.getKryo().register(java.util.ArrayList.class);
		server.getKryo().register(Missile.class, new MissileSerializer());
		server.getKryo().register(ServeurJoueur.class);
		server.getKryo().register(DatagramUpdateClient.class);
		server.getKryo().register(DatagramUpdateServer.class);
		server.getKryo().register(SegmentNouveauJoueur.class);
		server.getKryo().register(Bonus.class);
		server.getKryo().register(Boolean[].class);
		server.getKryo().register(long[].class);
		server.getKryo().register(TypeBonus.class);
		server.getKryo().register(SegmentCreationPartie.class);
		server.getKryo().register(ModeJeu.class);
		server.getKryo().register(DatagramUpdateServerCapture.class);
		server.getKryo().register(Flag.class);
		server.getKryo().register(SegmentRejoindrePartie.class);
		server.getKryo().register(GameOptions.class);
		server.getKryo().register(SegmentLobby.class);
		server.getKryo().register(SegmentStartPartie.class);
		server.getKryo().register(SegmentListeParties.class);


		server.bind(portTCP, portUDP);
		server.start();
		server.addListener(new Serveur());
		
		gestionnairePartie = new GestionnairePartie();
		
		System.out.println("The server is ready");
	}

	/**
	 * Méthode appelé dès qu'un joueur se connecte au serveur
	 */
	@Override
	public void connected(Connection c) {
		/*PacketAddPlayer packet = new PacketAddPlayer();
		packet.id = c.getID();

		server.sendToAllExceptTCP(c.getID(), packet);

		gestionnaireJoueur.addJoueur(c.getID());*/
		
		System.out.println("Connection received.");
	}

	/**
	 * Méthode appelé dès qu'un paquet ou qu'un datagram est reçu
	 */
	public void received(Connection c, Object o) {
		if(o instanceof DatagramUpdateClient) {
			DatagramUpdateClient datagram = (DatagramUpdateClient)o;
			DatagramUpdateServer datagramReponse = gestionnairePartie.updateClient(c.getID(), datagram);
	
			server.sendToUDP(c.getID(), datagramReponse);
		}
		else if(o instanceof SegmentCreationPartie) {
				SegmentNouveauJoueur segmentReponse = gestionnairePartie.creationPartie((SegmentCreationPartie)o, c.getID());
				server.sendToTCP(c.getID(), segmentReponse);
		}
		else if(o instanceof SegmentRejoindrePartie) {
			SegmentNouveauJoueur segmentReponse = gestionnairePartie.rejoindrePartie((SegmentRejoindrePartie)o, c.getID());
			server.sendToTCP(c.getID(), segmentReponse);
			
			Partie partie = gestionnairePartie.getPartie( ((SegmentRejoindrePartie)o).idPartie);
			if( !partie.getStart()) {
				
				SegmentLobby segment = partie.getSegmentLobby();
				for(Entry<Integer,ServeurJoueur> e : partie.gestionnaireJoueur.listePlayers.entrySet()) {
					server.sendToTCP(e.getKey(), segment);
				}
			}
		}
		else if(o instanceof SegmentStartPartie) {
			Partie partie = gestionnairePartie.getPartie( ((SegmentStartPartie)o).idPartie);
			partie.startPartie();
			for(Entry<Integer,ServeurJoueur> e : partie.gestionnaireJoueur.listePlayers.entrySet()) {
				server.sendToTCP(e.getKey(), o);
			}
		} else if(o instanceof String) {
			if(o.equals("liste")) {
				SegmentListeParties response = gestionnairePartie.sendListePartie();
				server.sendToTCP(c.getID(), response);
			}
		}
	}

	/**
	 * Méthode appelé dès qu'un joueur se déconnecte du serveur
	 */
	@Override
	public void disconnected(Connection c) {
		gestionnairePartie.removeJoueur(c.getID());

		System.out.println("Connection dropped.");
	}
}
