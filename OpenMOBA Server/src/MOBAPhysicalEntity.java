import java.awt.Color;
import java.awt.Graphics;

import javax.jws.Oneway;

import JOGE.entities.JOGEEntity;
import JOGE.entities.JOGEPhysicalEntity;


public class MOBAPhysicalEntity extends JOGEPhysicalEntity
{
	protected int health    = 0;
	protected int maxHealth = 0;
	protected double spawnX, spawnY;
	protected String type = "PhysicalEntity";
	
	public MOBAPhysicalEntity(double x, double y, double hitboxWidth, double hitboxHeight, int maxHealth)
	{
		super(x, y, hitboxWidth, hitboxHeight, true);
		
		spawnX = x;
		spawnY = y;
		health = maxHealth;
		this.maxHealth = maxHealth;
	}
	
	public void damage(int damage)
	{
		health -= damage;
		
		if(health <= 0)
			die();
	}
	
	protected void die()
	{
		setDead(true);
		setPosX(spawnX);
		setPosY(spawnY);
		//health = maxHealth;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public int getMaxHealth()
	{
		return maxHealth;
	}
	
	public MOBAPhysicalEntity setHealth(int health)
	{
		this.health = health;
		return this;
	}

	@Override
	public void onCollideWith(JOGEPhysicalEntity entity)
	{
		
	}
	
	public String getType()
	{
		return type;
	}
	
	public MOBAPhysicalEntity setType(String type)
	{
		this.type = type;
		return this;
	}
	
	public static void renderEntity(JOGEEntity entity, Graphics g)
	{
		
	}
}
