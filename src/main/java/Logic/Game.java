package Logic;

import java.util.*;

public class Game{

    protected static final int PLAYING_LAST = 0;
    private static final int INCREMENT_PLUS_ONE = 1;
    private static final int INCREMENT_PLUS_TWO = 2;
    private static final int DECREMENT_MINUS_ONE = -1;
    private static final int DECREMENT_MINUS_TWO = -2;
    public static int BANK = 0;
    public static int HIGHEST_BET = 0;
    private final ArrayList<Player> players;
    private final LinkedHashMap<ClassificationRank, Integer> statistics = new LinkedHashMap<>();
    private final int chips;
    private final Player mainPlayer;
    private final ArrayList<Card> cardsOnTable;
    private Deck deck;
    private Player firstPlayer;
    private Player secondPlayer;
    private int numOfCurrentGame = 0, indexOfDealer;

    public Game(int startingChips, List<Player> players) {

        chips = startingChips;
        this.players = new ArrayList<>(players);
        cardsOnTable = new ArrayList<>();
        mainPlayer = players.get(0);

        ///assign dealer role, big and small blinds
        List<Integer> rolledValues = initRandomThrows();
        indexOfDealer = rolledValues.indexOf(Collections.max(rolledValues));

        //each player will recieve same ammount of chips (elimination tournament version 4 now)
        addStartingChips();

        //setup game
        initialiseBeforeMatch(numOfCurrentGame);

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
        dealer.setRole(Player.player_role.DEALER);
        dealer.setPlaying_order(PLAYING_LAST);

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
        firstPlayer.setRole(Player.player_role.SMALL_BLIND);
        firstPlayer.setPlaying_order(1);

        if(players.size() != 1){
            if(isOnePlaceBeforeDealer)
                secondPlayer = players.get(0);
            else
                secondPlayer = players.get(indexOfDealer + INCREMENT_PLUS_TWO);

            secondPlayer.setRole(Player.player_role.BIG_BLIND);
            secondPlayer.setPlaying_order(2);
        }

    }

    ///TODO doesn't work for two players
    private void setOtherRoles() {
        Player temp_player = secondPlayer;
        int i = players.indexOf(secondPlayer);
        int counter = secondPlayer.getPlayng_order();

        while(temp_player.getRole() != Player.player_role.DEALER){
            i++;
            counter++;
            if(i >= players.size())
                i = 0;
            temp_player = players.get(i);
            temp_player.setPlaying_order(counter);
        }
    }

    private boolean hasRoundEnded() {
        int numOfPlayersChecked = 0;
        int numOfPlayersCalled = 0;

        for(Player p : players
        ){
            if(p.getBettingStatus() == Player.BETTING_STATUS.STILL_BETTING)
                return false;
            if(p.getBettingStatus() == Player.BETTING_STATUS.CHECKED)
                numOfPlayersChecked++;
            if(p.getBettingStatus() == Player.BETTING_STATUS.CALLED)
                numOfPlayersCalled++;
        }

        if(numOfPlayersCalled == players.size() - 1 && numOfPlayersChecked == 1){
            System.out.println("Everyone has called last raised bet.");
            return true;
        }

        if(numOfPlayersChecked == players.size()){
            System.out.println("Everyone has checked.");
            return true;
        }

        if(numOfPlayersCalled == players.size()){
            System.out.println("Everyone has called.");
            return true;
        }
        return false;
    }

    public void drawAndBurn(int numToDraw) {
        deck.burnCard();
        for(int j = 0; j < numToDraw; j++){
            cardsOnTable.add(deck.getTopCard());

        }
        ///TODO TEST COMBINATIONS
        /*cardsOnTable.add(new Card(CardEnums.cValue.TEN, CardEnums.cSymbol.HEARTS));
        cardsOnTable.add(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS));
        cardsOnTable.add(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS));
        cardsOnTable.add(new Card(CardEnums.cValue.KING, CardEnums.cSymbol.HEARTS));
        cardsOnTable.add(new Card(CardEnums.cValue.ACE, CardEnums.cSymbol.HEARTS));
        players.get(0).Fold();
        players.get(0).drawCard(new Card(CardEnums.cValue.QUEEN, CardEnums.cSymbol.HEARTS));
        players.get(0).drawCard(new Card(CardEnums.cValue.JACK, CardEnums.cSymbol.HEARTS));*/

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

    private void endGameStartOver() {
        //remove every player with no chips
        players.removeIf(p -> p.getChips() == 0);
        if(players.size() == 1){
            System.out.printf("Winner of whole match is %s!", players.get(0).getName());
            System.exit(0);
        }
        numOfCurrentGame++;

        //initialiseBeforeMatch(numOfCurrentGame);
    }

    private List<Integer> initRandomThrows() {
        List<Integer> rolledValues = new ArrayList<>();
        Random rnd = new Random();

        players.forEach(n -> rolledValues.add(rnd.nextInt(Integer.MAX_VALUE) + 1));

        return rolledValues;
    }

    //TODO rewrite or remove this later, not needed in GUI
    private void gameRounds() {
        printPlayers(false, false);
        System.out.println();

        int indexOfCurrentPlayer = players.indexOf(secondPlayer) + 1;

        while(cardsOnTable.size() != 5 & players.size() != 1){

            while(players.size() != 1){

                if(indexOfCurrentPlayer >= players.size())
                    indexOfCurrentPlayer = 0;

                Player current_player = players.get(indexOfCurrentPlayer);
                //playRound(current_player);

                if(current_player.getBettingStatus() == Player.BETTING_STATUS.FOLDED){
                    players.remove(current_player);
                    indexOfCurrentPlayer--;
                } else if(current_player.getBettingStatus() == Player.BETTING_STATUS.RAISED){
                    for(Player p : players)
                        p.setBettingStatus(Player.BETTING_STATUS.STILL_BETTING);

                    current_player.setBettingStatus(Player.BETTING_STATUS.RAISED);
                }
                if(hasRoundEnded()){
                    resetStatuses(false);
                    break;
                }

                indexOfCurrentPlayer++;

            }

            printPlayers(false, false);
            System.out.println();

            //TEST
            if(players.size() == 1)
                break;


            indexOfCurrentPlayer = players.indexOf(secondPlayer);
        }
        System.out.println("Cards on table: " + cardsOnTable);
        System.out.println();

        //evaulateWinner();
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

    private void printPlayers(boolean withCards, boolean withResult) {

        for(Player p : players
        ){
            System.out.println(p.toString(withCards, withResult));
        }
    }

    public void removeCardsOnTable() {
        cardsOnTable.clear();
    }

    //used when testing HandEvaluator for multiple games in succession
    public void setDeck(Deck deck) {
        this.deck = deck;
    }
}
