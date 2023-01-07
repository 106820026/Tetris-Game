package entity;

import Config.GameConfig;
import Service.GameTetris;

import java.awt.*;
import java.security.cert.PolicyNode;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class GameAct {
    private static final int MIN_X = GameConfig.getSystemConfig().getMinX();
    private static final int MIN_Y = GameConfig.getSystemConfig().getMinY();
    private static final int MAX_X = GameConfig.getSystemConfig().getMaxX();
    private static final int MAX_Y = GameConfig.getSystemConfig().getMaxY();

    private int typeCode;
    private Point[] actPoints;
    private GameTetris gameTetris;
    private static List<Point[]> TYPE_CONFIG = GameConfig.getSystemConfig().getTypeConfig();
    private static List<Boolean> TYPE_ROTATE = GameConfig.getSystemConfig().getTypeRotate();

    public GameAct(GameTetris gameTetris, int typeCode){
        this.gameTetris = gameTetris;
        initAct(typeCode);
    }

    public void initAct(int typeCode){
        // 存取方塊編號
        this.typeCode = typeCode;
        // 獲得方塊
        Point[] points = TYPE_CONFIG.get(typeCode);
        // 建立一個新的方塊給actPoint
        actPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            actPoints[i] = new Point(points[i].x, points[i].y);
        }
    }

    public Point[] getActPoints() {
        return actPoints;
    }

    public int getTypeCode() {
        return typeCode;
    }

    public boolean move(int x, int y, boolean[][] gameMap){
        // 檢查是否有超出格子
        for(Point p: actPoints){
            if(isOverMap(p.x + x, p.y + y, gameMap)){
                return false;
            }
        }
        // 如果都沒超出 就可以移動
        for(Point p: actPoints){
            p.x = p.x + x;
            p.y = p.y + y;
        }
        return true;
    }

    public void rotate(boolean[][] gameMap){
        if(TYPE_ROTATE.get(this.typeCode)){
            for(Point p: actPoints){
                int bufferX = actPoints[0].y + actPoints[0].x - p.y;
                int bufferY = actPoints[0].y - actPoints[0].x + p.x;
                if(isOverMap(bufferX, bufferY, gameMap)){
                    return;
                }
            }
            for(Point p: actPoints){
                int bufferX = actPoints[0].y + actPoints[0].x - p.y;
                int bufferY = actPoints[0].y - actPoints[0].x + p.x;
                p.x = bufferX;
                p.y = bufferY;
            }
        }
    }

    public boolean isOverMap(int x, int y, boolean[][] gameMap){
        if(x > MAX_X || x < MIN_X || y > MAX_Y || y < MIN_Y || gameMap[x][y]){
            return true;
        }
        return false;
    }
}
