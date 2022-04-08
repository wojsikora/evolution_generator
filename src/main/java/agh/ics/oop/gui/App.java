package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;



public class App extends Application implements IAnimalChangeObserver{

    private AbstractWorldMap map;
    private AbstractWorldMap map2;
    private GridPane grid;
    private GridPane grid2;
    private SimulationEngine engine;
    private SimulationEngine engine2;
    private final int windowWidth = 40;
    private final int windowHeight = 40;
    private final int moveDelay = 100;
    public boolean isStop = true;
    public boolean isStop2 = true;
    public Vector2d upperRight;
    public Vector2d lowerLeft;

    public Label animalsNumberLabel;
    public Label grassNumberLabel;
    public Label dominantGenotypeLabel;
    public Label averageEnergyLabel;
    public Label averageLifespanLabel;
    public Label averageChildrenNumberLabel;

    public Label animalsNumberLabel2;
    public Label grassNumberLabel2;
    public Label dominantGenotypeLabel2;
    public Label averageEnergyLabel2;
    public Label averageLifespanLabel2;
    public Label averageChildrenNumberLabel2;

    LineChart<Number, Number> lineChart;
    XYChart.Series<Number, Number> animalNumberSeries;
    XYChart.Series<Number, Number> grassNumberSeries;
    XYChart.Series<Number, Number> averageEnergySeries;
    XYChart.Series<Number, Number> averageLifespanSeries;
    XYChart.Series<Number, Number> averageChildrenNumberSeries;

    LineChart<Number, Number> lineChart2;
    XYChart.Series<Number, Number> animalNumberSeries2;
    XYChart.Series<Number, Number> grassNumberSeries2;
    XYChart.Series<Number, Number> averageEnergySeries2;
    XYChart.Series<Number, Number> averageLifespanSeries2;
    XYChart.Series<Number, Number> averageChildrenNumberSeries2;


    Statistics statistics;
    Statistics statistics2;
    Settings settings;

