package views;

import javax.swing.JPanel;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class Bubble extends JPanel implements Observer{
    private models.Bubble model;

    public Bubble(models.Bubble model) {
        this.model = model;
        new controllers.Bubble(model);
        model.addObserver(this);

        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(this.model.getColor());
        g.fillOval(this.model.getCenter().getX(), this.model.getCenter().getY(), this.model.getRadius(), this.model.getRadius());
    }

    @Override
    public void update(Observable o, Object arg) {
        this.repaint();
    }
}
