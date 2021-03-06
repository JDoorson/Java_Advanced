package whiteboard_client;

import shared.messages.Message;
import shared.messages.client.InitialMessage;
import shared.messages.client.StopMessage;
import shared.model.User;
import shared.model.drawing.Ring;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;

public class WhiteboardClient extends Observable {
    private User user;
    private Socket socket;
    private ObjectOutputStream oos;
    private Input inputOption = Input.TEXT;
    private Ring ring = new Ring(null, 5, 5);

    /**
     * Instantiate a WhiteboardClient
     *
     * @param config Object containing the data for a server connection
     * @param user   The user connected
     */
    WhiteboardClient(ServerConfiguration config, User user) {
        this.user = user;
        connect(config);

        sendMessage(new InitialMessage(user));

        new IncomingReader(socket, this);
    }

    /**
     * Connect to a server
     *
     * @param config Object containing the data for a server connection
     */
    private void connect(ServerConfiguration config) {
        try {
            socket = new Socket(config.getHost(), config.getPort());
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void disconnect() {
        sendMessage(new StopMessage(user));
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send a message to the server
     *
     * @param message The message being sent
     */
    void sendMessage(Message message) {
        try {
            oos.writeObject(message);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle a ClientMessage received through the server.
     *
     * @param message The message to be passed on to all observers
     */
    void handleIncomingMessage(Message message) {
        setChanged();
        notifyObservers(message);
    }

    /**
     * @return Returns the user property
     */
    public User getUser() {
        return user;
    }

    Input getInputOption() {
        return inputOption;
    }

    void setInputOption(Input input) {
        this.inputOption = input;
    }

    void setRing(Ring ring) {
        this.ring = ring;
    }

    Ring getRing() {
        return ring;
    }

    void setUser(User user) {
        this.user = user;
    }
}
