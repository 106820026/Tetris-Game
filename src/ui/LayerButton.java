package ui;

import java.awt.*;

public class LayerButton extends Layer{
    public LayerButton(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void paint(Graphics g){
        super.createWindow(g);
    }
}
