package com.softwaremongers.SeriouslyBeta.Listeners;

import com.softwaremongers.SeriouslyBeta.SeriouslyBeta;
import org.bukkit.event.Event;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.Vector;

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
