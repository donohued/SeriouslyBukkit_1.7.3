package com.softwaremongers.SeriouslyBeta.Listeners;

import com.softwaremongers.SeriouslyBeta.SeriouslyBeta;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.entity.CraftPig;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashSet;
import java.util.Set;

public class SeriouslyPlayerListener extends PlayerListener {
    private SeriouslyBeta plugin;
    private PluginManager pm;
    private Set<Entity> persistentEntities = new HashSet<>();

    public SeriouslyPlayerListener(SeriouslyBeta instance){
        this.plugin = instance;
    }

    public void registerEvents(){
        this.pm = this.plugin.getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_INTERACT_ENTITY, this, Event.Priority.Normal, this.plugin);

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this.plugin, new Runnable() {
            @Override
            public void run() {
                for (Entity entity : persistentEntities) {
                    entity.teleport(entity.getLocation());
                    //plugin.getServer().broadcastMessage("Teleported " + entity + " to " + entity.getLocation());
                }
            }
        }, 20L, 20L);
    }

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        //how can i make the clicked entity prevent despawning using bukkit api?
        Player p = event.getPlayer();
        Entity e = event.getRightClicked();
        Location l = e.getLocation();

        if(e instanceof CraftPig){
            CraftPig pig = (CraftPig) e;
            ItemStack itemInHand = p.getItemInHand();
            if (itemInHand != null && itemInHand.getType() == Material.SADDLE && !pig.hasSaddle()) {
                p.sendMessage("You saddled the pig!");
                this.persistentEntities.add(e);
            }
            if(((CraftPig) e).hasSaddle()){
                p.sendMessage("thats a saddled pig alright");
            }
        }
        //e.setVelocity(new Vector(0,1,0));
        //((LivingEntity) e).setPassenger(p);
        //p.sendMessage("Clicked an Entity");
    }
}
