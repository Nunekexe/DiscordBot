package discord.discord.Commands;

import discord.discord.CategoryManager;
import discord.discord.DiscordStatus;
import discord.discord.database;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.Role;
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

import java.lang.reflect.Member;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Link extends ListenerAdapter {



    private DiscordStatus getDiscordStatusFromDatabase(String code) throws SQLException {

        DiscordStatus discordStatus = database.findDiscordStatusByCode(code);


        return discordStatus;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("link")){
            Role role = event.getGuild().getRoleById("861257424219406346");
            OptionMapping kodOption = event.getOption("kod");
            String kod = String.valueOf(kodOption.getAsInt());
            if (!event.getGuild().getMember(event.getMember()).getRoles().contains(role)){

                try {
                    DiscordStatus discordStatus = getDiscordStatusFromDatabase(kod);

                    if (discordStatus == null || discordStatus.getCode() == 0) {
                        event.reply("Podałeś zły kod, użyj /link w minecraft!").setEphemeral(true).queue();

                        return;
                    }

                    ProxiedPlayer player = ProxyServer.getInstance().getPlayer(UUID.fromString(discordStatus.getPlayerUUID()));
                    if (!player.isConnected()){
                        return;
                    }

                    player.sendMessage(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', "&eZostałeś połączony z kontem discord!")));

                    discordStatus.setID(event.getUser().getId());
                    discordStatus.setCode(discordStatus.getCode() - discordStatus.getCode());
                    database.updatePlayerStats(discordStatus);
                    event.getGuild().addRoleToMember(event.getMember(),role).queue();
                    event.reply("Twoje konto minecraft zostało połączone z kontem discord!").setEphemeral(true).queue();
                    return;

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                event.reply("Masz już połączone konto!").setEphemeral(true).queue();
                return;
            }
        }
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();

        OptionData option1 = new OptionData(OptionType.INTEGER, "kod", "Podaj kod z minecraft /link", true);
        commandData.add(Commands.slash("link", "Test").addOptions(option1));

        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}

