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
        //setOpaque(false);
        setBackground(Color.cyan);
        //setSize(bubble.getStraal() * 2, bubble.getStraal() * 2);
        this.bubble = bubble;
        bubble.addObserver(this);

        Dimension size = getPreferredSize();
        setBounds(bubble.getMiddelpunt().x,
                bubble.getMiddelpunt().y,
                bubble.getStraal()*2, bubble.getStraal()*2);
    }

    public void draw(Graphics g) {
        g.fillOval(0,0,bubble.getStraal() * 2,bubble.getStraal() * 2);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void update(Observable obs, Object obj) {
        setBounds(bubble.getMiddelpunt().x,
                bubble.getMiddelpunt().y,
                bubble.getStraal() * 2, bubble.getStraal() * 2);
    }
}
