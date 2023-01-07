package ui;

import Config.FrameConfig;
import Config.GameConfig;
import Config.SystemConfig;
import control.GameControl;
import dto.GameDto;

import java.awt.*;

abstract public class Layer {
    private static FrameConfig frameConfig;
    private static SystemConfig systemConfig;
    static{
        frameConfig = GameConfig.getFrameConfig();
        systemConfig = GameConfig.getSystemConfig();
    }
    protected int x, y, w, h;
    protected GameDto gameDto;
    protected GameControl gameControl;
    // 數字前綴補空格用
    private int numberIndex;
    protected final static int padding = frameConfig.getPadding();
    private static final Image window = Img.defaultWindow;
    private final static int windowW = window.getWidth(null);
    private final static int windowH = window.getHeight(null);
    private final static int numberW = Img.number.getWidth(null) / 10;
    private final static int numberH = Img.number.getHeight(null);
    // 值曹寬度
    private final static int rectW = Img.rect.getWidth(null);
    // 值曹高度
    private final static int rectH = Img.rect.getHeight(null);
    // 數字最大位數
    protected final int MAX_BIT = systemConfig.getMaxbit();
    // 紀錄最大位數
    protected final int MAX_ROW = systemConfig.getMaxrow();

    public Layer(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void createWindow(Graphics g){
        //左上
        g.drawImage(window, x, y, x + padding, y + padding, 0, 0, padding, padding, null);
        //中上
        g.drawImage(window, x + padding, y, x + w - padding, y + padding, padding, 0, windowW - padding, padding, null);
        //右上
        g.drawImage(window, x + w - padding, y, x + w, y + padding, windowW - padding, 0, windowW, padding, null);
        //左中
        g.drawImage(window, x, y + padding, x + padding, y + h - padding, 0, padding, padding, windowH - padding, null);
        //正中
        g.drawImage(window, x + padding, y + padding, x + w - padding, y + h - padding, padding, padding, windowW - padding, windowH - padding, null);
        //右中
        g.drawImage(window, x + w - padding, y + padding, x + w, y + h - padding, windowW - padding, padding, windowW, windowH - padding, null);
        //左下
        g.drawImage(window, x, y + h - padding, x + padding, y + h, 0, windowH - padding, padding, windowH, null);
        //中下
        g.drawImage(window, x + padding, y + h - padding, x + w - padding, y + h, padding,  windowH - padding, windowW - padding, windowH, null);
        //右下
        g.drawImage(window, x + w - padding, y + h - padding, x + w, y + h, windowW - padding, windowH - padding, windowW, windowH, null);
    }

    // 畫圖片在中央
    protected void drawImageActCenter(Image image, Graphics g){
        int imgW = image.getWidth(null);
        int imgH = image.getHeight(null);
        // 計算置中位置
        int x = this.x + (this.w - imgW) / 2;
        int y = this.y + (this.h - imgH) / 2;
        g.drawImage(image, x, y, null);
    }

    // 畫圖片在中下方
    protected void drawImageActLower(Image image, Graphics g){
        int imgW = image.getWidth(null);
        int imgH = image.getHeight(null);
        // 計算置中位置
        int x = this.x + (this.w - imgW) / 2;
        int y = this.y + (this.h - imgH) / 2 + padding * 2;
        g.drawImage(image, x, y, null);
    }

    // 畫出數字
    protected void drawNumber(int x, int y, int num, int maxBit, Graphics g){
        String numStr = Integer.toString(num);
        for (int i = 0; i < numStr.length(); i++) {
            int bit = numStr.charAt(i) - '0';
            g.drawImage(Img.number,
                    x + (i + maxBit - numStr.length()) * numberW, y,
                    x + (i + maxBit - numStr.length() + 1) * numberW, y + numberH,
                    bit * numberW, 0,
                    (bit + 1) * numberW, numberH,
                    null);
        }
    }

    // 劃出值槽
    protected void drawRect(int y, double percent, Graphics g){
        int rectX = this.x + padding * 2;
        int rectY = this.y + y + 10;
        // 畫出外框黑色值曹
        g.setColor(Color.BLACK);
        g.drawRect(rectX - 2, rectY, this.w - padding * 4, rectH + 4);
        // 畫白框
        g.setColor(Color.white);
        g.drawRect(rectX - 1, rectY + 1, this.w - padding * 4 - 2, rectH + 2);
        // 畫出內部黑色值曹
        g.setColor(Color.BLACK);
        g.fillRect(rectX, rectY + 2, this.w - padding * 4 - 3, rectH);
        // 畫出值曹
        int w = (int)(percent * (this.w - padding * 4 - 3));
        int colorIndex = (int)(percent * rectW);
        g.drawImage(Img.rect, rectX, rectY + 2, rectX + w, rectY + 2 + rectH,
                colorIndex, 0, colorIndex + 1, rectH, null);
        // Next level文字
        g.drawImage(Img.defaultNextLevelText, 855, 452, 100, 50, null);
    }

    // 繪製紀錄
    protected void drawRectData(int y, String name, int point, String rank, double percent, int maxBit, Graphics g){
        int rectX = this.x + padding * 2;
        int rectY = this.y + y;
        // 畫出外框黑色值槽
        g.setColor(Color.BLACK);
        g.drawRect(rectX - 2, rectY, this.w - padding * 4, rectH + 4);
        // 畫白框
        g.setColor(Color.white);
        g.drawRect(rectX - 1, rectY + 1, this.w - padding * 4 - 2, rectH + 2);
        // 畫出內部黑色值槽
        g.setColor(Color.BLACK);
        g.fillRect(rectX, rectY + 2, this.w - padding * 4 - 3, rectH);
        // 畫出值槽
        int w = (int)(percent * (this.w - padding * 4 - 3));
        int colorIndex = (int)(percent * rectW) - 1;
        g.drawImage(Img.rect, rectX, rectY + 2, rectX + w, rectY + 2 + rectH,
        colorIndex, 0, colorIndex + 1, rectH, null);
        // 顯示玩家名稱
        g.setColor(Color.white);
        g.setFont(new Font("微軟正黑體", 0, 20));
        g.drawString(name, rectX + 6, rectY + 23);
        // 顯示段位
        g.drawString(rank, this.w / 5 * 3, rectY + 23);
        // 顯示分數
        String strPoint = Integer.toString(point);
        for (int i = 0; i < strPoint.length(); i++) {
            if(maxBit - i  <= strPoint.length()){
                g.drawString(strPoint, this.w / 4 * 3 + numberIndex * 10, rectY + 23);
            }else{
                numberIndex++;
            }
        }
        numberIndex = 0;
    }

    public void setGameDto(GameDto gameDto) {
        this.gameDto = gameDto;
    }

    public void setGameControl(GameControl gameControl) {
        this.gameControl = gameControl;
    }

    abstract protected void paint(Graphics g);

}
