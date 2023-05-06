package GUI;


import Logic.Card;
import Logic.Game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class BoardPanel extends HandPanel{


    private ArrayList<Card> cardsOnTable;

    public BoardPanel() {
        super();
        Dimension dim = getPreferredSize();
        dim.width = 980;
        dim.height = 400;
        setSize(dim);

        cardsOnTable = new ArrayList<>();

        Border innerBorder = BorderFactory.createTitledBorder("Board");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        setLayout(new GridBagLayout());
        layoutComponents();

        //updatePanel();
    }

    public void updatePanel(ArrayList<Card> cardsOnTable) {

        this.cardsOnTable = cardsOnTable;
        /*In summary, the updateUI() method is used to update the look and feel of a component.
        The repaint() and revalidate() methods are used to update the appearance and layout of a component, respectively.*/
        layoutComponents();
        revalidate();
        repaint();

    }


    @Override
    protected void layoutComponents() {

        removeAll();
        cardsOnTable.forEach(card -> add(new CardLabel(card)));

    }


    public void mouseEntered(MouseEvent e) {
    }
}




