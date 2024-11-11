package codes.domino.constant;

public enum PossibleOptions {
    PLUS("What is X plus Y?", '+'),
    MINUS("What is X minus Y?", '-'),
    MULTIPLY("What is X times Y?", '*'),
    DIVIDE("What is X divided by Y?", '/');

    private final String content;
    private final String[] words;
    private final char option;

    PossibleOptions(String content, char option) {
        this.content = content;
        words = content.split(" ");
        this.option = option;
    }

    public boolean isSimilar(String text) {
        //System.out.println("text = " + text);
        int grade = 0;
        int maxGrade = words.length;

        for (String word : words) {
            if (text.contains(word)) {
                grade++;
            }
        }
        return ((double) grade / maxGrade) >= 0.6;
    }
    public static PossibleOptions getOption(String question) {
        for (PossibleOptions option : PossibleOptions.values()) {
            if (option.isSimilar(question)) {
                return option;
            }
        }
        return null;
    }

    public String content() {
        return content;
    }

    public char option() {
        return option;
    }
}
