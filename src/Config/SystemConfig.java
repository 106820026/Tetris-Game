package Config;

import org.dom4j.Element;

import java.awt.*;
import java.util.*;
import java.util.List;

public class SystemConfig {
    private static int maxbit, // 數字最大位數
            maxrow, // 排行榜最多5行
            levelup, // 升等所需經驗值
            MIN_X, // 俄羅斯方塊窗體起始x
            MIN_Y, // 俄羅斯方塊窗體起始y
            MAX_X, // 俄羅斯方塊窗體結束x
            MAX_Y; // 俄羅斯方塊窗體結束y
    private static List<Point[]> TYPE_CONFIG;
    private static List<Boolean> TYPE_ROTATE;
    private static HashMap<Integer, Integer> PLUS_POINT;
    private static HashMap<Integer, String> RANK_TITLE;

    public static int getMaxbit() {
        return maxbit;
    }

    public static int getMaxrow() {
        return maxrow;
    }

    public static int getLevelup() {
        return levelup;
    }

    public static int getMinX() {
        return MIN_X;
    }

    public static int getMinY() {
        return MIN_Y;
    }

    public static int getMaxX() {
        return MAX_X;
    }

    public static int getMaxY() {
        return MAX_Y;
    }

    public static List<Point[]> getTypeConfig() {
        return TYPE_CONFIG;
    }

    public static List<Boolean> getTypeRotate() {
        return TYPE_ROTATE;
    }

    public static HashMap<Integer, Integer> getPlusPoint() {
        return PLUS_POINT;
    }

    public static HashMap<Integer, String> getRankTitle() {
        return RANK_TITLE;
    }

    // 設定窗口元素
    public SystemConfig(Element system){
        this.maxbit = Integer.parseInt(system.attributeValue("maxbit"));
        this.maxrow = Integer.parseInt(system.attributeValue("maxrow"));
        this.levelup = Integer.parseInt(system.attributeValue("levelup"));
        this.MIN_X = Integer.parseInt(system.attributeValue("MIN_X"));
        this.MIN_Y = Integer.parseInt(system.attributeValue("MIN_Y"));
        this.MAX_X = Integer.parseInt(system.attributeValue("MAX_X"));
        this.MAX_Y = Integer.parseInt(system.attributeValue("MAX_Y"));
        List<Element> rects = system.elements("rect");
        TYPE_ROTATE = new ArrayList<Boolean>(rects.size());
        TYPE_CONFIG = new ArrayList<Point[]>(rects.size());
        for(Element rect: rects){
            TYPE_ROTATE.add(Boolean.parseBoolean(rect.attributeValue("rotate")));
            List<Element> points = rect.elements("point");
            Point[] pointArr = new Point[points.size()];
            for(int i = 0; i < points.size(); i++){
                int x = Integer.parseInt(points.get(i).attributeValue("x"));
                int y = Integer.parseInt(points.get(i).attributeValue("y"));
                pointArr[i] = new Point(x, y);
            }
            TYPE_CONFIG.add(pointArr);
        }
        List<Element> plusPoints = system.elements("plusPoint");
        PLUS_POINT = new HashMap<Integer, Integer>();
        for(Element plusPoint: plusPoints){
            int rm = Integer.parseInt(plusPoint.attributeValue("rm"));
            int point = Integer.parseInt(plusPoint.attributeValue("point"));
            PLUS_POINT.put(rm, point);
        }
        List<Element> rankTitles = system.elements("rankTitle");
        RANK_TITLE = new HashMap<Integer, String>();
        for(Element rankTitle: rankTitles){
            int rank = Integer.parseInt(rankTitle.attributeValue("level"));
            String title = rankTitle.attributeValue("rank");
            RANK_TITLE.put(rank, title);
        }
    }
}
