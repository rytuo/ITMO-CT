// Generated from /home/rytuo/projects/mt/lab3/src/Prefix.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link PrefixParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface PrefixVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link PrefixParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(PrefixParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by {@link PrefixParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(PrefixParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link PrefixParser#ns}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNs(PrefixParser.NsContext ctx);
	/**
	 * Visit a parse tree produced by {@link PrefixParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt(PrefixParser.StmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PrefixParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(PrefixParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PrefixParser#val}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVal(PrefixParser.ValContext ctx);
}