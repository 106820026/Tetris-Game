package dto;

import Config.GameConfig;
import dao.Record;
import entity.GameAct;
import utility.GameFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class GameDto {

    // 遊戲是否開始
    private boolean start;
    // 陰影開關
    private boolean shadow;
    // 方塊下落間隔時間
    private int sleepTime;
    //暫停
    private boolean pause;
    // 玩家紀錄
    private List<Player> record;
    // 目前分數
    private int nowPoint;
    private GameAct gameAct;
    private boolean[][] gameMap;

    private int next ,nextNext, rmLine, level;
    private HashMap<Integer, String> RANK_TITLE = GameConfig.getSystemConfig().getRankTitle();

    public GameDto() {
        dtoInit();
    }

    public void dtoInit(){
        this.gameMap = new boolean[10][18];
        this.nowPoint = 0;
        this.rmLine = 0;
        this.level = 1;
        this.sleepTime = GameFunction.sleepTime(this.level);
    }

    public List<Player> getRecord() {
        return record;
    }

    public void setRecord(List<Player> record) {
        this.record = this.setFillRecord(record);
    }

    private List<Player> setFillRecord(List<Player> players) {
        // 如果是空的就創建資料
        if(players == null){
            players = new ArrayList<Player>();
        }
        // 如果未滿5筆資料 創建到五筆資料
        while(players.size() < 5){
            players.add(new Player("no data", 0, ""));
        }
        Collections.sort(players);
        return players;
    }

    public int getNowPoint() {
        return nowPoint;
    }

    public void setNowPoint(int nowPoint) {
        this.nowPoint = nowPoint;
    }

    public void setGameAct(GameAct gameAct) {
        this.gameAct = gameAct;
    }

    public GameAct getGameAct() {
        return gameAct;
    }

    public boolean[][] getGameMap() {
        return gameMap;
    }

    public void setGameMap(boolean[][] gameMap) {
        this.gameMap = gameMap;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getNextNext() {
        return nextNext;
    }

    public void setNextNext(int nextNext) {
        this.nextNext = nextNext;
    }

    public int getRmLine() {
        return rmLine;
    }

    public void setRmLine(int rmLine) {
        this.rmLine = rmLine;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        // 設定方塊下落
        this.sleepTime = GameFunction.sleepTime(level);
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isShadow() {
        return shadow;
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public HashMap<Integer, String> getRANK_TITLE() {
        return RANK_TITLE;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }
}
