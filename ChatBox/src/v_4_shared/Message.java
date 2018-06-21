package v_4_shared;

import java.io.Serializable;

public abstract class Message implements Serializable
{
    protected String sender;
    
    public Message( String sender )
    {
        this.sender = sender;
    }

    public String getSender()
    {
        return sender;
    }

    public abstract Message asTextMessage();
}
