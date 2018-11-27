package client;

public class GameOptions {

	private boolean collisions;
	private boolean reapparitions;
	private boolean tir;
	
	private boolean privateGame;
	private String password;
	private int maxPlayers;
	private ModeJeu selectedMode;
	private boolean lobby;

	public GameOptions() {
		collisions = reapparitions = tir = privateGame = false;
		password = "";
		maxPlayers = 8;
		selectedMode = ModeJeu.DEATHMATCH;
	}
	
	public boolean getLobby() {
		return lobby;
	}
	
	public void setLobby(boolean lobby) {
		this.lobby = lobby;
	}
	
	public boolean getPrivateGame() {
		return privateGame;
	}

	public void setPrivateGame(boolean privateGame) {
		this.privateGame = privateGame;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public ModeJeu getSelectedMode() {
		return selectedMode;
	}

	public void setSelectedMode(ModeJeu selectedMode) {
		this.selectedMode = selectedMode;
	}
	
	public boolean getCollisions() {
		return collisions;
	}
	public void setCollisions(boolean collisions) {
		this.collisions = collisions;
	}
	public boolean getReapparitions() {
		return reapparitions;
	}
	public void setReapparitions(boolean reapparitions) {
		this.reapparitions = reapparitions;
	}
	public boolean getTir() {
		return tir;
	}
	public void setTir(boolean tir) {
		this.tir = tir;
	}
	
	
}
