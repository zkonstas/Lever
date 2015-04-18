import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.Token;

//don't need to override every enter/exit method
public class LeverToJavaListener extends LeverBaseListener {
	LeverParser parser;
	
	private String fileName;
	private File targetFile;
	private BufferedWriter bw;

	public LeverToJavaListener(String _fileName) {
		//this.parser = parser;
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
		printTarget("public class " + fileName + " {\n");
		printTarget("\tpublic static void main(String[] args) {\n");
	}

	@Override
	public void exitMainProgram(LeverParser.MainProgramContext ctx) {
		printTarget("\t}\n}");
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
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
		printTarget(";\n");
	}
	
	@Override
	public void enterLiteral(LeverParser.LiteralContext ctx) {
		//printTarget(ctx.StringLiteral().getText());
	}
	@Override
	public void enterPrimary(LeverParser.PrimaryContext ctx) {
	
	}
	@Override
	public void visitTerminal(TerminalNode node) {

		Token token = node.getSymbol();
		int type = token.getType();
		String id = token.getText();

		switch(type) {
			case LeverLexer.Identifier:
				if (id.equals("output")) {
					printTarget("LeverClasses.output");
				} else {
					printTarget(id);
				}
				
				break;



			case LeverLexer.NumberLiteral:
			case LeverLexer.StringLiteral:
				printTarget(id);

				break;



		}
	}
}
