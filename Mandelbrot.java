import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JFrame;

/**
 * Visualize the Mandelbrot set with color
 */
public class Mandelbrot extends Canvas {
    final int WIDTH = 800;
    final int HEIGHT = 800;
    final int THRESHOLD = 5000;

    /**
     * Constructor
     */
    public Mandelbrot() {
        setSize(WIDTH, HEIGHT);
    }

    /**
     * Iterate the complex Mandelbrot function on the point (re, im)
     * Returns the number of iterations before the point escapes.
     *
     * @param re the real part of the starting point
     * @param im the imaginary part of the starting point
     * @return number of iterations before the point escapes
     */
    public int inSet(double re, double im) {
        double zReal = 0.0;
        double zImag = 0.0;
        for (int i = 0; i < THRESHOLD; i++) {
            double zSqReal = zReal * zReal - zImag * zImag;
            double zSqImag = 2 * zReal * zImag;
            zReal = zSqReal + re;
            zImag = zSqImag + im;
            if (zReal * zReal + zImag * zImag > 4.0) {
                return i;
            }
        }
        return THRESHOLD;
    }

    /**
     * Visualize the Mandelbrot set with the ability to zoom and colorize
     * based on escape velocity.
     *
     * @param g Graphics object for the drawable surface
     */
    public void draw(Graphics g) {
        double targetReal = -1.4011;
        double targetImag = 0.0;
        double zoom = 100.0;

        double minReal = targetReal - 2.0 / zoom;
        double maxReal = targetReal + 2.0 / zoom;
        double minImag = targetImag - (maxReal - minReal) / 2.0;
        double maxImag = targetImag + (maxReal - minReal) / 2.0;

        double realStep = (maxReal - minReal) / WIDTH;
        double imagStep = (maxImag - minImag) / HEIGHT;

        int[][] counts = new int[WIDTH][HEIGHT];
        int[] hist = new int[THRESHOLD + 1];

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                double re = minReal + realStep * x + realStep / 2;
                double im = maxImag - imagStep * y - imagStep / 2;
                counts[x][y] = inSet(re, im);
                hist[counts[x][y]]++;
            }
        }

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                int cumSum = 0;
                for (int i = 0; i <= counts[x][y]; i++) {
                    cumSum += hist[i];
                }
                float percentile = (float) cumSum / (WIDTH * HEIGHT);
                Color color = Color.getHSBColor(percentile, 0.85f, 1.0f);
                g.setColor(color);
                g.fillRect(x, y, 1, 1);
            }
        }
    }

    public void paint(Graphics g) {
        // Paint the background white
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        // Draw the colorized image
        draw(g);
    }

    public static void main(String[] args) {
        Mandelbrot m = new Mandelbrot();
        JFrame frame = new JFrame("Mandelbrot Set");
        frame.add(m);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
