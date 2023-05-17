package Controller;

import GUI.BoardPanel;
import GUI.ChoicesPanel;
import GUI.HandPanel;
import GUI.TimeLabel;
import Logic.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class Controller{

    private final Game game;
    private final BoardPanel boardPanel;
    private final ChoicesPanel choicesPanel;
    private final ArrayList<HandPanel> handPanels;


    /***
     * @param game Game class consists of logic and game rules
     * @param boardPanel JPanel containing cards on table, pot and timer
     * @param choicesPanel JPanel containing buttons for gameplay of main player
     *
     */
    public Controller(Game game, BoardPanel boardPanel, ChoicesPanel choicesPanel, ArrayList<HandPanel> handPanels, TimeLabel timeLabel) {

        //player1 = new Player(nameValidation(true));

        this.boardPanel = boardPanel;
        this.choicesPanel = choicesPanel;
        this.game = game;
        this.handPanels = handPanels;

        updateEveryPanel();

        game.getMainPlayer().getHandPanel().setIsOpponent(false);
        game.getMainPlayer().getHandPanel().revealCards();

        //there is a case when MainPlayer can have pair in hand, therefore validate his hand and update panel
        updatePanelAndEvaluate(game.getMainPlayer());
        initListeners();
    }

    private void updateEveryPanel() {
        //display cards of current players
        for(HandPanel p : handPanels){
            p.updatePanel();
        }
    }

    private void initListeners() {

        choicesPanel.getRaiseBtn().addActionListener(e -> {

            boolean gameNotEnd = true;
            if(game.getCardsOnTable().size() < 3){
                game.drawAndBurn(3);
            } else if(game.getCardsOnTable().size() < 5){
                game.drawAndBurn(1);
            } else
                gameNotEnd = false;

            if(gameNotEnd)
                updatePanelAndEvaluate(game.getMainPlayer());
            else{
                evaulatePlayers();
                updateEveryPanel();
            }

            boardPanel.updatePanel(game.getCardsOnTable());

        });

        //TODO implement fold logic, TESTS here
        choicesPanel.getFoldBtn().addActionListener(e -> {

            executeTests(false, 100000);

        });
    }

    private void executeTests(boolean onePlayer, int numOfTests) {

        int p1Won = 0;
        int p2Won = 0;
        int tie = 0;

        for(int i = 0; i < numOfTests; i++){
            if(!onePlayer){
                game.setDeck(new Deck());
                game.removeCardsOnTable();

                game.round0DrawHands();

                game.drawAndBurn(3);
                game.drawAndBurn(1);
                game.drawAndBurn(1);

                evaulatePlayers();

                if(Utils.winners.size() == 2)
                    tie++;
                else if(Utils.winners.contains(game.getMainPlayer()))
                    p1Won++;
                else
                    p2Won++;

            } else{
                game.setPlayers(new ArrayList<>(List.of(new Player("TP"))));
                game.setDeck(new Deck());
                game.removeCardsOnTable();

                game.round0DrawHands();

                game.drawAndBurn(3);
                game.drawAndBurn(1);
                game.drawAndBurn(1);


                evaulatePlayers();

            }
        }

        System.out.printf("[p1: %d, p2: %d, tie %d]\n", p1Won, p2Won, tie);
        System.out.println(game.getStatistics());

    }

    private void evaulatePlayers() {

        for(Player player : game.getPlayers()){
            player.getHandPanel().setIsOpponent(false);
            updatePanelAndEvaluate(player);
            player.getHandPanel().revealCards();

        }

        Utils.setWinners(game.getPlayers());

        for(Player winner : Utils.winners){
            game.addToStatistics(winner.getcRank());
        }
    }


    private void updatePanelAndEvaluate(Player player) {
        HandEvaluator ev = new HandEvaluator(player, game.getCardsOnTable());
        ev.evaulateClassificationRank();
        player.getHandPanel().updatePanel();
    }

    //simple name input for MainPlayer or second player if that will be the case in future (although it wouldn't make much sense to have two human players locally)
    private String nameValidation(boolean isFirstPlayer) {
        String message = "Please input nickname for %%% player";

        message = isFirstPlayer ? message.replace("%%%", "first") : message.replace("%%%", "second");

        String nickname = JOptionPane.showInputDialog(null, message, "", JOptionPane.PLAIN_MESSAGE);
        while(nickname == null){
            nickname = JOptionPane.showInputDialog(null, "Incorrect input. Try again.", "", JOptionPane.PLAIN_MESSAGE);
        }

        return nickname;
    }


}
