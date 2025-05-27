package view.login;

import model.MapModel;
import sound.MusicPlayer;
import view.FrameUtil;
import view.game.BackgroundPanel;
import view.game.GuestGameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.Point;

public class GuestMissionSelectFrame extends JFrame {
    private final JButton[] missionButtons = new JButton[10];
    private final String[] missionNames = {
            "横刀立马", "指挥若定", "将拥曹营", "齐头并进", "兵分三路",
            "雨声淅沥", "左右布兵", "桃花园中", "一路进军", "一路顺风"
    };

    public GuestMissionSelectFrame(int width, int height, MusicPlayer musicPlayer) {
        this.setTitle("Mission Selection");
        this.setLayout(null);
        this.setSize(width, height);
        this.setLocationRelativeTo(null); // 窗口居中
        BackgroundPanel backgroundPanel = new BackgroundPanel("src/picture/选关背景.jpg");
        backgroundPanel.setLayout(null);
        this.setContentPane(backgroundPanel);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // 统一按钮样式参数
        final int BUTTON_WIDTH = 100;
        final int BUTTON_HEIGHT = 80;

        // 创建第一排按钮
        for (int i = 0; i < 5; i++) {
            int x = 40+150*i;
            missionButtons[i] = FrameUtil.createButton(this,
                    "<html>#" + (i+1) + "<br>" + missionNames[i] + "</html>",
                    new Point(x,80), BUTTON_WIDTH, BUTTON_HEIGHT
            );
        }

// 创建第二排按钮
        for (int i = 5; i < 10; i++) {
            int x = 40+150*(i-5);
            int y = 280;
            missionButtons[i] = FrameUtil.createButton(this,
                    "<html>#" + (i+1) + "<br>" + missionNames[i] + "</html>",
                    new Point(x,y), BUTTON_WIDTH, BUTTON_HEIGHT
            );
        }


        for(int index=0;index<10;index++){

            missionButtons[index].setFont(new Font("微软雅黑", Font.BOLD, 14));
            missionButtons[index].setBackground(new Color(240, 240, 240));
            missionButtons[index].setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY, 1),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));}

        missionButtons[0].addActionListener(e ->{
            MapModel mapModel = new MapModel(new int[][]{
                    {3, 4, 4, 5},
                    {3, 4, 4, 5},
                    {7, 2, 2, 6},
                    {7, 1, 1, 6},
                    {1, 0, 0, 1},
            });
            GuestGameFrame guestGameFrame = new GuestGameFrame(800, 680, mapModel,musicPlayer);
            guestGameFrame.setVisible(true);
            this.setVisible(false);
        });
        missionButtons[1].addActionListener(e ->{
            MapModel mapModel = new MapModel(new int[][]{
                    {3, 4, 4, 6},
                    {3, 4, 4, 6},
                    {1, 2, 2, 1},
                    {5, 1, 1, 7},
                    {5, 0, 0, 7},
            });
            GuestGameFrame guestGameFrame = new GuestGameFrame(800, 680, mapModel,musicPlayer);
            this.setVisible(false);
            guestGameFrame.setVisible(true);
        });
        missionButtons[2].addActionListener(e ->{
            MapModel mapModel = new MapModel(new int[][]{
                    {0, 4, 4, 0},
                    {3, 4, 4, 7},
                    {3, 5, 6, 7},
                    {1, 5, 6, 1},
                    {2, 2, 1, 1},
            });
            GuestGameFrame guestGameFrame = new GuestGameFrame(800, 680, mapModel,musicPlayer);
            this.setVisible(false);
            guestGameFrame.setVisible(true);
        });
        missionButtons[3].addActionListener(e ->{
            MapModel mapModel = new MapModel(new int[][]{
                    {3, 4, 4, 5},
                    {3, 4, 4, 5},
                    {1, 1, 1, 1},
                    {6, 2, 2, 7},
                    {6, 0, 0, 7},
            });
            GuestGameFrame guestGameFrame = new GuestGameFrame(800, 680, mapModel,musicPlayer);
            this.setVisible(false);
            guestGameFrame.setVisible(true);
        });
        missionButtons[4].addActionListener(e ->{
            MapModel mapModel = new MapModel(new int[][]{
                    {1, 4, 4, 1},
                    {3, 4, 4, 5},
                    {3, 4, 4, 5},
                    {6, 1, 1, 7},
                    {6, 0, 0, 7},
            });
            GuestGameFrame guestGameFrame = new GuestGameFrame(800, 680, mapModel,musicPlayer);
            this.setVisible(false);
            guestGameFrame.setVisible(true);
        });
        missionButtons[5].addActionListener(e ->{
            MapModel mapModel = new MapModel(new int[][]{
                    {3, 4, 4, 1},
                    {3, 4, 4, 1},
                    {6, 2, 2, 5},
                    {6, 7, 0, 5},
                    {1, 7, 0, 1},
            });
            GuestGameFrame guestGameFrame = new GuestGameFrame(800, 680, mapModel,musicPlayer);
            this.setVisible(false);
            guestGameFrame.setVisible(true);
        });
        missionButtons[6].addActionListener(e ->{
            MapModel mapModel = new MapModel(new int[][]{
                    {1, 4, 4, 1},
                    {1, 4, 4, 1},
                    {3, 5, 6, 7},
                    {3, 5, 6, 7},
                    {0, 2, 2, 0},
            });
            GuestGameFrame guestGameFrame = new GuestGameFrame(800, 680, mapModel,musicPlayer);
            this.setVisible(false);
            guestGameFrame.setVisible(true);
        });
        missionButtons[7].addActionListener(e ->{
            MapModel mapModel = new MapModel(new int[][]{
                    {1, 4, 4, 1},
                    {3, 4, 4, 7},
                    {3, 5, 6, 7},
                    {1, 5, 6, 1},
                    {0, 2, 2, 0},
            });
            GuestGameFrame guestGameFrame = new GuestGameFrame(800, 680,mapModel ,musicPlayer);
            this.setVisible(false);
            guestGameFrame.setVisible(true);
        });
        missionButtons[8].addActionListener(e ->{
            MapModel mapModel = new MapModel(new int[][]{
                    {3, 4, 4, 1},
                    {3, 4, 4, 1},
                    {5, 7, 6, 1},
                    {5, 7, 6, 1},
                    {0, 2, 2, 0},
            });
            GuestGameFrame guestGameFrame = new GuestGameFrame(800, 680, mapModel,musicPlayer);
            this.setVisible(false);
            guestGameFrame.setVisible(true);
        });
        missionButtons[9].addActionListener(e ->{
            MapModel mapModel = new MapModel(new int[][]{
                    {5, 2, 2, 6},
                    {5, 1, 1, 6},
                    {3, 4, 4, 7},
                    {3, 4, 4, 7},
                    {1, 0, 0, 1},
            });
            GuestGameFrame guestGameFrame = new GuestGameFrame(800, 680, mapModel,musicPlayer);
            this.setVisible(false);
            guestGameFrame.setVisible(true);
        });
    }
}
