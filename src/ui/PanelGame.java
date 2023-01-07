package ui;

import Config.FrameConfig;
import Config.GameConfig;
import Config.LayerConfig;
import control.GameControl;
import control.PlayerControl;
import dto.GameDto;
import popupWindow.JFrameConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class PanelGame extends JPanel {
    private List<Layer> layers = null;
    private GameDto gameDto;
    private GameControl gameControl;
    private JButton start;
    private JButton setting;
    private JFrameConfig jFrameConfig;

    public PanelGame(GameDto gameDto, GameControl gameControl){
        // 獲取遊戲數據來源
        this.gameDto = gameDto;
        // 獲取遊戲控制
        this.gameControl = gameControl;
        // 初始化Layer
        this.initLayer();
        // 初始化組件
        initComponent();
        // 設定自由布局
        this.setLayout(null);
        // 新增鍵盤監聽
        this.addKeyListener(new PlayerControl(gameControl));
    }

   // 初始化按鍵監聽
    private void initComponent(){
        // 初始化按鈕
        this.start = new JButton(Img.startBtn);
        this.setting = new JButton(Img.settingBtn);
        // 設定按鈕在面板上的位置
        this.start.setBounds(GameConfig.getFrameConfig().getStartBtnX(), GameConfig.getFrameConfig().getStartBtnY(),
                GameConfig.getFrameConfig().getBtnW(), GameConfig.getFrameConfig().getBtnH());
        this.setting.setBounds(GameConfig.getFrameConfig().getSettingBtnX(), GameConfig.getFrameConfig().getSettingBtnY(),
                GameConfig.getFrameConfig().getBtnW(), GameConfig.getFrameConfig().getBtnH());
        // 將按鈕顯示在視窗上
        this.add(start);
        this.add(setting);
        // 按鈕增加監聽
        this.start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 關閉按鈕
                buttonSwitch(false);
                // 開始遊戲
                gameControl.start();
                // 返回焦點
                requestFocus();
            }
        });
        this.setting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 顯示設定面板
                jFrameConfig.setVisible(true);
                // 返回焦點
                requestFocus();
            }
        });
    }

    public void repaintButtonImage(){
        this.start.setIcon(Img.startBtn);
        this.setting.setIcon(Img.settingBtn);
    }

   // 初始化Layer
    private void initLayer(){
        // 獲得遊戲初始設定
        FrameConfig frameConfig = GameConfig.getFrameConfig();
        // 獲得Layer設定
        List<LayerConfig> layerConfigs = frameConfig.getLayerConfigs();
        // 創建Layers
        layers = new ArrayList<Layer>(layerConfigs.size());
        for(LayerConfig layer: layerConfigs){
            try {
                // 獲得類別
                Class<?> c = Class.forName(layer.getClassName());
                // 獲得建構函數
                Constructor<?> constructor = c.getConstructor(int.class, int.class, int.class, int.class);
                // 透過建構函數創建物件
                Layer l = (Layer) constructor.newInstance(layer.getX(), layer.getY(), layer.getW(), layer.getH());
                l.setGameDto(gameDto);
                l.setGameControl(gameControl);
                layers.add(l);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g){
        for(Layer layer : layers){
            layer.paint(g);
        }
        this.requestFocus();
    }

    // 開啟或關閉按鈕
    public void buttonSwitch(Boolean onOff){
        this.start.setEnabled(onOff);
        this.setting.setEnabled(onOff);
    }

    // 開啟或關閉按鈕
    public void settingButtonSwitch(Boolean onOff){
        this.setting.setEnabled(onOff);
    }

    public void setjFrameConfig(JFrameConfig jFrameConfig) {
        this.jFrameConfig = jFrameConfig;
    }
}
