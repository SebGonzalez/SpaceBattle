package client;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import client.Model.Joueur;
import client.Model.Missile;
import newtork.DatagramUpdateClient;
import newtork.DatagramUpdateServer;
import newtork.MissileSerializer;
import newtork.PacketAddPlayer;
import server.ServeurJoueur;

/**
 * Classe qui gère la connexion et les échanges avec le serveur
 * @author Sébastien Gonzalez
 *
 */
public class ConnectionClient extends Listener{

	Joueur joueur;
	GestionnaireAdversaire gestionnaireAdversaire;
	GestionnaireMissile gestionnaireMissile;
	
	String ip = "localhost";
	final int portTCP = 18000;
	final int portUDP = 19000;
	Client client;
	
	public ConnectionClient(Joueur joueur, GestionnaireAdversaire gestionnaireAdversaire, GestionnaireMissile gestionnaireMissile) {
		this.joueur = joueur;
		this.gestionnaireAdversaire = gestionnaireAdversaire;
		this.gestionnaireMissile = gestionnaireMissile;
	}
	
	/**
	 * Connexion avec le serveur
	 */
	public void connect(){
		client = new Client();
		client.getKryo().register(java.util.ArrayList.class);
		client.getKryo().register(Missile.class, new MissileSerializer());
		client.getKryo().register(ServeurJoueur.class);
		client.getKryo().register(PacketAddPlayer.class);
		client.getKryo().register(DatagramUpdateClient.class);
		client.getKryo().register(DatagramUpdateServer.class);
		client.addListener(this);
		
		client.start();
		try {
			client.connect(5000, ip, portTCP, portUDP);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Connexion établie avec le serveur");
		
		//sendInformation(joueur);
	}
	
	public void sendInformationConnection() {
		//peut être utile bientôt
	}
	
	/**
	 * Création du datagram contenant les informations du joueur et envoie au serveur
	 * @param joueur
	 */
	public void sendInformation(Joueur joueur) {
		DatagramUpdateClient datagram = new DatagramUpdateClient();
		datagram.x = joueur.getX();
		datagram.y = joueur.getY();
		datagram.r = joueur.getRotation();
		
		datagram.listeMissile = gestionnaireMissile.getListeMissileClient();
		//if(datagram.listeMissile.size()> 0) System.out.println("Nb missile envoyé : " + datagram.listeMissile.size() + " " + gestionnaireMissile.getListeMissileClient().size());
		
		client.sendUDP(datagram);
	}
	
	/**
	 * Fonction appelée dès qu'un packet ou un datagram est reçu
	 */
	public void received(Connection c, Object o){
		if(o instanceof PacketAddPlayer){
			PacketAddPlayer packet = (PacketAddPlayer) o;
			System.out.println("Nouveau joueur : " + packet.id);
		}
		else if(o instanceof DatagramUpdateServer) {
			//System.out.println("Update reçu");
			DatagramUpdateServer datagram = (DatagramUpdateServer)o;
						
			gestionnaireAdversaire.setReception( datagram.listeAdversaire );
		}
		else if(o instanceof String) {
			if(o.equals("ko")) {
				System.out.println("Je suis touch�");
				client.close();
				System.exit(0);
			}
		}
	}
}

