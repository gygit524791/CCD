package ccd.stringDetection;

import org.junit.Test;
import java.util.List;

public class SWTest {
    public static void testResult(String s,String t, int threshold){
        SWAlgorithm.executeSW(s,t,threshold);
        //测试结果:
        List<TraceBack> tbList = SWAlgorithm.tbList;
        System.out.println("tbList size: "+tbList.size());
        for (int i = 0; i < tbList.size(); i++) {
            TraceBack traceBack = tbList.get(i);
            SWAlgorithm.pringDX(traceBack.matchString.matchS.toString(),traceBack.matchString.matchT.toString());
            System.out.println();
            System.out.println();
        }
    }

    @Test
    public void testStr1 (){
        //完全相同
        String s = "abcdefghijklmnopqrst";
        String t = "abcdefghijklmnopqrst";
        int threshold = 5;//阈值
        testResult(s,t,threshold);
    }

    @Test
    public void testStr2 (){
        //开头部分相同
        String s = "abcdefghijklmnopqrst";
        String t = "abcdefgjoizjopijcpzxo";
        int threshold = 5;//阈值
        testResult(s,t,threshold);
    }

    @Test
    public void testStr3 (){
        //中间不同
        String s = "abcdefghijklmnopqrsjtuv";
        String t = "abcdefghijkzzzzzzzzzzzfxgzmnopqrsjtuv";
        int threshold = 5;//阈值
        testResult(s,t,threshold);
    }

    @Test
    public void testStr4 (){
        //开头和结尾部分不同,中间相同
        String s = "abcdefghijklmnopqrsjtuv";
        String t = "oxchvoiehijklmnopqwoeirjowier";
        int threshold = 5;//阈值
        testResult(s,t,threshold);
    }

    @Test
    public void testStr5 (){
        //结尾部分相同
        String s = "abcdefghijklmnopqrst";
        String t = "vlicjoesijrflcouewqenopqrst";
        int threshold = 5;//阈值
        testResult(s,t,threshold);
    }

    @Test
    public void testStr6 (){
        //两个完全相同的字符串, 然后在其中一个中间,加入个别其它字符,--阈值效果
        //中间不同的字符个数是4
        String s = "abcdefghijklmnopqrsjtuv";
        String t = "abcdefghijklaaggmnopqrsjtuv";
        int threshold = 5;//阈值
        testResult(s,t,threshold);
    }

    @Test
    public void testStr7 (){
        //开头相同,中间不同,尾部相同-论文中例子
        String s = "abcdefghijklmnopqrsjtuv";
        String t = "abcxdefghiymzjlukpqsjtuv";
        int threshold = 5;//阈值
        testResult(s,t,threshold);
    }

    @Test
    public void testStr8 (){
        //交错
        String s = "abcdefghi  pqrsjtuv";
        String t = "pqrsjtuv  abcdefghi";
        int threshold = 5;//阈值
        testResult(s,t,threshold);
    }

    @Test
    public void testStr9 (){

        String s = "abcdefghijklmnopqrsjtuv";
        String t = "abcdefgjoijoilmnssodpqrgasjtuv";
        int threshold = 5;//阈值
        testResult(s,t,threshold);
    }

    @Test
    public void testStr10 (){
        //abcdefgghijklmnkopnq
        //abcdxyefgxygxyhijklxynqqkopnq
        //70 71 72 73 75 76 77 77 84 126 127 128 129 130 207 128 131 163 207 29
        //70 71 72 73 3  5  75 76 77 3   5   77  3   5   84  126 127 128 129 130  3  5 207 29 29 128 131 163 207 29
        String s = "abcdefgghijklmnkopnq";
        String t = "abcdxyefgxygxyhijklxynqqkopnq";
        int threshold = 5;//阈值
        testResult(s,t,threshold);
    }

    @Test
    public void testStr11111 (){
        //稍微综合一点----结果有重叠
        String s = "gggggglkjoijoijoittttttyrgtrrvgabcdefghijklmnopqrsjtuv";
        String t = "ggggggudrtiddwerwwerwexcvdfvdoerjddttttttjhythttgabcxdefghiymzjlukpqsjtuv";
        int threshold = 5;//阈值
        testResult(s,t,threshold);
    }

}
