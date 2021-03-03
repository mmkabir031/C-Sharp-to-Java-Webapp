package webapp;


import java.util.ArrayList;

public class CustomParser {
	String text;
	int position;
	int currentLine;
	char lastChar;
	char currentChar;
	char nextChar;
	ArrayList<Token> tokens;


	//Constructor
	CustomParser(String text) {
		this.text = text;
		this.position = -1;
		this.currentLine = 1;
		this.lastChar = '\0';
		this.currentChar = 's';
		this.nextChar = '\0';

	}

	/* Returns and arrayList of tokens, each token is a string containing
	 * the token type, token value, and line number for the token
	 */
	public ArrayList<Token> getAllTokens(CustomParser self) {
		//Declare/instantiate variables
		Token token;
		String tempToken = "";
		tokens = new ArrayList<>();

		//This is for the very first character in input stream
		if(self.currentLine == 1) {
			moveForward(self);
		}
		//Keep going till the end of the file
		while(!self.endOfFile(self)) {
			//self explanatory functions
			removeComments(self);
			removeWhitespace(self);
			//If current character is a symbol then it's a token
			//Let's add the token
			if(containsSymbol(self.currentChar)) {
				for (EnumToken EnumTokenType : EnumToken.values()) {
					if (EnumTokenType.getChar() == (self.currentChar)) {
						switch (EnumTokenType) {

							//TODO: Ask brett if we want to change this to String cuz of "==" or "!=" etc
							//TODO: for now i have commented them out



							//TODO: check if its possible to use "lex" from enum cuz it is there.... will look into it in a while
							//Done
							case ASSIG://TODO: confirm if this is not a token? aka "="
								//TODO: Make it so it makes ONE token for "=="
								//done
								if (EnumTokenType.getChar() == (self.nextChar)) {
									//	System.out.println("current "+ self.currentChar);
									//	System.out.println("next "+ self.nextChar);
									//	System.out.println(currentLine);
									tokens.add(new Token((Character.toString(EnumTokenType.getChar()) + "="), "EQUAL_TO", self.currentLine));
									self.moveForward(self);
								}else
								tokens.add(new Token(Character.toString(EnumTokenType.getChar()) , "ASSIGN_OP", self.currentLine));
								//tokens.add("Left paren, symbol: " + self.currentChar + ", Line #" + self.currentLine);
								break;
							case CURLY_BRACKET_OPEN:
								tokens.add(new Token(Character.toString(EnumTokenType.getChar()), "LEFT_CURLY", self.currentLine));
								//tokens.add("Semicolon, symbol: " + self.currentChar + ", Line #" + self.currentLine);
								break;
							case CURLY_BRACKET_CLOSE:
								tokens.add(new Token(Character.toString(EnumTokenType.getChar()), "RIGHT_CURLY", self.currentLine));
								//tokens.add("Semicolon, symbol: " + self.currentChar + ", Line #" + self.currentLine);
								break;
							case MINUS:
								//TODO: Make it so it makes ONE token for "--"
								//done
								if (EnumTokenType.getChar() == (self.nextChar)) {
								//	System.out.println("current "+ self.currentChar);
								//	System.out.println("next "+ self.nextChar);
								//	System.out.println(currentLine);
									tokens.add(new Token((Character.toString(EnumTokenType.getChar()) + "-"), "DEC_OP", self.currentLine));
									self.moveForward(self);
								}else
								tokens.add(new Token((Character.toString(EnumTokenType.getChar())), "SUBTRACTION_OP", self.currentLine));
								//tokens.add("Subtraction Op, symbol: " + self.currentChar + ", Line #" + self.currentLine);


								break;
							case MULTIPLY:
								tokens.add(new Token(Character.toString(EnumTokenType.getChar()), "MULTIPLICATION_OP", self.currentLine));
								//tokens.add("Multiplication Op, symbol: " + self.currentChar + ", Line #" + self.currentLine);
								break;
							case DIVIDE:
								tokens.add(new Token(Character.toString(EnumTokenType.getChar()), "DIVISION_OP", self.currentLine));
								//tokens.add("Division Op, symbol: " + self.currentChar + ", Line #" + self.currentLine);
								break;
							case PLUS:
								//TODO: Make it so it makes ONE token for "++"
								//done
								if (EnumTokenType.getChar() == (self.nextChar)) {
								//	System.out.println("current "+ self.currentChar);
								//	System.out.println("next "+ self.nextChar);
								//	System.out.println(currentLine);
									tokens.add(new Token((Character.toString(EnumTokenType.getChar()) + "+"), "INC_OP", self.currentLine));
									self.moveForward(self);
								}else

								tokens.add(new Token(Character.toString(EnumTokenType.getChar()), "ADDITION_OP", self.currentLine));
								//tokens.add("Addition, symbol: " + self.currentChar + ", Line #" + self.currentLine);
								break;
							case SEMICOLON:
								tokens.add(new Token(Character.toString(EnumTokenType.getChar()), "SEMICOLON", self.currentLine));
								//tokens.add("Semicolon, symbol: " + self.currentChar + ", Line #" + self.currentLine);
								break;

							case MOD:
								tokens.add(new Token(Character.toString(EnumTokenType.getChar()), "MODULUS ", self.currentLine));
								//tokens.add("Left paren, symbol: " + self.currentChar + ", Line #" + self.currentLine);
								break;

							case OPEN_BRACKET:
								tokens.add(new Token(Character.toString(EnumTokenType.getChar()), "LEFT_PAREN", self.currentLine));
								//tokens.add("Left paren, symbol: " + self.currentChar + ", Line #" + self.currentLine);
								break;
							case GT_OPERATOR:
								//TODO: Make it so it makes ONE token for ">="
								//done
								if ((EnumToken.ASSIG.getChar())==(self.nextChar)){
								//	System.out.println("current "+ self.currentChar);
								//	System.out.println("next "+ self.nextChar);
								//	System.out.println(currentLine);
									tokens.add(new Token((Character.toString(EnumTokenType.getChar()) + "="), "GT_E_OPERATOR", self.currentLine));
									self.moveForward(self);
								}else

								tokens.add(new Token(Character.toString(EnumTokenType.getChar()), "GT_OPERATOR", self.currentLine));

								break;

							case LT_OPERATOR:
								//TODO: Make it so it makes ONE token for "<="
								//done
								if ((EnumToken.ASSIG.getChar())==(self.nextChar)){
								//	System.out.println("current "+ self.currentChar);
								//	System.out.println("next "+ self.nextChar);
								//	System.out.println(currentLine);
									tokens.add(new Token((Character.toString(EnumTokenType.getChar()) + "="), "LT_E_OPERATOR", self.currentLine));
									self.moveForward(self);
								}else
								tokens.add(new Token(Character.toString(EnumTokenType.getChar()), "LT_OPERATOR", self.currentLine));

							break;
							case CLOSE_BRACKET:
								tokens.add(new Token(Character.toString(EnumTokenType.getChar()), "RIGHT_PAREN", self.currentLine));
								//tokens.add("Right paren, symbol: " + self.currentChar + ", Line #" + self.currentLine);
								break;
							case EXPONENT:
								tokens.add(new Token(Character.toString(EnumTokenType.getChar()), "EXPONENT", self.currentLine));
								//tokens.add("Left paren, symbol: " + self.currentChar + ", Line #" + self.currentLine);
								break;
							case COMMA:
								tokens.add(new Token(Character.toString(EnumTokenType.getChar()), "COMMA", self.currentLine));
								//tokens.add("Comma, symbol: " + self.currentChar + ", Line #" + self.currentLine);
								break;
							case SQUARE_OPEN_BRACKET:
								tokens.add(new Token(Character.toString(EnumTokenType.getChar()), "LEFT_BRACKET", self.currentLine));
								//tokens.add("Left bracket, symbol: " + self.currentChar + ", Line #" + self.currentLine);
								break;
							case SQUARE_CLOSE_BRACKET:
								tokens.add(new Token(Character.toString(EnumTokenType.getChar()), "RIGHT_BRACKET", self.currentLine));
								//tokens.add("Right bracket, symbol: " + self.currentChar + ", Line #" + self.currentLine);
								break;



						}

					}
				}
				self.moveForward(self);
			}

			//Let's now check for stringLiteral's
			token = stringLiteral(self);
			//If we got one we add it, if not move on
			//TODO: get what? give an example perhaps?
			//Not sure....
			//TODO: Remove it for code cleanup and see...if anything breaks
			if (token.getLexeme() != "") {
				tokens.add(token);
				//token = "";
			}
			//Stops at the first sign of whitespace, a special symbol like addition '+', or if we reached the end of the file
			while(!Character.isWhitespace(self.currentChar) && self.currentChar != '\0' && !containsSymbol(self.currentChar)) {
				removeComments(self);
				removeWhitespace(self);
				removeComments(self);
				tempToken += self.currentChar;
				self.moveForward(self);
			}
			//Let's check for reserved words. If we find one then add it
			if (isReservedWord(tempToken)) {
				//tokens.add(new Token(tempToken, "RESERVED", self.currentLine));
				tokens.add(addReservedWord(tempToken));
				tempToken = "";
				//tokens.add("Reserved Word, symbol: " + token + ", Line #" + self.currentLine);
			}
			else if (tempToken != "") {
				//Look for integer literal using regular expressions
				if (tempToken.matches("[0-9]+")) {
					tokens.add(new Token(tempToken, "INTEGER_LITERAL", self.currentLine));
					//tokens.add("Integer Literal, symbol: " + token + ", Line #" + self.currentLine);
				}
				else if (tempToken.matches("0[0-9A-F]+h")) {
					tokens.add(new Token(tempToken, "HEX", self.currentLine));
					//tokens.add("Hexadecimal, symbol: " + token + ", Line #" + self.currentLine);
				}
				else if (tempToken.matches("=")) {
					tokens.add(new Token(tempToken, "ASSIGNMENT_OP", self.currentLine));
					//tokens.add("Assignment Op, symbol: " + token + ", Line #" + self.currentLine);
				}
				else if (tempToken.matches("[<>=!]+")) {
					tokens.add(new Token(tempToken, "RELATIONAL_OP", self.currentLine));
					//tokens.add("Relational Op, symbol: " + token + ", Line #" + self.currentLine);
				}
				else if (tempToken.matches("[-+]?[0-9]*\\.?[0-9]+")) {
					tokens.add(new Token(tempToken, "REAL_CONSTANT", self.currentLine));
					//tokens.add("Real Constant, symbol: " + token + ", Line #" + self.currentLine);
				}
				else {
					tokens.add(new Token(tempToken, "IDENTIFIER", self.currentLine));
					//tokens.add("Identifier, symbol: " + token + ", Line #" + self.currentLine);
				}

				tempToken = "";
			}
		}

		tokens.add(new Token("End of File", "EOF", self.currentLine + 1));
		return tokens;
	}

