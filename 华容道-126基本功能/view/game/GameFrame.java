package view.game;

import controller.GameController;
import model.MapModel;
import view.FrameUtil;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private GameController controller;
    private JButton restartBtn;
    private JButton loadBtn;
    private JButton upBtn;
    private JButton downBtn;
    private JButton leftBtn;
    private JButton rightBtn;

    private JLabel stepLabel;
    private GamePanel gamePanel;

    public GameFrame(int width, int height, MapModel mapModel) {
        this.setTitle("2025 CS109 Project Demo");
        this.setLayout(null);
        this.setSize(width, height);
        gamePanel = new GamePanel(mapModel);
        gamePanel.setLocation(30, height / 2 - gamePanel.getHeight() / 2);
        this.add(gamePanel);
        this.controller = new GameController(gamePanel, mapModel);

        this.restartBtn = FrameUtil.createButton(this, "Restart", new Point(gamePanel.getWidth() + 80, 120), 80, 50);
        this.loadBtn = FrameUtil.createButton(this, "Load", new Point(gamePanel.getWidth() + 80, 210), 80, 50);
        this.upBtn = FrameUtil.createButton(this, "Up", new Point(gamePanel.getWidth() + 180, 50), 80, 50);
        this.downBtn = FrameUtil.createButton(this, "Down", new Point(gamePanel.getWidth() + 180, 140), 80, 50);
        this.leftBtn = FrameUtil.createButton(this, "Left", new Point(gamePanel.getWidth() + 180, 230), 80, 50);
        this.rightBtn = FrameUtil.createButton(this, "Right", new Point(gamePanel.getWidth() + 180, 320), 80, 50);
        this.stepLabel = FrameUtil.createJLabel(this, "Start", new Font("serif", Font.ITALIC, 22), new Point(gamePanel.getWidth() + 80, 70), 180, 50);
        gamePanel.setStepLabel(stepLabel);

        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            gamePanel.requestFocusInWindow();//enable key listener
        });
        this.loadBtn.addActionListener(e -> {
            String string = JOptionPane.showInputDialog(this, "Input path:");
            System.out.println(string);
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
