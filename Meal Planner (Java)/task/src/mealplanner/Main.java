package mealplanner;

import mealplanner.db.dao.IngredientDao;
import mealplanner.db.dao.MealDao;
import mealplanner.db.dao.PlanDao;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static MealDao mealDao = new MealDao();
    private static IngredientDao ingredientDao = new IngredientDao();
    private static PlanDao planDao = new PlanDao();

    public static void main(String[] args) {
        mealDao.createTable();
        ingredientDao.createTable();
        planDao.createTable();

        setIdCounters();

        Scanner scanner = new Scanner(System.in);
        MenuManager menuManager = new MenuManager();
        boolean isProgramRunning = true;

        while (isProgramRunning) {
            System.out.println("What would you like to do (add, show, plan, exit)?");
            String command = scanner.nextLine();
            if (verifyCommand(command)) {
                switch (command) {
                    case "add" -> menuManager.add();
                    case "show" -> menuManager.show();
                    case "plan" -> menuManager.plan();
                    case "exit" -> {
                        System.out.println("Bye!");
                        isProgramRunning = false;
                    }
                }
            }
        }
    }

    private static void setIdCounters() {
        List<Meal> meals = mealDao.findAll();
        int lastMealId = 0;
        if (!meals.isEmpty()) {
            lastMealId = meals.get(0).getId();
        }
        Meal.setIdCounter(lastMealId);

        List<Ingredient> ingredients = ingredientDao.findAll();
        int lastIngredientId = 0;
        if (!ingredients.isEmpty()) {
            lastIngredientId = ingredients.get(0).getId();
        }
        Ingredient.setIdCounter(lastIngredientId);
    }

    public static boolean verifyCommand(String command) {
        return command.matches("add|show|exit|plan");
    }
}

