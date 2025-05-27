package view.game;

import controller.GameController;
import model.Direction;
import model.MapModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * It is the subclass of ListenerPanel, so that it should implement those four methods: do move left, up, down ,right.
 * The class contains a grids, which is the corresponding GUI view of the matrix variable in MapMatrix.
 */
public class GamePanel extends ListenerPanel {
    private List<BoxComponent> boxes;
    private MapModel model;
    private GameController controller;
    private JLabel stepLabel;
    private int steps;
    private final int GRID_SIZE = 50;
    private BoxComponent selectedBox;


    public GamePanel(MapModel model) {
        boxes = new ArrayList<>();
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setSize(model.getWidth() * GRID_SIZE + 4, model.getHeight() * GRID_SIZE + 4);
        this.model = model;
        this.selectedBox = null;
        initialGame(model.getMatrix());
    }

    public void initialGame(int[][] matrix) {
        this.steps = 0;
        if (stepLabel != null) {
            stepLabel.setText(String.format("Step: %d", this.steps)); // 更新标签显示
        }
        //copy a map
        int[][] map = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < map.length; i++) {
            System.arraycopy(matrix[i], 0, map[i], 0, map[0].length);
        }
        //build Component
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                BoxComponent box = null;
                if (map[i][j] == 1) {
                    box = new BoxComponent(Color.orange, i, j);
                    box.setSize(GRID_SIZE, GRID_SIZE);
                    map[i][j] = 0;
                } else if (map[i][j] == 2) {
                    box = new BoxComponent(Color.BLACK, i, j);
                    box.setSize(GRID_SIZE * 2, GRID_SIZE);
                    map[i][j] = 0;
                    map[i][j + 1] = 0;
                } else if (map[i][j] == 3) {
                    box = new BoxComponent(Color.RED, i, j);
                    box.setSize(GRID_SIZE, GRID_SIZE * 2);
                    map[i][j] = 0;
                    map[i + 1][j] = 0;
                } else if (map[i][j] == 4) {
                    box = new BoxComponent(Color.WHITE, i, j);
                    box.setSize(GRID_SIZE * 2, GRID_SIZE * 2);
                    map[i][j] = 0;
                    map[i + 1][j] = 0;
                    map[i][j + 1] = 0;
                    map[i + 1][j + 1] = 0;
                }
                if (box != null) {
                    box.setLocation(j * GRID_SIZE + 2, i * GRID_SIZE + 2);
                    boxes.add(box);
                    this.add(box);
                }
            }
        }
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawLine(0,0,0,this.getHeight()-1);
        g2d.drawLine(this.getWidth()-1,0,this.getWidth()-1,this.getHeight()-1);
        g2d.drawLine(0,0,this.getWidth()-1,0);
        g2d.drawLine(0,this.getHeight()-1,this.getWidth()/4,this.getHeight()-1);
        g2d.drawLine(this.getWidth()*3/4,this.getHeight()-1,this.getWidth()-1,this.getHeight()-1);
        g2d.setStroke(new BasicStroke(10));
        g2d.setColor(Color.green);
        g2d.drawLine(this.getWidth()/4,this.getHeight()+1,this.getWidth()*3/4,this.getHeight()+1);
    }

    public void clearAll(){
        for (BoxComponent boxComponent:boxes){
            removeBoxFromPanel(boxComponent);
        }
        this.boxes.clear();
        this.repaint();
    }

    public BoxComponent removeBoxFromPanel(BoxComponent boxComponent){
        this.remove(boxComponent);
        this.revalidate();
        return boxComponent;
    }

    @Override
    public void doMouseClick(Point point) {
        Component component = this.getComponentAt(point);
        if (component instanceof BoxComponent clickedComponent) {
            if (selectedBox == null) {
                selectedBox = clickedComponent;
                selectedBox.setSelected(true);
            } else if (selectedBox != clickedComponent) {
                selectedBox.setSelected(false);
                clickedComponent.setSelected(true);
                selectedBox = clickedComponent;
            } else {
                clickedComponent.setSelected(false);
                selectedBox = null;
            }
        }
    }

    @Override
    public void doMoveRight() {
        System.out.println("Click VK_RIGHT");
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.RIGHT)) {
                afterMove();
            }
        }
    }

    @Override
    public void doMoveLeft() {
        System.out.println("Click VK_LEFT");
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.LEFT)) {
                afterMove();
            }
        }
    }

    @Override
    public void doMoveUp() {
        System.out.println("Click VK_Up");
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.UP)) {
                afterMove();
            }
        }
    }

    @Override
    public void doMoveDown() {
        System.out.println("Click VK_DOWN");
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.DOWN)) {
                afterMove();
            }
        }
    }

    public void showWinMessage(int moveCount) {
        // 显示胜利对话框
        JOptionPane.showMessageDialog(this,
                "恭喜你赢了！\n总共移动了 " + moveCount + " 步",
                "游戏胜利",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void afterMove() {
        this.steps++;
        this.stepLabel.setText(String.format("Step: %d", this.steps));
    }

    public void setStepLabel(JLabel stepLabel) {
        this.stepLabel = stepLabel;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public JLabel getStepLabel() {
        return stepLabel;
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public BoxComponent getSelectedBox() {
        return selectedBox;
    }

    public int getGRID_SIZE() {
        return GRID_SIZE;
    }

    public int getSteps() {
        return steps;
    }

    public void setSelectedBox(BoxComponent selectedBox) {
        this.selectedBox = selectedBox;
    }
}
