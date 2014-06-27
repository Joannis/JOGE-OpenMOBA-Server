import JOGE.entities.JOGEPhysicalEntity;
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
	
	public void onTick()
	{
		super.onTick();
		
		entities.onTick();
		
		for(int i = 0; i < players.length; i++)
			if(players[i] != null)
				players[i].onTick();

		for(int i = 0; i < entities.getSize(); i++)
			for(int j = 0; j < players.length; j++)
				if(entities.getEntityFromId(i) instanceof JOGEPhysicalEntity && players[j] instanceof JOGEPhysicalEntity)
					if(((JOGEPhysicalEntity)(entities.getEntityFromId(i))).getHitbox().intersects(((JOGEPhysicalEntity)(players[j])).getHitbox()))
					{
						((JOGEPhysicalEntity)(entities.getEntityFromId(i))).onCollideWith(((JOGEPhysicalEntity)(players[j])));
						((JOGEPhysicalEntity)(players[j])).onCollideWith(((JOGEPhysicalEntity)(entities.getEntityFromId(i))));
					}
	}
}