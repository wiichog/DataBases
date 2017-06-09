// Generated from Gramatica.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GramaticaParser}.
 */
public interface GramaticaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(GramaticaParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(GramaticaParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#int_literal}.
	 * @param ctx the parse tree
	 */
	void enterInt_literal(GramaticaParser.Int_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#int_literal}.
	 * @param ctx the parse tree
	 */
	void exitInt_literal(GramaticaParser.Int_literalContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#float_literal}.
	 * @param ctx the parse tree
	 */
	void enterFloat_literal(GramaticaParser.Float_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#float_literal}.
	 * @param ctx the parse tree
	 */
	void exitFloat_literal(GramaticaParser.Float_literalContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#char_literal}.
	 * @param ctx the parse tree
	 */
	void enterChar_literal(GramaticaParser.Char_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#char_literal}.
	 * @param ctx the parse tree
	 */
	void exitChar_literal(GramaticaParser.Char_literalContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#date_literal}.
	 * @param ctx the parse tree
	 */
	void enterDate_literal(GramaticaParser.Date_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#date_literal}.
	 * @param ctx the parse tree
	 */
	void exitDate_literal(GramaticaParser.Date_literalContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#programa}.
	 * @param ctx the parse tree
	 */
	void enterPrograma(GramaticaParser.ProgramaContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#programa}.
	 * @param ctx the parse tree
	 */
	void exitPrograma(GramaticaParser.ProgramaContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#database}.
	 * @param ctx the parse tree
	 */
	void enterDatabase(GramaticaParser.DatabaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#database}.
	 * @param ctx the parse tree
	 */
	void exitDatabase(GramaticaParser.DatabaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#createDatabase}.
	 * @param ctx the parse tree
	 */
	void enterCreateDatabase(GramaticaParser.CreateDatabaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#createDatabase}.
	 * @param ctx the parse tree
	 */
	void exitCreateDatabase(GramaticaParser.CreateDatabaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#alterDatabase}.
	 * @param ctx the parse tree
	 */
	void enterAlterDatabase(GramaticaParser.AlterDatabaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#alterDatabase}.
	 * @param ctx the parse tree
	 */
	void exitAlterDatabase(GramaticaParser.AlterDatabaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#dropDatabase}.
	 * @param ctx the parse tree
	 */
	void enterDropDatabase(GramaticaParser.DropDatabaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#dropDatabase}.
	 * @param ctx the parse tree
	 */
	void exitDropDatabase(GramaticaParser.DropDatabaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#showDatabase}.
	 * @param ctx the parse tree
	 */
	void enterShowDatabase(GramaticaParser.ShowDatabaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#showDatabase}.
	 * @param ctx the parse tree
	 */
	void exitShowDatabase(GramaticaParser.ShowDatabaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#useDatabase}.
	 * @param ctx the parse tree
	 */
	void enterUseDatabase(GramaticaParser.UseDatabaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#useDatabase}.
	 * @param ctx the parse tree
	 */
	void exitUseDatabase(GramaticaParser.UseDatabaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#tipo}.
	 * @param ctx the parse tree
	 */
	void enterTipo(GramaticaParser.TipoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#tipo}.
	 * @param ctx the parse tree
	 */
	void exitTipo(GramaticaParser.TipoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#createTable}.
	 * @param ctx the parse tree
	 */
	void enterCreateTable(GramaticaParser.CreateTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#createTable}.
	 * @param ctx the parse tree
	 */
	void exitCreateTable(GramaticaParser.CreateTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#columna}.
	 * @param ctx the parse tree
	 */
	void enterColumna(GramaticaParser.ColumnaContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#columna}.
	 * @param ctx the parse tree
	 */
	void exitColumna(GramaticaParser.ColumnaContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#constraint}.
	 * @param ctx the parse tree
	 */
	void enterConstraint(GramaticaParser.ConstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#constraint}.
	 * @param ctx the parse tree
	 */
	void exitConstraint(GramaticaParser.ConstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#primaryKey}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryKey(GramaticaParser.PrimaryKeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#primaryKey}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryKey(GramaticaParser.PrimaryKeyContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#foreignKey}.
	 * @param ctx the parse tree
	 */
	void enterForeignKey(GramaticaParser.ForeignKeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#foreignKey}.
	 * @param ctx the parse tree
	 */
	void exitForeignKey(GramaticaParser.ForeignKeyContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#check}.
	 * @param ctx the parse tree
	 */
	void enterCheck(GramaticaParser.CheckContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#check}.
	 * @param ctx the parse tree
	 */
	void exitCheck(GramaticaParser.CheckContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExp(GramaticaParser.ExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExp(GramaticaParser.ExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#location}.
	 * @param ctx the parse tree
	 */
	void enterLocation(GramaticaParser.LocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#location}.
	 * @param ctx the parse tree
	 */
	void exitLocation(GramaticaParser.LocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#operacion}.
	 * @param ctx the parse tree
	 */
	void enterOperacion(GramaticaParser.OperacionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#operacion}.
	 * @param ctx the parse tree
	 */
	void exitOperacion(GramaticaParser.OperacionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterCond(GramaticaParser.CondContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitCond(GramaticaParser.CondContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#rel}.
	 * @param ctx the parse tree
	 */
	void enterRel(GramaticaParser.RelContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#rel}.
	 * @param ctx the parse tree
	 */
	void exitRel(GramaticaParser.RelContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#notOp}.
	 * @param ctx the parse tree
	 */
	void enterNotOp(GramaticaParser.NotOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#notOp}.
	 * @param ctx the parse tree
	 */
	void exitNotOp(GramaticaParser.NotOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#alterTable}.
	 * @param ctx the parse tree
	 */
	void enterAlterTable(GramaticaParser.AlterTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#alterTable}.
	 * @param ctx the parse tree
	 */
	void exitAlterTable(GramaticaParser.AlterTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#action}.
	 * @param ctx the parse tree
	 */
	void enterAction(GramaticaParser.ActionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#action}.
	 * @param ctx the parse tree
	 */
	void exitAction(GramaticaParser.ActionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#addColumn}.
	 * @param ctx the parse tree
	 */
	void enterAddColumn(GramaticaParser.AddColumnContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#addColumn}.
	 * @param ctx the parse tree
	 */
	void exitAddColumn(GramaticaParser.AddColumnContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#addConstraint}.
	 * @param ctx the parse tree
	 */
	void enterAddConstraint(GramaticaParser.AddConstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#addConstraint}.
	 * @param ctx the parse tree
	 */
	void exitAddConstraint(GramaticaParser.AddConstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#dropColumn}.
	 * @param ctx the parse tree
	 */
	void enterDropColumn(GramaticaParser.DropColumnContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#dropColumn}.
	 * @param ctx the parse tree
	 */
	void exitDropColumn(GramaticaParser.DropColumnContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#dropConstraint}.
	 * @param ctx the parse tree
	 */
	void enterDropConstraint(GramaticaParser.DropConstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#dropConstraint}.
	 * @param ctx the parse tree
	 */
	void exitDropConstraint(GramaticaParser.DropConstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#dropTable}.
	 * @param ctx the parse tree
	 */
	void enterDropTable(GramaticaParser.DropTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#dropTable}.
	 * @param ctx the parse tree
	 */
	void exitDropTable(GramaticaParser.DropTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#showTables}.
	 * @param ctx the parse tree
	 */
	void enterShowTables(GramaticaParser.ShowTablesContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#showTables}.
	 * @param ctx the parse tree
	 */
	void exitShowTables(GramaticaParser.ShowTablesContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#showColumns}.
	 * @param ctx the parse tree
	 */
	void enterShowColumns(GramaticaParser.ShowColumnsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#showColumns}.
	 * @param ctx the parse tree
	 */
	void exitShowColumns(GramaticaParser.ShowColumnsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#insertInto}.
	 * @param ctx the parse tree
	 */
	void enterInsertInto(GramaticaParser.InsertIntoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#insertInto}.
	 * @param ctx the parse tree
	 */
	void exitInsertInto(GramaticaParser.InsertIntoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#updateSet}.
	 * @param ctx the parse tree
	 */
	void enterUpdateSet(GramaticaParser.UpdateSetContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#updateSet}.
	 * @param ctx the parse tree
	 */
	void exitUpdateSet(GramaticaParser.UpdateSetContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#updateColumas}.
	 * @param ctx the parse tree
	 */
	void enterUpdateColumas(GramaticaParser.UpdateColumasContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#updateColumas}.
	 * @param ctx the parse tree
	 */
	void exitUpdateColumas(GramaticaParser.UpdateColumasContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#cambio}.
	 * @param ctx the parse tree
	 */
	void enterCambio(GramaticaParser.CambioContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#cambio}.
	 * @param ctx the parse tree
	 */
	void exitCambio(GramaticaParser.CambioContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#deleteFrom}.
	 * @param ctx the parse tree
	 */
	void enterDeleteFrom(GramaticaParser.DeleteFromContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#deleteFrom}.
	 * @param ctx the parse tree
	 */
	void exitDeleteFrom(GramaticaParser.DeleteFromContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#selectFrom}.
	 * @param ctx the parse tree
	 */
	void enterSelectFrom(GramaticaParser.SelectFromContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#selectFrom}.
	 * @param ctx the parse tree
	 */
	void exitSelectFrom(GramaticaParser.SelectFromContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#orden}.
	 * @param ctx the parse tree
	 */
	void enterOrden(GramaticaParser.OrdenContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#orden}.
	 * @param ctx the parse tree
	 */
	void exitOrden(GramaticaParser.OrdenContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#colOrder}.
	 * @param ctx the parse tree
	 */
	void enterColOrder(GramaticaParser.ColOrderContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#colOrder}.
	 * @param ctx the parse tree
	 */
	void exitColOrder(GramaticaParser.ColOrderContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#cols}.
	 * @param ctx the parse tree
	 */
	void enterCols(GramaticaParser.ColsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#cols}.
	 * @param ctx the parse tree
	 */
	void exitCols(GramaticaParser.ColsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#tabs}.
	 * @param ctx the parse tree
	 */
	void enterTabs(GramaticaParser.TabsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#tabs}.
	 * @param ctx the parse tree
	 */
	void exitTabs(GramaticaParser.TabsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#rel_op}.
	 * @param ctx the parse tree
	 */
	void enterRel_op(GramaticaParser.Rel_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#rel_op}.
	 * @param ctx the parse tree
	 */
	void exitRel_op(GramaticaParser.Rel_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#cond_op}.
	 * @param ctx the parse tree
	 */
	void enterCond_op(GramaticaParser.Cond_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#cond_op}.
	 * @param ctx the parse tree
	 */
	void exitCond_op(GramaticaParser.Cond_opContext ctx);
}