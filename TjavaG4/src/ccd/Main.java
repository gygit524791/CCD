package ccd;

import ccd.antlr.GenAST;
import ccd.antlr.GenSequence;
import ccd.detection.SWAlgorithm;
import ccd.detection.TraceBack;
import ccd.model.Sequence;

import java.util.*;


public class Main {

    public static void main(String[] args) throws Exception {
        String path = "testsrc";
        //String path = "src/test/testfile"; //检测的文件包路径
        GenSequence.genericCodeSequence(path);//该句执行完后会得出一个文件包下所有java文件形成的stack<sequence>

        Stack<Sequence> sequenceStack = GenAST.sequenceStack;
        //System.out.println(sequenceStack.size());
        int threshold = 45;//阈值
        //printAllMethods(sequenceStack);//打印出所有的method

        executeCCD(sequenceStack,threshold);//执行sw,收集匹配对

        System.out.println("========================");
        System.out.println("========================");

        printResultSet();//打印结果
        System.out.println("END...");
    }

    public static void executeCCD(Stack<Sequence> sequenceStack,int threshold){//栈内元素比较
        Sequence topSequence = sequenceStack.peek();
        sequenceStack.pop();
        Iterator<Sequence> iterator = sequenceStack.iterator();
        while(iterator.hasNext()){
            SWAlgorithm.executeSW(topSequence,iterator.next(),threshold);
        }
        if(!sequenceStack.empty()){
            executeCCD(sequenceStack,threshold);
        }
    }

    public static void printAllMethods(Stack<Sequence> sequenceStack ){//printAllMethods
        System.out.println("ALL Methods: "+sequenceStack.size());
        Iterator<Sequence> iterator = sequenceStack.iterator();
        Sequence sequence;
        while(iterator.hasNext()){
            sequence = iterator.next();
            System.out.println(sequence.getFileName()+" "+sequence.getStartLine()+" "+sequence.getEndLine()+" | "+sequence.getBody());
        }

    }

    public static void printResultSet(){//输出结果
        List<TraceBack> tbList = SWAlgorithm.tbList;
        System.out.println("CCD RESULT: ");
        System.out.println("SET SIZE: "+tbList.size());
        for (int i = 0; i < tbList.size(); i++) {
            TraceBack traceBack = tbList.get(i);
            printResult(traceBack);
            System.out.println();
            System.out.println();
        }
    }

    //倒序打印出匹配整型list-测试用
    public static void printResult(TraceBack traceBack){
        List<Integer> s1 = traceBack.matchList.matchLS;
        List<Integer> s2 = traceBack.matchList.matchLT;

        System.out.print(traceBack.matchList.s_Sequence.getFileName()+":"+traceBack.matchList.s_Sequence.getStartLine()+":"+traceBack.matchList.s_Sequence.getEndLine()+" | ");
         /*
        for (int i = s1.size() - 1; i >= 0; i--) {
            int ch = s1.get(i);
            System.out.print(ch+" ");
        }
        */
        System.out.println();
        System.out.print(traceBack.matchList.t_Sequence.getFileName()+":"+traceBack.matchList.t_Sequence.getStartLine()+":"+traceBack.matchList.t_Sequence.getEndLine()+" | ");
        /*
        for (int i = s2.size() - 1; i >= 0; i--) {
            int ch = s2.get(i);
            System.out.print(ch+" ");
        }
        */
    }

}
