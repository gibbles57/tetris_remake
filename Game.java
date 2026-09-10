import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Random;

/**
 * The GUI of the game and the code that runs the game itself.
 */
public final class Game extends JPanel
{
    /**
     * Creates the Tetris board.
     */
    private static Board board;
    /**
     * Creates the Block object.
     */
    private static Block block;
    /**
     * Initializes the random generator.
     */
    private static Random rand = new Random();
    /**
     * Initializes the colors able to be chosen.
     */
    private final static Color[] color = {
        Color.black, Color.red, Color.green, Color.blue, Color.cyan, Color.magenta, Color.orange, Color.yellow, Color.pink, Color.white
    };

    /* will be used for testing
    private void createBlock(int y, int x, int size)
    {
        Random rand = new Random();
        byte tileColor = (byte)(1 + rand.nextInt(color.length-1));

        block = new Block(y, x, size);
        for (int i=0; i<size; i++)
            for (int j=0; j<size; j++)
                if (rand.nextBoolean())
                    block.setTile(i, j, new Tile(tileColor));
    }
    */
    /**
     * Paints the board and blocks.
     * @param g graphics.
     */
    @Override 
    public void paintComponent(Graphics g)
    {
        drawBoard(g);
        drawBlock(g);
    }

    /**
     * Draws the board with the tiles.
     * @param g graphics.
     */
    private void drawBoard(Graphics g)
    {
        for (int y=0; y<board.getHeight(); y++)
        {
            for (int x=0; x<board.getWidth(); x++)
            {
                Tile tile = board.getTile(y,x);              
                int colorIndex = tile == null ? 0 : tile.getColor();
                g.setColor(color[colorIndex]);
                g.fillRect(20*x, 20*y, 20, 20);
            }
        }
    }

    /**
     * Draws the block on the GUI.
     * @param g graphics.
     */
    private void drawBlock(Graphics g)
    {
        for (int y=0; y<block.getSize(); y++)
            for (int x=0; x<block.getSize(); x++)
            {
                Tile tile = block.getTile(y,x);
                if (tile != null)
                {
                    g.setColor(color[tile.getColor()]);
                    g.fillRect(20*(x+block.getX()), 20*(y+block.getY()), 20, 20);
                }
            }
    }

    /**
     * Program enterance and controls the game.
     * @param args game settings.
     */
    public static void main(String[] args)
    {
        if (args.length != 2)
        {
            System.err.println("Usage: java Game <height> <width>");
            return;
        }

        JFrame window = new JFrame("CS310 - Spring 2024 - Project 1");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board = new Board(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        block = new Block(0, 3, 3, (byte)(1 + rand.nextInt(color.length-1)));
        window.setSize(board.getWidth()*20, board.getHeight()*20+29);
        window.setVisible(true);
        final Game tetris = new Game();
        window.add(tetris);

        KeyListener kl = new KeyListener()
        {
            public void keyPressed(KeyEvent e)
            {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (Tetris.canMoveLeft(board, block))
                            block.moveLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (Tetris.canMoveRight(board, block))
                            block.moveRight();
                        break;
                    case KeyEvent.VK_UP:
                        if (Tetris.canflipVertical(board, block))
                            block.flipVertical();
                        break;
                    case KeyEvent.VK_DOWN:
                        if (Tetris.canflipHorizontal(board, block))
                            block.flipHorizontal();
                        break;
                    case KeyEvent.VK_R:
                        if (Tetris.canRotate(board, block))
                            block.rotate();
                        break;
                    case KeyEvent.VK_OPEN_BRACKET:
                        if (Tetris.canScaleDown(board, block))
                        {
                            block = block.scaleDown();
                            board.penalize();
                        }
                        break;
                    case KeyEvent.VK_CLOSE_BRACKET:
                        if (Tetris.canScaleUp(board, block))
                        {
                            block = block.scaleUp();
                            board.reward();
                        }
                        break;
                    case KeyEvent.VK_PAGE_DOWN:
                        if (Tetris.canDrop(board, block))
                            block.drop();
                        break;
                }

                tetris.repaint();
            }
            
            public void keyReleased(KeyEvent e) { /* do nothing */ }

            public void keyTyped(KeyEvent e) { /* do nothing */ }

        };

        window.addKeyListener(kl);

        new Thread()
        {
            @Override
            public void run()
            {
                while (true) {
                    try
                    {
                        Thread.sleep(1000);

                        if (Tetris.canDrop(board, block))
                            block.drop();
                        else
                        {
                            board.consolidate(block);
                            board.clearRows();
                            block = new Block(0, 3, 3, (byte)(1 + rand.nextInt(color.length-1)));
                        }

                        tetris.repaint();

                        if (Tetris.isGameOver(board, block))
                        {
                            window.removeKeyListener(kl);
                            break;
                        }
                    }
                    catch(InterruptedException e )
                    {
                    }
                }
            }
        }.start();
    }
}
