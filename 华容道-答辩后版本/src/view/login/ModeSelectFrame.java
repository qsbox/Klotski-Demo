package view.login;

import model.MapModel;
import sound.MusicPlayer;
import view.FrameUtil;
import view.game.BackgroundPanel;
import view.game.GuestGameFrame;

import javax.swing.*;
import java.awt.*;


public class ModeSelectFrame extends JFrame {
    private JButton UserBtn;
    private JButton GuestBtn;
    private MusicPlayer musicPlayer;

    public ModeSelectFrame(int width, int height) {

        this.setTitle("Mode Selection");
        this.setSize(width, height);

        BackgroundPanel backgroundPanel = new BackgroundPanel("src/picture/模式背景.jpg");
        backgroundPanel.setLayout(null);
        this.setContentPane(backgroundPanel); // 替换默认的 ContentPane

        musicPlayer = new MusicPlayer();
        musicPlayer.loadMusic("src/sound/准备音乐.wav"); // 加载音乐文件
        musicPlayer.loop(); // 循环播放音乐

        UserBtn = FrameUtil.createButton(this, "User", new Point(80, 60), 100, 40);
        GuestBtn = FrameUtil.createButton(this, "Guest", new Point(80, 140), 100, 40);

        GuestBtn.addActionListener(e -> {
            GuestMissionSelectFrame guestMissionSelectFrame = new GuestMissionSelectFrame(800,480,musicPlayer);
            this.setVisible(false);
            guestMissionSelectFrame.setVisible(true);
//            musicPlayer.loadMusic("src/sound/战斗音乐.wav");
//            musicPlayer.loop();
        });
        UserBtn.addActionListener(e1 -> {
            LoginFrame loginFrame = new LoginFrame(280, 280,musicPlayer);
            loginFrame.setVisible(true);
            this.setVisible(false);
        });


        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }
}