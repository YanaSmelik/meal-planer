package mealplanner.db.dao;

import mealplanner.db.DbClient;
import mealplanner.Ingredient;
import mealplanner.Meal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDao implements Dao<Ingredient> {
    private final DbClient dbClient;

    public IngredientDao() {
        dbClient = new DbClient();
    }

    @Override
    public List<Ingredient> findAll() {
        String query = "SELECT ingredient_id FROM ingredients ORDER BY ingredient_id DESC";
        List<Ingredient> ingredients = new ArrayList<>();

        ResultSet resultSet;
        try(Connection connection = DriverManager.getConnection(DbClient.DB_URL, DbClient.USER, DbClient.PASSWORD);
            Statement statement = connection.createStatement()) {
            resultSet =  statement.executeQuery(query);

            ingredients = extractIngredientsFromResultSet(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return ingredients;
    }

    @Override
    public Ingredient findById(int id) {
        return null;
    }

    @Override
    public void add(Ingredient ingredient) {
        String query = "INSERT INTO ingredients (ingredient, ingredient_id, meal_id) VALUES ('%s', %d, %d)";

        dbClient.runUpdate(String.format(query, ingredient.getName(), ingredient.getId(), ingredient.getMealId()));
    }

    @Override
    public void update(Ingredient ingredient) {

    }

    @Override
    public void deleteById(int id) {

    }

    public List<Ingredient> findByMeal(Meal meal) {
        String query = String.format("SELECT * FROM ingredients WHERE meal_id = %d", meal.getId());
        List<Ingredient> ingredients = new ArrayList<>();

        ResultSet resultSet;
        try(Connection connection = DriverManager.getConnection(DbClient.DB_URL, DbClient.USER, DbClient.PASSWORD);
            Statement statement = connection.createStatement()) {
            resultSet =  statement.executeQuery(query);

            ingredients = extractIngredientsFromResultSet(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ingredients;
    }

    public void createTable() {
//        dbClient.runUpdate("DROP TABLE IF EXISTS ingredients cascade");

        String query = "CREATE TABLE IF NOT EXISTS ingredients (" +
                "ingredient varchar, " +
                "ingredient_id integer primary key," +
                "meal_id integer," +
                "FOREIGN KEY(meal_id) REFERENCES meals(meal_id))";
        dbClient.runUpdate(query);
    }

    private List<Ingredient> extractIngredientsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Ingredient> ingredients = new ArrayList<>();
        while (resultSet.next()) {
            String ingredientName = resultSet.getString("ingredient");
            int ingredientId = resultSet.getInt("ingredient_id");
            int mealId = resultSet.getInt("meal_id");
            ingredients.add(new Ingredient(ingredientName, ingredientId, mealId));
        }
        return ingredients;
    }
}
