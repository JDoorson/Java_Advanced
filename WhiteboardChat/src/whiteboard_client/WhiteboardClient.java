package whiteboard_client;

import shared.messages.Message;
import shared.messages.client.InitialMessage;
import shared.messages.client.StopMessage;
import shared.model.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class WhiteboardClient extends Observable {
    private User user;
    private List<User> users = new ArrayList<>();
    private Socket socket;
    private ObjectOutputStream oos;
    private Input inputOption = Input.TEXT;

    /**
     * Instantiate a WhiteboardClient
     * @param address Server address to connect to
     * @param port Server port to connect to
     * @param user The user connected
     */
    public WhiteboardClient(String address, int port, User user) {
        this.user = user;
        connect(address, port);

        sendMessage(new InitialMessage(user));

        new IncomingReader(socket, this);
    }

    /**
     * Connect to a server
     * @param address Server address
     * @param port Server port
     */
    private void connect(String address, int port) {
        try {
            socket = new Socket(address, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println(String.format("Network connection started on %s:%d", address, port));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        sendMessage(new StopMessage(user));
        try {
            socket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send a message to the server
     * @param message The message being sent
     */
    public void sendMessage(Message message) {
        try {
            oos.writeObject(message);
            oos.flush();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle a ClientMessage received through the server.
     * @param message The message to be passed on to all observers
     */
    public void handleIncomingMessage(Message message) {
        setChanged();
        notifyObservers(message);
    }

    /**
     * @return Returns the user property
     */
    public User getUser() {
        return user;
    }

    public Input getInputOption() {
        return inputOption;
    }

    public void setInputOption(Input input) {
        this.inputOption = input;
    }
}
