package org.openjfx;

public class DataValues {
    private int cases;
    private String date;

    public DataValues(String date, int cases){
        this.date = date;
        this.cases = cases;
    }

    public String getDate(){ return date; }

    public int getCases(){ return cases; }

    public String getDay() { return date.substring(0, date.indexOf("-")); }

    public String getMonth(){ return date.substring(date.indexOf("-")+1); }

    public String toString(){ return date + " : " + cases; }

}
