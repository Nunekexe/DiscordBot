package discord.discord;

import org.jetbrains.annotations.NotNull;

public class DiscordStatus {
    private String playerUUID;

    private String id;
    private int code;


    public DiscordStatus(@NotNull String playerUUID,@NotNull String id,@NotNull int code) {
        this.playerUUID = playerUUID;
        this.id = id;
        this.code = code;
    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(String playerUUID) {
        this.playerUUID = playerUUID;
    }

    public String getID() {
        return id;
    }

    public void setID(String  id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
