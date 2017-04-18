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
    }

    public Bubble(int straal, Color kleur, int dx, int dy)
    {
        this.middelpunt = new Point(30, 30);
        this.straal = straal;
        this.kleur = kleur;
        this.dx = dx;
        this.dy = dy;
    }

    public void move()
    {
        middelpunt.translate(dx, dy);
        //TODO: Gebruik middelpunt en straal om te laten stuiteren

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
}
