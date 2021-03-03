package webapp;

//ENUM FOR KEYWORDS TO COMPARE IF THE CURRENT ELEMENT IS THE SAME AS A KEYWORD


public enum EnumKeyword {
  FOR("for"),
  END("end"),
  IF("if"),
  WHILE("while"),
  ELSE("else"),
  PRINT("print"),
  PUBLIC("public"),
  FUNCTION("function"),
  USING("using"),
	SYSTEM("System"),
	NAMESPACE("namespace"),
	INTERNAL("internal"),
	CLASS("class"),
	STATIC("static"),
	VOID("void"),
	MAIN("Main"),
	STRING("string"),
	CONSTANT("constant"),
	BEGIN("begin"),
	ELSEIF("elseIf"),
	UNTIL("until"),
	RETURN("return"),
	INT("int"),
	DOUBLE("double"),
	DO("do"),
	GLOBAL("global");



  private   String c;

  EnumKeyword(String c) {
      this.c = c;
  }

  public String getString() {
      return c;
  }

}

