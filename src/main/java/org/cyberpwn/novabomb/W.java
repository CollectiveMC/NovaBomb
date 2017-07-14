package org.cyberpwn.novabomb;

import java.util.Iterator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.cyberpwn.novabomb.util.Area;
import org.cyberpwn.novabomb.util.FinalDouble;
import org.cyberpwn.novabomb.util.FinalInteger;
import org.cyberpwn.novabomb.util.GList;
import org.cyberpwn.novabomb.util.GSound;
import org.cyberpwn.novabomb.util.M;
import org.cyberpwn.novabomb.util.NMSX;
import org.cyberpwn.novabomb.util.ParticleEffect;
import org.cyberpwn.novabomb.util.Task;
import org.cyberpwn.novabomb.util.TaskLater;
import org.cyberpwn.novabomb.util.VectorMath;

public class W
{
	/**
	 * Drop a nova bomb
	 * 
	 * @param l
	 *            the given location
	 * @param power
	 *            the power/radius
	 * @param fuse
	 *            the fuse in ticks
	 * @param random
	 *            the randomness of blocks pulled
	 * @param blockRatio
	 *            the ratio of entities to blocks
	 * @param vol
	 *            the volitility
	 */
	public static void createNovaExplosion(Location l, double power, int fuse, double random, double blockRatio, double vol)
	{
		GList<Entity> entities = new GList<Entity>();
		Area a = new Area(l.clone(), power);
		FinalInteger push = new FinalInteger(0);
		FinalInteger k = new FinalInteger(0);
		FinalDouble build = new FinalDouble(0);
		Iterator<Block> blocks = a.toCuboid().iterator();
		
		while(blocks.hasNext())
		{
			Block b = blocks.next();
			
			if(b.getType().isSolid() && b.getLocation().distance(l.clone()) <= power * ((1.0 - random) + (Math.random() * random)))
			{
				if(M.r(blockRatio))
				{
					Entity f = NMSX.createFallingBlock(b.getLocation());
					entities.add(f);
				}
				
				b.setType(Material.AIR);
			}
		}
		
		for(Entity i : a.getNearbyEntities())
		{
			entities.add(i);
		}
		
		new TaskLater(fuse)
		{
			@Override
			public void run()
			{
				push.set(1);
				
				for(Entity i : entities)
				{
					i.setVelocity(VectorMath.direction(l.clone(), i.getLocation()).multiply(4.6 * Math.random()));
				}
				
				ParticleEffect.EXPLOSION_HUGE.display(4f, 4, l, 32);
				new GSound(Sound.ENTITY_GENERIC_EXPLODE, 3.7f, 0.6f).play(l);
				new GSound(Sound.ENTITY_GENERIC_EXPLODE, 3.7f, 0.2f).play(l);
				new GSound(Sound.ENTITY_WITHER_DEATH, 3.7f, 0.5f + (float) build.get()).play(l);
				new GSound(Sound.ENTITY_WITHER_DEATH, 3.7f, 0.566f + (float) build.get()).play(l);
			}
		};
		
		new Task(0)
		{
			@Override
			public void run()
			{
				if(push.get() == 0)
				{
					k.add(1);
					build.set((double) k.get() / (double) fuse);
					
					for(Entity i : entities)
					{
						if(!i.getLocation().getBlock().getType().equals(Material.AIR))
						{
							i.getLocation().getBlock().setType(Material.AIR);
						}
						
						i.setVelocity(i.getVelocity().clone().add(VectorMath.direction(i.getLocation(), l.clone()).multiply(build.get() * vol)));
					}
					
					new GSound(Sound.ENTITY_BAT_TAKEOFF, 2.0f, 0.4f + (float) build.get()).play(l);
					
					for(int i = 0; i < 12; i++)
					{
						ParticleEffect.ENCHANTMENT_TABLE.display((float) (10.0 * build.get()), (int) (20.0 * build.get()), l, 32);
					}
				}
				
				else
				{
					cancel();
				}
			}
		};
	}
}
