package popupWindow;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class TextCtrl extends JTextField {
    private int keyCode;
    private String methodName;

    public TextCtrl(int x, int y, int w, int h, String methodName){
        // 初始化方法名
        this.methodName = methodName;
        // 初始化位置
        this.setBounds(x, y, w, h);
        // 設定輸入水平置中
        this.setHorizontalAlignment(HORIZONTAL);
        // 按鍵監聽
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                setKeyCode(e.getKeyCode());
            }
        });
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.setText(KeyEvent.getKeyText(keyCode));
        this.keyCode = keyCode;
    }

    public String getMethodName() {
        return methodName;
    }
}
