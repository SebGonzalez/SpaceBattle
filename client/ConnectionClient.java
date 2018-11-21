package client;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import client.Gestionnaire.GestionnaireAdversaire;
import client.Gestionnaire.GestionnaireBonusClient;
import client.Gestionnaire.GestionnaireMissile;
import client.Gestionnaire.GestionnairePartie;
import client.IHM.WindowGame;
import client.Model.Bonus;
import client.Model.Flag;
import client.Model.Joueur;
import client.Model.Missile;
import client.Model.TypeBonus;
import network.DatagramUpdateClient;
import network.DatagramUpdateServer;
import network.DatagramUpdateServerCapture;
import network.MissileSerializer;
import network.PacketAddPlayer;
import network.SegmentCreationPartie;
import network.SegmentIDPartie;
import server.ServeurJoueur;

/**
 * Classe qui gère la connexion et les échanges avec le serveur
 * 
 * @author Sébastien Gonzalez
 *
 */
public class ConnectionClient extends Listener {
	
	GestionnairePartie gestionnairePartie;

	String ip = "localhost";
	final int portTCP = 18000;
	final int portUDP = 19000;
	Client client;

	public ConnectionClient(GestionnairePartie gestionnairePartie) {
		this.gestionnairePartie = gestionnairePartie;
	}

	public ConnectionClient(Joueur joueur, GestionnairePartie gestionnairePartie) {
		this.gestionnairePartie = gestionnairePartie;
	}

	public void addData(GestionnairePartie gestionnairePartie) {
		this.gestionnairePartie = gestionnairePartie;
	}

	/**
	 * Connexion avec le serveur
	 */
	public void connect() {
		client = new Client();
		client.getKryo().register(java.util.ArrayList.class);
		client.getKryo().register(Missile.class, new MissileSerializer());
		client.getKryo().register(ServeurJoueur.class);
		client.getKryo().register(PacketAddPlayer.class);
		client.getKryo().register(DatagramUpdateClient.class);
		client.getKryo().register(DatagramUpdateServer.class);
		client.getKryo().register(SegmentIDPartie.class);
		client.getKryo().register(Bonus.class);
		client.getKryo().register(Boolean[].class);
		client.getKryo().register(long[].class);
		client.getKryo().register(TypeBonus.class);
		client.getKryo().register(SegmentCreationPartie.class);
		client.getKryo().register(ModeJeu.class);
		client.getKryo().register(DatagramUpdateServerCapture.class);
		client.getKryo().register(Flag.class);
		

		client.addListener(this);

		client.start();
		try {
			client.connect(5000, ip, portTCP, portUDP);
		} catch (IOException e) {
			e.printStackTrace();
		} 

		System.out.println("Connexion etablie avec le serveur");

		// sendInformation(joueur);
	}

	public void sendInformationConnection() {
		// peut être utile bientôt
	}

	/**
	 * Création du datagram contenant les informations du joueur et envoie au
	 * serveur
	 * 
	 * @param joueur
	 */
	public void sendInformationGame(Joueur joueur) {
		if (gestionnairePartie.getIdPartie() != -1) {
			DatagramUpdateClient datagram = new DatagramUpdateClient();
			datagram.idPartie = gestionnairePartie.getIdPartie();
			datagram.x = joueur.getX();
			datagram.y = joueur.getY();
			datagram.accelerationX = joueur.getaccelerationX();
			datagram.accelerationY = joueur.getaccelerationY();
			datagram.r = joueur.getRotation();

			datagram.listeMissile = gestionnairePartie.getListeMissileClient();
			// if(datagram.listeMissile.size()> 0) System.out.println("Nb missile envoyé :
			// " + datagram.listeMissile.size() + " " +
			// gestionnaireMissile.getListeMissileClient().size());

			client.sendUDP(datagram);
		}
		else System.out.println("PArtie pas encore re�u");
	}

	public void createGame() {
		SegmentCreationPartie segment = new SegmentCreationPartie();
		segment.modeJeu = gestionnairePartie.getModeJeu();
		System.out.println("Création partie : " + segment.modeJeu);
		client.sendTCP(segment);
	}

	/**
	 * Fonction appelée dès qu'un packet ou un datagram est reçu
	 */
	public void received(Connection c, Object o) {
		if (o instanceof PacketAddPlayer) {
			PacketAddPlayer packet = (PacketAddPlayer) o;
			System.out.println("Nouveau joueur : " + packet.id);
		} else if (o instanceof DatagramUpdateServer) {
			// System.out.println("Update reçu");
			DatagramUpdateServer datagram = (DatagramUpdateServer) o;
			gestionnairePartie.setReception(datagram);
				
		} else if (o instanceof SegmentIDPartie) {
			gestionnairePartie.setIdPartie(((SegmentIDPartie) o).idPartie);
			System.out.println("Le joueur a cree la partie : " +  gestionnairePartie.getIdPartie());
		} else if (o instanceof String) {
			if (o.equals("ko")) {
				System.out.println("Je suis touch�");
				client.close();
				WindowGame.loop = false;
			}
		}
	}
}
