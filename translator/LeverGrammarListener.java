// Generated from LeverGrammar.g4 by ANTLR 4.5
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LeverGrammarParser}.
 */
public interface LeverGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LeverGrammarParser#lever}.
	 * @param ctx the parse tree
	 */
	void enterLever(LeverGrammarParser.LeverContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeverGrammarParser#lever}.
	 * @param ctx the parse tree
	 */
	void exitLever(LeverGrammarParser.LeverContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeverGrammarParser#compoundStatement}.
	 * @param ctx the parse tree
	 */
	void enterCompoundStatement(LeverGrammarParser.CompoundStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeverGrammarParser#compoundStatement}.
	 * @param ctx the parse tree
	 */
	void exitCompoundStatement(LeverGrammarParser.CompoundStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeverGrammarParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatement(LeverGrammarParser.BlockStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeverGrammarParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatement(LeverGrammarParser.BlockStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeverGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(LeverGrammarParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeverGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(LeverGrammarParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeverGrammarParser#memberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMemberDeclaration(LeverGrammarParser.MemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeverGrammarParser#memberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMemberDeclaration(LeverGrammarParser.MemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeverGrammarParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFieldDeclaration(LeverGrammarParser.FieldDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeverGrammarParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFieldDeclaration(LeverGrammarParser.FieldDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeverGrammarParser#declarationList}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationList(LeverGrammarParser.DeclarationListContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeverGrammarParser#declarationList}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationList(LeverGrammarParser.DeclarationListContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeverGrammarParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(LeverGrammarParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeverGrammarParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(LeverGrammarParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeverGrammarParser#variableId}.
	 * @param ctx the parse tree
	 */
	void enterVariableId(LeverGrammarParser.VariableIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeverGrammarParser#variableId}.
	 * @param ctx the parse tree
	 */
	void exitVariableId(LeverGrammarParser.VariableIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeverGrammarParser#variableInit}.
	 * @param ctx the parse tree
	 */
	void enterVariableInit(LeverGrammarParser.VariableInitContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeverGrammarParser#variableInit}.
	 * @param ctx the parse tree
	 */
	void exitVariableInit(LeverGrammarParser.VariableInitContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeverGrammarParser#arrayInit}.
	 * @param ctx the parse tree
	 */
	void enterArrayInit(LeverGrammarParser.ArrayInitContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeverGrammarParser#arrayInit}.
	 * @param ctx the parse tree
	 */
	void exitArrayInit(LeverGrammarParser.ArrayInitContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeverGrammarParser#identifierList}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierList(LeverGrammarParser.IdentifierListContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeverGrammarParser#identifierList}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierList(LeverGrammarParser.IdentifierListContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeverGrammarParser#statementExpression}.
	 * @param ctx the parse tree
	 */
	void enterStatementExpression(LeverGrammarParser.StatementExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeverGrammarParser#statementExpression}.
	 * @param ctx the parse tree
	 */
	void exitStatementExpression(LeverGrammarParser.StatementExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeverGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(LeverGrammarParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeverGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(LeverGrammarParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeverGrammarParser#primaryList}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryList(LeverGrammarParser.PrimaryListContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeverGrammarParser#primaryList}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryList(LeverGrammarParser.PrimaryListContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeverGrammarParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(LeverGrammarParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeverGrammarParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(LeverGrammarParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link LeverGrammarParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(LeverGrammarParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link LeverGrammarParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(LeverGrammarParser.LiteralContext ctx);
}