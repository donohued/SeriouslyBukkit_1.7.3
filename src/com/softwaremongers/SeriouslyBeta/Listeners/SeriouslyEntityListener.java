package com.softwaremongers.SeriouslyBeta.Listeners;

import com.softwaremongers.SeriouslyBeta.SeriouslyBeta;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.*;
import org.bukkit.plugin.PluginManager;

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
            plugin.getServer().broadcastMessage("Oof");
        }

    }

    @Override
    public void onEntityDeath(EntityDeathEvent event){
        try {
            if(event.getEntity() instanceof CraftPlayer){
                plugin.getServer().broadcastMessage(((CraftPlayer) event.getEntity()).getName() + " just fucking died.");
            }
        }catch(Error e) {
            System.out.println(e);
        }

    }

}
