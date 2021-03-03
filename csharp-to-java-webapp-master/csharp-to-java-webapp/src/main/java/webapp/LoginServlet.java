package webapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = "/login.do")
public class LoginServlet extends HttpServlet {
	public static ArrayList<Token> tokens = new ArrayList<Token>();
	boolean showTokens =true;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String code = request.getParameter("name");
		//String code = "Test 1234";
		//String initialCode = "Test 1234";
		ArrayList<String> output = new ArrayList<String>();
		
		if(code != "") {
			CustomParser customParser = new CustomParser(code);
			tokens = customParser.getAllTokens(customParser);
			
			Iterator<Token> itr = tokens.iterator();
			while (itr.hasNext()) {
				Token next = itr.next();
				if (next.getRowNumber() == -1) {
					itr.remove();
				}
			}

			tokens = Translation.TranslateTokensToJava(tokens);
			
			request.setAttribute("name", tokens);
			request.setAttribute("initialCode", code);
			
			try {
				output = Executer.Execute(tokens);
				request.setAttribute("output", output);
			} catch (Exception e) {
				e.printStackTrace();
				output.clear();
				output.add(e.getMessage());
				request.setAttribute("output", output);
			}
			
			code = code.replace("\r\n", "{{n}}");
			code = code.replace("\"", "{{quote}}");
			request.setAttribute("code", code);
		}
		
		
		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
	}
}