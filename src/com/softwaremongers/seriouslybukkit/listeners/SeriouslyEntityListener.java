package com.softwaremongers.seriouslybukkit.listeners;

import com.softwaremongers.seriouslybukkit.SeriouslyBeta;
import com.softwaremongers.seriouslybukkit.statistics.PlayerStatistics;
import com.softwaremongers.seriouslybukkit.statistics.StatisticManager;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.*;
import org.bukkit.plugin.PluginManager;

import java.io.IOException;

public class SeriouslyEntityListener extends EntityListener {
    private static SeriouslyBeta plugin;
    private PluginManager pm;
    public SeriouslyEntityListener(SeriouslyBeta instance){
        this.plugin = instance;
    }

    public void registerEvents(){
        this.pm = this.plugin.getServer().getPluginManager();
        this.pm.registerEvent(Event.Type.ENTITY_INTERACT, this, Event.Priority.Normal, this.plugin);
        this.pm.registerEvent(Event.Type.ENTITY_DAMAGE, this, Event.Priority.Normal, this.plugin);
        this.pm.registerEvent(Event.Type.ENTITY_DEATH, this, Event.Priority.Normal, this.plugin);
    }

    @Override
    public void onEntityTame(EntityTameEvent event) {
        event.getEntity().getServer().broadcastMessage("Tamed " + event.getEntity().toString());
    }

    @Override
    public void onEntityDamage(EntityDamageEvent event) {
        if(event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)){
            //plugin.getServer().broadcastMessage("YEEEOOOOOUCH");
        }
        if(event.getEntity() instanceof CraftPlayer){

            //plugin.getServer().broadcastMessage("Oof");
        }

    }

    @Override
    public void onEntityDeath(EntityDeathEvent event){
        try {
            if(event.getEntity() instanceof CraftPlayer){
                Player p = (Player) event.getEntity();
                this.plugin.reportPlayerEvent(p, PlayerStatistics.StatType.PLAYER_DEATHS, 1);
                this.plugin.getServer().broadcastMessage(((CraftPlayer) event.getEntity()).getName() + " just fucking died.");
                if(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent){
                    if(((EntityDamageByEntityEvent) event.getEntity().getLastDamageCause()).getDamager() instanceof CraftPlayer){
                        Player pd = (Player)(((EntityDamageByEntityEvent) event.getEntity().getLastDamageCause()).getDamager());
                        this.plugin.reportPlayerEvent(pd, PlayerStatistics.StatType.PLAYER_PLAYERS_KILLED, 1);
                    }

                }
            }else if(event.getEntity() instanceof Animals){
                if(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent){
                    if(((EntityDamageByEntityEvent) event.getEntity().getLastDamageCause()).getDamager() instanceof CraftPlayer){
                        Player p = (Player)(((EntityDamageByEntityEvent) event.getEntity().getLastDamageCause()).getDamager());
                        this.plugin.reportPlayerEvent(p, PlayerStatistics.StatType.PLAYER_ANIMALS_KILLED, 1);
                    }
                }
            }else if(event.getEntity() instanceof Monster){
                if(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent){
                    if(((EntityDamageByEntityEvent) event.getEntity().getLastDamageCause()).getDamager() instanceof CraftPlayer){
                        Player p = (Player)(((EntityDamageByEntityEvent) event.getEntity().getLastDamageCause()).getDamager());
                        this.plugin.reportPlayerEvent(p, PlayerStatistics.StatType.PLAYER_MOBS_KILLED, 1);
                    }
                }
            }
        }catch(Error e) {
            System.out.println(e);
        }

    }

}
