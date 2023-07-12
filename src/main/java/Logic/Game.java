package Logic;

import Enums.ClassificationRank;
import Enums.PlayerRole;

import javax.swing.*;
import java.util.*;

public class Game{

    protected static final int PLAYING_LAST = 0;
    private static final int INCREMENT_PLUS_ONE = 1;
    private static final int INCREMENT_PLUS_TWO = 2;
    private static final int DECREMENT_MINUS_ONE = -1;
    private static final int DECREMENT_MINUS_TWO = -2;
    public static int BANK = 0;
    public static int HIGHEST_BET = 0;

    final protected ArrayList<Card> cardsOnTable;
    private final LinkedHashMap<ClassificationRank, Integer> statistics;
    private ArrayList<Player> players;
    private final int chips;
    private final Player mainPlayer;

    public Game(int startingChips, List<Player> players) {

        chips = startingChips;
        this.players = new ArrayList<>(players);
        mainPlayer = players.get(0);
        mainPlayer.setName(nameValidation(true));
        statistics = new LinkedHashMap<>();
        cardsOnTable = new ArrayList<>();
        ///assign dealer role, big and small blinds
        List<Integer> rolledValues = initRandomThrows();
        indexOfDealer = rolledValues.indexOf(Collections.max(rolledValues));

        //each player will recieve same ammount of chips (elimination tournament version 4 now)
        addStartingChips();

        //setup game
        initialiseBeforeMatch(numOfCurrentGame);

    }

    private Deck deck;
    private Player firstPlayer;
    private Player secondPlayer;
    private int numOfCurrentGame = 0, indexOfDealer;

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Player getMainPlayer() {
        return mainPlayer;
    }

    public LinkedHashMap<ClassificationRank, Integer> getStatistics() {
        return statistics;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Card> getCardsOnTable() {
        return cardsOnTable;
    }

    private void initialiseBeforeMatch(int numOfCurrentGame) {
        deck = new Deck();
        resetStatuses(true);

        ///assign dealer role, big and small blinds
        initDealer();

        //after dealer has been set, set order of other players
        //setOtherRoles();

        //TODO fix sort players regarding their roles -
        //sortPlayersByRoles();

        //each player will recieve 2 cards
        round0DrawHands();

        //big and small blinds will place their bets, bets will be doubled every second match
        int smallBlind = (int) ((chips / 20) * ((Math.pow(numOfCurrentGame, 2)) + 1) / 2);
        //round0PayBlinds(smallBlind);


        //main loop of one match, not needed in GUI
        //gameRounds();


    }

    private void resetStatuses(boolean allStatuses) {
        if(!allStatuses){
            for(Player p : players
            ){
                p.resetStatuses();
            }
        } else{
            for(Player p : players
            ){
                p.resetAllStatuses();
            }
        }

    }

    private void initDealer() {
        indexOfDealer++;
        if(indexOfDealer == players.size())
            indexOfDealer = 0;
        Player dealer = players.get(indexOfDealer);
        dealer.setRole(PlayerRole.DEALER);
        dealer.setPlayingOrder(PLAYING_LAST);

        checkDealerOutOfBounds();
    }
    //TODO rewrite this later
    private void checkDealerOutOfBounds() {
        //if at end of circle, set SB to first player
        if(indexOfDealer == players.size() + DECREMENT_MINUS_ONE)
            setSmallAndBigBlinds(DECREMENT_MINUS_ONE, false);

            //if one place before end, SB = end, BB = beginning of circle
        else setSmallAndBigBlinds(indexOfDealer, indexOfDealer == players.size() + DECREMENT_MINUS_TWO);

    }
    //TODO rewrite this later
    private void setSmallAndBigBlinds(int indexOfDealer, boolean isOnePlaceBeforeDealer) {
        firstPlayer = players.get(indexOfDealer + INCREMENT_PLUS_ONE);
        firstPlayer.setRole(PlayerRole.SMALL_BLIND);
        firstPlayer.setPlayingOrder(1);

        if(players.size() != 1){
            if(isOnePlaceBeforeDealer)
                secondPlayer = players.get(0);
            else
                secondPlayer = players.get(indexOfDealer + INCREMENT_PLUS_TWO);

            secondPlayer.setRole(PlayerRole.BIG_BLIND);
            secondPlayer.setPlayingOrder(2);
        }

    }

    ///TODO doesn't work for two players
    private void setOtherRoles() {
        Player temp_player = secondPlayer;
        int i = players.indexOf(secondPlayer);
        int counter = secondPlayer.getPlayngOrder();

        while(temp_player.getRole() != PlayerRole.DEALER){
            i++;
            counter++;
            if(i >= players.size())
                i = 0;
            temp_player = players.get(i);
            temp_player.setPlayingOrder(counter);
        }
    }

    public void drawAndBurn(int numToDraw) {
        deck.burnCard();
        for(int j = 0; j < numToDraw; j++){
            cardsOnTable.add(deck.getTopCard());
        }
    }

    public void addToStatistics(ClassificationRank getcRank) {

        if(!statistics.isEmpty()){
            int count = statistics.get(getcRank);
            statistics.replace(getcRank, count + 1);
        } else{
            for(ClassificationRank rank : ClassificationRank.values()
            ){
                statistics.putIfAbsent(rank, 0);
            }
            addToStatistics(getcRank);
        }


    }

    private List<Integer> initRandomThrows() {
        List<Integer> rolledValues = new ArrayList<>();
        Random rnd = new Random();

        players.forEach(n -> rolledValues.add(rnd.nextInt(Integer.MAX_VALUE) + 1));

        return rolledValues;
    }


    private void sortPlayersByRoles() {
        players.sort(new PlayerComparatorByPlayerRole());
    }

    private void addStartingChips() {

        for(Player player : players){
            player.setChips(chips);
        }
    }

    public void round0DrawHands() {

        players.forEach(Player::Fold);

        for(int i = 0; i < 2; i++){
            for(Player p : players){
                p.drawCard(deck.getTopCard());
            }
        }
    }

    private void round0PayBlinds(int small_blind) {
        firstPlayer.raise(small_blind);
        secondPlayer.raise(small_blind);

    }


    public void removeCardsOnTable() {
        cardsOnTable.clear();
    }

    //used when testing HandEvaluator for multiple games in succession
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void resetStatistics() {
        statistics.clear();
    }

    //simple name input for MainPlayer or second player if that will be the case in future
    //(although it wouldn't make much sense to have two human players locally)
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
