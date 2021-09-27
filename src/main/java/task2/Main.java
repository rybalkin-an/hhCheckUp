package task2;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * Задача 2. Ролевой помощник
 * Условие задачи
 *
 * Ограничение времени, с	1
 * Ограничение памяти, МБ	64
 * Общее число попыток отправки	15
 *
 * По пятницам мы часто играем в популярную ролевую игру "Релиз до выходных" с коллегами. Правила этой игры довольно
 * сложны и предполагают хорошую стратегию и планирование. Чтобы иметь представление о последствиях тех или иных ходов,
 * часто хочется понимать, насколько вероятен тот или иной исход ситуации, с учетом разных вариантов выпадения игральных
 * костей.
 *
 * Необходимо написать программу, которая сможет, приняв на вход последовательность операндов и операций, вывести все
 * возможные варианты результата и их вероятности.
 *
 * Выражение на входе может содержать скобки, и следующие операторы в порядке уменьшения их приоритета:
 *
 * * – умножение
 * + и - – сложение и вычитание
 * > - левый операнд больше, чем правый. Результат равен 1, если истинно, и 0 - если ложно
 *
 * В качестве операндов могут выступать:
 *
 * n - целые положительные числа, либо 0 (0≤n≤100 000)
 * dn - результат броска игральной кости, где n целое положительное число, количество граней (1≤n≤100). Результатом
 * будет равномерное распределение вероятностей между всеми гранями (от 1 до n). Каждый такой операнд в выражении – это
 * результат отдельного броска (например, d4+d4 – это сумма результатов двух разных бросков четырехгранной кости).
 *
 * Входные данные (поступают в стандартный поток ввода)
 * Одна строка без пробелов, содержащая выражение для вычисления. Выражение гарантировано вычисляемое и не содержит
 * синтаксических ошибок. Максимальная длина строки 110 символов.
 *
 *
 * Выходные данные (ожидаются в стандартном потоке вывода)
 * Одна или несколько строк, в каждой из которых есть два числа, разделенных пробелом:
 *
 * 1. целое – один из вариантов результата
 * 2. вещественное - процент вероятности такого варианта с математическим округлением до 2 знаков и разделителем .
 * Строки на выходе должны быть отсортированы от меньшего результата к большему.
 *
 * Примечание к округлению - в связи с округлением вероятностей - суммарная вероятность всех исходов может быть
 * не равна 100%, это нормально, компенсировать это в решении не нужно.
 *
 *
 * Пример 1
 * Ввод:
 *
 * 2+2
 * Вывод:
 *
 * 4 100.00
 *
 * Пример 2
 * Ввод:
 *
 * d4+2
 * Вывод:
 *
 * 3 25.00
 * 4 25.00
 * 5 25.00
 * 6 25.00
 *
 * Пример 3
 * Ввод:
 *
 * d4+(d6>2)
 * Вывод:
 *
 * 1 8.33
 * 2 25.00
 * 3 25.00
 * 4 25.00
 * 5 16.67
 *
 * Примечания по оформлению решения
 * Возможно использование только стандартных библиотек языков, установки и использование дополнительных библиотек невозможны.
 *
 * При отправке решений на Java необходимо назвать исполняемый класс Main. В решении не нужно указывать пакет.
 */
public class Main {
    public static class SyntaxErrorException extends Exception {
        /** Construct a SyntaxErrorException with the specified message.
         @param message The message
         */
        SyntaxErrorException(String message) {
            super(message);
        }
    }
    /** This is the stack of operands:
     i.e. (doubles/parentheses/brackets/curly braces)
     */
    private static Stack<Double> operandStack = new Stack<Double>();

    /** This is the operator stack
     *  i.e. (+-/*%^)
     */
    private static Stack<String> operatorStack = new Stack<String>();

