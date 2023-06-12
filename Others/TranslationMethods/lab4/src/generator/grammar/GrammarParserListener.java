package generator.grammar;// Generated from /home/rytuo/projects/mt/lab4/src/GrammarParser.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GrammarParser}.
 */
public interface GrammarParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GrammarParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(GrammarParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(GrammarParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#rulesN}.
	 * @param ctx the parse tree
	 */
	void enterRulesN(GrammarParser.RulesNContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#rulesN}.
	 * @param ctx the parse tree
	 */
	void exitRulesN(GrammarParser.RulesNContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#rules}.
	 * @param ctx the parse tree
	 */
	void enterRules(GrammarParser.RulesContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#rules}.
	 * @param ctx the parse tree
	 */
	void exitRules(GrammarParser.RulesContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#reg}.
	 * @param ctx the parse tree
	 */
	void enterReg(GrammarParser.RegContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#reg}.
	 * @param ctx the parse tree
	 */
	void exitReg(GrammarParser.RegContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#q_text}.
	 * @param ctx the parse tree
	 */
	void enterQ_text(GrammarParser.Q_textContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#q_text}.
	 * @param ctx the parse tree
	 */
	void exitQ_text(GrammarParser.Q_textContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#singleRule}.
	 * @param ctx the parse tree
	 */
	void enterSingleRule(GrammarParser.SingleRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#singleRule}.
	 * @param ctx the parse tree
	 */
	void exitSingleRule(GrammarParser.SingleRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#code}.
	 * @param ctx the parse tree
	 */
	void enterCode(GrammarParser.CodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#code}.
	 * @param ctx the parse tree
	 */
	void exitCode(GrammarParser.CodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#fb_text}.
	 * @param ctx the parse tree
	 */
	void enterFb_text(GrammarParser.Fb_textContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#fb_text}.
	 * @param ctx the parse tree
	 */
	void exitFb_text(GrammarParser.Fb_textContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#nextRule}.
	 * @param ctx the parse tree
	 */
	void enterNextRule(GrammarParser.NextRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#nextRule}.
	 * @param ctx the parse tree
	 */
	void exitNextRule(GrammarParser.NextRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#tks}.
	 * @param ctx the parse tree
	 */
	void enterTks(GrammarParser.TksContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#tks}.
	 * @param ctx the parse tree
	 */
	void exitTks(GrammarParser.TksContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#params}.
	 * @param ctx the parse tree
	 */
	void enterParams(GrammarParser.ParamsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#params}.
	 * @param ctx the parse tree
	 */
	void exitParams(GrammarParser.ParamsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#sb_text}.
	 * @param ctx the parse tree
	 */
	void enterSb_text(GrammarParser.Sb_textContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#sb_text}.
	 * @param ctx the parse tree
	 */
	void exitSb_text(GrammarParser.Sb_textContext ctx);
}