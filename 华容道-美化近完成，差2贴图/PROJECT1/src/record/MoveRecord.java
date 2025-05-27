package record;

import model.Direction;
import view.game.BoxComponent;

public class MoveRecord {
    private final int fromRow;
    private final int fromCol;
    private final int toRow;
    private final int toCol;
    private final int boxType;
    private final Direction direction;
    private BoxComponent boxComponent;

    public MoveRecord(int fromRow, int fromCol, int toRow, int toCol, int boxType, Direction direction,BoxComponent boxComponent) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
        this.boxType = boxType;
        this.direction = direction;
        this.boxComponent=boxComponent;
    }

    // Getter方法
    public int getFromRow() { return fromRow; }
    public int getFromCol() { return fromCol; }
    public int getBoxType() { return boxType; }
    public Direction getDirection() { return direction; }

    public int getToRow() {
        return toRow;
    }

    public int getToCol() {
        return toCol;
    }

    public BoxComponent getBoxComponent() {
        return boxComponent;
    }

    @Override
    public String toString() {
        return String.format("方块%d从(%d,%d)向%s移动", boxType, fromRow, fromCol, direction);
    }
}