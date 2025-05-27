package view.game;

import User.User;
import controller.GameController;
import model.MapModel;
import sound.MusicPlayer;
import view.FrameUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserGameFrame extends JFrame {

    private final JLabel infoLabel;
    private GameController controller;
    private JButton restartBtn;
    private JButton loadBtn;
    private JButton saveBtn;
    private JButton upBtn;
    private JButton downBtn;
    private JButton leftBtn;
    private JButton rightBtn;
    private JButton undoBtn;
    private JLabel exitLabel;
    private User user;

    private JLabel stepLabel;
    private JLabel userLabel;
    private GamePanel gamePanel;
    private MusicPlayer musicPlayer;

    public UserGameFrame(int width, int height, MapModel mapModel, User user) {
        this.setTitle("2025 CS109 Project Demo");
        this.setSize(width, height);

        BackgroundPanel backgroundPanel = new BackgroundPanel("src/picture/战斗背景.jpg");
        backgroundPanel.setLayout(null);
        this.setContentPane(backgroundPanel); // 替换默认的 ContentPane

        this.user=user;
        gamePanel = new GamePanel(mapModel);
        gamePanel.setLocation(70, height / 2 - gamePanel.getHeight() / 2);
        backgroundPanel.add(gamePanel); // 添加到背景面板
        this.controller = new GameController(gamePanel, mapModel);
        musicPlayer = new MusicPlayer();
        musicPlayer.loadMusic("src/sound/3-qmms2.wav"); // 加载音乐文件
        musicPlayer.loop(); // 循环播放音乐

        this.restartBtn = FrameUtil.createButton(this, "Restart", new Point(gamePanel.getWidth() + 130, 60), 80, 50);
        this.undoBtn = FrameUtil.createButton(this, "Undo", new Point(gamePanel.getWidth() + 130, 150), 80, 50);
        this.upBtn = FrameUtil.createButton(this, "Up", new Point(gamePanel.getWidth() + 240, 60), 80, 50);
        this.downBtn = FrameUtil.createButton(this, "Down", new Point(gamePanel.getWidth() + 240, 150), 80, 50);
        this.leftBtn = FrameUtil.createButton(this, "Left", new Point(gamePanel.getWidth() + 240, 240), 80, 50);
        this.rightBtn = FrameUtil.createButton(this, "Right", new Point(gamePanel.getWidth() + 240, 330), 80, 50);
        this.stepLabel = FrameUtil.createJLabel(this, "Step: 0", new Font("serif", Font.ITALIC, 22), new Point(70, 60), 180, 50);
        this.loadBtn = FrameUtil.createButton(this, "Load", new Point(gamePanel.getWidth() + 130, 240), 80, 50);
        this.saveBtn = FrameUtil.createButton(this, "Save", new Point(gamePanel.getWidth() + 130, 330), 80, 50);
        this.userLabel = FrameUtil.createJLabel(this, "User:"+user.getUserName(), new Font("serif", Font.ITALIC, 22), new Point(70, 30), 280, 50);
        gamePanel.setStepLabel(stepLabel);
        this.infoLabel = new JLabel("Welcome to the game!");
        this.infoLabel.setFont(new Font("serif", Font.BOLD, 16));
        this.infoLabel.setBounds(200, 20, 200, 30);
        this.add(this.infoLabel);
        this.exitLabel = new JLabel("Exit");
        this.exitLabel.setFont(new Font("serif", Font.BOLD, 16));
        this.exitLabel.setBounds(160, 360, 200, 30);
        this.add(this.exitLabel);

        backgroundPanel.add(restartBtn);
        backgroundPanel.add(leftBtn);
        backgroundPanel.add(rightBtn);
        backgroundPanel.add(undoBtn);
        backgroundPanel.add(upBtn);
        backgroundPanel.add(downBtn);
        backgroundPanel.add(loadBtn);
        backgroundPanel.add(saveBtn);

        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            gamePanel.requestFocusInWindow();//enable key listener
        });
        this.loadBtn.addActionListener(e -> {
            String path = String.format("save/%s/data.txt",user.getUserName());
            String stepPath = String.format("save/%s/step.txt",user.getUserName());
            controller.loadGame(gamePanel,path,stepPath);
            int tempstep = gamePanel.getSteps();
            this.repaint();
            gamePanel.clearAll();
            gamePanel.initialGame(mapModel.getMatrix());
            gamePanel.setSteps(tempstep);
            this.stepLabel.setText(String.format("Step: %d", tempstep));
            gamePanel.requestFocusInWindow();
        });
        this.saveBtn.addActionListener(e -> {
            controller.saveGame(user);
            JOptionPane.showMessageDialog(gamePanel,"Game saved!");
            gamePanel.requestFocusInWindow();
        });
        this.undoBtn.addActionListener(e -> {
            controller.undoLastMove();
            gamePanel.requestFocusInWindow();//enable key listener
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    controller.saveGame(user);
                    System.out.println("已自动保存");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        this.upBtn.addActionListener(e -> {gamePanel.doMoveUp();
            gamePanel.requestFocusInWindow();});
        this.downBtn.addActionListener(e -> {gamePanel.doMoveDown();
            gamePanel.requestFocusInWindow();});
        this.leftBtn.addActionListener(e -> {gamePanel.doMoveLeft();
            gamePanel.requestFocusInWindow();});
        this.rightBtn.addActionListener(e -> {gamePanel.doMoveRight();
            gamePanel.requestFocusInWindow();});

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
