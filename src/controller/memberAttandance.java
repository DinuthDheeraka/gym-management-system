package controller;

public class memberAttandance {

    memberAttandance(String month, int count){
        this.count = count;
        this.month = month;
    }

    private String month;
    private int count;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
