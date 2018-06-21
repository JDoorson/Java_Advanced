package v_4_object_chat_client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import v_4_shared.TextMessage;

public class ChatClientView extends JFrame implements Observer
{
    private JTextField outgoingMessage;
    private JTextArea incommingMessages = new JTextArea("chatlog:\n");
;

    public static void main(String[] args)
    {
        String address = args[0];
        int port = Integer.parseInt( args[1] );
        String name = args[2];
        new ChatClientView( address, port, name );
    }

    public ChatClientView( String address, int port, String name )
    {
        ChatClient client = new ChatClient( address, port, name );

        client.addObserver( this );

        SendController controller = new SendController( client, this);
        
        maakGUI(client.getName(), controller );

    }
    
    @Override
    public void update( Observable o, Object object )
    {
        if (object instanceof TextMessage)
        {
            TextMessage message = (TextMessage) object;
            incommingMessages.append( message.toString() + "\n" );
        }
    }

    private void maakGUI(String naam, SendController controller )
    {
        setTitle("Chat Client Using Objects - " + naam);
        
        incommingMessages.setPreferredSize( new Dimension(300, 300) );
        incommingMessages.setLineWrap( true );
        incommingMessages.setWrapStyleWord( true );
        incommingMessages.setEditable( false );

        JScrollPane incommingPane = new JScrollPane( incommingMessages );
        add( incommingPane, BorderLayout.CENTER );
        
        JPanel sendPanel = new JPanel();
        outgoingMessage = new JTextField(20);
        outgoingMessage.addActionListener( controller );
        
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener( controller );
        
        sendPanel.add( outgoingMessage );
        sendPanel.add( sendButton );
        add( sendPanel, BorderLayout.SOUTH );
        
        pack();
        setVisible(true);
    }

    public String getMessageText()
    {
        return outgoingMessage.getText();
    }

    public void clearMessage()
    {
        outgoingMessage.setText( "" );
        outgoingMessage.requestFocus();
    }
}
