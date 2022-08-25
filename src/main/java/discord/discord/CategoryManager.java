package discord.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;

import java.sql.SQLException;
import java.util.EnumSet;


public class CategoryManager {

    private static Guild guild = Discord.discordBot.getGuildById("818737858213969921");

    private static DiscordStatus getDiscordStatusFromDatabase(ProxiedPlayer player) throws SQLException {

        DiscordStatus discordStatus = database.findDiscordStatusByUUID(player.getUniqueId().toString());
        return discordStatus;
    }
    private static CategoryManagerStatus getCategoryStatusFromDatabase(String ServerName) throws SQLException {

        CategoryManagerStatus categoryManagerStatus = database.findCategoryManagerStatus(ServerName);

        if (categoryManagerStatus == null) {
            categoryManagerStatus = new CategoryManagerStatus(ServerName, null, null, null, null, null, null, null);
            database.createCategoryManagerStatus(categoryManagerStatus);
        }

        return categoryManagerStatus;
    }

    public static void SetPlayerVoice(ProxiedPlayer player, String role) throws SQLException {
        String server = player.getServer().getInfo().getName();
        CreateCategory(server);
        CategoryManagerStatus categoryManagerStatus = getCategoryStatusFromDatabase(server);
        DiscordStatus discordStatus = getDiscordStatusFromDatabase(player);
        String id = discordStatus.getID();

        switch (role)
        {
            case "CLASS_D":
            {
                MoveMemberVoice(guild.getMemberById(id), guild.getVoiceChannelById(categoryManagerStatus.getvKlasaD()));
                break;
            }
            case "SCIENTIST":
            {
                MoveMemberVoice(guild.getMemberById(id), guild.getVoiceChannelById(categoryManagerStatus.getvNaukowiec()));
                break;
            }
            case "SCP":
            {
                MoveMemberVoice(guild.getMemberById(id), guild.getVoiceChannelById(categoryManagerStatus.getvScp()));
                break;
            }
            case "MTF":
            case "GUARD":
            {
                MoveMemberVoice(guild.getMemberById(id), guild.getVoiceChannelById(categoryManagerStatus.getvMfo()));
                break;
            }
            case "CI":
            {
                MoveMemberVoice(guild.getMemberById(id), guild.getVoiceChannelById(categoryManagerStatus.getvCi()));
                break;
            }
        }

    }

    private static void MoveMemberVoice(Member id, VoiceChannel voice){
        guild.moveVoiceMember(id, voice).complete();
    }

    public static void CreateCategory(String name) throws SQLException {
            CategoryManagerStatus categoryManagerStatus = getCategoryStatusFromDatabase(name);
            database.updateCategoryManagerStatus(categoryManagerStatus);
            if (categoryManagerStatus.getServerName() != name){
                return;
            }
            System.out.println(guild.getName());

            Category category = guild.createCategory(name).addPermissionOverride(guild.getRoleById("818737858213969921"), null, EnumSet.of(Permission.VIEW_CHANNEL)).complete();
            VoiceChannel obserwator = guild.createVoiceChannel("Obserwator", category).complete();
            VoiceChannel mfo = guild.createVoiceChannel("Mfo", category).complete();
            VoiceChannel ci = guild.createVoiceChannel("Ci", category).complete();
            VoiceChannel nuakowiec = guild.createVoiceChannel("Nuakowiec", category).complete();
            VoiceChannel klasad = guild.createVoiceChannel("KlasaD", category).complete();
            VoiceChannel scp = guild.createVoiceChannel("Scp", category).complete();

            categoryManagerStatus.setCategory(category.getId());
            categoryManagerStatus.setvObserwator(obserwator.getId());
            categoryManagerStatus.setvMfo(mfo.getId());
            categoryManagerStatus.setvCi(ci.getId());
            categoryManagerStatus.setvNaukowiec(nuakowiec.getId());
            categoryManagerStatus.setvKlasaD(klasad.getId());
            categoryManagerStatus.setvScp(scp.getId());

            database.updateCategoryManagerStatus(categoryManagerStatus);

    }

    public static void RemoveCategory(String name) throws SQLException {
        CategoryManagerStatus categoryManagerStatus = getCategoryStatusFromDatabase(name);
        if (categoryManagerStatus == null){
            return;
        }
        guild.getVoiceChannelById(categoryManagerStatus.getvScp()).delete().queue();
        guild.getVoiceChannelById(categoryManagerStatus.getvKlasaD()).delete().queue();
        guild.getVoiceChannelById(categoryManagerStatus.getvNaukowiec()).delete().queue();
        guild.getVoiceChannelById(categoryManagerStatus.getvCi()).delete().queue();
        guild.getVoiceChannelById(categoryManagerStatus.getvMfo()).delete().queue();
        guild.getVoiceChannelById(categoryManagerStatus.getvObserwator()).delete().queue();
        guild.getCategoryById(categoryManagerStatus.getCategory()).delete().queue();
        database.deleteCategoryManagerStatus(categoryManagerStatus);
    }

}
