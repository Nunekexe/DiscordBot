package discord.discord;

import com.tjplaysnow.discord.object.Bot;
import discord.discord.Commands.Link;
import discord.discord.Commands.LinkCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.md_5.bungee.api.plugin.Plugin;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;

public final class Discord extends Plugin {

    public JDA discordBot;
    public database database;
    public Bot bot;
    public final String TOKEN = "ODYxMDA1MjY0MTczNDAwMDc2.GDUifZ.v8n0xLjdb9tOz46QAdaoBHXYrdjUoxsc9WTR_s";

    @Override
    public void onEnable() {
        super.onEnable();

        System.out.println("Plugin started...");

        this.database = new database();
        try {
            this.database.initializeDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not initialize database.");
        }

        getLogger().info("Mamy to ?");
        bot = new Bot(TOKEN);

        try {
            discordBot = JDABuilder.createDefault(TOKEN).build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        discordBot.addEventListener(new Link());
        discordBot.getPresence().setActivity(Activity.watching("Stary wstał!"));
        getProxy().getPluginManager().registerListener(this, new JoinPlayer());
        getProxy().getPluginManager().registerCommand(this, new LinkCommand());
    }

    @Override
    public void onDisable() {
    }
}