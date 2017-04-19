/**
 * Created by Jamal on 13/04/2017.
 */
public class BubbleController implements Runnable{
    private Bubble bubble;
    private boolean isMoving;

    public BubbleController(Bubble bubble, boolean isMoving)
    {
        this.bubble = bubble;
        this.isMoving = isMoving;

        Thread t = new Thread(this);
        t.start();
    }

    public void stopThread()
    {
        isMoving = false;
    }

    public void run()
    {
        while(isMoving)
        {
            bubble.move();

            try{
                Thread.sleep(1000 / BoomshineApplicatie.FPS);
            }
            catch(Exception e)
            {
            }
        }
    }
}
