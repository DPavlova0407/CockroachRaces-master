package core;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cockroach extends JPanel implements Runnable {
    private static int width = 70, height = 50;

    private Thread cockroachThread;
    private String name;
    private int number;
    private Random random;
    private Game game;
    private Image cockroachImg;
    private int coordX, coordY;
    private int finishX;
    private boolean finished = false;

    public Cockroach(String name, int number, int x, int y, int finish, Game game) {
        random = new Random();
        this.cockroachImg = readImage();
        this.name = name;
        this.number = number;
        this.coordX = x;
        this.coordY = y;
        this.finishX = finish;
        this.game = game;
    }

    @Override
    public void run() {
        while (coordX + getCockroachWidth() < finishX) {
            try {
                makeStep();
            } catch (InterruptedException e) {
                Logger.getLogger(this.getClass().getName())
                        .log(Level.SEVERE, null, e);
            }
        }
        ifNewFinisher();
    }

    private void makeStep() throws InterruptedException {
        game.repaint();
        step();
        Thread.sleep(1000);
    }

    private void saveWinner() {
        if (!game.isHaveWinner()) {
            game.setWinner(this);
            game.setHaveWinner(true);
        }
    }

    private void ifNewFinisher() {
        if (coordX + getCockroachWidth() >= finishX) {
            finished = true;
            game.addFinisher(getNumber());
            saveWinner();
            ifAllFinished();
            stopCockroachThread();
        }
    }

    private void ifAllFinished() {
        if (game.getFinished() == game.getNumberOfTracks()) {
            game.delFinished();
            game.addRaceCounter();
            System.out.println(" забег № " + game.getRaceCounter() + " ПОБЕДИЛ " + game.getWinner().getName());
            game.setGameStarted(false);
        }
    }

    private int generateShift() {
        return (int) Math.sqrt(random.nextInt(100)) + random.nextInt(70);
    }

    public void step() {
        coordX = Math.min(coordX + generateShift(), finishX - getCockroachWidth());
    }

    public void step(int c) {
        coordX = Math.min(coordX + c, finishX - getCockroachWidth());
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public String getName() {
        return name;
    }

    public Image readImage() {
        return Toolkit.getDefaultToolkit().createImage(getClass().getResource("/resources/cockroach.gif"));
    }

    public void paint(Graphics g) {
        // перерисовать таракана из его текущих координат
        g.drawImage(cockroachImg, getCoordX(), getCoordY(), null);
    }

    public Thread getCockroachThread() {
        return cockroachThread;
    }

    public void setInStartPosition() {
        this.coordX = 0;
    }

    public void setCockroachThread() {
        this.cockroachThread = new Thread(this);
    }

    public void stopCockroachThread() {
        // остановить поток и обнулить координаты
        setInStartPosition();
        game.repaint();
        setFinished(false);
        cockroachThread.stop();
    }

    public boolean isFinished() {
        return finished;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getCockroachWidth() {
        return width;
    }

    public static int getCockroachHeight() {
        return height;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getNumber() {
        return number;
    }
}
