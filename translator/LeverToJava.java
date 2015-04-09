//don't need to override every enter/exit method
public class LeverToJava extends LeverGrammarBaseListener {
	/** Translate { to " */
	@Override
	public void enterLever(LeverGrammarParser.LeverContext ctx) {
		System.out.print('"');
	}
	/** Translate } to " */
	@Override
	public void exitLever(LeverGrammarParser.LeverContext ctx) {
		System.out.print('"');
	}


	/** Translate integers to 4-digit hexadecimal strings prefixed with \\u */
	/*@Override
	public void enterValue(LeverGrammarParser.ValueContext ctx) {
		//assuming no nested array initializers?

		//get INT token
		int value = Integer.valueOf(ctx.INT().getText());
		System.out.printf("\\u%04x", value);
	}*/
}
