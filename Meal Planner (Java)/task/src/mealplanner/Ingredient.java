package mealplanner;

public class Ingredient {

    private static int ID_COUNTER = 0;
    private String name;
    private int id;
    private int mealId;

    public Ingredient(String name) {
        this.name = name;
        ID_COUNTER = ID_COUNTER + 1;
    }

    public Ingredient(String name, int id, int mealId) {
        this.name = name;
        this.id = id;
        this.mealId = mealId;
    }

    public int getIdCounter() {
        return ID_COUNTER;
    }

    public static void setIdCounter(int newId) {
        ID_COUNTER = newId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
