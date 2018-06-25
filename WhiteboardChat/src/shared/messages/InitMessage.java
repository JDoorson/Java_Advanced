package shared.messages;

import java.util.List;

public class InitMessage implements Message {
    private static final long serialVersionUID = 13100;

    private List<Message> messages;

    public InitMessage(List<Message> messages) {
        this.messages = messages;
    }

    public List<Message> getMessages() {
        return messages;
    }
}