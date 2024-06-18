package com.softwaremongers.seriouslybukkit.statistics;

import org.bukkit.event.Event;

import java.util.Map;
import java.util.UUID;

public class PlayerStatistics {

    private String playerName;
    public String getPlayerName() {
        return playerName;
    }
    private UUID playerUUID;
    public UUID getPlayerUUID() {
        return playerUUID;
    }
    private long playerFirstJoin = 0;

    private long playerTimePlayed = 0;

    private int playerTimesJoined = 0;
    private int playerTimesKicked = 0;
    private int playerTimesQuit = 0;
    private int playerChatMessages = 0;

    private int playerTimesEnterBed = 0;
    private int playerTimesLeftBed = 0;
    private int playerBucketFilled = 0;
    private int playerBucketEmptied = 0;
    private int playerFishCaught = 0;
    private int playerLinesCast = 0;
    private int playerEggsThrown = 0;
    private int playerPortalCrossings = 0;
    private int playerTimesTeleported = 0;

    private int playerBlocksPlaced = 0;
    private int playerBlocksDestroyed = 0;
    private int playerItemsDropped = 0;
    private int playerItemsPickedUp = 0;

    private int playerMobsKilled = 0;
    private int playerAnimalsKilled = 0;
    private int playerPlayersKilled = 0;
    private int playerDeaths = 0;

    public PlayerStatistics(String playerName, UUID playerUUID) {
        this.playerName = playerName;
        this.playerUUID = playerUUID;
        this.playerFirstJoin = System.currentTimeMillis();
    }

    public void addPlayTime(long playtime) {
        playerTimePlayed += playtime;
    }

    public void updateStat(StatisticManager.StatType t, int val) {
        switch (t) {
            case PLAYER_TIMES_JOINED:
                playerTimesJoined += val;
                break;
            case PLAYER_TIMES_KICKED:
                playerTimesKicked += val;
                break;
            case PLAYER_TIMES_QUIT:
                playerTimesQuit += val;
                break;
            case PLAYER_CHAT_MESSAGES:
                playerChatMessages += val;
                break;
            case PLAYER_TIMES_ENTER_BED:
                playerTimesEnterBed += val;
                break;
            case PLAYER_TIMES_LEFT_BED:
                playerTimesLeftBed += val;
                break;
            case PLAYER_BUCKET_FILLED:
                playerBucketFilled += val;
                break;
            case PLAYER_BUCKET_EMPTIED:
                playerBucketEmptied += val;
                break;
            case PLAYER_FISH_CAUGHT:
                playerFishCaught += val;
                break;
            case PLAYER_LINES_CAST:
                playerLinesCast += val;
                break;
            case PLAYER_EGGS_THROWN:
                playerEggsThrown += val;
                break;
            case PLAYER_PORTAL_CROSSINGS:
                playerPortalCrossings += val;
                break;
            case PLAYER_TIMES_TELEPORTED:
                playerTimesTeleported += val;
                break;
            case PLAYER_BLOCKS_PLACED:
                playerBlocksPlaced += val;
                break;
            case PLAYER_BLOCKS_DESTROYED:
                playerBlocksDestroyed += val;
                break;
            case PLAYER_ITEMS_DROPPED:
                playerItemsDropped += val;
                break;
            case PLAYER_ITEMS_PICKED_UP:
                playerItemsPickedUp += val;
                break;
            case PLAYER_MOBS_KILLED:
                playerMobsKilled += val;
                break;
            case PLAYER_ANIMALS_KILLED:
                playerAnimalsKilled += val;
                break;
            case PLAYER_PLAYERS_KILLED:
                playerPlayersKilled += val;
                break;
            case PLAYER_DEATHS:
                playerDeaths += val;
                break;
            default:
                break;
        }
    }

