package view.login;

import model.MapModel;
import sound.MusicPlayer;
import view.FrameUtil;
import view.game.BackgroundPanel;
import view.game.UserGameFrame;
import User.User;

import javax.swing.*;
import java.awt.*;
import java.awt.Point;

public class UserMissionSelectFrame extends JFrame{
    private  User user;
    private final JButton[] missionButtons = new JButton[10];
    private final String[] missionNames = {
            "横刀立马", "指挥若定", "将拥曹营", "齐头并进", "兵分三路",
            "雨声淅沥", "左右布兵", "桃花园中", "一路进军", "一路顺风"
    };
    private MusicPlayer musicPlayer;

    public UserMissionSelectFrame(int width, int height, User user, MusicPlayer musicPlayer) {
        this.setTitle("Mission Selection");
        this.setLayout(null);
        this.setSize(width, height);
        this.user = user;
        this.musicPlayer = musicPlayer;
        this.setLocationRelativeTo(null); // 窗口居中
        BackgroundPanel backgroundPanel = new BackgroundPanel("src/picture/选关背景.jpg");
        backgroundPanel.setLayout(null);
        this.setContentPane(backgroundPanel);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        final int BUTTON_WIDTH = 100;
        final int BUTTON_HEIGHT = 80;

        for (int i = 0; i < 5; i++) {
            int x = 40+150*i;
            missionButtons[i] = FrameUtil.createButton(this,
                    "<html>#" + (i+1) + "<br>" + missionNames[i] + "</html>",
                    new Point(x,80), BUTTON_WIDTH, BUTTON_HEIGHT
            );
        }

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
            mapModel.setMission(0);
            UserGameFrame userGameFrame = new UserGameFrame(800, 680, mapModel, user ,musicPlayer);
            userGameFrame.setVisible(true);
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
            mapModel.setMission(1);
            UserGameFrame userGameFrame = new UserGameFrame(800, 680, mapModel, user ,musicPlayer);
            userGameFrame.setVisible(true);
            this.setVisible(false);
        });
        missionButtons[2].addActionListener(e ->{
            MapModel mapModel = new MapModel(new int[][]{
                    {0, 4, 4, 0},
                    {3, 4, 4, 7},
                    {3, 5, 6, 7},
                    {1, 5, 6, 1},
                    {2, 2, 1, 1},
            });
            mapModel.setMission(2);
            UserGameFrame userGameFrame = new UserGameFrame(800, 680, mapModel, user ,musicPlayer);
            userGameFrame.setVisible(true);
            this.setVisible(false);
        });
        missionButtons[3].addActionListener(e ->{
            MapModel mapModel = new MapModel(new int[][]{
                    {3, 4, 4, 5},
                    {3, 4, 4, 5},
                    {1, 1, 1, 1},
                    {6, 2, 2, 7},
                    {6, 0, 0, 7},
            });
            mapModel.setMission(3);
            UserGameFrame userGameFrame = new UserGameFrame(800, 680, mapModel, user ,musicPlayer);
            userGameFrame.setVisible(true);
            this.setVisible(false);
        });
        missionButtons[4].addActionListener(e ->{
            MapModel mapModel = new MapModel(new int[][]{
                    {1, 4, 4, 1},
                    {3, 4, 4, 5},
                    {3, 4, 4, 5},
                    {6, 1, 1, 7},
                    {6, 0, 0, 7},
            });
            mapModel.setMission(4);
            UserGameFrame userGameFrame = new UserGameFrame(800, 680, mapModel, user ,musicPlayer);
            userGameFrame.setVisible(true);
            this.setVisible(false);
        });
        missionButtons[5].addActionListener(e ->{
            MapModel mapModel = new MapModel(new int[][]{
                    {3, 4, 4, 1},
                    {3, 4, 4, 1},
                    {6, 2, 2, 5},
                    {6, 7, 0, 5},
                    {1, 7, 0, 1},
            });
            mapModel.setMission(5);
            UserGameFrame userGameFrame = new UserGameFrame(800, 680, mapModel, user ,musicPlayer);
            userGameFrame.setVisible(true);
            this.setVisible(false);
        });
        missionButtons[6].addActionListener(e ->{
            MapModel mapModel = new MapModel(new int[][]{
                    {1, 4, 4, 1},
                    {1, 4, 4, 1},
                    {3, 5, 6, 7},
                    {3, 5, 6, 7},
                    {0, 2, 2, 0},
            });
            mapModel.setMission(6);
            UserGameFrame userGameFrame = new UserGameFrame(800, 680, mapModel, user ,musicPlayer);
            userGameFrame.setVisible(true);
            this.setVisible(false);
        });
        missionButtons[7].addActionListener(e ->{
            MapModel mapModel = new MapModel(new int[][]{
                    {1, 4, 4, 1},
                    {3, 4, 4, 7},
                    {3, 5, 6, 7},
                    {1, 5, 6, 1},
                    {0, 2, 2, 0},
            });

            mapModel.setMission(7);
            UserGameFrame userGameFrame = new UserGameFrame(800, 680, mapModel, user ,musicPlayer);
            userGameFrame.setVisible(true);
            this.setVisible(false);
        });
        missionButtons[8].addActionListener(e ->{
            MapModel mapModel = new MapModel(new int[][]{
                    {3, 4, 4, 1},
                    {3, 4, 4, 1},
                    {5, 7, 6, 1},
                    {5, 7, 6, 1},
                    {0, 2, 2, 0},
            });
            mapModel.setMission(8);
            UserGameFrame userGameFrame = new UserGameFrame(800, 680, mapModel, user ,musicPlayer);
            userGameFrame.setVisible(true);
            this.setVisible(false);
        });
        missionButtons[9].addActionListener(e ->{
            MapModel mapModel = new MapModel(new int[][]{
                    {5, 2, 2, 6},
                    {5, 1, 1, 6},
                    {3, 4, 4, 7},
                    {3, 4, 4, 7},
                    {1, 0, 0, 1},
            });
            mapModel.setMission(9);
            UserGameFrame userGameFrame = new UserGameFrame(800, 680, mapModel, user ,musicPlayer);
            userGameFrame.setVisible(true);
            this.setVisible(false);
        });

    }
}
