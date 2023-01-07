package ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class Img{
    private static final String IMG_PATH = "img/";
    public static Image defaultBg, // 背景圖片
                        defaultWindow, // 框線圖片
                        defaultRankLogo, // 預設牌位logo
                        defaultRankText, // 預設Rank文字
                        defaultLocalText, // 預設Local文字
                        defaultNextText, // 預設Next文字
                        defaultNextXtText, // 預設Next next文字
                        defaultRemoveText, // 預設Remove文字
                        defaultScoreText, // 預設Score文字
                        defaultTimeText, // 預設Time文字
                        defaultNextLevelText, // 預設NextLevel文字
                        act, // 預設方塊顏色圖片
                        number, // 數字
                        rect, // 顏色頻譜圖
                        shadow, // 陰影
                        control, // 控制panel背景
                        pause; // 暫停
    public static ImageIcon startBtn, // 開始按鈕
                            settingBtn; // 設定按鈕
    public static Image[] NEXT_ACT; // 方塊圖片
    public static List<Image> RANK_LIST; // 段位圖片清單
    public static List<Image> BG_LIST; // 背景圖片清單


    static {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/style.dat"));
            String path = (String) ois.readObject();
            ois.close();
            setSkin(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void setSkin(String path){
        String skinPath = IMG_PATH + path;
        defaultBg = new ImageIcon(skinPath + "/background/background.jpg").getImage();
        defaultWindow = new ImageIcon(skinPath + "/window/Window.png").getImage();
        defaultRankLogo = new ImageIcon(skinPath + "trophy1.png").getImage();
        defaultRankText = new ImageIcon(skinPath + "/text/rank.png").getImage();
        defaultLocalText = new ImageIcon(skinPath + "/text/local.png").getImage();
        defaultNextText = new ImageIcon(skinPath + "/text/next.png").getImage();
        defaultNextXtText = new ImageIcon(skinPath + "/text/next2.png").getImage();
        defaultRemoveText = new ImageIcon(skinPath + "/text/remove.png").getImage();
        defaultScoreText = new ImageIcon(skinPath + "/text/score.png").getImage();
        defaultTimeText = new ImageIcon(skinPath + "/text/time.png").getImage();
        defaultNextLevelText = new ImageIcon(skinPath + "/text/nextLevel.png").getImage();
        pause = new ImageIcon(skinPath + "/text/pause.png").getImage();
        act = new ImageIcon(skinPath + "/block/rect.png").getImage();
        number = new ImageIcon(skinPath + "/num/num.png").getImage();
        rect = new ImageIcon(skinPath + "/block/rectt.png").getImage();
        shadow = new ImageIcon(skinPath + "/block/shadow.png").getImage();
        startBtn = new ImageIcon(skinPath + "/text/start.png");
        settingBtn = new ImageIcon(skinPath + "/text/setting.png");
        control = new ImageIcon(skinPath + "/background/control.png").getImage();
        
        NEXT_ACT = new Image[7];
        for (int i = 0; i < NEXT_ACT.length; i++) {
            NEXT_ACT[i] = new ImageIcon(skinPath + "/block/" + Integer.toString(i) + ".png").getImage();
        }

        // 讀取段位圖片
        File rankDir = new File(skinPath + "/rank/");
        File[] rankFiles = rankDir.listFiles();
        RANK_LIST = new ArrayList<Image>();
        for (File rankFile: rankFiles){
            if (!rankFile.isDirectory()) {
                RANK_LIST.add(new ImageIcon(rankFile.getPath()).getImage());
            }
        }

        // 讀取背景圖片
        File bgDir = new File(skinPath + "/background/");
        File[] bgFiles = bgDir.listFiles();
        BG_LIST = new ArrayList<Image>();
        for (File rankFile: bgFiles){
            if (!rankFile.isDirectory()) {
                BG_LIST.add(new ImageIcon(rankFile.getPath()).getImage());
            }
        }
    }
}
