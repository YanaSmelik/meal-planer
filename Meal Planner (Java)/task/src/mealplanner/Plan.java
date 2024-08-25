package mealplanner;

public class Plan {
    private DaysOfWeek dayOfWeek;
    private String mealOption;
    private String mealCategory;
    private int mealId;
    private static int ID_COUNTER = 0;

    public Plan(DaysOfWeek dayOfWeek, String mealOption, String mealCategory, int mealId) {
        this.dayOfWeek = dayOfWeek;
        this.mealOption = mealOption;
        this.mealCategory = mealCategory;
        this.mealId = mealId;
        ID_COUNTER = ID_COUNTER + 1;
    }

    public Plan(String dayOfWeek, String mealOption, String mealCategory) {
        this.dayOfWeek = DaysOfWeek.valueOf(dayOfWeek);
        this.mealOption = mealOption;
        this.mealCategory = mealCategory;
    }

    public DaysOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public String getMealOption() {
        return mealOption;
    }

    public String getMealCategory() {
        return mealCategory;
    }

    public int getMealId() {
        return mealId;
    }

}
