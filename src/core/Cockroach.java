package core;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cockroach extends JPanel implements Runnable {
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
        while (coordX < finishX) {
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
        //print();//---
        Thread.sleep(1000);
    }

    private void saveWinner() {
        if (game.getFinished() == 1)
            game.setWinner(this);
    }

    private void ifNewFinisher() {
        if (coordX >= finishX) {
            System.out.println("таракан " + getName() + " финишировал - x = " + getCoordX() + " finish = " + getFinishX());//---
            finished = true;
            game.addFinisher();
            saveWinner();
            ifAllFinished();
            stopCockroachThread();
        }
    }

    private void ifAllFinished() {
        if (game.getFinished() == game.getNumberOfTracks()) {
            game.delFinished();
            System.out.println("все тараканы финишировали");
            game.addRaceCounter();
            System.out.println(game.getRaceCounter() + " забег, таракан " + game.getWinner().getName() + " ПОБЕДИЛ");
            // восстановить изображение
            game.repaint();
            game.setGameStarted(false);
        }
    }

    private int generateShift() {
         return (int) Math.sqrt(random.nextInt(100));
        //return random.nextInt(100);
    }

    public void step() {
        coordX += generateShift();
    }
    public void step(int c) {
        coordX += c;
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
        return Toolkit.getDefaultToolkit().createImage(getClass().getResource("/resources/cockroach.jpg"));
    }

    public void paint(Graphics g) {
        // перерисовать таракана из его текущих координат
        g.drawImage(cockroachImg, getCoordX(), getCoordY(), null);
    }

    private void print() {
        System.out.println("( " + name + " " + coordX + " )");
    }

    public Thread getCockroachThread() {
        return cockroachThread;
    }

    public int getFinishX() {
        return finishX;
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
        cockroachThread.stop();
    }

    public boolean isFinished() {
        return finished;
    }

    public void setName(String name) {
        this.name = name;
    }
}
