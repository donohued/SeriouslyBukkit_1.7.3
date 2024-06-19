package com.softwaremongers.seriouslybukkit.listeners;

import com.softwaremongers.seriouslybukkit.SeriouslyBeta;
import org.bukkit.event.Event;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.plugin.PluginManager;

public class SeriouslyVehicleListener extends VehicleListener {
    private static SeriouslyBeta plugin;
    private PluginManager pm;
    public SeriouslyVehicleListener(SeriouslyBeta instance){
        this.plugin = instance;
    }

    public void registerEvents(){
        this.pm = this.plugin.getServer().getPluginManager();
        pm.registerEvent(Event.Type.VEHICLE_CREATE, this, Event.Priority.Normal, this.plugin);
    }

    @Override
    public void onVehicleCreate(VehicleCreateEvent event) {
        event.getVehicle().getServer().broadcastMessage("Vehicle Created");
    }
}
