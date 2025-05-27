import model.MapModel;
import view.game.GameFrame;
import view.login.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame(280, 280);
            loginFrame.setVisible(true);
            MapModel mapModel = new MapModel(new int[][]{
                    {2, 2, 1, 2, 2},
                    {3, 4, 4, 0, 0},
                    {3, 4, 4, 0, 1},
                    {1, 2, 2, 1, 0},

            });
            GameFrame gameFrame = new GameFrame(600, 450, mapModel);
            gameFrame.setVisible(false);
            loginFrame.setGameFrame(gameFrame);
        });
    }
}
