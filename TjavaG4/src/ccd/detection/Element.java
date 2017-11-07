package ccd.detection;
/**
 * 优化后的得分矩阵中的元素
 *
 * */
public class Element {
    private int score;//得分
    private int row;//所在行
    private int col;//所在列
    private Element preElement;//前驱元素
}
