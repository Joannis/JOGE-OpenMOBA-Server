import JOGE.networking.lobby.JOGEudpLobbyServer;

public class Server extends JOGEudpLobbyServer
{
	public static MOBAEntityList				entities;
	public static MOBAPhysicalEntity[]			players = new MOBAPhysicalEntity[12];
	
	public Server(int port, String threadName)
	{
		super(port, threadName);
		
		entities = new MOBAEntityList();
	}
	
	public void onRemoveConnection(int playerID)
	{
		try
		{
			for(int i = 0; i < connections.size(); i++)
				this.sendStringToHost("changeID " + i, connections.get(i));
		
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(Server.players[playerID] != null)
			Server.players[playerID].setDead(true);
	}
}