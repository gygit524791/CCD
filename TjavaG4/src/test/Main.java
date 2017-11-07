package test;

import ccd.antlr.GenAST;
import ccd.model.Body;
import ccd.model.Head;
import ccd.util.GetFoldFileNames;
import java8.Java8Lexer;
import java8.Java8Parser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Main {
    public static HashMap<String,List<Integer>> codeSequence = new HashMap<String,List<Integer>>();//整个检测的项目只有一个codeSeq
    //主测试方法
    public static void main(String[] args) throws Exception {
        String path = "src/test/testfile"; // 文件包路径
        List<String> fileNames = GetFoldFileNames.getFileName(path);//获取文件包下的所有文件名,之后做个迭代,测试所有文件
        for(int i = 0; i < fileNames.size(); i++){
            String TFileName = fileNames.get(i);//文件名
            FileInputStream fis = new FileInputStream(path+"/"+fileNames.get(i));
            ANTLRInputStream input = new ANTLRInputStream(fis);
            Java8Lexer lexer = new Java8Lexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            Java8Parser parser = new Java8Parser(tokens);
            ParseTree tree = parser.compilationUnit();

            GenAST genAST = new GenAST();
            genAST.setFilename(TFileName);
            genAST.visit(tree);
        }

        for (Map.Entry<String,List<Integer>> entry :codeSequence.entrySet()){//打印hash表
            System.out.print( entry.getKey()+" | ");
            List<Integer> ln = entry.getValue();
            for(int j = 0; j < ln.size(); j++){
                System.out.print(ln.get(j)+":");
            }
            System.out.println();
        }




    }
}
