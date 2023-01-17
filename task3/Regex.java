package task3;
public class Regex {

	private static final String REGEX_NUM = "(\\d)+";
    private static final String REGEX_PLUS = "(\\+)";
    private static final String REGEX_MINUS = "(\\-)";
    private static final String REGEX_MULT = "(\\*)";
    private static final String REGEX_DIV = "(/)";

	
	
	public static boolean isNum(String token) {
		return token.matches(REGEX_NUM);
	}
	
	public static boolean isPlus(String token) {
		return token.matches(REGEX_PLUS);
	}

	public static boolean isMinus(String token) {
		return token.matches(REGEX_MINUS);
	}

	public static boolean isMult(String token) {
		return token.matches(REGEX_MULT);
	}

	public static boolean isDiv(String token) {
		return token.matches(REGEX_DIV);
	}

}
