package popupWindow;

import control.GameControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JFrameSavePoint extends JFrame {
    private GameControl gameControl;
    private Font DEFAULT_FONT = new Font("微軟正黑體", 0, 14);
    private JLabel lbPoint,lbRank,lbName, lbErrMsg;
    private JButton btnSave;
    private JTextField txtFieldName;
    public JFrameSavePoint(GameControl gameControl) {
        this.gameControl = gameControl;
        // 設定標題
        this.setTitle("儲存分數");
        // 設定視窗大小
        this.setSize(250, 130);
        // 設定不可調大小
        this.setResizable(false);
        // 設定預設關閉
        this.setDefaultCloseOperation(1);
        // 設定自由布局
        this.setLayout(null);
        // 設定視窗置中
        this.setLocationRelativeTo(null);
        // 設定字型
        this.setFont(DEFAULT_FONT);
        // 初始化組件
        this.createComponent();
    }

    // 初始化組件
    private void createComponent(){
        // 初始化JLabel、JTextField、JButton
        this.lbPoint = new JLabel();
        this.lbRank = new JLabel();
        this.lbName = new JLabel();
        this.lbErrMsg = new JLabel();
        this.txtFieldName = new JTextField();
        this.btnSave = new JButton();
        // 設定JLabel文字
        this.lbPoint.setText("");
        this.lbRank.setText("");
        this.lbName.setText("您的名字: ");
        this.lbErrMsg.setText("");
        this.btnSave.setText("儲存");
        // 設定大小位置
        this.lbPoint.setBounds(8, 3, 150, 20);
        this.lbRank.setBounds(8, 23, 150, 20);
        this.lbName.setBounds(8, 43, 150, 20);
        this.lbErrMsg.setBounds(150, 3, 150, 20);
        this.txtFieldName.setBounds(70, 45, 150, 20);
        this.btnSave.setBounds(92, 70, 60, 20);
        // 設定顏色
        this.lbErrMsg.setForeground(Color.red);
        // 加入視窗中
        this.add(lbPoint);
        this.add(lbRank);
        this.add(lbName);
        this.add(lbErrMsg);
        this.add(txtFieldName);
        this.add(btnSave);
        // 新增按鍵監聽
        addClickListener();
    }

    // 事件監聽
    private void addClickListener(){
        this.btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 獲取輸入文字
                String name = txtFieldName.getText();
                if(name.length() < 1 || name.length() > 4) {
                    lbErrMsg.setText("名字限制1-4字");
                }else{
                    // 關閉保存分數視窗
                    setVisible(false);
                    // 保存分數
                    gameControl.saveRecord(name);
                    // 清除姓名欄位
                    txtFieldName.setText("");
                }
            }
        });
    }

    // 顯示保存分數視窗
    public void showSaveRecord(int point, String rank){
        this.lbPoint.setText("分數: " + point);
        this.lbRank.setText("您的段位:" + rank);
        this.setVisible(true);
    }
}
