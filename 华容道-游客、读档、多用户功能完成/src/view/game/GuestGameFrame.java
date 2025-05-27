package view.game;
import controller.GameController;
import model.MapModel;
import view.FrameUtil;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GuestGameFrame extends JFrame{

    private final JLabel infoLabel;
    private GameController controller;
    private JButton restartBtn;
    private JButton upBtn;
    private JButton downBtn;
    private JButton leftBtn;
    private JButton rightBtn;
    private JButton undoBtn;
    private JLabel exitLabel;
    private JLabel guestLabel;
    private JLabel stepLabel;
    private GamePanel gamePanel;

    public GuestGameFrame(int width, int height, MapModel mapModel) {
        this.setTitle("2025 CS109 Project Demo");
        this.setLayout(null);
        this.setSize(width, height);

        gamePanel = new GamePanel(mapModel);
        gamePanel.setLocation(70, height / 2 - gamePanel.getHeight() / 2);
        this.add(gamePanel);
        this.controller = new GameController(gamePanel, mapModel);

        this.restartBtn = FrameUtil.createButton(this, "Restart", new Point(gamePanel.getWidth() + 130, 240), 80, 50);
        this.undoBtn = FrameUtil.createButton(this, "Undo", new Point(gamePanel.getWidth() + 130, 150), 80, 50);
        this.upBtn = FrameUtil.createButton(this, "Up", new Point(gamePanel.getWidth() + 240, 60), 80, 50);
        this.downBtn = FrameUtil.createButton(this, "Down", new Point(gamePanel.getWidth() + 240, 150), 80, 50);
        this.leftBtn = FrameUtil.createButton(this, "Left", new Point(gamePanel.getWidth() + 240, 240), 80, 50);
        this.rightBtn = FrameUtil.createButton(this, "Right", new Point(gamePanel.getWidth() + 240, 330), 80, 50);
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

        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            gamePanel.requestFocusInWindow();//enable key listener
        });
        this.undoBtn.addActionListener(e -> {
            controller.undoLastMove();
            gamePanel.requestFocusInWindow();//enable key listener
        });

        this.upBtn.addActionListener(e -> {gamePanel.doMoveUp();
            gamePanel.requestFocusInWindow();});
        this.downBtn.addActionListener(e -> {gamePanel.doMoveDown();
            gamePanel.requestFocusInWindow();});
        this.leftBtn.addActionListener(e -> {gamePanel.doMoveLeft();
            gamePanel.requestFocusInWindow();});
        this.rightBtn.addActionListener(e -> {gamePanel.doMoveRight();
            gamePanel.requestFocusInWindow();});

        //todo: add other button here
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
