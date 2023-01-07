package ui;

import dto.Player;

import java.awt.*;
import java.util.List;

public class LayerDisk extends Layer{
    // 值槽起始Y座標
    private static int START_Y;
    // 值槽外徑
    private static int DISK_RECT_H = Img.rect.getHeight(null) + 6;
    // 值槽間距
    private static int SPACE;

    public LayerDisk(int x, int y, int w, int h) {
        super(x, y, w, h);
        SPACE = (this.h - padding * 2 - 45 - DISK_RECT_H * 5) / 5;
        START_Y = padding + 45;
    }

    public void paint(Graphics g){
        // 繪製窗口
        super.createWindow(g);
        // 顯示數據
        this.showData(Img.defaultLocalText, this.gameDto.getRecord(), g);
    }

    private void showData(Image title, List<Player> players, Graphics g) {
        // 繪製窗口標題
        g.drawImage(title, 60, 290, 150, 75, null);
        // 獲取現在分數
        int nowPoint = this.gameDto.getNowPoint();
        // 顯示紀錄
        for (int i = 0; i < MAX_ROW; i++) {
            // 獲取玩家紀錄
            Player player = players.get(i);
            // 計算玩家分數與紀錄分數的比率
            double percent = (double)nowPoint/player.getPoint();
            // 如果破紀錄 則比率設為1
            percent = percent > 1 ? 1 : percent;
            // 繪製紀錄
            this.drawRectData(START_Y + i * (SPACE + DISK_RECT_H), player.getName(), player.getPoint(), player.getRank(), percent, MAX_BIT, g);
        }
    }
}
