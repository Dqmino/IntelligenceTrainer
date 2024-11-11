package codes.domino.screen.visual;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import static codes.domino.screen.ScreenReader.postOCRRequest;

public abstract class VisualObject {
    public int x;
    public int y;
    public int width;
    public int height;
    protected final Robot robot;
    private final ByteArrayOutputStream baos;
    private final BufferedImage screenCapture;
    public VisualObject(int x, int y, int width, int height, Robot robot) throws IOException {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.robot = robot;
        BufferedImage screenCapture = robot.createScreenCapture(new Rectangle(x, y, width, height));
        this.screenCapture = screenCapture;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(screenCapture, "png", baos);
        baos.close();
        this.baos = baos;
        System.out.println("Screen captured");
    }

    public String evaluateText() {
        String text = postOCRRequest(Base64.getEncoder().encodeToString(
                baos.toByteArray())).getString("ParsedText")
//                .replace("Try some small talk on someone nearby.", "").replace("'", "")
                .replace("\n", " ").replace("\r", "")
                .replace("_", "")
                .replace(",", "");
        System.out.println("Text: " + text);
        return text;
    }

    public BufferedImage getScreenCapture() {
        return screenCapture;
    }
}
