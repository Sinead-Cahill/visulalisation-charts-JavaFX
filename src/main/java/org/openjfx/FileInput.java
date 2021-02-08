package org.openjfx;
import javafx.scene.image.Image;

import java.io.*;
import java.util.ArrayList;

public class FileInput{
    private BufferedReader reader;
    private FileInputStream stream;

    private String day, month, date, line, temp;
    private int noOfCases;

    public FileInput(){ reader = null; }

    public ArrayList<DataValues> readDataFile() throws Exception{
        try{
            stream = new FileInputStream("D://College//Year 2//CS6123 - Dissertation//owid-covid-data_ireland.json");
            reader = new BufferedReader(new InputStreamReader(stream, "UTF8"));

            ArrayList<DataValues> covidData = new ArrayList<DataValues>();

            while((line = reader.readLine()) != null){
                if(line.contains("date") || line.contains("\"new_cases\":")){
                    noOfCases = -1;
                    if (line.contains("date")) {
                        temp = line.substring(line.indexOf(":") + 3, line.indexOf(",") - 1);
                        month = getMonth(temp.substring(temp.indexOf("-") + 1, temp.lastIndexOf("-")));
                        day = temp.substring(temp.lastIndexOf("-") + 1);
                        date = day + "-" + month;
                    } else if (line.contains("\"new_cases\":")) {
                        temp = line.substring(line.indexOf(":") + 2, line.indexOf("."));
                        noOfCases = Integer.parseInt(temp);
                    }
                    if(noOfCases != -1) {
                        covidData.add(new DataValues(date, noOfCases));
                    }
                }
            }
            return covidData;
        }
        finally {
            if(reader != null){
                reader.close();
            }
        }
    }

    public Image readPopImage() throws Exception {
        try{
            stream = new FileInputStream("D://College//Year 2//CS6123 - Dissertation//Person_grey.png");
            Image image = new Image(stream);
            return image;
        }
        finally {
            if(stream != null){
                stream.close();
            }
        }
    }

    public Image readCaseImage() throws Exception {
        try{
            stream = new FileInputStream("D://College//Year 2//CS6123 - Dissertation//Person_blue.png");
            Image image = new Image(stream);
            return image;
        }
        finally {
            if(stream != null){
                stream.close();
            }
        }
    }

    public Image readHalfImage() throws Exception {
        try{
            stream = new FileInputStream("D://College//Year 2//CS6123 - Dissertation//Person_half.png");
            Image image = new Image(stream);
            return image;
        }
        finally {
            if(stream != null){
                stream.close();
            }
        }
    }

    private String getMonth(String date){
        switch(date){
            case "01":
                return "Jan";
            case "02":
                return "Feb";
            case "03":
                return "Mar";
            case "04":
                return "Apr";
            case "05":
                return "May";
            case "06":
                return "Jun";
            case "07":
                return "Jul";
            case "08":
                return "Aug";
            case "09":
                return "Sep";
            case "10":
                return "Oct";
            case "11":
                return "Nov";
            case "12":
                return "Dec";
        }
        return null;
    }
}
