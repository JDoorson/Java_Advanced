package whiteboard_client;

import shared.messages.client.DrawingMessage;
import shared.messages.client.InitialMessage;
import shared.model.User;
import shared.model.drawing.Drawing;
import shared.model.drawing.TextDrawing;

import javax.swing.*;
import javax.xml.soap.Text;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class WhiteboardView extends JFrame implements Observer{
    private JTextField userInputField;
    private JPanel whiteboard;

    /**
     * Instantiate a WhiteboardView
     * @param user The user connecting to the server
     */
    public WhiteboardView(User user) {
        setBounds(100, 100, 500, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        WhiteboardClient client = new WhiteboardClient("localhost", 1337, user);
        client.addObserver(this);

        SendController controller = new SendController(client, this);
        createGUI(client.getUser(), controller);
    }

    /**
     * It's the main.
     * @param args it's the arguments
     */
    public static void main(String[] args) {
        // TODO: Add user input for connection info
        String name = "Bob";
        Color color = Color.ORANGE;

        User user = new User(name, color);
        new WhiteboardView(user);
    }

    private void createGUI(User user, SendController controller) {
        setTitle(String.format("Whiteboard Client - %s", user.getName()));

        whiteboard = new JPanel();
        whiteboard.setLayout(null);
        whiteboard.setPreferredSize(new Dimension(300, 300));
        whiteboard.addMouseListener(controller);
        add(whiteboard, BorderLayout.CENTER);

        userInputField = new JTextField(20);
        JPanel inputPanel = new JPanel();
        inputPanel.add(userInputField);
        add(inputPanel, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    public void update(Observable obs, Object o) {
        if(o instanceof DrawingMessage) {
            DrawingMessage message = (DrawingMessage)o;
            if(message.getDrawing() instanceof TextDrawing) {
                TextDrawing d = (TextDrawing)message.getDrawing();
                Point p = d.getLocation();
                String text = d.getText();
                System.out.println(String.format("Received TextDrawing -- Text: %s -- Location: %f,%f", text, p.getX(), p.getY()));
                drawText(p, text);
            }
        } else if(o instanceof InitialMessage) {
            InitialMessage message = (InitialMessage)o;
            User u = message.getSender();
            System.out.println(String.format("User connected: %s", u.getName()));
        } else {
            System.out.println("Received a message that couldn't be handled");
        }
    }

    private void drawText(Point p, String text) {
        JLabel label = new JLabel(text);
        label.setSize(label.getPreferredSize());
        p.setLocation(adjustCenter(p, label.getPreferredSize()));
        label.setLocation(p);
        whiteboard.add(label);
        whiteboard.repaint();
    }

    private Point adjustCenter(Point p, Dimension d) {
        return new Point((int)(p.getX() - (d.width * 0.5)), (int)(p.getY() - (d.height * 0.5)));
    }

    public String getUserInput() {
        return userInputField.getText();
    }

    public void clearUserInput() {
        userInputField.setText("");
        userInputField.requestFocus();
    }
}
