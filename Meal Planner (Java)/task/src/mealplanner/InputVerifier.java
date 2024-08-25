package mealplanner;

import java.util.List;

public class InputVerifier {

    public boolean verifyIngredients(List<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            if (!verifyOnlyLetters(ingredient.getName())) {
                return false;
            }
        }
        return true;
    }

    public boolean verifyMealCategory(String mealCategory) {
        return mealCategory.matches("breakfast|lunch|dinner");
    }

    public boolean verifyOnlyLetters(String mealName) {
        return mealName.matches("[a-zA-Z ]+");
    }
}