    /** These are the possible operators */
    private static final String OPERATORS = "+-/*%^()[]{}";
    private static final String BRACES = "()[]{}";
    private static final String NONBRACES = "+-/*%^";
    //                                       +  -  /  *  %   ^  (   )   [   ]   {   }
    private static final int[] PRECEDENCE = {1, 1, 2, 2, 3, 3, -1, -1, -1, -1, -1, -1};
    /** This is an ArrayList of all the discrete
     things (operators/operands) making up an input.
     This is really just getting rid of the spaces,
     and dividing up the "stuff" into manageable pieces.
     */
    static ArrayList<String> input = new ArrayList<String>();

    /**
     * TODO: write this
     * @param postfix
     * @return
     */
    public static ArrayList inputCleaner(String postfix){
        StringBuilder sb = new StringBuilder();
        String noSpaces = postfix.replace(" ", "");
        try {
            for (int i = 0; i < noSpaces.length(); i++) {
                char c = noSpaces.charAt(i);
                boolean isNum = (c >= '0' && c <= '9');

                if (isNum) {
                    sb.append(c);
                    if (i == noSpaces.length()-1) {
                        input.add(sb.toString());
                        sb.delete(0, sb.length());
                    }
                } else if (c == '.') {
                    for (int j = 0; j < sb.length(); j++) {
                        if (sb.charAt(j) == '.') {
                            throw new SyntaxErrorException("You can't have two decimals in a number.");
                        } else if (j == sb.length() - 1) {
                            sb.append(c);
                            j = (sb.length() + 1);
                        }
                    }
                    if (sb.length() == 0) {
                        sb.append(c);
                    }
                    if (i == noSpaces.length()-1) {
                        throw new SyntaxErrorException("You can't end your equation with a decimal!");
                    }
                } else if (OPERATORS.indexOf(c)!= -1) {
                    if (sb.length() != 0) {
                        input.add(sb.toString());
                        sb.delete(0, sb.length());
                    }
                    sb.append(c);
                    input.add(sb.toString());
                    sb.delete(0, sb.length());
                } else {
                    throw new SyntaxErrorException("Make sure your input only contains numbers, operators, or parantheses/brackets/braces.");
                }
            }

            int numLP = 0;
            int numRP = 0;
            int numLB = 0;
            int numRB = 0;
            int numLBr = 0;
            int numRBr = 0;

            for (int f = 0; f < input.size(); f++) {
                switch (input.get(f)) {
                    case "(": numLP++;
                        break;
                    case "[": numLB++;
                        break;
                    case "{": numLBr++;
                        break;
                    case ")": numRP++;
                        break;
                    case "]": numRB++;
                        break;
                    case "}": numRBr++;
                        break;
                    default: //do nothing
                        break;
                }

            }
            if (numLP != numRP || numLB != numRB || numLBr != numRBr) {
                throw new SyntaxErrorException("The number of brackets, braces, or parentheses don't match up!");
            }

            int doop = 0;
            int scoop = 0;
            int foop = 0;
            for (int f = 0; f < input.size(); f++) {
                String awesome = input.get(f);
                switch (awesome) {
                    case "(": doop++;
                        break;
                    case "[": scoop++;
                        break;
                    case "{": foop++;
                        break;
                    case ")": doop--;
                        break;
                    case "]": scoop--;
                        break;
                    case "}": foop--;
                        break;
                    default: //do nothing
                        break;
                }
                if (doop < 0 || scoop < 0 || foop < 0) {
                    throw new SyntaxErrorException("The order of your parentheses, brackets, or braces is off.\nMake sure you open a set of parenthesis/brackets/braces before you close them.");
                }
            }
            if (NONBRACES.indexOf(input.get(input.size()-1)) != -1) {
                throw new SyntaxErrorException("The input can't end in an operator");
            }
            return input;
        } catch (SyntaxErrorException ex) {
            System.out.println(ex);
            return input;
        }
    }

