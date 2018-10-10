package core;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game extends JPanel implements Runnable {
    private static final int WIDTH = 800;
    private static final int finishX = WIDTH;
    private static int HEIGHT = 434;

    private JTextField raceLeader;
    private JPanel textPanel;
    private Image finishImg;

    private List<Cockroach> cockroaches;
    private Cockroach winner;
    private int numberOfTracks;
    private int trackHeight;
    private int finished = 0;
    private int raceCounter = 0;

    private boolean gameStarted = false;

    public Game(int numberOfTracks, JTextField leader, JPanel textPanel) {
        initGame(numberOfTracks, leader, textPanel);
        calculateHeight();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        createCockroaches();
    }

    public void paint(Graphics graphics) {
        paintInit(graphics);
        drawGameField(graphics);
    }

    @Override
    public void run() {
        for (int i = 0; i < cockroaches.size(); i++) {
            setGameStarted(true);
            // запретить редактирование полей
            cockroaches.get(i).setCockroachThread();
            cockroaches.get(i).getCockroachThread().start();
        }
    }

    public int getTrackHeight() {
        return trackHeight;
    }

    public void addFinisher() {
        finished += 1;
    }

    public void delFinished() {
        this.finished = 0;
    }

    public int getFinished() {
        return finished;
    }

    public int getNumberOfTracks() {
        return numberOfTracks;
    }

    public List<Cockroach> getCockroaches() {
        return cockroaches;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
        setTextFieldsEditable(!gameStarted);
    }

    public void setTextFieldsEditable(boolean editable){
        for (int i = 0; i < getNumberOfTracks(); i++){
            JTextField field1 = (JTextField) getTextPanel().getComponent(i);
            field1.setEditable(editable);
            // при начале забега обновим имена тараканов из текстовых полей
            if (!editable)
                getCockroaches().get(i).setName(field1.getText());
        }
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void createCockroaches() {
        cockroaches = new ArrayList<>();
        for (int i = 0; i < numberOfTracks; i++) {
            //JTextField field1 = (JTextField) getTextPanel().getComponent(i);
            Cockroach c = new Cockroach(((JTextField) getTextPanel().getComponent(i)).getText(), i, 0, i * trackHeight + trackHeight/2 - 25, finishX, this);
            cockroaches.add(c);
        }
    }

    public void checkLeader() {
        if (getFinished() == numberOfTracks || !isGameStarted())
            this.raceLeader.setText("Все тараканы на старте");
        else {
            Cockroach leader = cockroaches.get(0);
            for (Cockroach c : cockroaches) {
                if (!c.isFinished() && c.getCoordX() > leader.getCoordX())
                    leader = c;
            }
            this.raceLeader.setText("Лидирует " + leader.getName());
        }
    }

    public void initGame(int numberOfTracks, JTextField leader, JPanel textPanel) {
        this.numberOfTracks = numberOfTracks;
        this.raceLeader = leader;
        this.textPanel = textPanel;
        finishImg = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/resources/finish.gif"));
    }

    public void calculateHeight() {
        int h = HEIGHT / numberOfTracks;
        trackHeight = Math.max(h, 50);
        HEIGHT = trackHeight * numberOfTracks;
    }

    public void paintInit(Graphics graphics) {
        checkLeader();
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        Font currentFont = graphics.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.4F);
        graphics.setFont(newFont);
    }

    public void drawGameField(Graphics graphics) {
        for (int i = 0; i < numberOfTracks; i++) {
            // отрисовка трека
            drawLine(graphics, i);
            // отрисовка таракана
            cockroaches.get(i).paint(graphics);
            // отрисовка финишной линии
            drawFinish(graphics, i);
        }
    }

    public void drawLine(Graphics graphics, int i) {
        if (i % 2 == 0)
            graphics.setColor(Color.lightGray);
        else
            graphics.setColor(Color.white);
        graphics.fillRect(0, i * trackHeight, WIDTH, trackHeight);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(0, i * trackHeight, WIDTH, trackHeight);
        graphics.drawString(Integer.toString(i + 1), 10, i * trackHeight + trackHeight / 2 + 10);
    }

    public void drawFinish(Graphics graphics, int i) {
        graphics.setColor(Color.WHITE);
        graphics.fillRect(WIDTH + 1, i * trackHeight, 100, trackHeight);
        graphics.drawImage(finishImg, WIDTH + 1, i * trackHeight + trackHeight/2 - 25, this);
    }

    public void setWinner(Cockroach winner) {
        this.winner = winner;
    }

    public Cockroach getWinner() {
        return winner;
    }

    public int getRaceCounter() {
        return raceCounter;
    }

    public void addRaceCounter() {
        this.raceCounter += 1;
    }

    public void delRaceCounter() {
        this.raceCounter = 0;
    }

    public JPanel getTextPanel() {
        return textPanel;
    }

    /*public void checkNames(){
        for (core.Cockroach c : cockroaches) {

        }
    }*/
}
