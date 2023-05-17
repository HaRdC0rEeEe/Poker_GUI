package GUI;

import Enums.ClassificationRank;
import Logic.Player;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class HandPanel extends JPanel implements MouseListener{

    private CardLabel card1;
    private CardLabel card2;
    private Player player;
    private boolean isVisible;
    private boolean isOpponent;

    public HandPanel(Player player) {
        super();

        this.player = player;
        player.setHandPanel(this);
        isVisible = false;
        isOpponent = true;

        addMouseListener(this);

        setVisible(true);
    }

    public HandPanel() {
    }

    public void setIsOpponent(boolean opponent) {
        isOpponent = opponent;
    }

    public void revealCards() {
        card1.unhideCard();
        card2.unhideCard();
    }


    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {

        if(!isOpponent){
            isVisible = !isVisible;

            if(!isVisible)
                revealCards();
            else
                hideCards();
        }

    }

    private void hideCards() {
        card1.hideCard();
        card2.hideCard();

    }

    public void mouseExited(MouseEvent e) {
    }


    public void updatePanel() {

        if(isOpponent)
            setBorder(BorderFactory.createTitledBorder(player.getName() + ", " + player.getRole()));
        else if(player.getcRank() == ClassificationRank.HIGH_COMMUNITY_CARD)
            setBorder(BorderFactory.createTitledBorder(player.getName() + ", " + ClassificationRank.HIGH_CARD + ", " + player.getRole()));
        else if(player.getcRank() == ClassificationRank.LOWERSTRAIGHT)
            setBorder(BorderFactory.createTitledBorder(player.getName() + ", " + ClassificationRank.STRAIGHT + ", " + player.getRole()));
        else
            setBorder(BorderFactory.createTitledBorder(player.getName() + ", " + player.getcRank() + ", " + player.getRole()));

        if(player.isWinner())
            setBorder(BorderFactory.createTitledBorder(null,
                    "Winner! " + player.getName() + ", " + player.getcRank(),
                    TitledBorder.DEFAULT_POSITION,
                    TitledBorder.ABOVE_TOP,
                    new Font("Helvetica", Font.BOLD, 14),
                    new Color(50, 150, 60)
            ));

        layoutComponents();
        revalidate();
        repaint();

    }


    protected void layoutComponents() {

        removeAll();

        card1 = new CardLabel(player.getHand().get(0));
        card2 = new CardLabel(player.getHand().get(1));

        add(card1);
        add(card2);

        card1.setSize(new Dimension(card1.getWidth() - 60, card1.getHeight() - 60));

        if(isOpponent)
            hideCards();

    }
}
