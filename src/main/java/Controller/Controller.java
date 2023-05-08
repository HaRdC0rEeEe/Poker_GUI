package Controller;

import GUI.*;
import Logic.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Controller{

    private final Game game;
    private final BoardPanel boardPanel;
    private final ChoicesPanel choicesPanel;


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

        //display cards of current players
        for(HandPanel p: handPanels
            ){
            p.updatePanel();
        }
        //there is a case when MainPlayer can have pair in hand, therefore validate his hand and update panel
        updatePanelAndEvaluate(game.getMainPlayer());
        initListeners();
    }

    private void initListeners() {

        choicesPanel.getRaiseBtn().addActionListener(e->{

            boolean gameNotEnd = true;
                if(game.getCardsOnTable().size() < 3){
                    game.drawAndBurn(3);
                }
                else if(game.getCardsOnTable().size() < 5){
                    game.drawAndBurn(1);
                }
                else{
                    gameNotEnd = false;
                    for(Player currPlayer: game.getPlayers()
                    ){
                        currPlayer.getHandPanel().setIsOpponent(false);
                        currPlayer.getHandPanel().getCard1().unhideCard();
                        currPlayer.getHandPanel().getCard2().unhideCard();

                    }

                    //javax.swing.Timer timer = new javax.swing.Timer(2000, arg0 -> {

                        for(Player currPlayer: game.getPlayers()
                        ){
                            updatePanelAndEvaluate(currPlayer);

                            //System.out.println(currPlayer.getcRank());
                            game.addToStatistics(currPlayer.getcRank());

                        }


                    /*});
                    timer.setRepeats(false);
                    timer.start();*/

                    //System.out.println("________________________");
                    //System.out.println(game.getStatistics().toString());
                }

                if(gameNotEnd)
                    updatePanelAndEvaluate(game.getMainPlayer());
                else
                    System.out.println(determineWinners(game.getPlayers()).toString());

                boardPanel.updatePanel(game.getCardsOnTable());

        });

        //TODO implement fold logic
        choicesPanel.getFoldBtn().addActionListener(e -> {

            game.removeCardsOnTable();

        });
    }

    //TODO replace this method with determineWinners method in test methods
    public List<Player> determineWinners(List<Player> players) {
        //keep adding players with 0 (same level) to list or reset if new 1 will be added
        List<Player> winners = new ArrayList<>();
        winners.add(players.get(0)); // initialize the list with the first player

        for (int i = 1; i < players.size(); i++) {
            int result = new ComparatorByResult().compare(winners.get(0), players.get(i));
            if (result > 0) {
                winners.clear();
                winners.add(players.get(i));
            } else if (result == 0) {
                winners.add(players.get(i));
            }
        }
        winners.forEach(player -> player.setWinner(true));
        return winners;
    }

    private void updatePanelAndEvaluate(Player player) {
        HandEvaluator handEvaluator = new HandEvaluator(player, game.getCardsOnTable());
        handEvaluator.evaulateClassificationRank();
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
