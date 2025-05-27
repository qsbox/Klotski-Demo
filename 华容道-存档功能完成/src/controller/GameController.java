package controller;

import model.Direction;
import model.MapModel;
import record.MoveRecord;
import view.game.BoxComponent;
import view.game.GamePanel;
import User.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * It is a bridge to combine GamePanel(view) and MapMatrix(model) in one game.
 * You can design several methods about the game logic in this class.
 */
public class GameController {
    private final GamePanel view;
    private final MapModel model;
    private final List<MoveRecord> moveHistory = new ArrayList<>();
    private boolean isRecordingEnabled = true;

    public GameController(GamePanel view, MapModel model) {
        this.view = view;
        this.model = model;
        view.setController(this);
    }

    public void restartGame() {
        System.out.println("Do restart game here");
        this.model.resetOriginalMatrix();
        this.view.clearAll();
        this.view.initialGame(model.getMatrix());
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
        try {
            Files.write(Path.of(path+"/data.txt"),gameData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadGame(String path){
            try {
                List<String> lines = Files.readAllLines(Path.of(path));

                int rows = lines.size();

                String[] firstLineNumbers = lines.get(0).trim().split("\\s+");
                int cols = firstLineNumbers.length;

                int[][] saveMatrix = new int[rows][cols];

                for (int i = 0; i < rows; i++) {
                    String line = lines.get(i);
                    Scanner scanner = new Scanner(line);
                    for (int j = 0; j < cols; j++) {
                        if (scanner.hasNextInt()) {
                            saveMatrix[i][j] = scanner.nextInt();
                        } else {
                            throw new RuntimeException("第 " + (i+1) + " 行数据不完整或格式错误");
                        }
                    }

                    if (scanner.hasNext()) {
                        throw new RuntimeException("第 " + (i+1) + " 行数据过多");
                    }

                    scanner.close();
                }
                model.setMatrix(saveMatrix);
            } catch (IOException e) {
                throw new RuntimeException("读取文件失败: " + path, e);
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
                    box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                    box.repaint();
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
                            box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                            box.repaint();
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
                            box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                            box.repaint();
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
                        box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                        box.repaint();
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
                            box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                            box.repaint();
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
                            box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                            box.repaint();
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
                        box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                        box.repaint();
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
                        box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                        box.repaint();
                        if (model.checkWinCondition(model.getMatrix())) {
                            view.showWinMessage(view.getSteps());
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
                        box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                        box.repaint();
                        if (model.checkWinCondition(model.getMatrix())) {
                            view.showWinMessage(view.getSteps());
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
                        box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                        box.repaint();
                        if (model.checkWinCondition(model.getMatrix())) {
                            view.showWinMessage(view.getSteps());
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
                        box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                        box.repaint();
                        if (model.checkWinCondition(model.getMatrix())) {
                            view.showWinMessage(view.getSteps());
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
