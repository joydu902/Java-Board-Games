package fall18_207project.SlidingTile;

import android.support.annotation.NonNull;

import java.util.Observable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The sliding tiles board.
 * TODO: Make this implement Iterable<Tile>.
 */
public class Board extends Observable implements Serializable, Iterable<Tile> {

    /**
     * The number of rows.
     */
    public static int NUM_ROWS ;

    /**
     * The number of rows.
     */
    public static int NUM_COLS ;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles = new Tile[NUM_ROWS][NUM_COLS];

    private List<Tile> tile_list;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    public Board(List<Tile> tiles) {
        Iterator<Tile> iter = tiles.iterator();

        this.tile_list = tiles;
        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    public static int getNumRows() {
        return NUM_ROWS;
    }

    public static int getNumCols() {
        return NUM_COLS;
    }

    public static void setNumRows(int numRows) {
        NUM_ROWS = numRows;
    }

    public static void setNumCols(int numCols) {
        NUM_COLS = numCols;
    }

    /**
     * Return the number of tiles on the board.
     * @return the number of tiles on the board
     */
    public int numTiles() {
        // TODO: fix me
        return NUM_ROWS * NUM_COLS;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    public void swapTiles(int row1, int col1, int row2, int col2) {
        // TODO: swap
        Tile tile1 = getTile(row1, col1);
        Tile tile2 = getTile(row2, col2);
        this.tiles[row1][col1] = tile2;
        this.tiles[row2][col2] = tile1;
        setChanged();
        notifyObservers();
    }

    /**
     * @return make the Tile become iterable
     */
    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        this.tile_list.clear();

        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                this.tile_list.add(this.tiles[row][col]);
            }
        }
        return tile_list.iterator();
    }

    /**
     * @return represent the Board in string style
     */
    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }
}
