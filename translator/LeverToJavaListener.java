//don't need to override every enter/exit method
public class LeverToJavaListener extends LeverGrammarBaseListener {
	LeverGrammarParser parser;
	public LeverToJavaListener(String filename) {
		//this.parser = parser;
	}
	
	/** Translate { to " */
	@Override
	public void enterLever(LeverGrammarParser.LeverContext ctx) {
		System.out.println("class { ");
		System.out.println("\tpublic static void main(String[] args) {");
	}
	/** Translate } to " */
	@Override
	public void exitLever(LeverGrammarParser.LeverContext ctx) {
		System.out.println("\t}");
		System.out.println("}");
	}


	/*@Override
	public void enterValue(LeverGrammarParser.ValueContext ctx) {
		//assuming no nested array initializers?

		//get INT token
		int value = Integer.valueOf(ctx.INT().getText());
		System.out.printf("\\u%04x", value);
	}*/
}
