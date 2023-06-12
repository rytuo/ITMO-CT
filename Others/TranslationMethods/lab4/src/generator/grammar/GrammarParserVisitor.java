package generator.grammar;// Generated from /home/rytuo/projects/mt/lab4/src/GrammarParser.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GrammarParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GrammarParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(GrammarParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#rulesN}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRulesN(GrammarParser.RulesNContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#rules}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRules(GrammarParser.RulesContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#reg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReg(GrammarParser.RegContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#q_text}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQ_text(GrammarParser.Q_textContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#singleRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleRule(GrammarParser.SingleRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#code}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCode(GrammarParser.CodeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#fb_text}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFb_text(GrammarParser.Fb_textContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#nextRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNextRule(GrammarParser.NextRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#tks}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTks(GrammarParser.TksContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#params}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParams(GrammarParser.ParamsContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#sb_text}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSb_text(GrammarParser.Sb_textContext ctx);
}