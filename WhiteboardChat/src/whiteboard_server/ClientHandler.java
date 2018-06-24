package whiteboard_server;

import shared.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket socket;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private WhiteboardServer server;

    /**
     * Instantiate a ClientHandler.
     * @param socket The socket the client is connected to.
     * @param server The server instance.
     */
    public ClientHandler(Socket socket, WhiteboardServer server) {
        this.socket = socket;
        this.server = server;
        try {
            reader = new ObjectInputStream(socket.getInputStream());
            writer = new ObjectOutputStream(socket.getOutputStream());

            Thread t = new Thread(this);
            t.start();

            System.out.println("A new client has connected");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Object message;
        try {
            while((message = reader.readObject()) != null) {
                System.out.println(String.format("Received: %s", message));
                server.messageClients((Message)message);
            }
        } catch(ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send a message to the client.
     * @param message The message to be sent.
     */
    public void send(Message message) {
        try {
            writer.writeObject(message);
            writer.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
