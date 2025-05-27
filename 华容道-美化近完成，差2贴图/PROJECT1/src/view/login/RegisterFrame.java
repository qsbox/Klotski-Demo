package view.login;

import view.FrameUtil;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RegisterFrame extends JFrame{

    private JTextField username;
    private JTextField password;
    private JButton registerBtn;

    public RegisterFrame (int width,int height){
        this.setTitle("Register Frame");
        this.setLayout(null);
        this.setSize(width, height);
        JLabel userLabel = FrameUtil.createJLabel(this, new Point(50, 20), 70, 40, "username:");
        JLabel passLabel = FrameUtil.createJLabel(this, new Point(50, 80), 70, 40, "password:");
        username = FrameUtil.createJTextField(this, new Point(120, 20), 120, 40);
        password = FrameUtil.createJTextField(this, new Point(120, 80), 120, 40);
        registerBtn = FrameUtil.createButton(this, "Register", new Point(100, 140), 100, 40);

        registerBtn.addActionListener(e -> {
            System.out.println("Username = " + username.getText());
            System.out.println("Password = " + password.getText());
            if(username != null){
                if(password != null){
            String path = String.format("save/%s",username.getText());
            File file = new File(path);
            file.mkdirs();
            try {
                Files.writeString(Path.of(path+"/password.txt"), password.getText());
            } catch (IOException e1) {
                throw new RuntimeException(e1);
            }
            JOptionPane.showMessageDialog(this,"You have successfully registered as a user!");
            this.setVisible(false);}
                else {JOptionPane.showMessageDialog(this,"Please enter a valid password");}
            }
            else{
                JOptionPane.showMessageDialog(this,"Please enter a valid username");
            }


        });

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

}}
