package org.openjfx.staticChart;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.openjfx.DataValues;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Pictogram extends Pane {

    private ArrayList<DataValues> dataValues;
    private Image popImg, caseImg, halfImg;
    private int population, valuePerIcon, pplPerRow;

    public Pictogram(ArrayList<DataValues> dataValues, Image popImg, Image caseImg, Image halfImg){
        this.dataValues = dataValues; //Data List
        this.popImg = popImg;
        this.caseImg = caseImg;
        this.halfImg = halfImg;
        population = 4900000;
        valuePerIcon = 10000;
    }

    public void displayGraph() {
        Label date = new Label("October - December 2020");
        date.setStyle("-fx-text-fill: #0D1B2A; -fx-font: 20 Arial, Helvetica, sans-serif; -fx-font-weight: bold;");

        double noOfCases = 0;

        //Get number of cases
        for(DataValues d: dataValues){
            if(d.getMonth().equals("Oct") || d.getMonth().equals("Nov") || d.getMonth().equals("Dec")){
                noOfCases += d.getCases();
            }
        }

        //Get Pictogram Chart
        VBox chart = getChart(noOfCases);

        //Population Value Label
        Label popValue = new Label("4.9 MILLION PEOPLE");
        popValue.setStyle("-fx-text-fill: #acacac; -fx-font: 20 Arial, Helvetica, sans-serif; -fx-font-weight: bold;");
        HBox popValueVBox = new HBox();
        popValueVBox.getChildren().addAll(popValue);
        popValueVBox.setAlignment(Pos.CENTER_RIGHT);

        //Covid Cases Label
        DecimalFormat df = new DecimalFormat("#,###,###");
        Label caseValue = new Label(df.format(noOfCases) + " PEOPLE");
        caseValue.setStyle("-fx-text-fill: #4682B4; -fx-font: 20 Arial, Helvetica, sans-serif; -fx-font-weight: bold;");
        HBox caseValueVBox = new HBox();
        caseValueVBox.getChildren().addAll(caseValue);
        caseValueVBox.setAlignment(Pos.CENTER_LEFT);

        //Get chart Legend
        HBox legendInfo = getLegend();
        Rectangle outline = new Rectangle(400,80);
        outline.setFill(Color.TRANSPARENT);
        outline.setStyle("-fx-stroke-width: 2; -fx-stroke: black;");

        StackPane legend = new StackPane();
        legend.getChildren().addAll(outline, legendInfo);

        //Chart Container
        VBox container = new VBox(10);
        container.getChildren().addAll(date, caseValueVBox, chart, popValueVBox, legend);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(50, 56, 0, 56));

        if(getChildren().size() > 0){
            getChildren().remove(0);
        }
        getChildren().add(0, container);
    }

    //Create Pictogram Chart Icons
    private VBox getChart(double noOfCases){
        HBox row;
        VBox chart = new VBox();
        ImageView personIcon = null;

        pplPerRow = 55;

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

        return personIcon;
    }

    //Create Legend
    private HBox getLegend(){
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
        HBox legend = new HBox(10);
        legend.getChildren().addAll(populationVBox, covidVBox);
        legend.setAlignment(Pos.CENTER);

        return legend;
    }
}
