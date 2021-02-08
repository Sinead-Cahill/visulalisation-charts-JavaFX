package org.openjfx.staticChart;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.openjfx.DataValues;

import java.util.ArrayList;

public class LineGraph extends Pane {
    private ArrayList<DataValues> dataValues;
    private BorderPane bindingPane;

    public LineGraph(ArrayList<DataValues> dataValues, BorderPane bindingPane){
        this.dataValues = dataValues;
        this.bindingPane = bindingPane;
    }

    public void displayGraph(){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of Cases");

        LineChart<String,Number> lineGraph = new LineChart(xAxis,yAxis);

        XYChart.Series series = new XYChart.Series();
        series.setName("Number of Cases in Ireland");

        for(DataValues d: dataValues){
            if(d.getMonth().equals("Oct") || d.getMonth().equals("Nov") || d.getMonth().equals("Dec")){
                final XYChart.Data<String, Number> data = new XYChart.Data(d.getDate(), d.getCases());
                data.nodeProperty().addListener(new ChangeListener<Node>() {
                    @Override
                    public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                        if (node != null) {
                            node.setStyle("-fx-stroke: #4682B4; -fx-background-color: #4682B4; ");
//                            displayLabelForData(data);
                        }
                    }
                });
                series.getData().add(data);
            }
        }

        lineGraph.getData().add(series);
        lineGraph.getStylesheets().add(("colored-chart.css"));
        lineGraph.setLegendVisible(false);

        BorderPane lineGraphPane = new BorderPane();
        lineGraphPane.setCenter(lineGraph);
        lineGraphPane.prefWidthProperty().bind(bindingPane.widthProperty());
        lineGraphPane.prefHeightProperty().bind(bindingPane.heightProperty());

        getChildren().add(lineGraphPane);
    }
}
