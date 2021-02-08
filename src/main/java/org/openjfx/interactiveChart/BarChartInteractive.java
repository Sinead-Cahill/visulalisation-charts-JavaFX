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
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.openjfx.DataValues;

import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import java.util.ArrayList;

public class BarChartInteractive extends Pane {
    private ArrayList<DataValues> dataValues;
    private BorderPane bindingPane;
    private int i = 0;

    public BarChartInteractive(ArrayList<DataValues> dataValues, BorderPane bindingPane) {
        this.dataValues = dataValues;
        this.bindingPane = bindingPane;
    }

    public void displayGraph() {
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

        BorderPane barChartPane = new BorderPane();
        barChartPane.setCenter(showThreeMonths());
        barChartPane.setTop(btnHBox);
        barChartPane.prefWidthProperty().bind(bindingPane.widthProperty());
        barChartPane.prefHeightProperty().bind(bindingPane.heightProperty());

        allDataBtn.setOnAction(e -> { barChartPane.setCenter(showAllData()); allDataBtn.setDisable(true); threeMonthsBtn.setDisable(false); oneMonthBtn.setDisable(false); });
        threeMonthsBtn.setOnAction(e -> { barChartPane.setCenter(showThreeMonths()); allDataBtn.setDisable(false); threeMonthsBtn.setDisable(true); oneMonthBtn.setDisable(false); });
        oneMonthBtn.setOnAction(e -> { barChartPane.setCenter(showOneMonth());  allDataBtn.setDisable(false); threeMonthsBtn.setDisable(false); oneMonthBtn.setDisable(true); });

        getChildren().add(barChartPane);
    }


    private BarChart showAllData(){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");
        xAxis.setEndMargin(20);
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of Cases");

        BarChart<String, Number> barchart = new BarChart(xAxis, yAxis);
        barchart.setBarGap(2);
        barchart.getStylesheets().add(("colored-chart.css"));
        barchart.setLegendVisible(false);

        XYChart.Series series = new XYChart.Series();

        for (DataValues d : dataValues) {
            final XYChart.Data<String, Number> data = new XYChart.Data(d.getDate(), d.getCases());

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

        barchart.getData().add(series);
        return barchart;
    }

    private BarChart showThreeMonths(){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");
        xAxis.setEndMargin(10);
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of Cases");

        BarChart<String, Number> barchart = new BarChart(xAxis, yAxis);
        barchart.setBarGap(-2);
        barchart.getStylesheets().add(("colored-chart.css"));
        barchart.setLegendVisible(false);


        XYChart.Series series = new XYChart.Series();

        for (DataValues d : dataValues) {
            if(d.getMonth().equals("Oct") || d.getMonth().equals("Nov") || d.getMonth().equals("Dec")) {
                final XYChart.Data<String, Number> data = new XYChart.Data(d.getDate(), d.getCases());

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

        barchart.getData().add(series);

        return barchart;
    }

    /**
     * Show one full month (most recent)
     */
    private BarChart showOneMonth(){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of Cases");

        BarChart<String, Number> barchart = new BarChart(xAxis, yAxis);
        barchart.setBarGap(2);
        barchart.getStylesheets().add(("colored-chart.css"));
        barchart.setLegendVisible(false);

        XYChart.Series series = new XYChart.Series();

        for (DataValues d : dataValues) {
            if(d.getMonth().equals("Dec")) {
                final XYChart.Data<String, Number> data = new XYChart.Data(d.getDate(), d.getCases());

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

        barchart.getData().add(series);
        return barchart;
    }

    /**
     * Text label above each bar and hover-over colour change
     **/
    private void showCasesLabel(XYChart.Data<String, Number> data){
        final Node node = data.getNode();
        final Text dataText = new Text(data.getYValue() + "");

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
                            t1.getMinX() + t1.getWidth() / 2 - dataText.prefWidth(-1) / 2
                    ));
                    dataText.setLayoutY(Math.round(
                            t1.getMinY() - dataText.prefHeight(-1) * 0.5
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
//    private void showCasesLabel(XYChart.Data<String, Number> data){
//        final Node node = data.getNode();
//        final Text dataText = new Text(data.getYValue() + "");
//
//        node.parentProperty().addListener(new ChangeListener<Parent>() {
//            @Override
//            public void changed(ObservableValue<? extends Parent> observableValue, Parent parent, Parent t1) {
//                Group parentGroup = (Group) t1;
//                parentGroup.getChildren().add(dataText);
//            }
//        });
//
//        node.setOnMouseMoved(mouseEvent -> {
//            dataText.setLayoutX(mouseEvent.getSceneX() - 80);
//            dataText.setLayoutY(mouseEvent.getSceneY() - 150);
//        });
//
//        node.setOnMouseExited(e -> {
//            dataText.setLayoutX(-1);
//                    dataText.setLayoutY(-1);
//        });
//    }
}
