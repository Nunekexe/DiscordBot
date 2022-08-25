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

        String sql1 = "CREATE TABLE IF NOT EXISTS DiscordStatus (uuid varchar(36) primary key, id varchar(18), code int)";
        statement.execute(sql1);
        String sql2 = "CREATE TABLE IF NOT EXISTS CategoryManagerStatus (ServerName long, category long, vObserwator long, vMfo long, vCi long, vNaukowiec long, vKlasaD long, vScp long)";
        statement.execute(sql2);

        statement.close();

    }

    public static CategoryManagerStatus findCategoryManagerStatus(String name) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM CategoryManagerStatus WHERE ServerName = ?");
        statement.setString(1, name);

        ResultSet resultSet = statement.executeQuery();

        CategoryManagerStatus categoryStatus;

        if(resultSet.next()){

            categoryStatus = new CategoryManagerStatus(resultSet.getString("ServerName"), resultSet.getString("category"), resultSet.getString("vObserwator"), resultSet.getString("vMfo"), resultSet.getString("vCi"), resultSet.getString("vNaukowiec"), resultSet.getString("vKlasaD"), resultSet.getString("vScp"));

            statement.close();

            return categoryStatus;
        }

        statement.close();

        return null;
    }

    public static void createCategoryManagerStatus(CategoryManagerStatus categoryStatus) throws SQLException {

        PreparedStatement statement = getConnection()
                .prepareStatement("INSERT INTO CategoryManagerStatus(ServerName, category, vObserwator, vMfo, vCi, vNaukowiec, vKlasaD, vScp) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        statement.setString(1, categoryStatus.getServerName());
        statement.setString(2, categoryStatus.getCategory());
        statement.setString(3, categoryStatus.getvObserwator());
        statement.setString(4, categoryStatus.getvMfo());
        statement.setString(5, categoryStatus.getvCi());
        statement.setString(6, categoryStatus.getvNaukowiec());
        statement.setString(7, categoryStatus.getvKlasaD());
        statement.setString(8, categoryStatus.getvScp());


        statement.executeUpdate();

        statement.close();

    }

    public static void updateCategoryManagerStatus(CategoryManagerStatus categoryStatus) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement("UPDATE CategoryManagerStatus SET category = ?, vObserwator = ?, vMfo = ?, vCi = ?, vNaukowiec = ?, vKlasaD = ?, vScp = ? WHERE ServerName = ?");
        statement.setString(1, categoryStatus.getCategory());
        statement.setString(2, categoryStatus.getvObserwator());
        statement.setString(3, categoryStatus.getvMfo());
        statement.setString(4, categoryStatus.getvCi());
        statement.setString(5, categoryStatus.getvNaukowiec());
        statement.setString(6, categoryStatus.getvKlasaD());
        statement.setString(7, categoryStatus.getvScp());
        statement.setString(8, categoryStatus.getServerName());

        statement.executeUpdate();

        statement.close();
    }

    public static void deleteCategoryManagerStatus(CategoryManagerStatus categoryManagerStatus) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement("DELETE FROM CategoryManagerStatus WHERE ServerName = ?");
        statement.setString(1, categoryManagerStatus.getServerName());

        statement.executeUpdate();

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
