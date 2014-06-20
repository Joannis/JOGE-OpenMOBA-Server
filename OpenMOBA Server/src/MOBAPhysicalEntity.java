import java.awt.Color;
import java.awt.Graphics;
import java.net.SocketAddress;

import javax.jws.Oneway;

import JOGE.entities.JOGEEntity;
import JOGE.entities.JOGEPhysicalEntity;


public class MOBAPhysicalEntity extends JOGEPhysicalEntity
{
	protected int health    = 0;
	protected int maxHealth = 0;
	protected double spawnX, spawnY;
	protected String type = "PhysicalEntity";
	public SocketAddress playerAddress = null;
	protected boolean freezeMovement = false;
	
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
	}
	
	public void onTick()
	{
		super.onTick();
		
		if(getType() == "MOBAentityPlayer")
			health--;
		
		if(health <= 0)
			freezeMovement = true;
		else
			freezeMovement = false;
		
		if(health < 0)
			health = 0;
		
		if(health > maxHealth)
			health = maxHealth;
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
	
	public MOBAPhysicalEntity setMaxHealth(int maxHealth)
	{
		this.maxHealth = maxHealth;
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
