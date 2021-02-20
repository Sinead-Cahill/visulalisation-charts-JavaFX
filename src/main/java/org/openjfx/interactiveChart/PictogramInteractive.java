package org.openjfx.interactiveChart;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.openjfx.DataValues;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PictogramInteractive extends Pane {

    private ArrayList<DataValues> dataValues;
    private Image popImg, caseImg, halfImg;
    private int population, valuePerIcon, pplPerRow, noOfMonths;
    private double noOfCases;
    private String dateLbl;
    private List<String> datesList;
    private StackPane hoverOverPane;

    public PictogramInteractive(ArrayList<DataValues> dataValues, Image popImg, Image caseImg, Image halfImg){
        this.dataValues = dataValues; //Data List
        this.popImg = popImg;
        this.caseImg = caseImg;
        this.halfImg = halfImg;
        population = 4900000; //Ireland Population
        valuePerIcon = 10000;
        pplPerRow = 55;
        datesList = new ArrayList<>();
        dateLbl = null;
        noOfCases = 0;
        noOfMonths = 3; //Start showing 3 months
    }

    public void displayGraph() {
        //Zoom Buttons
        Button allDataBtn = new Button("All Data");
        Button threeMonthsBtn = new Button("3 Months"); threeMonthsBtn.setDisable(true);
        Button oneMonthBtn = new Button("1 Month");

        HBox btnHBox = new HBox(5);
        btnHBox.getChildren().addAll(allDataBtn, threeMonthsBtn, oneMonthBtn);

        //Button Style
        for(int i=0; i<btnHBox.getChildren().size(); i++){
            btnHBox.getChildren().get(i).setStyle("-fx-background-color: #4682B466; -fx-background-radius: 5; -fx-font: 12 Verdana;");
        }

        //Get chart Legend
        StackPane legend = getLegend();

        //Chart Container
        VBox container = new VBox(10);
        container.getChildren().addAll(btnHBox, showPictogram(), legend);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(50, 56, 0, 56));

        hoverOverPane = hoverOverLegend();
        hoverOverPane.setVisible(false);

        allDataBtn.setOnAction(e -> {
            noOfMonths = 12;
            container.getChildren().remove(1);
            container.getChildren().add(1, showPictogram());
            allDataBtn.setDisable(true); threeMonthsBtn.setDisable(false); oneMonthBtn.setDisable(false); });
        threeMonthsBtn.setOnAction(e -> {
            noOfMonths = 3;
            container.getChildren().remove(1);
            container.getChildren().add(1, showPictogram())
            ; allDataBtn.setDisable(false); threeMonthsBtn.setDisable(true); oneMonthBtn.setDisable(false); });
        oneMonthBtn.setOnAction(e -> {
            noOfMonths = 1;
            container.getChildren().remove(1);
            container.getChildren().add(1, showPictogram());
            allDataBtn.setDisable(false); threeMonthsBtn.setDisable(false); oneMonthBtn.setDisable(true); });


        if(getChildren().size() > 0){
            getChildren().remove(0);
        }
        getChildren().addAll(container, hoverOverPane);
    }

    //Get Pictogram Chart
    private VBox showPictogram(){
        double cases = getNumberOfCases(noOfMonths);
        VBox pictogram = getChart();

        Label date = new Label(dateLbl);
        date.setStyle("-fx-text-fill: #0D1B2A; -fx-font: 20 Arial, Helvetica, sans-serif; -fx-font-weight: bold;");

        //Population Value Label
        Label popValue = new Label("4.9 MILLION PEOPLE");
        popValue.setStyle("-fx-text-fill: #acacac; -fx-font: 20 Arial, Helvetica, sans-serif; -fx-font-weight: bold;");
        HBox popValueVBox = new HBox();
        popValueVBox.getChildren().addAll(popValue);
        popValueVBox.setAlignment(Pos.CENTER_RIGHT);

        //Covid Cases Label
        DecimalFormat df = new DecimalFormat("#,###,###");
        Label caseValue = new Label(df.format(cases) + " PEOPLE");
        caseValue.setStyle("-fx-text-fill: #4682B4; -fx-font: 20 Arial, Helvetica, sans-serif; -fx-font-weight: bold;");
        HBox caseValueVBox = new HBox();
        caseValueVBox.getChildren().addAll(caseValue);
        caseValueVBox.setAlignment(Pos.CENTER_LEFT);

        VBox chartContainer = new VBox(10);
        chartContainer.getChildren().addAll(date, caseValueVBox, pictogram, popValueVBox);
        chartContainer.setAlignment(Pos.CENTER);

        return chartContainer;
    }

    private double getNumberOfCases(int noOfMonths){
        noOfCases = 0;

        int temp = dataValues.get(0).getCases();
        int i = 1;

        //Get number of cases
        for(DataValues d: dataValues){
            //Total cases per month
            if(i <= dataValues.size()) {
                if(i == dataValues.size()) {
                    datesList.add(d.getMonth() +  ": " + temp);
                }else if (d.getMonth().equals(dataValues.get(i).getMonth())) {
                    temp += dataValues.get(i).getCases();
                } else {
                    datesList.add(d.getMonth() + ": " + temp);
                    temp = 0;
                }
                i++;
            }

            if(noOfMonths == 3) {
                if (d.getMonth().equals("Oct") || d.getMonth().equals("Nov") || d.getMonth().equals("Dec")) {
                    noOfCases += d.getCases();
                }
                dateLbl = "October - December 2020";
            }else if(noOfMonths == 1){
                if (d.getMonth().equals("Dec")) {
                    noOfCases += d.getCases();
                }
                dateLbl = "December 2020";
            }else{
                noOfCases += d.getCases();
                dateLbl = "March - December 2020";
            }
        }

        return noOfCases;
    }

    //Create Pictogram Chart Icons
    private VBox getChart(){
        HBox row;
        VBox chart = new VBox();

        double cases = noOfCases/valuePerIcon;

        int noOfPpl = population/valuePerIcon;
        int noOfLines = (noOfPpl/pplPerRow)+1;

        for(int i = 0; i < noOfLines; i++){
            row = new HBox();

            for (int j = 0; j < pplPerRow; j++) {
                double temp = 0, temp1 = 0;

                if(cases != 0) {
                    if(cases > pplPerRow){
                        temp = (int)cases - pplPerRow;
                        cases = pplPerRow;
                    }

                    temp1 = cases;
                    for (int k = 0; k < cases; k++) {
                        if(temp1 < 0.6){
                            row.getChildren().add(getImage(halfImg));
                        }else{
                            row.getChildren().add(getImage(caseImg));
                            temp1--;
                        }
                    }
                    j = j + (int)cases;
                    cases = temp;
                }else{
                    row.getChildren().add(getImage(popImg));
                }
            }
            chart.getChildren().add(row);
        }
        return chart;
    }

    private ImageView getImage(Image img){
        ImageView personIcon = new ImageView(img);
        personIcon.setFitHeight(41);
        personIcon.setPreserveRatio(true);

        if(img.equals(caseImg) || img.equals(halfImg)){
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(0.5);

            personIcon.setOnMouseEntered(e ->{
                personIcon.setEffect(colorAdjust);
                hoverOverPane.setVisible(true);
            });
            personIcon.setOnMouseExited(e -> {
                personIcon.setEffect(null);
                hoverOverPane.setVisible(false);
            });
        }

        return personIcon;
    }

    //Hover Over Case Icon Legend
    //Shows case value per month
    private StackPane hoverOverLegend(){
        String temp = null;

        if(noOfMonths == 12){
            for(int i=0; i<datesList.size(); i++){
                temp += datesList.get(i) + "\n";
            }
        }else if(noOfMonths == 3){
            for(int i=datesList.size()-(noOfMonths+1); i < datesList.size(); i++){
                temp += datesList.get(i) + "\n";
            }
        }

        Label casesPerMonth = new Label(temp);

        Rectangle outline = new Rectangle(200,80);
        outline.setFill(Color.TRANSPARENT);
        outline.setStyle("-fx-stroke-width: 2; -fx-stroke: black; -fx-fill: white;");

        StackPane pane = new StackPane();
        pane.getChildren().addAll(outline, casesPerMonth);
        return pane;
    }

    //Create Legend
    private StackPane getLegend(){
        //Population
        ImageView greyImg = new ImageView(popImg);
        greyImg.setFitHeight(45);
        greyImg.setPreserveRatio(true);

        Label greyValue = new Label("= TEN THOUSAND PEOPLE");

        HBox populationVBox = new HBox(5);
        populationVBox.getChildren().addAll(greyImg, greyValue);
        populationVBox.setAlignment(Pos.CENTER);

        //Covid Cases
        ImageView blueImg = new ImageView(caseImg);
        blueImg.setFitHeight(45);
        blueImg.setPreserveRatio(true);

        Label blueValue = new Label("= COVID POSITIVE");

        HBox covidVBox = new HBox(5);
        covidVBox.getChildren().addAll(blueImg, blueValue);
        covidVBox.setAlignment(Pos.CENTER);

        //Legend Container
        HBox legendInfo = new HBox(10);
        legendInfo.getChildren().addAll(populationVBox, covidVBox);
        legendInfo.setAlignment(Pos.CENTER);

        Rectangle outline = new Rectangle(400,80);
        outline.setFill(Color.TRANSPARENT);
        outline.setStyle("-fx-stroke-width: 2; -fx-stroke: black;");

        StackPane legend = new StackPane();
        legend.getChildren().addAll(outline, legendInfo);

        return legend;
    }
}
