import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.*;

//don't need to override every enter/exit method
public class LeverToJavaListener extends LeverBaseListener {
	LeverParser parser;
	
	private String fileName;
	private File targetFile;
	private BufferedWriter bw;

	private int indents;

	private HashSet<String> leverConstructs = new HashSet<String>();
	private HashSet<String> leverTerminals = new HashSet<String>();

	private static String userKey = "uSeR";

	private HashMap<String, VariableCheckingListener.LType> symbolTable;
	
	public LeverToJavaListener(LeverParser parser, String _fileName,
		HashMap<String, VariableCheckingListener.LType> _symbolTable) {

		this.parser = parser;
		this.symbolTable = _symbolTable;
		
		initConstructs();
		
		//String fileName = _fileName.split(".")[0];
		Path p = Paths.get(_fileName);
		fileName = p.getFileName().toString();
		int pos = fileName.lastIndexOf(".");
		if (pos > 0) fileName = fileName.substring(0, pos);

		//javaFile = Paths.get(fileName + ".java");
		
		String path = _fileName.substring(0, _fileName.lastIndexOf(File.separator));
		path = path + File.separator + fileName + ".java";
		
		try {
			targetFile = new File(path);
			if (!targetFile.exists()) {
				targetFile.createNewFile();
			}

			FileWriter fw = new FileWriter(targetFile);
			bw = new BufferedWriter(fw);
			System.out.println(path);
		} catch (IOException e) {
			System.out.println("error creating target file..");
			System.exit(1);
		}

		indents = 0;
	}
	private void initConstructs() {
		leverConstructs.add("for");
		leverConstructs.add("output");
		leverConstructs.add("get");


		leverTerminals.add("for");
		leverTerminals.add("each");
		leverTerminals.add("in");
		leverTerminals.add("(");
		leverTerminals.add(")");
		leverTerminals.add(",");
		leverTerminals.add("yes");
		leverTerminals.add("no");
		leverTerminals.add("var");
		leverTerminals.add("program");
		//leverTerminals.add("get");
		
	}
	private void openBraces() {
		indents++;
		printTarget("{\n");
	}
	private void closeBraces() {
		indents--;
		printTarget("\n");
		printTabs();
		printTarget("}\n");
	}
	private void printTabs() {
		int i = indents;
		while (i > 0) {
			printTarget("\t");
			i--;
		}
	}
	private void printTarget(String s) {
		try {
			bw.write(s);
		} catch (IOException e) {
			System.out.println("error writing to file..");
			System.exit(1);
		}
	}

	@Override public void enterLever(LeverParser.LeverContext ctx) {
		printTarget("import sun.jvm.hotspot.utilities.Interval;\n");
		printTarget("import twitter4j.*;\n");
		printTarget("import twitter4j.User;\n");
		printTarget("\n");
		printTarget("import java.util.*;\n\n");

		printTarget("public class " + fileName + " ");
		openBraces();
		
	}

	@Override
	public void enterMainProgram(LeverParser.MainProgramContext ctx) {
		printTarget("\tpublic static void main(String[] args) ");
	}

