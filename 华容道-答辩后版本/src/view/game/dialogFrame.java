package view.game;

import model.MapModel;
import view.FrameUtil;

import javax.swing.*;
import java.awt.*;
import User.User;
import controller.GameController;

public class dialogFrame extends JFrame{

    private JButton yesBtn;
    private JButton noBtn;
    private User user;
    private GameController controller;
    private JLabel saveLabel;


    public dialogFrame(User user, MapModel mapModel,GamePanel gamePanel){
        int[] save=new int[1];
        save[0]=1;
        this.setSize(280, 280);
        this.setTitle("Save or not?");
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.controller = new GameController(gamePanel, mapModel);
        this.setVisible(true);
        this.user=user;
        this.yesBtn= FrameUtil.createButton( this, "Yes", new Point(30, 140), 100, 40);
        this.noBtn= FrameUtil.createButton( this, "No", new Point(150, 140), 100, 40);
        this.saveLabel = new JLabel("Do you want to save?");
        this.saveLabel.setFont(new Font("serif", Font.BOLD, 16));
        this.saveLabel.setBounds(60, 60, 200, 40);
        this.add(saveLabel);
        this.yesBtn.addActionListener(e -> {
        controller.saveGame(user);
            System.out.println("已自动保存");
            this.dispose();
            System.exit(0);
        });
        this.noBtn.addActionListener(e -> {
            System.out.println("不保存");
            this.dispose();
            System.exit(0);

    });
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);}
}
