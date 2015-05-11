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

	public static HashMap<String, LType> symbolTable = new HashMap<String, LType>();
	public static HashMap<String, LeverParser.MethodDefinitionContext> functionTable = new HashMap<String, LeverParser.MethodDefinitionContext>();

	private class FunctionDef {
		int paramNum = -1;
		ArrayList<String> parameterIds;
		LType type = null;
		int returnParamIndex = -1;
	}

	public VariableCheckingListener(LeverParser parser) {
		this.parser = parser;
	}

	@Override public void enterIdentifierVar(LeverParser.IdentifierVarContext ctx) {
		//System.out.println(ctx.Identifier().getText());
		addVarId(ctx.Identifier().getText());
	}

	public static LType getExpressionType(LeverParser.ExpressionContext expCtx) {

		LType type = null;
		LeverParser.ExpressionContext exp = expCtx;
		
		while (exp != null) {

			if (exp.primary() != null || exp.methodCall() != null || exp.dictionary() != null) {
				//we found a primary or method call expression from which we can get the literal
				break;
			}
			//System.out.println(exp.functionInvocation().getText());
			//Get first expression
			if (exp.resultUserAccess() != null) {
				return null;
			}
			exp = exp.expression().get(0);
		}
			
		if (exp.primary() != null) {

			if (exp.primary().literal() != null) {
				LeverParser.LiteralContext literal = exp.primary().literal();

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
			else if (exp.primary().Identifier() != null) {

				String varId = exp.primary().Identifier().getText();

				if (varId.equals("input")) {
					return LType.LString;
				}

				if (!symbolTable.containsKey(varId)) {
					System.out.println("identifier has not been declared!");
					System.out.println("identifier: " + varId);
					System.exit(1);
				}
				else {
					LType idType = symbolTable.get(varId);

					if (idType == null) {
						System.out.println("identifier may have not been initialized");
						System.out.println("identifier: " + varId);
						System.exit(1);	
					}
					else {
						type = idType;
					}
				}

			} else if (exp.primary().leverLiteral() != null) {
                //LEVER LITERALS @ and #
                LeverParser.LeverLiteralContext litx = exp.primary().leverLiteral();

                System.out.println("saw hashtag  " + exp.primary().leverLiteral().Identifier());
                if (litx.HASHTAG() != null) {
                    type = LType.LString;
                }
            }
		}

		if (exp.methodCall() != null) {
			System.out.println(exp.methodCall().getText());
			if (exp.methodCall().objectMethodCall() == null) {

				String varId = exp.methodCall().Identifier().getText();

				if (varId.equals("get")) {
					type = LType.LResult;
				}
				else {
					//type = symbolTable.get(varId);
				}
			}
			else {
				//type = getMethodCallType(exp.methodCall());
			}
		}

		return type;
	}

	@Override public void enterStatementExpression(LeverParser.StatementExpressionContext ctx) {

		if (ctx.expression() != null) {
			LeverParser.ExpressionContext expCtx = ctx.expression();

			//Check if this is an assignment expression
			if (expCtx.getToken(LeverLexer.ASSIGN, 0) != null) {

				//Get variable identifier
				ParseTree left = expCtx.getChild(0);
				LeverParser.ExpressionContext lExp = (LeverParser.ExpressionContext)left;

				// String varId = "";

				if (lExp.arrayAccess() != null) {
					return;
				}

				TerminalNode id = lExp.primary().Identifier();
				String varId = id.getText();

				//Get type of right expression
				ParseTree right = expCtx.getChild(2);
				LeverParser.ExpressionContext rExp = (LeverParser.ExpressionContext)right;
				LType type = getExpressionType(rExp);



                System.out.println("-->" + varId + " " + type.name());
				assignVarIdType(varId, type, ctx);
			}
		}
		else if (ctx.zeroArgumentMethodCall() != null) {
			String funcId = ctx.zeroArgumentMethodCall().Identifier().getText();
			if (!functionTable.containsKey(funcId)) {
				System.out.println("calling undefined function: " + funcId);
				System.exit(1);	
			}
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

	public static void initializeVarIdType(String varId, LType type) {

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

	public void assignVarIdType(String varId, LType type, ParserRuleContext ctx) {

		if (symbolTable.containsKey(varId)) {

			LType assignedType = symbolTable.get(varId);

			if (assignedType != null) {

				if (assignedType != type) {
					System.out.println(assignedType);
					System.out.println(type);
					//trying to assign incompatible variable types
					exitErrorLine("Incompatible types!", ctx);

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

	// public void saveToTable(String varId, LType type, ParserRuleContext ctx) {

 //        if (!symbolTable.containsKey(varId)) {
 //            symbolTable.put(varId, type);

 //            // System.out.println(id.getText());
 //            // System.out.println(type);
 //        } else {
	// 		if (symbolTable.get(varId) != type) {
 //                exitErrorLine("Incompatible types!", ctx);
	// 			//trying to assign incompatible variable types
	// 		}
	// 	}
	// }

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
            // saveToTable(varId, type, ctx);
            initializeVarIdType(varId, type);
        }
		// System.out.println("Initialized " + varId);
		
	}

	public static LType getMethodCallType(String funcId) {
		LType type = null;

		LeverParser.MethodDefinitionContext ctx = functionTable.get(funcId);


		LeverParser.BlockContext block = ctx.methodBody().block();

		if (block != null) {

			List statements = block.blockStatement();

			for (Object stm : statements) {

				LeverParser.BlockStatementContext blSt = (LeverParser.BlockStatementContext)stm;

				if (blSt.statement() != null) {

					if (blSt.statement().nonBlockStatement() != null && blSt.statement().nonBlockStatement().getToken(LeverLexer.RETURN, 0) != null) {
						
						LeverParser.ExpressionContext exp = blSt.statement().nonBlockStatement().expression();
						LType typeId = getExpressionType(exp);
						type = typeId;
						// initializeVarIdType(funcId, type);
					}
				}

			}
		}

		return type;		
	}


	@Override public void enterMethodDefinition(LeverParser.MethodDefinitionContext ctx) { 

		//save function identifier
		String funcId = ctx.Identifier().getText();
		addVarId(funcId);

		//assign parameter types
		if (ctx.formalParameterList() != null) {
			List<TerminalNode> ids = ctx.formalParameterList().Identifier();

			for (TerminalNode node : ids) {
				String varId = node.getText();
				addVarId(varId);
				// System.out.println(varId);
			}
		}

		functionTable.put(funcId, ctx);

		// LeverParser.BlockContext block = ctx.methodBody().block();

		// if (block != null) {

		// 	List statements = block.blockStatement();

		// 	for (Object stm : statements) {

		// 		LeverParser.BlockStatementContext blSt = (LeverParser.BlockStatementContext)stm;

		// 		if (blSt.statement() != null) {

		// 			if (blSt.statement().nonBlockStatement() != null && blSt.statement().nonBlockStatement().getToken(LeverLexer.RETURN, 0) != null) {
						
		// 				LeverParser.ExpressionContext exp = blSt.statement().nonBlockStatement().expression();
		// 				LType type = getExpressionType(exp);
		// 				initializeVarIdType(varId, type);
		// 			}
		// 		}

		// 	}
		// }

	}

}
