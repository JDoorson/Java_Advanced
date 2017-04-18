import javax.swing.JFrame;
import java.awt.*;

/**
 * Created by Jamal on 13/04/2017.
 */
public class BoomshineApplicatie extends JFrame{
    public BoomshineApplicatie()
    {
        init();
        setVisible(true);
    }

    private void init()
    {
        //TODO: Make dynamic
        setBounds(100,100,500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Boomshine");

        //TODO: Add BoomshineView, add Bubbles to View.
        Bubble b = new Bubble(35, Color.RED, 30, 15);
        add(new BubbleView(b));
        new BubbleController(b, true);
    }

    public static void main(String[] args)
    {
        new BoomshineApplicatie();
    }
}
