package Service;

import Config.GameConfig;
import control.GameControl;
import dto.GameDto;
import dto.Player;
import entity.GameAct;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GameTetris {
    private boolean isSwitched;
    private GameDto gameDto;
    private GameControl gameControl;
    private Random random = new Random();
    private HashMap<Integer, Integer> PLUS_POINT = GameConfig.getSystemConfig().getPlusPoint();

    public GameTetris(GameDto gameDto, GameControl gameControl) {
        this.gameDto = gameDto;
        this.gameControl = gameControl;
    }

    public void keyUp() {
        // 遊戲開始才能移動
        if(this.gameDto.isStart()){
            this.gameDto.getGameAct().rotate(this.gameDto.getGameMap());
        }
    }

    public boolean keyDown() {
        // 遊戲開始才能移動
        if(this.gameDto.isStart()){
            // 正確向下移動
            if (this.gameDto.getGameAct().move(0, 1, this.gameDto.getGameMap())) {
                return true;
            }
            // 獲取地圖
            boolean[][] gameMap = this.gameDto.getGameMap();
            // 獲取方塊
            Point[] act = this.gameDto.getGameAct().getActPoints();
            // 將方塊家入地圖陣列
            for (Point point : act) {
                gameMap[point.x][point.y] = true;
            }

            // 判斷消行與獲取消行數
            int plusExp = this.plusExp(gameMap);
            if(plusExp > 0){
                plusPoint(plusExp);
            }
            // 產生下一個方塊
            this.gameDto.getGameAct().initAct(this.gameDto.getNext());
            // 刷新下一個和下下個方塊
            this.updateAct();
            // 交換方塊刷新
            isSwitched = false;
            // 判斷遊戲是否結束
            if(isOver(gameMap)){
                // 遊戲設為結束
                this.gameDto.setStart(false);
                // 方塊陰影關閉
                this.gameDto.setShadow(false);
                // 關閉交換方塊
                isSwitched = true;
            }
        }
        return false;
    }

    // 增加分數
    private void plusPoint(int removeLine){
        // 更新分數
        this.gameDto.setNowPoint(this.gameDto.getNowPoint() + PLUS_POINT.get(removeLine));
        // 更新消行
        this.gameDto.setRmLine(this.gameDto.getRmLine() + removeLine);
        // 更新等級
        this.gameDto.setLevel(this.gameDto.getNowPoint()/300 + 1);
    }

    // 判斷是否需要消行 與 計算消行數
    private int plusExp(boolean[][] gameMap) {
        int removeLine = 0;
        // 依序掃描每一行 看有沒有要消行
        for (int y = 0; y < gameMap[0].length; y++) {
            if(canRemoveLine(y, gameMap)){
                this.removeLine(y, gameMap);
                removeLine++;
            }
        }
        return removeLine;
    }

    // 執行消行動作
    private void removeLine(int rowNumber, boolean[][] gameMap) {
        for (int x = 0; x < gameMap.length; x++) {
            for (int y = rowNumber ; y > 0 ; y--) {
                gameMap[x][y] = gameMap[x][y - 1]; // 將上面行數往下移
            }
            gameMap[x][0] = false;
        }
    }

    // 判斷是否能消行
    private boolean canRemoveLine(int y, boolean[][] gameMap) {
        // 依序掃描每一行 看有沒有要消行
        for (int x = 0; x < gameMap.length; x++) {
            // 只要有false就不需要消
            if(!gameMap[x][y]){
                return false;
            }
        }
        return true;
    }

    // 判斷遊戲結束
    private boolean isOver(boolean[][] map){
        Point[] act = this.gameDto.getGameAct().getActPoints();
        for (int i = 0; i < act.length; i++) {
            if(map[act[i].x][act[i].y]){
                return true;
            }
        }
        return false;
    }

    public void keyLeft() {
        // 遊戲開始才能移動
        if(this.gameDto.isStart() && !this.gameDto.isPause()){
            this.gameDto.getGameAct().move(-1, 0, this.gameDto.getGameMap());
        }
    }

    public void keyRight() {
        // 遊戲開始才能移動
        if(this.gameDto.isStart() && !this.gameDto.isPause()){
            this.gameDto.getGameAct().move(1, 0, this.gameDto.getGameMap());
        }
    }


    // 更新下下個方塊 並將下下個方塊設為下一個
    private void updateAct(){
        // 設定下一個方塊為下下個方塊
        this.gameDto.setNext(this.gameDto.getNextNext());
        // 產生新的下下個方塊
        this.gameDto.setNextNext(random.nextInt(7));
    }

    public void keyPause() {
        // 遊戲開始才能暫停
        if(this.gameDto.isStart()){
            this.gameDto.setPause(!this.gameDto.isPause());
        }
    }

    public void keyShadow() {
        if(this.gameDto.isStart() && !this.gameDto.isPause()) {
            this.gameDto.setShadow(!this.gameDto.isShadow());
        }
    }

    // 設定紀錄
    public void setRecord(List<Player> players){
        this.gameDto.setRecord(players);
    }

    // 方塊快速到底
    public void quickDown() {
        if(this.gameDto.isStart() && !this.gameDto.isPause()) {
            while (this.keyDown()) ;
        }
    }

    // 與下一個方塊做交換
    public void keySwitch() {
        if(this.gameDto.isStart() && !this.gameDto.isPause()) {
            // 已經交換過或是相同方塊就不可再交換
            if (!isSwitched && !(this.gameDto.getGameAct().getTypeCode() == this.gameDto.getNext())) {
                int buffer = this.gameDto.getGameAct().getTypeCode();
                this.gameDto.getGameAct().initAct(this.gameDto.getNext());
                this.gameDto.setNext(buffer);
                // 此輪不可再交換方塊
                isSwitched = true;
            }
        }
    }

    // 開始遊戲
    public void start() {
        // 產生目前方塊
        this.gameDto.setGameAct(new GameAct(this, random.nextInt(7)));
        // 產生下一個方塊
        this.gameDto.setNext(random.nextInt(7));
        // 產生下下個方塊
        this.gameDto.setNextNext(random.nextInt(7));
        // 開啟陰影
        this.gameDto.setShadow(true);
        // 將遊戲設定為開始
        this.gameDto.setStart(true);
    }
}
