package cn.maiaimei.framework.swift.util;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

public class SpelUtils {
    private static final SpelExpressionParser PARSER = new SpelExpressionParser();

    public static StandardEvaluationContext newStandardEvaluationContext(
            String name, Object value) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable(name, value);
        return context;
    }

    public static StandardEvaluationContext newStandardEvaluationContext(
            Map<String, Object> variables) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariables(variables);
        return context;
    }

    public static boolean parseExpressionAsBoolean(
            StandardEvaluationContext context, String expressionString) {
        return parseExpression(context, expressionString, Boolean.class);
    }

    public static <T> T parseExpression(
            StandardEvaluationContext context, String expressionString, Class<T> clazz) {
        Expression exp = PARSER.parseExpression(expressionString);
        return exp.getValue(context, clazz);
    }

    public SpelUtils() {
        throw new UnsupportedOperationException();
    }
}
