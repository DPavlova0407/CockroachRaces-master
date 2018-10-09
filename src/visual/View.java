package visual;

import core.Game;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 520;

    private Game game;
    private int numberOfTracks;

    private JPanel panel;
    private JScrollPane scrollPaneText;
    private JScrollPane scrollPaneGame;
    private JButton button;
    private JTextField textField;
    private JTextField raceLeader;

    public View(int numberOfTracks) {
        this.numberOfTracks = numberOfTracks;
        createContent();
        createGame();
        createNamePanel();
        addContentAndPack();
    }

    public Game getGame() {
        return game;
    }

    public JButton getButton() {
        return button;
    }

    public void createContent() {
        setTitle("Races");
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        raceLeader = new JTextField("Все тараканы на старте");
        raceLeader.setEditable(false);

        button = new JButton("Start");
    }

    public void createGame() {
        game = new Game(numberOfTracks, raceLeader);
        scrollPaneGame = new JScrollPane(game);
    }

    public void createNamePanel() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (int i = 1; i < numberOfTracks + 1; i++) {
            textField = new JTextField();
            textField.setText("Set name " + i);
            textField.setPreferredSize(new Dimension(100, game.getTrackHeight()));
            panel.add(textField);
        }
        scrollPaneText = new JScrollPane(panel);
    }

    public void addContentAndPack() {
        getContentPane().add(button, BorderLayout.NORTH);
        getContentPane().add(scrollPaneGame, BorderLayout.CENTER);
        getContentPane().add(scrollPaneText, BorderLayout.WEST);
        getContentPane().add(raceLeader, BorderLayout.SOUTH);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
