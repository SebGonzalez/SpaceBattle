package server;

import java.io.IOException;
import java.util.ArrayList;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import client.Model.Missile;
import newtork.DatagramUpdateClient;
import newtork.DatagramUpdateServer;
import newtork.MissileSerializer;
import newtork.PacketAddPlayer;

/**
 * Classe principale du serveur
 * @author Sébastien Gonzalez
 *
 */
public class Serveur extends Listener {

	static Server server;
	static final int portTCP = 18000;
	static final int portUDP = 19000;
	
	public static GestionnaireJoueur gestionnaireJoueur;

	public static void main(String[] args) throws IOException {
		server = new Server();
		server.getKryo().register(java.util.ArrayList.class);
		server.getKryo().register(Missile.class, new MissileSerializer());
		server.getKryo().register(ServeurJoueur.class);
		server.getKryo().register(PacketAddPlayer.class);
		server.getKryo().register(DatagramUpdateClient.class);
		server.getKryo().register(DatagramUpdateServer.class);
		server.bind(portTCP, portUDP);
		server.start();
		server.addListener(new Serveur());
		
		gestionnaireJoueur = new GestionnaireJoueur();
		
		System.out.println("The server is ready");
	}

	/**
	 * Méthode appelé dès qu'un joueur se connecte au serveur
	 */
	@Override
	public void connected(Connection c) {
		PacketAddPlayer packet = new PacketAddPlayer();
		packet.id = c.getID();

		server.sendToAllExceptTCP(c.getID(), packet);

		gestionnaireJoueur.addJoueur(c.getID());
		
		System.out.println("Connection received.");
	}

	/**
	 * Méthode appelé dès qu'un paquet ou qu'un datagram est reçu
	 */
	public void received(Connection c, Object o) {
		if(o instanceof DatagramUpdateClient) {
			//System.out.println("Update client reçu");
			DatagramUpdateClient datagram = (DatagramUpdateClient)o;

			DatagramUpdateServer datagramReponse = gestionnaireJoueur.updateJoueur(c.getID(), datagram);
			
			server.sendToTCP(c.getID(), datagramReponse);
			
		}
	}

	/**
	 * Méthode appelé dès qu'un joueur se déconnecte du serveur
	 */
	@Override
	public void disconnected(Connection c) {
		gestionnaireJoueur.removeJoueur(c.getID());

		System.out.println("Connection dropped.");
	}
}
