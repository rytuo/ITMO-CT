// Generated from /home/rytuo/projects/mt/lab4/src/GrammarParser.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		GR=1, T=2, R=3, LINE_SEP=4, WS=5, COLON=6, Q=7, WS_T=8, Q_TEXT=9, SB=10, 
		FB=11, RULE_SEP=12, WS_R=13, SB_TEXT=14, FB_TEXT=15, CSB_SB=16, CFB_FB=17;
	public static final int
		RULE_start = 0, RULE_rulesN = 1, RULE_rules = 2, RULE_reg = 3, RULE_q_text = 4, 
		RULE_singleRule = 5, RULE_code = 6, RULE_fb_text = 7, RULE_nextRule = 8, 
		RULE_tks = 9, RULE_params = 10, RULE_sb_text = 11;
	private static String[] makeRuleNames() {
		return new String[] {
			"start", "rulesN", "rules", "reg", "q_text", "singleRule", "code", "fb_text", 
			"nextRule", "tks", "params", "sb_text"
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

	@Override
	public String getGrammarFileName() { return "GrammarParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class StartContext extends ParserRuleContext {
		public TerminalNode GR() { return getToken(GrammarParser.GR, 0); }
		public TerminalNode T() { return getToken(GrammarParser.T, 0); }
		public TerminalNode LINE_SEP() { return getToken(GrammarParser.LINE_SEP, 0); }
		public RulesNContext rulesN() {
			return getRuleContext(RulesNContext.class,0);
		}
		public TerminalNode EOF() { return getToken(GrammarParser.EOF, 0); }
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarParserListener ) ((GrammarParserListener)listener).enterStart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarParserListener ) ((GrammarParserListener)listener).exitStart(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarParserVisitor ) return ((GrammarParserVisitor<? extends T>)visitor).visitStart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_start);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			match(GR);
			setState(25);
			match(T);
			setState(26);
			match(LINE_SEP);
			setState(27);
			rulesN();
			setState(28);
			match(EOF);
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

	public static class RulesNContext extends ParserRuleContext {
		public RulesContext rules() {
			return getRuleContext(RulesContext.class,0);
		}
		public RulesNContext rulesN() {
			return getRuleContext(RulesNContext.class,0);
		}
		public RulesNContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rulesN; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarParserListener ) ((GrammarParserListener)listener).enterRulesN(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarParserListener ) ((GrammarParserListener)listener).exitRulesN(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarParserVisitor ) return ((GrammarParserVisitor<? extends T>)visitor).visitRulesN(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RulesNContext rulesN() throws RecognitionException {
		RulesNContext _localctx = new RulesNContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_rulesN);
		try {
			setState(34);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T:
			case R:
				enterOuterAlt(_localctx, 1);
				{
				setState(30);
				rules();
				setState(31);
				rulesN();
				}
				break;
			case EOF:
				enterOuterAlt(_localctx, 2);
				{
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

	public static class RulesContext extends ParserRuleContext {
		public TerminalNode T() { return getToken(GrammarParser.T, 0); }
		public TerminalNode COLON() { return getToken(GrammarParser.COLON, 0); }
		public RegContext reg() {
			return getRuleContext(RegContext.class,0);
		}
		public TerminalNode LINE_SEP() { return getToken(GrammarParser.LINE_SEP, 0); }
		public TerminalNode R() { return getToken(GrammarParser.R, 0); }
		public SingleRuleContext singleRule() {
			return getRuleContext(SingleRuleContext.class,0);
		}
		public NextRuleContext nextRule() {
			return getRuleContext(NextRuleContext.class,0);
		}
		public RulesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rules; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarParserListener ) ((GrammarParserListener)listener).enterRules(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarParserListener ) ((GrammarParserListener)listener).exitRules(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarParserVisitor ) return ((GrammarParserVisitor<? extends T>)visitor).visitRules(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RulesContext rules() throws RecognitionException {
		RulesContext _localctx = new RulesContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_rules);
		try {
			setState(47);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T:
				enterOuterAlt(_localctx, 1);
				{
				setState(36);
				match(T);
				setState(37);
				match(COLON);
				setState(38);
				reg();
				setState(39);
				match(LINE_SEP);
				}
				break;
			case R:
				enterOuterAlt(_localctx, 2);
				{
				setState(41);
				match(R);
				setState(42);
				match(COLON);
				setState(43);
				singleRule();
				setState(44);
				nextRule();
				setState(45);
				match(LINE_SEP);
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

	public static class RegContext extends ParserRuleContext {
		public List<TerminalNode> Q() { return getTokens(GrammarParser.Q); }
		public TerminalNode Q(int i) {
			return getToken(GrammarParser.Q, i);
		}
		public Q_textContext q_text() {
			return getRuleContext(Q_textContext.class,0);
		}
		public RegContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_reg; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarParserListener ) ((GrammarParserListener)listener).enterReg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarParserListener ) ((GrammarParserListener)listener).exitReg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarParserVisitor ) return ((GrammarParserVisitor<? extends T>)visitor).visitReg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RegContext reg() throws RecognitionException {
		RegContext _localctx = new RegContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_reg);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			match(Q);
			setState(50);
			q_text();
			setState(51);
			match(Q);
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

	public static class Q_textContext extends ParserRuleContext {
		public List<TerminalNode> Q_TEXT() { return getTokens(GrammarParser.Q_TEXT); }
		public TerminalNode Q_TEXT(int i) {
			return getToken(GrammarParser.Q_TEXT, i);
		}
		public Q_textContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_q_text; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarParserListener ) ((GrammarParserListener)listener).enterQ_text(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarParserListener ) ((GrammarParserListener)listener).exitQ_text(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarParserVisitor ) return ((GrammarParserVisitor<? extends T>)visitor).visitQ_text(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Q_textContext q_text() throws RecognitionException {
		Q_textContext _localctx = new Q_textContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_q_text);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(53);
				match(Q_TEXT);
				}
				}
				setState(56); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==Q_TEXT );
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

	public static class SingleRuleContext extends ParserRuleContext {
		public TksContext tks() {
			return getRuleContext(TksContext.class,0);
		}
		public CodeContext code() {
			return getRuleContext(CodeContext.class,0);
		}
		public SingleRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_singleRule; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarParserListener ) ((GrammarParserListener)listener).enterSingleRule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarParserListener ) ((GrammarParserListener)listener).exitSingleRule(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarParserVisitor ) return ((GrammarParserVisitor<? extends T>)visitor).visitSingleRule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SingleRuleContext singleRule() throws RecognitionException {
		SingleRuleContext _localctx = new SingleRuleContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_singleRule);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			tks();
			setState(59);
			code();
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

	public static class CodeContext extends ParserRuleContext {
		public List<TerminalNode> FB() { return getTokens(GrammarParser.FB); }
		public TerminalNode FB(int i) {
			return getToken(GrammarParser.FB, i);
		}
		public Fb_textContext fb_text() {
			return getRuleContext(Fb_textContext.class,0);
		}
		public CodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_code; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarParserListener ) ((GrammarParserListener)listener).enterCode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarParserListener ) ((GrammarParserListener)listener).exitCode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarParserVisitor ) return ((GrammarParserVisitor<? extends T>)visitor).visitCode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CodeContext code() throws RecognitionException {
		CodeContext _localctx = new CodeContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_code);
		try {
			setState(66);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FB:
				enterOuterAlt(_localctx, 1);
				{
				setState(61);
				match(FB);
				setState(62);
				fb_text();
				setState(63);
				match(FB);
				}
				break;
			case LINE_SEP:
			case RULE_SEP:
				enterOuterAlt(_localctx, 2);
				{
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

	public static class Fb_textContext extends ParserRuleContext {
		public List<TerminalNode> FB_TEXT() { return getTokens(GrammarParser.FB_TEXT); }
		public TerminalNode FB_TEXT(int i) {
			return getToken(GrammarParser.FB_TEXT, i);
		}
		public List<TerminalNode> FB() { return getTokens(GrammarParser.FB); }
		public TerminalNode FB(int i) {
			return getToken(GrammarParser.FB, i);
		}
		public Fb_textContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fb_text; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarParserListener ) ((GrammarParserListener)listener).enterFb_text(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarParserListener ) ((GrammarParserListener)listener).exitFb_text(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarParserVisitor ) return ((GrammarParserVisitor<? extends T>)visitor).visitFb_text(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Fb_textContext fb_text() throws RecognitionException {
		Fb_textContext _localctx = new Fb_textContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_fb_text);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(72); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					setState(72);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case FB_TEXT:
						{
						setState(68);
						match(FB_TEXT);
						}
						break;
					case FB:
						{
						setState(69);
						match(FB);
						setState(70);
						match(FB_TEXT);
						setState(71);
						match(FB);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(74); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
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

	public static class NextRuleContext extends ParserRuleContext {
		public TerminalNode RULE_SEP() { return getToken(GrammarParser.RULE_SEP, 0); }
		public SingleRuleContext singleRule() {
			return getRuleContext(SingleRuleContext.class,0);
		}
		public NextRuleContext nextRule() {
			return getRuleContext(NextRuleContext.class,0);
		}
		public NextRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nextRule; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarParserListener ) ((GrammarParserListener)listener).enterNextRule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarParserListener ) ((GrammarParserListener)listener).exitNextRule(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarParserVisitor ) return ((GrammarParserVisitor<? extends T>)visitor).visitNextRule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NextRuleContext nextRule() throws RecognitionException {
		NextRuleContext _localctx = new NextRuleContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_nextRule);
		try {
			setState(81);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case RULE_SEP:
				enterOuterAlt(_localctx, 1);
				{
				setState(76);
				match(RULE_SEP);
				setState(77);
				singleRule();
				setState(78);
				nextRule();
				}
				break;
			case LINE_SEP:
				enterOuterAlt(_localctx, 2);
				{
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

	public static class TksContext extends ParserRuleContext {
		public TerminalNode T() { return getToken(GrammarParser.T, 0); }
		public TksContext tks() {
			return getRuleContext(TksContext.class,0);
		}
		public TerminalNode R() { return getToken(GrammarParser.R, 0); }
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
		}
		public TksContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tks; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarParserListener ) ((GrammarParserListener)listener).enterTks(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarParserListener ) ((GrammarParserListener)listener).exitTks(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarParserVisitor ) return ((GrammarParserVisitor<? extends T>)visitor).visitTks(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TksContext tks() throws RecognitionException {
		TksContext _localctx = new TksContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_tks);
		try {
			setState(90);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T:
				enterOuterAlt(_localctx, 1);
				{
				setState(83);
				match(T);
				setState(84);
				tks();
				}
				break;
			case R:
				enterOuterAlt(_localctx, 2);
				{
				setState(85);
				match(R);
				setState(86);
				params();
				setState(87);
				tks();
				}
				break;
			case LINE_SEP:
			case FB:
			case RULE_SEP:
				enterOuterAlt(_localctx, 3);
				{
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

	public static class ParamsContext extends ParserRuleContext {
		public List<TerminalNode> SB() { return getTokens(GrammarParser.SB); }
		public TerminalNode SB(int i) {
			return getToken(GrammarParser.SB, i);
		}
		public Sb_textContext sb_text() {
			return getRuleContext(Sb_textContext.class,0);
		}
		public ParamsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_params; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarParserListener ) ((GrammarParserListener)listener).enterParams(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarParserListener ) ((GrammarParserListener)listener).exitParams(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarParserVisitor ) return ((GrammarParserVisitor<? extends T>)visitor).visitParams(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamsContext params() throws RecognitionException {
		ParamsContext _localctx = new ParamsContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_params);
		try {
			setState(97);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SB:
				enterOuterAlt(_localctx, 1);
				{
				setState(92);
				match(SB);
				setState(93);
				sb_text();
				setState(94);
				match(SB);
				}
				break;
			case T:
			case R:
			case LINE_SEP:
			case FB:
			case RULE_SEP:
				enterOuterAlt(_localctx, 2);
				{
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

	public static class Sb_textContext extends ParserRuleContext {
		public List<TerminalNode> SB_TEXT() { return getTokens(GrammarParser.SB_TEXT); }
		public TerminalNode SB_TEXT(int i) {
			return getToken(GrammarParser.SB_TEXT, i);
		}
		public List<TerminalNode> SB() { return getTokens(GrammarParser.SB); }
		public TerminalNode SB(int i) {
			return getToken(GrammarParser.SB, i);
		}
		public Sb_textContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sb_text; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarParserListener ) ((GrammarParserListener)listener).enterSb_text(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarParserListener ) ((GrammarParserListener)listener).exitSb_text(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarParserVisitor ) return ((GrammarParserVisitor<? extends T>)visitor).visitSb_text(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Sb_textContext sb_text() throws RecognitionException {
		Sb_textContext _localctx = new Sb_textContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_sb_text);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(103); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					setState(103);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case SB_TEXT:
						{
						setState(99);
						match(SB_TEXT);
						}
						break;
					case SB:
						{
						setState(100);
						match(SB);
						setState(101);
						match(SB_TEXT);
						setState(102);
						match(SB);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(105); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\23n\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\4\r\t\r\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\5\3%\n\3\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4\62\n\4\3\5\3\5\3\5\3\5\3\6"+
		"\6\69\n\6\r\6\16\6:\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\5\bE\n\b\3\t\3\t\3"+
		"\t\3\t\6\tK\n\t\r\t\16\tL\3\n\3\n\3\n\3\n\3\n\5\nT\n\n\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\5\13]\n\13\3\f\3\f\3\f\3\f\3\f\5\fd\n\f\3\r\3\r\3"+
		"\r\3\r\6\rj\n\r\r\r\16\rk\3\r\2\2\16\2\4\6\b\n\f\16\20\22\24\26\30\2\2"+
		"\2m\2\32\3\2\2\2\4$\3\2\2\2\6\61\3\2\2\2\b\63\3\2\2\2\n8\3\2\2\2\f<\3"+
		"\2\2\2\16D\3\2\2\2\20J\3\2\2\2\22S\3\2\2\2\24\\\3\2\2\2\26c\3\2\2\2\30"+
		"i\3\2\2\2\32\33\7\3\2\2\33\34\7\4\2\2\34\35\7\6\2\2\35\36\5\4\3\2\36\37"+
		"\7\2\2\3\37\3\3\2\2\2 !\5\6\4\2!\"\5\4\3\2\"%\3\2\2\2#%\3\2\2\2$ \3\2"+
		"\2\2$#\3\2\2\2%\5\3\2\2\2&\'\7\4\2\2\'(\7\b\2\2()\5\b\5\2)*\7\6\2\2*\62"+
		"\3\2\2\2+,\7\5\2\2,-\7\b\2\2-.\5\f\7\2./\5\22\n\2/\60\7\6\2\2\60\62\3"+
		"\2\2\2\61&\3\2\2\2\61+\3\2\2\2\62\7\3\2\2\2\63\64\7\t\2\2\64\65\5\n\6"+
		"\2\65\66\7\t\2\2\66\t\3\2\2\2\679\7\13\2\28\67\3\2\2\29:\3\2\2\2:8\3\2"+
		"\2\2:;\3\2\2\2;\13\3\2\2\2<=\5\24\13\2=>\5\16\b\2>\r\3\2\2\2?@\7\r\2\2"+
		"@A\5\20\t\2AB\7\r\2\2BE\3\2\2\2CE\3\2\2\2D?\3\2\2\2DC\3\2\2\2E\17\3\2"+
		"\2\2FK\7\21\2\2GH\7\r\2\2HI\7\21\2\2IK\7\r\2\2JF\3\2\2\2JG\3\2\2\2KL\3"+
		"\2\2\2LJ\3\2\2\2LM\3\2\2\2M\21\3\2\2\2NO\7\16\2\2OP\5\f\7\2PQ\5\22\n\2"+
		"QT\3\2\2\2RT\3\2\2\2SN\3\2\2\2SR\3\2\2\2T\23\3\2\2\2UV\7\4\2\2V]\5\24"+
		"\13\2WX\7\5\2\2XY\5\26\f\2YZ\5\24\13\2Z]\3\2\2\2[]\3\2\2\2\\U\3\2\2\2"+
		"\\W\3\2\2\2\\[\3\2\2\2]\25\3\2\2\2^_\7\f\2\2_`\5\30\r\2`a\7\f\2\2ad\3"+
		"\2\2\2bd\3\2\2\2c^\3\2\2\2cb\3\2\2\2d\27\3\2\2\2ej\7\20\2\2fg\7\f\2\2"+
		"gh\7\20\2\2hj\7\f\2\2ie\3\2\2\2if\3\2\2\2jk\3\2\2\2ki\3\2\2\2kl\3\2\2"+
		"\2l\31\3\2\2\2\r$\61:DJLS\\cik";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}