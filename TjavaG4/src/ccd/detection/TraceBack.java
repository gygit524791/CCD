package ccd.detection;

/**
 * 回溯:
 * 参数:
 *  1.得分矩阵M.
 *  2.矩阵中最大元组的下标:m,n.
 *  3.待匹配的两个字符串
 *  4.MatchString : 得出的两个匹配结果的字符串-放到一个类中
 * */
public class TraceBack {//得分矩阵的回溯
    public MatchString matchString = new MatchString();//进行回溯得到的两个匹配字符串
    private int stopRow;//右下矩阵回溯的终止条件
    private int stopCol;
    private int endRow;//回溯终止元素的坐标
    private int endCol;

    public void traceBack(int[][] M_matrix,String s,String t,int m,int n){
        if(M_matrix[m][n] == 0  || (m <= stopRow || n <= stopCol)){//回溯终止条件
            endRow = m;
            endCol = n;
            //System.out.println("回溯终止元素下标: ROW = "+m+", COL = "+n);
            return;
        }
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
    }


    public int getStopRow() {
        return stopRow;
    }

    public void setStopRow(int stopRow) {
        this.stopRow = stopRow;
    }

    public int getStopCol() {
        return stopCol;
    }

    public void setStopCol(int stopCol) {
        this.stopCol = stopCol;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getEndCol() {
        return endCol;
    }

    public void setEndCol(int endCol) {
        this.endCol = endCol;
    }
}
