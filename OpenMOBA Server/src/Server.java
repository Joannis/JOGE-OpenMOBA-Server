import JOGE.networking.lobby.JOGEudpLobbyServer;

public class Server extends JOGEudpLobbyServer
{
	public static MOBAEntityList				entities;
	public static MOBAEntityList				players;
	
	public Server(int port, String threadName)
	{
		super(port, threadName);
		
		entities = new MOBAEntityList();
		players = new MOBAEntityList();
	}
	
	public void onRemoveConnection(int playerID)
	{
		Server.players.getEntityFromId(Integer.valueOf(playerID)).setDead(true);
	}
}