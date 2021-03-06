import java.util.*;

/**
 * Created by PetruninaIN on 16.01.2016.
 */

// Создать консольный калькулятор, который поддерживает +, -, *, /, ^

public class Calc {
    public static void main(String[] args) throws Exception {
        String exprInp;
        double ans=0;
        System.out.println("Поддерживаются сложение (+), вычитание (-), умножение (*), деление (/), возведение в степень (^), а также скобки () ");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Введите выражение, которое нужно вычислить: ");
            exprInp = scanner.nextLine();

            if(exprInp.equals("Close") || exprInp.equals("close")) break;
            else {
                exprInp = ReversePolishNotation(exprInp);
                if(exprInp.equals("false")) System.out.println();
                else {
                    ans = CalcExp(exprInp);
                    if (ans == 0.0000000177) {
                        System.out.println("Используйте только цифры, +, -, *, /, ^ и (). Проверьте правильность введенного выражения!");
                        ans=0;
                    }
                    else System.out.println(ans);
                }

            }
        }
    }

    //Преобразование входной строки в обратную польскую нотацию
    // т.е. (1+2)*4+3   преобразуется в   1 2 + 4 × 3 +
    private static String ReversePolishNotation(String exprInp) {
        StringBuilder sbStack = new StringBuilder(""), sbOut = new StringBuilder("");
        char symbolIn, SymbolStack;

        for (int i = 0; i < exprInp.length(); i++) {
            //считываем символ из введенного выражения
            symbolIn = exprInp.charAt(i);
            if (TrueOperat(symbolIn)) {
                while (sbStack.length() > 0) {
                    //считываем символ из стека для операций
                    SymbolStack = sbStack.substring(sbStack.length()-1).charAt(0);
                    //проверка приоритетов
                    if (TrueOperat(SymbolStack) && (PrioritetOperat(symbolIn) <= PrioritetOperat(SymbolStack))) {
                        //добавляем символ в конец строки
                        sbOut.append(" ").append(SymbolStack).append(" ");
                        sbStack.setLength(sbStack.length()-1);
                    } else {
                        //добавляем символ в конец строки
                        sbOut.append(" ");
                        break;
                    }
                }
                //добавляем символ в конец строки
                sbOut.append(" ");
                sbStack.append(symbolIn);
            } else if ('(' == symbolIn) {
                //добавляем символ в конец строки
                sbStack.append(symbolIn);
            } else if (')' == symbolIn) {
                SymbolStack = sbStack.substring(sbStack.length()-1).charAt(0);
                while ('(' != SymbolStack) {
                    //добавляем символ в конец строки
                    sbOut.append(" ").append(SymbolStack);
                    sbStack.setLength(sbStack.length()-1);
                    SymbolStack = sbStack.substring(sbStack.length()-1).charAt(0);
                }
                sbStack.setLength(sbStack.length()-1);
            } else if(TrueChar(symbolIn)) {
                // Если символ не является знаком операции, то добавляем его в выходную последовательность
                sbOut.append(symbolIn);
            }
            else {
                System.out.println("Не используйте буквы или символы, кроме +,-,*,^,/,(,)");
                return "false";
            }
        }
        // Если в стеке остались операторы, добавляем их в входную строку
        while (sbStack.length() > 0) {
            sbOut.append(" ").append(sbStack.substring(sbStack.length()-1));
            sbStack.setLength(sbStack.length()-1);
        }
        return  sbOut.toString();
    }

    //Проверка правильности ввода знаков операций
    private static boolean TrueChar(char OperInp) {
        switch (OperInp) {
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case '0':
            case '.':
                return true;
        }
        return false;
    }

    //Проверка правильности ввода знаков операций
    private static boolean TrueOperat(char OperInp) {
        switch (OperInp) {
            case '-':
            case '+':
            case '*':
            case '/':
            case '^':
                return true;
        }
        return false;
    }

    //Вычисление приоритета для каждой операции
    private static byte PrioritetOperat(char OperInp) {
        switch (OperInp) {
            case '^': //возведение в степень
                return 2;
            case '*': //умножение
            case '/': //деление
                return 1;
        }
        return 0; // сложение и вычитание
    }

    //Вычисление выражения
    private static double CalcExp(String exprInp) throws Exception {
        double FirstSymbol = 0, SecondSymbol = 0;
        String SymbolStack;
        Deque<Double> stack = new ArrayDeque<Double>();
        StringTokenizer st = new StringTokenizer(exprInp);
        while(st.hasMoreTokens()) {
            try {
                SymbolStack = st.nextToken().trim();
                if (1 == SymbolStack.length() && TrueOperat(SymbolStack.charAt(0))) {
                    SecondSymbol = stack.pop();
                    FirstSymbol = stack.pop();
                    switch (SymbolStack.charAt(0)) {
                        case '+':
                            FirstSymbol += SecondSymbol;
                            break;
                        case '-':
                            FirstSymbol -= SecondSymbol;
                            break;
                        case '*':
                            FirstSymbol *= SecondSymbol;
                            break;
                        case '/':
                            FirstSymbol /= SecondSymbol;
                            break;
                        case '^':
                            FirstSymbol = Math.pow(FirstSymbol, SecondSymbol);
                            break;
                        default:
                    }
                    stack.push(FirstSymbol);
                } else {
                    FirstSymbol = Double.parseDouble(SymbolStack);
                    stack.push(FirstSymbol);
                }
            } catch (Exception e) {
                return 0.0000000177;
            }
        }
        return stack.pop();
    }
}
