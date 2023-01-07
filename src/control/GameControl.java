package control;

import Service.GameTetris;
import dao.Record;
import dto.GameDto;
import dto.Player;
import popupWindow.JFrameConfig;
import popupWindow.JFrameSavePoint;
import ui.FrameGame;
import ui.PanelGame;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GameControl {
    private PanelGame panelGame;
    private GameDto gameDto;
    private GameTetris gameTetris;
    private Thread gameThread;
    private JFrameSavePoint jFrameSavePoint;
    private Record record;
    private HashMap<Integer, Method> actionList;
    private JFrameConfig jFrameConfig;

    public GameControl() {
        // 獲取遊戲數據來源
        this.gameDto = new GameDto();
        // 創建遊戲面板
        this.panelGame = new PanelGame(gameDto, this);
        // 創建遊戲邏輯
        this.gameTetris = new GameTetris(gameDto, this);
        // 創建分數儲存頁
        this.jFrameSavePoint = new JFrameSavePoint(this);
        // 創建遊戲設定面板
        this.jFrameConfig = new JFrameConfig(this, this.panelGame);
        // 給遊戲面板控制權
        this.panelGame.setjFrameConfig(jFrameConfig);
        // 讀取設定
        this.setControlConfig();
        // 讀取遊戲資料
        this.record = new Record();
        this.gameTetris.setRecord(record.loadData());
        //創建遊戲窗口
        FrameGame frameGame = new FrameGame(panelGame);
    }

    public void saveRecord(String name) {
        // 檢查等級
        int tempLevel = this.gameDto.getLevel();
        tempLevel = tempLevel > 9 ? 9 : tempLevel;
        // 建立新的Player
        Player player = new Player(name, this.gameDto.getNowPoint(), this.gameDto.getRANK_TITLE().get(tempLevel));
        // 將玩家記錄保存
        this.record.setData(player);
        // 重新讀取新紀錄
        this.gameTetris.setRecord(this.record.loadData());
        // 刷新畫面
        this.panelGame.repaint();
    }

    // 下落軌跡
    private class GameThread extends Thread{
        @Override
        public void run(){
            // 遊戲開始才執行下落軌跡
            while (gameDto.isStart()){
                try {
                    // 下落間格時間
                    Thread.sleep(gameDto.getSleepTime());
                    // 暫停時就continue
                    if(gameDto.isPause()){
                        // 開啟設定按鈕
                        panelGame.settingButtonSwitch(true);
                        continue;
                    }
                    // 關閉設定按鈕
                    panelGame.settingButtonSwitch(false);
                    // 方塊下落
                    gameTetris.keyDown();
                    // 刷新畫面
                    panelGame.repaint();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            // 遊戲結束執行後要做的事
            afterLoose();
        }

    }

    // 讀取玩家設定
    public void setControlConfig(){
        this.actionList = new HashMap<Integer, Method>();
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/control.dat"));
            HashMap<Integer, String> configSet = (HashMap<Integer, String>)ois.readObject();
            Set<Map.Entry<Integer, String>> entrySet = configSet.entrySet();
            for (Map.Entry<Integer, String> e: entrySet) {
                this.actionList.put(e.getKey(), this.gameTetris.getClass().getMethod(e.getValue()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 反射調用方法
    public void doActionByKeycode(int keycode){
        if(actionList.containsKey(keycode)){
            try {
                actionList.get(keycode).invoke(gameTetris);
                // 刷新畫面
                panelGame.repaint();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 遊戲開始
    public void start() {
        // 數據初始化
        this.gameDto.dtoInit();
        // 開始遊戲
        this.gameTetris.start();
        // 創建下落軌跡
        this.gameThread = new GameThread();
        // 啟動下落軌跡
        this.gameThread.start();
        // 刷新畫面
        this.panelGame.repaint();
    }

    // 遊戲結束
    private void afterLoose() {
        // 檢查等級
        int tempLevel = this.gameDto.getLevel();
        tempLevel = tempLevel > 9 ? 9 : tempLevel;
        System.out.println(this.gameDto.getRANK_TITLE().get(tempLevel));
        // 開啟開始按鈕
        panelGame.buttonSwitch(true);
        // 顯示儲存分數頁
        jFrameSavePoint.showSaveRecord(this.gameDto.getNowPoint(), this.gameDto.getRANK_TITLE().get(tempLevel));
    }
}
