package Logic;

import GUI.HandPanel;

import java.util.*;

import static Logic.Game.HIGHEST_BET;


public class Player{

    public static final Scanner sc = new Scanner(System.in);
    private static boolean has_not_yet_been_raised = false;
    private final String name;
    private final List<Card> hand = new ArrayList<>();
    private BETTING_STATUS bettingStatus;
    private int chips;
    private Enum<?> role;
    private int playing_order;
    private int bet, totalBet;
    private ClassificationRank cRank;
    private Card threeKind;
    private Card fourKind;
    private HandPanel handPanel;
    private Card pair;
    private Card highestStraight;

    public boolean isWinner() {
        return isWinner;
    }

    private boolean isWinner = false;

    public Player() {
        Random r = new Random();
        String[] pool_of_names = new String[]{"Amanda", "Peter", "Debilko", "Frajer", "Bruhhh", "Sajmon", "Arnošt", "Nicole", "Andyyy", "Sajmon", "Ferkoo", "Denisa"};
        int x = r.nextInt(pool_of_names.length);

        name = pool_of_names[x];
        cRank = ClassificationRank.HIGH_CARD;
    }

    public Player(String name) {
        this.name = name;
        cRank = ClassificationRank.HIGH_CARD;
    }

    public Card getHighestStraight() {
        return highestStraight;
    }

    public BETTING_STATUS getBettingStatus() {
        return bettingStatus;
    }

    public void setBettingStatus(BETTING_STATUS bettingStatus) {
        this.bettingStatus = bettingStatus;
    }

    public List<Card> getHand() {
        return hand;
    }

    public ClassificationRank getcRank() {
        return this.cRank;
    }

    public int getRank() { return cRank.getValue();  }

    protected Card getPair() {
        return pair;
    }

    protected void setPair(Card winning_card) {
        pair = winning_card;
    }

    protected void setClassificationRank(ClassificationRank cRank) {
        this.cRank = cRank;
    }

    public void setWinner(boolean winner) {  isWinner = winner;  }

    protected void drawCard(Card drawnCard) {
        hand.add(drawnCard);
    }

    public void Fold() {
        if(!hand.isEmpty()){
            hand.remove(0);
            hand.remove(0);
        }

    }

    public void call() {
        bet = HIGHEST_BET - totalBet;
        totalBet += bet;

        this.chips -= bet;
        Game.BANK += bet;
    }

    public void raise(int raisedBy) {

        HIGHEST_BET += raisedBy;
        call();

        setStatus(BETTING_STATUS.RAISED);
        has_not_yet_been_raised = false;
    }

    protected void resetStatuses() {
        has_not_yet_been_raised = false;
        HIGHEST_BET = 0;
        bet = 0;
        totalBet = 0;
        bettingStatus = BETTING_STATUS.STILL_BETTING;

    }

    protected void resetAllStatuses() {
        resetStatuses();
        cRank = ClassificationRank.HIGH_CARD;
        role = player_role.NONE;
        playing_order = 0;
    }

    public int getChips() {
        return chips;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }

    public Enum<?> getRole() {
        return role;
    }

    public void setRole(Enum<?> role) {
        this.role = role;
    }

    public void print_actions() {
        if(has_not_yet_been_raised || totalBet == HIGHEST_BET){
            System.out.println("[1] - > CHECK");
        } else{
            System.out.printf("[1] - > CALL (%d)\n", HIGHEST_BET - totalBet);
        }
        System.out.println("[2] - > RAISE");
        System.out.println("[3] - > FOLD");
        System.out.println("[4] - > HAND");
    }

    public void choose_action(int chosen_action) {

        switch(chosen_action){
            case 1:
                if(has_not_yet_been_raised || totalBet == HIGHEST_BET){
                    System.out.printf("%s has checked.\n", name);
                    setStatus(BETTING_STATUS.CHECKED);
                } else{
                    call();
                    setStatus(BETTING_STATUS.CALLED);
                    System.out.printf("%s has called %d chips.\n", name, bet);
                }
                break;

            case 2:
                int raise = check_if_bet_is_okay();
                raise(raise);
                System.out.printf("%s has raised by %d chips.\n", name, HIGHEST_BET);
                break;

            case 3:
                this.Fold();
                setStatus(BETTING_STATUS.FOLDED);
                break;

            case 4:
                System.out.println(getHand());
                print_actions();
                choose_action(this.get_action());
                break;
        }
    }

    protected int get_action() {

        System.out.println("Choose your action: ");
        int action = try_catch_input_mismatch_exception();

        if(action > 4 || action <= 0){
            System.out.println("Incorrect action number");
            sc.next();
            return get_action();
        }
        return action;
    }

    protected int try_catch_input_mismatch_exception() {
        int n;
        try{
            n = sc.nextInt();
        } catch(Exception e){
            //When a scanner throws an InputMismatchException, the scanner will not pass the token that caused the exception.
            // we must use sc.next() to discard token which caused exception
            System.out.println("Incorrect number, try again");
            sc.next();
            return try_catch_input_mismatch_exception();
        }

        return n;
    }

    protected int check_if_bet_is_okay() {

        System.out.println("Enter amount of your bet: ");
        int tempBet = try_catch_input_mismatch_exception();

        //cant bet more than you have, hence ALL IN
        if(tempBet >= this.getChips()){
            System.out.printf("%s has went ALL IN\n", name);
            tempBet = this.getChips() - HIGHEST_BET;
        } else if(tempBet <= 0){
            System.out.println("Incorrect number");
            sc.next();
            return check_if_bet_is_okay();
        }
        return tempBet;

    }

    private void setStatus(BETTING_STATUS bettingStatus) {
        this.bettingStatus = bettingStatus;
    }

    @Override
    public String toString() {

        return String.format("%s,\tchips: %d , role: %s", name, chips, role);

    }

    protected String toString(boolean withCards) {
        if(withCards)
            return String.format("%s,\tchips: %d \tcards: %s, role: %s", name, chips, hand, role);
        else return this.toString();
    }

    protected String toString(boolean withCards, boolean withResult) {
        if(withCards && withResult)
            return String.format("%s, \tcards: %s\t->\tRESULT: %s", name, hand, cRank);
        else return this.toString(withCards);
    }

    protected void setPlaying_order(int newVal) {

        this.playing_order = newVal;
    }

    protected int getPlayng_order() {

        return this.playing_order;
    }

    public void addChips(int chips) {
        this.chips += chips;
    }

    public Object getName() {
        return this.name;
    }

    public HandPanel getHandPanel() {
        return handPanel;
    }

    public void setHandPanel(HandPanel handPanel) {
        this.handPanel = handPanel;
    }

    public Card getThreeKind() {
        return threeKind;
    }

    public void setThreeKind(Card threeKind) {
        this.threeKind = threeKind;
    }

    public Card getFourKind() {
        return fourKind;
    }

    public void setFourKind(Card fourKind) {
        this.fourKind = fourKind;
    }

    public int getHighestCard() {
        return Math.max(hand.get(0).getRank(), hand.get(1).getRank());
    }

    public void setBiggestStraight(Card card) {
        highestStraight = card;
    }


    protected enum player_role{
        DEALER, BIG_BLIND, SMALL_BLIND, NONE

    }

    protected enum BETTING_STATUS{
        CALLED,
        STILL_BETTING,
        ALL_IN,
        FOLDED,
        CHECKED,
        RAISED
    }


}

class PlayerComparatorByPlayerRole implements Comparator<Player>{
    @Override
    public int compare(Player o1, Player o2) {
        return o1.getPlayng_order() - o2.getPlayng_order();
    }
}

