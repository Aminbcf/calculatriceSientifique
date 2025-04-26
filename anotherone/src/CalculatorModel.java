public class CalculatorModel {
    private String expression;
    private double result;

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public double getResult() {
        return result;
    }

    public void calculate() {
        this.result = new ExpressionParser().parse(expression);
    }

    public void clear() {
        expression = "";
        result = 0;
    }


    private static class ExpressionParser {
        private int pos = -1;
        private int ch;
        // Move to the next character in the expression
        private void nextChar(String expression) {
            ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
        }
        // Skip whitespace characters and check if the current character matches the one to be eaten
        private boolean eat(int charToEat, String expression) {
            while (ch == ' ') nextChar(expression);
            if (ch == charToEat) {
                nextChar(expression);
                return true;
            }
            return false;
        }
        // Parse the entire expression and return the result
        private double parse(String expression) {
            nextChar(expression);
            double x = parseExpression(expression);
            if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char)ch);
            return x;
        }

        // Parse the expression by evaluating the terms and handling addition and subtraction
        private double parseExpression(String expression) {
            double x = parseTerm(expression);
            while (true) {
                if (eat('+', expression)) x += parseTerm(expression);
                else if (eat('-', expression)) x -= parseTerm(expression);
                else return x;
            }
        }

        // Parse the term by evaluating the factors and handling multiplication, division, and exponentiation
        private double parseTerm(String expression) {
            double x = parseFactor(expression);
            while (true) {
                if (eat('*', expression)) x *= parseFactor(expression);
                else if (eat('/', expression)) x /= parseFactor(expression);
                else if (eat('^', expression)) x = Math.pow(x, parseFactor(expression));
                else return x;
            }
        }

        // Parse the factor by handling positive/negative signs, parentheses, numbers, and functions
        private double parseFactor(String expression) {
            if (eat('+', expression)) return parseFactor(expression);
            if (eat('-', expression)) return -parseFactor(expression);

            double x;
            int startPos = this.pos;
            if (eat('(', expression)) {
                // Handle parentheses by recursively parsing the expression inside them
                x = parseExpression(expression);
                eat(')', expression);
            } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                // Parse numbers (integer or decimal)
                while ((ch >= '0' && ch <= '9') || ch == '.') nextChar(expression);
                x = Double.parseDouble(expression.substring(startPos, this.pos));
            } else if (ch >= 'a' && ch <= 'z') {
                // Parse functions (such as sqrt, sin, cos, etc.)
                while (ch >= 'a' && ch <= 'z') nextChar(expression);
                String func = expression.substring(startPos, this.pos);
                x = parseFactor(expression);
                switch (func) {
                    case "sqrt":
                        x = Math.sqrt(x);
                        break;
                    case "cbrt":
                        x = Math.cbrt(x);
                        break;
                    case "log":
                        x = Math.log10(x);
                        break;
                    case "sin":
                        x = Math.sin(Math.toRadians(x));
                        break;
                    case "cos":
                        x = Math.cos(Math.toRadians(x));
                        break;
                    case "tan":
                        x = Math.tan(Math.toRadians(x));
                        break;
                    case "asin":
                        x = Math.toDegrees(Math.asin(x));
                        break;
                    case "acos":
                        x = Math.toDegrees(Math.acos(x));
                        break;
                    case "atan":
                        x = Math.toDegrees(Math.atan(x));
                        break;
                    case "!":
                        x = factorial((int) x);
                        break;
                    case "%":
                        x = x / 100.0;
                        break;
                    case "|x|":
                        x = Math.abs(x);
                        break;
                    default:
                        throw new RuntimeException("Unknown function: " + func);
                }
            } else {
                throw new RuntimeException("Unexpected: " + (char)ch);
            }

            return x;
        }

        // Compute the factorial of a number
        private int factorial(int n) {
            if (n == 0) return 1;
            int fact = 1;
            for (int i = 1; i <= n; i++) {
                fact *= i;
            }
            return fact;
        }

    }
}