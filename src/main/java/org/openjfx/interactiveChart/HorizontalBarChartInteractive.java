package org.openjfx.interactiveChart;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.openjfx.DataValues;

import java.util.ArrayList;

public class HorizontalBarChartInteractive extends Pane {
    private ArrayList<DataValues> dataValues;
    private BorderPane bindingPane;

    public HorizontalBarChartInteractive(ArrayList<DataValues> dataValues, BorderPane bindingPane){
        this.dataValues = dataValues;
        this.bindingPane = bindingPane;
    }

    public void displayGraph() {
        getChildren().clear();

        // Zoom Buttons
        Button allDataBtn = new Button("All Data");
        Button threeMonthsBtn = new Button("3 Months");
        Button oneMonthBtn = new Button("1 Month");

        threeMonthsBtn.setDisable(true);

        HBox btnHBox = new HBox(5);
        btnHBox.getChildren().addAll(allDataBtn, threeMonthsBtn, oneMonthBtn);
        btnHBox.setMargin(allDataBtn, new Insets(0,0,5,70));

        for(int i=0; i<btnHBox.getChildren().size(); i++){
            btnHBox.getChildren().get(i).setStyle("-fx-background-color: #4682B466; -fx-background-radius: 5; -fx-font: 12 Verdana;");
        }

        BorderPane hBarChartPane = new BorderPane();
        hBarChartPane.setCenter(showThreeMonths());
        hBarChartPane.setTop(btnHBox);
        hBarChartPane.prefWidthProperty().bind(bindingPane.widthProperty());
        hBarChartPane.prefHeightProperty().bind(bindingPane.heightProperty());

        allDataBtn.setOnAction(e -> { hBarChartPane.setCenter(showAllData()); allDataBtn.setDisable(true); threeMonthsBtn.setDisable(false); oneMonthBtn.setDisable(false); });
        threeMonthsBtn.setOnAction(e -> { hBarChartPane.setCenter(showThreeMonths()); allDataBtn.setDisable(false); threeMonthsBtn.setDisable(true); oneMonthBtn.setDisable(false); });
        oneMonthBtn.setOnAction(e -> { hBarChartPane.setCenter(showOneMonth());  allDataBtn.setDisable(false); threeMonthsBtn.setDisable(false); oneMonthBtn.setDisable(true); });

        getChildren().add(hBarChartPane);
    }

    private BarChart showAllData(){
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Number of Cases");
//        xAxis.setEndMargin(20);
        CategoryAxis yAxis = new CategoryAxis();
        yAxis.setLabel("Date");

        BarChart<Number, String> hBarchart = new BarChart(xAxis, yAxis);
        hBarchart.getStylesheets().add(("colored-chart.css"));
        hBarchart.setLegendVisible(false);

        XYChart.Series series = new XYChart.Series();

        for (DataValues d : dataValues) {
            final XYChart.Data<Number, String> data = new XYChart.Data(d.getCases(), d.getDate());

            data.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        node.setStyle("-fx-bar-fill: #4682B4;");
                        showCasesLabel(data);
                    }
                }
            });
            series.getData().add(data);
        }

        hBarchart.getData().add(series);
        return hBarchart;
    }

    private BarChart showThreeMonths(){
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Number of Cases");
//        xAxis.setEndMargin(10);
        CategoryAxis yAxis = new CategoryAxis();
        yAxis.setLabel("Date");

        BarChart<Number, String> hBarchart = new BarChart(xAxis, yAxis);
        hBarchart.setBarGap(-4);
        hBarchart.getStylesheets().add(("colored-chart.css"));
        hBarchart.setLegendVisible(false);


        XYChart.Series series = new XYChart.Series();

        for (DataValues d : dataValues) {
            if(d.getMonth().equals("Oct") || d.getMonth().equals("Nov") || d.getMonth().equals("Dec")) {
                final XYChart.Data<Number, String> data = new XYChart.Data(d.getCases(), d.getDate());

                data.nodeProperty().addListener(new ChangeListener<Node>() {
                    @Override
                    public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                        if (node != null) {
                            node.setStyle("-fx-bar-fill: #4682B4;");
                            showCasesLabel(data);
                        }
                    }
                });
                series.getData().add(data);
            }
        }

        hBarchart.getData().add(series);

        return hBarchart;
    }

    /**
     * Show one full month (most recent)
     */
    private BarChart showOneMonth(){
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Number of Cases");
//        xAxis.setEndMargin(10);
        CategoryAxis yAxis = new CategoryAxis();
        yAxis.setLabel("Date");

        BarChart<Number, String> hBarchart = new BarChart(xAxis, yAxis);
        hBarchart.setBarGap(-4);
        hBarchart.getStylesheets().add(("colored-chart.css"));
        hBarchart.setLegendVisible(false);

        XYChart.Series series = new XYChart.Series();

        for (DataValues d : dataValues) {
            if(d.getMonth().equals("Dec")) {
                final XYChart.Data<Number, String> data = new XYChart.Data(d.getCases(), d.getDate());

                data.nodeProperty().addListener(new ChangeListener<Node>() {
                    @Override
                    public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                        if (node != null) {
                            node.setStyle("-fx-bar-fill: #4682B4;");
                            showCasesLabel(data);
                        }
                    }
                });
                series.getData().add(data);
            }
        }

        hBarchart.getData().add(series);
        return hBarchart;
    }

    /**
     * Text label above each bar and hover-over colour change
     **/
    private void showCasesLabel(XYChart.Data<Number, String> data){
        final Node node = data.getNode();
        final Text dataText = new Text(data.getXValue() + "");

        node.parentProperty().addListener(new ChangeListener<Parent>() {
            @Override
            public void changed(ObservableValue<? extends Parent> observableValue, Parent parent, Parent t1) {
                Group parentGroup = (Group) t1;
                parentGroup.getChildren().add(dataText);
            }
        });

        node.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observableValue, Bounds bounds, Bounds t1) {
                node.setOnMouseEntered(e -> {
                    node.setStyle("-fx-bar-fill: #7ba7cc;");
                    dataText.setFont(Font.font(15));

                    dataText.setLayoutX(Math.round(
                            t1.getMinX() + t1.getWidth() + 5
                    ));
                    dataText.setLayoutY(Math.round(
                            t1.getMinY() + t1.getHeight() - 2.5
                    ));
                });
                node.setOnMouseExited(e -> {
                    node.setStyle("-fx-bar-fill: #4682B4;");

                    dataText.setLayoutX(-1);
                    dataText.setLayoutY(-1);
                });
            }
        });
    }
}
