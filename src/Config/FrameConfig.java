package Config;

import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class FrameConfig {
    private String title;
    private int width, height, padding, startBtnX, startBtnY, settingBtnX, settingBtnY, btnW, btnH;
    private List<LayerConfig> layerConfigs;

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPadding() {
        return padding;
    }

    public List<LayerConfig> getLayerConfigs() {
        return layerConfigs;
    }

    public int getStartBtnX() {
        return startBtnX;
    }

    public int getStartBtnY() {
        return startBtnY;
    }

    public int getSettingBtnX() {
        return settingBtnX;
    }

    public int getSettingBtnY() {
        return settingBtnY;
    }

    public int getBtnW() {
        return btnW;
    }

    public int getBtnH() {
        return btnH;
    }

    // 設定窗口元素
    public FrameConfig(Element frame){
        this.title = frame.attributeValue("title");
        this.width = Integer.parseInt(frame.attributeValue("width"));
        this.height = Integer.parseInt(frame.attributeValue("height"));
        this.padding = Integer.parseInt(frame.attributeValue("padding"));
        this.startBtnX = Integer.parseInt(frame.attributeValue("startBtnX"));
        this.startBtnY = Integer.parseInt(frame.attributeValue("startBtnY"));
        this.settingBtnX = Integer.parseInt(frame.attributeValue("settingBtnX"));
        this.settingBtnY = Integer.parseInt(frame.attributeValue("settingBtnY"));
        this.btnW = Integer.parseInt(frame.attributeValue("btnW"));
        this.btnH = Integer.parseInt(frame.attributeValue("btnH"));
        List<Element> layers = frame.elements("layer");
        layerConfigs = new ArrayList<LayerConfig>(layers.size());
        for(Element layer: layers){
            LayerConfig lc = new LayerConfig(layer.attributeValue("className"),
                    Integer.parseInt(layer.attributeValue("x")),
                    Integer.parseInt(layer.attributeValue("y")),
                    Integer.parseInt(layer.attributeValue("w")),
                    Integer.parseInt(layer.attributeValue("h"))
            );
            layerConfigs.add(lc);
        }
    }
}
