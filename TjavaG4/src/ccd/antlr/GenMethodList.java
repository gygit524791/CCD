package ccd.antlr;

import java8.Java8BaseVisitor;
import java8.Java8Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class GenMethodList extends Java8BaseVisitor<Integer> {
    private List<Integer> methodNode = new ArrayList<Integer>();
    private List<Integer> ruleLine = new ArrayList<Integer>();//method 内规则在源码出现的行号

    @Override
    public Integer visit(ParseTree tree) {
        return tree.accept(this);
    }

    @Override
    public Integer visitChildren(RuleNode node) {
        int ruleIndex = node.getRuleContext().getRuleIndex();
        //rules filter
        List<Integer> rulesfilter = RulesFilter.rulesFilter();

        if(rulesfilter.contains(ruleIndex)){
            methodNode.add(ruleIndex);
        }
        Integer result = this.defaultResult();
        int n = node.getChildCount();

        for(int i = 0; i < n && this.shouldVisitNextChild(node, result); ++i) {
            ParseTree c = node.getChild(i);
            Integer childResult = c.accept(this);
            result = this.aggregateResult(result, childResult);
        }
        return result;
    }

    @Override
    public Integer visitTerminal(TerminalNode node) {
        //String tokenName = node.getText();//具体的token
        int line = node.getSymbol().getLine();
        ruleLine.add(line);
        return this.defaultResult();
    }

    public List<Integer> getMethodNode() { return methodNode; }
    public void setMethodNode(List<Integer> methodNode) {
        this.methodNode = methodNode;
    }
    public List<Integer> getRuleLine() {
        return ruleLine;
    }

    public void setRuleLine(List<Integer> ruleLine) {
        this.ruleLine = ruleLine;
    }
}
