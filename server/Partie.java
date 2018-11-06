package server;

public class Partie {

	public GestionnaireJoueur gestionnaireJoueur;
	public GestionnaireBonusServeur gestionnaireBonus;
	private int id;
	
	public Partie(int id) {
		this.id = id;
		gestionnaireJoueur = new GestionnaireJoueur();
		gestionnaireBonus = new GestionnaireBonusServeur(gestionnaireJoueur);
	}

	public int getId() {
		return id;
	}
	
	
}
