package fall18_207project.SlidingTile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
public class BoardManager implements Serializable {

    /**
     * The board being managed.
     */
    private Board board;
    /**
     * The step being count.
     */
    private int step = 0;
    /**
     * The undoList use for undo.
     */
    private ArrayList<Integer> undoList = new ArrayList();

    /**
     * Manage a board that has been pre-populated.
     * @param board the board
     */
    BoardManager(Board board) {
        this.board = board;
    }

    /**
     * Return the current board.
     */
    Board getBoard() {
        return board;
    }

    /**
     * Return the current step number.
     */
    int getStep() {
        return step;
    }

    /**
     * Manage a new shuffled board.
     */
    ArrayList getUndoList(){return undoList;}

    BoardManager() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = Board.NUM_ROWS * Board.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles-1; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        tiles.add(new Tile(24));

//        Collections.shuffle(tiles);
        this.board = new Board(tiles);

        // Make slidingtiles always solvable.
        int i = 0;
        while (i < 100){
            int position = 0;
            ArrayList<Integer> possibleMoves = new ArrayList();
            while (position < numTiles - 1){
                if (isValidTap(position)) {
                    possibleMoves.add(position);
                }
                position = position + 1;
            }

//            Collections.shuffle(possibleMoves);
            int possiblePosition = possibleMoves.get(0);
            touchMovePrepare(possiblePosition);
            i = i + 1;

        }
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {
        boolean solved = true;

        /*check whether the all of the later ids are smaller than the previous ones. If any one of
        of the ids fails that, change solved into false.
         */

        for (int rowNum = 0; rowNum < Board.NUM_ROWS; rowNum++) {
            for (int colNum = 0; colNum < Board.NUM_COLS; colNum++) {
                if (colNum != 0) {
                    if (this.board.getTile(rowNum, colNum - 1).getId()
                            > this.board.getTile(rowNum, colNum).getId()) {
                        solved = false;
                    }
                } else {
                    if (rowNum != 0) {
                        if (this.board.getTile(rowNum - 1, Board.NUM_COLS - 1).getId()
                                > this.board.getTile(rowNum, colNum).getId()) {
                            solved = false;
                        }
                    }
                }
            }
        }
        return solved;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {

        int row = position / Board.NUM_COLS;
        int col = position % Board.NUM_COLS;
        int blankId = 25;
        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == Board.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == Board.NUM_COLS - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {

        int row = position / Board.NUM_ROWS;
        int col = position % Board.NUM_COLS;
        int afterRow = row;
        int afterCol = col;
        int blankId = 25;

        // TODO: figure out when to call board.swapTiles. Specifically, if any of the neighbouring
        // tiles is the blank tile, swap by calling Board's swap method.
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == Board.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == Board.NUM_COLS - 1 ? null : board.getTile(row, col + 1);

        if (below != null && below.getId() == blankId) {
            this.board.swapTiles(row, col, row + 1, col);
            afterRow = row + 1;
            afterCol = col;
            undoList.add(row);
            undoList.add(col);
            undoList.add(afterRow);
            undoList.add(afterCol);
            step += 1;
        }
        if (above != null && above.getId() == blankId) {
            this.board.swapTiles(row, col, row - 1, col);
            afterRow = row - 1;
            afterCol = col;
            undoList.add(row);
            undoList.add(col);
            undoList.add(afterRow);
            undoList.add(afterCol);
            step += 1;
        }
        if (left != null && left.getId() == blankId) {
            this.board.swapTiles(row, col, row, col - 1);
            afterRow = row;
            afterCol = col - 1;
            undoList.add(row);
            undoList.add(col);
            undoList.add(afterRow);
            undoList.add(afterCol);
            step += 1;
        }
        if (right != null && right.getId() == blankId) {
            this.board.swapTiles(row, col, row, col + 1);
            afterRow = row;
            afterCol = col + 1;
            undoList.add(row);
            undoList.add(col);
            undoList.add(afterRow);
            undoList.add(afterCol);
            step += 1;
        }
    }

    /**
     * @param position the prepation for the movement
     */
    void touchMovePrepare(int position) {

        int row = position / Board.NUM_ROWS;
        int col = position % Board.NUM_COLS;
        int afterRow = row;
        int afterCol = col;
        int blankId = 25;

        // TODO: figure out when to call board.swapTiles. Specifically, if any of the neighbouring
        // tiles is the blank tile, swap by calling Board's swap method.
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == Board.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == Board.NUM_COLS - 1 ? null : board.getTile(row, col + 1);

        if (below != null && below.getId() == blankId) {
            this.board.swapTiles(row, col, row + 1, col);
        }
        if (above != null && above.getId() == blankId) {
            this.board.swapTiles(row, col, row - 1, col);
        }
        if (left != null && left.getId() == blankId) {
            this.board.swapTiles(row, col, row, col - 1);
        }
        if (right != null && right.getId() == blankId) {
            this.board.swapTiles(row, col, row, col + 1);
        }
    }

    /**
     * The undo method for 3 steps.
     */
    void unDo() {
        if (undoList.size() >= 4 * 3) {
            for (int time = 0; time < 3; time++) {
                this.board.swapTiles(undoList.get(undoList.size() - 2), undoList.get(undoList.size() - 1), undoList.get(undoList.size() - 4)
                        ,undoList.get(undoList.size() - 3));
                undoList.remove(undoList.size() - 1);
                undoList.remove(undoList.size() - 1);
                undoList.remove(undoList.size() - 1);
                undoList.remove(undoList.size() - 1);
            }
        }

    }
    /**
     * The undo method for n steps
     * @param  n
     */

    void unDoNSteps(int n) {
        if (undoList.size() >= 4 * n) {
            for (int time = 0; time < n; time++) {
                this.board.swapTiles(undoList.get(undoList.size() - 2), undoList.get(undoList.size() - 1), undoList.get(undoList.size() - 4)
                        , undoList.get(undoList.size() - 3));
                undoList.remove(undoList.size() - 1);
                undoList.remove(undoList.size() - 1);
                undoList.remove(undoList.size() - 1);
                undoList.remove(undoList.size() - 1);
            }
        }


    }

    public boolean isValidUndo(){
        return undoList.size() >= 4 * 3;
    }

    public boolean isValidUndoNSteps(int n){
        return undoList.size() >= 4 * n;
    }
}
