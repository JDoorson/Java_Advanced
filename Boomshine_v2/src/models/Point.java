package models;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void translate(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public int getX() {
        return x;
    }

    public void setX(int dx) {
        this.x += dx;
    }

    public int getY() {
        return y;
    }

    public void setY(int dy) {
        this.y += dy;
    }
}
