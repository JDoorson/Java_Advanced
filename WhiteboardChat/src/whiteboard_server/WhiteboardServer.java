package whiteboard_server;

import shared.messages.InitMessage;
import shared.messages.Message;
import shared.messages.server.UsersMessage;
import shared.model.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class WhiteboardServer {
    private List<ClientHandler> clients = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();

    /**
     * Instantiate a WhiteboardServer.
     *
     * @param port The port to listen to.
     */
    private WhiteboardServer(int port) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println(String.format("Server started on port %d", port));

            while (true) {
                clients.add(new ClientHandler(serverSocket.accept(), this));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Send a message to all connected clients.
     *
     * @param message The message to be sent.
     */
    public void messageClients(Message message) {
        System.out.println(String.format("Sending: <%s> to %d clients", message.toString(), clients.size()));
        for (ClientHandler client : clients) {
            client.send(message);
        }
    }

    public void sendUserUpdate() {
        List<User> users = new ArrayList<>();
        for (ClientHandler c : clients) {
            users.add(c.getUser());
        }
        messageClients(new UsersMessage(users));
    }

    public void sendWhiteboard(ClientHandler client) {
        System.out.println(String.format("Sending init list containing %d messages", messages.size()));
        client.send(new InitMessage(messages));
    }

    public void disconnectClient(ClientHandler client) {
        clients.remove(client);
    }

    public void addMessage(Message m) {
        messages.add(m);
    }

    public static void main(String[] args) {
        int port = 0;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("Invalid port entered, exiting.");
            System.exit(0);
        }
        new WhiteboardServer(port);
    }
}
