//ANTLR's runtime libraries
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Leverc {
	public static void main(String[] args) throws Exception {

		//CharStream that reads from standard input
		ANTLRInputStream input = new ANTLRInputStream(System.in);

		//create lexer that feeds off of input CharStream
		LeverGrammarLexer lexer = new LeverGrammarLexer(input);

		//buffer of tokens pulled from lexer
		CommonTokenStream tokens = new CommonTokenStream(lexer);

		//parser that feeds off tokens buffer
		LeverGrammarParser parser = new LeverGrammarParser(tokens);

		ParseTree tree = parser.lever(); //begin parsing at prog rule
		//System.out.println(tree.toStringTree(parser)); //print LISP-style tree

		//generic parse tree walker that triggers callbacks
		ParseTreeWalker walker = new ParseTreeWalker();
		//walk tree, trigger callbacks
		walker.walk(new LeverToJava(), tree);
		System.out.println();
	}
}
