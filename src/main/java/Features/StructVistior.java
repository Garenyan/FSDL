package Features;

import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by garen on 2019/3/12.
 */
public class StructVistior extends ASTVisitor{
    private List<VariableDeclarationStatement> variableDeclarationStatements = new ArrayList<VariableDeclarationStatement>();
    private List<ContinueStatement> continueStatements = new ArrayList<ContinueStatement>();
    private List<BreakStatement> breakStatements = new ArrayList<BreakStatement>();
    private List<ExpressionStatement> expressionStatements = new ArrayList<ExpressionStatement>();
    private List<ReturnStatement> returnStatements = new ArrayList<ReturnStatement>();

    private List<IfStatement> ifStatements = new ArrayList<IfStatement>();
    private List<ForStatement> forStatements = new ArrayList<ForStatement>();
    private List<WhileStatement> whileStatements = new ArrayList<WhileStatement>();
    private List<DoStatement> doStatements = new ArrayList<DoStatement>();
    private List<SwitchStatement> switchStatements = new ArrayList<SwitchStatement>();


    public List<BreakStatement> getBreakStatements() {
        return breakStatements;
    }

    public List<ContinueStatement> getContinueStatements() {
        return continueStatements;
    }

    public List<ExpressionStatement> getExpressionStatements() {
        return expressionStatements;
    }

    public List<DoStatement> getDoStatements() {
        return doStatements;
    }

    public List<ForStatement> getForStatements() {
        return forStatements;
    }

    public List<IfStatement> getIfStatements() {
        return ifStatements;
    }

    public List<VariableDeclarationStatement> getVariableDeclarationStatements() {
        return variableDeclarationStatements;
    }

    public List<SwitchStatement> getSwitchStatements() {
        return switchStatements;
    }

    public List<WhileStatement> getWhileStatements() {
        return whileStatements;
    }

    public List<ReturnStatement> getReturnStatements() {
        return returnStatements;
    }

    @Override
    public boolean visit(VariableDeclarationStatement variableDeclarationStatement)
    {
        variableDeclarationStatements.add(variableDeclarationStatement);
        return true;
    }

    @Override
    public boolean visit(ExpressionStatement expressionStatement)
    {
        expressionStatements.add(expressionStatement);
        return true;
    }

    @Override
    public boolean visit(ContinueStatement continueStatement)
    {
        continueStatements.add(continueStatement);
        return true;
    }

    @Override
    public boolean visit(BreakStatement breakStatement)
    {
        breakStatements.add(breakStatement);
        return true;
    }

    @Override
    public boolean visit(ForStatement forStatement)
    {
        forStatements.add(forStatement);
        return true;
    }

    @Override
    public boolean visit(WhileStatement whileStatement)
    {
        whileStatements.add(whileStatement);
        return true;
    }

    @Override
    public boolean visit(SwitchStatement switchStatement)
    {
        switchStatements.add(switchStatement);
        return true;
    }

    @Override
    public boolean visit(IfStatement ifStatement)
    {
        ifStatements.add(ifStatement);
        return true;
    }

    @Override
    public boolean visit(DoStatement doStatement)
    {
        doStatements.add(doStatement);
        return true;
    }

    @Override
    public boolean visit(ReturnStatement returnStatement)
    {
        returnStatements.add(returnStatement);
        return true;
    }
}
