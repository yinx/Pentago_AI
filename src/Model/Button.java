package Model;

import javax.swing.*;

/**
 * Created by tombe on 10/12/2015.
 */
public class Button extends JButton {
    private boolean direction;
    private int part;

    public Button(String text, boolean direction, int part) {
        super(text);
        this.direction = direction;
        this.part = part;
    }

    public boolean isDirection() {
        return direction;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public int getPart() {
        return part;
    }

    public void setPart(int part) {
        this.part = part;
    }
}
