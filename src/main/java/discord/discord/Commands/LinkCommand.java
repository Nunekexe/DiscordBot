package discord.discord.Commands;

import discord.discord.DiscordStatus;
import discord.discord.database;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class LinkCommand extends Command {

    public LinkCommand(){
        super("link");
    }

    private DiscordStatus getDiscordStatusFromDatabase(ProxiedPlayer player) throws SQLException {

        DiscordStatus discordStatus = database.findDiscordStatusByUUID(player.getUniqueId().toString());

        if (discordStatus == null) {
            discordStatus = new DiscordStatus(player.getUniqueId().toString(), null, 0);
            database.createPlayerStats(discordStatus);
        }

        return discordStatus;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer){
            ProxiedPlayer player = (ProxiedPlayer) sender;
            int code = ThreadLocalRandom.current().nextInt(1000, 9000);

            try {
                DiscordStatus discordStatus = getDiscordStatusFromDatabase(player);

                if (discordStatus.getID() != null) {
                    player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"Masz już connect")));
                    return;}

                discordStatus.setCode(code);
                database.updatePlayerStats(discordStatus);
                player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"Twój code to:" + code)));
            } catch (SQLException e1) { e1.printStackTrace(); }
        }
    }
}
