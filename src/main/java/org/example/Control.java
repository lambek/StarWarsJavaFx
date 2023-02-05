package org.example;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class Control implements EventHandler {
    Main main;

    public Control(Main main) {
        this.main = main;
    }


    @Override
    public void handle(Event event) {
        KeyEvent key = (KeyEvent) event;
//        System.out.println(key.getCode());
        switch (key.getCode()) {
            case RIGHT:
                if (main.shipX + main.stepShip + main.shipWidth / 2 <= main.width) {
                    main.shipX += main.stepShip;
                    main.ship.setX(main.shipX);
                }
                break;
            case LEFT:
                if (main.shipX - main.stepShip + main.shipWidth / 2 >= 0) {
                    main.shipX -= main.stepShip;
                    main.ship.setX(main.shipX);
                }
                break;
            case SPACE:
                main.circleArrayList.get(11).setCenterX(main.shipX + 23);
                main.circleArrayList.get(11).setCenterY(500.00);
                main.fire = true;
                break;

        }
    }

}
