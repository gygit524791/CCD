package ccd.detection;

public class CandidateNode {//候选回溯结点
    int row;//结点在矩阵的行号
    int col;//结点在矩阵的列号
    int score;//在矩阵中的得分
    //是否在回溯过程中被访问
    public boolean isVisited(int r,int c){
        if((row == r) && (col ==c)){
            return true;
        }else{
            return false;
        }
    }
}
