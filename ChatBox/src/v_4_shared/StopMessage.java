package v_4_shared;

public class StopMessage extends Message
{
    public StopMessage(String sender)
    {
        super(sender);
    }

    @Override
    public Message asTextMessage()
    {
        return new TextMessage(sender, "exited");
    }

}
