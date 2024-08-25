package mealplanner;

import java.util.ArrayList;
import java.util.List;

public class Meal implements Comparable<Meal>{

    private static int ID_COUNTER = 0;
    private String category;
    private String name;
    private List<Ingredient> ingredients;
    private int id;

    public Meal() {
        this.ingredients = new ArrayList<>();
        ID_COUNTER = ID_COUNTER + 1;
    }

    public Meal(String category, String name, int id) {
        this.category = category;
        this.name = name;
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public int getIdCounter() {
        return ID_COUNTER;
    }
    public static void setIdCounter(int newId) {
        ID_COUNTER = newId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(Meal meal) {
        return this.getName().compareTo(meal.getName());
    }
}
