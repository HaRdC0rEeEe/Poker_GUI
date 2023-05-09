package GUI;

import Logic.Card;
import Main.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class CardLabel extends JLabel{
    private final BufferedImage cardBack;
    private final BufferedImage image;

    public CardLabel(Card card) {
        super();

        //load image depending on Card properties from resources folder
        image = loadCardImageURL(card);

        try{
            cardBack = ImageIO.read(Objects.requireNonNull(Main.class.getClassLoader().getResource("card_back.jpg")));
        } catch(IOException e){
            throw new RuntimeException(e);
        }

        // Calculate the aspect ratio of the loaded image
        assert image != null;
        calcSizeAndSetImage(image);

        setFocusable(false);
        setVisible(true);

    }


    public void hideCard() {
        calcSizeAndSetImage(cardBack);
    }

    public void unhideCard() {
        calcSizeAndSetImage(image);
    }

    private void calcSizeAndSetImage(BufferedImage image) {

        double aspectRatio = (double) image.getWidth() / image.getHeight();

        // Calculate the desired width and height of the scaled image
        int labelWidth = 125; // new width of the JLabel in pixels
        int labelHeight = 181; // new height of the JLabel in pixels

        // Portrait image
        int scaledWidth = (int) (labelHeight * aspectRatio);

        // Create a scaled version of the loaded image with the desired width and height
        Image scaledImage = image.getScaledInstance(scaledWidth, labelHeight, Image.SCALE_SMOOTH);

        // Create a new ImageIcon from the scaled image
        setIcon(new ImageIcon(scaledImage));

        // Set the preferred size of the JLabel to the desired width and height
        setPreferredSize(new Dimension(labelWidth, labelHeight));
    }

    private BufferedImage loadCardImageURL(Card card) {

        try{
            int rankInt = card.getCardValue().getRank();

            String rankStr = String.valueOf(rankInt);

            if(rankInt > 10)
                rankStr = String.valueOf(card.getCardValue()).toLowerCase();


            String pathStr = String.format("%s_of_%s.png", rankStr, card.getCardSymbol().toString().toLowerCase());

            return ImageIO.read(Objects.requireNonNull(Main.class.getClassLoader().getResource(pathStr)));

        } catch(Exception e){
            System.out.println("IMAGE NOT LOADED, load URL: " + e);
        }


        return null;
    }
}
