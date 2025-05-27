package controller;

import model.Direction;
import model.MapModel;
import record.MoveRecord;
import view.game.BoxComponent;
import view.game.GamePanel;
import User.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static view.game.GuestGameFrame.countDownLabel;
import static view.game.UserGameFrame.countdownLabel;
import static controller.GameController.updateCountdown;

/**
 * It is a bridge to combine GamePanel(view) and MapMatrix(model) in one game.
 * You can design several methods about the game logic in this class.
 */
public class GameController {
    private final GamePanel view;
    private final MapModel model;
    private final List<MoveRecord> moveHistory = new ArrayList<>();
    private boolean isRecordingEnabled = true;
    private Timer countdownTimer;
    private int timeLeft;
    private boolean isGameStarted = false;


    public GameController(GamePanel view, MapModel model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        this.timeLeft=60;
    }

    public void startGame() {
        isGameStarted = true; // 设置游戏已开始
        view.setVisible(true);
        // 启动倒计时
        countdownTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                if (countdownLabel!=null){
                updateCountdown(timeLeft);}
                if (countDownLabel!=null){
                updateCountDown(timeLeft);}
                if (timeLeft == 0) {
                    ((Timer)e.getSource()).stop();
                    endGame();
                }
            }
        });
        countdownTimer.start();


    }
    public static void updateCountdown(int timeLeft) {
        if(countdownLabel!=null){
            countdownLabel.setText("Time: " + timeLeft);}
    }
    public static void updateCountDown(int timeLeft) {
        if(countDownLabel!=null){
            countDownLabel.setText("Time: " + timeLeft);}
    }

    private void endGame() {
        JOptionPane.showMessageDialog(view, "Time's up! Game Over.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    public void restartGame() {
        System.out.println("Do restart game here");
        this.model.resetOriginalMatrix();
        this.view.clearAll();
        this.view.initialGame(model.getMatrix());
        moveHistory.clear();
        this.timeLeft=60;
        if (countDownLabel!=null){
            countDownLabel.setText(String.format("Time: %d", this.timeLeft));
        }
        if (countdownLabel!=null){
            countdownLabel.setText(String.format("Time: %d", this.timeLeft));}

        if (countdownTimer!=null){
        countdownTimer.stop();}
        isGameStarted=false;
    }

    public void saveGame(User user){
        int[][] saveMatrix = model.getMatrix();
        List<String> gameData = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for(int[] line:saveMatrix){
            for(int value:line){
                sb.append(value).append(" ");
            }
            gameData.add(sb.toString());
            sb.setLength(0);
        }
        for (String s :gameData){
            System.out.println(s);
        }
        String path = String.format("save/%s",user.getUserName());
        File file = new File(path);
        file.mkdirs();
        String step = view.getSteps()+"";
        try {
            String filename = String.format("data%d.txt", model.getMission());
            String filename1 = String.format("time%d.txt", model.getMission());
            Files.write(Path.of(path, filename), gameData);
            Files.writeString(Path.of(path + "/step.txt"), step);
            Files.writeString(Path.of(path, filename1),String.valueOf(this.timeLeft));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadGame(GamePanel gamePanel, String path, String stepPath , String timePath) {
        try {
            if (!Files.exists(Path.of(path))||!Files.exists(Path.of(stepPath))||!Files.exists(Path.of(timePath))) {
                System.err.println("存档文件不存在或格式损坏");
                JOptionPane.showMessageDialog(gamePanel,"The save is unavailable!");
                return;
            }

            List<String> lines;
            lines = Files.readAllLines(Path.of(path));

            if (lines.isEmpty()) {
                System.err.println("存档文件为空");
                JOptionPane.showMessageDialog(gamePanel,"The save is unavailable!");
                return;
            }

            // 4. 解析文件内容
            int rows = lines.size();
            String[] firstLineNumbers = lines.get(0).trim().split("\\s+");
            int cols = firstLineNumbers.length;

            int[][] saveMatrix = new int[rows][cols];
            boolean hasError = false;

            int time = Integer.parseInt(Files.readString(Path.of(timePath)).trim());
            if(time >60){
                hasError=true;
                System.err.println("倒计时文件异常");
                JOptionPane.showMessageDialog(gamePanel,"The save is unavailable!");
                return;
            }

            for (int i = 0; i < rows; i++) {
                String line = lines.get(i);
                try (Scanner scanner = new Scanner(line)) {
                    for (int j = 0; j < cols; j++) {
                        if (scanner.hasNextInt()) {
                            saveMatrix[i][j] = scanner.nextInt();
                        } else {
                            System.err.println("第 " + (i+1) + " 行数据不完整或格式错误");
                            JOptionPane.showMessageDialog(gamePanel,"The save is unavailable!");
                            hasError = true;
                            break;
                        }
                    }

                    if (scanner.hasNext()) {
                        System.err.println("第 " + (i+1) + " 行数据过多");
                        JOptionPane.showMessageDialog(gamePanel,"The save is unavailable!");
                        hasError = true;
                    }
                }
            }

            List<String> steps;
            steps = Files.readAllLines(Path.of(stepPath));

            if(steps.isEmpty()){
                System.err.println("步数不可为空");
                JOptionPane.showMessageDialog(gamePanel,"The save is unavailable!");
                hasError=true;
            }

            if (hasError) {
                System.err.println("存档文件内容损坏，无法读档");
            } else {
                model.setMatrix(saveMatrix);
                gamePanel.setSteps(Integer.parseInt(Files.readString(Path.of(stepPath)).trim()));
                this.timeLeft = time;
                updateCountdown(time);
                updateCountDown(time);
                System.out.println("读档成功");
                JOptionPane.showMessageDialog(gamePanel,"Game loaded!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public boolean doMove(int row, int col, Direction direction) {
        int boxType = model.getId(row, col);
        boolean moveSuccess = false;
        int nextRow = row + direction.getRow();
        int nextCol = col + direction.getCol();
        BoxComponent boxComponent=null;
        if (model.getId(row, col) == 1) {
            if (model.checkInHeightSize(nextRow) && model.checkInWidthSize(nextCol)) {
                if (model.getId(nextRow, nextCol) == 0) {
                    model.getMatrix()[row][col] = 0;
                    model.getMatrix()[nextRow][nextCol] = 1;
                    BoxComponent box = view.getSelectedBox();
                    boxComponent=view.getSelectedBox();
                    box.setRow(nextRow);
                    box.setCol(nextCol);
                    view.updateBoxPosition();
                    moveSuccess = true;
                }
            }
        }
        if (model.getId(row,col)==2){
            if (model.checkInHeightSize(nextRow) ) {
                if(direction.equals(Direction.RIGHT)) {
                    if(model.checkInWidthSize(nextCol+1)) {
                        if (model.getId(nextRow, nextCol+1) == 0) {
                            model.getMatrix()[row][col] = 0;
                            model.getMatrix()[row][col+1]=0;
                            model.getMatrix()[nextRow][nextCol] = 2;
                            model.getMatrix()[nextRow][nextCol+1] = 2;
                            BoxComponent box = view.getSelectedBox();
                            boxComponent=view.getSelectedBox();
                            box.setRow(nextRow);
                            box.setCol(nextCol);
                            view.updateBoxPosition();
                            moveSuccess = true;
                        }
                    }
                }
                else if (direction.equals(Direction.LEFT)){
                    if (model.checkInWidthSize(nextCol)){
                        if (model.getId(nextRow, nextCol) == 0) {
                            model.getMatrix()[row][col] = 0;
                            model.getMatrix()[row][col+1]=0;
                            model.getMatrix()[nextRow][nextCol] = 2;
                            model.getMatrix()[nextRow][nextCol+1] = 2;
                            BoxComponent box = view.getSelectedBox();
                            boxComponent=view.getSelectedBox();
                            box.setRow(nextRow);
                            box.setCol(nextCol);
                            view.updateBoxPosition();
                            moveSuccess = true;
                        }
                    }
                }
                else {
                    if (model.getId(nextRow,nextCol)==0 && model.getId(nextRow,nextCol+1)==0){
                        model.getMatrix()[row][col] = 0;
                        model.getMatrix()[row][col+1]=0;
                        model.getMatrix()[nextRow][nextCol] = 2;
                        model.getMatrix()[nextRow][nextCol+1] = 2;
                        BoxComponent box = view.getSelectedBox();
                        boxComponent=view.getSelectedBox();
                        box.setRow(nextRow);
                        box.setCol(nextCol);
                        view.updateBoxPosition();
                        moveSuccess = true;
                    }
                }
            }

        }
        if (model.getId(row,col)==3){
            if (model.checkInWidthSize(nextCol) ) {
                if(direction.equals(Direction.UP)) {
                    if(model.checkInHeightSize(nextRow)) {
                        if (model.getId(nextRow, nextCol) == 0) {
                            model.getMatrix()[row][col] = 0;
                            model.getMatrix()[row+1][col]=0;
                            model.getMatrix()[nextRow][nextCol] = 3;
                            model.getMatrix()[nextRow+1][nextCol] = 3;
                            BoxComponent box = view.getSelectedBox();
                            boxComponent=view.getSelectedBox();
                            box.setRow(nextRow);
                            box.setCol(nextCol);
                            view.updateBoxPosition();
                            moveSuccess = true;
                        }
                    }
                }
                else if(direction.equals(Direction.DOWN)) {
                    if(model.checkInHeightSize(nextRow+1)) {
                        if (model.getId(nextRow+1, nextCol) == 0) {
                            model.getMatrix()[row][col] = 0;
                            model.getMatrix()[row+1][col]=0;
                            model.getMatrix()[nextRow][nextCol] = 3;
                            model.getMatrix()[nextRow+1][nextCol] = 3;
                            BoxComponent box = view.getSelectedBox();
                            boxComponent=view.getSelectedBox();
                            box.setRow(nextRow);
                            box.setCol(nextCol);
                            view.updateBoxPosition();
                            moveSuccess = true;
                        }
                    }
                }
                else {
                    if (model.getId(nextRow, nextCol) == 0 && model.getId(nextRow+1,nextCol)==0) {
                        model.getMatrix()[row][col] = 0;
                        model.getMatrix()[row + 1][col] = 0;
                        model.getMatrix()[nextRow][nextCol] = 3;
                        model.getMatrix()[nextRow + 1][nextCol] = 3;
                        BoxComponent box = view.getSelectedBox();
                        boxComponent=view.getSelectedBox();
                        box.setRow(nextRow);
                        box.setCol(nextCol);
                        view.updateBoxPosition();
                        moveSuccess = true;
                    }
                }
            }
        }
        if (model.getId(row,col)==5){
            if (model.checkInWidthSize(nextCol) ) {
                if(direction.equals(Direction.UP)) {
                    if(model.checkInHeightSize(nextRow)) {
                        if (model.getId(nextRow, nextCol) == 0) {
                            model.getMatrix()[row][col] = 0;
                            model.getMatrix()[row+1][col]=0;
                            model.getMatrix()[nextRow][nextCol] = 5;
                            model.getMatrix()[nextRow+1][nextCol] = 5;
                            BoxComponent box = view.getSelectedBox();
                            boxComponent=view.getSelectedBox();
                            box.setRow(nextRow);
                            box.setCol(nextCol);
                            view.updateBoxPosition();
                            moveSuccess = true;
                        }
                    }
                }
                else if(direction.equals(Direction.DOWN)) {
                    if(model.checkInHeightSize(nextRow+1)) {
                        if (model.getId(nextRow+1, nextCol) == 0) {
                            model.getMatrix()[row][col] = 0;
                            model.getMatrix()[row+1][col]=0;
                            model.getMatrix()[nextRow][nextCol] = 5;
                            model.getMatrix()[nextRow+1][nextCol] = 5;
                            BoxComponent box = view.getSelectedBox();
                            boxComponent=view.getSelectedBox();
                            box.setRow(nextRow);
                            box.setCol(nextCol);
                            view.updateBoxPosition();
                            moveSuccess = true;
                        }
                    }
                }
                else {
                    if (model.getId(nextRow, nextCol) == 0 && model.getId(nextRow+1,nextCol)==0) {
                        model.getMatrix()[row][col] = 0;
                        model.getMatrix()[row + 1][col] = 0;
                        model.getMatrix()[nextRow][nextCol] = 5;
                        model.getMatrix()[nextRow + 1][nextCol] = 5;
                        BoxComponent box = view.getSelectedBox();
                        boxComponent=view.getSelectedBox();
                        box.setRow(nextRow);
                        box.setCol(nextCol);
                        view.updateBoxPosition();
                        moveSuccess = true;
                    }
                }
            }

        }
        if (model.getId(row,col)==6){
            if (model.checkInWidthSize(nextCol) ) {
                if(direction.equals(Direction.UP)) {
                    if(model.checkInHeightSize(nextRow)) {
                        if (model.getId(nextRow, nextCol) == 0) {
                            model.getMatrix()[row][col] = 0;
                            model.getMatrix()[row+1][col]=0;
                            model.getMatrix()[nextRow][nextCol] = 6;
                            model.getMatrix()[nextRow+1][nextCol] = 6;
                            BoxComponent box = view.getSelectedBox();
                            boxComponent=view.getSelectedBox();
                            box.setRow(nextRow);
                            box.setCol(nextCol);
                            view.updateBoxPosition();
                            moveSuccess = true;
                        }
                    }
                }
                else if(direction.equals(Direction.DOWN)) {
                    if(model.checkInHeightSize(nextRow+1)) {
                        if (model.getId(nextRow+1, nextCol) == 0) {
                            model.getMatrix()[row][col] = 0;
                            model.getMatrix()[row+1][col]=0;
                            model.getMatrix()[nextRow][nextCol] = 6;
                            model.getMatrix()[nextRow+1][nextCol] = 6;
                            BoxComponent box = view.getSelectedBox();
                            boxComponent=view.getSelectedBox();
                            box.setRow(nextRow);
                            box.setCol(nextCol);
                            view.updateBoxPosition();
                            moveSuccess = true;
                        }
                    }
                }
                else {
                    if (model.getId(nextRow, nextCol) == 0 && model.getId(nextRow+1,nextCol)==0) {
                        model.getMatrix()[row][col] = 0;
                        model.getMatrix()[row + 1][col] = 0;
                        model.getMatrix()[nextRow][nextCol] = 6;
                        model.getMatrix()[nextRow + 1][nextCol] = 6;
                        BoxComponent box = view.getSelectedBox();
                        boxComponent=view.getSelectedBox();
                        box.setRow(nextRow);
                        box.setCol(nextCol);
                        view.updateBoxPosition();
                        moveSuccess = true;
                    }
                }
            }

        }
        if (model.getId(row,col)==7){
            if (model.checkInWidthSize(nextCol) ) {
                if(direction.equals(Direction.UP)) {
                    if(model.checkInHeightSize(nextRow)) {
                        if (model.getId(nextRow, nextCol) == 0) {
                            model.getMatrix()[row][col] = 0;
                            model.getMatrix()[row+1][col]=0;
                            model.getMatrix()[nextRow][nextCol] = 7;
                            model.getMatrix()[nextRow+1][nextCol] = 7;
                            BoxComponent box = view.getSelectedBox();
                            boxComponent=view.getSelectedBox();
                            box.setRow(nextRow);
                            box.setCol(nextCol);
                            view.updateBoxPosition();
                            moveSuccess = true;
                        }
                    }
                }
                else if(direction.equals(Direction.DOWN)) {
                    if(model.checkInHeightSize(nextRow+1)) {
                        if (model.getId(nextRow+1, nextCol) == 0) {
                            model.getMatrix()[row][col] = 0;
                            model.getMatrix()[row+1][col]=0;
                            model.getMatrix()[nextRow][nextCol] = 7;
                            model.getMatrix()[nextRow+1][nextCol] = 7;
                            BoxComponent box = view.getSelectedBox();
                            boxComponent=view.getSelectedBox();
                            box.setRow(nextRow);
                            box.setCol(nextCol);
                            view.updateBoxPosition();
                            moveSuccess = true;
                        }
                    }
                }
                else {
                    if (model.getId(nextRow, nextCol) == 0 && model.getId(nextRow+1,nextCol)==0) {
                        model.getMatrix()[row][col] = 0;
                        model.getMatrix()[row + 1][col] = 0;
                        model.getMatrix()[nextRow][nextCol] = 7;
                        model.getMatrix()[nextRow + 1][nextCol] = 7;
                        BoxComponent box = view.getSelectedBox();
                        boxComponent=view.getSelectedBox();
                        box.setRow(nextRow);
                        box.setCol(nextCol);
                        view.updateBoxPosition();
                        moveSuccess = true;
                    }
                }
            }

        }
        if (model.getId(row,col)==4){
            if (direction.equals(Direction.UP)){
                if(model.checkInHeightSize(nextRow)){
                    if(model.getId(nextRow,nextCol)==0 && model.getId(nextRow,nextCol+1)==0){
                        model.getMatrix()[row][col]=0;
                        model.getMatrix()[row+1][col]=0;
                        model.getMatrix()[row][col+1]=0;
                        model.getMatrix()[row+1][col+1]=0;
                        model.getMatrix()[nextRow][nextCol] = 4;
                        model.getMatrix()[nextRow + 1][nextCol] = 4;
                        model.getMatrix()[nextRow][nextCol+1] = 4;
                        model.getMatrix()[nextRow+1][nextCol+1] = 4;
                        BoxComponent box = view.getSelectedBox();
                        boxComponent=view.getSelectedBox();
                        box.setRow(nextRow);
                        box.setCol(nextCol);
                        view.updateBoxPosition();
                        if (model.checkWinCondition(model.getMatrix())) {
                            view.showWinMessage(view.getSteps()+1);
                        }
                        moveSuccess = true;
                    }
                }
            }
            else if (direction.equals(Direction.DOWN)){
                if(model.checkInHeightSize(nextRow+1)){
                    if(model.getId(nextRow+1,nextCol)==0 && model.getId(nextRow+1,nextCol+1)==0){
                        model.getMatrix()[row][col]=0;
                        model.getMatrix()[row+1][col]=0;
                        model.getMatrix()[row][col+1]=0;
                        model.getMatrix()[row+1][col+1]=0;
                        model.getMatrix()[nextRow][nextCol] = 4;
                        model.getMatrix()[nextRow + 1][nextCol] = 4;
                        model.getMatrix()[nextRow][nextCol+1] = 4;
                        model.getMatrix()[nextRow+1][nextCol+1] = 4;
                        BoxComponent box = view.getSelectedBox();
                        boxComponent=view.getSelectedBox();
                        box.setRow(nextRow);
                        box.setCol(nextCol);
                        view.updateBoxPosition();
                        if (model.checkWinCondition(model.getMatrix())) {
                            view.showWinMessage(view.getSteps()+1);
                        }
                        moveSuccess = true;
                    }
                }
            }
            else if (direction.equals(Direction.LEFT)){
                if(model.checkInWidthSize(nextCol)){
                    if(model.getId(nextRow,nextCol)==0 && model.getId(nextRow+1,nextCol)==0){
                        model.getMatrix()[row][col]=0;
                        model.getMatrix()[row+1][col]=0;
                        model.getMatrix()[row][col+1]=0;
                        model.getMatrix()[row+1][col+1]=0;
                        model.getMatrix()[nextRow][nextCol] = 4;
                        model.getMatrix()[nextRow + 1][nextCol] = 4;
                        model.getMatrix()[nextRow][nextCol+1] = 4;
                        model.getMatrix()[nextRow+1][nextCol+1] = 4;
                        BoxComponent box = view.getSelectedBox();
                        boxComponent=view.getSelectedBox();
                        box.setRow(nextRow);
                        box.setCol(nextCol);
                        view.updateBoxPosition();
                        if (model.checkWinCondition(model.getMatrix())) {
                            view.showWinMessage(view.getSteps()+1);
                        }
                        moveSuccess = true;
                    }
                }
            }
            else{
                if(model.checkInWidthSize(nextCol+1)){
                    if(model.getId(nextRow,nextCol+1)==0 && model.getId(nextRow+1,nextCol+1)==0){
                        model.getMatrix()[row][col]=0;
                        model.getMatrix()[row+1][col]=0;
                        model.getMatrix()[row][col+1]=0;
                        model.getMatrix()[row+1][col+1]=0;
                        model.getMatrix()[nextRow][nextCol] = 4;
                        model.getMatrix()[nextRow + 1][nextCol] = 4;
                        model.getMatrix()[nextRow][nextCol+1] = 4;
                        model.getMatrix()[nextRow+1][nextCol+1] = 4;
                        BoxComponent box = view.getSelectedBox();
                        boxComponent=view.getSelectedBox();
                        box.setRow(nextRow);
                        box.setCol(nextCol);
                        view.updateBoxPosition();
                        if (model.checkWinCondition(model.getMatrix())) {
                            view.showWinMessage(view.getSteps()+1);
                        }
                        moveSuccess = true;
                    }
                }
            }
        }
        if (moveSuccess && isRecordingEnabled) {
            moveHistory.add(new MoveRecord(row, col, nextRow, nextCol, boxType, direction,boxComponent));
        }
        return moveSuccess;
    }

    public void undoLastMove() {
        if (!moveHistory.isEmpty()) {

        // 获取并移除最后一次移动记录
        MoveRecord lastMove = moveHistory.removeLast();

        // 计算相反方向
        Direction oppositeDir = getOppositeDirection(lastMove.getDirection());

        // 计算方块移动后的位置
        int movedRow = lastMove.getFromRow() + lastMove.getDirection().getRow();
        int movedCol = lastMove.getFromCol() + lastMove.getDirection().getCol();

        // 临时禁用记录
        boolean oldRecordingState = isRecordingEnabled;
        isRecordingEnabled = false;

        if(view.getSelectedBox()==lastMove.getBoxComponent()){
        // 执行相反方向的移动
        doMove(movedRow, movedCol, oppositeDir);
        }
        else{
            BoxComponent movedBox =lastMove.getBoxComponent();
            if (movedBox != null) {
                view.getSelectedBox().setSelected(false);
                view.setSelectedBox(movedBox);
                movedBox.setSelected(true);
                doMove(movedRow, movedCol, oppositeDir);
            }
        }

        // 恢复记录状态
        isRecordingEnabled = oldRecordingState;

        int currentStep=view.getSteps();
        currentStep--;
        view.setSteps(currentStep);
        JLabel stepLabel = view.getStepLabel();
        stepLabel.setText(String.format("Step: %d", view.getSteps()));
        }
    }

    private Direction getOppositeDirection(Direction dir) {
        return switch (dir) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
            case LEFT -> Direction.RIGHT;
            case RIGHT -> Direction.LEFT;
        };
    }
    //todo: add other methods such as loadGame, saveGame...
}
