package ui;

import java.awt.*;

public class LayerRank extends Layer{
    public LayerRank(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void paint(Graphics g){
        super.createWindow(g);
        g.drawImage(Img.defaultRankLogo, 120, 95, 170, 170, null);
        g.drawImage(Img.defaultRankText, 25, 40, 150, 75, null);
        // 畫段位
        int rankIndex = this.gameDto.getLevel() - 1;
        if(rankIndex > Img.RANK_LIST.size() - 1){
            rankIndex = Img.RANK_LIST.size() - 1;
        }
        this.drawImageActCenter(Img.RANK_LIST.get(rankIndex), g);
    }
}