	@Override
	public void exitMainProgram(LeverParser.MainProgramContext ctx) {

	}
	@Override public void exitLever(LeverParser.LeverContext ctx) {
		closeBraces();
		//printTarget("exit lever");

		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void enterBlock(LeverParser.BlockContext ctx) {
	}
	@Override
	public void exitBlock(LeverParser.BlockContext ctx) {
	}
	@Override public void enterBlockStatement(LeverParser.BlockStatementContext ctx) {
		
		
		
	}

	@Override public void enterNonBlockStatement(LeverParser.NonBlockStatementContext ctx) {
		//printTarget("\n");
		printTabs();	
	}

	@Override
	public void enterStatement(LeverParser.StatementContext ctx) {
				
		//printTabs();

		//TokenStream tokens = parser.getTokenStream();
		//String i = "blah";
		//if (ctx.IF() != null) {
			//i = tokens.getText(ctx.IF());
			//i = ctx.IF().toString();
			//System.out.println("asdf " + i);
		//	printTarget("if ");
		//}
	}

	@Override public void exitStatement(LeverParser.StatementContext ctx) {
		//printTarget("\n");	
	}
	
	@Override
	public void enterForIn(LeverParser.ForInContext ctx) {
		//TokenStream tokens = parser.getTokenStream();

		TerminalNode begin = ctx.getToken(LeverLexer.NumberLiteral, 0);
		TerminalNode end = ctx.getToken(LeverLexer.NumberLiteral, 1);

		printTarget("for (int " + ctx.Identifier() + " = " + begin + "; ");
		printTarget(ctx.Identifier() + " < " + end + "; " + ctx.Identifier() + "++) ");
	}
	@Override
	public void exitForIn(LeverParser.ForInContext ctx) {
	}

	@Override
	public void enterForEach(LeverParser.ForEachContext ctx) {
		
		printTarget("for (String ");

		TerminalNode userTerminal = ctx.getToken(LeverLexer.AT, 0);
		if (userTerminal != null) {
			printTarget(userKey + " : ");
			printTarget(ctx.getToken(LeverLexer.Identifier, 0) + ".user) ");

		} else {
			printTarget(ctx.getToken(LeverLexer.Identifier, 0) + " : ");
			printTarget(ctx.getToken(LeverLexer.Identifier, 1) + ") ");
		}
	}

	@Override
	public void enterParExpression(LeverParser.ParExpressionContext ctx) {
		
		printTarget("(");
	}
	@Override
	public void exitParExpression(LeverParser.ParExpressionContext ctx) {
		printTarget(") ");
		
	}

	@Override
	public void enterExpressionList(LeverParser.ExpressionListContext ctx) {
		if (!leverTerminals.contains("dontPrintParams"))
			printTarget("(");

	}
	@Override
	public void exitExpressionList(LeverParser.ExpressionListContext ctx) {
		if (!leverTerminals.contains("dontPrintParams"))
			printTarget(")");

	}

	@Override
	public void enterStatementExpression(LeverParser.StatementExpressionContext ctx) {
			
	}
	@Override
	public void exitStatementExpression(LeverParser.StatementExpressionContext ctx) {
		//printTarget(";");
	}
	
	@Override
	public void enterLiteral(LeverParser.LiteralContext ctx) {
		//printTarget(ctx.StringLiteral().getText());
		if (ctx.BooleanLiteral() != null) {
			String lit = ctx.BooleanLiteral().toString();
			if (lit.equals("yes") || lit.equals("true")) {
				printTarget("true");
			} else if (lit.equals("no") || lit.equals("false")) {
				printTarget("false");
			}
		}
	}
	@Override
	public void enterPrimary(LeverParser.PrimaryContext ctx) {
		
	}

	@Override public void enterExpressionB(LeverParser.ExpressionBContext ctx) { 
		if (!leverTerminals.contains("dontPrintParams")) {
			printTarget(", ");
		}
			
	}

	@Override public void enterVariableDeclarator(LeverParser.VariableDeclaratorContext ctx) {
	}

	@Override public void exitVariableDeclarator(LeverParser.VariableDeclaratorContext ctx) {
		
	}
	@Override public void enterMethodDefinition(LeverParser.MethodDefinitionContext ctx) {


		String varId = ctx.Identifier().getText();
		VariableCheckingListener.LType type = symbolTable.get(varId);

		printTabs();
		printTarget("public static ");

		if (type != null) {
			printTarget(getJavaType(type) + " ");			
		}
		else {
			printTarget("void ");
		}

		//Check to see if there are any 
	}
	@Override public void enterFormalParameterList(LeverParser.FormalParameterListContext ctx) { 
		printTarget("(");
	}

	@Override public void exitFormalParameterList(LeverParser.FormalParameterListContext ctx) { 
		printTarget(")");
	}

	@Override public void enterFormalParameterA(LeverParser.FormalParameterAContext ctx) {
		VariableCheckingListener.LType _type = symbolTable.get(ctx.Identifier());
		printTarget(getJavaType(_type) + " ");	

		//printTarget("LeverVar " );
	}

	@Override public void enterFormalParameterB(LeverParser.FormalParameterBContext ctx) {
		VariableCheckingListener.LType _type = symbolTable.get(ctx.Identifier());
			printTarget(", " + getJavaType(_type) + " ");	

		//printTarget(", LeverVar " );
	}

	@Override public void enterIdentifierVar(LeverParser.IdentifierVarContext ctx) {
		printTabs();

		VariableCheckingListener.LType _type = symbolTable.get(ctx.Identifier().getText());
		if (_type != null)
			printTarget(getJavaType(_type) + " ");	
	}

	@Override public void exitIdentifierVar(LeverParser.IdentifierVarContext ctx) {

		//printTarget(" = new LeverVar();\n");
		
		
		
		
			
	}

	private String getJavaType(VariableCheckingListener.LType _type) {
		if (_type != null)
		switch (_type) {
			case LString:
				return "String";
			case LInteger:
				return "int";
			case LDouble:
				return "double";
			case LBoolean:
				return "boolean";
			case LList:
				return "ArrayList";
			case LDictionary:
				return "dictionary";


			case LUser:
				return "String";
			case LTopic:
				return "String";

			case LResult:
				return "Result";

			default:
				return "???";


		}
		else
			return null;

		}

	@Override public void enterMethodCall(LeverParser.MethodCallContext ctx) { 
		if (ctx.Identifier().getText().equals("get")) {
			leverTerminals.add("dontPrintParams");
			String text = ctx.getText();
			printTarget("QueryManager.getResultFromArguments(\"" + text.substring(text.indexOf("get")+3, text.length()) + "\")");
		}
	}

	@Override public void exitMethodCall(LeverParser.MethodCallContext ctx) { 
		if (ctx.Identifier().getText().equals("get")) {
			leverTerminals.remove("dontPrintParams");
		}
	}
	@Override public void enterInitialization(LeverParser.InitializationContext ctx) {

		LeverParser.VariableDeclaratorContext parent = (LeverParser.VariableDeclaratorContext)ctx.getParent();
		TerminalNode id = parent.identifierVar().Identifier();

		//Get type and assign it to the appropriate member of LeverVar

		//printTabs();
		//printTarget(id.getText() + ".val ");
	}

	@Override public void exitInitialization(LeverParser.InitializationContext ctx) {
		//printTarget(";\n");
	}

	@Override public void enterVariableInit(LeverParser.VariableInitContext ctx) {


	}

	@Override
	public void visitTerminal(TerminalNode node) {

		Token token = node.getSymbol();
		int type = token.getType();
		String id = token.getText();

		String tmp;
		switch(type) {
			case LeverLexer.Identifier:
				if (id.equals("output")) {
					printTarget("LeverAPI.output");
					
				} else if (id.equals("input")) {
					printTarget("LeverAPI.input()");
				
				
				} else if (id.equals("graph")) {
					printTarget("LeverAPI.graph");

				} else if (id.equals("get")) {

				} else {
					tmp = node.getParent().getChild(0).toString();
					if (leverConstructs.contains(tmp)  || (leverTerminals.contains("dontPrintParams"))) {

					} else {
						printTarget(id);

					}
				}
				
				break;

			case LeverLexer.SEMI:
				printTarget(";\n");
				break;

			case LeverLexer.NumberLiteral:
				tmp = node.getParent().getChild(0).toString();
				if (leverConstructs.contains(tmp)) { //numbers in 'for loop'
					break;
				}
				//printTarget(id);
				//break;

			case LeverLexer.StringLiteral:
				printTarget(id);
				break;

			case LeverLexer.AND:
				printTarget(" && ");
				break;
			case LeverLexer.OR:
				printTarget(" || ");
				break;
			case LeverLexer.IF:
				printTarget("if ");
				break;
			case LeverLexer.ELSE:
				printTarget(" else ");
				break;
			case LeverLexer.WHILE:
				printTarget("while ");
				break;

			case LeverLexer.LBRACE:
				openBraces();
				break;

			case LeverLexer.RBRACE:
				closeBraces();
				break;

			case LeverLexer.BANG:
				printTarget(id);
				break;
			case LeverLexer.BREAK:
				printTarget(id);
				break;

			case LeverLexer.AT:
				tmp = node.getParent().getChild(0).toString();
				if (leverConstructs.contains(tmp)) {

				} else {
					printTarget(userKey);
				}
				break;

			default:
				if (!(leverTerminals.contains(id) || (leverTerminals.contains("dontPrintParams")))) {
					printTarget(id + " ");
				}
			
		}
	}
}
