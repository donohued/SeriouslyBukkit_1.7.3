package com.softwaremongers.seriouslybukkit.listeners;

import com.softwaremongers.seriouslybukkit.SeriouslyBeta;
import com.softwaremongers.seriouslybukkit.statistics.StatisticManager;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
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
        pm.registerEvent(Event.Type.BLOCK_PLACE, this, Event.Priority.Normal, this.plugin);
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event){
        this.plugin.statisticManager.reportEvent(event.getPlayer(), StatisticManager.StatType.PLAYER_BLOCKS_DESTROYED, 1);
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event){
        this.plugin.statisticManager.reportEvent(event.getPlayer(), StatisticManager.StatType.PLAYER_BLOCKS_PLACED, 1);
    }
}
