package v_4_object_chat_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import v_4_shared.Message;
import v_4_shared.TextMessage;

public class ChatServer
{
    private List<ClientHandler> clients = new ArrayList<>();
    
    public static void main(String[] args)
    {
        new ChatServer( Integer.parseInt( args[0] ) );
    }

    public ChatServer( int port )
    {
        ServerSocket serverSocket = null;
        try
        {
            serverSocket = new ServerSocket( port );
            
            System.out.println( "Server started op port " + port );

            while ( true )
            {
                Socket clientSocket = serverSocket.accept();
                clients.add( new ClientHandler( clientSocket, this ) );
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if (serverSocket != null)
                try
                {
                    serverSocket.close();
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
        }
   }
    
    public void tellEveryone( Message message )
    {
        if ( ! (message instanceof TextMessage) )
            message = message.asTextMessage();
                
        for ( ClientHandler client : clients )
        {
            client.send( message );
        }
    }
}
