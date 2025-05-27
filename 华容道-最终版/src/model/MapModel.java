package model;

import javax.swing.*;
import java.awt.*;

/**
 * This class is to record the map of one game. For example:
 */
public class MapModel extends Component {
    int[][] matrix;
    int mission;
    private int[][] originalMatrix;

    public MapModel(int[][] matrix) {
        this.matrix = matrix;
        this.originalMatrix=copyMatrix(matrix);
    }

    public void resetOriginalMatrix(){
        this.matrix=copyMatrix(originalMatrix);
    }

    public int[][] copyMatrix(int[][] matrix){
        int [][] copy=new int[matrix.length][matrix[0].length];
        for (int i=0;i<matrix.length;i++) {
            System.arraycopy(matrix[i], 0, copy[i], 0, matrix[i].length);
        }
        return copy;
    }

    public int getWidth() {
        return this.matrix[0].length;
    }

    public int getHeight() {
        return this.matrix.length;
    }

    public int getId(int row, int col) {
        return matrix[row][col];
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getMission() {
        return mission;
    }

    public void setMission(int mission) {
        this.mission = mission;
    }

    public boolean checkInWidthSize(int col) {
        return col >= 0 && col < matrix[0].length;
    }

    public boolean checkInHeightSize(int row) {
        return row >= 0 && row < matrix.length;
    }

    public boolean checkWinCondition(int[][] matrix) {
        return matrix[3][1]==4 && matrix[4][2]==4;
    }
}
