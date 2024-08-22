import java.awt.Graphics;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

public class FallingSand extends Canvas implements MouseListener, MouseMotionListener {
    private final int WIDTH = 640;
    private final int HEIGHT = 240;
    private final boolean[][] sand = new boolean[HEIGHT][WIDTH];
    private boolean active = false;
    private int x;
    private int y;

    /**
     * Constructor
     */
    public FallingSand() {
        setSize(WIDTH, HEIGHT);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    /**
     * Draw each object to the screen
     */
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.BLACK);
        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                if (sand[r][c]) {
                    g.fillRect(c, r, 1, 1); // Draw a 1x1 pixel for each sand particle
                }
            }
        }
    }

    /**
     * The main game loop
     */
    public void run() {
        boolean running = true;
        while (running) {
            if (this.active) {
                if (y < HEIGHT && x < WIDTH) {
                    this.sand[y][x] = true;
                }
            }

            for (int r = HEIGHT - 2; r >= 0; r--) {
                for (int c = 1; c < WIDTH - 1; c++) {
                    if (this.sand[r][c]) {
                        if (!this.sand[r + 1][c]) {
                            this.sand[r][c] = false;
                            this.sand[r + 1][c] = true;
                        } else if (!this.sand[r + 1][c - 1]) {
                            this.sand[r][c] = false;
                            this.sand[r + 1][c - 1] = true;
                        } else if (!this.sand[r + 1][c + 1]) {
                            this.sand[r][c] = false;
                            this.sand[r + 1][c + 1] = true;
                        }
                    }
                }
            }

            repaint();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Mouse events
     */
    public void mousePressed(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
        this.active = true;
    }

    public void mouseDragged(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
    }

    public void mouseReleased(MouseEvent e) {
        this.active = false;
    }

    public void mouseClicked(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseMoved(MouseEvent e) {}

    /**
     * Main method to launch the application
     */
    public static void main(String[] args) {
        FallingSand game = new FallingSand();
        JFrame frame = new JFrame("Falling Sand");
        frame.add(game);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        new Thread(game::run).start();
    }
}
