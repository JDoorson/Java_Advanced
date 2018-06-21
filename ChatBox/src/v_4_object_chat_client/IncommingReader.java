package v_4_object_chat_client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import v_4_shared.TextMessage;

public class IncommingReader implements Runnable
{
    private ObjectInputStream reader;
    private ChatClient client;
    
    public IncommingReader( Socket socket, ChatClient client )
    {
        this.client = client;
        try
        {
            reader = new ObjectInputStream( socket.getInputStream() );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        new Thread( this ).start();
    }

    @Override
    public void run()
    {
        Object object;
        try
        {
            while ( ( object = reader.readObject() ) != null)
            {
                if ( object instanceof TextMessage )
                {
                    TextMessage message = (TextMessage) object;
                    System.out.println( message );
                    client.addIncomming( message );
                }
                else 
                    System.out.println( "illegal message type: " + object.getClass().getSimpleName()  );
            }
        }
        catch ( ClassNotFoundException | IOException e )
        {
            e.printStackTrace();
        }
    }
}
