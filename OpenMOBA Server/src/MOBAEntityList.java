import java.util.ArrayList;

import JOGE.entities.JOGEEntity;
import JOGE.entities.JOGEEntityList;
import JOGE.entities.JOGEPhysicalEntity;


public class MOBAEntityList extends JOGEEntityList
{
	public static ArrayList<JOGEEntity> getEntitiesFromString(String entityString)
	{
		String[]	data	= entityString.split("\\s+");
		ArrayList<JOGEEntity> entityList = new ArrayList<JOGEEntity>();
		boolean dead = false;
		
		// ID, coordX, coordY, width, height, health, max-health, type
		for(int i = 0; i < (int) (data.length / 8); i++)
		{			
			if(Integer.valueOf(data[i*8 + 5]) <= 0)
				dead = true;
			else
				dead = false;
			
			entityList.add(((MOBAPhysicalEntity) new MOBAPhysicalEntity(Double.valueOf(data[i*4 + 1]), Double.valueOf(data[i*4 + 2]),
					Double.valueOf(data[i*4 + 3]), Double.valueOf(data[i*4 + 4]), Integer.valueOf(data[i*4 + 6]))
			{
				public void onCollideWith(JOGEPhysicalEntity entity)
				{
					
				}
				
			}.setDead(dead)).setHealth(Integer.valueOf(data[i*4 + 5])).setType(data[i*4 + 7]));
		}
		
		return entityList;
	}
	
	public String getEntityString()
	{
		String s = "";
		
		JOGEEntity[]	entitylist = getEntityArray();
		
		for(int i = 0; i < entitylist.length; i++)
			if(entitylist[i] != null)
			{
				if(entitylist[i] instanceof MOBAPhysicalEntity)
				{
					s += i +  " " + entitylist[i].getPosX() + " " + entitylist[i].getPosY() + " " + ((MOBAPhysicalEntity) entitylist[i]).getHitboxWidth()
						+ " " + ((MOBAPhysicalEntity) entitylist[i]).getHitboxHeight() + " " + ((MOBAPhysicalEntity) entitylist[i]).getHealth() + " " +
							((MOBAPhysicalEntity) entitylist[i]).getMaxHealth() + " " + ((MOBAPhysicalEntity) entitylist[i]).getType() + " ";
					
				} else {
					
					if(entitylist[i].isDead())
						s += i +  " " + entitylist[i].getPosX() + " " + entitylist[i].getPosY() + " dead ";
					else
						s += i +  " " + entitylist[i].getPosX() + " " + entitylist[i].getPosY() + " alive ";
				}
			}
		
		return s;
	}
}