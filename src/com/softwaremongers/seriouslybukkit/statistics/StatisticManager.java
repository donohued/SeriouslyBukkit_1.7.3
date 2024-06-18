package com.softwaremongers.seriouslybukkit.statistics;

import com.softwaremongers.seriouslybukkit.SeriouslyBeta;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Type;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitScheduler;

import static com.softwaremongers.seriouslybukkit.statistics.StatisticManager.StatType.PLAYER_TIMES_JOINED;
import static org.bukkit.event.Event.Type.*;

public class StatisticManager {
    private SeriouslyBeta plugin;
    private HashMap<String, PlayerStatistics> playerData;
    private HashMap<String, Long> joinTimes;

    public StatisticManager(SeriouslyBeta instance) {
        this.plugin = instance;
        this.playerData = new HashMap<>();
        this.joinTimes = new HashMap<>();
    }

    public void init() {
        BukkitScheduler scheduler = this.plugin.getServer().getScheduler();
        scheduler.scheduleAsyncRepeatingTask(this.plugin, new Runnable() {
            @Override
            public void run() {
                saveAllUsersToFile();
            }
        }, 60L, 12000L);
    }

    public void reportEvent(Player player, StatisticManager.StatType t, int val) {

        if(t.equals(PLAYER_TIMES_JOINED) && !playerData.containsKey(player.getName())){
            playerJoining(player);
        }

        playerData.get(player.getName()).updateStat(t, val);
    }

    public void saveAllUsersToFile(){
        for(PlayerStatistics ps : playerData.values()){
            ps.addPlayTime((System.currentTimeMillis() - joinTimes.get(ps.getPlayerName()))/1000);
            writePlayerData(ps);
        }
    }

    public void playerJoining(Player player) {
        if(playerData.containsKey(player.getName())){
            return;
        }
        PlayerStatistics data = getPlayerData(player.getName(), player.getUniqueId());
        this.playerData.put(data.getPlayerName(), data);
        this.joinTimes.put(player.getName(), System.currentTimeMillis());
    }

    public void playerLeaving(Player player) {
        if(!playerData.containsKey(player.getName())){
            return;
        }
        PlayerStatistics data = this.playerData.get(player.getName());
        data.addPlayTime((System.currentTimeMillis() - joinTimes.get(data.getPlayerName()))/1000);
        writePlayerData(data);
        playerData.remove(data.getPlayerName());
        joinTimes.remove(data.getPlayerName());
    }

    private PlayerStatistics getPlayerData(String playerName, UUID playerUUID){
        Path filePath = Paths.get(this.plugin.getDataFolder() +"/"+ playerName + ".yml");
        if(Files.exists(filePath)){
            //Player data found, reading file
            this.plugin.log("Player data found, reading file");
            try {
                String yaml = Files.readString(filePath);
                Map<String, String> data = SimpleYamlParser.parseYaml(yaml);
                PlayerStatistics ps = new PlayerStatistics(playerName, playerUUID);
                ps.initFromFile(data);
                return ps;
            }catch (IOException e){
                this.plugin.log(e.toString());
            }
        }else{
            this.plugin.log("No player data found, creating new file");
            //No file found, creating new file
            PlayerStatistics ps = new PlayerStatistics(playerName, playerUUID);
            writePlayerData(ps);
            return ps;
        }

        return null;
    }

    public void writePlayerData(PlayerStatistics playerStatistics) {
        Path dirPath = Paths.get(this.plugin.getDataFolder() +"/");
        Path filePath = Paths.get(this.plugin.getDataFolder() +"/"+ playerStatistics.getPlayerName() + ".yml");
        String yaml = playerStatistics.toYaml();

        try {
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }
        } catch (IOException e) {
            this.plugin.log(e.toString());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toString()))) {
            writer.write(yaml);
            this.plugin.log("Player data written to file.");
        } catch (IOException e) {
            this.plugin.log(e.toString());
        }
    }

    public void logUserData(String name){
        PlayerStatistics ps = playerData.get(name);
        this.plugin.log(ps.toYaml());
    }

    public class SimpleYamlParser {
        public static Map<String, String> parseYaml(String yaml) {
            Map<String, String> result = new HashMap<>();
            String[] lines = yaml.split("\n");
            for (String line : lines) {
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    result.put(parts[0].trim(), parts[1].trim());
                }
            }
            return result;
        }
    }

    public static enum StatType {
        PLAYER_TIME_PLAYED,
        PLAYER_TIMES_JOINED,
        PLAYER_TIMES_KICKED,
        PLAYER_TIMES_QUIT,
        PLAYER_CHAT_MESSAGES,
        PLAYER_TIMES_ENTER_BED,
        PLAYER_TIMES_LEFT_BED,
        PLAYER_BUCKET_FILLED,
        PLAYER_BUCKET_EMPTIED,
        PLAYER_FISH_CAUGHT,
        PLAYER_LINES_CAST,
        PLAYER_EGGS_THROWN,
        PLAYER_PORTAL_CROSSINGS,
        PLAYER_TIMES_TELEPORTED,
        PLAYER_BLOCKS_PLACED,
        PLAYER_BLOCKS_DESTROYED,
        PLAYER_ITEMS_DROPPED,
        PLAYER_ITEMS_PICKED_UP,
        PLAYER_MOBS_KILLED,
        PLAYER_ANIMALS_KILLED,
        PLAYER_PLAYERS_KILLED,
        PLAYER_DEATHS
    }
}




