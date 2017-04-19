import javax.swing.JPanel;
import java.awt.Color;
import java.util.Observer;
import java.util.Observable;

/**
 * Created by Jamal on 13/04/2017.
 */
public class BoomshineView extends JPanel implements Observer{


    public BoomshineView()
    {
        setLayout(null);
        setBackground(Color.RED);
    }

    public void update(Observable obs, Object obj)
    {

    }
}
