package mealplanner;

public enum DaysOfWeek {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

   public String getName(DaysOfWeek day) {
        String dayName = day.name();
        return dayName.charAt(0) + dayName.substring(1).toLowerCase();
   }

}
