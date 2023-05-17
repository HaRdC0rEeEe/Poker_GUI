package GUI;

import Controller.Controller;
import Logic.Game;
import Logic.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;


public class MainFrame extends JFrame{

    private final BoardPanel boardPanel;
    private final ChoicesPanel choicesPanel;
    private final JPanel parentPanel;
    private final ArrayList<HandPanel> playerPanels;

    private final Game game;


    public MainFrame() {
        super("Game of Poker");
        game = new Game(10000, Arrays.asList(new Logic.Player("Peter"), new Logic.Player(), new Logic.Player(), new Logic.Player()));
        //game = new Game(10000, Arrays.asList(new Logic.Player("Pepr"), new Logic.Player("Eve")));

        choicesPanel = new ChoicesPanel();
        boardPanel = new BoardPanel();
        parentPanel = new JPanel(new GridBagLayout());
        parentPanel.setSize(new Dimension(600, 600));

        playerPanels = new ArrayList<>();
        //add panels of opponent hands
        for(Player p : game.getPlayers()){
            playerPanels.add(new HandPanel(p));

        }

        new Controller(game, boardPanel, choicesPanel, playerPanels,new TimeLabel(""));

        layoutFrame();
    }

    private void layoutFrame() {
        //setSize(1200, 800);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        //setMinimumSize(new Dimension(boardPanel.getWidth() - 130, boardPanel.getHeight() + choicesPanel.getHeight() - 150));

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 1;
        gbc.weighty = 1;

        gbc.anchor = GridBagConstraints.WEST;
        for(Player player : game.getPlayers()){
            if(player != game.getMainPlayer()){
                parentPanel.add(player.getHandPanel(), gbc);
                gbc.gridx++;
            }

        }
        parentPanel.add(game.getMainPlayer().getHandPanel(), gbc);


        gbc.gridx = playerPanels.size() / 2;
        gbc.gridy++;

        gbc.anchor = GridBagConstraints.CENTER;
        parentPanel.add(boardPanel, gbc);


        gbc.gridy++;
        parentPanel.add(choicesPanel, gbc);


        add(parentPanel);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


}

