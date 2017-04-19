import java.awt.Color;
import java.awt.Point;
import java.util.Observable;

/**
 * Created by Jamal on 13/04/2017.
 */
public class Bubble extends Observable{
    private Point middelpunt;
    private int straal;
    private Color kleur;
    private int dx, dy;

    public Bubble(Point middelpunt, int straal, Color kleur, int dx, int dy)
    {
        this.middelpunt = middelpunt;
        this.straal = straal;
        this.kleur = kleur;
        this.dx = dx;
        this.dy = dy;

        new BubbleController(this, true);
    }

    public Bubble(int straal, Color kleur, int dx, int dy)
    {
        this.middelpunt = new Point(100, 100);
        this.straal = straal;
        this.kleur = kleur;
        this.dx = dx;
        this.dy = dy;

        new BubbleController(this, true);
    }

    public void move()
    {
        middelpunt.translate(dx, dy);

        if(middelpunt.x - getDiameter() <= 0) {
            dx *= -1;
            //middelpunt.x = 0;
        }
        else if(middelpunt.x + getDiameter() >= BoomshineApplicatie.WINDOW_WIDTH){
            dx *= -1;
            //middelpunt.x = BoomshineApplicatie.WINDOW_WIDTH;
        }

        if(middelpunt.y - getDiameter() <= 0) {
            dy *= -1;
            //middelpunt.y = 0;
        }
        else if(middelpunt.y + getDiameter() >= BoomshineApplicatie.WINDOW_HEIGHT){
            dy *= -1;
            //middelpunt.y = BoomshineApplicatie.WINDOW_HEIGHT;
        }

        setChanged();
        notifyObservers();
    }

    public Point getMiddelpunt()
    {
        return middelpunt;
    }

    public int getStraal()
    {
        return straal;
    }

    public int getDiameter()
    {
        return straal * 2;
    }
}
