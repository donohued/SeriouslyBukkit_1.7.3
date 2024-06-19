package com.softwaremongers.seriouslybukkit.statistics;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerStatistics {

    private String playerName;
    public String getPlayerName() {
        return this.playerName;
    }
    private UUID playerUUID;
    public UUID getPlayerUUID() {
        return this.playerUUID;
    }
    private long playerFirstJoin = 0;
    public long getPlayerFirstJoin() {
        return this.playerFirstJoin;
    }
    private long playerLastOnline = 0;
    public long getPlayerLastOnline() {
        return this.playerLastOnline;
    }

    private Map<StatType, Integer> playerStats;

    public PlayerStatistics(String playerName, UUID playerUUID) {
        this.playerName = playerName;
        this.playerUUID = playerUUID;
        this.playerFirstJoin = System.currentTimeMillis();
        this.playerLastOnline = System.currentTimeMillis();
        this.playerStats = new HashMap<>();
        for (StatType type : StatType.values()) {
            this.playerStats.put(type, 0);
        }
    }

    public void addPlayTime(long playtime) {
        this.playerStats.put(StatType.PLAYER_TIME_PLAYED, this.playerStats.get(StatType.PLAYER_TIME_PLAYED) + (int) playtime);
        this.playerLastOnline = System.currentTimeMillis();
    }

    public void updateStat(PlayerStatistics.StatType t, int val) {
        this.playerStats.put(t, this.playerStats.get(t) + val);
    }

    public void initFromFile(Map<String, String> data){
        this.playerName = data.get("playerName");
        this.playerUUID = UUID.fromString(data.get("playerUUID"));
        this.playerFirstJoin = Long.parseLong(data.get("playerFirstJoin"));
        for (StatType type : StatType.values()) {
            this.playerStats.put(type, Integer.parseInt(data.get(type.name())));
        }
    }

    public String toYaml(){
        StringBuilder yaml = new StringBuilder(
                "playerName: " + this.playerName + "\n" +
                "playerUUID: " + this.playerUUID + "\n" +
                "playerFirstJoin: " + this.playerFirstJoin + "\n" +
                "playerLastOnline: " + this.playerLastOnline + "\n"
        );
        for (StatType type : StatType.values()) {
            yaml.append(type.name()).append(": ").append(this.playerStats.get(type)).append("\n");
        }
        return yaml.toString();
    }

    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        json.put("playerInfo", new JSONObject()
                .put("playerName", this.playerName)
                .put("playerUUID", this.playerUUID.toString())
                .put("playerFirstJoin", this.playerFirstJoin)
                .put("playerLastOnline", this.playerLastOnline));

        JSONObject stats = new JSONObject();
        for (StatType type : StatType.values()) {
            stats.put(type.name(), this.playerStats.get(type));
        }
        json.put("playerStats", stats);

        return json;
    }

    public enum StatType {
        PLAYER_TIME_PLAYED("Time Played"),
        PLAYER_TIMES_JOINED("Times Joined"),
        PLAYER_TIMES_KICKED("Times Kicked"),
        PLAYER_TIMES_QUIT("Times Quit"),
        PLAYER_CHAT_MESSAGES("Sent Chat Messages"),

        PLAYER_TIMES_ENTER_BED("Times Entered Bed"),
        PLAYER_TIMES_LEFT_BED("Times Left Bed"),
        PLAYER_BUCKET_FILLED("Times Filled Bucket"),
        PLAYER_BUCKET_EMPTIED("Times Emptied Bucket"),
        PLAYER_FISH_CAUGHT("Fish Caught"),
        PLAYER_LINES_CAST("Fishing Lines Cast"),
        PLAYER_EGGS_THROWN("Eggs Thrown"),
        PLAYER_PORTAL_CROSSINGS("Portal Crossings"),
        PLAYER_TIMES_TELEPORTED("Times Teleported"),

        PLAYER_BLOCKS_PLACED("Blocks Placed"),
        PLAYER_BLOCKS_DESTROYED("Blocks Destroyed"),
        PLAYER_ITEMS_DROPPED("Items Dropped"),
        PLAYER_ITEMS_PICKED_UP("Items Picked Up"),

        PLAYER_MOBS_KILLED("Monsters Killed"),
        PLAYER_ANIMALS_KILLED("Animals Killed"),
        PLAYER_PLAYERS_KILLED("Players Killed"),
        PLAYER_DEATHS("Deaths");

        /*
        Stats to add
        PLAYER_ITEMS_CRAFTED("Items Crafted"),
        PLAYER_ITEMS_SMELTED("Items Smelted"),

        PLAYER_DIAMONDS_MINED("Diamonds Mined"),
        PLAYER_REDSTONE_MINED("Redstone Mined"),
        PLAYER_LAPIS_MINED("Lapis Mined"),
        PLAYER_GOLD_MINED("Gold Mined"),
        PLAYER_IRON_MINED("Iron Mined"),
        PLAYER_COAL_MINED("Coal Mined"),

        PLAYER_WOLVES_TAMED("Wolves Tamed"),
        PLAYER_DUNGEONS_FOUND("Dungeons Found"),
        PLAYER_CAKE_EATEN("Cakes Eaten"),
        PLAYER_SNOWBALLS_THROWN("Snowballs Thrown"),
        PLAYER_ARROW_SHOTS("Arrow Shots"),

        PLAYER_BIGGEST_FALL("Biggest Fall"),
        PLAYER_PIGS_RIDDEN("Pigs Ridden"),
        PLAYER_CHICKENS_HATCHED("Chickens Hatched"),

        */

        public final String label;
        private StatType(String label) {
            this.label = label;
        }
    }
}
