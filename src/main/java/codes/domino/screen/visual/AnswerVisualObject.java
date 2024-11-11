package codes.domino.screen.visual;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.IOException;

public class AnswerVisualObject extends VisualObject {
    public int number;
    public AnswerVisualObject(int number) throws IOException, AWTException {
        super(814, (calculateYLevel(number)), 275, 30, new Robot());
        this.number = number;
    }

    private static int calculateYLevel(int number) {
        int yLevel;
        switch (number) {
            case 1:
                yLevel = 500;
                break;
            case 2:
                yLevel = 530;
                break;
            case 3:
                yLevel = 566;
                break;
            case 4:
                yLevel = 601;
                break;
            default:
                throw new RuntimeException("Invalid answer number");
        }
        return yLevel;
    }

    public void click() {

        System.out.println("Clicked");
        robot.mouseMove(955, calculateYLevel(number) + 15);
        robot.delay(300);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(200);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    @Override
    public String evaluateText() {
        return super.evaluateText().replace("I", "1").replace("S", "5")
                .replace("o", "0").replace("a", "7");
    }
}
