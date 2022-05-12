package view.tm;

public class ScheduleTM {
    private String scheduleId;
    private String trainerId;
    private String startDate;
    private String endDate;
    private int daysPerWeek;
    private String mealPlanId;

    ScheduleTM(){}

    public ScheduleTM(String scheduleId, String trainerId,
                      String startDate, String endDate, int daysPerWeek, String mealPlanId) {
        this.scheduleId = scheduleId;
        this.trainerId = trainerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.daysPerWeek = daysPerWeek;
        this.mealPlanId = mealPlanId;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getDaysPerWeek() {
        return daysPerWeek;
    }

    public void setDaysPerWeek(int daysPerWeek) {
        this.daysPerWeek = daysPerWeek;
    }

    public String getMealPlanId() {
        return mealPlanId;
    }

    public void setMealPlanId(String mealPlanId) {
        this.mealPlanId = mealPlanId;
    }
}
