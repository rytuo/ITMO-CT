// Generated from /home/rytuo/projects/mt/lab4/out/production/lab4/GrammarLexer.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		GR=1, T=2, R=3, LINE_SEP=4, WS=5, COLON=6, Q=7, WS_T=8, Q_TEXT=9, SB=10, 
		FB=11, RULE_SEP=12, WS_R=13, SB_TEXT=14, FB_TEXT=15, CSB_SB=16, CFB_FB=17;
	public static final int
		T_MODE=1, Q_MODE=2, R_MODE=3, SB_MODE=4, FB_MODE=5;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE", "T_MODE", "Q_MODE", "R_MODE", "SB_MODE", "FB_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"GR", "T", "R", "LINE_SEP", "WS", "LINE_SEP_T", "COLON", "Q", "WS_T", 
			"Q_TEXT", "Q_Q", "SB", "FB", "LINE_SEP_R", "COLON_R", "RULE_SEP", "T_R", 
			"R_R", "WS_R", "SB_TEXT", "OSB_SB", "CSB_SB", "FB_TEXT", "OFB_FB", "CFB_FB"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'grammar'", null, null, null, null, null, null, null, null, "'['", 
			"'{'", "'|'", null, null, null, "']'", "'}'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "GR", "T", "R", "LINE_SEP", "WS", "COLON", "Q", "WS_T", "Q_TEXT", 
			"SB", "FB", "RULE_SEP", "WS_R", "SB_TEXT", "FB_TEXT", "CSB_SB", "CFB_FB"
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


	public GrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "GrammarLexer.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\23\u00c8\b\1\b\1"+
		"\b\1\b\1\b\1\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t"+
		"\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20"+
		"\t\20\4\21\t\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27"+
		"\t\27\4\30\t\30\4\31\t\31\4\32\t\32\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\3\3\3\7\3G\n\3\f\3\16\3J\13\3\3\3\3\3\3\4\3\4\7\4P\n\4\f\4\16"+
		"\4S\13\4\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3"+
		"\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\13\6\13m\n\13\r\13\16\13n\3\13\3\13\5"+
		"\13s\n\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\17"+
		"\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\21\3\21\3\22\3\22\7\22\u008f"+
		"\n\22\f\22\16\22\u0092\13\22\3\22\3\22\3\23\3\23\7\23\u0098\n\23\f\23"+
		"\16\23\u009b\13\23\3\23\3\23\3\24\3\24\3\24\3\24\3\25\6\25\u00a4\n\25"+
		"\r\25\16\25\u00a5\3\25\3\25\5\25\u00aa\n\25\3\26\3\26\3\26\3\26\3\26\3"+
		"\27\3\27\3\27\3\27\3\27\3\30\6\30\u00b7\n\30\r\30\16\30\u00b8\3\30\3\30"+
		"\5\30\u00bd\n\30\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\2\2"+
		"\33\b\3\n\4\f\5\16\6\20\7\22\2\24\b\26\t\30\n\32\13\34\2\36\f \r\"\2$"+
		"\2&\16(\2*\2,\17.\20\60\2\62\22\64\21\66\28\23\b\2\3\4\5\6\7\t\3\2C\\"+
		"\6\2\62;C\\aac|\3\2c|\4\2\13\f\"\"\4\2))^^\3\2]_\5\2^^}}\177\177\2\u00cc"+
		"\2\b\3\2\2\2\2\n\3\2\2\2\2\f\3\2\2\2\2\16\3\2\2\2\2\20\3\2\2\2\3\22\3"+
		"\2\2\2\3\24\3\2\2\2\3\26\3\2\2\2\3\30\3\2\2\2\4\32\3\2\2\2\4\34\3\2\2"+
		"\2\5\36\3\2\2\2\5 \3\2\2\2\5\"\3\2\2\2\5$\3\2\2\2\5&\3\2\2\2\5(\3\2\2"+
		"\2\5*\3\2\2\2\5,\3\2\2\2\6.\3\2\2\2\6\60\3\2\2\2\6\62\3\2\2\2\7\64\3\2"+
		"\2\2\7\66\3\2\2\2\78\3\2\2\2\b:\3\2\2\2\nD\3\2\2\2\fM\3\2\2\2\16V\3\2"+
		"\2\2\20X\3\2\2\2\22\\\3\2\2\2\24a\3\2\2\2\26c\3\2\2\2\30g\3\2\2\2\32r"+
		"\3\2\2\2\34t\3\2\2\2\36y\3\2\2\2 }\3\2\2\2\"\u0081\3\2\2\2$\u0086\3\2"+
		"\2\2&\u008a\3\2\2\2(\u008c\3\2\2\2*\u0095\3\2\2\2,\u009e\3\2\2\2.\u00a9"+
		"\3\2\2\2\60\u00ab\3\2\2\2\62\u00b0\3\2\2\2\64\u00bc\3\2\2\2\66\u00be\3"+
		"\2\2\28\u00c3\3\2\2\2:;\7i\2\2;<\7t\2\2<=\7c\2\2=>\7o\2\2>?\7o\2\2?@\7"+
		"c\2\2@A\7t\2\2AB\3\2\2\2BC\b\2\2\2C\t\3\2\2\2DH\t\2\2\2EG\t\3\2\2FE\3"+
		"\2\2\2GJ\3\2\2\2HF\3\2\2\2HI\3\2\2\2IK\3\2\2\2JH\3\2\2\2KL\b\3\3\2L\13"+
		"\3\2\2\2MQ\t\4\2\2NP\t\3\2\2ON\3\2\2\2PS\3\2\2\2QO\3\2\2\2QR\3\2\2\2R"+
		"T\3\2\2\2SQ\3\2\2\2TU\b\4\4\2U\r\3\2\2\2VW\7=\2\2W\17\3\2\2\2XY\t\5\2"+
		"\2YZ\3\2\2\2Z[\b\6\5\2[\21\3\2\2\2\\]\7=\2\2]^\3\2\2\2^_\b\7\6\2_`\b\7"+
		"\7\2`\23\3\2\2\2ab\7<\2\2b\25\3\2\2\2cd\7)\2\2de\3\2\2\2ef\b\t\b\2f\27"+
		"\3\2\2\2gh\t\5\2\2hi\3\2\2\2ij\b\n\5\2j\31\3\2\2\2km\n\6\2\2lk\3\2\2\2"+
		"mn\3\2\2\2nl\3\2\2\2no\3\2\2\2os\3\2\2\2pq\7^\2\2qs\13\2\2\2rl\3\2\2\2"+
		"rp\3\2\2\2s\33\3\2\2\2tu\7)\2\2uv\3\2\2\2vw\b\f\t\2wx\b\f\7\2x\35\3\2"+
		"\2\2yz\7]\2\2z{\3\2\2\2{|\b\r\n\2|\37\3\2\2\2}~\7}\2\2~\177\3\2\2\2\177"+
		"\u0080\b\16\13\2\u0080!\3\2\2\2\u0081\u0082\7=\2\2\u0082\u0083\3\2\2\2"+
		"\u0083\u0084\b\17\6\2\u0084\u0085\b\17\7\2\u0085#\3\2\2\2\u0086\u0087"+
		"\7<\2\2\u0087\u0088\3\2\2\2\u0088\u0089\b\20\f\2\u0089%\3\2\2\2\u008a"+
		"\u008b\7~\2\2\u008b\'\3\2\2\2\u008c\u0090\t\2\2\2\u008d\u008f\t\3\2\2"+
		"\u008e\u008d\3\2\2\2\u008f\u0092\3\2\2\2\u0090\u008e\3\2\2\2\u0090\u0091"+
		"\3\2\2\2\u0091\u0093\3\2\2\2\u0092\u0090\3\2\2\2\u0093\u0094\b\22\r\2"+
		"\u0094)\3\2\2\2\u0095\u0099\t\4\2\2\u0096\u0098\t\3\2\2\u0097\u0096\3"+
		"\2\2\2\u0098\u009b\3\2\2\2\u0099\u0097\3\2\2\2\u0099\u009a\3\2\2\2\u009a"+
		"\u009c\3\2\2\2\u009b\u0099\3\2\2\2\u009c\u009d\b\23\16\2\u009d+\3\2\2"+
		"\2\u009e\u009f\t\5\2\2\u009f\u00a0\3\2\2\2\u00a0\u00a1\b\24\5\2\u00a1"+
		"-\3\2\2\2\u00a2\u00a4\n\7\2\2\u00a3\u00a2\3\2\2\2\u00a4\u00a5\3\2\2\2"+
		"\u00a5\u00a3\3\2\2\2\u00a5\u00a6\3\2\2\2\u00a6\u00aa\3\2\2\2\u00a7\u00a8"+
		"\7^\2\2\u00a8\u00aa\13\2\2\2\u00a9\u00a3\3\2\2\2\u00a9\u00a7\3\2\2\2\u00aa"+
		"/\3\2\2\2\u00ab\u00ac\7]\2\2\u00ac\u00ad\3\2\2\2\u00ad\u00ae\b\26\17\2"+
		"\u00ae\u00af\b\26\n\2\u00af\61\3\2\2\2\u00b0\u00b1\7_\2\2\u00b1\u00b2"+
		"\3\2\2\2\u00b2\u00b3\b\27\17\2\u00b3\u00b4\b\27\7\2\u00b4\63\3\2\2\2\u00b5"+
		"\u00b7\n\b\2\2\u00b6\u00b5\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8\u00b6\3\2"+
		"\2\2\u00b8\u00b9\3\2\2\2\u00b9\u00bd\3\2\2\2\u00ba\u00bb\7^\2\2\u00bb"+
		"\u00bd\13\2\2\2\u00bc\u00b6\3\2\2\2\u00bc\u00ba\3\2\2\2\u00bd\65\3\2\2"+
		"\2\u00be\u00bf\7}\2\2\u00bf\u00c0\3\2\2\2\u00c0\u00c1\b\31\20\2\u00c1"+
		"\u00c2\b\31\13\2\u00c2\67\3\2\2\2\u00c3\u00c4\7\177\2\2\u00c4\u00c5\3"+
		"\2\2\2\u00c5\u00c6\b\32\20\2\u00c6\u00c7\b\32\7\2\u00c79\3\2\2\2\22\2"+
		"\3\4\5\6\7HQnr\u0090\u0099\u00a5\u00a9\u00b8\u00bc\21\7\2\2\7\3\2\7\5"+
		"\2\b\2\2\t\6\2\6\2\2\7\4\2\t\t\2\7\6\2\7\7\2\t\b\2\t\4\2\t\5\2\t\f\2\t"+
		"\r\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}