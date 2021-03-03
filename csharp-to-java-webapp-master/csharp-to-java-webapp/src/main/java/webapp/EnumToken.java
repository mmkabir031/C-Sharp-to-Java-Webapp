package webapp;


////ENUM FOR TOKENS TO COMPARE IF THE CURRENT ELEMENT IS THE SAME AS A SYMBOL


public enum EnumToken {

ASSIG('='),
MINUS('-'),
MULTIPLY('*'),
DIVIDE('/'),
PLUS('+'),
SEMICOLON(';'),
//  LE_OPERATOR(''<=''),
LT_OPERATOR('<'),
// GE_OPERATOR(''>=''),
GT_OPERATOR('>'),
// EQ_OPERATOR(''==''),
// NE_OPERATOR(''!=''),
MOD('%'),
REV_DIVIDE('\\'),
OPEN_BRACKET('('),
CLOSE_BRACKET(')'),
EXPONENT('^'),
COMMA(','),
SQUARE_OPEN_BRACKET('['),
SQUARE_CLOSE_BRACKET(']'),
CURLY_BRACKET_OPEN('{'),
CURLY_BRACKET_CLOSE('}');

private char c;

EnumToken(char c) {
    this.c = c;
}

public char getChar() {
    return c;
}


/*
private String s;

EnumToken(String s) {
    this.s = s;
}

public String getString() {
    return s;
}

*/


}