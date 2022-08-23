package discord.discord.Commands;

import discord.discord.DiscordStatus;
import discord.discord.database;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Link extends ListenerAdapter {



    private DiscordStatus getDiscordStatusFromDatabase(String code) throws SQLException {

        DiscordStatus discordStatus = database.findDiscordStatusByCode(code);


        return discordStatus;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("link")){
            String kod = null;
            OptionMapping kodOption = event.getOption("kod");
            if (kodOption != null){
                kod = kodOption.getAsString();
            }

            try {
                DiscordStatus discordStatus = getDiscordStatusFromDatabase(kod);
                discordStatus.setID(event.getUser().getId());
                database.updatePlayerStats(discordStatus);
                event.reply(discordStatus.getPlayerUUID() + event.getUser().getId() + kod).queue();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();

        OptionData option1 = new OptionData(OptionType.STRING, "kod", "Podaj kod z minecraft /link", true);
        commandData.add(Commands.slash("link", "Test").addOptions(option1));

        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}

