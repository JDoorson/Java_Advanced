package whiteboard_client;

import shared.messages.InitMessage;
import shared.messages.Message;
import shared.messages.client.DrawingMessage;
import shared.messages.server.UsersMessage;
import shared.model.User;
import shared.model.drawing.*;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class WhiteboardView extends JFrame implements Observer {
    private JTextField userInputField;
    private JPanel whiteboard;
    private JPanel connectedUsers;

    /**
     * Instantiate a WhiteboardView
     */
    private WhiteboardView() {
        // Configuration dialogs
        User user = userOptionsDialog();
        ServerConfiguration config = serverConfigDialog();

        // Define the client
        WhiteboardClient client;
        if (user.isHuman()) {
            client = new WhiteboardClient(config, user);
        } else {
            client = new TestClient(config, user);
        }
        client.addObserver(this);

        // Set the GUI stuff
        initGUI(client.getUser(), client);
    }

    /**
     * It's the main.
     *
     * @param args it's the arguments
     */
    public static void main(String[] args) {
        new WhiteboardView();
    }

    /**
     * Creates the GUI.
     *
     * @param user   The user currently using the client.
     * @param client The client instance to 'talk' to.
     */
    private void initGUI(User user, WhiteboardClient client) {
        initFrame(client, user);
        initWhiteboard(client, user);
        initUserList();
        if (user.isHuman()) {
            initInputs(client);
        }
        pack();
        setVisible(true);
    }

    @Override
    public void update(Observable obs, Object obj) {
        if (obj instanceof InitMessage) {
            handleInitMessage((InitMessage) obj);
        } else if (obj instanceof DrawingMessage) {
            handleDrawingMessage((DrawingMessage) obj);
        } else if (obj instanceof UsersMessage) {
            handleUsersMessage((UsersMessage) obj);
        }
    }

    /**
     * Identify the child-type of DrawingMessage, and add the drawing at the given location.
     *
     * @param message
     */
    private void handleDrawingMessage(DrawingMessage message) {
        Drawing drawing = message.getDrawing();
        Graphics g = whiteboard.getGraphics();

        if (drawing instanceof TextDrawing) {
            TextDrawing d = (TextDrawing) message.getDrawing();
            g.setColor(message.getSender().getColor());
            g.drawString(d.getText(), (int)drawing.getLocation().getX(), (int)drawing.getLocation().getY());
        } else if (drawing instanceof Line) {
            Line l = (Line) drawing;
            g.setColor(message.getSender().getColor());
            g.drawLine((int) l.getLocation().getX(), (int) l.getLocation().getY(), (int) l.getEnd().getX(), (int) l.getEnd().getY());
            System.out.println(String.format("Received %s -- Start: (%f,%f) -- End: (%f,%f)", message.getClass().getSimpleName(), l.getLocation().getX(), l.getLocation().getY(), l.getEnd().getX(), l.getEnd().getY()));
        } else if (drawing instanceof Stamp) {
            Stamp s = (Stamp)drawing;
            boolean[][] stamp = s.getStamp();
            g.setColor(message.getSender().getColor());
            for (int y = 0; y < stamp.length; y++) {
                for (int x = 0; x < stamp[0].length; x++) {
                    if(stamp[x][y]){
                        g.fillRect( drawing.getLocation().x + y,drawing.getLocation().y + x, 1, 1);
                    }
                }
            }
        } else if(drawing instanceof Ring) {
            Ring r = (Ring)drawing;
            int xPos = (int)r.getLocation().getX();
            int yPos = (int)r.getLocation().getY();
            int diameter = r.getDiameter();
            System.out.println(String.format("Ring data: (%d,%d) -- %d", xPos, yPos, diameter));
            g.setColor(message.getSender().getColor());
            for(int i = 0; i < r.getSize(); i++) {
                g.drawOval(xPos, yPos, diameter + i, diameter + i);
            }
        }
    }

    private void handleInitMessage(InitMessage message) {
        for (Message m : message.getMessages()) {
            if (!(m instanceof DrawingMessage)) {
                continue;
            }

            handleDrawingMessage((DrawingMessage) m);
        }
    }

    /**
     * Clears the list of connected users and replaces it with the updated version.
     *
     * @param message The UsersMessage containing the required data.
     */
    private void handleUsersMessage(UsersMessage message) {
        // Remove the current elements
        connectedUsers.removeAll();

        // Generate a new panel for each user
        for (User u : message.getUsers()) {
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
    }

    /**
     * Adds the inputs required to add things to the Whiteboard.
     *
     * @param client The client instance being used.
     */
    private void initInputs(WhiteboardClient client) {
        JPanel inputPanel = new JPanel();
        InputController controller = new InputController(client, this);

        InputButton square = new InputButton("Square", Input.SQUARE);
        square.addActionListener(controller);
        inputPanel.add(square);

        InputButton circle = new InputButton("Circle", Input.CIRCLE);
        circle.addActionListener(controller);
        inputPanel.add(circle);

        InputButton sphere = new InputButton("Sphere", Input.SPHERE);
        sphere.addActionListener(controller);
        inputPanel.add(sphere);

        InputButton smiley = new InputButton("Smiley", Input.SMILEY);
        smiley.addActionListener(controller);
        inputPanel.add(smiley);

        InputButton solid = new InputButton("Solid", Input.SOLID);
        solid.addActionListener(controller);
        inputPanel.add(solid);

        InputButton ring = new InputButton("Ring", Input.RING);
        ring.addActionListener(controller);
        inputPanel.add(ring);

        // Text input setup
        userInputField = new JTextField(20);
        userInputField.addActionListener(controller);
        userInputField.addFocusListener(controller);
        inputPanel.add(userInputField);
        add(inputPanel, BorderLayout.SOUTH);
    }

    /**
     * Add the list of users.
     */
    private void initUserList() {
        connectedUsers = new JPanel(new GridBagLayout());
        add(new JScrollPane(connectedUsers), BorderLayout.EAST);
    }

    /**
     * Add the actual whiteboard.
     *
     * @param client The client instance being used.
     */
    private void initWhiteboard(WhiteboardClient client, User user) {
        whiteboard = new JPanel();
        whiteboard.setLayout(null);
        whiteboard.setPreferredSize(new Dimension(300, 300));
        if (user.isHuman()) {
            whiteboard.addMouseListener(new WhiteboardController(client, this));
        }
        add(whiteboard, BorderLayout.CENTER);
    }

    /**
     * Initialize the JFrame, which contains all of the other JComponents.
     *
     * @param client The client instance being used.
     * @param user   The user connecting to the server.
     */
    private void initFrame(WhiteboardClient client, User user) {
        setBounds(100, 100, 500, 500);
        setLayout(new BorderLayout());
        setTitle(String.format("Whiteboard Client - %s", user.getName()));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowController(client));
    }

    /**
     * Dialog to allow a user to set their name and color.
     *
     * @return The User object containing the entered values.
     */
    private User userOptionsDialog() {
        User user = new User();

        // Keep reopening the dialog if the entered information is invalid
        while (!user.isValid()) {
            JTextField username = new JTextField();
            JColorChooser color = new JColorChooser();
            ActionListener listener = e -> {
                if (e.getActionCommand().equals("Robot")) {
                    user.setHuman(false);
                } else {
                    user.setHuman(true);
                }
            };

            ButtonGroup btnGroup = new ButtonGroup();
            JRadioButton human = new JRadioButton("Human");
            human.addActionListener(listener);
            human.setActionCommand("Human");
            human.setSelected(true);
            btnGroup.add(human);
            JRadioButton robot = new JRadioButton("Robot");
            robot.addActionListener(listener);
            robot.setActionCommand("Robot");
            btnGroup.add(robot);
            final JComponent[] fields = new JComponent[]{
                    new JLabel("Username"),
                    username,
                    new JLabel("Color"),
                    color,
                    human,
                    robot
            };
            int result = JOptionPane.showConfirmDialog(null, fields, "User options", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                user.setName(username.getText());
                user.setColor(color.getColor());
            } else {
                System.exit(0);
            }
        }

        return user;
    }

    /**
     * Dialog to allow a user to enter the information of the server they're connecting to.
     *
     * @return The ServerConfiguration object containing the entered values.
     */
    private ServerConfiguration serverConfigDialog() {
        ServerConfiguration config = new ServerConfiguration();

        // Keep reopening the dialog if the entered information is invalid
        while (!config.isValid()) {
            JTextField host = new JTextField();
            JTextField port = new JTextField();
            final JComponent[] fields = new JComponent[]{
                    new JLabel("Host"),
                    host,
                    new JLabel("Port"),
                    port
            };
            int result = JOptionPane.showConfirmDialog(null, fields, "Server configuration", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                config.setHost(host.getText());
                config.setPort(port.getText());
            } else {
                System.exit(0);
            }
        }

        return config;
    }

    Ring ringDialog() {
        int diameter = 0;
        int size = 0;

        while(diameter <= 0 || size <= 0) {
            JTextField diameterField = new JTextField();
            JTextField sizeField = new JTextField();
            final JComponent[] fields = new JComponent[] {
                    new JLabel("Diameter"),
                    diameterField,
                    new JLabel("Size"),
                    sizeField
            };
            int result = JOptionPane.showConfirmDialog(null, fields, "Ring configuration", JOptionPane.OK_CANCEL_OPTION);
            if(result == JOptionPane.OK_OPTION) {
                try {
                    diameter = Integer.parseInt(diameterField.getText());
                    size = Integer.parseInt(sizeField.getText());
                } catch(NumberFormatException e) {
                    e.printStackTrace();
                }
            } else {
                return new Ring();
            }
        }

        return new Ring(diameter, size);
    }

    /**
     * Adjust the values of a point so that they anchor at the center of an object, instead of the top-left.
     *
     * @param p The point being adjusted.
     * @param d The dimension of the something.
     * @return The adjusted point.
     */
    private Point adjustCenter(Point p, Dimension d) {
        return new Point((int) (p.getX() - (d.width * 0.5)), (int) (p.getY() - (d.height * 0.5)));
    }

    /**
     * Retrieve the input from the text field.
     *
     * @return The string present in the text field.
     */
    String getUserInput() {
        return userInputField.getText();
    }
}
