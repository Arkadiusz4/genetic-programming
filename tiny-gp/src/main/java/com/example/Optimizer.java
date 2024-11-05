package com.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Optimizer {

    // Uproszcza wyrażenia arytmetyczne, uwzględniając operacje na stałych i usuwanie zbędnych nawiasów
    public static String simplifyExpression(String expression) {
        // Usuwanie podwójnych minusów i innych prostych symboli
        expression = expression.replaceAll("--", "+");  // Zamiana "--" na "+"
        expression = expression.replaceAll("\\+\\-", "-");  // Zamiana "+-" na "-"

        // Uproszczanie wyrażeń z operatorami na stałych liczbach w nawiasach
        Pattern pattern = Pattern.compile("\\((\\-?\\d+\\.?\\d*)\\s*([+\\-*/])\\s*(\\-?\\d+\\.?\\d*)\\)");
        Matcher matcher = pattern.matcher(expression);

        while (matcher.find()) {
            double leftOperand = Double.parseDouble(matcher.group(1));
            String operator = matcher.group(2);
            double rightOperand = Double.parseDouble(matcher.group(3));

            double result = 0;
            switch (operator) {
                case "+":
                    result = leftOperand + rightOperand;
                    break;
                case "-":
                    result = leftOperand - rightOperand;
                    break;
                case "*":
                    result = leftOperand * rightOperand;
                    break;
                case "/":
                    if (rightOperand != 0) {
                        result = leftOperand / rightOperand;
                    } else {
                        continue;
                    }
                    break;
            }

            // Zamiana operacji na wynik i usunięcie nawiasów wokół wyniku
            String simplifiedOperation = matcher.group(0);  // np. "(1.07 + 2.1)"
            expression = expression.replace(simplifiedOperation, String.valueOf(result));

            // Aktualizacja matcher’a dla nowego wyrażenia po zamianie
            matcher = pattern.matcher(expression);
        }

        // Usuwanie zbędnych nawiasów wokół pojedynczych liczb
        expression = expression.replaceAll("\\((\\-?\\d+\\.?\\d*)\\)", "$1");

        return expression;
    }
}
