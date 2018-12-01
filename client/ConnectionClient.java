package client;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import client.Gestionnaire.GestionnairePartie;
import client.IHM.WindowGame;
import client.IHM.WindowGameList;
import client.IHM.WindowLobby;
import client.Model.Bonus;
import client.Model.Flag;
import client.Model.Joueur;
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
		Log.set(Log.LEVEL_DEBUG);
		client = new Client();
		client.getKryo().register(java.util.ArrayList.class);
		client.getKryo().register(Missile.class, new MissileSerializer());
		client.getKryo().register(ServeurJoueur.class);
		client.getKryo().register(DatagramUpdateClient.class);
		client.getKryo().register(DatagramUpdateServer.class);
		client.getKryo().register(SegmentNouveauJoueur.class);
		client.getKryo().register(Bonus.class);
		client.getKryo().register(Boolean[].class);
		client.getKryo().register(long[].class);
		client.getKryo().register(TypeBonus.class);
		client.getKryo().register(SegmentCreationPartie.class);
		client.getKryo().register(ModeJeu.class);
		client.getKryo().register(DatagramUpdateServerCapture.class);
		client.getKryo().register(Flag.class);
		client.getKryo().register(SegmentRejoindrePartie.class);
		client.getKryo().register(GameOptions.class);
		client.getKryo().register(SegmentLobby.class);
		client.getKryo().register(SegmentStartPartie.class);
		client.getKryo().register(SegmentListeParties.class);
		
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
	
	public void disconnect() {
		client.close();
		//client.discoverHost(18000, 5000);
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
		segment.optionsPartie = gestionnairePartie.getOptionsPartie();
		segment.pseudo = "hôte";
		System.out.println("Création partie : " + segment.optionsPartie);
		client.sendTCP(segment);
	}
	
	public void joinGame(String pseudo) {
		SegmentRejoindrePartie segment = new SegmentRejoindrePartie();
		segment.idPartie = gestionnairePartie.getIdPartie();
		segment.pseudo = pseudo;
		client.sendTCP(segment);
	}
	
	public void startGame() {
		SegmentStartPartie segment = new SegmentStartPartie();
		segment.idPartie = gestionnairePartie.getIdPartie();
		client.sendTCP(segment);
	}
	
	public void askForGameList() {
		client.sendTCP("liste");
	}
	
	public void leaveLobby() {
		disconnect();
	}

	/**
	 * Fonction appelée dès qu'un packet ou un datagram est reçu
	 */
	public void received(Connection c, Object o) {
		if (o instanceof DatagramUpdateServer) {
			// System.out.println("Update reçu");
			DatagramUpdateServer datagram = (DatagramUpdateServer) o;
			
			gestionnairePartie.setReception(datagram);
			
				
		} else if (o instanceof SegmentNouveauJoueur) {
			gestionnairePartie.setIdPartie(((SegmentNouveauJoueur) o).idPartie);
			gestionnairePartie.setTeamJoueur(((SegmentNouveauJoueur) o).team);
			System.out.println("Le joueur a rejoint la partie : " +  gestionnairePartie.getIdPartie() + " et appartient à la team : " + ((SegmentNouveauJoueur) o).team );
		} else if (o instanceof String) {
			if (o.equals("ko")) {
				System.out.println("Je suis touch�");
				client.close();
				WindowGame.loop = false;
			}
		} else if(o instanceof SegmentLobby) {
			WindowLobby.playersInLobby = ((SegmentLobby) o).listeJoueurLobby;
		} else if(o instanceof SegmentStartPartie) {
			WindowLobby.start = true;
		} else if(o instanceof SegmentListeParties) {
			WindowGameList.setListeParties( ((SegmentListeParties) o).listeIdParties, ((SegmentListeParties) o).listeOptionsParties);
		}
	}
}
