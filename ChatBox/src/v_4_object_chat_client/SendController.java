package v_4_object_chat_client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import v_4_shared.TextMessage;

public class SendController implements ActionListener
{
    private ChatClient client;
    private ChatClientView view;
    
    public SendController( ChatClient client, ChatClientView view )
    {
        this.client = client;
        this.view = view;
    }
    
    @Override
    public void actionPerformed( ActionEvent e )
    {
        client.sendMessage( new TextMessage(client.getName(), view.getMessageText()) );
        view.clearMessage();
    }
}
