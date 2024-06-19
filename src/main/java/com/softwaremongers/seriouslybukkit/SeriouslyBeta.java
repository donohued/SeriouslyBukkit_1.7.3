package com.softwaremongers.seriouslybukkit;

import com.softwaremongers.seriouslybukkit.listeners.SeriouslyBlockListener;
import com.softwaremongers.seriouslybukkit.listeners.SeriouslyEntityListener;
import com.softwaremongers.seriouslybukkit.listeners.SeriouslyPlayerListener;
import com.softwaremongers.seriouslybukkit.listeners.SeriouslyVehicleListener;
import com.softwaremongers.seriouslybukkit.statistics.PlayerStatistics;
import com.softwaremongers.seriouslybukkit.statistics.StatisticManager;
import com.softwaremongers.seriouslybukkit.web.WebManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class SeriouslyBeta extends JavaPlugin {

    public static SeriouslyBeta plugin;
    private final SeriouslyBlockListener blockListener = new SeriouslyBlockListener(this);
    private final SeriouslyEntityListener entityListener = new SeriouslyEntityListener(this);
    private final SeriouslyPlayerListener playerListener = new SeriouslyPlayerListener(this);
    private final SeriouslyVehicleListener vehicleListener = new SeriouslyVehicleListener(this);

    public final StatisticManager statisticManager = new StatisticManager(this);
    private final WebManager webManager = new WebManager(this);

    private final Logger logger = Logger.getLogger("Minecraft");

    @Override
    public void onDisable() {
        this.logger.info("SeriouslyBeta starting shutdown.");
        this.statisticManager.saveAllUsersToFile();
        this.webManager.getWebServer().stop();
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

        this.statisticManager.init();
        this.webManager.getWebServer().start();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        if (commandLabel.equalsIgnoreCase("sb")){
            if(args.length == 0){
                return false;
            }
            if(args[0].equalsIgnoreCase("r") || args[0].equalsIgnoreCase("reload")){
                this.reloadPlugin();
                return true;
            }
            if(args[0].equalsIgnoreCase("l") || args[0].equalsIgnoreCase("log")){
                this.statisticManager.logUserData(sender.getName());
                return true;
            }

        }else if(commandLabel.equalsIgnoreCase("sb")){
            //output player data to log
            //log()
        }
        return false;
    }

    public void reportPlayerEvent(Player p, PlayerStatistics.StatType t, int e){
        statisticManager.reportEvent(p, t, e);
    }

    public void log(String message){
        this.logger.info("seriously: " + message);
    }

    public void reloadPlugin() {
        this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
        this.plugin.getServer().getPluginManager().enablePlugin(this.plugin);
    }

    /*
    TODO:
    - Make bookshelves drop themselves as an item
    - Make Saddled pigs live forever!!
    - Stat Tracker (SEE PLAYERSTATISTICS.JAVA)
    - RESTful backend, or webserver dynmap style
    */


}