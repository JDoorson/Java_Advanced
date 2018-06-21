import javax.swing.WindowConstants;
import javax.swing.JFrame;

public class Application extends JFrame{
    private final static String APPLICATION_NAME = "Boomshine";
    public final static int FRAME_WIDTH = 300;
    public final static int FRAME_HEIGHT = 300;

    public Application() {
        this.setBounds(100, 100, 600, 600);
        this.setTitle(APPLICATION_NAME);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.initBubbles(3);

        setVisible(true);
    }

    private void initBubbles(int nBubbles) {
        for(int i = 0; i < nBubbles; i++) {
            this.add(new views.Bubble(new models.Bubble()));
        }
    }

    public static void main(String args[]) {
        new Application();
    }
}
