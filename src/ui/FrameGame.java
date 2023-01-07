package ui;

import Config.FrameConfig;
import Config.GameConfig;

import javax.swing.*;

public class FrameGame extends JFrame {
    /*
    FrameGame建構子
    */
    public FrameGame(PanelGame panelGame){
        // 獲得遊戲初始設定
        FrameConfig frameConfig = GameConfig.getFrameConfig();
        //設定標題
        this.setTitle(frameConfig.getTitle());
        //設定視窗大小
        this.setSize(frameConfig.getWidth(), frameConfig.getHeight());
        //設定不可調整視窗大小
        this.setResizable(false);
        //預設關閉方式
        this.setDefaultCloseOperation(3);
        //窗體置中
        this.setLocationRelativeTo(null);
        //設定Panel
        this.setContentPane(panelGame);
        //顯示GameFrame
        this.setVisible(true);
    }
}
