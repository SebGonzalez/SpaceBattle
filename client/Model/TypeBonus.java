package client.Model;

import java.util.Random;

public enum TypeBonus {
	TripleMissile, VitesseUp, TeteChercheuse, Bouclier;

    public static TypeBonus randomValue() {
    	return TypeBonus.values()[new Random().nextInt(TypeBonus.values().length)];
    }
}
