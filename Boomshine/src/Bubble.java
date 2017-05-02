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

        if(middelpunt.x - getStraal() < 0) {
            dx *= -1;
            middelpunt.x = getStraal();
        }
        else if(middelpunt.x + getStraal() > BoomshineApplicatie.WINDOW_WIDTH){
            dx *= -1;
            middelpunt.x = BoomshineApplicatie.WINDOW_WIDTH - getStraal();
        }

        if(middelpunt.y - getStraal() < 0) {
            dy *= -1;
            middelpunt.y = getStraal();
        }
        else if(middelpunt.y + getStraal() > BoomshineApplicatie.WINDOW_HEIGHT){
            dy *= -1;
            middelpunt.y = BoomshineApplicatie.WINDOW_HEIGHT - getStraal();
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

    public Color getKleur(){
        return kleur;
    }
}
