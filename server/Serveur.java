package server;

import java.io.IOException;
import java.util.ArrayList;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import client.Model.Bonus;
import client.Model.Missile;
import client.Model.TypeBonus;
import network.DatagramUpdateClient;
import network.DatagramUpdateServer;
import network.MissileSerializer;
import network.PacketAddPlayer;
import network.SegmentIDPartie;

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
		server.getKryo().register(PacketAddPlayer.class);
		server.getKryo().register(DatagramUpdateClient.class);
		server.getKryo().register(DatagramUpdateServer.class);
		server.getKryo().register(SegmentIDPartie.class);
		server.getKryo().register(Bonus.class);
		server.getKryo().register(Boolean[].class);
		server.getKryo().register(long[].class);
		server.getKryo().register(TypeBonus.class);
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
			
			//System.out.println("datagramme recu par le serveur");
			DatagramUpdateClient datagram = (DatagramUpdateClient)o;
			DatagramUpdateServer datagramReponse = gestionnairePartie.updateClient(c.getID(), datagram);
	
			server.sendToTCP(c.getID(), datagramReponse);
		}
		else if(o instanceof String) {
			System.out.println("re�u : " + o);
			if(o.equals("create")) {
				System.out.println("Cr�ation partie");
				SegmentIDPartie segmentReponse = gestionnairePartie.creationPartie();
				server.sendToTCP(c.getID(), segmentReponse);
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
