package ui;

import java.awt.*;

public class LayerAbout extends Layer{
    public LayerAbout(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void paint(Graphics g){
        super.createWindow(g);
        g.drawImage(Img.defaultTimeText, 820, 540, 150, 75, null);
    }
}
