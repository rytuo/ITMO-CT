// Generated from /home/rytuo/projects/mt/lab3/src/Prefix.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PrefixLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, N=17, 
		WS=18, L=19, KEY=20, VAR=21;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "N", "WS", 
			"L", "KEY", "VAR"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'if'", "'('", "')'", "'print'", "'='", "'while'", "'or'", "'and'", 
			"'<'", "'>'", "'=='", "'+'", "'-'", "'*'", "'/'", "'not'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, "N", "WS", "L", "KEY", "VAR"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
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


	public PrefixLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Prefix.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\27\u010e\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\3\2\3\2\3\2\3\3\3\3\3\4"+
		"\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3"+
		"\b\3\t\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\17"+
		"\3\17\3\20\3\20\3\21\3\21\3\21\3\21\3\22\6\22^\n\22\r\22\16\22_\3\23\6"+
		"\23c\n\23\r\23\16\23d\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\5\24r\n\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\5\25\u0106\n\25\3\26\3\26"+
		"\7\26\u010a\n\26\f\26\16\26\u010d\13\26\2\2\27\3\3\5\4\7\5\t\6\13\7\r"+
		"\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25"+
		")\26+\27\3\2\6\3\2\62;\4\2\13\f\"\"\4\2C\\c|\6\2\62;C\\aac|\2\u0131\2"+
		"\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2"+
		"\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2"+
		"\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2"+
		"\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\3-\3\2\2\2\5\60\3\2\2"+
		"\2\7\62\3\2\2\2\t\64\3\2\2\2\13:\3\2\2\2\r<\3\2\2\2\17B\3\2\2\2\21E\3"+
		"\2\2\2\23I\3\2\2\2\25K\3\2\2\2\27M\3\2\2\2\31P\3\2\2\2\33R\3\2\2\2\35"+
		"T\3\2\2\2\37V\3\2\2\2!X\3\2\2\2#]\3\2\2\2%b\3\2\2\2\'q\3\2\2\2)\u0105"+
		"\3\2\2\2+\u0107\3\2\2\2-.\7k\2\2./\7h\2\2/\4\3\2\2\2\60\61\7*\2\2\61\6"+
		"\3\2\2\2\62\63\7+\2\2\63\b\3\2\2\2\64\65\7r\2\2\65\66\7t\2\2\66\67\7k"+
		"\2\2\678\7p\2\289\7v\2\29\n\3\2\2\2:;\7?\2\2;\f\3\2\2\2<=\7y\2\2=>\7j"+
		"\2\2>?\7k\2\2?@\7n\2\2@A\7g\2\2A\16\3\2\2\2BC\7q\2\2CD\7t\2\2D\20\3\2"+
		"\2\2EF\7c\2\2FG\7p\2\2GH\7f\2\2H\22\3\2\2\2IJ\7>\2\2J\24\3\2\2\2KL\7@"+
		"\2\2L\26\3\2\2\2MN\7?\2\2NO\7?\2\2O\30\3\2\2\2PQ\7-\2\2Q\32\3\2\2\2RS"+
		"\7/\2\2S\34\3\2\2\2TU\7,\2\2U\36\3\2\2\2VW\7\61\2\2W \3\2\2\2XY\7p\2\2"+
		"YZ\7q\2\2Z[\7v\2\2[\"\3\2\2\2\\^\t\2\2\2]\\\3\2\2\2^_\3\2\2\2_]\3\2\2"+
		"\2_`\3\2\2\2`$\3\2\2\2ac\t\3\2\2ba\3\2\2\2cd\3\2\2\2db\3\2\2\2de\3\2\2"+
		"\2ef\3\2\2\2fg\b\23\2\2g&\3\2\2\2hi\7V\2\2ij\7t\2\2jk\7w\2\2kr\7g\2\2"+
		"lm\7H\2\2mn\7c\2\2no\7n\2\2op\7u\2\2pr\7g\2\2qh\3\2\2\2ql\3\2\2\2r(\3"+
		"\2\2\2st\7c\2\2tu\7y\2\2uv\7c\2\2vw\7k\2\2w\u0106\7v\2\2xy\7g\2\2yz\7"+
		"n\2\2z{\7u\2\2{\u0106\7g\2\2|}\7k\2\2}~\7o\2\2~\177\7r\2\2\177\u0080\7"+
		"q\2\2\u0080\u0081\7t\2\2\u0081\u0106\7v\2\2\u0082\u0083\7r\2\2\u0083\u0084"+
		"\7c\2\2\u0084\u0085\7u\2\2\u0085\u0106\7u\2\2\u0086\u0087\7P\2\2\u0087"+
		"\u0088\7q\2\2\u0088\u0089\7p\2\2\u0089\u0106\7g\2\2\u008a\u008b\7d\2\2"+
		"\u008b\u008c\7t\2\2\u008c\u008d\7g\2\2\u008d\u008e\7c\2\2\u008e\u0106"+
		"\7m\2\2\u008f\u0090\7g\2\2\u0090\u0091\7z\2\2\u0091\u0092\7e\2\2\u0092"+
		"\u0093\7g\2\2\u0093\u0094\7r\2\2\u0094\u0106\7v\2\2\u0095\u0096\7k\2\2"+
		"\u0096\u0106\7p\2\2\u0097\u0098\7t\2\2\u0098\u0099\7c\2\2\u0099\u009a"+
		"\7k\2\2\u009a\u009b\7u\2\2\u009b\u0106\7g\2\2\u009c\u009d\7e\2\2\u009d"+
		"\u009e\7n\2\2\u009e\u009f\7c\2\2\u009f\u00a0\7u\2\2\u00a0\u0106\7u\2\2"+
		"\u00a1\u00a2\7h\2\2\u00a2\u00a3\7k\2\2\u00a3\u00a4\7p\2\2\u00a4\u00a5"+
		"\7c\2\2\u00a5\u00a6\7n\2\2\u00a6\u00a7\7n\2\2\u00a7\u0106\7{\2\2\u00a8"+
		"\u00a9\7k\2\2\u00a9\u0106\7u\2\2\u00aa\u00ab\7t\2\2\u00ab\u00ac\7g\2\2"+
		"\u00ac\u00ad\7v\2\2\u00ad\u00ae\7w\2\2\u00ae\u00af\7t\2\2\u00af\u0106"+
		"\7p\2\2\u00b0\u00b1\7c\2\2\u00b1\u00b2\7p\2\2\u00b2\u0106\7f\2\2\u00b3"+
		"\u00b4\7e\2\2\u00b4\u00b5\7q\2\2\u00b5\u00b6\7p\2\2\u00b6\u00b7\7v\2\2"+
		"\u00b7\u00b8\7k\2\2\u00b8\u00b9\7p\2\2\u00b9\u00ba\7w\2\2\u00ba\u0106"+
		"\7g\2\2\u00bb\u00bc\7h\2\2\u00bc\u00bd\7q\2\2\u00bd\u0106\7t\2\2\u00be"+
		"\u00bf\7n\2\2\u00bf\u00c0\7c\2\2\u00c0\u00c1\7o\2\2\u00c1\u00c2\7d\2\2"+
		"\u00c2\u00c3\7f\2\2\u00c3\u0106\7c\2\2\u00c4\u00c5\7v\2\2\u00c5\u00c6"+
		"\7t\2\2\u00c6\u0106\7{\2\2\u00c7\u00c8\7c\2\2\u00c8\u0106\7u\2\2\u00c9"+
		"\u00ca\7f\2\2\u00ca\u00cb\7g\2\2\u00cb\u0106\7h\2\2\u00cc\u00cd\7h\2\2"+
		"\u00cd\u00ce\7t\2\2\u00ce\u00cf\7q\2\2\u00cf\u0106\7o\2\2\u00d0\u00d1"+
		"\7p\2\2\u00d1\u00d2\7q\2\2\u00d2\u00d3\7p\2\2\u00d3\u00d4\7n\2\2\u00d4"+
		"\u00d5\7q\2\2\u00d5\u00d6\7e\2\2\u00d6\u00d7\7c\2\2\u00d7\u0106\7n\2\2"+
		"\u00d8\u00d9\7y\2\2\u00d9\u00da\7j\2\2\u00da\u00db\7k\2\2\u00db\u00dc"+
		"\7n\2\2\u00dc\u0106\7g\2\2\u00dd\u00de\7c\2\2\u00de\u00df\7u\2\2\u00df"+
		"\u00e0\7u\2\2\u00e0\u00e1\7g\2\2\u00e1\u00e2\7t\2\2\u00e2\u0106\7v\2\2"+
		"\u00e3\u00e4\7f\2\2\u00e4\u00e5\7g\2\2\u00e5\u0106\7n\2\2\u00e6\u00e7"+
		"\7i\2\2\u00e7\u00e8\7n\2\2\u00e8\u00e9\7q\2\2\u00e9\u00ea\7d\2\2\u00ea"+
		"\u00eb\7c\2\2\u00eb\u0106\7n\2\2\u00ec\u00ed\7p\2\2\u00ed\u00ee\7q\2\2"+
		"\u00ee\u0106\7v\2\2\u00ef\u00f0\7y\2\2\u00f0\u00f1\7k\2\2\u00f1\u00f2"+
		"\7v\2\2\u00f2\u0106\7j\2\2\u00f3\u00f4\7c\2\2\u00f4\u00f5\7u\2\2\u00f5"+
		"\u00f6\7{\2\2\u00f6\u00f7\7p\2\2\u00f7\u0106\7e\2\2\u00f8\u00f9\7g\2\2"+
		"\u00f9\u00fa\7n\2\2\u00fa\u00fb\7k\2\2\u00fb\u0106\7h\2\2\u00fc\u00fd"+
		"\7k\2\2\u00fd\u0106\7h\2\2\u00fe\u00ff\7q\2\2\u00ff\u0106\7t\2\2\u0100"+
		"\u0101\7{\2\2\u0101\u0102\7k\2\2\u0102\u0103\7g\2\2\u0103\u0104\7n\2\2"+
		"\u0104\u0106\7f\2\2\u0105s\3\2\2\2\u0105x\3\2\2\2\u0105|\3\2\2\2\u0105"+
		"\u0082\3\2\2\2\u0105\u0086\3\2\2\2\u0105\u008a\3\2\2\2\u0105\u008f\3\2"+
		"\2\2\u0105\u0095\3\2\2\2\u0105\u0097\3\2\2\2\u0105\u009c\3\2\2\2\u0105"+
		"\u00a1\3\2\2\2\u0105\u00a8\3\2\2\2\u0105\u00aa\3\2\2\2\u0105\u00b0\3\2"+
		"\2\2\u0105\u00b3\3\2\2\2\u0105\u00bb\3\2\2\2\u0105\u00be\3\2\2\2\u0105"+
		"\u00c4\3\2\2\2\u0105\u00c7\3\2\2\2\u0105\u00c9\3\2\2\2\u0105\u00cc\3\2"+
		"\2\2\u0105\u00d0\3\2\2\2\u0105\u00d8\3\2\2\2\u0105\u00dd\3\2\2\2\u0105"+
		"\u00e3\3\2\2\2\u0105\u00e6\3\2\2\2\u0105\u00ec\3\2\2\2\u0105\u00ef\3\2"+
		"\2\2\u0105\u00f3\3\2\2\2\u0105\u00f8\3\2\2\2\u0105\u00fc\3\2\2\2\u0105"+
		"\u00fe\3\2\2\2\u0105\u0100\3\2\2\2\u0106*\3\2\2\2\u0107\u010b\t\4\2\2"+
		"\u0108\u010a\t\5\2\2\u0109\u0108\3\2\2\2\u010a\u010d\3\2\2\2\u010b\u0109"+
		"\3\2\2\2\u010b\u010c\3\2\2\2\u010c,\3\2\2\2\u010d\u010b\3\2\2\2\b\2_d"+
		"q\u0105\u010b\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}