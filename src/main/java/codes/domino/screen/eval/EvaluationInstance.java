package codes.domino.screen.eval;

import codes.domino.screen.data.QuestionData;
import codes.domino.screen.visual.AnswerVisualObject;
import codes.domino.screen.visual.QuestionVisualObject;

import java.awt.*;
import java.io.IOException;

public class EvaluationInstance {

    public EvaluationInstance() throws IOException, AWTException {
        String actualText = new QuestionVisualObject().evaluateText();
        AnswerVisualObject[] answers = {
                new AnswerVisualObject(1),
                new AnswerVisualObject(2),
                new AnswerVisualObject(3),
                new AnswerVisualObject(4)
        };
        QuestionData questionData = new QuestionData(actualText);

        for (AnswerVisualObject optionVO : answers) {
            Double option;
            try {
                option = Double.parseDouble(optionVO.evaluateText());
            } catch (NumberFormatException e) {
                System.out.println("Invalid answer");
                System.out.println(optionVO.evaluateText());
                continue;
            }
            for (String possibleAnswerString : questionData.getEvaluableAnswer()) {
                if (option.equals(EvaluationManager.evaluate(possibleAnswerString))) {
                    optionVO.click();
                    break;
                }
            }
        }
    }
}
