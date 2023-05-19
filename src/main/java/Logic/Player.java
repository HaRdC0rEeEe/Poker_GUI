package Logic;

import Enums.BettingStatus;
import Enums.ClassificationRank;
import Enums.PlayerRole;
import GUI.HandPanel;

import java.util.*;

import static Logic.Game.HIGHEST_BET;


public class Player{

    public static final Scanner sc = new Scanner(System.in);
    private static boolean hasNotBeenRaised = false;
    private String name;
    private final List<Card> hand;
    private ArrayList<Integer> flush;
    private BettingStatus bettingStatus;
    private int chips;
    private PlayerRole role;
    private int playingOrder;
    private int bet, totalBet;
    private ClassificationRank cRank;
    private Card threeKind;
    private Card fourKind;
    private HandPanel handPanel;
    private Card pair;
    private Card secondPair;
    private Card highestStraight;
    private boolean isWinner = false;
    public Player() {
        Random r = new Random();
        String[] poolOfNames = new String[]{"Amanda", "Peter", "Simon", "Arnošt", "Nicole", "Michael", "Valeria", "David"};
        int x = r.nextInt(poolOfNames.length);

        hand = new ArrayList<>();
        name = poolOfNames[x];
        cRank = ClassificationRank.HIGH_CARD;
    }

    public Player(String name) {
        this.name = name;
        cRank = ClassificationRank.HIGH_CARD;
        hand = new ArrayList<>();
    }

    public ArrayList<Integer> getFlush() {
        return flush;
    }

    public void setFlush(ArrayList<Integer> flush) {
        this.flush = flush;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

    public Card getHighestStraight() {
        return highestStraight;
    }

    public BettingStatus getBettingStatus() {
        return bettingStatus;
    }

    public void setBettingStatus(BettingStatus bettingStatus) {
        this.bettingStatus = bettingStatus;
    }

    public List<Card> getHand() {
        return hand;
    }

    public ClassificationRank getcRank() {
        return this.cRank;
    }

    public int getRank() {
        return cRank.getValue();
    }

    protected Card getPair() {
        return pair;
    }

    protected void setPair(Card winningCard) {
        pair = winningCard;
    }

    protected void setClassificationRank(ClassificationRank cRank) {
        this.cRank = cRank;
    }

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

        setStatus(BettingStatus.RAISED);
        hasNotBeenRaised = false;
    }

    protected void resetStatuses() {
        hasNotBeenRaised = false;
        HIGHEST_BET = 0;
        bet = 0;
        totalBet = 0;
        bettingStatus = BettingStatus.STILL_BETTING;

    }

    protected void resetAllStatuses() {
        resetStatuses();
        role = PlayerRole.NONE;
        playingOrder = 0;
    }

    public int getChips() {
        return chips;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }

    public PlayerRole getRole() {
        return role;
    }

    public void setRole(PlayerRole role) {
        this.role = role;
    }

    private void setStatus(BettingStatus bettingStatus) {
        this.bettingStatus = bettingStatus;
    }

    @Override
    public String toString() {
        return String.format("%s [%s:%s] %s", name, hand.get(0), hand.get(1), cRank);
        //return String.format("%s [%s:%s] %s", name, hand.get(0).getRank(), hand.get(1).getRank(), cRank);

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

    protected void setPlayingOrder(int newVal) {
        playingOrder = newVal;
    }

    protected int getPlayngOrder() {
        return playingOrder;
    }

    public void addChips(int chips) {
        this.chips += chips;
    }

    public String getName() {
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

    public void setBiggestStraight(Card card) {
        highestStraight = card;
    }

    public Card getSecondPair() {
        return secondPair;
    }

    public void setSecondPair(Card secondPair) {
        this.secondPair = secondPair;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class PlayerComparatorByPlayerRole implements Comparator<Player>{
    @Override
    public int compare(Player p1, Player p2) {
        return p1.getPlayngOrder() - p2.getPlayngOrder();
    }
}

