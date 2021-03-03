package webapp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Executer {
	static List<Variable> variables;
	static ArrayList<String> output = new ArrayList<String>();
	
	public static ArrayList<String> Execute(ArrayList<Token> tokens) throws Exception {
		ArrayList<Token> namespaceTokens = checkNamespaceStructure(tokens);
		ArrayList<Token> classTokens = null;
		ArrayList<Token> mainMethodTokens = null;
		output.clear();
		
		variables = new ArrayList<Variable>();
		
		
		if (namespaceTokens != null) {
			classTokens = checkClassStructure(namespaceTokens);
			
			mainMethodTokens = checkFunctionStructure(classTokens);
			
			while(!mainMethodTokens.isEmpty()) {
				mainMethodTokens = mainExecution(mainMethodTokens);
				if(mainMethodTokens == null) {
					throwException("Exception: Error occurred");
					break;
				}
			}
			
		}
		return output;
	}
	
	public static ArrayList<Token> checkNamespaceStructure(ArrayList<Token> tokens) throws Exception {
		int index = 0;
		boolean curlyStructure = false;
		ArrayList<Token> innerTokens = null;
		
		Token token = tokens.get(index);
		
		if (token.getLexeme().equals("namespace")) {
			token = tokens.get(index + 1);
			
			if (token.getTokenType().equals("IDENTIFIER")) {
				if(hasCurlyStructure(tokens)) {
					return new ArrayList<Token>(tokens.subList(3, tokens.size() - 2));
				}
				else {
//					output.clear();
//					output.add("exception" );
					throwException("Exception: Syntax error, expected curly brace at line # " + token.getRowNumber());
				}
			}
		}
		else {
			if(token.getLexeme().equals("using")) {
				throwException("Exception: Using statements are not currently supported");
			}
			else {
				throwException("Exception: No namespace was declared at line # " + token.getRowNumber());
			}
		}
		return null;
	}
	
	public static ArrayList<Token> checkClassStructure(ArrayList<Token> tokens) throws Exception{
		Token token = tokens.get(0);
		
		if (token.getTokenType().equals("KEYWORD")) {
			if (token.getLexeme().equals("protected") || token.getLexeme().equals("public") || token.getLexeme().equals("private")) {
				token = tokens.get(1);
				if(token.getLexeme().equals("class")) {
					token = tokens.get(2);
					if (token.getTokenType().equals("IDENTIFIER")) {
						return new ArrayList<Token>(tokens.subList(4, tokens.size() - 1));
					}
				}
				else {
					throwException("Exception: Class must be declared at line # " + token.getRowNumber());
				}
			}
			else {
				throwException("Exception: unexpected class modifier " + token.getLexeme() + " found at line # " + token.getRowNumber());
			}
		}
		else {
			throwException("Exception: unexpected token " + token.getLexeme() + " found at line # " + token.getRowNumber());
		}
		return null;
	}
	
	public static ArrayList<Token> checkFunctionStructure(ArrayList<Token> tokens) throws Exception{
		Token token = tokens.get(0);
		
		if (token.getLexeme().equals("protected") || token.getLexeme().equals("public") || token.getLexeme().equals("private")) {
			token = tokens.get(1);
			if(token.getLexeme().equals("static")) {
				token = tokens.get(2);
				if(token.getLexeme().equals("void")) {
					token = tokens.get(3);
					if(token.getLexeme().toLowerCase().equals("main")) {
						token = tokens.get(4);
						if(token.getLexeme().equals("(")) {
							if(checkFunctionArgs(tokens)) {
								return new ArrayList<Token>(tokens.subList(11, tokens.size() - 1));
							}
							else {
								throwException("Exception: unexpected token " + token.getLexeme() + " found at line # " + token.getRowNumber());
							}
						}
						else {
							throwException("Exception: Missing opening parentheses at line # " + token.getRowNumber());
						}
					}
					else {
						throwException("Exception: Main method return void at line # " + token.getRowNumber());
					}
				}
				else {
					throwException("Exception: Main method return void at line # " + token.getRowNumber());
				}
			}
			else {
				throwException("Exception: Main method must be static at line # " + token.getRowNumber());
			}
		}
		else {
			throwException("Exception: unexpected class modifier " + token.getLexeme() + " found at line # " + token.getRowNumber());
		}
		return null;
	}
	
	public static boolean checkFunctionArgs(ArrayList<Token> tokens) throws Exception{
		Token token = tokens.get(5);
		
		if((token.getLexeme().equals("int"))||(token.getLexeme().equals("string"))||(token.getLexeme().equals("bool"))||(token.getLexeme().equals("double"))){
			token = tokens.get(8);
			if (token.getTokenType().equals("IDENTIFIER")) {
				token = tokens.get(9);
				if (token.getTokenType().equals("RIGHT_PAREN")) {
					return true;
				}
				else {
					throwException("Exception: Variable (" + token.getLexeme() + ") must have a declared name at line # " + token.getRowNumber());
				}
			}
			else {
				throwException("Exception: Variable (" + token.getLexeme() + ") must have a declared name at line # " + token.getRowNumber());
			}
		}
		else {
			throwException("Exception: Variable type (" + token.getLexeme() + ") not supported currently at line # " + token.getRowNumber());
		}
		
		return false;
	}
	
	public static ArrayList<Token> mainExecution(ArrayList<Token> tokens) throws Exception {
		
		Token token = tokens.get(0);
		if((((token.getLexeme().equals("int"))||(token.getLexeme().equals("string"))||(token.getLexeme().equals("bool")) ||(token.getLexeme().equals("double"))) && (!(tokens.get(2).getTokenType().equals("ASSIGN_OP"))) && tokens.get(2).getTokenType().equals("SEMICOLON"))){
			//Declaration
			if (tokens.get(1).getTokenType().equals("IDENTIFIER") && tokens.get(2).getTokenType().equals("SEMICOLON")) {
				switch(tokens.get(0).getLexeme().toLowerCase()) {
					case "int" :
						variables.add(new Variable("INTEGER_LITERAL", "-1", tokens.get(1).getLexeme()));
						return new ArrayList<Token>(tokens.subList(3, tokens.size()));
	
					case "string" :
						variables.add(new Variable("STRING_LITERAL", "-1", tokens.get(1).getLexeme()));
						return new ArrayList<Token>(tokens.subList(3, tokens.size()));
	
					case "bool" :
					 variables.add(new Variable("BOOLEAN", "-1", tokens.get(1).getLexeme()));
					 return new ArrayList<Token>(tokens.subList(3, tokens.size()));
	
					 case "double" :
						 variables.add(new Variable("BOOLEAN", "-1", tokens.get(1).getLexeme()));
						 return new ArrayList<Token>(tokens.subList(3, tokens.size()));
	
					default : 
					   throwException("Exception: Declaration statement invalid syntax at line # " + token.getRowNumber());
					}
				}
			}
			//Assignment
			else if(tokens.get(1).getLexeme().equals("=")&&(tokens.get(0).getTokenType().equals("IDENTIFIER"))) {//checks if it is the assignment statement = and the name
				String varName = tokens.get(0).getLexeme();
				boolean varExists = false;
				int index = 0;
				
				
				for (Variable var : variables) {
					if (var.name.equals(varName)) {
						varExists = true;
						variables.remove(index);
						var.value = tokens.get(2).getLexeme();
						variables.add(var);
					}
					index++;
				}
				return new ArrayList<Token>(tokens.subList(4, tokens.size()));
				
			}
			//Declaration/assignment
			else if(tokens.get(2).getLexeme().equals("=")&&(tokens.get(1).getTokenType().equals("IDENTIFIER"))) {//checks if it is the assignment statement = and the name
			
				switch(token.getLexeme().toLowerCase()) {
				   case "int" :
				            if(tokens.get(3).getTokenType().equals("INTEGER_LITERAL")){
				            	variables.add(new Variable("INTEGER_LITERAL", tokens.get(3).getLexeme(), tokens.get(1).getLexeme()));
				            	return new ArrayList<Token>(tokens.subList(5, tokens.size()));
				            }
				            else {
				            	throwException("Exception: Invalid string found at line # " + token.getRowNumber());
				            }
				      break; 
				   
				   case "string" :
				      if(tokens.get(3).getTokenType().equals("STRING_LITERAL")){
				    	  variables.add(new Variable("STRING_LITERAL", tokens.get(3).getLexeme(), tokens.get(1).getLexeme()));
				    	  return new ArrayList<Token>(tokens.subList(5, tokens.size()));
				      }
				      break; 
				      
	
			       case "bool" :
			    	  if(tokens.get(3).getLexeme().toLowerCase().equals("true") || tokens.get(3).getLexeme().toLowerCase().equals("false")){
			            	variables.add(new Variable("BOOLEAN", tokens.get(3).getLexeme(), tokens.get(1).getLexeme()));
			            	return new ArrayList<Token>(tokens.subList(5, tokens.size()));
			            }
			            else {
			            	throwException("Exception: Invalid boolean found at line # " + token.getRowNumber());
			            }
			         break; // optional

			         case "double" :
			        	 if(tokens.get(3).getTokenType().equals("REAL_CONSTANT")){
				            	variables.add(new Variable("DOUBLE", tokens.get(3).getLexeme(), tokens.get(1).getLexeme()));
				            	return new ArrayList<Token>(tokens.subList(5, tokens.size()));
				            }
				            else {
				            	throwException("Exception: Invalid double found at line # " + token.getRowNumber());
				            }
			            break; // optional
			  
				   default : 
					   throwException("Exception: Assignment statement invalid syntax at line # " + token.getRowNumber());
					}
				}
		else if (token.getLexeme().equals("while")||token.getLexeme().equals("for")||(token.getLexeme().equals("System.out.println"))||(token.getLexeme().equals("if"))) {
			switch(token.getLexeme().toLowerCase()) {
			   case "while":
				   		//Need more conditional checks here for var1 || var2 && var3
				   		int start = 0, stop = 0;
				   		for (int i = 1; i < tokens.size() - 1; i++) {
				   			if(tokens.get(i).getTokenType().equals("LEFT_PAREN")) {
				   				start = i;
				   			}
				   			else if(tokens.get(i).getTokenType().equals("RIGHT_PAREN")) {
				   				stop = i;
				   				break;
				   			}
				   		}

					   if (checkConditionalStatement(new ArrayList<Token>(tokens.subList(start + 1, stop)))) {
						   ArrayList<Token> condition = new ArrayList<Token>(tokens.subList(start + 1, stop));
		            		if (tokens.get(stop + 1).getTokenType().equals("LEFT_CURLY")) {
		            			start = stop;
		            			for (int i = start; i < tokens.size(); i++) {
						   			if(tokens.get(i).getTokenType().equals("RIGHT_CURLY")) {
						   				stop = i;
						   				break;
						   			}
						   		}
		            			ArrayList<Token> tempWhileTokens = new ArrayList<Token>(tokens.subList(start + 2, stop));
		            			ArrayList<Token> remainingTokens = new ArrayList<Token>(tokens.subList(stop + 1, tokens.size()));
		            			int size = tempWhileTokens.size();
		            			
		            			executeWhileLoop(tempWhileTokens, condition);
		            			
		            			return remainingTokens;
		            			
//		            			if (tempWhileTokens.size() == size) {
//		            				tempWhileTokens.clear();
//		        					return tempWhileTokens;
//		        				}
//		        				else {
//		        					return new ArrayList<Token>(tempWhileTokens.subList(5, tempWhileTokens.size()));
//		        				}
		            			
		            			//tempWhileTokens = new ArrayList<Token>(tempWhileTokens.subList(6, tempWhileTokens.size() - 1));
		            			//Begin Execution of while loop here
		            			
		            		}
		            		else {
		            			throwException("Exception: Invalid while statement syntax found at line # " + token.getRowNumber());
		            		}
		            	}
					   else {
						   throwException("Exception: Invalid conditional statement syntax found at line # " + token.getRowNumber());
					   }
					   break;
		     
		      case "System.out.println" :
		            if(tokens.get(1).getTokenType().equals("OPEN_BRACKET")&&(tokens.get(2).getTokenType().equals("\""))&&(tokens.get(3).getTokenType().equals("STRING_LITERAL"))&&(tokens.get(4).getTokenType().equals("OPEN_BRACKET")&&(tokens.get(4).getTokenType().equals("OPEN_BRACKET")))){
		            	//printConsole(tokens.get(0));
		            }
		            else {
		            	throwException("Exception: Invalid Console.writeline found at line # " + token.getRowNumber());
		            }
		      case "if" :
		    		//Need more conditional checks here for var1 || var2 && var3
		    		start = 0;
		    		stop = 0;
		    		for (int i = 1; i < tokens.size() - 1; i++) {
		    			if(tokens.get(i).getTokenType().equals("LEFT_PAREN")) {
		    				start = i;
		    			}
		    			else if(tokens.get(i).getTokenType().equals("RIGHT_PAREN")) {
		    				stop = i;
		    				break;
		    			}
		    		}
		    	   if (checkConditionalStatement(new ArrayList<Token>(tokens.subList(start + 1, stop)))) {
		    		   ArrayList<Token> IFcondition = new ArrayList<Token>(tokens.subList(start + 1, stop));
		    		   if (tokens.get(stop + 1).getTokenType().equals("LEFT_CURLY")) {
		    				start = stop + 1;
		    				for (int i = start; i <= tokens.size() - 1; i++) {
		    					if(tokens.get(i).getTokenType().equals("RIGHT_CURLY")) {
		    						stop = i;
		    						break;
		    					}
		    				}
		    				ArrayList<Token> tempIfTokens = new ArrayList<Token>(tokens.subList(start + 1, stop));
		    				ArrayList<Token> remainingTokens = new ArrayList<Token>(tokens.subList(stop + 1, tokens.size()));
		    				
		    				executeIfLoop(tempIfTokens, IFcondition);
		    				
		    				return remainingTokens;
//		    				if (tempIfTokens.size() == 5) {
//		    					tempIfTokens.clear();
//	        					return tempIfTokens;
//	        				}
//	        				else {
//	        					return new ArrayList<Token>(tempIfTokens.subList(5, tempIfTokens.size()));
//	        				}
		    				
		    				//tempIfTokens = new ArrayList<Token>(tempIfTokens.subList(6, tempIfTokens.size() - 1));
		    				//Begin Execution of while loop here
		    				
		    				
		    			}
		    			else {
		    				throwException("Exception: Invalid if statement syntax found at line # " + token.getRowNumber());
		    			}
		    		}
		    	   else {
		    		   throwException("Exception: Invalid conditional statement syntax found at line # " + token.getRowNumber());
		    	   }
		      case "for" :
		    	  start = 0;
		    		stop = 0;
		    		for (int i = 1; i < tokens.size() - 1; i++) {
		    			if(tokens.get(i).getTokenType().equals("LEFT_PAREN")) {
		    				start = i;
		    			}
		    			else if(tokens.get(i).getTokenType().equals("RIGHT_PAREN")) {
		    				stop = i;
		    				break;
		    			}
		    		}
		    		if(tokens.get(1).getTokenType().equals("LEFT_PAREN")&&((tokens.get(13).getTokenType().equals("RIGHT_PAREN")))){
		    				//first part for for loop
	    				if(tokens.get(2).getLexeme().toLowerCase().equals("int")&&((tokens.get(3).getTokenType().equals("IDENTIFIER")))&&(tokens.get(4).getLexeme().equals("="))&&((tokens.get(5).getTokenType().equals("INTEGER_LITERAL"))&&((tokens.get(6).getTokenType().equals("SEMICOLON"))))) {
	    				Variable newVar = new Variable();
	    				newVar.name =tokens.get(3).getLexeme();
	    				newVar.type =tokens.get(2).getLexeme();
	    				newVar.value = tokens.get(5).getLexeme();
	    					variables.add(newVar);
	    					//second part conditional 
	    					if(((tokens.get(7).getTokenType().equals("IDENTIFIER")))&&(tokens.get(9).getTokenType().equals("INTEGER_LITERAL"))&&((tokens.get(10).getTokenType().equals("SEMICOLON")))) { //ADD OPERATOR
	    						//check third condition
	    					
						 
	    					//	if(tokens.get(8).getLexeme() == "<" || tokens.get(8).getLexeme() == ">" || tokens.get(8).getLexeme() == "<=" || tokens.get(8).getLexeme() == ">=") {
	    						if(tokens.get(8).getLexeme().equals("<")|| tokens.get(8).getLexeme().equals(">") ||  tokens.get(8).getLexeme().equals("<=") ||tokens.get(8).getLexeme().equals(">=")) {
	    					
	    					//third part conditional
	    					if(((tokens.get(11).getTokenType().equals("IDENTIFIER")))){
	    						switch((tokens.get(12)).getTokenType()) {
	    						case "INC_OP":
	    							ArrayList<Token> Forcondition = new ArrayList<Token>(tokens.subList(start + 1, stop));
	    				    		   if (tokens.get(stop + 1).getTokenType().equals("LEFT_CURLY")) {
	    				    				start = stop + 1;
	    				    				for (int i = start; i <= tokens.size() - 1; i++) {
	    				    					if(tokens.get(i).getTokenType().equals("RIGHT_CURLY")) {
	    				    						stop = i;
	    				    						break;
	    				    					}
	    				    				}
	    				    				ArrayList<Token> tempForTokens = new ArrayList<Token>(tokens.subList(start + 1, stop));
	    				    				ArrayList<Token> remainingTokens = new ArrayList<Token>(tokens.subList(stop + 1, tokens.size()));
	    				    				
	    				    				executeForLoopINC(tempForTokens, Forcondition);
	    				    				
	    				    				return remainingTokens;
	    						
	    				    		   
	    				    		   }
	    				
		    		
			
		
	    							break;
	    							
	    						case "DEC_OP":
	    							ArrayList<Token> Forcondition2 = new ArrayList<Token>(tokens.subList(start + 1, stop));
	    				    		   if (tokens.get(stop + 1).getTokenType().equals("LEFT_CURLY")) {
	    				    				start = stop + 1;
	    				    				for (int i = start; i <= tokens.size() - 1; i++) {
	    				    					if(tokens.get(i).getTokenType().equals("RIGHT_CURLY")) {
	    				    						stop = i;
	    				    						break;
	    				    					}
	    				    				}
	    				    				ArrayList<Token> tempForTokens = new ArrayList<Token>(tokens.subList(start + 1, stop));
	    				    				ArrayList<Token> remainingTokens = new ArrayList<Token>(tokens.subList(stop + 1, tokens.size()));
	    				    				
	    				    				executeForLoopDEC(tempForTokens, Forcondition2);
	    				    				
	    				    				return remainingTokens;
	    						
	    				    		   
	    				    		   }
	    							break;
	    							
	    						default: throwException("Exception: Invalid incremental/decremental value at line # " + token.getRowNumber());
		    				
    							}
	    					}
	    						}
	    					
    						
		    		else {
		    			throwException("Exception: Invalid for found at line # " + token.getRowNumber());
		    		}
		     
			//   default : 
			//	   throwException("Exception: Assignment statement invalid syntax at line # " + token.getRowNumber());
			//	}
		}else {
			throwException("Exception: Invalid for syntax at line # " + token.getRowNumber());
		}
	    				}else {
			    			throwException("Exception: Invalid for syntax at line # " + token.getRowNumber());
			    		}
		    		}
			}
			}
		else {
			throwException("An unexpected error occured");
		}
		
		return null;
	}
	
	
	public static void executeForLoopDEC(ArrayList<Token> forTokens, ArrayList<Token> Forcondition) throws Exception{
	//TODO: support !=
	switch (Forcondition.get(6).getLexeme()) {
		case ">":
			int start = 0;
			int end = 0;
			
			if (doesVarExist(Forcondition.get(1).getLexeme())) {
				if(doesVarHaveValue(Forcondition.get(0).getLexeme())) {
					start = Integer.parseInt(getVarValue(Forcondition.get(1).getLexeme()));
				}
				start = Integer.parseInt(Forcondition.get(3).getLexeme());
			}
			else {
				start = Integer.parseInt(Forcondition.get(3).getLexeme());
			}
			
			if (doesVarExist(Forcondition.get(7).getLexeme())) {
				if(doesVarHaveValue(Forcondition.get(7).getLexeme())) {
						end = Integer.parseInt(getVarValue(Forcondition.get(7).getLexeme()));	
					}
			}
			else {
				end = Integer.parseInt(Forcondition.get(7).getLexeme());
			}
			// temp1 = Integer.parseInt(getVarValue(Forcondition.get(0).getLexeme()));
			// temp2 = Integer.parseInt(getVarValue(Forcondition.get(2).getLexeme()));
			
			while(start > end) {
				if (forTokens.get(0).getLexeme().equals("System.out.println")) {
					printConsole(forTokens);
					
					Variable variable = new Variable();
					variable = getVar(Forcondition.get(1).getLexeme());
					variable.value = Integer.toString(decrementVar(Integer.parseInt(variable.value)));
					boolean varExists = false;
					int index = 0;
					Iterator<Variable> iterator = variables.iterator();
					Variable temp = new Variable();
					
					while(iterator.hasNext()) {
						Variable var = iterator.next();
						if (var.name.equals(variable.name)) {
							varExists = true;
							iterator.remove();
							temp.name = variable.name;
							temp.type = variable.type;
							temp.value = variable.value;
		//					var.value = variable.value;
		//					variables.add(var);
						}
					}
					
					variables.add(temp);
					start-=1;
					//whileTokens = new ArrayList<Token>(whileTokens.subList(5, whileTokens.size()));
				}
			}
				Variable variable = new Variable();
				variable = getVar(Forcondition.get(1).getLexeme());
				variable.value = Integer.toString(decrementVar(Integer.parseInt(variable.value)));
				boolean varExists = false;
				int index = 0;
				Iterator<Variable> iterator = variables.iterator();
				
			
				while(iterator.hasNext()) {
					Variable var = iterator.next();
					if (var.name.equals(variable.name)) {
						varExists = true;
						iterator.remove();
		//				var.value = variable.value;
		//				variables.add(var);
					}
				}
		
		//		else {
		//			throwException("Exception: Variable (" + Forcondition.get(0).getLexeme() + "has not been declared, at line # " + Forcondition.get(0).getRowNumber());
		//		}
			
		break;
		
		case ">=":
			 start = 0;
			 end = 0;
			
			if (doesVarExist(Forcondition.get(1).getLexeme())) {
				if(doesVarHaveValue(Forcondition.get(0).getLexeme())) {
					start = Integer.parseInt(getVarValue(Forcondition.get(1).getLexeme()));
				}
				start = Integer.parseInt(Forcondition.get(3).getLexeme());
			}
			else {
				start = Integer.parseInt(Forcondition.get(3).getLexeme());
			}
			
			if (doesVarExist(Forcondition.get(7).getLexeme())) {
				if(doesVarHaveValue(Forcondition.get(7).getLexeme())) {
						end = Integer.parseInt(getVarValue(Forcondition.get(7).getLexeme()));	
					}
			}
			else {
				end = Integer.parseInt(Forcondition.get(7).getLexeme());
			}
			// temp1 = Integer.parseInt(getVarValue(Forcondition.get(0).getLexeme()));
			// temp2 = Integer.parseInt(getVarValue(Forcondition.get(2).getLexeme()));
			
			while(start >= end) {
				if (forTokens.get(0).getLexeme().equals("System.out.println")) {
					printConsole(forTokens);
					
					Variable variable1 = new Variable();
					variable1 = getVar(Forcondition.get(1).getLexeme());
					variable1.value = Integer.toString(decrementVar(Integer.parseInt(variable1.value)));
					boolean varExists1 = false;
					 index = 0;
					Iterator<Variable> iterator1 = variables.iterator();
					Variable temp = new Variable();
					
					while(iterator1.hasNext()) {
						Variable var = iterator1.next();
						if (var.name.equals(variable1.name)) {
							varExists1 = true;
							iterator1.remove();
							temp.name = variable1.name;
							temp.type = variable1.type;
							temp.value = variable1.value;
		//					var.value = variable.value;
		//					variables.add(var);
						}
					}
					
					variables.add(temp);
					start-=1;
					//whileTokens = new ArrayList<Token>(whileTokens.subList(5, whileTokens.size()));
				}
			}
				Variable variable1 = new Variable();
				variable1 = getVar(Forcondition.get(1).getLexeme());
				variable1.value = Integer.toString(decrementVar(Integer.parseInt(variable1.value)));
				boolean varExists1 = false;
				int index1 = 0;
				Iterator<Variable> iterator1 = variables.iterator();
				
			
				while(iterator1.hasNext()) {
					Variable var = iterator1.next();
					if (var.name.equals(variable1.name)) {
						varExists1 = true;
						iterator1.remove();
		//				var.value = variable.value;
		//				variables.add(var);
					}
				}
		
		//		else {
		//			throwException("Exception: Variable (" + Forcondition.get(0).getLexeme() + "has not been declared, at line # " + Forcondition.get(0).getRowNumber());
		//		}
			
		break;
/*					
			break;
		case "==":
			while(Integer.parseInt(Forcondition.get(0).getLexeme()) == Integer.parseInt(Forcondition.get(2).getLexeme())) {
				
			}
			break;
*/				
	}
	//return whileTokens;
}
	
			
			public static void executeForLoopINC(ArrayList<Token> forTokens, ArrayList<Token> Forcondition) throws Exception{
				//TODO: support !=
				switch (Forcondition.get(6).getLexeme()) {
					case "<":
						int start = 0;
						int end = 0;
						
						if (doesVarExist(Forcondition.get(1).getLexeme())) {
							if(doesVarHaveValue(Forcondition.get(0).getLexeme())) {
								start = Integer.parseInt(getVarValue(Forcondition.get(1).getLexeme()));
							}
						}
						else {
							start = Integer.parseInt(Forcondition.get(3).getLexeme());
						}
						
						if (doesVarExist(Forcondition.get(7).getLexeme())) {
							if(doesVarHaveValue(Forcondition.get(7).getLexeme())) {
									end = Integer.parseInt(getVarValue(Forcondition.get(7).getLexeme()));	
								}
						}
						else {
							end = Integer.parseInt(Forcondition.get(7).getLexeme());
						}
						int temp1 = Integer.parseInt(getVarValue(Forcondition.get(0).getLexeme()));
						int temp2 = Integer.parseInt(getVarValue(Forcondition.get(2).getLexeme()));
						
						while(start < end) {
		        			if (forTokens.get(0).getLexeme().equals("System.out.println")) {
		        				printConsole(forTokens);
		        				
		        				Variable variable = new Variable();
								variable = getVar(Forcondition.get(1).getLexeme());
		        				variable.value = Integer.toString(incrementVar(Integer.parseInt(variable.value)));
	    						boolean varExists = false;
	    						int index = 0;
		        				Iterator<Variable> iterator = variables.iterator();
	    						Variable temp = new Variable();
	    						
	    						while(iterator.hasNext()) {
	    							Variable var = iterator.next();
	    							if (var.name.equals(variable.name)) {
	    								varExists = true;
	    								iterator.remove();
	    								temp.name = variable.name;
	    								temp.type = variable.type;
	    								temp.value = variable.value;
//	    								var.value = variable.value;
//	    								variables.add(var);
	    							}
	    						}
	    						
	    						variables.add(temp);
		        				start+=1;
		        				//whileTokens = new ArrayList<Token>(whileTokens.subList(5, whileTokens.size()));
			    			}
		        			
		        			Variable variable = new Variable();
							variable = getVar(Forcondition.get(1).getLexeme());
	        				variable.value = Integer.toString(incrementVar(Integer.parseInt(variable.value)));
    						boolean varExists = false;
    						int index = 0;
	        				Iterator<Variable> iterator = variables.iterator();
    						
    						
    						while(iterator.hasNext()) {
    							Variable var = iterator.next();
    							if (var.name.equals(variable.name)) {
    								varExists = true;
    								iterator.remove();
//    								var.value = variable.value;
//    								variables.add(var);
    							}
    						}
 
			    	//		else {
			    	//			throwException("Exception: Variable (" + Forcondition.get(0).getLexeme() + "has not been declared, at line # " + Forcondition.get(0).getRowNumber());
			    	//		}
						}
						
						break;
/*					 
*/					case "<=":
							 start = 0;
							 end = 0;
							
							if (doesVarExist(Forcondition.get(1).getLexeme())) {
								if(doesVarHaveValue(Forcondition.get(0).getLexeme())) {
									start = Integer.parseInt(getVarValue(Forcondition.get(1).getLexeme()));
								}
							}
							else {
								start = Integer.parseInt(Forcondition.get(3).getLexeme());
							}
							
							if (doesVarExist(Forcondition.get(7).getLexeme())) {
								if(doesVarHaveValue(Forcondition.get(7).getLexeme())) {
										end = Integer.parseInt(getVarValue(Forcondition.get(7).getLexeme()));	
									}
							}
							else {
								end = Integer.parseInt(Forcondition.get(7).getLexeme());
							}
							 temp1 = Integer.parseInt(getVarValue(Forcondition.get(0).getLexeme()));
							 temp2 = Integer.parseInt(getVarValue(Forcondition.get(2).getLexeme()));
							
							while(start <= end) {
								if (forTokens.get(0).getLexeme().equals("System.out.println")) {
									printConsole(forTokens);
									
									Variable variable = new Variable();
									variable = getVar(Forcondition.get(1).getLexeme());
									variable.value = Integer.toString(incrementVar(Integer.parseInt(variable.value)));
									boolean varExists = false;
									int index = 0;
									Iterator<Variable> iterator = variables.iterator();
									Variable temp = new Variable();
									
									while(iterator.hasNext()) {
										Variable var = iterator.next();
										if (var.name.equals(variable.name)) {
											varExists = true;
											iterator.remove();
											temp.name = variable.name;
											temp.type = variable.type;
											temp.value = variable.value;
						//					var.value = variable.value;
						//					variables.add(var);
										}
									}
									
									variables.add(temp);
									start+=1;
									//whileTokens = new ArrayList<Token>(whileTokens.subList(5, whileTokens.size()));
								}
								
								Variable variable = new Variable();
								variable = getVar(Forcondition.get(1).getLexeme());
								variable.value = Integer.toString(incrementVar(Integer.parseInt(variable.value)));
								boolean varExists = false;
								int index = 0;
								Iterator<Variable> iterator = variables.iterator();
								
								
								while(iterator.hasNext()) {
									Variable var = iterator.next();
									if (var.name.equals(variable.name)) {
										varExists = true;
										iterator.remove();
						//				var.value = variable.value;
						//				variables.add(var);
									}
								}
						
						//		else {
						//			throwException("Exception: Variable (" + Forcondition.get(0).getLexeme() + "has not been declared, at line # " + Forcondition.get(0).getRowNumber());
						//		}
							}
						break;
					
				}
				//return whileTokens;
			}
	
	public static void executeWhileLoop(ArrayList<Token> whileTokens, ArrayList<Token> condition) throws Exception{
		//TODO: support !=
		switch (condition.get(1).getLexeme()) {
			case "<":
				while(Integer.parseInt(condition.get(0).getLexeme()) < Integer.parseInt(condition.get(2).getLexeme())) {
					
				}
				break;
			case ">":
				while(Integer.parseInt(condition.get(0).getLexeme()) > Integer.parseInt(condition.get(2).getLexeme())) {
					
				}
				break;
			case "<=":
				int temp1 = Integer.parseInt(getVarValue(condition.get(0).getLexeme()));
				int temp2 = Integer.parseInt(getVarValue(condition.get(2).getLexeme()));
				
				while(Integer.parseInt(getVarValue(condition.get(0).getLexeme())) <= Integer.parseInt(getVarValue(condition.get(2).getLexeme()))) {
        			if (whileTokens.get(0).getLexeme().equals("System.out.println")) {
        				printConsole(whileTokens);
        				//whileTokens = new ArrayList<Token>(whileTokens.subList(5, whileTokens.size()));
	    			}
	    			
        			if (doesVarExist(whileTokens.get(5).getLexeme())) {
	    				if(doesVarHaveValue(whileTokens.get(5).getLexeme())) {
	    					if(whileTokens.get(6).getTokenType().equals("INC_OP")) {
	    						Variable variable = getVar(whileTokens.get(5).getLexeme());
	    						variable.value = Integer.toString(incrementVar(Integer.parseInt(variable.value)));
	    						boolean varExists = false;
	    						int index = 0;
	    						
	    						
	    						Iterator<Variable> iterator = variables.iterator();
	    						Variable temp = new Variable();
	    						
	    						while(iterator.hasNext()) {
	    							Variable var = iterator.next();
	    							if (var.name.equals(variable.name)) {
	    								varExists = true;
	    								iterator.remove();
	    								temp.name = variable.name;
	    								temp.type = variable.type;
	    								temp.value = variable.value;
//	    								var.value = variable.value;
//	    								variables.add(var);
	    							}
	    						}
	    						
	    						variables.add(temp);
//	    						for (Variable var : variables) {
//	    							if (var.name.equals(variable.name)) {
//	    								varExists = true;
//	    								variables.remove(index);
//	    								var.value = variable.value;
//	    								variables.add(var);
//	    							}
//	    							index++;
//	    						}
	    					}
	    				}
	    				else {
	    					throwException("Exception: Variable (" + whileTokens.get(0).getLexeme() + " does not have a value, at line # " + condition.get(0).getRowNumber());
	    				}
	    			}
	    			else {
	    				throwException("Exception: Variable (" + condition.get(0).getLexeme() + "has not been declared, at line # " + condition.get(0).getRowNumber());
	    			}
				}
				break;
			case ">=":
				while(Integer.parseInt(condition.get(0).getLexeme()) >= Integer.parseInt(condition.get(2).getLexeme())) {
					
				}
				break;
			case "==":
				while(Integer.parseInt(condition.get(0).getLexeme()) == Integer.parseInt(condition.get(2).getLexeme())) {
					
				}
				break;
			
		}
		//return whileTokens;
	}
	
	public static int incrementVar(int value) {
		return (value + 1);
	}
	
	public static int decrementVar(int value) {
		return (value - 1);
	}
	
	public static void printConsole(ArrayList<Token> printTokens) throws Exception{
		if (printTokens.get(0).getLexeme().equals("System.out.println")) {
			if (printTokens.get(1).getTokenType().equals("LEFT_PAREN") && printTokens.get(3).getTokenType().equals("RIGHT_PAREN")) {
				if (printTokens.get(4).getTokenType().equals("SEMICOLON")) {
					if (!printTokens.get(2).getTokenType().equals("STRING_LITERAL")) {
						if(doesVarExist(printTokens.get(2).getLexeme())) {
							if(doesVarHaveValue(printTokens.get(2).getLexeme())) {
								Variable var = getVar(printTokens.get(2).getLexeme());
								output.add(var.value);
							}
							else {
								throwException("Exception: variable: " + printTokens.get(2).getLexeme() + " is undefined at line # " + printTokens.get(2).getRowNumber());
							}
						}
						else {
							throwException("Exception: variable: " + printTokens.get(2).getLexeme() + " has not been declared at line # " + printTokens.get(2).getRowNumber());
						}
					}
					else {
						output.add(printTokens.get(2).getLexeme());
					}
					//output.add(printTokens.get(2).getLexeme());
				}
				else {
					throwException("Exception: Missing semicolon at line # " + printTokens.get(4).getRowNumber());
				}
			}
			else {
				throwException("Exception: Missing parenthesis at line # " + printTokens.get(3).getRowNumber());
			}
		}
	}
	
	public static boolean checkConditionalStatement(ArrayList<Token> tokens) {
		boolean var1good = false;
		boolean var2good = false;
		boolean condition = false;
		
		if (tokens.size() == 3) {
			if (doesVarExist(tokens.get(0).getLexeme())) {
				if (doesVarHaveValue(tokens.get(0).getLexeme())) {
					var1good = true;
				}
			}
			if (doesVarExist(tokens.get(2).getLexeme())) {
				if (doesVarHaveValue(tokens.get(2).getLexeme())) {
					var2good = true;
				}
			}
			
			if (!tokens.get(0).getLexeme().equals("")) {
				var1good = true;
			}

			if (tokens.get(1).getTokenType().equals("LT_E_OPERATOR") 
					|| tokens.get(1).getTokenType().equals("GT_E_OPERATOR")
					|| tokens.get(1).getTokenType().equals("GT_OPERATOR")
					|| tokens.get(1).getTokenType().equals("LT_OPERATOR")
					|| tokens.get(1).getTokenType().equals("EQUAL_TO")){
				condition = true;
			}
			
			if (!tokens.get(2).getLexeme().equals("")) {
				var2good = true;
			}
			
			if (var1good && var2good && condition) {
//				if (tokens.get(0).getTokenType().equals(tokens.get(2).getTokenType())) {
//					return true;
//				}
				return true;
			}
		}
		
		return false;
	}
	
	public static void executeIfLoop(ArrayList<Token> ifTokens, ArrayList<Token> IFcondition) throws Exception{
		//TODO: support !=
		int left = 0;
		int right = 0;
		
		if (doesVarExist(IFcondition.get(0).getLexeme())) {
			if(doesVarHaveValue(IFcondition.get(0).getLexeme())) {
				left = Integer.parseInt(getVarValue(IFcondition.get(0).getLexeme()));
			}
		}
		else {
			left = Integer.parseInt(IFcondition.get(0).getLexeme());
		}
		
		if (doesVarExist(IFcondition.get(2).getLexeme())) {
			if(doesVarHaveValue(IFcondition.get(2).getLexeme())) {
					right = Integer.parseInt(getVarValue(IFcondition.get(2).getLexeme()));	
				}
		}
		else {
			right = Integer.parseInt(IFcondition.get(2).getLexeme());
		}
			
		switch (IFcondition.get(1).getLexeme()) {
			case "<":
				if(left < right) {
					if (ifTokens.get(0).getLexeme().equals("System.out.println")) {
						printConsole(ifTokens);
						//ifTokens = new ArrayList<Token>(ifTokens.subList(0, ifTokens.size()));
					}
					
				/*	if (doesVarExist(ifTokens.get(0).getLexeme())) {
						if(doesVarHaveValue(ifTokens.get(0).getLexeme())) {
							//TODO: check type
						}
						else {
							throwException("Exception: Variable (" + ifTokens.get(0).getLexeme() + "does not have a value, at line # " + IFcondition.get(0).getRowNumber());
						}
				}*/
				}
				break;
			case ">":
				if(left > right) {
					
						if (ifTokens.get(0).getLexeme().equals("System.out.println")) {
							printConsole(ifTokens);
							//ifTokens = new ArrayList<Token>(ifTokens.subList(6, ifTokens.size() - 1));
						}
						
					/*	if (doesVarExist(ifTokens.get(0).getLexeme())) {
							if(doesVarHaveValue(ifTokens.get(0).getLexeme())) {
								//TODO: check type
							}
							else {
								throwException("Exception: Variable (" + ifTokens.get(0).getLexeme() + "does not have a value, at line # " + IFcondition.get(0).getRowNumber());
							}
					}*/
					
				}
				break;
			case "<=":
				if(left <= right) {
					if (ifTokens.get(0).getLexeme().equals("System.out.println")) {
						printConsole(ifTokens);
						//ifTokens = new ArrayList<Token>(ifTokens.subList(6, ifTokens.size() - 1));
					}
					
				/*	if (doesVarExist(ifTokens.get(0).getLexeme())) {
						if(doesVarHaveValue(ifTokens.get(0).getLexeme())) {
							//TODO: check type
						}
						else {
							throwException("Exception: Variable (" + ifTokens.get(0).getLexeme() + "does not have a value, at line # " + IFcondition.get(0).getRowNumber());
						}
					}*/
					else {
						throwException("Exception: Variable (" + ifTokens.get(0).getLexeme() + "has not been declared, at line # " + IFcondition.get(0).getRowNumber());
					}
				}
				break;
			case ">=":
				if(left >= right) {
					
						if (ifTokens.get(0).getLexeme().equals("System.out.println")) {
							printConsole(ifTokens);
							ifTokens = new ArrayList<Token>(ifTokens.subList(6, ifTokens.size() - 1));
						}
						
					/*	if (doesVarExist(ifTokens.get(0).getLexeme())) {
							if(doesVarHaveValue(ifTokens.get(0).getLexeme())) {
								//TODO: check type
							}
							else {
								throwException("Exception: Variable (" + ifTokens.get(0).getLexeme() + "does not have a value, at line # " + IFcondition.get(0).getRowNumber());
							}
					}*/
					
				}
				break;
			case "==":
				// To do: Figure out why '==' always run true
				if(left == right) {
					
						if (ifTokens.get(0).getLexeme().equals("System.out.println")) {
							printConsole(ifTokens);
							ifTokens = new ArrayList<Token>(ifTokens.subList(6, ifTokens.size() - 1));
						}
						
				/*		if (doesVarExist(ifTokens.get(0).getLexeme())) {
							if(doesVarHaveValue(ifTokens.get(0).getLexeme())) {
								//TODO: check type
							}
							else {
								throwException("Exception: Variable (" + ifTokens.get(0).getLexeme() + "does not have a value, at line # " + IFcondition.get(0).getRowNumber());
							}
					}*/
					
				}
				break;
		}
	}
	public static Variable getVar(String name) {
		Variable variable = new Variable();
		
		for (Variable var : variables) {
			if (var.name.equals(name)) {
				variable = new Variable(var.type, var.value, var.name);
			}
		}
		return variable;
	}
	
	public static boolean doesVarExist(String name) {
		
		for (Variable var : variables) {
			if (var.name.equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean doesVarHaveValue(String name) {
		
		for (Variable var : variables) {
			if (var.name.equals(name)) {
				if (!var.value.equals("-1")) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static String getVarValue(String variable) throws Exception{
		try {
			if (!variable.matches("[0-9]+")) {
				for (Variable var : variables) {
					if (var.name.equals(variable)) {
						if (!var.value.equals("-1")) {
							return var.getValue();
						}
					}
				}
			}
			else {
				return variable;
			}
		}
		catch (Exception ex){
			throwException("Exception: Variable has no value");
		}
		return "-1";
	}
	
	public static boolean hasCurlyStructure(ArrayList<Token> tokens) {
		int openCount = 0;
		int closedCount = 0;
		
		for (Token token : tokens) {
			if (token.getTokenType().equals("LEFT_CURLY")) {
				openCount++;
			}
			else if (token.getTokenType().equals("RIGHT_CURLY")) {
				closedCount++;
			}
		}
		
		if (openCount == closedCount) {
			return true;
		}
		
		return false;
	}
	
	public static void throwException(String exception) throws Exception{
		throw new Exception(exception);
	}
	
}