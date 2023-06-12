// Generated from /home/rytuo/projects/mt/lab3/src/Prefix.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PrefixParser}.
 */
public interface PrefixListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link PrefixParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(PrefixParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(PrefixParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(PrefixParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(PrefixParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixParser#ns}.
	 * @param ctx the parse tree
	 */
	void enterNs(PrefixParser.NsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixParser#ns}.
	 * @param ctx the parse tree
	 */
	void exitNs(PrefixParser.NsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmt(PrefixParser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmt(PrefixParser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(PrefixParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(PrefixParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixParser#val}.
	 * @param ctx the parse tree
	 */
	void enterVal(PrefixParser.ValContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixParser#val}.
	 * @param ctx the parse tree
	 */
	void exitVal(PrefixParser.ValContext ctx);
}