package controller;

import core.Cockroach;
import visual.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Controller {
    private View view;
    private boolean flag = false;
    private boolean flag1 = false;

    private static int widthCockroach = Cockroach.getCockroachWidth();
    private static int heightCockroach = Cockroach.getCockroachHeight();

    public Controller(View viewN) {
        this.view = viewN;
        this.view.getButton().addActionListener(new Button());
        this.view.getGame().addMouseListener(new Mouse());
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

    class Mouse implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (view.getGame().isGameStarted()) {
                for (int i = 0; i < view.getGame().getNumberOfTracks(); i++) {
                    int x = view.getGame().getCockroaches().get(i).getCoordX();
                    int y = view.getGame().getCockroaches().get(i).getCoordY();

                    if ((e.getX() >= x && e.getX() <= x + widthCockroach))
                        flag = true;

                    if ((e.getY() >= y && e.getY() <= y + heightCockroach))
                        flag1 = true;

                    if (flag && flag1) {
                        view.getGame().getCockroaches().get(i).step(200);
                        view.getGame().repaint();

                        flag = false;
                        flag1 = false;
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
