//import view.login.MissionSelectFrame;
import view.login.MissionSelectFrame;
import view.login.ModeSelectFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ModeSelectFrame modeSelectFrame = new ModeSelectFrame(280, 280);
            modeSelectFrame.setVisible(true);
        //  MissionSelectFrame missionSelectFrame = new MissionSelectFrame(800,480);
        //  missionSelectFrame.setVisible(true);
        });
    }
}
//todo:1.退出时自动保存√  2.多关卡  3.