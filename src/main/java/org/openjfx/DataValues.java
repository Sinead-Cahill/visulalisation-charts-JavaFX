package org.openjfx;

public class DataValues {
    private int cases;
    private String day;

    public DataValues(String day, int cases){
        this.day = day;
        this.cases = cases;
    }

    public String getDate(){ return day; }

    public int getCases(){ return cases; }

    public String getMonth(){ return day.substring(day.indexOf("-")+1); }

    public String toString(){ return day + " : " + cases; }
}
