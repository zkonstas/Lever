// Generated from translator/LeverGrammar.g4 by ANTLR 4.5
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LeverGrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, StringLiteral=11, PROGRAM=12, VAR=13, Identifier=14, WS=15, BLOCK_COMMENT=16, 
		LINE_COMMENT=17;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "StringLiteral", "StringCharacters", "StringCharacter", "EscapeSequence", 
		"PROGRAM", "VAR", "Identifier", "JavaLetter", "LetterOrDigit", "WS", "BLOCK_COMMENT", 
		"LINE_COMMENT"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'{'", "'}'", "';'", "'='", "'['", "']'", "','", "'('", "')'", "'this'", 
		null, "'program'", "'var'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, "StringLiteral", 
		"PROGRAM", "VAR", "Identifier", "WS", "BLOCK_COMMENT", "LINE_COMMENT"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public LeverGrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "LeverGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\23\u008f\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\3\2\3\2\3\3\3"+
		"\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3"+
		"\13\3\13\3\13\3\f\3\f\5\fI\n\f\3\f\3\f\3\r\6\rN\n\r\r\r\16\rO\3\16\3\16"+
		"\5\16T\n\16\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21"+
		"\3\21\3\21\3\21\3\22\3\22\7\22g\n\22\f\22\16\22j\13\22\3\23\3\23\3\24"+
		"\3\24\3\25\6\25q\n\25\r\25\16\25r\3\25\3\25\3\26\3\26\3\26\3\26\7\26{"+
		"\n\26\f\26\16\26~\13\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\7"+
		"\27\u0089\n\27\f\27\16\27\u008c\13\27\3\27\3\27\3|\2\30\3\3\5\4\7\5\t"+
		"\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\2\33\2\35\2\37\16!\17#\20%\2"+
		"\'\2)\21+\22-\23\3\2\b\4\2$$^^\n\2$$))^^ddhhppttvv\6\2&&C\\aac|\7\2&&"+
		"\62;C\\aac|\5\2\13\f\17\17\"\"\4\2\f\f\17\17\u0090\2\3\3\2\2\2\2\5\3\2"+
		"\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21"+
		"\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\37\3\2\2\2\2!\3\2\2"+
		"\2\2#\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\3/\3\2\2\2\5\61\3\2\2\2"+
		"\7\63\3\2\2\2\t\65\3\2\2\2\13\67\3\2\2\2\r9\3\2\2\2\17;\3\2\2\2\21=\3"+
		"\2\2\2\23?\3\2\2\2\25A\3\2\2\2\27F\3\2\2\2\31M\3\2\2\2\33S\3\2\2\2\35"+
		"U\3\2\2\2\37X\3\2\2\2!`\3\2\2\2#d\3\2\2\2%k\3\2\2\2\'m\3\2\2\2)p\3\2\2"+
		"\2+v\3\2\2\2-\u0084\3\2\2\2/\60\7}\2\2\60\4\3\2\2\2\61\62\7\177\2\2\62"+
		"\6\3\2\2\2\63\64\7=\2\2\64\b\3\2\2\2\65\66\7?\2\2\66\n\3\2\2\2\678\7]"+
		"\2\28\f\3\2\2\29:\7_\2\2:\16\3\2\2\2;<\7.\2\2<\20\3\2\2\2=>\7*\2\2>\22"+
		"\3\2\2\2?@\7+\2\2@\24\3\2\2\2AB\7v\2\2BC\7j\2\2CD\7k\2\2DE\7u\2\2E\26"+
		"\3\2\2\2FH\7$\2\2GI\5\31\r\2HG\3\2\2\2HI\3\2\2\2IJ\3\2\2\2JK\7$\2\2K\30"+
		"\3\2\2\2LN\5\33\16\2ML\3\2\2\2NO\3\2\2\2OM\3\2\2\2OP\3\2\2\2P\32\3\2\2"+
		"\2QT\n\2\2\2RT\5\35\17\2SQ\3\2\2\2SR\3\2\2\2T\34\3\2\2\2UV\7^\2\2VW\t"+
		"\3\2\2W\36\3\2\2\2XY\7r\2\2YZ\7t\2\2Z[\7q\2\2[\\\7i\2\2\\]\7t\2\2]^\7"+
		"c\2\2^_\7o\2\2_ \3\2\2\2`a\7x\2\2ab\7c\2\2bc\7t\2\2c\"\3\2\2\2dh\5%\23"+
		"\2eg\5\'\24\2fe\3\2\2\2gj\3\2\2\2hf\3\2\2\2hi\3\2\2\2i$\3\2\2\2jh\3\2"+
		"\2\2kl\t\4\2\2l&\3\2\2\2mn\t\5\2\2n(\3\2\2\2oq\t\6\2\2po\3\2\2\2qr\3\2"+
		"\2\2rp\3\2\2\2rs\3\2\2\2st\3\2\2\2tu\b\25\2\2u*\3\2\2\2vw\7\61\2\2wx\7"+
		",\2\2x|\3\2\2\2y{\13\2\2\2zy\3\2\2\2{~\3\2\2\2|}\3\2\2\2|z\3\2\2\2}\177"+
		"\3\2\2\2~|\3\2\2\2\177\u0080\7,\2\2\u0080\u0081\7\61\2\2\u0081\u0082\3"+
		"\2\2\2\u0082\u0083\b\26\2\2\u0083,\3\2\2\2\u0084\u0085\7\61\2\2\u0085"+
		"\u0086\7\61\2\2\u0086\u008a\3\2\2\2\u0087\u0089\n\7\2\2\u0088\u0087\3"+
		"\2\2\2\u0089\u008c\3\2\2\2\u008a\u0088\3\2\2\2\u008a\u008b\3\2\2\2\u008b"+
		"\u008d\3\2\2\2\u008c\u008a\3\2\2\2\u008d\u008e\b\27\2\2\u008e.\3\2\2\2"+
		"\n\2HOShr|\u008a\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}