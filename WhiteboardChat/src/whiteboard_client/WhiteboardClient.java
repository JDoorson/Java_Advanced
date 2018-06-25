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
     * @param config Object containing the data for a server connection
     * @param user The user connected
     */
    WhiteboardClient(ServerConfiguration config, User user) {
        this.user = user;
        connect(config);

        sendMessage(new InitialMessage(user));

        new IncomingReader(socket, this);
    }

    /**
     * Connect to a server
     * @param config Object containing the data for a server connection
     */
    private void connect(ServerConfiguration config) {
        try {
            socket = new Socket(config.getHost(), config.getPort());
            oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println(String.format("Network connection started on %s:%d", config.getHost(), config.getPort()));
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
