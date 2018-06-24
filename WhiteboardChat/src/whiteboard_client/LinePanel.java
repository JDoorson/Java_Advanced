package whiteboard_client;

import javax.swing.*;
import java.awt.*;

public class LinePanel extends JPanel {
    private int startX, startY, endX, endY;

    public LinePanel(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(startX, startY, endX, endY);
    }
}
