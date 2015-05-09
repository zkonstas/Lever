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
    	LDictionary, LUser, LTopic, LResult,
        LListString, LListInteger, LListDouble, LListBoolean, LListUser, LListTopic
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
						//System.out.println("int");
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

			saveToTable(varId, type, ctx);
		}
	}

	public void saveToTable(String varId, LType type, ParserRuleContext ctx) {

        if (!symbolTable.containsKey(varId)) {
            symbolTable.put(varId, type);

            // System.out.println(id.getText());
            // System.out.println(type);
        } else {
			if (symbolTable.get(varId) != type) {
                exitErrorLine("Incompatible types!", ctx);
				//trying to assign incompatible variable types
			}
		}
	}

    private void exitErrorLine(String _error, ParserRuleContext _ctx) {
        System.out.println("Sorry, Lever compile failed! :(");
        System.out.println("line " + _ctx.getStart().getLine() + ": " + _error);
        System.exit(1);
    }
	@Override
	public void enterInitialization(LeverParser.InitializationContext ctx) {

		LeverParser.VariableDeclaratorContext parent = (LeverParser.VariableDeclaratorContext)ctx.getParent();
		TerminalNode id = parent.identifierVar().Identifier();
		String varId = id.getText();

        LType type = null;
		LeverParser.ExpressionContext expCtx = ctx.expression();
        if (expCtx != null) {
            type = getExpressionType(expCtx);
        } else {
            LeverParser.ArrayInitContext arrCtx = ctx.arrayInit();
            if (arrCtx != null) {
                type = getExpressionType(arrCtx.expression(0));

                switch(type) {
                    case LInteger:
                        type = LType.LListInteger;
                        break;
                    case LDouble:
                        type = LType.LListDouble;
                        break;
                    case LString:
                        type = LType.LListString;
                        break;
                    case LBoolean:
                        type = LType.LListBoolean;
                        break;
                }
                //System.out.println(type.name());
            }

        }

        if (type == null) {
            exitErrorLine("can't figure out what type this var is!", ctx);
        } else {
            // System.out.println("Initialized " + varId);
            saveToTable(varId, type, ctx);
        }


	}

}
