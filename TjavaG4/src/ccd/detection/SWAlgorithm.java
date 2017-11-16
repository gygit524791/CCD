package ccd.detection;

import ccd.model.Sequence;
import ccd.detection.CandidateNode;
import ccd.detection.TraceBackPath;

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
    public static List<TraceBack> tbList = new ArrayList<TraceBack>();//回溯结果集合

    public static void executeSW(Sequence s, Sequence t, int threshold){
        List<CandidateNode> candidateNodeList;
        //s长度不能小于t长度,s和t直接互换位置不行:M_matrix = GenMatrix.genMatrix(t,s,threshold);
        if(s.getBody().size() <= t.getBody().size()){
            M_matrix = GenMatrix.genMatrix(s,t,threshold);
        }else{
            Sequence u = s;
            s = t;
            t = u;
            M_matrix = GenMatrix.genMatrix(s,t,threshold);
        }
        candidateNodeList = GenMatrix.getCandidateNodeList(threshold);//获取候选回溯结点的集合
        s_size = s.getBody().size();
        t_size = t.getBody().size();
        executeTraceBack(s,t,candidateNodeList);
    }

    public static void executeTraceBack(Sequence s,Sequence t,List<CandidateNode> candidateNodeList){
        TraceBack tb = new TraceBack();
        if(candidateNodeList.size() > 0){//候选回溯结点的集合不为空
            List<CandidateNode> maxCandidateNodeList = getMaxCandidateNode(candidateNodeList);//获取最大的候选回溯结点
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

    //候选回溯结点中的最大值可能不止一个
    public static List<CandidateNode> getMaxCandidateNode(List<CandidateNode> candidateNodeList){
        if(candidateNodeList.size() == 0){return null;}
        List<CandidateNode> maxCandidateNode = new ArrayList<>();
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
}
