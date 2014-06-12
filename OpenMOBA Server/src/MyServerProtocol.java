import java.net.DatagramPacket;
import java.net.SocketException;

import JOGE.entities.JOGEEntity;
import JOGE.entities.JOGEPhysicalEntity;
import JOGE.networking.JOGENetworking;
import JOGE.networking.JOGEProtocol;
import JOGE.networking.JOGEtcpNetworking;
import JOGE.networking.JOGEudpNetworking;
import JOGE.networking.JOGEudpServer;

public class MyServerProtocol extends JOGEProtocol
{
	public MyServerProtocol(JOGENetworking networking)
	{
		super(networking);
	}
	
	public void onReceiveUDPPacket(DatagramPacket packet)
	{
		
	}

	@Override
	public void onReceiveTCPData(JOGEtcpNetworking connection, int clientID, String data)
	{
		System.out.println(data);
		
		/*
		if(data.contains("Register"))
		{
			String		position	= data.substring(9, data.length());
			String[]	positions	= position.split("\\s+");
			
			Server.players.addEntityToList(new JOGEPhysicalEntity(Double.valueOf(positions[0]), Double.valueOf(positions[1]), 20D, 20D)
			{
				public void onCollideWith(JOGEPhysicalEntity entity) {	}
			});
			
			try
			{
				networking.sendStringToHost("id " + (((JOGEudpServer) networking).connections.size() - 1), packet.getSocketAddress());
				
			} catch (SocketException e)
			{
				e.printStackTrace();
			}
			
			System.out.println("New Entity @ x=" + positions[0] + " and y=" + positions[1] + " with ID " + (((JOGEudpServer) networking).connections.size() - 2));
			
		} else if(data.contains("list"))
		{
			try
			{
				networking.sendStringToHost("list " + Server.entities.getEntityString(), packet.getSocketAddress());
				
			} catch (SocketException e)
			{
				e.printStackTrace();
			}
			
		}  else if(data.contains("players"))
		{
			try
			{
				networking.sendStringToHost("players " + Server.players.getEntityString(), packet.getSocketAddress());
				
			} catch (SocketException e)
			{
				e.printStackTrace();
			}
			
		} else if(data.contains("Update"))
		{
			String		position	= data.substring(7, data.length());
			String[]	positions	= position.split("\\s+");
			
			if(Integer.valueOf(positions[0]) >= 0 && Integer.valueOf(positions[0]) < Server.players.getSize())
			{
				JOGEEntity entity = Server.players.getEntityFromId(Integer.valueOf(positions[0]));
				
				entity.setPosX(Double.valueOf(positions[1]));
				entity.setPosY(Double.valueOf(positions[2]));
				
				Server.players.setEntityAtId(entity, Integer.valueOf(positions[0]));
			}
			
		} else if(data.contains("Kill"))
		{
			String		id	= data.substring(5, data.length());
			
			Server.players.getEntityFromId(Integer.valueOf(id)).setDead(true);
			
			System.out.println("Removed Entity ID " + id);
			
		} else {
			
			String message = "Player-" + ((JOGEudpServer) networking).getPlayerIDfromConnection(packet) + ": " + data;
			
			for(int i = 0; i < ((JOGEudpServer) networking).connections.size(); i++)
			{
				try
				{
					networking.sendStringToHost("Message " + message, ((JOGEudpServer) networking).connections.get(i));
					
				} catch (SocketException e)
				{
					e.printStackTrace();
				}
			}
		}*/
	}
}