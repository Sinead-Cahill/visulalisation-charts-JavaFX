package org.openjfx.staticChart;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.openjfx.DataValues;

import java.util.ArrayList;

public class HorizontalBarChart extends Pane {

    private ArrayList<DataValues> dataValues;
    private BorderPane bindingPane;

    public HorizontalBarChart(ArrayList<DataValues> dataValues, BorderPane bindingPane){
        this.dataValues = dataValues;
        this.bindingPane = bindingPane;
    }

    public void displayGraph() {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Number of Cases");
        CategoryAxis yAxis = new CategoryAxis();
        yAxis.setLabel("Date");

        BarChart<Number, String> hBarchart = new BarChart(xAxis, yAxis);

        XYChart.Series series = new XYChart.Series();
        series.setName("Number of Cases in Ireland");

        for(DataValues d: dataValues){
            if(d.getMonth().equals("Oct") || d.getMonth().equals("Nov") || d.getMonth().equals("Dec")){
                final XYChart.Data<Number, String> data = new XYChart.Data(d.getCases(), d.getDate());
                data.nodeProperty().addListener(new ChangeListener<Node>() {
                    @Override
                    public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                        if (node != null) {
                            node.setStyle("-fx-bar-fill: #4682B4;");
//                            displayLabelForData(data);
                        }
                    }
                });
                series.getData().add(data);
            }
        }

        hBarchart.getData().addAll(series);
        hBarchart.setBarGap(-4);
        hBarchart.styleProperty();

        hBarchart.getStylesheets().add(("colored-chart.css"));
        hBarchart.setLegendVisible(false);

        BorderPane hBarChartPane = new BorderPane();
        hBarChartPane.setCenter(hBarchart);
        hBarChartPane.prefWidthProperty().bind(bindingPane.widthProperty());
        hBarChartPane.prefHeightProperty().bind(bindingPane.heightProperty());

        getChildren().add(hBarChartPane);
    }
}
