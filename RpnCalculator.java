import java.util.Scanner;
import java.io.File;
import java.util.Stack;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class RpnCalculator {

    private static Stack<Double> pilha = new Stack<>();
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);  
        System.out.println("Digite o nome do arquivo que deseja ler");
        String filename = scan.nextLine();
        
        Scanner scanner = new Scanner(new FileReader("./"+ filename)).useDelimiter("\\n");
        System.out.println("Digite inicie a operação (s para sair): ");

        String input = "";
        while (scanner.hasNextLine()) {
            input = scanner.nextLine();

            if (isNumber(input)) {
                pilha.push(Double.parseDouble(input));
            } else if (isOperation(input)) {
                Double current = parseOperation(input, pilha);
                if (pilha.size() == 0) {
                    System.out.println(">> " + current);
                }
                pilha.push(current);
            }
        }
    }

    // Aqui é necessário entender com cuidado algumas definições de uma RPN.
    // Cada operação depende de dois números. Se há apenas um item na pilha
    // quando for requisitada a operação, nada deve ocorrer e o numeral deve
    // retornar à pilha. Se não houver nada na pilha, o número 0 deve ser
    // colocado. O retorno deste método é o que será colocado na pilha. Perceba
    // que [buffer.pop()] é chamado duas vezes, indicando que dois números são
    // retirados, enquanto apenas um (o resultado entre eles) é colocado de volta.
    public static Double parseOperation(String operation, Stack<Double> pilha) {
        Double result = (pilha.empty()) ? 0 : pilha.pop();

        if (!pilha.empty()) {
            result = operate(operation, pilha.pop(), result);
        }

        return result;
    }

    // É necessário se atentar ao fato de que não é possível dividir por 0.
    // Este é um problema comum em programação: Ter de tratar possíveis entradas
    // inválidas que o usuário possa introduzir ao programa. No caso optamos por
    // indicar o erro e em caso de operação inválida, manter o último número que
    // havia anteriormente na pilha.
    public static Double operate(String operation, Double left, Double right) {
        switch (operation) {
            case "+": return left + right;
            case "-": return left - right;
            case "*": return left * right;
            case "/": {
                if (right == 0) {
                    System.out.println("ERR: Cannot divide by 0");
                    return left;
                }
                return left / right;
            }
            default: return left;
        }
    }

    // Para evitar prosseguir para estados inválidos por erro de digitação,
    // os métodos abaixo verificam se a entrada do usuário pertence a uma de
    // duas possíveis naturezas:
    //   É um numeral inteiro, ou
    //   É uma operação reconhecida
    // Para que assim possamos prosseguir pro comportamento correto
    public static boolean isNumber(String input) {
        if (input == null) return false;

        try {
            Double.parseDouble(input);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public static boolean isOperation(String input) {
        if (input == null) return false;

        return input.equals("+") || input.equals("-") || input.equals("*") || input.equals("/");
    }
}