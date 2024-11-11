package codes.domino.screen.eval;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class EvaluationManager {
    private final static ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");

    public static double evaluate(String script) {
        try {
            System.out.println("Attempting to evaluate: " + script);
            return round((double) engine.eval(script),2);
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
