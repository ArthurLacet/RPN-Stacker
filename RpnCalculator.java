import java.util.Scanner;
import java.util.Stack;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class RpnCalculator {

    private static Stack<Double> pilha = new Stack<>();
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);  
        System.out.println("Digite o nome do arquivo que deseja ler");
        String filename = scan.nextLine();
        
        Scanner scanner = new Scanner(new FileReader("./"+ filename)).useDelimiter("\\n");
        System.out.println("Pressione a tecla s para sair");

        String input = "";
        while (scanner.hasNextLine()) {
            input = scanner.nextLine();

            if (isNumber(input)) {
                pilha.push(Double.parseDouble(input));
                Token token = new Token(TokenType.NUM, input);
                System.out.println(token.toString());

            } else if (isOperation(input) != null) {
                if (isOperation(input) == TokenType.PLUS){
                    Token token = new Token(TokenType.PLUS, input);
                    System.out.println(token.toString());
                }
                if (isOperation(input) == TokenType.MINUS){
                    Token token = new Token(TokenType.MINUS, input);
                    System.out.println(token.toString());
                }
                if (isOperation(input) == TokenType.STAR){
                    Token token = new Token(TokenType.STAR, input);
                    System.out.println(token.toString());

                }
                if (isOperation(input) == TokenType.SLASH){
                    Token token = new Token(TokenType.SLASH, input);
                    System.out.println(token.toString());
                }
                Double current = parseOperation(input, pilha);
                if (pilha.size() == 0) {
                    System.out.println(">> " + current);
                }
                pilha.push(current);
            } else{
                System.out.println("Error: Unexpected character: " + input);
                break;
            }
        }
    }

    // Aqui ?? necess??rio entender com cuidado algumas defini????es de uma RPN.
    // Cada opera????o depende de dois n??meros. Se h?? apenas um item na pilha
    // quando for requisitada a opera????o, nada deve ocorrer e o numeral deve
    // retornar ?? pilha. Se n??o houver nada na pilha, o n??mero 0 deve ser
    // colocado. O retorno deste m??todo ?? o que ser?? colocado na pilha. Perceba
    // que [buffer.pop()] ?? chamado duas vezes, indicando que dois n??meros s??o
    // retirados, enquanto apenas um (o resultado entre eles) ?? colocado de volta.
    public static Double parseOperation(String operation, Stack<Double> pilha) {
        Double result = (pilha.empty()) ? 0 : pilha.pop();

        if (!pilha.empty()) {
            result = operate(operation, pilha.pop(), result);
        }

        return result;
    }

    // ?? necess??rio se atentar ao fato de que n??o ?? poss??vel dividir por 0.
    // Este ?? um problema comum em programa????o: Ter de tratar poss??veis entradas
    // inv??lidas que o usu??rio possa introduzir ao programa. No caso optamos por
    // indicar o erro e em caso de opera????o inv??lida, manter o ??ltimo n??mero que
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

    // Para evitar prosseguir para estados inv??lidos por erro de digita????o,
    // os m??todos abaixo verificam se a entrada do usu??rio pertence a uma de
    // duas poss??veis naturezas:
    //   ?? um numeral inteiro, ou
    //   ?? uma opera????o reconhecida
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

    public static TokenType isOperation(String input) {
        if (input.equals("+")) return TokenType.PLUS;
        if (input.equals("-")) return TokenType.MINUS;
        if (input.equals("*") ) return TokenType.STAR;
        if (input.equals("/")) return TokenType.SLASH;
        return null;
    }
}