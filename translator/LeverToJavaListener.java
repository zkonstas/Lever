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

	public LeverToJavaListener(LeverParser parser, String _fileName) {
		this.parser = parser;

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


		leverTerminals.add(";");
		leverTerminals.add("true");
		leverTerminals.add("false");
		leverTerminals.add("for");
		leverTerminals.add("in");
		leverTerminals.add("(");
		leverTerminals.add(")");
		leverTerminals.add(",");
		leverTerminals.add("yes");
		leverTerminals.add("no");
		leverTerminals.add("var");
		
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

	@Override
	public void enterMainProgram(LeverParser.MainProgramContext ctx) {
		printTarget("public class " + fileName + " ");
		openBraces();
		printTarget("\tpublic static void main(String[] args) ");
	}

	@Override
	public void exitMainProgram(LeverParser.MainProgramContext ctx) {
		closeBraces();

		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void enterBlock(LeverParser.BlockContext ctx) {
		//openBraces();
	}
	@Override
	public void exitBlock(LeverParser.BlockContext ctx) {
		//closeBraces();
	}

	@Override
	public void enterStatement(LeverParser.StatementContext ctx) {
		printTabs();
		
		//TokenStream tokens = parser.getTokenStream();
		//String i = "blah";
		if (ctx.IF() != null) {
			//i = tokens.getText(ctx.IF());
			//i = ctx.IF().toString();
			//System.out.println("asdf " + i);
		//	printTarget("if ");
		}
		
		
		
	}

	@Override public void exitStatement(LeverParser.StatementContext ctx) {
		printTarget("\n");	
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
	public void enterForEach(LeverParser.ForEachContext ctx) {
		
		
	}



	@Override
	public void enterParExpression(LeverParser.ParExpressionContext ctx) {
		
		printTarget("(");
	}
	@Override
	public void exitParExpression(LeverParser.ParExpressionContext ctx) {
		printTarget(")");
		
	}

	@Override
	public void enterExpressionList(LeverParser.ExpressionListContext ctx) {
		printTarget("(");

	}
	@Override
	public void exitExpressionList(LeverParser.ExpressionListContext ctx) {
		printTarget(")");

	}

	@Override
	public void enterStatementExpression(LeverParser.StatementExpressionContext ctx) {
		
	}
	@Override
	public void exitStatementExpression(LeverParser.StatementExpressionContext ctx) {
		printTarget(";");
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
			//System.out.println(ctx.BooleanLiteral().toString());
		}
	}
	@Override
	public void enterPrimary(LeverParser.PrimaryContext ctx) {
	
	}


	@Override public void enterVariableDeclarator(LeverParser.VariableDeclaratorContext ctx) {

		

	}

	@Override public void exitVariableDeclarator(LeverParser.VariableDeclaratorContext ctx) {
		
	}

	@Override public void enterIdentifierVar(LeverParser.IdentifierVarContext ctx) {
		printTabs();
		printTarget("LeverVar ");	
	}

	@Override public void exitIdentifierVar(LeverParser.IdentifierVarContext ctx) {

		printTarget(" = new LeverVar();\n");	
	}

	@Override public void enterInitialization(LeverParser.InitializationContext ctx) {

		LeverParser.VariableDeclaratorContext parent = (LeverParser.VariableDeclaratorContext)ctx.getParent();
		TerminalNode id = parent.identifierVar().Identifier();

		printTabs();
		printTarget(id.getText() + ".val ");
	}

	@Override public void exitInitialization(LeverParser.InitializationContext ctx) {
		printTarget(";\n");
	}

	@Override
	public void visitTerminal(TerminalNode node) {

		Token token = node.getSymbol();
		int type = token.getType();
		String id = token.getText();

		switch(type) {
			case LeverLexer.Identifier:
				if (id.equals("output")) {
					printTarget("LeverAPI.output");
				} else {

					String tmp = node.getParent().getChild(0).toString();
					if (leverConstructs.contains(tmp)) {

					} else {
						printTarget(id);

					}
					//System.out.println(node.getParent().getChild(0) + ", " + id);
				}
				
				break;



			case LeverLexer.NumberLiteral:
				String tmp = node.getParent().getChild(0).toString();
				if (leverConstructs.contains(tmp)) {
					break;
				}
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
				printTarget("else");
				break;
			case LeverLexer.WHILE:
				printTarget("while ");
				break;

			case LeverLexer.ADD:
				printTarget(" + ");
				break;

			case LeverLexer.LBRACE:
				openBraces();
				break;

			case LeverLexer.RBRACE:
				closeBraces();
				break;
			default:
				if (!leverTerminals.contains(id)) {
					printTarget(id);

				}
			


		}
	}
}
