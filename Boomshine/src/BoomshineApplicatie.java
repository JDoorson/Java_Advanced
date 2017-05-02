import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Point;
import java.util.Random;

/**
 * Created by Jamal on 13/04/2017.
 */
public class BoomshineApplicatie extends JFrame {
    public static final int WINDOW_WIDTH = 750, WINDOW_HEIGHT = 750, FPS = 60;
    private final int NUM_BALLS = 200, MIN_VELOCITY = 0, MAX_VELOCITY = 10, MIN_SIZE = 5, MAX_SIZE = 10;

    public BoomshineApplicatie() {
        init();
        setVisible(true);
    }

    private void init() {
        //TODO: Make dynamic

        setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Boomshine");

        JPanel view = new BoomshineView();
        add(view);

        for (int i = 0; i < NUM_BALLS; i++) {
            Bubble b = new Bubble(
                    randomStartpunt(),
                    randomInt(MIN_SIZE, MAX_SIZE),
                    randomColor(),
                    randomInt(MIN_VELOCITY, MAX_VELOCITY),
                    randomInt(MIN_VELOCITY, MAX_VELOCITY));
            view.add(new BubbleView(b));
        }
    }

    private Color randomColor() {
        Random rng = new Random();

        float r = rng.nextFloat();
        float g = rng.nextFloat();
        float b = rng.nextFloat();

        return new Color(r, g, b);
    }

    private int randomInt(int minVal, int maxVal)
    {
        Random rng = new Random();
        int velocity = rng.nextInt(maxVal - minVal + 1) + minVal;

        if(rng.nextInt(2)==1)
            velocity *= -1;

        return velocity;
    }

    private Point randomStartpunt(){
        Random rng = new Random();

        int x = rng.nextInt(WINDOW_WIDTH - MAX_SIZE + 1) + MAX_SIZE;
        int y = rng.nextInt(WINDOW_HEIGHT - MAX_SIZE + 1) + MAX_SIZE;

        return new Point(x, y);
    }

    public static void main(String[] args) {
        new BoomshineApplicatie();
    }
}
