/**
 * The Tetris board which contains the tiles.
 */
public class Board
{
    /**
     * height of the board.
     */
    private int height;
    /**
     * width of the board.
     */
    private int width;
    /**
     * board storage array.
     */
    private DynamicArray<DynamicArray<Tile>> board; // the internal storage of the board data

    /**
     * Initializes the board.
     * @param height height of the board.
     * @param width width of the board.
     */
    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.board = new DynamicArray<>(height);
        for (int i = 0; i < height; i++) {
            board.set(i, new DynamicArray<Tile>(width));
        }
    } // this contructor creates a 2D placeholder of null values; these values will be populated later with calls to setTile() -- O(height * width)

    /**
     * Gets the width of the board.
     * @return width of the board.
     */
    public int getWidth() {
        return width;
    } // returns the width of the board -- O(1)

    /**
     * Gets the height of the board.
     * @return height of the board.
     */
    public int getHeight() {
        return height;
    } // returns the height of the board -- O(1)

    /**
     * Sets the tile at the given position.
     * @param y y-position of the tile.
     * @param x x-position of the tile.
     * @param t new tile being set.
     */
    public void setTile(int y, int x, Tile t) {
        board.get(y).set(x, t);
    } // sets the tile at location y,x -- O(1)

    /**
     * Gets the tile at the given position.
     * @param y y-position of the tile.
     * @param x x-position of the tile.
     * @return the tile at the position.
     */
    public Tile getTile(int y, int x) {
        return board.get(y).get(x);
    } // gets the tile from location y,x -- O(1)

    /**
     * Sets the tiles when the block lands.
     * @param block the block falling.
     */
    public void consolidate(Block block) {
        for (int i = 0; i < block.getSize(); i++) {
            for (int j = 0; j < block.getSize(); j++) {
                Tile temp = block.getTile(i, j);
                if (temp != null) {
                    setTile(block.getY() + i, block.getX() + j, new Tile(temp.getColor()));
                }
            }
        }
    } // when the dropping block has reached its final location, this method will consolidate it into the tetris well -- O(block_size)

    /**
     * Clears a row if it is full.
     */
    public void clearRows() {
        for (int i = 0; i < height; i++) {
            boolean full = true;
            for (int j = 0; j < width; j++) {
                if (getTile(i, j) == null) {
                    full = false;
                    break;
                }
            }
            if (full) {
                removeRow(i);
            }
        }
    } // clear any/all rows that are complete and shifts the above tiles down -- O(board_size)

    /**
     * Reward function to remove a row when scaled up.
     */
    public void reward() {
        int targetRow = -1;
        int maxTiles = -1;
        for (int i = height - 1; i >= 0; i--) {
            int count = countTiles(i);
            if (count >= maxTiles && count > 0) {
                maxTiles = count;
                targetRow = i;
            }
        }
        if (targetRow != -1) {
            removeRow(targetRow);
        }
    } // applies the reward as explained in the project description -- O(board_size)

    /** Penalty function to add a row when scaled down.
     */
    public void penalize() {
        int targetRow = -1;
        int minTiles = width + 1;
        for (int i = 0; i < height; i++) {
            int count = countTiles(i);
            if (count < minTiles && count > 0) {
                minTiles = count;
                targetRow = i;
                break;
            }
        }
        if (targetRow != -1) {
            board.set(targetRow - 1, board.get(targetRow));
        }
    } // applies the penalty as explained in the project description -- O(board_size)

    /**
     * Counts number of tiles in a row.
     * @param row number of row to count from.
     * @return number of tiles in given row.
     */
    private int countTiles(int row) {
        int count = 0;
        for (int i = 0; i < width; i++) {
            if (getTile(row, i) != null) {
                count += 1;
            }
        }
        return count;
    }

    /**
     * Removes a given row from the board.
     * @param row row number.
     */
    private void removeRow(int row) {
        for (int i = row; i > 0; i--) {
            board.set(i, board.get(i - 1));
        }
        board.set(0, new DynamicArray<Tile>(width));
    }
}
