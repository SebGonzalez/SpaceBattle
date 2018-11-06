package network;

import java.util.ArrayList;

import client.Model.Missile;

/**
 * Datagram permettant au client d'envoyer ses informations au serveur
 * @author Sébastien Gonzalez
 *
 */
public class DatagramUpdateClient {
	public int idPartie;
	public double x;
	public double y;
	public float r;
	public ArrayList<Missile> listeMissile = new ArrayList<>();
	
}
