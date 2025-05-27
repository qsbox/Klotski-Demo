package view.game;
import controller.GameController;
import model.MapModel;
import sound.MusicPlayer;
import view.FrameUtil;

import javax.swing.*;
import java.awt.*;

public class GuestGameFrame extends JFrame{

    private final JLabel infoLabel;
    private GameController controller;
    private JButton restartBtn;
    private JButton upBtn;
    private JButton downBtn;
    private JButton leftBtn;
    private JButton rightBtn;
    private JButton undoBtn;
    private final JButton startBtn;

    private JLabel exitLabel;
    private JLabel guestLabel;
    private JLabel stepLabel;
    private GamePanel gamePanel;
    private MusicPlayer musicPlayer;
    public static JLabel countDownLabel;

    public static JLabel countdownLabel; // 倒计时标签

    public GuestGameFrame(int width, int height, MapModel mapModel,MusicPlayer musicPlayer) {
        this.musicPlayer=musicPlayer;
        this.setTitle("2025 CS109 Project Demo");
        this.setSize(width, height);
        this.setLayout(null); // 禁用布局管理器

        BackgroundPanel backgroundPanel = new BackgroundPanel("src/picture/战斗背景.jpg");
        backgroundPanel.setLayout(null);
        this.setContentPane(backgroundPanel); // 替换默认的 ContentPane

        gamePanel = new GamePanel(mapModel);
        gamePanel.setLocation(70, height / 2 - gamePanel.getHeight() / 2);

        backgroundPanel.add(gamePanel); // 添加到背景面板
        this.controller = new GameController(gamePanel, mapModel);

        this.restartBtn = FrameUtil.createButton(this, "Restart", new Point(gamePanel.getWidth() + 130, 240), 80, 50);
        this.undoBtn = FrameUtil.createButton(this, "Undo", new Point(gamePanel.getWidth() + 130, 150), 80, 50);
        this.upBtn = FrameUtil.createButton(this, "Up", new Point(gamePanel.getWidth() + 240, 60), 80, 50);
        this.downBtn = FrameUtil.createButton(this, "Down", new Point(gamePanel.getWidth() + 240, 150), 80, 50);
        this.leftBtn = FrameUtil.createButton(this, "Left", new Point(gamePanel.getWidth() + 240, 240), 80, 50);
        this.rightBtn = FrameUtil.createButton(this, "Right", new Point(gamePanel.getWidth() + 240, 330), 80, 50);
        this.startBtn = FrameUtil.createButton(this, "TimeLimited", new Point(200,390), 160, 50);
        this.stepLabel = FrameUtil.createJLabel(this, "Step: 0", new Font("serif", Font.ITALIC, 22), new Point(70, 60), 180, 50);
        this.guestLabel = FrameUtil.createJLabel(this, "Guest", new Font("serif", Font.ITALIC, 22), new Point(70, 30), 180, 50);
        gamePanel.setStepLabel(stepLabel);
        this.infoLabel = new JLabel("Welcome to the game!");
        this.infoLabel.setFont(new Font("serif", Font.BOLD, 16));
        this.infoLabel.setBounds(200, 20, 200, 30);
        this.add(this.infoLabel);
        this.exitLabel = new JLabel("Exit");
        this.exitLabel.setFont(new Font("serif", Font.BOLD, 16));
        this.exitLabel.setBounds(160, 360, 200, 30);
        this.add(this.exitLabel);

        countDownLabel = new JLabel("Time: 60", SwingConstants.CENTER);
        countDownLabel.setFont(new Font("Arial", Font.BOLD, 20));
        countDownLabel.setBounds(10, 10, 100, 30);
        this.add(countDownLabel);

        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            gamePanel.requestFocusInWindow();//enable key listener
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

        //todo: add other button here
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


}
