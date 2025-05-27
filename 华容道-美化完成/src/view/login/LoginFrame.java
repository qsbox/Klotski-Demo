package view.login;

import User.User;
import model.MapModel;
import view.FrameUtil;
import view.game.BackgroundPanel;
import view.game.UserGameFrame;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;


public class LoginFrame extends JFrame {
    private JTextField username;
    private JTextField password;
    private JButton LoginBtn;
    private JButton registerBtn;
    private UserGameFrame userGameFrame;


    public LoginFrame(int width, int height) {
        this.setTitle("Login Frame");
        this.setSize(width, height);

        BackgroundPanel backgroundPanel = new BackgroundPanel("src/picture/登录背景.jpg");
        backgroundPanel.setLayout(null);
        this.setContentPane(backgroundPanel); // 替换默认的 ContentPane

        JLabel userLabel = FrameUtil.createJLabel(this, new Point(50, 20), 70, 40, "username:");
        JLabel passLabel = FrameUtil.createJLabel(this, new Point(50, 80), 70, 40, "password:");
        username = FrameUtil.createJTextField(this, new Point(120, 20), 120, 40);
        password = FrameUtil.createJTextField(this, new Point(120, 80), 120, 40);
        Boolean[] logincheck = {false};

        LoginBtn = FrameUtil.createButton(this, "Login", new Point(40, 140), 100, 40);
        registerBtn = FrameUtil.createButton(this, "Register", new Point(160, 140), 100, 40);

        LoginBtn.addActionListener(e -> {
            System.out.println("Username = " + username.getText());
            System.out.println("Password = " + password.getText());
            String path = String.format("save/%s/password.txt",username.getText());
            File file = new File(path);
            if(file.exists()){
                try {
                    if(password.getText().equals(Files.readString(Paths.get(path)).trim())){
                        logincheck[0] = true;
                    }else{
                        JOptionPane.showMessageDialog(this,"The password is wrong!");
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }else{
                JOptionPane.showMessageDialog(this,"The user does not exist!");
            }
            if(logincheck[0]){
                User user = new User(username.getText(),password.getText());
            MapModel mapModel = new MapModel(new int[][]{
                    {3, 4, 4, 7},
                    {3, 4, 4, 7},
                    {5, 2, 2, 6},
                    {5, 1, 1, 6},
                    {1, 0, 0, 1},
            });
            UserGameFrame userGameFrame = new UserGameFrame(600, 480, mapModel,user);
            userGameFrame.setVisible(true);
            this.setVisible(false);}

        });
        registerBtn.addActionListener(e -> {
            RegisterFrame registerFrame = new RegisterFrame(280,280);
            registerFrame.setVisible(true);

        });

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}

