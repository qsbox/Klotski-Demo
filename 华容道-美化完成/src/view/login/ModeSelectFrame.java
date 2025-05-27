package view.login;

import model.MapModel;
import view.FrameUtil;
import view.game.BackgroundPanel;
import view.game.GuestGameFrame;

import javax.swing.*;
import java.awt.*;


public class ModeSelectFrame extends JFrame {
    private JButton UserBtn;
    private JButton GuestBtn;

    public ModeSelectFrame(int width, int height) {

        this.setTitle("Mode Selection");
        this.setSize(width, height);

        BackgroundPanel backgroundPanel = new BackgroundPanel("src/picture/模式背景.jpg");
        backgroundPanel.setLayout(null);
        this.setContentPane(backgroundPanel); // 替换默认的 ContentPane


        UserBtn = FrameUtil.createButton(this, "User", new Point(80, 60), 100, 40);
        GuestBtn = FrameUtil.createButton(this, "Guest", new Point(80, 140), 100, 40);

        GuestBtn.addActionListener(e -> {
            MapModel mapModel = new MapModel(new int[][]{
                    {2, 2, 1, 1},
                    {3, 4, 4, 0},
                    {3, 4, 4, 0},
                    {1, 2, 2, 1},
                    {2, 2, 2, 2},
            });
            GuestGameFrame guestGameFrame = new GuestGameFrame(600, 480, mapModel);
            guestGameFrame.setVisible(true);
            this.setVisible(false);
        });
        UserBtn.addActionListener(e1 -> {
            LoginFrame loginFrame = new LoginFrame(280, 280);
            loginFrame.setVisible(true);
            this.setVisible(false);
        });


        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}