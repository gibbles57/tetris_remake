/**
 * Class to check game moves and states.
 */
public class Tetris
{
    /**
     * Checks if a block movememnt is valid.
     * @param board board array.
     * @param y y-position of the block.
     * @param x x-position of the block.
     * @param b the block.
     * @return whether the check passes or not.
     */
    private static boolean check(Board board, int y, int x, Block b) {
        for (int i = 0; i < b.getSize(); i++) {
            for (int j = 0; j < b.getSize(); j++) {
                if (b.getTile(i, j) != null) {
                    int nextY = y + i;
                    int nextX = x + j;
                    if (nextY < 0 || nextY >= board.getHeight() || nextX < 0 || nextX >= board.getWidth() || board.getTile(nextY, nextX) != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Checks if block can move left 1 unit.
     * @param board the board array.
     * @param block the block moving.
     * @return if the movement is valid or not.
     */
    public static boolean canMoveLeft(Board board, Block block) {
        return check(board, block.getY(), block.getX() - 1, block);
    } // O(board_size)

    /**
     * Checks if block can move right 1 unit.
     * @param board the board array.
     * @param block the block moving.
     * @return if the movememnt is valid or not.
     */
    public static boolean canMoveRight(Board board, Block block) {
        return check(board, block.getY(), block.getX() + 1, block);
    } // O(board_size)

    /**
     * Checks if a vertical flip is valid.
     * @param board the board array.
     * @param block the block being flipped;
     * @return if the flip is valid or not.
     */
    public static boolean canflipVertical(Board board, Block block) {
        Block temp = new Block (block.getY(), block.getX(), block.getSize());
        for (int i = 0; i < block.getSize(); i++) {
            for (int j = 0; j < block.getSize(); j++) {
                temp.setTile(block.getSize() - 1 - i, j, block.getTile(i, j));
            }
        }
        return check(board, temp.getY(), temp.getX(), temp);
    } // O(board_size)

    /**
     * Checks if a horizontal flip is valid.
     * @param board the board array.
     * @param block the block being flipped.
     * @return if the flip is valid or not.
     */
    public static boolean canflipHorizontal(Board board, Block block) {
        Block temp = new Block(block.getY(), block.getX(), block.getSize());
        for (int i = 0; i < block.getSize(); i++) {
            for (int j = 0; j < block.getSize(); j++) {
                temp.setTile(i, block.getSize() - 1 - j, block.getTile(i, j));
            }
        }
        return check(board, temp.getY(), temp.getX(), temp);
    } // O(board_size)

    /**
     * Checks if a rotate is valid.
     * @param board the board array.
     * @param block the block being rotated.
     * @return if the rotation is valid or not.
     */
    public static boolean canRotate(Board board, Block block) {
        Block temp = new Block(block.getY(), block.getX(), block.getSize());
        for (int i = 0; i < block.getSize(); i++) {
            for (int j = 0; j < block.getSize(); j++) {
                temp.setTile(j, block.getSize() - 1 - i, block.getTile(i, j));
            }
        }
        return check(board, temp.getY(), temp.getX(), temp);
    } // O(board_size)

    /**
     * Checks if a scale down is valid.
     * @param board the board array.
     * @param block the block being scaled.
     * @return if the scaling is valid or not.
     */
    public static boolean canScaleDown(Board board, Block block) {
        if (block.getSize() > 2) {
            return true;
        } else {
            return false;
        }
    } // O(board_size)

    /**
     * Checks if a scale up is valid.
     * @param board the board array.
     * @param block the block being scaled.
     * @return if the scaling is valid or not.
     */
    public static boolean canScaleUp(Board board, Block block) {
        return check(board, block.getY(), block.getX(), block.scaleUp());
    } // O(board_size)

    /**
     * Checks if block can move down 1 unit.
     * @param board the board array.
     * @param block the block moving.
     * @return if the movement is valid or not.
     */
    public static boolean canDrop(Board board, Block block) {
        return check(board, block.getY() + 1, block.getX(), block);
    } // O(board_size)

    /**
     * Checks if the game is over.
     * @param board the board array.
     * @param block the block moving.
     * @return if the game has ended or not.
     */
    public static boolean isGameOver(Board board, Block block) {
        if (canDrop(board, block)) {
            return false;
        }
        if (block.getY() == 0) {
            return true;
        } else {
            return false;
        }
    } // O(board_size)
}
