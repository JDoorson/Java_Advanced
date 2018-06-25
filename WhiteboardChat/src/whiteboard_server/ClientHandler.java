package whiteboard_server;

import shared.messages.Message;
import shared.messages.client.InitialMessage;
import shared.messages.client.StopMessage;
import shared.messages.client.UserMessage;
import shared.model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private WhiteboardServer server;
    private User user;

    /**
     * Instantiate a ClientHandler.
     *
     * @param socket The socket the client is connected to.
     * @param server The server instance.
     */
    ClientHandler(Socket socket, WhiteboardServer server) {
        this.server = server;
        try {
            reader = new ObjectInputStream(socket.getInputStream());
            writer = new ObjectOutputStream(socket.getOutputStream());

            Thread t = new Thread(this);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Object message;
        try {
            while ((message = reader.readObject()) != null) {
                if (message instanceof InitialMessage) {
                    InitialMessage m = (InitialMessage) message;
                    user = m.getSender();
                    server.sendUserUpdate();
                    server.sendWhiteboard(this);
                } else if (message instanceof StopMessage) {
                    server.disconnectClient(this);
                    server.sendUserUpdate();
                } else if(message instanceof UserMessage) {
                    UserMessage m = (UserMessage)message;
                    user = m.getSender();
                    server.sendUserUpdate();
                } else {
                    server.messageClients((Message) message);
                    server.addMessage((Message) message);
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send a message to the client.
     *
     * @param message The message to be sent.
     */
    public void send(Message message) {
        try {
            writer.writeObject(message);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getUser() {
        return user;
    }
}
