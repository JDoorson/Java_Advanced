import javax.swing.JPanel;
import java.awt.*;
import java.util.Observer;
import java.util.Observable;

/**
 * Created by Jamal on 13/04/2017.
 */
public class BubbleView extends JPanel implements Observer {
    private Bubble bubble;

    public BubbleView(Bubble bubble) {
        setOpaque(false);
        this.bubble = bubble;
        bubble.addObserver(this);

        Dimension size = getPreferredSize();
        setBounds(bubble.getMiddelpunt().x - bubble.getStraal(),
                bubble.getMiddelpunt().y - bubble.getStraal(),
                bubble.getDiameter(), bubble.getDiameter());
    }

    public void draw(Graphics g) {
        g.setColor(bubble.getKleur());
        g.fillOval(0,0,bubble.getDiameter(),bubble.getDiameter());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void update(Observable obs, Object obj) {
        setBounds(bubble.getMiddelpunt().x - bubble.getStraal(),
                bubble.getMiddelpunt().y - bubble.getStraal(),
                bubble.getDiameter(), bubble.getDiameter());
    }
}
