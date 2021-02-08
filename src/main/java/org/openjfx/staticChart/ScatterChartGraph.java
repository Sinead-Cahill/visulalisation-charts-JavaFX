package org.openjfx.staticChart;

import javafx.scene.chart.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.openjfx.DataValues;

import java.util.ArrayList;

public class ScatterChartGraph extends Pane {
    private ArrayList<DataValues> dataValues;
    private BorderPane bindingPane;

    public ScatterChartGraph(ArrayList<DataValues> dataValues, BorderPane bindingPane){
        this.dataValues = dataValues;
        this.bindingPane = bindingPane;
    }

    public void displayGraph(){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date (2 Week Period)");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of Cases");

        ScatterChart<String,Number> scatterChart = new ScatterChart(xAxis,yAxis);

        XYChart.Series series = new XYChart.Series();
        series.setName("Number of Cases in Ireland");

        for(DataValues d: dataValues){
            if(d.getMonth().equals("Oct") || d.getMonth().equals("Nov") || d.getMonth().equals("Dec")){
                XYChart.Data<String, Number> data = new XYChart.Data(d.getDate(), d.getCases());
                series.getData().add(data);
            }
        }

        scatterChart.getData().add(series);
        scatterChart.getStylesheets().add(("colored-chart.css"));
        scatterChart.setLegendVisible(false);

        BorderPane scatterChartPane = new BorderPane();
        scatterChartPane.setCenter(scatterChart);
        scatterChartPane.prefWidthProperty().bind(bindingPane.widthProperty());
        scatterChartPane.prefHeightProperty().bind(bindingPane.heightProperty());

        getChildren().add(scatterChartPane);
    }
}
