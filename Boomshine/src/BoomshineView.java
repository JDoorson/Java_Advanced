import javax.swing.JPanel;
import java.awt.Color;
import java.util.Observer;
import java.util.Observable;
import java.util.Random;

/**
 * Created by Jamal on 13/04/2017.
 */
public class BoomshineView extends JPanel implements Observer{
    public static int VIEW_WIDTH, VIEW_HEIGHT;

    public BoomshineView()
    {
        setLayout(null);

        VIEW_WIDTH = BoomshineApplicatie.WINDOW_WIDTH;
        VIEW_HEIGHT = BoomshineApplicatie.WINDOW_HEIGHT;

        setBounds(0,0,VIEW_WIDTH, VIEW_HEIGHT);
    }

    public void update(Observable obs, Object obj)
    {
    }
}
