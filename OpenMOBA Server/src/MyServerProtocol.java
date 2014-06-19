import java.net.DatagramPacket;
import java.net.SocketException;

import JOGE.entities.JOGEEntity;
import JOGE.entities.JOGEPhysicalEntity;
import JOGE.networking.JOGENetworking;
import JOGE.networking.JOGEProtocol;
import JOGE.networking.JOGEtcpNetworking;
import JOGE.networking.JOGEudpServer;

public class MyServerProtocol extends JOGEProtocol
{
	public MyServerProtocol(JOGENetworking networking)
	{
		super(networking);
	}
	
	public void onReceiveUDPPacket(DatagramPacket packet)
	{
		JOGEudpServer net = ((JOGEudpServer) networking);
		String data = net.readStringFromPacket(packet);
		
		System.out.println("meep");
		System.out.println(data);
		
		if(data.contains("Register"))
		{
			String		position	= data.substring(9, data.length());
			String[]	positions	= position.split("\\s+");
			
			int socketID =  net.connections.indexOf(packet.getSocketAddress());
			
			if(socketID != -1 && Server.players.getEntityFromId(socketID) == null)
			{
				MOBAPhysicalEntity entity = (MOBAPhysicalEntity) new MOBAPhysicalEntity(Double.valueOf(positions[0]), Double.valueOf(positions[1]), 64D, 64D, 1000) {
					
					@Override
					public void onCollideWith(JOGEPhysicalEntity entity) {
						// TODO Auto-generated method stub
						
					}
				}.setDead(false);
				
				Server.players.addEntityToId(entity, socketID);
				
				try
				{
					net.sendStringToHost("id " + socketID, packet.getSocketAddress());
					
				} catch (SocketException e)
				{
					e.printStackTrace();
				}
				
				System.out.println("New Entity @ x=" + positions[0] + " and y=" + positions[1] + " with ID " + socketID);
				
			} else {
				
				try {
					net.sendStringToHost("id " + socketID, packet.getSocketAddress());
				} catch (SocketException e) {
					e.printStackTrace();
				}
			}
			
		} else if(data.contains("list"))
		{
			try
			{
				net.sendStringToHost("list " + Server.entities.getEntityString(), packet.getSocketAddress());
				
			} catch (SocketException e)
			{
				e.printStackTrace();
			}
			
		}  else if(data.contains("players"))
		{
			try
			{
				net.sendStringToHost("players " + Server.players.getEntityString(), packet.getSocketAddress());
				
			} catch (SocketException e)
			{
				e.printStackTrace();
			}
			
		} else if(data.contains("Update"))
		{
			String		position	= data.substring(7,  data.length());
			String[]	positions	= position.split("\\s+");
			
			if(Integer.valueOf(positions[0]) >= 0 && Integer.valueOf(positions[0]) < Server.players.getSize())
			{
				MOBAPhysicalEntity entity = (MOBAPhysicalEntity) Server.players.getEntityFromId(Integer.valueOf(positions[0]));
				
				entity.setPosX(Double.valueOf(positions[1]));
				entity.setPosY(Double.valueOf(positions[2]));
				
				entity.setMaxHealth(1000);
				entity.setHealth(entity.getHealth() - 1);
				
				Server.players.setEntityAtId(entity, Integer.valueOf(positions[0]));
				
				// check if the update is realistic
				try
				{
					net.sendStringToHost("stats " + entity.getPosX() + " " + entity.getPosY() + " " + entity.getHealth() + " " + entity.getMaxHealth(), packet.getSocketAddress());
					
				} catch(SocketException e)
				{
					e.printStackTrace();
				}
			}
			
		} else if(data.contains("Disconnect"))
		{
			int id = ServerCore.server.connections.indexOf(packet.getSocketAddress());
			
			Server.players.getEntityFromId(Integer.valueOf(id)).setDead(true);
			Server.players.setEntityAtId(null, Integer.valueOf(id));
			
			System.out.println("Removed Entity ID " + id);
			ServerCore.server.connections.remove(id);
		}
	}

	@Override
	public void onReceiveTCPData(JOGEtcpNetworking connection, int clientID, String data)
	{
		System.out.println(data);
	}
}