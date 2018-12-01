package network;

import java.util.ArrayList;

import client.Model.Missile;

/**
 * Datagram permettant au client d'envoyer ses informations au serveur
 * @author Sébastien Gonzalez
 *
 */
public class DatagramUpdateClient {
	public String name;
	public int idPartie;
	public float x;
	public float y;
	public float accelerationX;
	public float accelerationY;
	
	public float r;
	public ArrayList<Missile> listeMissile = new ArrayList<>();
}
