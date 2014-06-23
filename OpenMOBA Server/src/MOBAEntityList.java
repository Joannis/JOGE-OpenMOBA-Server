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
		for(int i = 0; i < (int) (data.length / 9); i++)
		{
			if(Integer.valueOf(data[i*9 + 5]) <= 0)
				dead = true;
			else
				dead = false;
			
			entityList.add(Integer.valueOf(data[i*9]), ((MOBAPhysicalEntity) new MOBAPhysicalEntity(Double.valueOf(data[i*9 + 1]), Double.valueOf(data[i*9 + 2]),
					Double.valueOf(data[i*9 + 3]), Double.valueOf(data[i*9 + 4]), Integer.valueOf(data[i*9 + 6]))
			{
				public void onCollideWith(JOGEPhysicalEntity entity)
				{
					
				}
				
			}.setDead(dead)).setHealth(Integer.valueOf(data[i*9 + 5])).setType(data[i*9 + 7]).setRotation(Double.valueOf(data[i*9 + 8])));
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
							((MOBAPhysicalEntity) entitylist[i]).getMaxHealth() + " " + ((MOBAPhysicalEntity) entitylist[i]).getType() + " " +
								((MOBAPhysicalEntity) entitylist[i]).rotation + " ";
					
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