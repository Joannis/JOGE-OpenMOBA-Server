import JOGE.entities.JOGEEntityList;
import JOGE.networking.lobby.JOGEtcpLobbyServer;

public class Server extends JOGEtcpLobbyServer
{
	public static JOGEEntityList				entities;
	public static JOGEEntityList				players;
	
	public Server(int port, String threadName)
	{
		super(port, threadName);
		
		entities = new JOGEEntityList();
		players = new JOGEEntityList();
	}
	
	public void onRemoveConnection(int playerID)
	{
		Server.players.getEntityFromId(Integer.valueOf(playerID)).setDead(true);
	}
}