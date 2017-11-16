package ccd.detection;

import ccd.model.Sequence;
import ccd.detection.CandidateNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenMatrix {
    public static  int[][] M_matrix;//原始得分矩阵
    public static  int[][] H_matrix;//优化得分矩阵

    public static int s_size;
    public static int t_size;

    public static int A = 1;
    public static int B = -1;

    public static int[][] genMatrix(Sequence s, Sequence t, int threshold){
        s_size = s.getBody().size();//list s的长度
        t_size = t.getBody().size();//list t的长度

        M_matrix = new int [s_size+1][t_size+1];
        H_matrix = new int [s_size+1][t_size+1];

        //第一行,第一列设置为0
        for(int i = 0; i <= s_size; i++){
            M_matrix[0][i] = H_matrix[0][i] = 0;
        }
        for(int i = 0; i <= s_size; i++){
            M_matrix[i][0] = H_matrix[i][0] = 0;
        }

        //构造矩阵
        for(int i = 1; i <= s_size; i++){
            for(int j = 1; j <= t_size; j++){
                if(s.getBody().get(i-1).intValue() == t.getBody().get(j-1).intValue()){
                    A = 1;
                }else{
                    A = -1;
                }
                M_matrix[i][j] = Math.max(0,Math.max(Math.max(M_matrix[i-1][j] + B,M_matrix[i][j-1] + B),M_matrix[i-1][j-1] + A));
                if(M_matrix[i][j] == 0){
                    H_matrix[i][j] = 0;
                }
                if(s.getBody().get(i-1).intValue() == t.getBody().get(j-1).intValue()){
                    H_matrix[i][j] = Math.max(M_matrix[i-1][j-1],H_matrix[i-1][j-1]);
                }else{
                    //候选值
                    int a = Math.max(M_matrix[i][j-1],M_matrix[i-1][j]);
                    int b = Math.max(a,M_matrix[i-1][j-1]);
                    int c = Math.max(H_matrix[i][j-1],H_matrix[i-1][j]);
                    int d = Math.max(c,H_matrix[i-1][j-1]);
                    int e = Math.max(b,d);
                    H_matrix[i][j] = e;
                }
                if((H_matrix[i][j] - M_matrix[i][j]) >= threshold){
                    M_matrix[i][j] = H_matrix[i][j] = 0;
                }
            }
        }
        return M_matrix;
    }

    //获取候选回溯结点集合
    public static List<CandidateNode> getCandidateNodeList(int threshold){
        List<CandidateNode> candidateNodeList = new ArrayList<>();
        CandidateNode candidateNode;
        for (int i = 0; i < s_size+1; i++) {
            for (int j = 0; j < t_size+1; j++) {
                if((M_matrix[i][j] >= threshold) && (M_matrix[i][j]> H_matrix[i][j])){
                    candidateNode = new CandidateNode();
                    candidateNode.row = i;
                    candidateNode.col = j;
                    candidateNode.score = M_matrix[i][j];
                    //candidateNode.visited = false;
                    candidateNodeList.add(candidateNode);
                }
            }
        }
        return candidateNodeList;
    }

    //打印矩阵-int list
    public static void printMatrix(int[][] matrix,Sequence s,Sequence t){
        for(int i = 0; i < s_size+1; i++){
            if(i == 0){//字符s(第0列)
                System.out.print("      ");
            }else if(i< s_size+1){
                String si = s.getBody().get(i-1)+"";
                if(si.length()==1){
                    System.out.print(s.getBody().get(i-1)+"     ");
                }else if(si.length()==2){
                    System.out.print(s.getBody().get(i-1)+"    ");
                }else{
                    System.out.print(s.getBody().get(i-1)+"   ");
                }
            }

            if(i == 0){//字符t(第0行)
                for (int j = 0; j < t_size; j++){
                    if(j==0){
                        System.out.print("    "+t.getBody().get(j)+"   ");
                    }else{
                        System.out.print(t.getBody().get(j)+"   ");
                    }
                }
                System.out.println();
            }

            for (int j = 0; j < t_size+1; j++){//矩阵
                String spaceLen = "";
                if((matrix[i][j]+"").length()==1){
                    spaceLen  = "   ";
                }else if((matrix[i][j]+"").length()==2){
                    spaceLen  = "  ";
                }else{
                    spaceLen  = " ";
                }

                System.out.print(matrix[i][j]+spaceLen);

                /*
                if(matrix[i][j] == 0){
                    System.out.print(spaceLen);
                }else {
                    System.out.print(matrix[i][j]+spaceLen);
                }
                */
            }
            System.out.println();
        }
    }

}
