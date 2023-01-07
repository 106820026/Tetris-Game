package popupWindow;

import control.GameControl;
import ui.Img;
import ui.PanelGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JFrameConfig extends JFrame {
    private JButton btnOk = new JButton("確定");
    private JButton btnCancel = new JButton("取消");
    private JButton btnApply = new JButton("套用");
    private TextCtrl[] keyTexts = new TextCtrl[8];
    private static final String[] METHOD_NAMEs = {
        "keyRight", "keyUp", "keyLeft", "keyDown",
        "quickDown", "keyPause", "keyShadow", "keySwitch"
    };
    private JLabel errMsg = new JLabel("");
    private JPanel styleView;
    private JList styleList;
    private DefaultListModel styleName = new DefaultListModel();
    private Image[] styleViewList;
    private PanelGame panelGame;
    private GameControl gameControl;

    public JFrameConfig(GameControl gameControl, PanelGame panelGame){
        // 獲取遊戲控制
        this.gameControl = gameControl;
        // 獲取主面板
        this.panelGame = panelGame;
        // 設定標題
        this.setTitle("設定完畢請按確定返回遊戲");
        // 設定大小
        this.setSize(575, 350);
        // 設定不可縮放
        this.setResizable(false);
        // 設定開啟時置中
        this.setLocationRelativeTo(null);
        // 設定邊界布局
        this.setLayout(new BorderLayout());
        // 設定預設關閉
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        // 初始化按鍵輸入框
        this.initTextCtrl();
        // 安裝主面板
        this.add(this.createMainPanel(), BorderLayout.CENTER);
        // 安裝按鈕面板
        this.add(this.createButtonPanel(), BorderLayout.SOUTH);
    }

    // 創建主面板(tab panel)
    private JTabbedPane createMainPanel() {
        JTabbedPane jtp = new JTabbedPane();
        jtp.add("按鍵設定", this.CreateControlPanel());
        jtp.add("造型設定", this.CreateStylePanel());
        return jtp;
    }

    // 創建控制設定面板
    private JPanel CreateControlPanel() {
        JPanel jp = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(Img.control, 0, 0, null);
            }
        };
        // 設定自由布局
        jp.setLayout(null);
        for (TextCtrl textCtrl: keyTexts) {
            jp.add(textCtrl);
        }
        return jp;
    }
    // 創建造型設定面板
    private JPanel CreateStylePanel() {
        // 創建JPanel並設定邊界佈局
        JPanel jp = new JPanel(new BorderLayout());
        // 設定檔案位置
        File dir = new File("img/");
        // 獲取所有檔案
        File[] files = dir.listFiles();
        // 初始化圖片
        styleViewList = new Image[files.length];
        // 依序加入檔案名稱
        for (int i = 0; i < styleViewList.length; i++) {
            // 造型名稱
            styleName.addElement(files[i].getName());
            // 對應預覽圖
            styleViewList[i] = new ImageIcon(files[i].getPath() + "\\view.png").getImage();
        }
        this.styleList = new JList(styleName);
        // 設定初始選中為1
        this.styleList.setSelectedIndex(0);
        // 設定滑鼠監聽
        this.styleList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                repaint();
            }
        });
        this.styleView = new JPanel(){
            // 畫預覽圖
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(styleViewList[styleList.getSelectedIndex()], 0, 0, null);
            }
        };
        jp.add(styleList, BorderLayout.WEST);
        jp.add(styleView, BorderLayout.CENTER);
        return jp;
    }

    // 按鈕面板
    private JPanel createButtonPanel(){
        JPanel jp = new JPanel();
        // 流式佈局
        jp.setLayout(new FlowLayout(FlowLayout.RIGHT));
        errMsg.setForeground(Color.red);
        jp.add(errMsg);
        jp.add(btnApply);
        jp.add(btnCancel);
        jp.add(btnOk);
        // 建立按鈕監聽
        addButtonActionListener();
        return jp;
    }

    // 按鈕監聽
    private void addButtonActionListener(){
        this.btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 儲存按鍵設定檔並輸出
                if(writeConfig()){
                    // 讀取配置
                    gameControl.setControlConfig();
                    // 重繪按鈕文字
                    panelGame.repaintButtonImage();
                    // 重繪主面板
                    panelGame.repaint();
                    // 重置錯誤訊息
                    errMsg.setText("");
                    // 關閉設定視窗
                    setVisible(false);
                }
            }
        });
        this.btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 重置錯誤訊息
                errMsg.setText("");
                // 重繪按鈕文字
                panelGame.repaintButtonImage();
                // 重繪主面板
                panelGame.repaint();
                // 關閉設定視窗
                setVisible(false);
            }
        });
        this.btnApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 儲存按鍵設定檔並輸出
                if(writeConfig()){
                    errMsg.setText("設定成功");
                }
            }
        });
    }

    // 初始化按鍵輸入框
    private void initTextCtrl(){
        int x = 10, y = 40, w = 64, h = 20;
        for (int i = 0; i < 4; i++) {
            keyTexts[i] = new TextCtrl(x, y, w, h, METHOD_NAMEs[i]);
            y += 42;
        }
        x = 480;
        y = 40;

        for (int i = 4; i < 8; i++) {
            keyTexts[i] = new TextCtrl(x, y, w, h, METHOD_NAMEs[i]);
            y += 42;
        }
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/control.dat"));
            HashMap<Integer, String> keyset = (HashMap<Integer, String>) ois.readObject();
            Set<Map.Entry<Integer, String>> entrySet = keyset.entrySet();
            for(Map.Entry<Integer, String> e: entrySet){
                for(TextCtrl tc: keyTexts) {
                    if(tc.getMethodName().equals(e.getValue())){
                        tc.setKeyCode(e.getKey());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    // 讀取按鍵控制
    private boolean writeConfig(){
        HashMap<Integer, String> keyset = new HashMap<Integer, String>();
        for (int i = 0; i < keyTexts.length; i++) {
            int keyCode = keyTexts[i].getKeyCode();
            // 如果有按鍵沒設置到
            if(keyCode == 0){
                this.errMsg.setText("未設置按鍵!");
                return false;
            }
            keyset.put(keyCode, keyTexts[i].getMethodName());
        }
        // 如果重複按鍵
        if(keyset.size() != 8){
            this.errMsg.setText("有重複按鍵!");
            return false;
        }
        try{
            Img.setSkin(this.styleName.get(this.styleList.getSelectedIndex()).toString());
            writeSettingConfig(keyset);
            writeStyleConfig();
        }catch (Exception e){
            this.errMsg.setText("有重複或未設置按鍵!");
            return false;
        }
        return true;
    }

    // 儲存造型設定
    private void writeSettingConfig(HashMap<Integer, String> keyset){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/control.dat"));
            oos.writeObject(keyset);
            oos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 儲存造型設定
    private void writeStyleConfig(){
        try{
            String stylePath = styleName.get(styleList.getSelectedIndex()).toString();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/style.dat"));
            oos.writeObject(stylePath);
            oos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
