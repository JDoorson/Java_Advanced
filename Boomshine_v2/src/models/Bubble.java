package models;

import java.awt.Color;
import java.util.Observable;
import java.util.Random;

public class Bubble extends Observable {
    private Point center;
    private int radius;
    private Color color;
    private int dx;
    private int dy;

    /**
     * Instantiate a bubble with randomly assigned values.
     */
    public Bubble() {
        Random rng = new Random();

        this.center = new Point(rng.nextInt(50), rng.nextInt(50));
        this.radius = rng.nextInt(10);
        this.color = new Color(rng.nextFloat(), rng.nextFloat(), rng.nextFloat());
        this.dx = rng.nextInt(10) - 5;
        this.dy = rng.nextInt(10) - 5;
    }

    /**
     * Instantiate a new bubble, using a point object to define the center.
     * @param center,   Point object defining the center of the bubble.
     * @param radius,   Integer defining the radius of the bubble.
     * @param color,    Color object defining the bubble's color.
     * @param dx,       Integer value defining the horizontal distance this bubble travels in pixels every time move() is called.
     * @param dy,       Integer value defining the vertical distance this bubble travels in pixels every time move() is called.
     */
    public Bubble(Point center, int radius, Color color, int dx, int dy) {
        this.center = center;
        this.radius = radius;
        this.color = color;
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Instantiate a new bubble, using a point object to define the center.
     * @param centerX,, Integer value defining the horizontal location of the bubble's center.
     * @param centerY,  Integer value defining the vertical location of the bubble's center.
     * @param radius,   Integer defining the radius of the bubble.
     * @param color,    Color object defining the bubble's color.
     * @param dx,       Integer value defining the horizontal distance this bubble travels in pixels every time move() is called.
     * @param dy,       Integer value defining the vertical distance this bubble travels in pixels every time move() is called.
     */
    public Bubble(int centerX, int centerY, int radius, Color color, int dx, int dy) {
        this.center = new Point(centerX, centerY);
        this.radius = radius;
        this.color = color;
        this.dx = dx;
        this.dy = dy;
    }

    public void move() {
        this.center.translate(this.dx, this.dy);

        this.setChanged();
        this.notifyObservers();
    }

    // TODO: Perform actual collision checks
    private void checkCollision()
    {
        if (this.center.getX() < FRAME_WIDTH) {
            this.handleHorizontalCollision();
        }

        if (true) {
            this.handleVerticalCollision();
        }
    }

    private void handleHorizontalCollision() {
        this.dx *= -1;
    }

    private void handleVerticalCollision() {
        this.dy *= -1;
    }

    public Point getCenter() {
        return this.center;
    }

    public int getRadius() {
        return this.radius;
    }

    public Color getColor() {
        return this.color;
    }
}
