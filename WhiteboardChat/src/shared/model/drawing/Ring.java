package shared.model.drawing;

import java.awt.*;

public class Ring extends Drawing {
    private int diameter;
    private int size;

    public Ring(Point location, int diameter, int size) {
        super(location);
        this.diameter = diameter;
        this.size = size;
    }

    public Ring(int diameter, int size) {
        super(null);
        this.diameter = diameter;
        this.size = size;
    }

    public Ring() {
        super(null);
        this.diameter = 10;
        this.size = 10;
    }

    public int getDiameter() {
        return diameter;
    }

    public int getSize() {
        return size;
    }

    public void setLocation(Point p) {
        System.out.println("Setloc");
        this.location = p;
    }
}
