package whiteboard_client;

import shared.messages.client.DrawingMessage;
import shared.messages.server.UsersMessage;
import shared.model.User;
import shared.model.drawing.Drawing;
import shared.model.drawing.Line;
import shared.model.drawing.Stamp;
import shared.model.drawing.TextDrawing;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class WhiteboardView extends JFrame implements Observer{
    private JTextField userInputField;
    private JPanel whiteboard;
    private JPanel connectedUsers;

    /**
     * Instantiate a WhiteboardView
     * @param user The user connecting to the server
     */
    private WhiteboardView(User user) {
        user = userOptionsDialog();
        setBounds(100, 100, 500, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        WhiteboardClient client = new WhiteboardClient("localhost", 1337, user);
        client.addObserver(this);

        initGUI(client.getUser(), client);
    }

    /**
     * It's the main.
     * @param args it's the arguments
     */
    public static void main(String[] args) {
        String name = "Gyro Zeppeli";
        Color color = Color.RED;

        User user = new User(name, color);
        new WhiteboardView(user);
    }

    /**
     * Creates the GUI.
     * @param user The user currently using the client.
     * @param client The client instance to 'talk' to.
     */
    private void initGUI(User user, WhiteboardClient client) {
        setTitle(String.format("Whiteboard Client - %s", user.getName()));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowController(client));
        initWhiteboard(client);
        addUserList();
        addInputs(client);
        pack();
        setVisible(true);
    }

    @Override
    public void update(Observable obs, Object obj) {
        if(obj instanceof DrawingMessage) {
            handleDrawingMessage((DrawingMessage)obj);
        } else if(obj instanceof UsersMessage) {
            handleUsersMessage((UsersMessage)obj);
        } else {
            System.out.println("Received a message that couldn't be handled");
        }
    }

    /**
     * Identify the child-type of DrawingMessage, and add the drawing at the given location.
     * @param message
     */
    private void handleDrawingMessage(DrawingMessage message) {
        Drawing drawing = message.getDrawing();

        if(drawing instanceof TextDrawing) {
            TextDrawing d = (TextDrawing)message.getDrawing();
            JLabel label = new JLabel(d.getText());
            label.setSize(label.getPreferredSize());
            label.setLocation(adjustCenter(d.getLocation(), label.getPreferredSize()));
            label.setForeground(message.getSender().getColor());
            whiteboard.add(label);
        } else if(drawing instanceof Line) {
            Line l = (Line)drawing;
//            JPanel panel = new JPanel() {
//                @Override
//                protected void paintComponent(Graphics g) {
//                    super.paintComponent(g);
//                    g.drawLine(10, 10, 10, 10);
//                    System.out.println(String.format("Received %s -- Start: (%f,%f) -- End: (%f,%f)", message.getClass().getSimpleName(), l.getLocation().getX(), l.getLocation().getY(), l.getEnd().getX(), l.getEnd().getY()));
//                }
//            };
            whiteboard.add(new LinePanel((int)l.getLocation().getX(), (int)l.getLocation().getY(), (int)l.getEnd().getX(), (int)l.getEnd().getY()));
            //System.out.println(String.format("Received %s -- Start: (%f,%f) -- End: (%f,%f)", message.getClass().getSimpleName(), l.getLocation().getX(), l.getLocation().getY(), l.getEnd().getX(), l.getEnd().getY()));
            //Graphics g = panel.getGraphics();
            //g.drawLine((int)l.getLocation().getX(), (int)l.getLocation().getY(), (int)l.getEnd().getX(), (int)l.getEnd().getY());
        } else if(drawing instanceof Stamp) {
            Stamp s = (Stamp)message.getDrawing();
            System.out.println(String.format("Received %s -- Currently unsupported", s.getClass().getSimpleName()));
        } else {
            System.out.println(String.format("Received illegal drawing: %s", drawing.getClass().getSimpleName()));
        }

        // Display the new additions
        repaint();
    }

    /**
     * Clears the list of connected users and replaces it with the updated version.
     * @param message The UsersMessage containing the required data.
     */
    private void handleUsersMessage(UsersMessage message) {
        // Remove the current elements
        connectedUsers.removeAll();

        // Generate a new panel for each user
        for(User u : message.getUsers()) {
            // Define the panel
            JPanel panel = new JPanel();
            panel.add(new JLabel(u.getName()));
            panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
            panel.setBackground(u.getColor());

            // Define the GBC
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Add to the Panel
            connectedUsers.add(panel, gbc, 0);
        }

        // Apply layout changes
        validate();
        repaint();
    }

    /**
     * Adds the inputs required to
     * @param client
     */
    private void addInputs(WhiteboardClient client) {
        JPanel inputPanel = new JPanel();
        InputController controller = new InputController(client);

        StampButton square = new StampButton("Square", "src/resources/blokje.stp", Input.SQUARE);
        square.addActionListener(controller);
        inputPanel.add(square);

        StampButton circle = new StampButton("Circle", "src/resources/cirkel.stp", Input.CIRCLE);
        circle.addActionListener(controller);
        inputPanel.add(circle);

        StampButton sphere = new StampButton("Sphere", "src/resources/rondje.stp", Input.SPHERE);
        sphere.addActionListener(controller);
        inputPanel.add(sphere);

        StampButton smiley = new StampButton("Smiley", "src/resources/smiley.stp", Input.SMILEY);
        smiley.addActionListener(controller);
        inputPanel.add(smiley);

        StampButton solid = new StampButton("Solid", "src/resources/solid.stp", Input.SOLID);
        solid.addActionListener(controller);
        inputPanel.add(solid);

        // Text input setup
        userInputField = new JTextField(20);
        userInputField.addActionListener(controller);
        userInputField.addFocusListener(controller);
        inputPanel.add(userInputField);
        add(inputPanel, BorderLayout.SOUTH);
    }

    private void addUserList() {
        connectedUsers = new JPanel(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridwidth = GridBagConstraints.REMAINDER;
//        gbc.weightx = 1;
//        gbc.weighty = 1;
//        connectedUsers.add(new JPanel(), gbc);
        add(new JScrollPane(connectedUsers), BorderLayout.EAST);
    }

    private void initWhiteboard(WhiteboardClient client) {
        whiteboard = new JPanel();
        whiteboard.setLayout(null);
        whiteboard.setPreferredSize(new Dimension(300, 300));
        whiteboard.addMouseListener(new WhiteboardController(client, this));
        add(whiteboard, BorderLayout.CENTER);
    }

    private User userOptionsDialog() {
        String name = "";

        while(name.equals("")) {
            JTextField username = new JTextField();
            JColorChooser color = new JColorChooser();
            final JComponent[] fields = new JComponent[]{
                    new JLabel("Username"),
                    username,
                    new JLabel("Color"),
                    color
            };
            int result = JOptionPane.showConfirmDialog(null, fields, "User options", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                name = username.getText();
                if(!name.equals("")) {
                    return new User(name, color.getColor());
                }
            } else {
                System.exit(0);
            }
        }

        return null;
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
