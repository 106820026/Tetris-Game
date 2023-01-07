package ui;

import Config.GameConfig;

import java.awt.*;

public class LayerPoint extends Layer{

    // 升級條件
    private final int LEVEL_UP = GameConfig.getSystemConfig().getLevelup();


    public LayerPoint(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void paint(Graphics g){
        super.createWindow(g);
        g.drawImage(Img.defaultScoreText, 830, 300, 150, 75, null);
        g.drawImage(Img.defaultRemoveText, 830, 370, 150, 75, null);
        // 畫出分數
        this.drawNumber(950, 325, this.gameDto.getNowPoint(), MAX_BIT, g);
        // 畫出消行
        this.drawNumber(950, 395, this.gameDto.getRmLine(), MAX_BIT, g);
        // 畫出值曹
        this.drawRect(140, (double) this.gameDto.getNowPoint() % LEVEL_UP / LEVEL_UP, g);
    }
}
