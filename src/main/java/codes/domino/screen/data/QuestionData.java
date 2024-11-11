package codes.domino.screen.data;

import codes.domino.constant.PossibleOptions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class QuestionData {
    private final String question;
    public QuestionData(String question) {
        this.question = question;
    }

    public List<Double> getInvolvedNumbers() {
        return Arrays.stream(question.split(" "))
                .filter(s -> s.replace("?", "").matches("-?[0-9.]+"))
                .map(s -> Double.parseDouble(s.replace(" ", "")
                        .replace("?", ""))).collect(Collectors.toList());
    }
    public Set<List<Double>> getInvolvedNumbersSet() {
        List<Double> abPossibilities = getInvolvedNumbers();
        abPossibilities.set(0, abPossibilities.get(0) / 10);


        List<Double> baPossibilities = getInvolvedNumbers();
        baPossibilities.set(1, baPossibilities.get(1) / 10);


        List<Double> aaPossibilities = getInvolvedNumbers();
        aaPossibilities.set(0, aaPossibilities.get(0) / 10);
        aaPossibilities.set(1, aaPossibilities.get(1) / 10);

        List<Double> bbPossibilities = getInvolvedNumbers();

        return new HashSet<>(Arrays.asList(abPossibilities, baPossibilities, aaPossibilities, bbPossibilities));
    }
    public Set<String> getEvaluableAnswer() {
        HashSet<String> answers = new HashSet<>();
        for (List<Double> possibility : getInvolvedNumbersSet()) {
            String answer = possibility.get(0) + String.valueOf(PossibleOptions.getOption(question).option()) + "(" + possibility.get(1) + ")";
            answers.add(answer);
        }
        return answers;
    }


}