    public String toYaml(){
        return "playerName: " + playerName + "\n" +
                "playerUUID: " + playerUUID + "\n" +
                "playerFirstJoin: " + playerFirstJoin + "\n" +
                "playerTimePlayed: " + playerTimePlayed + "\n" +
                "playerTimesJoined: " + playerTimesJoined + "\n" +
                "playerTimesKicked: " + playerTimesKicked + "\n" +
                "playerTimesQuit: " + playerTimesQuit + "\n" +
                "playerChatMessages: " + playerChatMessages + "\n" +
                "playerTimesEnterBed: " + playerTimesEnterBed + "\n" +
                "playerTimesLeftBed: " + playerTimesLeftBed + "\n" +
                "playerBucketFilled: " + playerBucketFilled + "\n" +
                "playerBucketEmptied: " + playerBucketEmptied + "\n" +
                "playerFishCaught: " + playerFishCaught + "\n" +
                "playerLinesCast: " + playerLinesCast + "\n" +
                "playerEggsThrown: " + playerEggsThrown + "\n" +
                "playerPortalCrossings: " + playerPortalCrossings + "\n" +
                "playerTimesTeleported: " + playerTimesTeleported + "\n" +
                "playerBlocksPlaced: " + playerBlocksPlaced + "\n" +
                "playerBlocksDestroyed: " + playerBlocksDestroyed + "\n" +
                "playerItemsDropped: " + playerItemsDropped + "\n" +
                "playerItemsPickedUp: " + playerItemsPickedUp + "\n" +
                "playerMobsKilled: " + playerMobsKilled + "\n" +
                "playerAnimalsKilled: " + playerAnimalsKilled + "\n" +
                "playerPlayersKilled: " + playerPlayersKilled + "\n" +
                "playerDeaths: " + playerDeaths + "\n";
    }

    public void initFromFile(Map<String, String> data){
        this.playerName = data.get("playerName");
        this.playerUUID = UUID.fromString(data.get("playerUUID"));
        this.playerFirstJoin = Long.parseLong(data.get("playerFirstJoin"));
        this.playerTimePlayed = Long.parseLong(data.get("playerTimePlayed"));
        this.playerTimesJoined = Integer.parseInt(data.get("playerTimesJoined"));
        this.playerTimesKicked = Integer.parseInt(data.get("playerTimesKicked"));
        this.playerTimesQuit = Integer.parseInt(data.get("playerTimesQuit"));
        this.playerChatMessages = Integer.parseInt(data.get("playerChatMessages"));
        this.playerTimesEnterBed = Integer.parseInt(data.get("playerTimesEnterBed"));
        this.playerTimesLeftBed = Integer.parseInt(data.get("playerTimesLeftBed"));
        this.playerBucketFilled = Integer.parseInt(data.get("playerBucketFilled"));
        this.playerBucketEmptied = Integer.parseInt(data.get("playerBucketEmptied"));
        this.playerFishCaught = Integer.parseInt(data.get("playerFishCaught"));
        this.playerLinesCast = Integer.parseInt(data.get("playerLinesCast"));
        this.playerEggsThrown = Integer.parseInt(data.get("playerEggsThrown"));
        this.playerPortalCrossings = Integer.parseInt(data.get("playerPortalCrossings"));
        this.playerTimesTeleported = Integer.parseInt(data.get("playerTimesTeleported"));
        this.playerBlocksPlaced = Integer.parseInt(data.get("playerBlocksPlaced"));
        this.playerBlocksDestroyed = Integer.parseInt(data.get("playerBlocksDestroyed"));
        this.playerItemsDropped = Integer.parseInt(data.get("playerItemsDropped"));
        this.playerItemsPickedUp = Integer.parseInt(data.get("playerItemsPickedUp"));
        this.playerMobsKilled = Integer.parseInt(data.get("playerMobsKilled"));
        this.playerAnimalsKilled = Integer.parseInt(data.get("playerAnimalsKilled"));
        this.playerPlayersKilled = Integer.parseInt(data.get("playerPlayersKilled"));
        this.playerDeaths = Integer.parseInt(data.get("playerDeaths"));
    }
}