    /**Method to process operators
     * @param op The operator
     * @throws SyntaxErrorException
     * @throws EmptyStackException
     */
    private static void processOperator(String op) throws SyntaxErrorException {
        if (operatorStack.empty() || op.equals("(") || op.equals("[") || op.equals("{")) {
            operatorStack.push(op);
        } else {
            //peek the operator stack and
            //let topOp be the top operator.
            String topOp = operatorStack.peek();
            if (precedence(op) > precedence(topOp)) {
                topOp = op;
                operatorStack.push(op);
            } else {
                System.out.println(operatorStack);
                System.out.println(operandStack);
                System.out.println("--------------");
                //Pop all stacked operators with equal
                // or higher precedence than op.
                while (operandStack.size() >= 2 && !operatorStack.isEmpty()) {
                    double r = operandStack.pop();
                    double l = operandStack.pop();
                    String work = getNextNonBracerOperator();
                    System.out.println("L:" + l + " R:" + r + " W:" + work);

                    doOperandWork(work, l, r);

                    if(op.equals("(") || op.equals("[") || op.equals("{")) {
                        //matching '(' popped - exit loop.
                        operandStack.push(l);
                        operandStack.push(r);
                        break;
                    }

                    if (!operatorStack.empty()) {
                        //reset topOp
                        topOp = operatorStack.peek();
                    }
                }

                //assert: Operator stack is empty or
                // current operator precedence > top of stack operator precedence.
                if(!op.equals(")") || !op.equals("}") || !op.equals("}")) {
                    operatorStack.push(op);
                }
            }
        }
    }

    /**
     * TODO: write this
     * @param expressions
     * @return
     * @throws SyntaxErrorException
     */
    public static String infixCalculator(ArrayList<String> expressions) throws SyntaxErrorException {
        for (String expression : expressions) {
            if (OPERATORS.indexOf(expression) == -1) {
                operandStack.push(Double.parseDouble(expression));
            } else {
                processOperator(expression);
            }
        }
        while (operandStack.size() >= 2 && !operatorStack.isEmpty()) {
            System.out.println("--------------");
            System.out.println(operandStack);
            System.out.println(operatorStack);

            double r = operandStack.pop();
            double l = operandStack.pop();
            String work = getNextNonBracerOperator();
            System.out.println("L:" + l + " R:" + r + " W:" + work);

            doOperandWork(work, l, r);
        }
        if(operandStack.isEmpty())
            return null;
        return String.valueOf(operandStack.pop());
    }

    /**
     * goes through the stack and pops off all non operatable operations until it gets to one that is in the NONBRACES String
     * @return The next operatable string
     */
    private static String getNextNonBracerOperator() {
        String work = "\0"; // \0 is null,
        while(!operatorStack.isEmpty() && NONBRACES.indexOf(work) == -1)
            work = operatorStack.pop();
        return work;
    }

    /**
     *
     * @param work The operator you want to work. This really should be a character but its still a string
     * @param l Left side number
     * @param r Right side number
     * @throws SyntaxErrorException If the operator could not be found
     */
    private static void doOperandWork(String work, double l, double r) throws SyntaxErrorException {
        switch (work) {
            case "+": operandStack.push(l+r);
                break;
            case "-": operandStack.push(l-r);
                break;
            case "*": operandStack.push(l*r);
                break;
            case "/": operandStack.push(l/r);
                break;
            case "%": operandStack.push(l%r);
                break;
            case "^": operandStack.push(Math.pow(l, r));
                break;
            default:
                throw new SyntaxErrorException("Invalid operand " + work);
        }
    }

    /**
     * @param op The operator
     * @return the precedence
     */
    private static int precedence(String op) {
        return PRECEDENCE[OPERATORS.indexOf(op)];
    }

    public static void main(String[] args) {
        try {
            ArrayList test = new ArrayList();
            Scanner f = new Scanner(System.in);

            //System.out.println("Please insert an argument: ");

            //String g = f.nextLine();
            //String g = "(1+1)^(3+1)";
            String g = "(1+3)*3^2+2*4-1";
            test = inputCleaner(g);

            for (int z = 0; z < test.size(); z++) {
                System.out.println(test.get(z));
            }

            System.out.println(infixCalculator(test));

            test.clear();
        } catch (SyntaxErrorException e) {
            System.out.println("Make sure you only put in operators and operands.");
            e.printStackTrace();
        }
    }
}
