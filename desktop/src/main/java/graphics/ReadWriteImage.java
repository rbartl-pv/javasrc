package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

/**
 * Reads an image, adds some text, and writes it back to a new file.
 * In fact, it creates a bunch of files, for use as icons in the Java Cookbook.
 * Doubtful these'll actually get used in the book, but worth knowing how to.
 * Choose number-in-a-circle or number-on-crude-coffee cup by setting "mode".
 * @author Ian Darwin
 */
public class ReadWriteImage {
	/** The mode: IMAGE reads a file, CIRCLE creates a canvas with a gray circle */
	enum Mode { IMAGE, CIRCLE }
	/** The mode used in this run */
	static Mode mode = Mode.IMAGE;
	/** Java release to start with */
	final static int START = 8;
	/** Several beyond current Java release, room for expansion */
	final static int END = 25;
	/** Font size to draw in */
	final static int FONT_SIZE = 84;
	/** Radius, only used if mode == CIRCLE */
	final static int RADIUS = 160;
	final static String DIRECTORY = "images" + File.separatorChar;

	public static void main(String[] args) throws Exception {
		String dir = DIRECTORY;
		IntStream.rangeClosed(START, END).forEach(i -> process(DIRECTORY, Integer.toString(i)));
		List.of("21P").forEach(s -> process(DIRECTORY, s));
		System.exit(0);
	}

	static void process(String dir, String label) {
		try {
		BufferedImage image = null;
		if (mode == Mode.IMAGE) {
			image = ImageIO.read(new File(dir + "coffeecup.png"));
		} else if (mode == Mode.CIRCLE) {
			image = new BufferedImage(RADIUS, RADIUS, BufferedImage.TYPE_INT_RGB);
		} else {
			throw new IllegalStateException(STR."Invalid Mode \{mode}");
		}
		Graphics2D g = image.createGraphics();
		if (mode == Mode.CIRCLE) {

			// Draw the filled Circle
			g.setColor(Color.GRAY);
			g.fillOval(0, 0, RADIUS, RADIUS);
		} else {
			// Nothing, the image was already loaded
		}

		// Draw the number
		Font textFont = new Font("SansSerif", Font.BOLD, FONT_SIZE);
		g.setFont(textFont);
		g.setColor(mode == Mode.CIRCLE ? Color.WHITE : Color.GRAY);
		Rectangle2D lineMetrics = textFont.getStringBounds(label, g.getFontRenderContext());
		// System.out.println(STR."Font measured size = \{lineMetrics}");
		int x = (int)((image.getWidth() - lineMetrics.getWidth()) / 2);
		int y = (int)((image.getHeight() - lineMetrics.getHeight()) / 2);
		y += mode == Mode.CIRCLE ? 80 : 60;
		g.drawString(label, x, y);
		ImageIO.write(image, "png", 
			new File(String.format("%sjava%s.png",dir, label)));
		} catch (Exception ex) {
			throw new RuntimeException(STR."WTF: Failure \{ex}");
		}
	}
}
