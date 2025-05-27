import view.login.ModeSelectFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ModeSelectFrame modeSelectFrame = new ModeSelectFrame(280, 280);
            modeSelectFrame.setVisible(true);
        });
    }
}
//todo：1.登录选择√  2.账户验证√  3.鲁棒性√