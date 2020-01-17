package Home;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.util.function.UnaryOperator;

/**
 * Main UI class for this Monty Hall simulation
 * This class should not reference the business logic directly, only the abstraction provided by the controllers
 */
public class Main extends Application {

    private Controller Controller;
    private VBox ChartPane;
    private javafx.scene.chart.Chart Chart;
    private ToggleGroup ChartTypeToggle;

    private final String PieChartButtonId = "Pie";
    private final String BarChartButtonId = "Bar";

    /**
     * Sets up the UI at application startup
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.Controller = new Controller();

        primaryStage.setTitle("Monty Hall Simulation");

        StackPane root = new StackPane();

        BorderPane border = new BorderPane();
        border.setLeft(getLeftPane());
        border.setCenter(getCenterPane());

        root.getChildren().add(border);

        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();
    }

    // the left side options pane for the simulator
    private VBox getLeftPane() {
        VBox leftPane = new VBox();
        leftPane.setPadding(new Insets(10));
        leftPane.setSpacing(8);

        Text title = new Text("Options");
        title.setFont(Font.font("Roboto", FontWeight.BOLD, 14));
        leftPane.getChildren().add(title);

        Label lblChartType = new Label("Chart Type:");
        ToggleGroup chartTypeGroup = new ToggleGroup();
        this.ChartTypeToggle = chartTypeGroup;
        RadioButton btnPie = new RadioButton(PieChartButtonId);
        btnPie.setId("Pie");
        btnPie.setToggleGroup(chartTypeGroup);
        btnPie.setSelected(true);
        RadioButton btnBar = new RadioButton(BarChartButtonId);
        btnBar.setId("Bar");
        btnBar.setToggleGroup(chartTypeGroup);

        leftPane.getChildren().add(lblChartType);
        leftPane.getChildren().add(btnPie);
        leftPane.getChildren().add(btnBar);

        Label lblIterations = new Label("Iterations:");
        TextField nfIterations = new TextField();

        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("([1-9][0-9]*)?")) {
                return change;
            }
            return null;
        };

        nfIterations.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0, integerFilter));

        leftPane.getChildren().add(lblIterations);
        leftPane.getChildren().add(nfIterations);

        Button btnSimulate = new Button();
        btnSimulate.setOnAction(e -> {
           SimulationResult result = Controller.Simulate(Integer.parseInt(nfIterations.getText()));
           applySimulationResultToChart(result);
        });
        btnSimulate.setText("Simulate!");

        leftPane.getChildren().add(btnSimulate);

        return leftPane;
    }

    // the center/right chart pane for the simulator
    private VBox getCenterPane() {
        VBox centerPane = new VBox();

        this.initializePieChart();

        centerPane.getChildren().add(this.Chart);

        this.ChartPane = centerPane;
        return centerPane;
    }

    // sets up the basic settings for a pie chart and caches the reference
    private void initializePieChart() {
        PieChart chart = new PieChart();
        initializeChartBase(chart);
        chart.setStartAngle(90);

        this.Chart = chart;
    }

    // sets up the basic settings for a bar chart and caches the reference
    private void initializeBarChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> chart = new BarChart<String, Number>(xAxis, yAxis);
        initializeChartBase(chart);

        this.Chart = chart;
    }

    // sets up settings shared by all chart types
    private void initializeChartBase(Chart chart) {
        chart.setTitle("Results");
        chart.setAnimated(false);
    }

    // does the work of updating the chart with the latest simulation results
    // currently fires when 'Simulate' button is clicked, but could be extended to when chart type is changed
    private void applySimulationResultToChart(SimulationResult result) {
        RadioButton selectedChartType = (RadioButton) this.ChartTypeToggle.getSelectedToggle();
        String selectedButtonId = selectedChartType.getId();

        this.ChartPane.getChildren().remove(this.Chart);

        if (selectedButtonId.equals(PieChartButtonId))
        {
            this.initializePieChart();
            ((PieChart)this.Chart).setData(FXCollections.observableArrayList(
                    new PieChart.Data("Number correct if stayed", result.GetNumCorrectIfStayed()),
                    new PieChart.Data("Number correct if switched", result.GetNumCorrectIfSwitched())
            ));
        }
        else if (selectedButtonId.equals(BarChartButtonId))
        {
            this.initializeBarChart();
            BarChart barChart = (BarChart)this.Chart;
            barChart.getData().clear();
            barChart.layout();
            XYChart.Series series = new XYChart.Series();
            series.getData().add(new XYChart.Data("Number correct if stayed", result.GetNumCorrectIfStayed()));
            series.getData().add(new XYChart.Data("Number correct if switched", result.GetNumCorrectIfSwitched()));
            barChart.getData().addAll(series);
        }

        this.ChartPane.getChildren().add(this.Chart);
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