	//Ignore whitespace
	public void removeWhitespace(CustomParser self) {
		while (self.currentChar != '\0' && Character.isWhitespace(self.currentChar)) {
			self.moveForward(self);
		}
	}
	//Check for and return tokens of type string literal
	public Token stringLiteral (CustomParser self) {
		String result = "";
		if (self.currentChar != '\"') {
			return new Token(null, null, -1);
		}
		self.moveForward(self);
		while (self.currentChar != '\0' && self.currentChar != '\"') {
			result += self.currentChar;
			self.moveForward(self);
		}
		self.moveForward(self);
		return new Token(result, "STRING_LITERAL", self.currentLine);
		//return "String Literal, symbol: \"" + result + "\", line #" + self.currentLine;
	}

	//Function to remove the comments from input
	public String removeComments(CustomParser self) {
		String result = "";
		//int start = self.position;

		//If it's not a comment then return
		if (self.currentChar != '/') {
			return "";
		}
		self.moveForward(self);

		//Remove single line comments
		if(self.currentChar == '/') {
			while (self.currentChar != '\0' && self.currentChar != '\n' ) {
				result += self.currentChar;
				self.moveForward(self);
			}
			return result;
		}
		//Remove multiple line comments
		if (self.currentChar == '*') {
			self.moveForward(self);
			while (self.currentChar != '\0' && self.currentChar != '*' && self.nextChar != '/') {
				result += self.currentChar;
				self.moveForward(self);
			}
			self.moveForward(self);
			self.moveForward(self);
			return result;
		}
		return result;
	}
	//Move forward one character
	public void moveForward(CustomParser self) {
		if(self.lastChar == '\n') {
			self.currentLine += 1;
			//System.out.println("NewLine: " + self.currentLine);
		}

		self.position += 1;
		self.lastChar = self.currentChar;

		if (self.position >= (self.text.length())) {
			self.currentChar = '\0';
			self.nextChar = '\0';
		}
		else {
			self.currentChar = self.text.charAt(self.position);
			//System.out.println(self.currentChar);
			if (self.position >= self.text.length() - 1) {
				self.nextChar = '\0';
			}
			else {
				self.nextChar = self.text.charAt(self.position + 1);
			}
		}
	}
	//Check reserved words list
	public boolean isReservedWord(String value) {

		//TODO: make sure it uses ENUMKeywords
		//Done

		boolean reserved = false;

		for (EnumKeyword m : EnumKeyword.values()) {
			if (m.getString().equals(value)) {
				reserved = true;
				//System.out.println("Enum:  " + m.getString());
				//System.out.println("Value: " + value);
				break;
			}
		}
		//reserved is always false UNLESS above if statement is true
		return reserved;
	}
	//TODO: why was this made? ?
	//TODO: remove but it is used above so check if it breaks anything... will do later during code cleanup

	public Token addReservedWord(String value) {
		value = value.toLowerCase();
		if(value.equalsIgnoreCase("NOT") ||
				value.equalsIgnoreCase("GREATER") ||
				value.equalsIgnoreCase("OR") ||
				value.equalsIgnoreCase("EQUAL")) {
			return new Token(value, "RELATIONAL_OP", this.currentLine);
		}
		return new Token(value, "KEYWORD", this.currentLine);
	}

	//Check if character is a special symbol


	public boolean containsSymbol(char ch) {
		//TODO: make sure it uses ENUMToken
		//Done
		boolean isSymbol = false;

		for (EnumToken n : EnumToken.values()) {
			if (n.getChar() == (ch)) {
				isSymbol = true;
				//System.out.println("Enum:  " + n.getCharacters());
				//System.out.println("Value: " + ch);
				break;
			}
		}
		return isSymbol;
	}
	//Check if end of file
	public boolean endOfFile(CustomParser self) {
		return self.currentChar == '\0';
	}
}


//TODO: Are we supporting postfix/prefix? ++ and -- should be able to work...
//TODO: make a token for != and make sur it errors out if its just ! ...or let it go through the switch statement.. thatll work too..


