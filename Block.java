import java.util.Random;

/**
 * A class to represent the Tetris blocks.
 */
public class Block
{
    /**
     * x position of the block.
     */
    private int xpos;
    /**
     * y position of the block.
     */
    private int ypos;
    /**
     * size of the block.
     */
    private int size;

    /**
     * array to hold block data.
     */
    private DynamicArray<DynamicArray<Tile>> block; // the internal storage of the block data

    /**
     * Initializes a Block instance.
     * @param y y-position of the block.
     * @param x x-position of the block.
     * @param size size of the array.
     */
    public Block(int y, int x, int size) {
        this.xpos = x;
        this.ypos = y;
        this.size = size;
        this.block = new DynamicArray<>(size);
        for (int i = 0; i < size; i++) {
            block.set(i, new DynamicArray<Tile>(size));
        }
    } // this contructor creates a 2D placeholder of null values; these values will be populated later with calls to setTile() -- O(block_size)

    /**
     * Initializes a block with position, size, and color tiles.
     * @param y y-position of block.
     * @param x x-position of block.
     * @param size size of block.
     * @param color color of the tiles.
     */
    public Block(int y, int x, int size, byte color) {
        this(y, x, size);
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (random.nextBoolean()) {
                    setTile(i, j, new Tile(color));
                }
            }
        }

    } // overloaded constuctor that creates a 2D matrix with actual tile objects; no need to call setTile afterwards -- O(block_size)

    /**
     * Returns the length of the block.
     * @return size of block.
     */
    public int getSize() {
        return this.size;
    } // returns the length of the side of block -- O(1)

    /**
     * Returns the y-position of the block.
     * @return y-position of block.
     */
    public int getY() {
        return this.ypos;
    } // returns the top-left Y-coordinate of the block -- O(1)

    /**
     * Returns the x-potision of the block.
     * @return x-position of block.
     */
    public int getX() {
        return this.xpos;
    } // returns the top-left X-coordinate of the block -- O(1)

    /**
     * Sets the tile at the given location.
     * @param y y-position of the tile.
     * @param x x-position of the tile.
     * @param t tile being set.
     */
    public void setTile(int y, int x, Tile t) {
        block.get(y).set(x, t);
    } // sets the tile at location y,x -- O(1)

    /**
     * Gets the tile from the given location.
     * @param y y-position of the tile.
     * @param x x-position of the tile.
     * @return the tile at location
     */
    public Tile getTile(int y, int x) {
        return block.get(y).get(x);
    } // gets the tile from location y,x -- O(1)

    /**
     * Drops the block down by 1 unit.
     */
    public void drop() {
        this.ypos += 1;
    } // drops the block by one row -- O(block_size)

    /**
     * Moves the block left by 1 unit.
     */
    public void moveLeft() {
        this.xpos -= 1;
    } // moves the block one spot to the left -- O(block_size)

    /**
     * Moves the block right by 1 unit.
     */
    public void moveRight() {
        this.xpos += 1;
    } // moves the block one spot to the right -- O(block_size)

    /**
     * Rotates the block by 90 degrees clockwise.
     */
    public void rotate() {
        Block next = new Block(ypos, xpos, size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                next.setTile(j, size - 1 - i, getTile(i, j));
            }
        }
        this.block = next.block;
    } // rotates the block 90 degrees clockwise -- O(block_size)

    /**
     * Flips the block about the x-axis.
     */
    public void flipVertical() {
        Block next = new Block(ypos, xpos, size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                next.setTile(size - 1 - i, j, getTile(i, j));
            }
        }
        this.block = next.block;
    } // flips the block vertically -- O(block_size)

    /**
     * Flips the block about the y-axis.
     */
    public void flipHorizontal() {
        Block next = new Block(ypos, xpos, size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                next.setTile(i, size - 1 - j, getTile(i, j));
            }
        }
        this.block = next.block;
    } // flips the block horizontally -- O(block_size)

    /**
     * Scales the block up by 1 scale.
     * @return the scaled block.
     */
    public Block scaleUp() {
        Block next = new Block(ypos, xpos, size * 2);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Tile temp = getTile(i, j);
                if (temp != null) {
                    byte color = temp.getColor();
                    next.setTile(i * 2, j * 2, temp);
                    next.setTile(i * 2 + 1, j * 2, temp);
                    next.setTile(i * 2, j * 2 + 1, temp);
                    next.setTile(i * 2 + 1, j * 2 + 1, temp);
                }
            }
        }
        return next;
    } // scales up the block (double size) -- O(block_size)

    /** Scales the block down by 1 scale.
     * @return the scaled block.
     */
    public Block scaleDown() {
        if (size < 3) {
            return this;
        }
        int newSize = size / 2;
        Block next = new Block(ypos, xpos, newSize);
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                Tile temp = getTile(i * 2, j * 2);
                if (temp != null) {
                    next.setTile(i, j, temp);
                }
            }
        }
        return next;
    } // scales down the block (half size) -- O(block_size)
}
