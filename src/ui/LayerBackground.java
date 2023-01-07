package ui;

import java.awt.*;

public class LayerBackground extends Layer{
    public LayerBackground(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void paint(Graphics g){
        int bgIndex = this.gameDto.getLevel() - 1;
        if(bgIndex > Img.BG_LIST.size() - 1){
            bgIndex = Img.BG_LIST.size() - 1;
        }
        // 畫段位
        g.drawImage(Img.BG_LIST.get(bgIndex), 0, 0, this.w, this.h, null);
    }
}
