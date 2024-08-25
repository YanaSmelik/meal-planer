package mealplanner.db.dao;

import mealplanner.db.DbClient;
import mealplanner.Meal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MealDao implements Dao<Meal> {

    private final DbClient dbClient;

    public MealDao() {
        dbClient = new DbClient();
    }

    @Override
    public List<Meal> findAll(){
        String query = "SELECT meal_id FROM meals ORDER BY meal_id DESC";

        ResultSet resultSet;
        List<Meal> meals = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(DbClient.DB_URL, DbClient.USER, DbClient.PASSWORD);
            Statement statement = connection.createStatement()) {
            resultSet =  statement.executeQuery(query);

            meals = extractMealsFromResultSet(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return meals;
    }

    @Override
    public Meal findById(int id) {
        return null;
    }

    @Override
    public void add(Meal meal) {
        String query = "INSERT INTO meals (category, meal, meal_id) VALUES ( '%s' , '%s', %d)";

        dbClient.runUpdate(String.format(query, meal.getCategory(), meal.getName(), meal.getId()));
    }

    @Override
    public void update(Meal meal) {

    }

    @Override
    public void deleteById(int id) {

    }

    public List<Meal> findByCategory(String category){
        String query = String.format("SELECT * FROM meals WHERE category = '%s'", category);
        List<Meal> meals =  new ArrayList<>();

        ResultSet resultSet;
        try(Connection connection = DriverManager.getConnection(DbClient.DB_URL, DbClient.USER, DbClient.PASSWORD);
            Statement statement = connection.createStatement()) {
            resultSet =  statement.executeQuery(query);

            meals = extractMealsFromResultSet(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return meals;
    }

    public void createTable() {
//        dbClient.runUpdate("DROP TABLE IF EXISTS meals cascade");

        String query = "CREATE TABLE IF NOT EXISTS meals (category varchar, meal varchar, meal_id integer primary key)";
        dbClient.runUpdate(query);
    }

    private List<Meal> extractMealsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Meal> meals = new ArrayList<>();
        while (resultSet.next()) {
            String mealCategory = resultSet.getString("category");
            String mealName = resultSet.getString("meal");
            int mealId = resultSet.getInt("meal_id");
            meals.add(new Meal(mealCategory, mealName, mealId));
        }
        return meals;
    }
}
