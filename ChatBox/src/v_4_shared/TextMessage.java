package v_4_shared;

public class TextMessage extends Message
{
    private String text;
    
    public TextMessage( String sender, String text)
    {
        super(sender);
        this.text = text;
    }

    public String getText()
    {
        return text;
    }
    
    public String toString()
    {
        return sender + ": " + text;
    }

    @Override
    public Message asTextMessage()
    {
        return this;
    }

}
