package discord.discord.Listener;

import discord.discord.CategoryManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;


public class ChannelListener implements Listener {

    @EventHandler
    public void onPluginMessage(PluginMessageEvent event) {
        if (event.getTag().equalsIgnoreCase("BungeeCord")) {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()));
            try {
                String channel = in.readUTF();
                if (channel.equals("VoiceChat")) {
                    ProxiedPlayer player = ProxyServer.getInstance().getPlayer(UUID.fromString(in.readUTF()));
                    String role = in.readUTF();
                    CategoryManager.SetPlayerVoice(player, role);


                }
            } catch (IOException ignored) { } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

}
