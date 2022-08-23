package discord.discord;

import java.sql.*;
import java.util.UUID;

public class database {

    private static Connection connection;

    public static Connection getConnection() throws SQLException {

        if(connection != null){
            return connection;
        }

        String host = "localhost";
        String port = "3307";
        String database = "idmcdc";
        String username = "root";
        String password = "";

        Connection connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false", username, password);


        connection = connection;
        System.out.println("Connected to database.");

        return connection;
    }

    public void initializeDatabase() throws SQLException {

        Statement statement = getConnection().createStatement();

        //Create the player_stats table
        String sql = "CREATE TABLE IF NOT EXISTS DiscordStatus (uuid varchar(36) primary key, id varchar(18), code int)";

        statement.execute(sql);

        statement.close();

    }

    public static DiscordStatus findDiscordStatusByCode(String  code) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM DiscordStatus WHERE code = ?");
        statement.setString(1, code);

        ResultSet resultSet = statement.executeQuery();

        DiscordStatus discordStatus;

        if(resultSet.next()){

            discordStatus = new DiscordStatus(resultSet.getString("uuid"), resultSet.getString("id"), resultSet.getInt("code"));

            statement.close();

            return discordStatus;
        }

        statement.close();

        return null;
    }

    public static DiscordStatus findDiscordStatusByUUID(String uuid) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM DiscordStatus WHERE uuid = ?");
        statement.setString(1, uuid);

        ResultSet resultSet = statement.executeQuery();

        DiscordStatus discordStatus;

        if(resultSet.next()){

            discordStatus = new DiscordStatus(resultSet.getString("uuid"), resultSet.getString("id"), resultSet.getInt("code"));

            statement.close();

            return discordStatus;
        }

        statement.close();

        return null;
    }

    public static void createPlayerStats(DiscordStatus discordStatus) throws SQLException {

        PreparedStatement statement = getConnection()
                .prepareStatement("INSERT INTO DiscordStatus(uuid, id, code) VALUES (?, ?, ?)");
        statement.setString(1, discordStatus.getPlayerUUID());
        statement.setString(2, discordStatus.getID());
        statement.setInt(3, discordStatus.getCode());

        statement.executeUpdate();

        statement.close();

    }

    public static void updatePlayerStats(DiscordStatus discordStatus) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement("UPDATE DiscordStatus SET id = ?, code = ? WHERE uuid = ?");
        statement.setString(1, discordStatus.getID());
        statement.setInt(2, discordStatus.getCode());
        statement.setString(3, discordStatus.getPlayerUUID());

        statement.executeUpdate();

        statement.close();
    }

}
