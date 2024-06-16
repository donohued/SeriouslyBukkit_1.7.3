package com.softwaremongers.SeriouslyBeta.Listeners;

import com.softwaremongers.SeriouslyBeta.SeriouslyBeta;
import org.bukkit.craftbukkit.entity.CraftEgg;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockListener;
import org.bukkit.plugin.PluginManager;

public class SeriouslyBlockListener extends BlockListener {
    private static SeriouslyBeta plugin;
    private PluginManager pm;
    public SeriouslyBlockListener(SeriouslyBeta instance){
        this.plugin = instance;
    }

    public void registerEvents(){
        this.pm = this.plugin.getServer().getPluginManager();
        pm.registerEvent(Event.Type.BLOCK_BREAK, this, Event.Priority.Normal, this.plugin);
    }
}
