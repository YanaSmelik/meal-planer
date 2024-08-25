package mealplanner.db.dao;

import mealplanner.DaysOfWeek;
import mealplanner.Plan;
import mealplanner.db.DbClient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanDao implements Dao<Plan> {
    private final DbClient dbClient;

    public PlanDao() {
        this.dbClient = new DbClient();
    }

    @Override
    public List<Plan> findAll() {
        String query = "SELECT day, option, category FROM plan ORDER BY day DESC";
        List<Plan> plans = new ArrayList<>();

        ResultSet resultSet;
        try(Connection connection = DriverManager.getConnection(DbClient.DB_URL, DbClient.USER, DbClient.PASSWORD);
            Statement statement = connection.createStatement()) {
            resultSet =  statement.executeQuery(query);

            plans = extractPlansFromResultSet(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return plans;
    }

    @Override
    public Plan findById(int id) {
        return null;
    }

    public List<Plan> findByDay(DaysOfWeek dayOfWeek) {
        String query = String.format("SELECT * FROM plan WHERE day = '%s'", dayOfWeek.name());
        List<Plan> plans = new ArrayList<>();

        ResultSet resultSet;
        try(Connection connection = DriverManager.getConnection(DbClient.DB_URL, DbClient.USER, DbClient.PASSWORD);
            Statement statement = connection.createStatement()) {
            resultSet =  statement.executeQuery(query);

            plans = extractPlansFromResultSet(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return plans;
    }

    @Override
    public void add(Plan plan) {
        String query = "INSERT INTO plan (day, option, category, meal_id) VALUES ( '%s', '%s', '%s', %d)";

        dbClient.runUpdate(String.format(query, plan.getDayOfWeek().name(),
                plan.getMealOption(), plan.getMealCategory(), plan.getMealId()));

    }

    @Override
    public void update(Plan plan) {

    }

    @Override
    public void deleteById(int id) {

    }

    public void createTable (){
//        dbClient.runUpdate("DROP TABLE IF EXISTS plan cascade");

        String query = "CREATE TABLE IF NOT EXISTS plan (" +
                "day varchar, " +
                "option varchar, " +
                "category varchar," +
                "meal_id integer," +
                "FOREIGN KEY(meal_id) REFERENCES meals(meal_id))";
        dbClient.runUpdate(query);
    }

    private List<Plan> extractPlansFromResultSet(ResultSet resultSet) throws SQLException {
        List<Plan> plans = new ArrayList<>();
        while (resultSet.next()) {
            String category = resultSet.getString("category");
            String option = resultSet.getString("option");
            String day = resultSet.getString("day");
            plans.add(new Plan(day, option, category));
        }
        return plans;
    }
}
