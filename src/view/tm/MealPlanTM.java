package view.tm;

public class MealPlanTM {
    private String mealPlanId;
    private String description;

    MealPlanTM(){}

    public MealPlanTM(String mealPlanId, String description) {
        this.mealPlanId = mealPlanId;
        this.description = description;
    }

    public String getMealPlanId() {
        return mealPlanId;
    }

    public void setMealPlanId(String mealPlanId) {
        this.mealPlanId = mealPlanId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
