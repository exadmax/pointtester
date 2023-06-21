package com.exadmax.pointtester;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.util.Timer;
import java.util.TimerTask;

public class Mover {

    private static final int INTERVALO = 20000; // 20 segundos
    private static final int DESLOCAMENTO = 10; // 1 centímetro

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new MouseMoveTask(), 0, INTERVALO);
    }

    private static class MouseMoveTask extends TimerTask {
        private Robot robot;
        private int x;
        private int y;

        public MouseMoveTask() {
            try {
                this.robot = new Robot();
                this.x = MouseInfo.getPointerInfo().getLocation().x;
                this.y = MouseInfo.getPointerInfo().getLocation().y;
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            // Movendo o mouse
            robot.mouseMove(x + DESLOCAMENTO, y + DESLOCAMENTO);

            // Atualizando a posição do mouse
            x += DESLOCAMENTO;
            y += DESLOCAMENTO;
        }
    }
}