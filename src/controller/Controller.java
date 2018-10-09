package controller;

import core.Cockroach;
import visual.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private View view;

    public Controller(View viewN) {
        view = viewN;
        view.getButton().addActionListener(new Button());
    }

    class Button implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // при нажатии на кнопку во время забега, забег будет остановлен
            // для начала нового забега повторно нажать на кнопку
            if (view.getGame().isGameStarted()) {
                raceInterrupted();
            } else {
                view.getGame().run();// по нажатию кнопки запустить забег
            }
        }

        private void raceInterrupted() {
            // убить потоки и обнулить координаты
            reloadCockroaches();
            System.out.println("Забег был прерван");
            view.getGame().delRaceCounter();
            //восстановить картинку
            reloadGame();
        }

        private void reloadCockroaches() {
            for (Cockroach c : view.getGame().getCockroaches()) {
                c.stopCockroachThread();
            }
        }

        private void reloadGame() {
            view.getGame().repaint();
            view.getGame().delFinished();
            view.getGame().setGameStarted(false);
        }
    }
}
