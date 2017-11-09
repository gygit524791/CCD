package ccd.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Sequence {//代码序列
    private String packageName;
    private String fileName;
    private int startLine;
    private int endLine;
    private List<Integer> body = new ArrayList<Integer>();

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getStartLine() {
        return startLine;
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public int getEndLine() {
        return endLine;
    }

    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    public List<Integer> getBody() {
        return body;
    }

    public void setBody(List<Integer> body) {
        this.body = body;
    }

    public static void main(String[] args) throws Exception {
        Stack<Sequence> scs = new Stack<Sequence>();
    }

}