    @Override
    public void start(Stage primaryStage){

        Label firstEvolutionTypeLabel = new Label("Type of evolution for first map (normal/magic): ");
        TextField firstEvolutionField = new TextField();
        HBox firstEvolutionBox = new HBox(firstEvolutionTypeLabel, firstEvolutionField);
        firstEvolutionBox.setAlignment(Pos.CENTER);


        Label secondEvolutionTypeLabel = new Label("Type of evolution for first map (normal/magic): ");
        TextField secondEvolutionField = new TextField();
        HBox secondEvolutionBox = new HBox(secondEvolutionTypeLabel, secondEvolutionField);
        secondEvolutionBox.setAlignment(Pos.CENTER);

        Label widthLabel = new Label("Map width: ");
        TextField widthField = new TextField();
        HBox widthBox = new HBox(widthLabel, widthField);
        widthBox.setAlignment(Pos.CENTER);


        Label heightLabel = new Label("Map height: ");
        TextField heightField = new TextField();
        HBox heightBox = new HBox(heightLabel, heightField);
        heightBox.setAlignment(Pos.CENTER);

        Label energyLabel = new Label("Initial animal energy: ");
        TextField energyField = new TextField();
        HBox energyBox = new HBox(energyLabel, energyField);
        energyBox.setAlignment(Pos.CENTER);

        Label moveLabel = new Label("Animal's move energy: ");
        TextField moveField = new TextField();
        HBox moveBox = new HBox(moveLabel, moveField);
        moveBox.setAlignment(Pos.CENTER);

        Label plantLabel = new Label("Plant's energy: ");
        TextField plantField = new TextField();
        HBox plantBox = new HBox(plantLabel, plantField);
        plantBox.setAlignment(Pos.CENTER);

        Label jungleLabel = new Label("Jungle ratio: ");
        TextField jungleField = new TextField();
        HBox jungleBox = new HBox(jungleLabel, jungleField);
        jungleBox.setAlignment(Pos.CENTER);


        Label animalLabel = new Label("Number of starting animals: ");
        TextField animalField = new TextField();
        HBox animalBox = new HBox(animalLabel, animalField);
        animalBox.setAlignment(Pos.CENTER);


        Button startButton = new Button("Start simulation");


        VBox startBox = new VBox(widthBox, heightBox, energyBox, moveBox, plantBox, jungleBox, animalBox,
                firstEvolutionBox, secondEvolutionBox, startButton);
        startBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(startBox, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        startButton.setOnAction(event -> {
            int firstEvol = -1;
            if(firstEvolutionField.getText().equalsIgnoreCase("normal")){
                firstEvol = 0;
            }
            else if(firstEvolutionField.getText().equalsIgnoreCase("magic")){
                firstEvol = 1;
            }

            int secondEvol = -1;
            if(secondEvolutionField.getText().equalsIgnoreCase("normal")){
                secondEvol = 0;
            }
            else if(secondEvolutionField.getText().equalsIgnoreCase("magic")){
                secondEvol = 1;
            }

            int wdth = Integer.parseInt(widthField.getText());
            int hgth = Integer.parseInt(heightField.getText());
            int enrg = Integer.parseInt(energyField.getText());
            int mv = Integer.parseInt(moveField.getText());
            int plnt = Integer.parseInt(plantField.getText());
            double jngl = Double.parseDouble(jungleField.getText());
            int anm = Integer.parseInt(animalField.getText());

            settings = new Settings(firstEvol, secondEvol, wdth, hgth, enrg, mv, plnt, jngl, anm);
            try {
                simulationWindow(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }


        });

    }

    public void simulationWindow(Stage primaryStage) throws Exception {

        initializeWorld();

        Button button = new Button("Start simulation");
        Button restartStopButton = new Button();
        restartStopButton.setVisible(false);

        Button csvButton = new Button("Statistics to CSV");
        csvButton.setVisible(false);


        animalsNumberLabel = new Label();
        animalsNumberLabel.setVisible(false);
        grassNumberLabel = new Label();
        grassNumberLabel.setVisible(false);
        dominantGenotypeLabel = new Label();
        dominantGenotypeLabel.setVisible(false);
        averageEnergyLabel = new Label();
        averageEnergyLabel.setVisible(false);
        averageLifespanLabel = new Label();
        averageLifespanLabel.setVisible(false);
        averageChildrenNumberLabel = new Label();
        averageChildrenNumberLabel.setVisible(false);

        animalsNumberLabel2 = new Label();
        animalsNumberLabel2.setVisible(false);
        grassNumberLabel2 = new Label();
        grassNumberLabel2.setVisible(false);
        dominantGenotypeLabel2 = new Label();
        dominantGenotypeLabel2.setVisible(false);
        averageEnergyLabel2 = new Label();
        averageEnergyLabel2.setVisible(false);
        averageLifespanLabel2 = new Label();
        averageLifespanLabel2.setVisible(false);
        averageChildrenNumberLabel2 = new Label();
        averageChildrenNumberLabel2.setVisible(false);

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        lineChart = new LineChart<Number, Number>(xAxis,yAxis );
        lineChart.setTitle("Evolution statistics");
        lineChart.setMaxSize(500,400);
        lineChart.setLegendVisible(true);

        lineChart2 = new LineChart<Number, Number>(xAxis,yAxis );
        lineChart2.setTitle("Evolution statistics");
        lineChart2.setMaxSize(500,400);
        lineChart2.setLegendVisible(true);

        animalNumberSeries = new XYChart.Series<>();
        animalNumberSeries.setName("Animal number");

        grassNumberSeries = new XYChart.Series<>();
        grassNumberSeries.setName("Grass number");

        averageEnergySeries = new XYChart.Series<>();
        averageEnergySeries.setName("Average energy");

        averageLifespanSeries = new XYChart.Series<>();
        averageLifespanSeries.setName("Average lifespan for dead animals");

        averageChildrenNumberSeries = new XYChart.Series<>();
        averageChildrenNumberSeries.setName("Average children number for alive animals");

        animalNumberSeries2 = new XYChart.Series<>();
        animalNumberSeries2.setName("Animal number");

        grassNumberSeries2 = new XYChart.Series<>();
        grassNumberSeries2.setName("Grass number");

        averageEnergySeries2 = new XYChart.Series<>();
        averageEnergySeries2.setName("Average energy");

        averageLifespanSeries2 = new XYChart.Series<>();
        averageLifespanSeries2.setName("Average lifespan for dead animals");

        averageChildrenNumberSeries2 = new XYChart.Series<>();
        averageChildrenNumberSeries2.setName("Average children number for alive animals");


        lineChart.getData().add(animalNumberSeries);
        lineChart.getData().add(grassNumberSeries);
        lineChart.getData().add(averageEnergySeries);
        lineChart.getData().add(averageLifespanSeries);
        lineChart.getData().add(averageChildrenNumberSeries);

        lineChart2.getData().add(animalNumberSeries2);
        lineChart2.getData().add(grassNumberSeries2);
        lineChart2.getData().add(averageEnergySeries2);
        lineChart2.getData().add(averageLifespanSeries2);
        lineChart2.getData().add(averageChildrenNumberSeries2);


        VBox vBox = new VBox(dominantGenotypeLabel ,lineChart);
        VBox box = new VBox(grid, vBox);

        VBox vBox2 = new VBox(dominantGenotypeLabel2 ,lineChart2);
        VBox box2 = new VBox(grid2, vBox2);

        VBox box3 = new VBox(button ,restartStopButton, csvButton);


        HBox hbox = new HBox(box, box2, box3);


        button.setOnAction(event -> {

                Thread engineThread = new Thread(engine);
                engineThread.start();
                restartStopButton.setVisible(true);
                isStop = false;
                restartStopButton.setText("Stop");
                this.engine.flag=true;
                button.setVisible(false);



                animalsNumberLabel.setVisible(true);
                grassNumberLabel.setVisible(true);
                dominantGenotypeLabel.setVisible(true);
                averageEnergyLabel.setVisible(true);
                averageLifespanLabel.setVisible(true);
                averageChildrenNumberLabel.setVisible(true);

                Thread engineThread2 = new Thread(engine2);
                engineThread2.start();
                this.engine2.flag=true;


                animalsNumberLabel2.setVisible(true);
                grassNumberLabel2.setVisible(true);
                dominantGenotypeLabel2.setVisible(true);
                averageEnergyLabel2.setVisible(true);
                averageLifespanLabel2.setVisible(true);
                averageChildrenNumberLabel2.setVisible(true);


       });

        restartStopButton.setOnAction(event->{
            if(isStop){
                restartStopButton.setText("Stop");
                this.engine.flag=true;
                this.engine2.flag=true;
                isStop = false;
                csvButton.setVisible(false);

            }
            else{
                restartStopButton.setText("Start");
                this.engine.flag=false;
                this.engine2.flag=false;
                isStop = true;
                csvButton.setVisible(true);
            }

        });

        csvButton.setOnAction(event->{
            try {
                this.statistics.addAverageStatisticsLine();
                this.statistics2.addAverageStatisticsLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        draw();
        setGridConstraints();
        Scene scene = new Scene(hbox, 1200, 820);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public void initializeWorld(){
        try{

            this.map = new GrassField(0, this.settings.width, this.settings.height, this.settings.plantEnergy, this.settings.jungleRatio);
            this.map2 = new GrassField(1, this.settings.width, this.settings.height, this.settings.plantEnergy, this.settings.jungleRatio);
            this.statistics = new Statistics(this.map,1);
            this.statistics2 = new Statistics(this.map2,2);
            this.upperRight = new Vector2d(this.settings.width-1, this.settings.height-1);
            this.lowerLeft = new Vector2d(0,0);
            engine = new SimulationEngine(this.map, this.settings, this.settings.firstEvolutionType);
            engine2 = new SimulationEngine(this.map2, this.settings, this.settings.secondEvolutionType);

            engine.setMoveDelay(moveDelay);
            engine.addToObservers(this);
            engine2.setMoveDelay(moveDelay);
            engine2.addToObservers(this);

            this.grid = new GridPane();
            this.grid2 = new GridPane();

        } catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setGridConstraints() {


            for (int i = upperRight.x + 1; i >= lowerLeft.x; i--)
                this.grid.getColumnConstraints().add(new ColumnConstraints(windowWidth));

            for (int j = upperRight.y + 1; j >= lowerLeft.y; j--)
                this.grid.getRowConstraints().add(new RowConstraints(windowHeight));

            for (int i = upperRight.x + 1; i >= lowerLeft.x; i--)
                this.grid2.getColumnConstraints().add(new ColumnConstraints(windowWidth));

            for (int j = upperRight.y + 1; j >= lowerLeft.y; j--)
                this.grid2.getRowConstraints().add(new RowConstraints(windowHeight));

    }

    private void drawObject() throws FileNotFoundException {


         for (int i = upperRight.x; i >= lowerLeft.x; i--) {
             for (int j = upperRight.y; j >= lowerLeft.y; j--) {
                 if (this.map.isOccupied(new Vector2d(i, j))) {

                     GuiElementBox guiElementBox = new GuiElementBox((IMapElement) this.map.objectAt(new Vector2d(i, j)));
                     Label l = new Label(this.map.objectAt(new Vector2d(i, j)).toString());
                     this.grid.add(guiElementBox.vbox, i - lowerLeft.x + 1, upperRight.y - j + 1);
                     GridPane.setHalignment(l, HPos.CENTER);
                 } else {
                     Label l = new Label("  ");
                     this.grid.add(l, i - lowerLeft.x + 1, upperRight.y - j + 1);
                     GridPane.setHalignment(l, HPos.CENTER);
                 }
             }
         }


         for (int i = upperRight.x; i >= lowerLeft.x; i--) {
             for (int j = upperRight.y; j >= lowerLeft.y; j--) {
                 if (this.map2.isOccupied(new Vector2d(i, j))) {

                     GuiElementBox guiElementBox = new GuiElementBox((IMapElement) this.map2.objectAt(new Vector2d(i, j)));
                     Label l = new Label(this.map2.objectAt(new Vector2d(i, j)).toString());
                     this.grid2.add(guiElementBox.vbox, i - lowerLeft.x + 1, upperRight.y - j + 1);
                     GridPane.setHalignment(l, HPos.CENTER);
                 } else {
                     Label l = new Label("  ");
                     this.grid2.add(l, i - lowerLeft.x + 1, upperRight.y - j + 1);
                     GridPane.setHalignment(l, HPos.CENTER);
                 }
             }
         }


    }

    private void drawCoordinates(){

        for(int i=upperRight.y; i>=lowerLeft.y; i--){
            Label l = new Label(""+i);
            this.grid.add(l, 0, upperRight.y-i+1);
            GridPane.setHalignment(l, HPos.CENTER);
        }

        for(int i=upperRight.x; i>=lowerLeft.x; i--){
            Label l = new Label(""+i);
            this.grid.add(l,i-lowerLeft.x+1, 0);
            GridPane.setHalignment(l, HPos.CENTER);
        }


        for(int i=upperRight.y; i>=lowerLeft.y; i--){
            Label l = new Label(""+i);
            this.grid2.add(l, 0, upperRight.y-i+1);
            GridPane.setHalignment(l, HPos.CENTER);
        }

        for(int i=upperRight.x; i>=lowerLeft.x; i--){
            Label l = new Label(""+i);
            this.grid2.add(l,i-lowerLeft.x+1, 0);
            GridPane.setHalignment(l, HPos.CENTER);

    }

    }

    public void draw() throws FileNotFoundException {

            Label label = new Label("y/x");
            grid.add(label, 0, 0);
            GridPane.setHalignment(label, HPos.CENTER);

            drawCoordinates();

            drawObject();


            grid.setGridLinesVisible(false);
            grid.setGridLinesVisible(true);


            Label label2 = new Label("y/x");
            grid2.add(label, 0, 0);
            GridPane.setHalignment(label2, HPos.CENTER);

            drawCoordinates();

            drawObject();


            grid2.setGridLinesVisible(false);
            grid2.setGridLinesVisible(true);


    }

    public void updateStatistics() throws IOException {


            this.statistics.saveStatisticsToCSVFile();
            animalsNumberLabel.setText("Animal number: " + this.statistics.getCurrentAnimalNumber());
            grassNumberLabel.setText("Grass number: " + this.statistics.getCurrentGrassNumber());
            dominantGenotypeLabel.setText("Dominant genotype: " + this.statistics.getDominantGenotype());
            averageEnergyLabel.setText("Average energy: " + this.statistics.getAverageAliveAnimalEnergy());
            averageLifespanLabel.setText("Average lifespan for dead animals: " + this.statistics.getAverageDeadAnimalLifespan());
            averageChildrenNumberLabel.setText("Average children number for alive animals: "
                    + this.statistics.getAverageAliveAnimalChildrenNumber());

            this.statistics2.saveStatisticsToCSVFile();
            animalsNumberLabel2.setText("Animal number: " + this.statistics2.getCurrentAnimalNumber());
            grassNumberLabel2.setText("Grass number: " + this.statistics2.getCurrentGrassNumber());
            dominantGenotypeLabel2.setText("Dominant genotype: " + this.statistics2.getDominantGenotype());
            averageEnergyLabel2.setText("Average energy: " + this.statistics2.getAverageAliveAnimalEnergy());
            averageLifespanLabel2.setText("Average lifespan for dead animals: " + this.statistics2.getAverageDeadAnimalLifespan());
            averageChildrenNumberLabel2.setText("Average children number for alive animals: "
                    + this.statistics2.getAverageAliveAnimalChildrenNumber());

    }

    public void updateChart() {

            animalNumberSeries.getData().add((new XYChart.Data<>(this.map.epoch, this.statistics.getCurrentAnimalNumber())));
            grassNumberSeries.getData().add((new XYChart.Data<>(this.map.epoch, this.statistics.getCurrentGrassNumber())));
            averageEnergySeries.getData().add((new XYChart.Data<>(this.map.epoch, this.statistics.getAverageAliveAnimalEnergy())));
            averageLifespanSeries.getData().add((new XYChart.Data<>(this.map.epoch, this.statistics.getAverageDeadAnimalLifespan())));
            averageChildrenNumberSeries.getData().add((new XYChart.Data<>(this.map.epoch, this.statistics.getAverageAliveAnimalChildrenNumber())));

            animalNumberSeries2.getData().add((new XYChart.Data<>(this.map2.epoch, this.statistics2.getCurrentAnimalNumber())));
            grassNumberSeries2.getData().add((new XYChart.Data<>(this.map2.epoch, this.statistics2.getCurrentGrassNumber())));
            averageEnergySeries2.getData().add((new XYChart.Data<>(this.map2.epoch, this.statistics2.getAverageAliveAnimalEnergy())));
            averageLifespanSeries2.getData().add((new XYChart.Data<>(this.map2.epoch, this.statistics2.getAverageDeadAnimalLifespan())));
            averageChildrenNumberSeries2.getData().add((new XYChart.Data<>(this.map2.epoch, this.statistics2.getAverageAliveAnimalChildrenNumber())));

    }



    @Override
    public void updateAnimalsOnMap() {
        Platform.runLater(() ->{
            this.grid.getChildren().clear();
            try {
                draw();
                updateStatistics();
                updateChart();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}
