import shared.messages.client.InitialMessage;
import shared.model.User;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.PrintWriter;
import java.net.Socket;

public class WhiteboardClient extends JFrame{
    public WhiteboardClient(User user) {
        setBounds(100, 100, 500, 500);
        setLayout(new BorderLayout());
        setTitle("Campsites");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Socket socket;
        int port = 1337;

        InitialMessage message = new InitialMessage(user);

        try {
            socket = new Socket("localhost", port);
            System.out.println("Server started on port: " + port);
        } catch(Exception e) {
            e.printStackTrace();
        }

        PrintWriter pr = new PrintWriter(socket.getOutputStream());
        pr.println(message);
    }

    public static void main(String[] args) {
        // TODO: Add user input for connection info
        String name = "Bob";
        Color color = Color.ORANGE;

        User user = new User(name, color);
        new WhiteboardClient(user);
    }


}
