// https://coderlessons.com/tutorials/java-tekhnologii/vyuchi-javafx/javafx-animatsiia
// https://javarush.com/groups/posts/2560-vvedenie-v-java-fx
// https://ru.stackoverflow.com/questions/459113/%D0%9A%D0%B0%D0%BA-%D1%81%D0%B4%D0%B5%D0%BB%D0%B0%D1%82%D1%8C-%D0%BE%D0%B1%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D0%BA%D1%83-%D1%81%D0%BE%D0%B1%D1%8B%D1%82%D0%B8%D0%B9-%D0%BA%D0%BB%D0%B0%D0%B2%D0%B8%D1%88

package org.example;

import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {
    double width = 800.00;
    double height = 600.00;
    double shipWidth = 46.00;
    double shipHeight = 46.00;
    double shipX = (width - shipWidth) / 2;
    double shipY = 500.00;
    double stepShip = 10.00;
    double circleRadius = 1;
    int timerIndex = 0;
    MyAnimationTimer runer;
    Label label, labelGamesOver;
    RotateTransition rotateTransition;
    Group root;
    ArrayList<Circle> circleArrayList = new ArrayList();
    ArrayList<ImageView> shipLivesArrayList = new ArrayList();
    int lives = 3;
    boolean fire = false;
    ImageView ship;


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("star wars");
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);

        Image _fon = new Image(new FileInputStream("images\\kosmos_fon.jpg"));
        ImageView fon = new ImageView(_fon);
        fon.setX(0);
        fon.setY(0);
        fon.setFitWidth(width);
        fon.setFitHeight(height);

        label = new Label();
        label.setLayoutX(30);
        label.setLayoutY(10);
        label.setFont(new Font(24));
        label.setTextFill(Color.WHITE);

        labelGamesOver = new Label();
        labelGamesOver.setLayoutX((width - 270) / 2);
        labelGamesOver.setLayoutY((height - 275) / 2);
        labelGamesOver.setFont(new Font(35));
        labelGamesOver.setTextFill(Color.WHITE);
        labelGamesOver.setText("Вы проиграли\nвам удалось выжить\nпосле крушения\nвы доблестно сражались");

        Image _ship = new Image(new FileInputStream("images\\ship.png"));
        ship = new ImageView(_ship);
        ship.setX(shipX);
        ship.setY(shipY);
        ship.setFitWidth(shipWidth);
        ship.setFitHeight(shipHeight);

        for (int i = 0; i < lives; i++) {
            ImageView shipLive = new ImageView(_ship);
            shipLive.setX((width - 100) - (i * 30));
            shipLive.setY(15);
            shipLive.setFitWidth(25);
            shipLive.setFitHeight(25);
            shipLivesArrayList.add(i, shipLive);
        }


        rotateTransition = new RotateTransition();
        rotateTransition.setDuration(Duration.millis(300));
        rotateTransition.setNode(ship);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(3);


        this.initCircleArrayList();

        root = new Group(fon, label, ship, shipLivesArrayList.get(0), shipLivesArrayList.get(1), shipLivesArrayList.get(2));
        Scene scene = new Scene(root, width, height);

        runer = new MyAnimationTimer(this);
        runer.start();

        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setOnKeyPressed(new Control(this));
    }

    public void setTimerLable() {
//        timerIndex++;
        label.setText("Total: " + String.valueOf(timerIndex));
    }

    protected void initCircleArrayList() {
        for (int i = 0; i <= 11; i++) {
            Circle cn = new Circle();
            cn.setFill(Color.WHITE);
            cn.setCenterX(new Random().nextInt(((int) width - 20)));
            cn.setCenterY(0.0);
            cn.setRadius(circleRadius + 5);
            circleArrayList.add(i, cn);
        }
    }

    public void renderCircle() {
        int i = 0;
        if (new Random().nextBoolean()) {
            i = new Random().nextInt(4);
        } else if (new Random().nextBoolean()) {
            i = new Random().nextInt(6);
        } else {
            i = new Random().nextInt(10);
        }
        double centerX = circleArrayList.get(i).getCenterX();
        double centerY = circleArrayList.get(i).getCenterY();
        centerY += ((Math.random() * 10) + 5);
        if (shipX <= centerX && (shipX + 45) >= centerX && (shipY + 15) <= centerY) {
            centerY = height;
            checkKill(rotateTransition);
        }

        circleArrayList.get(i).setCenterY(centerY);
        if (!root.getChildren().contains(circleArrayList.get(i))) {
            root.getChildren().add(circleArrayList.get(i));
        }
        if (centerY >= height) {
            circleArrayList.get(i).setCenterY(0.0);
            circleArrayList.get(i).setCenterX(new Random().nextInt(((int) width - 20)));
            root.getChildren().remove(circleArrayList.get(i));
        }
    }

    protected void checkKill(RotateTransition rotateTransition) {
        rotateTransition.play();
        if (shipLivesArrayList.size() != 0) {
            int i = shipLivesArrayList.size() - 1;
            root.getChildren().remove(shipLivesArrayList.get(i));
            shipLivesArrayList.remove(i);
        } else {
            runer.stop();
            root.getChildren().add(labelGamesOver);
        }
    }

    public void fire() {
        if (fire) {
            if (!root.getChildren().contains(circleArrayList.get(11))) {
                root.getChildren().add(circleArrayList.get(11));
            }
            double centerX = circleArrayList.get(11).getCenterX();
            double centerY = circleArrayList.get(11).getCenterY();
            centerY -= 15;
            circleArrayList.get(11).setCenterY(centerY);
            int fireForIndex = 0;
            for (int i = 0; i <= 10; i++) {
                double bulletCenterX = circleArrayList.get(i).getCenterX();
                if ((bulletCenterX + 1) >= centerX && centerX <= (bulletCenterX - 1)) {
                    fireForIndex = i;
                    break;
                }
            }
            double bulletCenterY = circleArrayList.get(fireForIndex).getCenterY();
            if ((bulletCenterY + 5) >= centerY && centerY <= (bulletCenterY - 5)) {
                circleArrayList.get(fireForIndex).setCenterX(new Random().nextInt(((int) width - 20)));
                circleArrayList.get(fireForIndex).setCenterY(0.0);
                root.getChildren().remove(circleArrayList.get(11));
                timerIndex++;
                fire = false;
            }
            if (centerY <= 10) {
                root.getChildren().remove(circleArrayList.get(11));
                fire = false;
            }
        }
    }

}

