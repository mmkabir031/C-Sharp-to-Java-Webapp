<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="webapp.Token" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset= UTF-8">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
<script
  src="https://code.jquery.com/jquery-3.5.1.min.js"
  integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
  crossorigin="anonymous"></script>
  
<title>C# to Java</title>
<style type="text/css" media="screen">
    #codeEditor { 
        top: 0;
        height: 500px;
        width:50%;
    }
    #translation { 
        top: 0;
        height: 500px;
        width:50%;
    }
    .float-container {
    border: 3px solid #fff;
    padding: 20px;
	}
	
	.float-child {
	    width: 50%;
	    float: left;
	    padding: 20px;
	} 
</style>
<script type="text/javascript">
	//var initial = 'namespace Loops\n\t{\n\t\tinternal class Program\n{\npublic static void Main(string[] args)\n{\nint test1 = 1;\nwhile (test1 <= 4)\n{\nConsole.WriteLine("Test 1 Passed (prints 4 times)");\ntest1++;\n}\n\n\n\nint test3 = 21;\ndo\n{\nConsole.WriteLine("If this line prints once, Test 3 Passed");\ntest3++;\n} while (test3 >= 20);\nConsole.WriteLine("Test 4 Passed if prints once");\n}\n}\n}';
	var initial = 'namespace Loops\n{\n\tinternal class Program\n\t{\n\t\tpublic static void Main(string[] args)\n\t\t{\n\t\tint test1;\n\t\ttest1 = 2;\n\t\tstring test4 = "something here";\n\t\twhile (test1 <= 6)\n\t\t{\n\t\t\tConsole.WriteLine(test1);\n\t\t\ttest1++;\n\t\t}\n\t\tif (test1 <= 10){\n\t\tConsole.WriteLine("If statement passed");\n\t\t}\n\t}\n\t}\n}';
