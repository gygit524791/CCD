package ccd.stringDetection;

import java.util.ArrayList;
import java.util.List;

/**
 * SW FOR CCD
 * */
public class SWAlgorithm {
    public static int[][] M_matrix;//得分矩阵
    //待检测字符串的长度-矩阵大小
    public static int s_size;
    public static int t_size;
    static List<TraceBack> tbList = new ArrayList<>();//回溯结果集合

    public static void main(String args[]) {
        String s = "abcdefghi  pqrsjtuv";
        String t = "pqrsjtuv  abcdefghi";
        int threshold = 5;//阈值

        SWAlgorithm.executeSW(s,t,threshold);

        //结果测试:
        System.out.println("tbList size: "+tbList.size());
        System.out.println("--------------------------");
        for (int i = 0; i < tbList.size(); i++) {
            TraceBack traceBack = tbList.get(i);
            pringDX(traceBack.matchString.matchS.toString(),traceBack.matchString.matchT.toString());
            System.out.println();
            System.out.println();
        }
        System.out.println("--------------------------");

    }

    public static void executeSW(String s,String t,int threshold){
        s_size = s.length();
        t_size = t.length();
        List<CandidateNode> candidateNodeList;
        //CandidateNode candidateNode;
        if(s_size <= t_size){
            M_matrix = GenMatrix.genMatrix(s,t,threshold);
        }else{//s长度不能小于t长度,s和t直接互换位置不行:M_matrix = GenMatrix.genMatrix(t,s,threshold);
            String u = s;
            s = t;
            t = u;
            M_matrix = GenMatrix.genMatrix(s,t,threshold);
        }
        candidateNodeList = GenMatrix.getCandidateNodeList(threshold);
        s_size = s.length();
        t_size = t.length();

        executeTraceBack(s,t,candidateNodeList);

    }

    public static void executeTraceBack(String s,String t,List<CandidateNode> candidateNodeList){
        TraceBack tb = new TraceBack();
        if(candidateNodeList.size() > 0){
            List<CandidateNode> maxCandidateNodeList = getMaxCandidateNode(candidateNodeList);
            for (int i = 0; i < maxCandidateNodeList.size(); i++) {//相同最大值
                CandidateNode candidateNode = maxCandidateNodeList.get(i);
                List<TraceBackPath> traceBackPathList = tb.traceBack(M_matrix,s,t,candidateNode.row,candidateNode.col);
                tbList.add(tb);
                /**
                 * 查看回溯路径中是否有候选回溯结点,如果有,就把该节点从候选中去除
                 * */
                for (int j = 0; j < traceBackPathList.size(); j++) {
                    int row = traceBackPathList.get(j).row;
                    int col = traceBackPathList.get(j).col;
                    for (int k = 0; k < candidateNodeList.size(); k++) {
                        CandidateNode cn = candidateNodeList.get(k);
                        if(cn.isVisited(row,col)){
                            candidateNodeList.remove(cn);
                        }
                    }
                }
                executeTraceBack(s,t,candidateNodeList);
            }
        }
    }

    public static List<CandidateNode> getMaxCandidateNode(List<CandidateNode> candidateNodeList){
        if(candidateNodeList.size() == 0){return null;}
        List<CandidateNode> maxCandidateNode = new ArrayList<>();//候选回溯结点中的最大值可能不止一个
        int maxScore = candidateNodeList.get(0).score;
        for (int i = 0; i < candidateNodeList.size() ; i++) {
            if(maxScore < candidateNodeList.get(i).score){
                maxScore = candidateNodeList.get(i).score;
            }
        }
        for (int i = 0; i < candidateNodeList.size() ; i++) {
            if(maxScore == candidateNodeList.get(i).score){
                maxCandidateNode.add(candidateNodeList.get(i));
            }
        }
        return maxCandidateNode;
    }

    //倒序打印出匹配字符串-测试用
    public static void pringDX(String s1, String s2){
        for (int i = s1.length() - 1; i >= 0; i--) {
            char ch = s1.charAt(i);
            System.out.print(ch);
        }
        System.out.println();
        for (int i = s2.length() - 1; i >= 0; i--) {
            char ch = s2.charAt(i);
            System.out.print(ch);
        }
    }

}
