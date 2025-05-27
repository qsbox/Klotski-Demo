package view.login;

import view.FrameUtil;
import view.game.BackgroundPanel;

import javax.swing.*;
import java.awt.*;

public class MissionSelectFrame extends JFrame {
    private final JButton[] missionButtons = new JButton[10];
    private final String[] missionNames = {
            "横刀立马", "指挥若定", "将拥曹营", "齐头并进", "兵分三路",
            "雨声淅沥", "左右布兵", "桃花园中", "一路进军", "一路顺风"
    };

    public MissionSelectFrame(int width, int height) {
        this.setTitle("Mission Selection");
        this.setSize(width, height);

        BackgroundPanel backgroundPanel = new BackgroundPanel("src/picture/选关背景.jpg");
        backgroundPanel.setLayout(null);
        this.setContentPane(backgroundPanel); // 替换默认的 ContentPane

        this.setLocationRelativeTo(null); // 窗口居中

        // 统一按钮样式参数
        final int BUTTON_WIDTH = 100;
        final int BUTTON_HEIGHT = 80;
        final int START_Y = 80;

        // 创建第一排按钮
        for (int i = 0; i < 5; i++) {
            int x = 40+150*i;
            createMissionButton(i, x, START_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        }

        // 创建第二排按钮
        for (int i = 5; i < 10; i++) {
            int x = 40+150*(i-5);
            int y = 280;
            createMissionButton(i, x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
        }

    }

    private void createMissionButton(int index, int x, int y, int width, int height) {
        String buttonText = String.format("<html><center>#%d<br>%s</center></html>",
                index + 1, missionNames[index]);

        missionButtons[index] = FrameUtil.createButton(this, buttonText,
                new Point(x, y), width, height);

        // 设置按钮样式
        missionButtons[index].setFont(new Font("微软雅黑", Font.BOLD, 14));
        missionButtons[index].setBackground(new Color(240, 240, 240));
        missionButtons[index].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        // 添加点击事件
        final int missionNumber = index + 1;
        missionButtons[index].addActionListener(e ->
                JOptionPane.showMessageDialog(this, "启动关卡 #" + missionNumber));
    }
}