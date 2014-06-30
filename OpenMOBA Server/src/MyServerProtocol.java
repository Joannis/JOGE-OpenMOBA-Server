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
		
		if(data.startsWith("Register"))
		{
			int socketID =  net.connections.indexOf(packet.getSocketAddress());
			
			if(socketID != -1 && Server.players[socketID] == null)
			{
				MOBAPhysicalEntity entity = ((MOBAPhysicalEntity) new MOBAPhysicalEntity(400D, 400D, /*50D*/22D, 22D, 1000) {
					
					@Override
					public void onCollideWith(JOGEPhysicalEntity entity) {
						// TODO Auto-generated method stub
						
					}
				}.setDead(false)).setType("MOBAentityPlayer").setID(socketID);
				
				entity.playerAddress = net.connections.get(socketID);
				Server.players[socketID] = entity;
				
				try
				{
					net.sendStringToHost("id " + socketID, packet.getSocketAddress());
					
				} catch (SocketException e)
				{
					e.printStackTrace();
				}
				
				try {
					net.sendStringToHost("stats " + entity.getPosX() + " " + entity.getPosY() + " " + entity.getHealth() + " " + entity.getMaxHealth(), packet.getSocketAddress());
				} catch (SocketException e) {
					e.printStackTrace();
				}
				
				System.out.println("New Entity with ID " + socketID);
				
			} else {
				
				try {
					net.sendStringToHost("id " + socketID, packet.getSocketAddress());
				} catch (SocketException e) {
					e.printStackTrace();
				}
			}
			
		} else if(data.startsWith("list"))
		{
			try
			{
				net.sendStringToHost("list " + Server.entities.getEntityString(), packet.getSocketAddress());
				
			} catch (SocketException e)
			{
				e.printStackTrace();
			}
			
		}  else if(data.startsWith("players"))
		{
			try
			{
				net.sendStringToHost("players " + MOBAEntityList.getEntityStringFromArray(Server.players), packet.getSocketAddress());
				
			} catch (SocketException e)
			{
				e.printStackTrace();
			}
			
		} else if(data.startsWith("Update"))
		{
			String		position	= data.substring(7,  data.length());
			String[]	positions	= position.split("\\s+");
			
			if(Integer.valueOf(positions[0]) >= 0 && Integer.valueOf(positions[0]) < Server.players.length)
			{
				MOBAPhysicalEntity entity = (MOBAPhysicalEntity) Server.players[Integer.valueOf(positions[0])];
				
				if(entity != null)
				{
					if(!entity.freezeMovement)
					{
						entity.setPosX(Double.valueOf(positions[1]));
						entity.setPosY(Double.valueOf(positions[2]));
						entity.renderedDegrees = Double.valueOf(positions[3]);
						
						if(positions[4].contains("casting") && !entity.getState().contains("casting"))
						{
							// shoot
							MOBAPhysicalEntity projectile = ((MOBAPhysicalEntity) new MOBAPhysicalEntity(Double.valueOf(positions[1]), Double.valueOf(positions[2]), /*50D*/16D, 16D, 1000) {
								
								@Override
								public void onCollideWith(JOGEPhysicalEntity entity)
								{
									if(entity.equals(getHost()))
										return;
									
									if(entity instanceof MOBAPhysicalEntity)
									{
										((MOBAPhysicalEntity) entity).damage(500);
										
										die();
										
									} else {
										
										System.out.println("Not a player");
									}
								}
							}.setDead(false)).setType("MOBAmagicBulletProjectile").setRenderedRotation(Double.valueOf(positions[3])).setID(Server.entities.getSize());
							
							projectile.setHost(entity);
							
							Server.entities.addEntityToList(projectile);
						}
						
						entity.setState(positions[4]);
						
						Server.players[Integer.valueOf(positions[0])] = entity;
					}
					
					// check if the update is realistic
					
					try
					{
						net.sendStringToHost("stats " + entity.getPosX() + " " + entity.getPosY() + " " + entity.getHealth() + " " + entity.getMaxHealth(), packet.getSocketAddress());
						
					} catch(SocketException e)
					{
						e.printStackTrace();
					}
				}
			}
			
		} else if(data.startsWith("Disconnect"))
		{
			int id = ServerCore.server.connections.indexOf(packet.getSocketAddress());
			
			Server.players[id] = null;
			
			System.out.println("Removed Entity ID " + id);
			
		}
	}

	@Override
	public void onReceiveTCPData(JOGEtcpNetworking connection, int clientID, String data)
	{
		System.out.println(data);
	}
}