package Controller;

import Enums.ClassificationRank;
import GUI.BoardPanel;
import GUI.ChoicesPanel;
import GUI.HandPanel;
import GUI.TimeLabel;
import Logic.*;

import java.util.ArrayList;
import java.util.List;


public class Controller{

    private final Game game;
    private final BoardPanel boardPanel;
    private final ChoicesPanel choicesPanel;
    private final ArrayList<HandPanel> handPanels;
    private final TimeLabel timeLabel;


    /***
     * @param game Game class consists of logic and game rules
     * @param boardPanel JPanel containing cards on table, pot and timer
     * @param choicesPanel JPanel containing buttons for gameplay of main player
     *
     */
    public Controller(Game game, BoardPanel boardPanel, ChoicesPanel choicesPanel, ArrayList<HandPanel> handPanels, TimeLabel timeLabel) {


        this.boardPanel = boardPanel;
        this.choicesPanel = choicesPanel;
        this.game = game;
        this.handPanels = handPanels;
        //TODO implement timeLabel display, will represent x-seconds limit per round for player using SwingWorker
        this.timeLabel = timeLabel;


        updateEveryPanel();

        //non-opponent players play with cards revealed
        game.getMainPlayer().getHandPanel().setIsOpponent(false);
        game.getMainPlayer().getHandPanel().revealCards();

        //there is a case when MainPlayer can have pair in hand pre-flop, therefore validate his hand and update panel accordingly
        updatePanelAndEvaluate(game.getMainPlayer());
        initListeners();
    }

    //display changes to playerPanels, after players will draw their cards and after card reveal
    private void updateEveryPanel() {
        //display cards of current players
        for(HandPanel p : handPanels){
            p.updatePanel();
        }
    }

    private void initListeners() {

        //TODO change this button to validate choice of player to either check, raise or call
        //at this state this button is used to display my further changes to UI (when cards are dealt to board, when players are revealing their cards and after winner is evaulated)
        choicesPanel.getRaiseBtn().addActionListener(e -> {

            boolean gameNotEnd = true;
            //FLOP
            if(game.getCardsOnTable().size() < 3)
                game.drawAndBurn(3);
                //pre-river and river
            else if(game.getCardsOnTable().size() < 5)
                game.drawAndBurn(1);
                //evaluate step
            else
                gameNotEnd = false;

            if(gameNotEnd){
                updatePanelAndEvaluate(game.getMainPlayer());
                boardPanel.updatePanel(game.getCardsOnTable());
            } else{
                evaluatePlayers();
                updateEveryPanel();
            }
        });

        //TODO implement fold logic, tests for multiple games present here
        choicesPanel.getFoldBtn().addActionListener(e -> {
            new Thread(() -> {
                //executeTests(false, 1000);
                executeTests(false, 100);
            }).start();


        });
    }

    private void executeTests(boolean onePlayer, int numOfTests) {

        int p1Won = 0;
        int p2Won = 0;
        int tie = 0;

        for(int i = 0; i < numOfTests; i++){
            game.setDeck(new Deck());
            game.removeCardsOnTable();

            if(onePlayer){
                game.setPlayers(new ArrayList<>(List.of(new Player("TP"))));
                game.getPlayers().forEach(player -> player.setHandPanel(new HandPanel(player)));
            }


            game.round0DrawHands();

            game.drawAndBurn(3);
            game.drawAndBurn(1);
            game.drawAndBurn(1);
            evaluatePlayers();

            if(!onePlayer){
                if(Utils.winners.size() == 2)
                    tie++;
                else if(Utils.winners.contains(game.getMainPlayer()))
                    p1Won++;
                else
                    p2Won++;
            }

        }

        if(!onePlayer)
            System.out.printf("[p1: %d, p2: %d, tie %d]\n", p1Won, p2Won, tie);
        System.out.println(game.getStatistics());


        //Print statistical results in percentage values
        String tmp = "";
        for(ClassificationRank cv : game.getStatistics().keySet()){

            double percentage = (game.getStatistics().get(cv) * 100.0 / numOfTests) ;

            String decreaseDecimals;
            if(cv.getValue() >= ClassificationRank.FOURS.getValue())
                decreaseDecimals = String.format("%.4f", percentage);
            else
                decreaseDecimals = String.format("%.2f", percentage);

            tmp += cv + ": " + decreaseDecimals + "%, ";
        }

        System.out.println(tmp);

    }

    private void evaluatePlayers() {

        for(Player player : game.getPlayers()){
            //non-opponent players will display cards
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
        //evaluate and update ClassificationRank variable of player
        ev.evaluateClassificationRank();
        player.getHandPanel().updatePanel();
    }
}
