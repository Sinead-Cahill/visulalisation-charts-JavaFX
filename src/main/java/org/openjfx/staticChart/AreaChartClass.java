package org.openjfx.staticChart;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.openjfx.DataValues;

import java.util.ArrayList;

public class AreaChartClass extends Pane {
    private ArrayList<DataValues> dataValues;
    private BorderPane bindingPane;

    public AreaChartClass(ArrayList<DataValues> dataValues, BorderPane bindingPane){
        this.dataValues = dataValues;
        this.bindingPane = bindingPane;
    }

    public void displayGraph(){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of Cases");

        AreaChart<String,Number> areaChart = new AreaChart(xAxis,yAxis);

        XYChart.Series series = new XYChart.Series();
        series.setName("Number of Cases in Ireland");

        for(DataValues d: dataValues){
            if(d.getMonth().equals("Oct") || d.getMonth().equals("Nov") || d.getMonth().equals("Dec")){
                XYChart.Data<String, Number> data = new XYChart.Data(d.getDate(), d.getCases());
                series.getData().add(data);
            }
        }

        areaChart.getData().add(series);
        areaChart.getStylesheets().add(("colored-chart.css"));
        areaChart.setLegendVisible(false);
        areaChart.setCreateSymbols(false);

        BorderPane areaChartPane = new BorderPane();
        areaChartPane.setCenter(areaChart);
        areaChartPane.prefWidthProperty().bind(bindingPane.widthProperty());
        areaChartPane.prefHeightProperty().bind(bindingPane.heightProperty());

        getChildren().add(areaChartPane);
    }
}
