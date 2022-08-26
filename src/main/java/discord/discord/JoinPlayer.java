package discord.discord;

import net.dv8tion.jda.api.JDA;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.sql.SQLException;

public class JoinPlayer implements Listener {

    private DiscordStatus getDiscordStatusFromDatabase(ProxiedPlayer player) throws SQLException {

        DiscordStatus discordStatus = database.findDiscordStatusByUUID(player.getUniqueId().toString());

        if (discordStatus == null) {
            discordStatus = new DiscordStatus(player.getUniqueId().toString(), null, 0);
            database.createPlayerStats(discordStatus);
        }

        return discordStatus;
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        try{
            DiscordStatus discordStatus = getDiscordStatusFromDatabase(event.getPlayer());
            database.updatePlayerStats(discordStatus);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
