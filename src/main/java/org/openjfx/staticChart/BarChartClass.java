package org.openjfx.staticChart;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.*;
import org.openjfx.DataValues;

import java.util.ArrayList;

public class BarChartClass extends Pane {
    private ArrayList<DataValues> dataValues;
    private BorderPane bindingPane;
    private int i=0;

    public BarChartClass(ArrayList<DataValues> dataValues, BorderPane bindingPane){
        this.dataValues = dataValues;
        this.bindingPane = bindingPane;
    }

    public void displayGraph(){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of Cases");

        BarChart<String,Number> barchart = new BarChart(xAxis,yAxis);

        XYChart.Series series = new XYChart.Series();
        series.setName("Number of Cases in Ireland");

        for(DataValues d: dataValues){
            if(d.getMonth().equals("Oct") || d.getMonth().equals("Nov") || d.getMonth().equals("Dec")){
//            series1.getData().add(createData(d.getDate(), d.getCases()));
                final XYChart.Data<String, Number> data = new XYChart.Data(d.getDate(), d.getCases());
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

        barchart.getData().addAll(series);
        barchart.setBarGap(-2);
        barchart.styleProperty();

        barchart.getStylesheets().add(("colored-chart.css"));
        barchart.setLegendVisible(false);

        BorderPane barChartPane = new BorderPane();
        barChartPane.setCenter(barchart);
        barChartPane.prefWidthProperty().bind(bindingPane.widthProperty());
        barChartPane.prefHeightProperty().bind(bindingPane.heightProperty());

        getChildren().add(barChartPane);
    }
}