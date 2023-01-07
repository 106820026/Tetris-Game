package ui;

import java.awt.*;

public class LayerNextXt extends Layer{
    public LayerNextXt(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void paint(Graphics g){
        super.createWindow(g);
        g.drawImage(Img.defaultNextXtText, 1025, 130, 150, 75, null);
        // 遊戲開始才畫
        if(gameDto.isStart()) {
            this.drawImageActLower(Img.NEXT_ACT[this.gameDto.getNextNext()], g);
        }
    }
}
