package ui;

import java.awt.*;

public class LayerGame extends Layer{
    private static final int actSize = 32;
    public LayerGame(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void paint(Graphics g){
        // 繪製遊戲窗口
        super.createWindow(g);
        // 遊戲開始才繪製
        if(this.gameDto.getGameAct() != null){
            // 獲取目前方塊
            Point[] points = this.gameDto.getGameAct().getActPoints();
            // 繪製陰影
            this.drawShadow(points, g);
            // 繪製目前方塊
            drawActPoints(points, g);
            // 繪製地圖
            drawMap(g);
        }
        // 繪製暫停
        if(this.gameDto.isPause()){
            this.drawImageActCenter(Img.pause, g);
        }
    }

    // 繪製陰影
    private void drawShadow(Point[] points, Graphics g) {
        if(this.gameDto.isShadow()){
            int leftX = 9;
            int rightX = 0;
            int topY = 0;
            for(Point p : points){
                leftX = p.x < leftX ? p.x : leftX;
                rightX = p.x > rightX ? p.x : rightX;
                topY = topY < p.y ? p.y : topY;
            }
            // 畫陰影方塊
            g.drawImage(Img.shadow, this.x + padding + actSize * leftX, this.y + topY * actSize + padding,
                    actSize * (rightX - leftX + 1), this.h - padding * 2 - topY * actSize, null);
        }
    }

    // 繪製完整方塊
    private void drawActPoints(Point[] points, Graphics g){
        // 設定地圖方塊編號 遊戲輸掉後設為8
        int temp = this.gameDto.getGameAct().getTypeCode() + 1;
        temp = this.gameDto.isStart() ? temp : 8;
        for(Point p: points){
            drawActPoint(p.x, p.y, temp, g);
        }
    }

    // 繪製單個方塊
    private void drawActPoint(int x, int y, int imgIndex, Graphics g){
        g.drawImage(Img.act,
                this.x + x * 32 + padding,
                this.y + y * 32 + padding,
                this.x + x * 32 + padding + actSize,
                this.y + y * 32 + padding + actSize,
                actSize * imgIndex, 0, actSize * imgIndex + actSize, actSize, null);
    }

    // 繪製地圖
    private void drawMap(Graphics g){
        // 設定地圖方塊編號 遊戲輸掉後設為8
        int temp = 0;
        temp = this.gameDto.isStart() ? temp : 8;
        boolean[][] gameMap = this.gameDto.getGameMap();
        for (int i = 0; i < gameMap.length; i++) {
            for (int j = 0; j < gameMap[i].length; j++) {
                if(gameMap[i][j]){
                    drawActPoint(i, j, temp,  g);
                }
            }
        }
    }
}
