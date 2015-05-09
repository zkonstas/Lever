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
public class VariableCheckingListener extends LeverBaseListener {
	LeverParser parser;

	public enum LType {
    	LString, LInteger, LDouble, LBoolean,
    	LList, LDictionary, LUser, LTopic, LResult 
	}

	public HashMap<String, LType> symbolTable = new HashMap<String, LType>();

	public VariableCheckingListener(LeverParser parser) {
		this.parser = parser;
	}

	public LType getExpressionType(LeverParser.ExpressionContext expCtx) {

		LType type = null;
		LeverParser.ExpressionContext exp = expCtx;
		
		while (exp != null) {

			if (exp.primary() != null) {
				//we found a primary expression from which we can get the literal
				break;
			}

			//Get first expression
			exp = exp.expression().get(0);
		}
			
		if (exp.primary() != null) {

			LeverParser.LiteralContext literal = exp.primary().literal();

			if (literal != null) {

				if (literal.NumberLiteral() != null) {
					
					if (literal.NumberLiteral().getText().contains(".")) {
						type = LType.LDouble;
						// System.out.println("double");						
					}
					else {
						// System.out.println("int");
						type = LType.LInteger;
					}
				}
				else if (literal.StringLiteral() != null) {
					// System.out.println("string");
					type = LType.LString;
				}
				else if (literal.BooleanLiteral() != null) {
					// System.out.println("boolean");
					type = LType.LBoolean;
				}


			}
		}
		return type;
	}

	@Override public void enterStatementExpression(LeverParser.StatementExpressionContext ctx) {

		LeverParser.ExpressionContext expCtx = ctx.expression();

		//Check if this is an assignment expression
		if (expCtx.getToken(LeverLexer.ASSIGN, 0) != null) {

			//Get variable identifier
			ParseTree left = expCtx.getChild(0);
			LeverParser.ExpressionContext lExp = (LeverParser.ExpressionContext)left;
			TerminalNode id = lExp.primary().Identifier();
			String varId = id.getText();

			//Get type of right expression
			ParseTree right = expCtx.getChild(2);
			LeverParser.ExpressionContext rExp = (LeverParser.ExpressionContext)right;
			LType type = getExpressionType(rExp);

			saveToTable(varId, type);
		}
	}

	public void saveToTable(String varId, LType type) {

		if (!symbolTable.containsKey(varId)) {
				symbolTable.put(varId, type);
				
				// System.out.println(id.getText());
				// System.out.println(type);
			}
			else {
				if (symbolTable.get(varId) != type) {

					//trying to assign incompatible variable types
					System.out.println("incompatible types!");
					System.exit(1);
				}
				
			}
	}

	@Override
	public void enterInitialization(LeverParser.InitializationContext ctx) {

		LeverParser.VariableDeclaratorContext parent = (LeverParser.VariableDeclaratorContext)ctx.getParent();
		TerminalNode id = parent.identifierVar().Identifier();
		String varId = id.getText();

		System.out.println(ctx.getText());
		LeverParser.ExpressionContext expCtx = ctx.expression();
		LType type = getExpressionType(expCtx);

		// System.out.println("Initialized " + varId);
		saveToTable(varId, type);
	}

}
