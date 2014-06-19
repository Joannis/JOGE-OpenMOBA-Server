import java.util.ArrayList;
import java.util.Arrays;

import JOGE.entities.JOGEEntity;
import JOGE.entities.JOGEEntityList;
import JOGE.entities.JOGEPhysicalEntity;


public class MOBAEntityList extends JOGEEntityList
{
	public static ArrayList<JOGEEntity> getEntitiesFromString(String entityString)
	{
		String[]	split	= entityString.split("\\s+");
		String[]	data	= Arrays.copyOfRange(split, 1, split.length);
		int totalEntities = Integer.valueOf(split[0]);
		ArrayList<JOGEEntity> entityList = new ArrayList<JOGEEntity>();
		boolean dead = false;
		
		entityList.ensureCapacity(totalEntities);
		
		// ID, coordX, coordY, width, height, health, max-health, type
		for(int i = 0; i < (int) (data.length / 8); i++)
		{
			if(Integer.valueOf(data[i*8 + 5]) <= 0)
				dead = true;
			else
				dead = false;
			
			entityList.add(Integer.valueOf(data[i*8]), ((MOBAPhysicalEntity) new MOBAPhysicalEntity(Double.valueOf(data[i*8 + 1]), Double.valueOf(data[i*8 + 2]),
					Double.valueOf(data[i*8 + 3]), Double.valueOf(data[i*8 + 4]), Integer.valueOf(data[i*8 + 6]))
			{
				public void onCollideWith(JOGEPhysicalEntity entity)
				{
					
				}
				
			}.setDead(dead)).setHealth(Integer.valueOf(data[i*8 + 5])).setType(data[i*8 + 7]));
		}
		
		return entityList;
	}
	
	public String getEntityString()
	{
		String s = (entities.size() + 1) + " ";
		
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

	public static String getEntityStringFromArray(MOBAPhysicalEntity[] entitylist)
	{
		String s = entitylist.length + " ";
		
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
	
	public void removeEntityAtId(int id)
	{
		entities.remove(id);
		entities.ensureCapacity(id + 1);
	}
}