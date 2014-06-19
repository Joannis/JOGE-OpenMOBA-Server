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
		System.out.println("kaas");
		System.out.println("kaas");
		System.out.println("kaas");
		System.out.println("kaas");
		
		try
		{
			for(int i = 0; i < players.length; i++)
			{
				if(players[i] != null)
				{
					int newID = connections.indexOf(players[i].playerAddress);
					
					if(i != newID)
					{
						if(newID != -1)
						{
							players[newID] = players[i];
							sendStringToHost("id " + newID, connections.get(newID));
						}
						
						players[i] = null;
					}
				}
			}
		
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(Server.players[playerID] != null)
			Server.players[playerID].setDead(true);
	}
}