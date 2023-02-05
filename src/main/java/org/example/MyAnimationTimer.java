package org.example;

import javafx.animation.AnimationTimer;

class MyAnimationTimer extends AnimationTimer {

    Object obj;

    public MyAnimationTimer(Object Obj) {
        this.obj = Obj;
    }

    @Override
    public void handle(long now) {
        try {
//            Thread.sleep(300);
            Main m = (Main) obj;
            m.setTimerLable();
            m.renderCircle();
            m.fire();
        } catch (Exception e) {

        }

    }

}
