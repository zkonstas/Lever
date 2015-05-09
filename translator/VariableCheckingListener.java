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

	@Override public void enterIdentifierVar(LeverParser.IdentifierVarContext ctx) {
		//System.out.println(ctx.Identifier().getText());
		addVarId(ctx.Identifier().getText());
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

			assignVarIdType(varId, type);
		}
	}

	public void addVarId(String varId) {

		if (!symbolTable.containsKey(varId)) {
				symbolTable.put(varId, null);
				
				// System.out.println(id.getText());
				// System.out.println(type);
		}
		else {
			//variable identifier has already been declared
			System.out.println("duplicate declaration of identifier: " + varId);
			System.exit(1);
		}
	}

	public void initializeVarIdType(String varId, LType type) {

		if (symbolTable.containsKey(varId)) {
				symbolTable.put(varId, type);
				// System.out.println(id.getText());
				// System.out.println(type);
		}
		else {

			System.out.println("semantic check error!");
			System.exit(1);	
		}
	}

	public void assignVarIdType(String varId, LType type) {

		if (symbolTable.containsKey(varId)) {

			LType assignedType = symbolTable.get(varId);

			if (assignedType != null) {

				if (assignedType != type) {
					//trying to assign incompatible variable types
					System.out.println("incompatible type assignment");
					System.exit(1);
				}
			}
			else {
				symbolTable.put(varId, type);
			}
		}
		else {
			//trying to assign incompatible variable types
			System.out.println("identifier has not been declared!");
			System.out.println("identifier: " + varId);
			System.exit(1);
		}
	}

	@Override
	public void enterInitialization(LeverParser.InitializationContext ctx) {

		LeverParser.VariableDeclaratorContext parent = (LeverParser.VariableDeclaratorContext)ctx.getParent();
		TerminalNode id = parent.identifierVar().Identifier();
		String varId = id.getText();

		LeverParser.ExpressionContext expCtx = ctx.expression();
		LType type = getExpressionType(expCtx);

		// System.out.println("Initialized " + varId);
		initializeVarIdType(varId, type);
	}


	@Override public void enterMethodDefinition(LeverParser.MethodDefinitionContext ctx) { 

		String varId = ctx.Identifier().getText();
		addVarId(varId);
		
		LeverParser.BlockContext block = ctx.methodBody().block();

		if (block != null) {

			List statements = block.blockStatement();

			for (Object stm : statements) {

				LeverParser.BlockStatementContext blSt = (LeverParser.BlockStatementContext)stm;

				if (blSt.statement() != null) {

					if (blSt.statement().nonBlockStatement() != null && blSt.statement().nonBlockStatement().getToken(LeverLexer.RETURN, 0) != null) {
						
						LeverParser.ExpressionContext exp = blSt.statement().nonBlockStatement().expression();
						LType type = getExpressionType(exp);
						initializeVarIdType(varId, type);
					}
				}

			}
		}


	}

}
