package mealplanner;

import mealplanner.db.dao.IngredientDao;
import mealplanner.db.dao.MealDao;
import mealplanner.db.dao.PlanDao;

import java.util.*;

public class MenuManager {
    private final Scanner scanner = new Scanner(System.in);
    private List<Meal> meals;
    private InputVerifier inputVerifier;
    private MealDao mealDao;
    private PlanDao planDao;
    private IngredientDao ingredientDao;

    public MenuManager() {
        this.meals = new ArrayList<>();
        this.inputVerifier = new InputVerifier();
        mealDao = new MealDao();
        ingredientDao = new IngredientDao();
        planDao = new PlanDao();
    }

    public void add() {
        Meal meal = new Meal();
        inputMealCategory(meal);
        inputMealName(meal);
        inputMealIngredients(meal);
        meal.setId(meal.getIdCounter());

        mealDao.add(meal);

        addIngredients(meal);
        meals.add(meal);
        System.out.println("The meal has been added!");
    }

    public void show() {
        System.out.println("Which category do you want to print (breakfast, lunch, dinner)?");
        String category = scanner.nextLine();

        while (!inputVerifier.verifyMealCategory(category)) {
            System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
            category = scanner.nextLine();
        }

        List<Meal> meals = mealDao.findByCategory(category);

        if (meals.isEmpty()) {
            System.out.println("No meals found.");
        } else {
            System.out.println("Category: " + category);

            printMeals(meals);
        }
    }

    public void plan() {
        List<Meal> breakfasts = mealDao.findByCategory("breakfast");
        Collections.sort(breakfasts);
        List<Meal> lunches = mealDao.findByCategory("lunch");
        Collections.sort(lunches);
        List<Meal> dinners = mealDao.findByCategory("dinner");
        Collections.sort(dinners);

        for (DaysOfWeek day: DaysOfWeek.values()) {
            String dayName = day.getName(day);
            System.out.println(dayName);

            chooseMeal(breakfasts, day, "breakfast");
            chooseMeal(lunches, day, "lunch");
            chooseMeal(dinners, day, "dinner");

            System.out.println("Yeah! We planned the meals for " + dayName + ".\n");
        }

        printPlan();

    }

    private void chooseMeal (List<Meal> meals, DaysOfWeek day, String mealType) {
        boolean mealExists = false;
        while (!mealExists) {
            for (Meal meal : meals) {
                System.out.println(meal.getName());
            }
            System.out.println("Choose the " + mealType + " for " + day.getName(day) + " from the list above: ");
            String selectedMeal = scanner.nextLine();

            mealExists = verifyMealExists(meals, selectedMeal, day);
        }
    }

    private void printPlan() {
        for (DaysOfWeek day : DaysOfWeek.values()) {
            List<Plan> plans = planDao.findByDay(day);
            Plan breakfastPlan = getPlanByCategory(plans, "breakfast");
            Plan lunchPlan = getPlanByCategory(plans, "lunch");
            Plan dinnerPlan = getPlanByCategory(plans, "dinner");

            System.out.println(day.getName(day));
            System.out.println();
            System.out.println("Breakfast: " + breakfastPlan.getMealOption());
            System.out.println("Lunch: "  + lunchPlan.getMealOption());
            System.out.println("Dinner: " + dinnerPlan.getMealOption());
            System.out.println();
        }
    }

    private Plan getPlanByCategory(List<Plan> plans, String category) {
        return plans.stream()
                .filter(pl -> category.equals(pl.getMealCategory()))
                .findFirst()
                .orElse(null);
    }
    private boolean verifyMealExists (List<Meal> meals,
                                   String selectedMeal,
                                   DaysOfWeek dayOfWeek) {

        Meal foundMeal = meals.stream().filter(meal -> selectedMeal.equals(meal.getName())).findFirst().orElse(null);
        boolean mealExists = foundMeal != null;
        if (mealExists) {
                planDao.add(new Plan(dayOfWeek,
                        foundMeal.getName(),
                        foundMeal.getCategory(),
                        foundMeal.getId()));


        } else {
            System.out.println("This meal doesn’t exist. Choose a meal from the list above.");
        }
        return mealExists;
    }

    private void printMeals(List<Meal> meals) {
        for (Meal meal : meals) {
            System.out.println();
            System.out.println("Name: " + meal.getName());
            System.out.println("Ingredients: ");
            List<Ingredient> ingredients = ingredientDao.findByMeal(meal);
            for (Ingredient ingredient : ingredients) {
                System.out.println(ingredient.getName());
            }
        }
    }

    private void addIngredients(Meal meal) {
        List<Ingredient> ingredients = meal.getIngredients();

        for (Ingredient ingredient : ingredients) {
            ingredient.setMealId(meal.getId());
            ingredientDao.add(ingredient);
        }
    }

    private List<Ingredient> parseIngredients(String mealIngredients) {
        String[] ingredientNames = Arrays.stream(mealIngredients.split(","))
                .map(String::trim)
                .toArray(String[]::new);
        List<Ingredient> ingredients = new ArrayList<>();
        for (String name : ingredientNames) {
            Ingredient ingredient = new Ingredient(name);
            ingredient.setId(ingredient.getIdCounter());
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    private void inputMealCategory(Meal meal) {
        System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
        boolean isSuccess = false;

        while (!isSuccess) {
            String category = scanner.nextLine();
            if (inputVerifier.verifyMealCategory(category)) {
                meal.setCategory(category);
                isSuccess = true;
            } else {
                System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
            }
        }
    }

    private void inputMealName(Meal meal) {
        System.out.println("Input the meal's name:");
        boolean isSuccess = false;
        while (!isSuccess) {
            String name = scanner.nextLine();
            if (inputVerifier.verifyOnlyLetters(name)) {
                meal.setName(name);
                isSuccess = true;
            } else {
                System.out.println("Wrong format. Use letters only!");
            }
        }
    }

    private void inputMealIngredients(Meal meal) {
        System.out.println("Input the ingredients:");
        boolean isSuccess = false;
        while (!isSuccess) {
            String mealIngredients = scanner.nextLine();
            List<Ingredient> ingredients = parseIngredients(mealIngredients);
            if (inputVerifier.verifyIngredients(ingredients)) {
                meal.setIngredients(ingredients);
                isSuccess = true;
            } else {
                System.out.println("Wrong format. Use letters only!");
            }
        }
    }
}
