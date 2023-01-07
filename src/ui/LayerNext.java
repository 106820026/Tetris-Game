package ui;

import java.awt.*;

public class LayerNext extends Layer{
    public LayerNext(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void paint(Graphics g){
        // 會製窗體
        super.createWindow(g);
        // 繪製窗口標題
        g.drawImage(Img.defaultNextText, 850, 130, 150, 75, null);
        // 遊戲開始才畫
        if(gameDto.isStart()){
            this.drawImageActLower(Img.NEXT_ACT[this.gameDto.getNext()], g);
        }
    }
}
