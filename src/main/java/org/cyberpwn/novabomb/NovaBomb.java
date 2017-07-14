package org.cyberpwn.novabomb;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.cyberpwn.novabomb.util.Wraith;

public class NovaBomb extends JavaPlugin implements Listener
{
	@Override
	public void onEnable()
	{
		Wraith.instance = this;
		Wraith.registerListener(this);
	}
	
	@Override
	public void onDisable()
	{
		
	}
	
	@EventHandler
	public void on(EntityExplodeEvent e)
	{
		e.blockList().clear();
		W.createNovaExplosion(e.getLocation(), 10, 100, 0.25, 0.34, 0.22);
	}
}
