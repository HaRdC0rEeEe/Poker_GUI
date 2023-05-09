package GUI;

import Logic.Player;

import javax.swing.*;
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
    public HandPanel() {}

    public CardLabel getCard1() { return card1; }

    public CardLabel getCard2() { return card2; }

    public void setIsOpponent(boolean isOpponent) {
        this.isOpponent = isOpponent;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {

        isVisible = !isVisible;
        if(!isVisible && !isOpponent){
            card1.unhideCard();
            card2.unhideCard();

        } else if (!isOpponent){
            card1.hideCard();
            card2.hideCard();

        }

    }

    public void mouseExited(MouseEvent e) {}


    public void updatePanel() {

        if(isOpponent)
            setBorder(BorderFactory.createTitledBorder(player.getName() + ", " + player.getRole()));
        else
            setBorder(BorderFactory.createTitledBorder(player.getName() + ", " + player.getcRank() + ", " + player.getRole()));

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

        if(isOpponent){
            card1.hideCard();
            card2.hideCard();
        }


    }
}
