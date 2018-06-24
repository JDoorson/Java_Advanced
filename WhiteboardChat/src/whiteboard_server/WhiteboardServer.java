package whiteboard_server;

import shared.messages.Message;
import shared.messages.client.DrawingMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class WhiteboardServer {
    private List<ClientHandler> clients = new ArrayList<>();

    /**
     * Instantiate a WhiteboardServer.
     * @param port The port to listen to.
     */
    public WhiteboardServer(int port) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println(String.format("Server started on port %d", port));

            while(true) {
                clients.add(new ClientHandler(serverSocket.accept(), this));
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Send a message to all connected clients.
     * @param message The message to be sent.
     */
    public void messageClients(Message message) {
        System.out.println(String.format("Sending: <%s> to %d clients", message.toString(), clients.size()));
        for(ClientHandler client : clients) {
            client.send(message);
        }
    }

    public static void main(String[] args) {
        new WhiteboardServer(1337);
    }
}
