import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import JOGE.entities.JOGEPhysicalEntity;
import JOGE.networking.lobby.JOGEudpLobbyServer;

public class Server extends JOGEudpLobbyServer
{
	public static MOBAEntityList                entities;
	public static MOBAPhysicalEntity[]          players = new MOBAPhysicalEntity[12];
	public static Rectangle[]                   level;
	
	public Server(int port, String threadName)
	{
		super(port, threadName);
		
		entities = new MOBAEntityList();
		level = new Rectangle[100];

		int lh  = 1000; // Level Height
		int lw  = 5000; // Level Width
		int ww  = 20;   // Wall  Width
		int lws = 2;    // Level Wall Scale
		
		int bw  = 500;  // Base width
		
		// Level walls horizontal
		level[0] = new Rectangle(  0 * lws, -ww * lws,  lw * lws,  ww * lws);
		level[1] = new Rectangle(  0 * lws,  lh * lws,  lw * lws,  ww * lws);
		
		// Level walls vertical
		level[2] = new Rectangle(-ww * lws, -ww * lws,  ww * lws,  (lh + ww * 2) * lws);
		level[3] = new Rectangle(lw  * lws, -ww * lws,  ww * lws,  (lh + ww * 2) * lws);
		
		// Base walls
		level[4] = new Rectangle(bw * lws,         ww * 10 * lws,  ww * lws,  (lh - (ww * 10) * 2) * lws);
		level[5] = new Rectangle((lw - bw) * lws,  ww * 10 * lws,  ww * lws,  (lh - (ww * 10) * 2) * lws);
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
							players[newID].setID(newID);
							
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
				if(entities.getEntityFromId(i) != null)
					if(entities.getEntityFromId(i) instanceof JOGEPhysicalEntity && players[j] instanceof JOGEPhysicalEntity)
						if(((JOGEPhysicalEntity)(entities.getEntityFromId(i))).getHitbox().intersects(((JOGEPhysicalEntity)(players[j])).getHitbox()))
						{
							((JOGEPhysicalEntity)(entities.getEntityFromId(i))).onCollideWith(((JOGEPhysicalEntity)(players[j])));
							((JOGEPhysicalEntity)(players[j])).onCollideWith(((JOGEPhysicalEntity)(entities.getEntityFromId(i))));
						}
	}
}