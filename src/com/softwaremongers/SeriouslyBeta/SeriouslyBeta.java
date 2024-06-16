package com.softwaremongers.SeriouslyBeta;

import com.softwaremongers.SeriouslyBeta.Listeners.SeriouslyBlockListener;
import com.softwaremongers.SeriouslyBeta.Listeners.SeriouslyEntityListener;
import com.softwaremongers.SeriouslyBeta.Listeners.SeriouslyPlayerListener;
import com.softwaremongers.SeriouslyBeta.Listeners.SeriouslyVehicleListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class SeriouslyBeta extends JavaPlugin {

    public static SeriouslyBeta plugin;
    private final SeriouslyBlockListener blockListener = new SeriouslyBlockListener(this);
    private final SeriouslyEntityListener entityListener = new SeriouslyEntityListener(this);
    private final SeriouslyPlayerListener playerListener = new SeriouslyPlayerListener(this);
    private final SeriouslyVehicleListener vehicleListener = new SeriouslyVehicleListener(this);

    private final Logger logger = Logger.getLogger("Minecraft");


    @Override
    public void onDisable() {
        this.logger.info("SeriouslyBeta Disabled.");
    }

    @Override
    public void onEnable() {
        this.blockListener.registerEvents();
        this.entityListener.registerEvents();
        this.playerListener.registerEvents();
        this.vehicleListener.registerEvents();
        PluginDescriptionFile pdFile = this.getDescription();
        this.logger.info(pdFile.getName() + " version: " + pdFile.getVersion() + " is enabled.");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        if (commandLabel.equalsIgnoreCase("tss") || commandLabel.equalsIgnoreCase("sb")){
            toggleVision((Player) sender);
        }
        return false;
    }

    public void reloadPlugin(){
        return;
    }

    public void toggleVision(Player player){
        player.sendMessage("ah ha!");
    }


}