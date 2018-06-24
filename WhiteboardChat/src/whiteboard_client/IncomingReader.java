package whiteboard_client;

import shared.messages.client.ClientMessage;
import shared.messages.client.DrawingMessage;
import shared.messages.client.InitialMessage;
import shared.model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class IncomingReader implements Runnable {
    private ObjectInputStream reader;
    private WhiteboardClient client;

    /**
     * Instantiate an IncomingReader
     * @param socket The socket to retrieve input from
     * @param client The client to handle the input
     */
    public IncomingReader(Socket socket, WhiteboardClient client) {
        this.client = client;
        try {
            reader = new ObjectInputStream(socket.getInputStream());
        } catch(IOException e) {
            e.printStackTrace();
        }

        new Thread(this).start();
    }


    @Override
    public void run() {
        Object o;

        try {
            while((o = reader.readObject()) != null) {
                if(o instanceof ClientMessage) {
                    ClientMessage message = (ClientMessage) o;
                    //TODO: Properly handle based on message type and whatnot
                    client.handleIncoming(message);
                } else {
                    System.out.println(String.format("Illegal message received: %s", o.getClass().getSimpleName()));
                }
            }
        } catch(ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
