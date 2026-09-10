/**
 * A class to store a tile's color.
 */
public class Tile
{
    /**
     * Color of the tile.
     */
    private byte color;

    /**
     * Sets a tile's color.
     * @param color the color being set.
     */
    public Tile(byte color)
    {
        this.color = color;
    }

    /**
     * Gets a tile's current color.
     * @return the color of the tile.
     */
    public byte getColor()
    {
        return color;
    }
}
