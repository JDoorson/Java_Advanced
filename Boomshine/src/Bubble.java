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
    }

    public void move()
    {
        middelpunt.translate(dx, dy);
        //TODO: Gebruik middelpunt en straal om te laten stuiteren
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
