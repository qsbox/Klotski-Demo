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
    private final JButton startBtn;
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
    public static JLabel countdownLabel; // 倒计时标签
    private GamePanel gamePanel;
    private MusicPlayer musicPlayer;

    public UserGameFrame(int width, int height, MapModel mapModel, User user,MusicPlayer musicPlayer) {
        this.musicPlayer=musicPlayer;
        this.setTitle("2025 CS109 Project Demo");
        this.setSize(width, height);
        musicPlayer.stop();
        musicPlayer.loadMusic("src/sound/战斗音乐.wav");
        musicPlayer.loop();


        BackgroundPanel backgroundPanel = new BackgroundPanel("src/picture/战斗背景.jpg");
        backgroundPanel.setLayout(null);
        this.setContentPane(backgroundPanel); // 替换默认的 ContentPane

        this.user=user;
        gamePanel = new GamePanel(mapModel);
        gamePanel.setLocation(70, height / 2 - gamePanel.getHeight() / 2);
        backgroundPanel.add(gamePanel); // 添加到背景面板
        this.controller = new GameController(gamePanel, mapModel);

        this.restartBtn = FrameUtil.createButton(this, "Restart", new Point(600, 220), 80, 50);
        this.undoBtn = FrameUtil.createButton(this, "Undo", new Point(600, 330), 80, 50);
        this.startBtn = FrameUtil.createButton(this, "TimeLimited", new Point(150,0), 160, 50);
        this.upBtn = FrameUtil.createButton(this, "Up", new Point(470, 400), 80, 50);
        this.downBtn = FrameUtil.createButton(this, "Down", new Point(470, 490), 80, 50);
        this.leftBtn = FrameUtil.createButton(this, "Left", new Point(340, 490), 80, 50);
        this.rightBtn = FrameUtil.createButton(this, "Right", new Point(600, 490), 80, 50);
        this.stepLabel = FrameUtil.createJLabel(this, "Step: 0", new Font("serif", Font.ITALIC, 22), new Point(70, 60), 180, 50);
        this.loadBtn = FrameUtil.createButton(this, "Load", new Point(340, 330), 80, 50);
        this.saveBtn = FrameUtil.createButton(this, "Save", new Point(340, 220), 80, 50);
        this.userLabel = FrameUtil.createJLabel(this, "User:"+user.getUserName(), new Font("serif", Font.ITALIC, 22), new Point(70, 130), 280, 50);
        gamePanel.setStepLabel(stepLabel);
        this.infoLabel = new JLabel("水墨华容道");
        this.infoLabel.setFont(new Font("serif", Font.BOLD, 50));
        this.infoLabel.setBounds(260, 800, 400, 50);
        this.add(this.infoLabel);
        this.exitLabel = new JLabel("Exit");
        this.exitLabel.setFont(new Font("serif", Font.BOLD, 32));
        this.exitLabel.setBounds(140, 500, 200, 30);
        this.add(this.exitLabel);
        countdownLabel = new JLabel("Time: 60", SwingConstants.CENTER);
        countdownLabel.setFont(new Font("Arial", Font.BOLD, 20));
        countdownLabel.setBounds(10, 10, 100, 30);
        this.add(countdownLabel);

        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            startBtn.setEnabled(true);
            gamePanel.requestFocusInWindow();//enable key listener
        });
        this.loadBtn.addActionListener(e -> {
            String path = String.format("save/%s/data%s.txt",user.getUserName(),mapModel.getMission());
            String stepPath = String.format("save/%s/step.txt",user.getUserName());
            String timePath = String.format("save/%s/time%s.txt",user.getUserName(),mapModel.getMission());
            controller.loadGame(gamePanel,path,stepPath,timePath);
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
        this.startBtn.addActionListener(e -> {
            controller.startGame();
            startBtn.setEnabled(false);
            gamePanel.requestFocusInWindow();//enable key listener
        });


        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e3) {
                try {
                    new dialogFrame(user, mapModel, gamePanel);
                }
             catch(
            Exception e4){
                e4.printStackTrace();}
        }

        });

        this.upBtn.addActionListener(e -> {gamePanel.doMoveUp();
            startBtn.setEnabled(false);
            gamePanel.requestFocusInWindow();});
        this.downBtn.addActionListener(e -> {gamePanel.doMoveDown();
            startBtn.setEnabled(false);
            gamePanel.requestFocusInWindow();});
        this.leftBtn.addActionListener(e -> {gamePanel.doMoveLeft();
            startBtn.setEnabled(false);
            gamePanel.requestFocusInWindow();});
        this.rightBtn.addActionListener(e -> {gamePanel.doMoveRight();
            startBtn.setEnabled(false);
            gamePanel.requestFocusInWindow();});

        this.setLocationRelativeTo(null);
    }
}
