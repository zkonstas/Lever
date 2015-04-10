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

//don't need to override every enter/exit method
public class LeverToJavaListener extends LeverGrammarBaseListener {
	LeverGrammarParser parser;
	
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
	public void enterLever(LeverGrammarParser.LeverContext ctx) {
		printTarget("public class " + fileName + " {\n");
		printTarget("\tpublic static void main(String[] args) {\n");
	}

	@Override
	public void exitLever(LeverGrammarParser.LeverContext ctx) {
		printTarget("\t}\n}");
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void enterCompoundStatement(LeverGrammarParser.CompoundStatementContext ctx) {
		//TODO keep track of tabs
		//printTarget("{\n");
	}
	@Override
	public void exitCompoundStatement(LeverGrammarParser.CompoundStatementContext ctx) {
		//printTarget("}");
	}
	@Override
	public void enterStatementExpression(LeverGrammarParser.StatementExpressionContext ctx) {
		
	}
	@Override
	public void exitStatementExpression(LeverGrammarParser.StatementExpressionContext ctx) {
		printTarget(";\n");
	}
	@Override
	public void enterFunctionParams(LeverGrammarParser.FunctionParamsContext ctx) {
		printTarget("(");
	}
	@Override
	public void exitFunctionParams(LeverGrammarParser.FunctionParamsContext ctx) {
		printTarget(")");
	}

	@Override
	public void enterLiteral(LeverGrammarParser.LiteralContext ctx) {
		//printTarget(ctx.StringLiteral().getText());
	}
	@Override
	public void enterPrimary(LeverGrammarParser.PrimaryContext ctx) {
		String id = ctx.getText();
		if (id.equals("output")) {
			printTarget("LeverClasses.output");
		} else {
			printTarget(ctx.getText());
		}
	}
	@Override
	public void visitTerminal(TerminalNode node) {
		String id = node.getSymbol().getText();
		if (id.equals("output")) {
			//printTarget("LeverClasses.output");
		} else {
			//printTarget(id);
		}
	}
}
