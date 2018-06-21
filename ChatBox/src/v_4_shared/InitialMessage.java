package v_4_shared;

import java.awt.Color;

import javax.swing.ImageIcon;

public class InitialMessage extends Message
{
    private String status;
    private Color fontColor;
    private ImageIcon image;
    
    public InitialMessage(String sender, String status, Color fontColor, ImageIcon image)
    {
        super(sender);
        this.status = status;
        this.fontColor = fontColor;
        this.image = image;
    }

    public String getStatus()
    {
        return status;
    }

    public Color getFontColor()
    {
        return fontColor;
    }

    public ImageIcon getImage()
    {
        return image;
    }

    @Override
    public Message asTextMessage()
    {
        return new TextMessage(sender, "entered");
    }

}
