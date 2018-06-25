package shared.model;

import java.awt.Color;
import java.io.Serializable;

public class User implements Serializable
{
    private static final long serialVersionUID = 20000;

    private String name;
    private Color color;

    public User( String name, Color color )
    {
        this.name = name;
        this.color = color;
    }

    public User() {
        this.name = "";
        this.color = Color.BLACK;
    }

    public boolean isValid() {
        if(name.equals("") || color.equals(Color.BLACK)) {
            return false;
        }

        return true;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor( Color color )
    {
        this.color = color;
    }
}
