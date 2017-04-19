import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Created by Jamal on 13/04/2017.
 */
public class BoomshineApplicatie extends JFrame{
    public static final int WINDOW_WIDTH = 500, WINDOW_HEIGHT = 500, FPS = 60;

    public BoomshineApplicatie()
    {
        init();
        setVisible(true);
    }

    private void init()
    {
        //TODO: Make dynamic

        setBounds(100,100, WINDOW_WIDTH,WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Boomshine");

        JPanel view = new BoomshineView();
        add(view);

        ArrayList<Bubble> bubbles = new ArrayList<>();
        bubbles.add(new Bubble(50, Color.RED, 1, 3));

        for(Bubble b : bubbles)
        {
            BubbleView bv = new BubbleView(b);
            view.add(bv);
        }
    }

    public static void main(String[] args)
    {
        new BoomshineApplicatie();
    }
}
