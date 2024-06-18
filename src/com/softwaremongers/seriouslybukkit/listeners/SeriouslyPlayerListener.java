package com.softwaremongers.seriouslybukkit.listeners;

import com.softwaremongers.seriouslybukkit.SeriouslyBeta;
import com.softwaremongers.seriouslybukkit.statistics.PlayerStatistics;
import com.softwaremongers.seriouslybukkit.statistics.StatisticManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.entity.CraftPig;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

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
        pm.registerEvent(Event.Type.PLAYER_LOGIN, this, Event.Priority.Normal, this.plugin);
        pm.registerEvent(Event.Type.PLAYER_JOIN, this, Event.Priority.Normal, this.plugin);
        pm.registerEvent(Event.Type.PLAYER_KICK, this, Event.Priority.Normal, this.plugin);
        pm.registerEvent(Event.Type.PLAYER_QUIT, this, Event.Priority.Normal, this.plugin);
        pm.registerEvent(Event.Type.PLAYER_CHAT, this, Event.Priority.Normal, this.plugin);
        pm.registerEvent(Event.Type.PLAYER_BED_ENTER, this, Event.Priority.Normal, this.plugin);
        pm.registerEvent(Event.Type.PLAYER_BED_LEAVE, this, Event.Priority.Normal, this.plugin);
        pm.registerEvent(Event.Type.PLAYER_BUCKET_FILL, this, Event.Priority.Normal, this.plugin);
        pm.registerEvent(Event.Type.PLAYER_BUCKET_EMPTY, this, Event.Priority.Normal, this.plugin);
        pm.registerEvent(Event.Type.PLAYER_FISH, this, Event.Priority.Normal, this.plugin);
        pm.registerEvent(Event.Type.PLAYER_EGG_THROW, this, Event.Priority.Normal, this.plugin);
        pm.registerEvent(Event.Type.PLAYER_PORTAL, this, Event.Priority.Normal, this.plugin);
        pm.registerEvent(Event.Type.PLAYER_TELEPORT, this, Event.Priority.Normal, this.plugin);
        pm.registerEvent(Event.Type.PLAYER_DROP_ITEM, this, Event.Priority.Normal, this.plugin);
        pm.registerEvent(Event.Type.PLAYER_PICKUP_ITEM, this, Event.Priority.Normal, this.plugin);
        pm.registerEvent(Event.Type.PLAYER_INTERACT_ENTITY, this, Event.Priority.Normal, this.plugin);
    }

    @Override
    public void onPlayerLogin(PlayerLoginEvent event){
        this.plugin.statisticManager.playerJoining(event.getPlayer());
        this.plugin.reportPlayerEvent(event.getPlayer(), PlayerStatistics.StatType.PLAYER_TIMES_JOINED, 1);
    }
    @Override
    public void onPlayerJoin(PlayerJoinEvent event){
        this.plugin.log(event.getPlayer().getDisplayName() + "'s physical being has been formed.");

    }
    @Override
    public void onPlayerKick(PlayerKickEvent event){
        this.plugin.reportPlayerEvent(event.getPlayer(), PlayerStatistics.StatType.PLAYER_TIMES_KICKED, 1);
        this.plugin.statisticManager.playerLeaving(event.getPlayer());
    }
    @Override
    public void onPlayerQuit(PlayerQuitEvent event){
        this.plugin.reportPlayerEvent(event.getPlayer(), PlayerStatistics.StatType.PLAYER_TIMES_QUIT, 1);
        this.plugin.statisticManager.playerLeaving(event.getPlayer());
    }
    @Override
    public void onPlayerChat(PlayerChatEvent event){
        this.plugin.reportPlayerEvent(event.getPlayer(), PlayerStatistics.StatType.PLAYER_CHAT_MESSAGES, 1);
    }
    @Override
    public void onPlayerBedEnter(PlayerBedEnterEvent event){
        this.plugin.reportPlayerEvent(event.getPlayer(), PlayerStatistics.StatType.PLAYER_TIMES_ENTER_BED, 1);
    }
    @Override
    public void onPlayerBedLeave(PlayerBedLeaveEvent event){
        this.plugin.reportPlayerEvent(event.getPlayer(), PlayerStatistics.StatType.PLAYER_TIMES_LEFT_BED, 1);
    }
    @Override
    public void onPlayerBucketFill(PlayerBucketFillEvent event){
        this.plugin.reportPlayerEvent(event.getPlayer(), PlayerStatistics.StatType.PLAYER_BUCKET_FILLED, 1);
    }
    @Override
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event){
        this.plugin.reportPlayerEvent(event.getPlayer(), PlayerStatistics.StatType.PLAYER_BUCKET_EMPTIED, 1);
    }
    @Override
    public void onPlayerFish(PlayerFishEvent event){
        if(event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)){
            this.plugin.reportPlayerEvent(event.getPlayer(), PlayerStatistics.StatType.PLAYER_FISH_CAUGHT, 1);
        }
        this.plugin.reportPlayerEvent(event.getPlayer(), PlayerStatistics.StatType.PLAYER_LINES_CAST, 1);
    }
    @Override
    public void onPlayerEggThrow(PlayerEggThrowEvent event){
        this.plugin.reportPlayerEvent(event.getPlayer(), PlayerStatistics.StatType.PLAYER_EGGS_THROWN, 1);
    }
    @Override
    public void onPlayerPortal(PlayerPortalEvent event){
        this.plugin.reportPlayerEvent(event.getPlayer(), PlayerStatistics.StatType.PLAYER_PORTAL_CROSSINGS, 1);
    }
    @Override
    public void onPlayerTeleport(PlayerTeleportEvent event){
        this.plugin.reportPlayerEvent(event.getPlayer(), PlayerStatistics.StatType.PLAYER_TIMES_TELEPORTED, 1);
    }
    @Override
    public void onPlayerDropItem(PlayerDropItemEvent event){
        this.plugin.reportPlayerEvent(event.getPlayer(), PlayerStatistics.StatType.PLAYER_ITEMS_DROPPED, 1);
    }
    @Override
    public void onPlayerPickupItem(PlayerPickupItemEvent event){
        this.plugin.reportPlayerEvent(event.getPlayer(), PlayerStatistics.StatType.PLAYER_ITEMS_PICKED_UP, 1);
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
                //this.persistentEntities.add(e);
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
