package ccd.antlr;


import ccd.model.Sequence;
import java8.Java8BaseVisitor;
import java8.Java8Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GenAST extends Java8BaseVisitor<Integer> {
    private String filename;//检测文件的文件名

    public static Stack<Sequence> sequenceStack = new Stack<Sequence>();

    @Override
    public Integer visitCompilationUnit(Java8Parser.CompilationUnitContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Integer visit(ParseTree tree) {
        return tree.accept(this);
    }

    @Override
    public Integer visitChildren(RuleNode node) {
        Integer result = this.defaultResult();
        int n = node.getChildCount();
        int ruleIndex = node.getRuleContext().getRuleIndex();
        GenMethodList gml;
        if(ruleIndex == Java8Parser.RULE_methodDeclaration){
            //node.getRuleContext().getText() = node.getText,打印出整个方法
            //System.out.println(node.getText());
            gml = new GenMethodList();
            //gml.visitChildren(node);//visit方式主要包含了rulenode 和 terminalnode两种(其实还有一种errornode),即遍历AST所有结点
            gml.visit(node);

            Sequence codeSequence = new Sequence();
            //codeSequence.setPackageName("");
            codeSequence.setFileName(filename);
            codeSequence.setStartLine(gml.getRuleLine().get(0));//该函数在源文件开始的第一行
            codeSequence.setEndLine(gml.getRuleLine().get(gml.getRuleLine().size()-1));//该函数在源文件的最后一行
            List<Integer> body = gml.getMethodNode();
            codeSequence.setBody(body);

            sequenceStack.add(codeSequence);
        }

        for(int i = 0; i < n && this.shouldVisitNextChild(node, result); ++i) {
            //遍历node的每一个子节点的子树-依次进行下去就相当于遍历整个AST(以node为根节点)
            //visitChildren方法调用accept方法,accept方法又调用visitChildren方法-叶子节点没有子节点,所以到叶子节点就终止这次遍历了
            //这种遍历方式相当于是深度优先遍历
            ParseTree c = node.getChild(i);//比如node有3个子树,则i表示0,1,2.这样相当于三次for,每一次遍历一个子树i
            Integer childResult = c.accept(this);
            result = this.aggregateResult(result, childResult);
        }
        return result;
    }

    @Override
    public Integer visitTerminal(TerminalNode node) {
        return this.defaultResult();
    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
}