</script>
</head>
<body>
	<div>
		<div>
			<label for="samples">Sample Programs</label>
			<select name="samples" id="samples" onchange="populateCode()">
			  <option value="1">Sample 1</option>
			  <option value="2">Sample 2</option>
			  <option value="3">Sample 3</option>
			  <option value="4">Sample 4</option>
			  <option value="5">Sample 5</option>
			</select>
		</div>
		<div>
			<input type="file" id="fileSelect" name="fileSelect" accept=".cs,.txt">
		</div>
		<form class="form-group" action="/login.do" method="post">
			<div class="float-container">
				<span>
					<div class="float-child" id="codeEditor" class="offset-1" ></div>
					<textarea  id="codeSubmit" class="col-10 form-control offset-1" name="name" rows="10" style="display:none"></textarea>
					<div class="float-child" id="translation" class="offset-1" ></div>
				</span>
			</div>
			<div>
				<input style="width:20%; margin-left:250px" class=" btn btn-secondary btn-lg btn-block offset-1" type="submit" value="Translate">
			</div>
			
		</form>
	</div>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.4.12/ace.js" type="text/javascript" charset="utf-8"></script>
	<script>
		var editor1 = ace.edit("codeEditor");
	    editor1.setTheme("ace/theme/monokai");
	    //editor.session.setMode("ace/mode/javascript");
	    editor1.session.setValue(initial);
	    var textarea = document.getElementById("codeSubmit");
	    var fileSelect = document.getElementById("fileSelect");
	    
	    
	    var editor2 = ace.edit("translation");
	    editor2.setTheme("ace/theme/monokai");
	    textarea.value = editor1.getSession().getValue();

	    editor1.getSession().on("change", function () {
	    	//debugger;
	    	textarea.value = editor1.getSession().getValue();
	    });
	    
	    fileSelect.addEventListener('change', function () {
	    	const file = this.files[0];
	    	var reader = new FileReader();
	    	reader.readAsText(file);
	    	reader.onload = function() {
	    		editor1.getSession().setValue(reader.result);
	    	}
	    });
	    
	    function populateCode() {
	    	//debugger;
	    	var sample1 = 'namespace Loops\n{\n\tinternal class Program\n\t{\n\t\tpublic static void Main(string[] args)\n\t\t{\n\t\t\tint myVar;\n\t\t\tmyVar = 2;\n\n\t\t\tString pass = "Test passed";\n\n\t\t\tif (myVar <= 10) {\n\t\t\t\tConsole.WriteLine(pass);\n\t\t\t}\n\t\t}\n\t}\n}';
	    	var sample2 = 'namespace Loops\n{\n\tinternal class Program\n\t{\n\t\tpublic static void Main(string[] args)\n\t\t{\n\t\t\tint myVar;\n\t\t\tmyVar = 2;\n\n\t\t\tString pass = "Test passed";\n\n\t\t\tif (myVar <= 10) {\n\t\t\t\tConsole.WriteLine(pass);\n\t\t\t}\n\n\t\t\tif (myVar > 2) {\n\t\t\t\tConsole.WriteLine("Will not print");\n\t\t\t}\n\t\t}\n\t}\n}';
	    	var sample3 = 'namespace Loops\n{\n\tinternal class Program\n\t{\n\t\tpublic static void Main(string[] args)\n\t\t{\n\n\t\t\tint zeroIndex = 0;\n\n\t\t\twhile (zeroIndex <= 10)\n\t\t\t{\n\t\t\t\tConsole.WriteLine(zeroIndex);\n\t\t\t\tzeroIndex++;\n\t\t\t}\n\t\t\tif(zeroIndex > 5){\n\t\t\t\tConsole.WriteLine("Test passed");\n\t\t\t}\n\t\t}\n\t}\n}';
	    	var sample4 = 'namespace Loops\n{\n\tinternal class Program\n\t{\n\t\tpublic static void Main(string[] args)\n\t\t{\n\n\t\t\tint zeroIndex = 0;\n\n\t\t\tfor(int i = 0; i < 4; i++) {\n\t\t\t\tConsole.WriteLine("Print 4 times");\n\t\t\t}\n\t\t}\n\t}\n}';
	    	var sample5 = 'namespace Loops\n{\n\tinternal class Program\n\t{\n\t\tpublic static void Main(string[] args)\n\t\t{\n\n\t\t\tint zeroIndex = 0;\n\n\t\t\twhile (zeroIndex <= 10)\n\t\t\t{\n\t\t\t\tConsole.WriteLine(zeroIndex);\n\t\t\t\tzeroIndex++;\n\t\t\t}\n\t\t\tif(zeroIndex > 5){\n\t\t\t\tConsole.WriteLine("Test passed");\n\t\t\t}\n\t\t\tfor(int j = 0; j < 1; j++){\n\t\t\t\tConsole.WriteLine("For loop execution");\n\t\t\t}\n\t\t}\n\t}\n}';
	    	
	    	var sampleSelect = document.getElementById("samples");
	    	var selection = sampleSelect.value;
	    	
	    	
	    	if (selection === "1") {
	    		editor1.session.setValue(sample1);
	    	} else if (selection === "2") {
	    		editor1.session.setValue(sample2);
	    	} else if (selection === "3") {
	    		editor1.session.setValue(sample3);
	    	} else if (selection === "4") {
	    		editor1.session.setValue(sample4);
	    	} else if (selection === "5") {
	    		editor1.session.setValue(sample5);
	    	}
	    	//alert(selection);
	    }
	</script>
	<div class="offset-1 col-10"  style='background-color:#F1F1F1;'>
	<%! public String value = "", code = "";%>
	<%ArrayList translation = (ArrayList)request.getAttribute("name");
	if(translation != null) {
		int count = 1;
		int column = 0;
		
		//value += "<label for='output'>Java Translation</label><div>";
		//out.println("<label for='output'>Java Translation</label>");
		//out.println("<div>");
		for (int i = 0; i < translation.size(); i++) { 
			
			Token token = (Token)translation.get(i);
			if (token.getRowNumber() == count) {
				//out.println(token.getLexeme());
				value += token.getLexeme() + " ";
				column++;
			}
			else {
				value += token.getLexeme() + " ";
				//out.println("</div>");
				//out.println("<div>");
				//out.println(token.getLexeme());
				count++;
				column++;
			}%>
			<script type="text/javascript">
			editor2.getSession().insert({
				row: <%=count%>
			},  "<%=value%>" + "\n");
		
		</script>
		
	<%value = "";
	column = 0;}%><%}%>
	
	<%code = (String)request.getAttribute("code");%>
	
	<script type="text/javascript">
	//debugger;
	var userCode = "<%=code%>";
	
	if (userCode != "null") {
		userCode = userCode.replace(/{{n}}/g, "\r\n");
		userCode = userCode.replace(/{{quote}}/g, "\"");
		editor1.session.setValue(userCode);
	} else {
		editor1.session.setValue(initial);
	}
	
	</script>
	
	
	
	<%ArrayList output = (ArrayList)request.getAttribute("output");
	if(output != null) {
		int count = 0;
		out.println("<label for='output'>Java Output</label>");
		for (int i = 0; i < output.size(); i++) {
			out.println("<div>");
			out.println(output.get(i));
			out.println("</div>");}}%>
			
	</div>
</body>
</html>