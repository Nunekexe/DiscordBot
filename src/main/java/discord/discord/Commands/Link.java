package discord.discord.Commands;

import discord.discord.CategoryManager;
import discord.discord.DiscordStatus;
import discord.discord.database;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.internal.handle.GuildSetupController;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Member;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Link extends ListenerAdapter {



    public static List<String> getGuilds(ReadyEvent event) {
        List<String> list = new ArrayList<>();
        for (Guild guild : event.getJDA().getGuilds())
            list.add(guild.getName());
        return list;
    }

    private DiscordStatus getDiscordStatusFromDatabase(String code) throws SQLException {

        DiscordStatus discordStatus = database.findDiscordStatusByCode(code);


        return discordStatus;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("link")){
            OptionMapping kodOption = event.getOption("kod");
            String kod = String.valueOf(kodOption.getAsInt());

            try {
                DiscordStatus discordStatus = getDiscordStatusFromDatabase(kod);

                if (discordStatus == null) {
                    event.reply("Podałeś zły kod, użyj /link w minecraft!").setEphemeral(true).queue();
                    CategoryManager.RemoveCategory("BREACH01");
                    return;
                }

                if (discordStatus.getID() != null){
                    event.reply("Masz już połączone konto!").setEphemeral(true).queue();
                    return;
                }

                ProxiedPlayer player = ProxyServer.getInstance().getPlayer(UUID.fromString(discordStatus.getPlayerUUID()));
                if (!player.isConnected()){
                    return;
                }

                player.sendMessage(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', "&eZostałeś połączony z kontem discord!")));

                discordStatus.setID(event.getUser().getId());
                database.updatePlayerStats(discordStatus);
                event.reply("Twoje konto minecraft zostało połączone z kontem discord!").setEphemeral(true).queue();
                return;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();

        OptionData option1 = new OptionData(OptionType.INTEGER, "kod", "Podaj kod z minecraft /link", true);
        commandData.add(Commands.slash("link", "Test").addOptions(option1));

        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}

