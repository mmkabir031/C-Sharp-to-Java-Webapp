package webapp;

public class Token
{

	private String lex;
	private String tokenType;

	public int rowNum;
	public int columnNum;
	
	public Token()
	{
		this.lex = "";
		this.tokenType = "IGNORE";
		this.rowNum = -1;
		this.columnNum = -1;
	}
	
	public Token(String lex, String tokenType, int rowNum)
	{
		this.lex = lex;
		this.tokenType = tokenType;
		this.rowNum = rowNum;
		this.columnNum = -1;
	}
	
	public Token(String lex, String tokenType, int rowNum, int columnNum)
	{
		this.lex = lex;
		this.tokenType = tokenType;
		this.rowNum = rowNum;
		this.columnNum = columnNum;
	}

	public String getLexeme()
	{
		return lex;
	}

	public String getTokenType()
	{
		return tokenType;
	}

	public int getRowNumber()
	{
		return rowNum;
	}

	public int getColumnNumber()
	{
		return columnNum;
	}
}