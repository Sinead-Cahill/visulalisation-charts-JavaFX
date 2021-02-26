package org.openjfx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.openjfx.interactiveChart.*;
import org.openjfx.staticChart.*;

import java.util.ArrayList;

public class GraphController extends Application {

    private FileInput readFile;
    private ArrayList<DataValues> dataValues;
    private Image popImg, caseImg, halfImg;

    private BarChartClass barChart;
    private BarChartInteractive barChartInteractive;
    private HorizontalBarChart hBarChart;
    private HorizontalBarChartInteractive hBarChartInteractive;
    private LineGraph lineGraph;
    private LineGraphInteractive lineGraphInteractive;
    private AreaChartClass areaChart;
    private AreaChartInteractive areaChartInteractive;
    private ScatterChartGraph scatterChart;
    private ScatterChartInteractive scatterChartInteractive;
    private Pictogram pictogramChart;
    private PictogramInteractive pictogramInteractive;

    public GraphController() throws Exception{
        readFile = new FileInput(); //Read File
        dataValues = readFile.readDataFile(); //Data List
        popImg = readFile.readPopImage();
        caseImg = readFile.readCaseImage();
        halfImg = readFile.readHalfImage();
    }

    public void start(Stage primaryStage){
        BorderPane graphPane = new BorderPane();

        //Graphs - Static
        barChart = new BarChartClass(dataValues, graphPane);
        hBarChart = new HorizontalBarChart(dataValues, graphPane);
        lineGraph = new LineGraph(dataValues,graphPane);
        areaChart = new AreaChartClass(dataValues,graphPane);
        scatterChart = new ScatterChartGraph(dataValues,graphPane);
        pictogramChart = new Pictogram(dataValues, popImg, caseImg, halfImg);
        //Graphs - Interactive
        barChartInteractive = new BarChartInteractive(dataValues, graphPane);
        hBarChartInteractive = new HorizontalBarChartInteractive(dataValues, graphPane);
        lineGraphInteractive = new LineGraphInteractive(dataValues,graphPane);
        areaChartInteractive = new AreaChartInteractive(dataValues, graphPane);
        scatterChartInteractive = new ScatterChartInteractive(dataValues,graphPane);
        pictogramInteractive = new PictogramInteractive(dataValues, popImg, caseImg, halfImg);

        Node[] shapes = {barChart,hBarChart,lineGraph,areaChart,scatterChart,pictogramChart,barChartInteractive, hBarChartInteractive, lineGraphInteractive, areaChartInteractive, scatterChartInteractive, pictogramInteractive};

        //Title of Project
        Label title = new Label("Spread of COVID-19 in Ireland");
        title.setStyle("-fx-text-fill: #0D1B2A; -fx-font: 30 Arial, Helvetica, sans-serif; -fx-font-weight: bold;");

        //Title of Graph
        Label graphTitle = new Label("Bar Chart");
        graphTitle.setStyle("-fx-text-fill: #0D1B2A; -fx-font: 20 Arial, Helvetica, sans-serif; -fx-font-weight: bold;");

        //Title Container
        VBox titleVBox = new VBox(20);
        titleVBox.getChildren().addAll(title, graphTitle);
        titleVBox.setAlignment(Pos.CENTER);
        titleVBox.setPadding(new Insets(10, 0, 10, 0));

        //Buttons - Static
        Button barChartBtn = new Button("Bar Chart");
        Button hBarChartBtn = new Button("Horizontal Bar Chart");
        Button lineGraphBtn = new Button("Line Graph");
        Button areaChartBtn = new Button("Area Chart");
        Button scatterChartBtn = new Button("Scatter Chart");
        Button pictogramBtn = new Button("Pictogram");

        //Button - Interactive
        Button barChartInteractiveBtn = new Button("Bar Chart");
        Button hBarChartInteractiveBtn = new Button("Horizontal Bar Chart");
        Button lineGraphInteractiveBtn = new Button("Line Graph");
        Button areaChartInteractiveBtn = new Button("Area Chart");
        Button scatterChartInteractiveBtn = new Button("Scatter Chart");
        Button pictogramInteractiveBtn = new Button("Pictogram");

        //Static Buttons Container
        HBox staticBtnHBox = new HBox(10);
        Label staticCharts = new Label("Static Visualisations:".toUpperCase());
        staticCharts.setStyle("-fx-font-weight: bold;");
        staticBtnHBox.getChildren().addAll(staticCharts, barChartBtn, hBarChartBtn, lineGraphBtn, areaChartBtn, scatterChartBtn, pictogramBtn);
        staticBtnHBox.setAlignment(Pos.CENTER);
        staticBtnHBox.setPadding(new Insets(0, 0, 15, 0));

        //Interactive Buttons Container
        HBox interactiveBtnHBox = new HBox(10);
        Label interactiveCharts = new Label("Interactive Visualisations:".toUpperCase());
        interactiveCharts.setStyle("-fx-font-weight: bold;");
        interactiveBtnHBox.getChildren().addAll(interactiveCharts, barChartInteractiveBtn, hBarChartInteractiveBtn, lineGraphInteractiveBtn, areaChartInteractiveBtn, scatterChartInteractiveBtn, pictogramInteractiveBtn);
        interactiveBtnHBox.setAlignment(Pos.CENTER);
        interactiveBtnHBox.setPadding(new Insets(0, 0, 15, 0));

        //All Button Container
        VBox allBtns = new VBox();
        allBtns.getChildren().addAll(staticBtnHBox, interactiveBtnHBox);

        Node[] buttons = {barChartBtn, hBarChartBtn, lineGraphBtn, areaChartBtn, scatterChartBtn, pictogramBtn, barChartInteractiveBtn, hBarChartInteractiveBtn, lineGraphInteractiveBtn, areaChartInteractiveBtn, scatterChartInteractiveBtn, pictogramInteractiveBtn};
        btnEffect(buttons, null);

        //Button Action
        barChartBtn.setOnAction(e -> {btnAction(graphPane, shapes, 0, graphTitle, "Bar Chart"); btnEffect(buttons,barChartBtn); barChart.displayGraph(); });
        hBarChartBtn.setOnAction(e -> {btnAction(graphPane, shapes, 1, graphTitle, "Horizontal Bar Chart"); btnEffect(buttons,hBarChartBtn); hBarChart.displayGraph(); });
        lineGraphBtn.setOnAction(e -> {btnAction(graphPane, shapes, 2, graphTitle, "Line Graph"); btnEffect(buttons,lineGraphBtn); lineGraph.displayGraph(); });
        areaChartBtn.setOnAction(e -> {btnAction(graphPane, shapes, 3, graphTitle, "Area Chart"); btnEffect(buttons,areaChartBtn); areaChart.displayGraph(); });
        scatterChartBtn.setOnAction(e -> {btnAction(graphPane, shapes, 4, graphTitle, "Scatter Chart"); btnEffect(buttons,scatterChartBtn); scatterChart.displayGraph(); });
        pictogramBtn.setOnAction(e -> {btnAction(graphPane, shapes, 5, graphTitle, "Pictogram"); btnEffect(buttons,pictogramBtn); pictogramChart.displayGraph(); });


        barChartInteractiveBtn.setOnAction(e -> {btnAction(graphPane, shapes, 6, graphTitle, "Bar Chart"); btnEffect(buttons,barChartInteractiveBtn); barChartInteractive.displayGraph(); });
        hBarChartInteractiveBtn.setOnAction(e -> {btnAction(graphPane, shapes, 7, graphTitle, "Horizontal Bar Chart"); btnEffect(buttons,hBarChartInteractiveBtn); hBarChartInteractive.displayGraph(); });
        lineGraphInteractiveBtn.setOnAction(e -> {btnAction(graphPane, shapes, 8, graphTitle, "Line Graph"); btnEffect(buttons,lineGraphInteractiveBtn); lineGraphInteractive.displayGraph(); });
        areaChartInteractiveBtn.setOnAction(e -> {btnAction(graphPane, shapes, 9, graphTitle, "Area Chart"); btnEffect(buttons,areaChartInteractiveBtn); areaChartInteractive.displayGraph(); });
        scatterChartInteractiveBtn.setOnAction(e -> {btnAction(graphPane, shapes, 10, graphTitle, "Scatter Chart"); btnEffect(buttons,scatterChartInteractiveBtn); scatterChartInteractive.displayGraph(); });
        pictogramInteractiveBtn.setOnAction(e -> {btnAction(graphPane, shapes, 11, graphTitle, "Pictogram"); btnEffect(buttons,pictogramInteractiveBtn); pictogramInteractive.displayGraph(); });

        barChartBtn.fire();

        BorderPane container = new BorderPane();
        container.setTop(titleVBox);
        container.setCenter(graphPane);
        container.setBottom(allBtns);
        container.getStylesheets().add(("colored-chart.css"));

        Scene scene = new Scene(container, 1100, 900);
        primaryStage.setTitle("Data Visualisation: Covid-19 in Ireland"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    private void btnAction(BorderPane pane, Node[] grp, int i, Label lbl, String title){
        if(pane.getChildren().size() != 0) {
            pane.getChildren().remove(0);
        }
        pane.getChildren().add(grp[i]);
        lbl.setText(title);
    }

    private void btnEffect(Node[] btns, Button btn){
        for(int i=0; i<btns.length; i++){
            btns[i].setStyle("-fx-background-color: #4682B466; -fx-background-radius: 5; -fx-font: 12 Verdana;");

            if(btns[i].equals(btn)){
                btn.setDisable(true);
            }else{
                btns[i].setDisable(false);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
