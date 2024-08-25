package mealplanner.db;

import java.sql.*;

public class DbClient {
    public static final String DB_URL = "jdbc:postgresql:meals_db";
    public static final String USER = "postgres";
    public static final String PASSWORD = "1111";

    public DbClient() {}

    public void runUpdate(String query) {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
