//ANTLR's runtime libraries
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Leverc {
	public static void main(String[] args) throws Exception {

		if (args.length == 0) {
			System.out.println("usage: Leverc <filename.lever>");
			return;
		}
		String fileName = args[0];

		//CharStream that reads from standard input
		//ANTLRInputStream input = new ANTLRInputStream(System.in);

		//create lexer that feeds off of input CharStream
		Lexer lexer = new LeverLexer(new ANTLRFileStream(fileName));

		//buffer of tokens pulled from lexer
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		//TokenRewriteStream tokens = new TokenRewriteStream(lexer);

		//parser that feeds off tokens buffer
		LeverParser parser = new LeverParser(tokens);

		ParseTree tree = parser.lever(); //begin parsing at prog rule
		//System.out.println(tree.toStringTree(parser)); //print LISP-style tree

		//generic parse tree walker that triggers callbacks
		ParseTreeWalker walker = new ParseTreeWalker();
		//walk tree, trigger callbacks
		walker.walk(new LeverToJavaListener(fileName), tree);


	}
}
