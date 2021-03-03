package webapp;

import java.util.HashMap;
import java.util.ArrayList;

public class Translation {
	
	public static ArrayList<Token> TranslateTokensToJava(ArrayList<Token> cSharpTokens) {
		ArrayList<Token> javaTokens = new ArrayList<Token>();
		
		
		HashMap<String, String> cToJavaKeywords = new HashMap<String, String>();
		cToJavaKeywords.put("using", "import");
		cToJavaKeywords.put("internal", "protected");
		cToJavaKeywords.put("Console.WriteLine", "System.out.println");

	    for (Token t : cSharpTokens) {
	        if(cToJavaKeywords.containsKey(t.getLexeme())) {
	        	Token java = new Token(cToJavaKeywords.get(t.getLexeme()), t.getTokenType(), t.getRowNumber());
	        	javaTokens.add(java);



				}else{
				Token java = new Token((t.getLexeme()), t.getTokenType(), t.getRowNumber());
				javaTokens.add(java);
	        }

	    }
		return javaTokens;
	}
}