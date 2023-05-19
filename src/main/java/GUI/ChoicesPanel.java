package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


public class ChoicesPanel extends JPanel{

    private final TimeLabel timeLabel;
    private final JLabel chipsLabel;

    private final JButton raiseBtn;

    public TimeLabel getTimeLabel() {
        return timeLabel;
    }

    public JLabel getChipsLabel() {
        return chipsLabel;
    }

    public JButton getRaiseBtn() {
        return raiseBtn;
    }

    public JButton getFoldBtn() {
        return foldBtn;
    }

    public GridBagConstraints getGc() {
        return gc;
    }

    private final JButton foldBtn;

    private final GridBagConstraints gc = new GridBagConstraints();


    public ChoicesPanel() {
        Dimension dim = getPreferredSize();
        dim.width = 780;
        dim.height = 400;
        setSize(dim);

        raiseBtn = new JButton("Draw card/s");
        foldBtn = new JButton("Execute tests");
        chipsLabel = new JLabel();
        timeLabel = new TimeLabel("");


        Dimension btnSize = new Dimension(300, 50);
        raiseBtn.setPreferredSize(btnSize);
        chipsLabel.setPreferredSize(btnSize);
        timeLabel.setPreferredSize(btnSize);
        foldBtn.setPreferredSize(new Dimension((int) btnSize.getHeight() * 3, (int) btnSize.getHeight()));
        //throwIntoStrongCombo.setPreferredSize(new Dimension((int) btnSize.getHeight() * 6, (int) btnSize.getHeight()));

        raiseBtn.setEnabled(true);
        chipsLabel.setEnabled(true);
        timeLabel.setEnabled(true);
        foldBtn.setEnabled(true);


        Border innerBorder = BorderFactory.createBevelBorder(1);
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        setLayout(new GridBagLayout());

        layoutComponents();

    }



    private void layoutComponents() {

        gc.fill = GridBagConstraints.NONE;
        gc.gridx = 0;
        gc.gridy = 0;

        gc.weightx = 1;
        gc.weighty = 1;


        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(5, 10, 5, 5);
        add(chipsLabel, gc);

        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx++;
        add(timeLabel, gc);


        gc.insets = new Insets(5, 5, 5, 5);
        gc.anchor = GridBagConstraints.CENTER;
        gc.gridy++;
        gc.gridx--;
        add(raiseBtn, gc);

        gc.weightx = 0.1;
        gc.gridx++;
        add(foldBtn, gc);


    }


}
