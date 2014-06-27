import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
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
	public double rotation = 0D;
	public double renderedDegrees = 0D;
	private String state = "idle";
	
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
		if(state.contains("blocking"))
			damage /= 2;
		
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
		
		if(health <= 0)
			freezeMovement = true;
		else
			freezeMovement = false;
		
		if(health < 0)
			health = 0;
		
		if(health == 0)
		{
			setPosX(spawnX);
			setPosY(spawnY);
			health = getMaxHealth();
		}
		
		if(health > maxHealth)
			health = maxHealth;
		
		if(getState().contains("attacking") && getType().contains("MOBAentityPlayer"))
		{
			double rot = getRenderedRotation();
			double range = 32D;
			double weaponWidth = 40D;
			Rectangle2D rect = null;
			
			if(rot == 0 || rot == 360)
			{
				rect = new Rectangle((int) (getPosX() + getHitboxWidth() / 2 - (int) weaponWidth), (int) (getPosY() - getHitboxHeight() / 2 - range), (int) weaponWidth, (int) range);
				
			} else if(rot == 45)
			{
				rect = new Rectangle((int) (getPosX() + getHitboxWidth() / 2), (int) (getPosY() - getHitboxHeight() / 2 - range), (int) range, (int) range);
				
			} else if(rot == 90)
			{
				rect = new Rectangle((int) (getPosX() + getHitboxWidth() / 2), (int) (getPosY() + getHitboxHeight() / 2 - weaponWidth), (int) range, (int) weaponWidth);
				
			} else if(rot == 135)
			{
				rect = new Rectangle((int) (getPosX() + getHitboxWidth() / 2), (int) (getPosY() + getHitboxHeight() / 2), (int) range, (int) range);
				
			} else if(rot == 180)
			{
				rect = new Rectangle((int) (getPosX() - getHitboxWidth() / 2), (int) (getPosY() + getHitboxHeight() / 2), (int) weaponWidth, (int) range);
				
			} else if(rot == 225)
			{
				rect = new Rectangle((int) (getPosX() - getHitboxWidth() / 2 - range), (int) (getPosY() + getHitboxHeight() / 2), (int) range, (int) range);
				
			} else if(rot == 270)
			{
				rect = new Rectangle((int) (getPosX() - getHitboxWidth() / 2 - range), (int) (getPosY() - getHitboxHeight() / 2), (int) range, (int) weaponWidth);
				
			} else {// if(rot == 315)
				
				rect = new Rectangle((int) (getPosX() - getHitboxWidth() / 2 - range), (int) (getPosY() - getHitboxHeight() / 2), (int) range, (int) range);
			}
			
			if(rect != null)
			{
				for(int j = 0; j < Server.players.length; j++)
					if(Server.players[j] instanceof MOBAPhysicalEntity)
						if(((MOBAPhysicalEntity)(Server.players[j])).getHitbox().intersects(rect))
							((MOBAPhysicalEntity) Server.players[j]).damage(10);
			}
		}
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
	
	public MOBAPhysicalEntity setRotation(double rotation)
	{
		this.rotation = rotation;
		return this;
	}
	
	public double getRotation()
	{
		return rotation;
	}
	
	public static void renderEntity(JOGEEntity entity, Graphics g)
	{
		
	}

	public MOBAPhysicalEntity setRenderedRotation(double renderedDegrees)
	{
		this.renderedDegrees = renderedDegrees;
		
		return this;
	}
	
	public double getRenderedRotation()
	{
		return renderedDegrees;
	}

	public MOBAPhysicalEntity setState(String state)
	{
		this.state  = state;
		
		return this;
	}

	public String getState() {
		return state;
	}
}
