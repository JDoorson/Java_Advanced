package v_4_object_chat_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import v_4_shared.Message;

public class ClientHandler implements Runnable
{
    private Socket socket;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private ChatServer server; 
    
    public ClientHandler( Socket socket, ChatServer server )
    {
        this.socket = socket;
        this.server = server;
        try
        {
            reader = new ObjectInputStream( socket.getInputStream() );
            writer = new ObjectOutputStream( socket.getOutputStream() );

            Thread t = new Thread( this );
            t.start();

            System.out.println( "new client connected" );
        }
        catch ( IOException ex )
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        Object message;
        try
        {
            while ( ( message = reader.readObject() ) != null)
            {
                System.out.println( "read - " + message );
                server.tellEveryone( (Message) message );
            }
        }
        catch ( ClassNotFoundException | IOException e )
        {
            e.printStackTrace();
        }
    }

    public void send( Message message )
    {
        try
        {
            writer.writeObject( message );
            writer.flush();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
}
