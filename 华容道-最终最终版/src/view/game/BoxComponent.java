package view.game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class BoxComponent extends JComponent {
    private Color color;
    private int row;
    private int col;
    private boolean isSelected;
    private Image image;
    private float currentX, currentY;
    private int targetX, targetY;
    private Timer animationTimer;
    private long animationStart;
    private static final int ANIMATION_DURATION = 300; // 毫秒


    public BoxComponent(String imagePath, Color color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
        isSelected = false;
        try {
            image = new ImageIcon(imagePath).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        animationTimer = new Timer(16, e -> updateAnimation());
        animationTimer.setRepeats(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
        Border border ;
        if(isSelected){
            border = BorderFactory.createLineBorder(Color.blue,3);
        }else {
            border = BorderFactory.createLineBorder(Color.DARK_GRAY, 1);
        }
        this.setBorder(border);
        if (image != null) {
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        this.repaint();
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void animateToPosition() {
        this.targetX = col * 50+2;
        this.targetY = row * 50+2;

        if (!animationTimer.isRunning()) {
            animationStart = System.currentTimeMillis();
            animationTimer.start();
        }
    }

    private void updateAnimation() {
        long elapsed = System.currentTimeMillis() - animationStart;
        float progress = Math.min(1.0f, (float)elapsed / ANIMATION_DURATION);

        // 使用缓动函数
        float easedProgress = easeOutQuad(progress);

        currentX = (col * 50) * easedProgress + (getX() * (1 - easedProgress));
        currentY = (row * 50) * easedProgress + (getY() * (1 - easedProgress));

        setLocation((int)currentX, (int)currentY);

        if (progress >= 1.0f) {
            animationTimer.stop();
            setLocation(col * 50+2, row * 50+2);
        }

        repaint();
    }

    private float easeOutQuad(float t) {
        return t * (2 - t);
    }
}
