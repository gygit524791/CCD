package ccd.stringDetection;

import java.util.ArrayList;
import java.util.List;
/**
 * 记录回溯结点的回溯路径-消除覆盖现象,同时减少不必要的回溯
 * */
class TraceBackPath{
    int row;
    int col;
}

public class TraceBack {//得分矩阵的回溯
    public MatchString matchString = new MatchString();//进行回溯得到的两个匹配字符串
    //public static List<CandidateNode> tIndexListTb;
    List<TraceBackPath> traceBackPathList = new ArrayList<>();//结点的回溯路径

    public List<TraceBackPath> traceBack(int[][] M_matrix,String s,String t,int m, int n){
        if(M_matrix[m][n] == 0){return traceBackPathList;}//回溯结束条件

        TraceBackPath traceBackPath = new TraceBackPath();
        traceBackPath.row = m;
        traceBackPath.col = n;
        traceBackPathList.add(traceBackPath);

        int max = Math.max(M_matrix[m-1][n-1],Math.max(M_matrix[m-1][n],M_matrix[m][n-1]));//记录元组的上方,左方,右上方中的最大值
        if(((M_matrix[m-1][n-1] == M_matrix[m][n-1]) || (M_matrix[m-1][n-1] == M_matrix[m-1][n])) && (max <= M_matrix[m-1][n-1])){
            matchString.matchS.append(s.charAt(m-1));
            matchString.matchT.append(t.charAt(n-1));

            traceBack(M_matrix,s,t,m-1,n-1);

        }else if(max == M_matrix[m-1][n]){
            matchString.matchS.append(s.charAt(m-1));
            matchString.matchT.append('-');

            traceBack(M_matrix,s,t,m-1,n);//递归

        }else if(max == M_matrix[m][n-1]){
            matchString.matchS.append('-');
            matchString.matchT.append(t.charAt(n-1));

            traceBack(M_matrix,s,t,m,n-1);

        }else {
            matchString.matchS.append(s.charAt(m-1));
            matchString.matchT.append(t.charAt(n-1));

            traceBack(M_matrix,s,t,m-1,n-1);
        }

        return traceBackPathList;
    }
}
