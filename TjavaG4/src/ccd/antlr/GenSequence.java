package ccd.antlr;

import ccd.util.GetFoldFileNames;
import java8.Java8Lexer;
import java8.Java8Parser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class GenSequence {
    public static void genericCodeSequence(String path) throws IOException {
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
    }

}
