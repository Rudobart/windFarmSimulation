import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Main extends Application {
    Group root = new Group();
    Scene scene = new Scene(root, 600, 500, Color.WHITE);
    public List<windTurbine> windTurbineList = new ArrayList<windTurbine>();
    private double rowCount=0;
    private double columnCount=0;
    public Double sumPower = 0.0;
    public Double moneyInvested=0.0;
    public Double moneyEarned=0.0;
    public Double kwhPrize=0.05;
    public int day=1;
    public double windAverage=0;
    public double windSum=0;
    protected int hour=1;
    public int stop=0;


    public Main() throws IOException {

    }

    public static void main(String[] args) throws IOException, NullPointerException {
        launch(args);
    }

    public void start(Stage stage) {

        Line line = new Line(350, 0, 350, 500);
        Line line2 = new Line(350, 170, 600, 170);

        Label powerLabel = new Label("Power (MW): "+String.valueOf(Math.round(((sumPower)/1000) * 100) / 100));
        powerLabel.setLayoutX(375);
        powerLabel.setLayoutY(220);
        powerLabel.setMinHeight(30);
        powerLabel.setMinWidth(200);
        powerLabel.setAlignment(Pos.CENTER);
        powerLabel.setFont(new Font("Arial", 15));

        Label percentLabel = new Label("0%");
        percentLabel.setLayoutX(375);
        percentLabel.setLayoutY(420);
        percentLabel.setMinHeight(90);
        percentLabel.setMinWidth(200);
        percentLabel.setAlignment(Pos.CENTER);
        percentLabel.setFont(new Font("Arial", 30));

        Label paybackLabel = new Label("Payback:");
        paybackLabel.setLayoutX(375);
        paybackLabel.setLayoutY(385);
        paybackLabel.setMinHeight(90);
        paybackLabel.setMinWidth(200);
        paybackLabel.setAlignment(Pos.CENTER);
        paybackLabel.setFont(new Font("Arial", 15));


        Label countLabel = new Label("Count: " + String.valueOf(windTurbineList.size()));
        countLabel.setLayoutX(375);
        countLabel.setLayoutY(140);
        countLabel.setMinHeight(30);
        countLabel.setMinWidth(200);
        countLabel.setAlignment(Pos.CENTER);
        countLabel.setFont(new Font("Arial", 15));

        Label moneyInvestedL = new Label("Money invested: "+moneyInvested/1000000+"£");
        moneyInvestedL.setLayoutX(375);
        moneyInvestedL.setLayoutY(260);
        moneyInvestedL.setMinHeight(30);
        moneyInvestedL.setMinWidth(200);
        moneyInvestedL.setAlignment(Pos.CENTER);
        moneyInvestedL.setFont(new Font("Arial", 15));

        Label moneyEarnedL = new Label("Money earned: "+moneyEarned+"£");
        moneyEarnedL.setLayoutX(375);
        moneyEarnedL.setLayoutY(300);
        moneyEarnedL.setMinHeight(30);
        moneyEarnedL.setMinWidth(200);
        moneyEarnedL.setAlignment(Pos.CENTER);
        moneyEarnedL.setFont(new Font("Arial", 15));

        Label dateLabel=new Label("Date: -.-.- -:-");
        dateLabel.setLayoutX(375);
        dateLabel.setLayoutY(180);
        dateLabel.setMinHeight(30);
        dateLabel.setMinWidth(200);
        dateLabel.setAlignment(Pos.CENTER);
        dateLabel.setFont(new Font("Arial", 15));

        Label speedLabel=new Label("Average wind speed: 0 m/s");
        speedLabel.setLayoutX(375);
        speedLabel.setLayoutY(340);
        speedLabel.setMinHeight(30);
        speedLabel.setMinWidth(200);
        speedLabel.setAlignment(Pos.CENTER);
        speedLabel.setFont(new Font("Arial", 15));

        Label daysLabel=new Label("Days passed: 0 days");
        daysLabel.setLayoutX(375);
        daysLabel.setLayoutY(380);
        daysLabel.setMinHeight(30);
        daysLabel.setMinWidth(200);
        daysLabel.setAlignment(Pos.CENTER);
        daysLabel.setFont(new Font("Arial", 15));

        Button startButton = new Button("Start");
        startButton.setLayoutX(375);
        startButton.setLayoutY(15);
        startButton.setMinWidth(200);
        startButton.setMinHeight(30);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                startSimulation(powerLabel,dateLabel,percentLabel,moneyEarnedL,speedLabel,daysLabel);
            }
        });

        Button stopButton = new Button("Stop");
        stopButton.setLayoutX(375);
        stopButton.setLayoutY(50);
        stopButton.setMinWidth(100);
        stopButton.setMinHeight(30);
        stopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                stop=1;
            }
        });
        Button resumeButton = new Button("Resume");
        resumeButton.setLayoutX(475);
        resumeButton.setLayoutY(50);
        resumeButton.setMinWidth(100);
        resumeButton.setMinHeight(30);
        resumeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                stop=0;
            }
        });

        Button addButton = new Button("Add wind turbine");
        addButton.setLayoutX(375);
        addButton.setLayoutY(100);
        addButton.setMinWidth(200);
        addButton.setMinHeight(30);
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (windTurbineList.size() < 35) {
                    windTurbineList.add(new windTurbine());
                    moneyInvested+=windTurbine.price;
                    moneyInvestedL.setText("Money invested: "+moneyInvested/1000000+"mln £");
                    countLabel.setText("Count: "+String.valueOf(windTurbineList.size()));
                    Image image1 = new Image("file:res/811342-200.png");
                    ImageView iv1 = new ImageView();
                    iv1.setImage(image1);
                    iv1.setFitHeight(70);
                    iv1.setFitWidth(70);
                    if (columnCount % 5 == 0 && columnCount != 0) {
                        rowCount += 1;
                        columnCount = 0;
                    }
                    iv1.setX((columnCount) * 70);
                    iv1.setY(rowCount * 70);
                    root.getChildren().add(iv1);
                    columnCount += 1;
                }
            }
        });

        root.getChildren().add(addButton);
        root.getChildren().add(startButton);
        root.getChildren().add(stopButton);
        root.getChildren().add(resumeButton);
        root.getChildren().add(powerLabel);
        root.getChildren().add(countLabel);
        root.getChildren().add(dateLabel);
        root.getChildren().add(percentLabel);
        root.getChildren().add(line);
        root.getChildren().add(line2);
        root.getChildren().add(moneyInvestedL);
        root.getChildren().add(moneyEarnedL);
        root.getChildren().add(speedLabel);
        root.getChildren().add(daysLabel);
        root.getChildren().add(paybackLabel);
        stage.setTitle("windFarm");
        stage.setScene(scene);
        stage.show();
    }

    private void startSimulation(Label powerLabel,Label dateLabel,Label percentLabel,Label moneyEarnedL,Label speedLabel,Label daysLabel) {
        final LongProperty lastUpdateTime = new SimpleLongProperty(0);
        try {
            Weather weather = new Weather();
            final AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long timestamp) {
                    if (stop == 0) {
                        if (lastUpdateTime.get() > 0) {
                            long elapsedTime = timestamp - lastUpdateTime.get();
                        }
                        lastUpdateTime.set(timestamp);
                        hour += 1;
                        if (hour % 24 == 0) {
                            day += 1;
                        }
                        weather.readWeather();
                        weather.calculateVariables();
                        windSum += weather.windAtTurbineHeight;
                        windAverage = windSum / hour;

                        if (weather.wind == null) {
                            stopWorking();
                        }
                        dateLabel.setText("Date: "+weather.date);
                        if (weather.wind == null) {
                            System.exit(0);
                        }
                        for (windTurbine turbine : windTurbineList) {
                            sumPower += turbine.generatePower(weather);
                            moneyEarned += turbine.generatePower(weather) * kwhPrize;
                            if(hour%24==0){
                                moneyEarned-=turbine.dailyMaintenance;
                            }
                            powerLabel.setText("Power (MW): "+String.valueOf(Math.round(((sumPower)/1000) * 100.0) / 100.0));
                            percentLabel.setText(String.valueOf(Math.round((moneyEarned / moneyInvested) * 100)) + "%");
                            moneyEarnedL.setText(("Money earned: "+Math.round(((moneyEarned)/100) * 100.0)+" £"));
                            speedLabel.setText("Average wind speed: "+Math.round(((windAverage)) * 100.0)/100.0);
                            daysLabel.setText("Days passed: "+day);
                        }
                        //                try
                        //                {
                        //                    Thread.sleep(1000);
                        //                }
                        //                catch(InterruptedException ex)
                        //                {
                        //                    Thread.currentThread().interrupt();
                        //                }
                    }
                }
            };
            timer.start();
        } catch (IOException e) {
        }
    }
    private void calculateEarnings(Weather weather,windTurbine turbine,int day){
        moneyEarned += turbine.generatePower(weather) * kwhPrize;
    }
    private void stopWorking(){

    }
}
