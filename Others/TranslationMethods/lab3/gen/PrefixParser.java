// Generated from /home/rytuo/projects/mt/lab3/src/Prefix.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PrefixParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, N=17, 
		WS=18, L=19, KEY=20, VAR=21;
	public static final int
		RULE_start = 0, RULE_prog = 1, RULE_ns = 2, RULE_stmt = 3, RULE_expr = 4, 
		RULE_val = 5;
	private static String[] makeRuleNames() {
		return new String[] {
			"start", "prog", "ns", "stmt", "expr", "val"
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

	@Override
	public String getGrammarFileName() { return "Prefix.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public PrefixParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class StartContext extends ParserRuleContext {
		public ProgContext prog() {
			return getRuleContext(ProgContext.class,0);
		}
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixListener ) ((PrefixListener)listener).enterStart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixListener ) ((PrefixListener)listener).exitStart(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PrefixVisitor ) return ((PrefixVisitor<? extends T>)visitor).visitStart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_start);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(12);
			prog();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProgContext extends ParserRuleContext {
		public NsContext ns;
		public NsContext ns() {
			return getRuleContext(NsContext.class,0);
		}
		public TerminalNode EOF() { return getToken(PrefixParser.EOF, 0); }
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixListener ) ((PrefixListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixListener ) ((PrefixListener)listener).exitProg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PrefixVisitor ) return ((PrefixVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_prog);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(14);
			((ProgContext)_localctx).ns = ns();
			setState(15);
			match(EOF);
			 System.out.println("begin\n" + ((ProgContext)_localctx).ns.res + "end."); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NsContext extends ParserRuleContext {
		public String res;
		public StmtContext stmt;
		public NsContext ns;
		public StmtContext stmt() {
			return getRuleContext(StmtContext.class,0);
		}
		public NsContext ns() {
			return getRuleContext(NsContext.class,0);
		}
		public NsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ns; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixListener ) ((PrefixListener)listener).enterNs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixListener ) ((PrefixListener)listener).exitNs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PrefixVisitor ) return ((PrefixVisitor<? extends T>)visitor).visitNs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NsContext ns() throws RecognitionException {
		NsContext _localctx = new NsContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_ns);
		try {
			setState(23);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
			case T__1:
			case T__3:
			case T__4:
			case T__5:
				enterOuterAlt(_localctx, 1);
				{
				setState(18);
				((NsContext)_localctx).stmt = stmt();
				setState(19);
				((NsContext)_localctx).ns = ns();
				 ((NsContext)_localctx).res =  ((NsContext)_localctx).stmt.res + ";\n" + ((NsContext)_localctx).ns.res; 
				}
				break;
			case EOF:
			case T__2:
				enterOuterAlt(_localctx, 2);
				{
				 ((NsContext)_localctx).res =  ""; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StmtContext extends ParserRuleContext {
		public String res;
		public ExprContext expr;
		public StmtContext s1;
		public StmtContext s2;
		public NsContext ns;
		public Token VAR;
		public StmtContext stmt;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public NsContext ns() {
			return getRuleContext(NsContext.class,0);
		}
		public TerminalNode VAR() { return getToken(PrefixParser.VAR, 0); }
		public StmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixListener ) ((PrefixListener)listener).enterStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixListener ) ((PrefixListener)listener).exitStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PrefixVisitor ) return ((PrefixVisitor<? extends T>)visitor).visitStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StmtContext stmt() throws RecognitionException {
		StmtContext _localctx = new StmtContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_stmt);
		try {
			setState(52);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				setState(25);
				match(T__0);
				setState(26);
				((StmtContext)_localctx).expr = expr();
				setState(27);
				((StmtContext)_localctx).s1 = stmt();
				setState(29);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
				case 1:
					{
					setState(28);
					((StmtContext)_localctx).s2 = stmt();
					}
					break;
				}
				 ((StmtContext)_localctx).res =  "if (" + ((StmtContext)_localctx).expr.res + ") then " + ((StmtContext)_localctx).s1.res +
				        ((((StmtContext)_localctx).s2!=null?_input.getText(((StmtContext)_localctx).s2.start,((StmtContext)_localctx).s2.stop):null) != null ? ("\nelse " + ((StmtContext)_localctx).s2.res) : ""); 
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 2);
				{
				setState(33);
				match(T__1);
				setState(34);
				((StmtContext)_localctx).ns = ns();
				setState(35);
				match(T__2);
				 ((StmtContext)_localctx).res =  "begin\n" + ((StmtContext)_localctx).ns.res + "end"; 
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 3);
				{
				setState(38);
				match(T__3);
				setState(39);
				((StmtContext)_localctx).expr = expr();
				 ((StmtContext)_localctx).res =  "writeln(" + ((StmtContext)_localctx).expr.res + ")"; 
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 4);
				{
				setState(42);
				match(T__4);
				setState(43);
				((StmtContext)_localctx).VAR = match(VAR);
				setState(44);
				((StmtContext)_localctx).expr = expr();
				 ((StmtContext)_localctx).res =  (((StmtContext)_localctx).VAR!=null?((StmtContext)_localctx).VAR.getText():null) + " = " + ((StmtContext)_localctx).expr.res; 
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 5);
				{
				setState(47);
				match(T__5);
				setState(48);
				((StmtContext)_localctx).expr = expr();
				setState(49);
				((StmtContext)_localctx).stmt = stmt();
				 ((StmtContext)_localctx).res =  "while " + ((StmtContext)_localctx).expr.res + " do\n" + ((StmtContext)_localctx).stmt.res; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public String res;
		public Token op;
		public ExprContext e1;
		public ExprContext e2;
		public ExprContext expr;
		public ValContext val;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ValContext val() {
			return getRuleContext(ValContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixListener ) ((PrefixListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixListener ) ((PrefixListener)listener).exitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PrefixVisitor ) return ((PrefixVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_expr);
		int _la;
		try {
			setState(66);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
			case T__7:
			case T__8:
			case T__9:
			case T__10:
			case T__11:
			case T__12:
			case T__13:
			case T__14:
				enterOuterAlt(_localctx, 1);
				{
				setState(54);
				((ExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14))) != 0)) ) {
					((ExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(55);
				((ExprContext)_localctx).e1 = expr();
				setState(56);
				((ExprContext)_localctx).e2 = expr();
				 ((ExprContext)_localctx).res =  "(" + ((ExprContext)_localctx).e1.res + " " + (((ExprContext)_localctx).op!=null?((ExprContext)_localctx).op.getText():null) + " " + ((ExprContext)_localctx).e2.res + ")"; 
				}
				break;
			case T__15:
				enterOuterAlt(_localctx, 2);
				{
				setState(59);
				match(T__15);
				setState(60);
				((ExprContext)_localctx).expr = expr();
				 ((ExprContext)_localctx).res =  "(not " + ((ExprContext)_localctx).expr.res + ")"; 
				}
				break;
			case N:
			case L:
			case VAR:
				enterOuterAlt(_localctx, 3);
				{
				setState(63);
				((ExprContext)_localctx).val = val();
				 ((ExprContext)_localctx).res =  (((ExprContext)_localctx).val!=null?_input.getText(((ExprContext)_localctx).val.start,((ExprContext)_localctx).val.stop):null); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValContext extends ParserRuleContext {
		public TerminalNode N() { return getToken(PrefixParser.N, 0); }
		public TerminalNode L() { return getToken(PrefixParser.L, 0); }
		public TerminalNode VAR() { return getToken(PrefixParser.VAR, 0); }
		public ValContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_val; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixListener ) ((PrefixListener)listener).enterVal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixListener ) ((PrefixListener)listener).exitVal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PrefixVisitor ) return ((PrefixVisitor<? extends T>)visitor).visitVal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValContext val() throws RecognitionException {
		ValContext _localctx = new ValContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_val);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << N) | (1L << L) | (1L << VAR))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\27I\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4"+
		"\3\4\3\4\5\4\32\n\4\3\5\3\5\3\5\3\5\5\5 \n\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5\67\n\5"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6E\n\6\3\7\3\7\3\7"+
		"\2\2\b\2\4\6\b\n\f\2\4\3\2\t\21\5\2\23\23\25\25\27\27\2J\2\16\3\2\2\2"+
		"\4\20\3\2\2\2\6\31\3\2\2\2\b\66\3\2\2\2\nD\3\2\2\2\fF\3\2\2\2\16\17\5"+
		"\4\3\2\17\3\3\2\2\2\20\21\5\6\4\2\21\22\7\2\2\3\22\23\b\3\1\2\23\5\3\2"+
		"\2\2\24\25\5\b\5\2\25\26\5\6\4\2\26\27\b\4\1\2\27\32\3\2\2\2\30\32\b\4"+
		"\1\2\31\24\3\2\2\2\31\30\3\2\2\2\32\7\3\2\2\2\33\34\7\3\2\2\34\35\5\n"+
		"\6\2\35\37\5\b\5\2\36 \5\b\5\2\37\36\3\2\2\2\37 \3\2\2\2 !\3\2\2\2!\""+
		"\b\5\1\2\"\67\3\2\2\2#$\7\4\2\2$%\5\6\4\2%&\7\5\2\2&\'\b\5\1\2\'\67\3"+
		"\2\2\2()\7\6\2\2)*\5\n\6\2*+\b\5\1\2+\67\3\2\2\2,-\7\7\2\2-.\7\27\2\2"+
		"./\5\n\6\2/\60\b\5\1\2\60\67\3\2\2\2\61\62\7\b\2\2\62\63\5\n\6\2\63\64"+
		"\5\b\5\2\64\65\b\5\1\2\65\67\3\2\2\2\66\33\3\2\2\2\66#\3\2\2\2\66(\3\2"+
		"\2\2\66,\3\2\2\2\66\61\3\2\2\2\67\t\3\2\2\289\t\2\2\29:\5\n\6\2:;\5\n"+
		"\6\2;<\b\6\1\2<E\3\2\2\2=>\7\22\2\2>?\5\n\6\2?@\b\6\1\2@E\3\2\2\2AB\5"+
		"\f\7\2BC\b\6\1\2CE\3\2\2\2D8\3\2\2\2D=\3\2\2\2DA\3\2\2\2E\13\3\2\2\2F"+
		"G\t\3\2\2G\r\3\2\2\2\6\31\37\66D";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}