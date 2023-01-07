package Config;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.ArrayList;
import java.util.List;

public class GameConfig {
    private static FrameConfig frameConfig;
    private static SystemConfig systemConfig;

    static{
        try{
            // 建立讀取器
            SAXReader reader = new SAXReader();
            // 讀取XML文檔
            Document doc = reader.read("./data/config.xml");
            // 獲取根元素
            Element game = doc.getRootElement();
            // 設定窗口元素
            frameConfig = new FrameConfig(game.element("frame"));
            // 設定系統元素
            systemConfig = new SystemConfig(game.element("system"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 建構子私有化
    private GameConfig(){

    }

    public static FrameConfig getFrameConfig() {
        return frameConfig;
    }

    public static SystemConfig getSystemConfig() {
        return systemConfig;
    }
}
