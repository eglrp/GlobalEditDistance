/**
 * Created by T430u on 2017/3/31.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class testName {
    public static void main(String[] args) throws IOException {
        //单个字母的变换测试
        GlobalEditDistance globalEditDistance = new GlobalEditDistance(0);
        List list1 = globalEditDistance.readTxtFile("E:\\work\\finish33\\GlobalEditDistance\\src\\test.txt");
        List list2 = globalEditDistance.readTxtFile("E:\\work\\finish33\\GlobalEditDistance\\src\\names.txt");

        List<String> result = new ArrayList();

        //这里改变输入单词
        String testName="ANDVR";
        String strLower = testName.toLowerCase();
        List<Double> data0 = new ArrayList<Double>();
        for (int j = 0; j < list2.size(); j++) {
            data0.add(globalEditDistance.similar(strLower, list2.get(j).toString()));
        }
        List<Integer> bestId = globalEditDistance.getBest(data0);
        List best = new ArrayList();
        for (int n = 0; n < bestId.size(); n++)
            best.add(list2.get(bestId.get(n)));

        System.out.println(testName);
        for (int n = 0; n < 1; n++) {
            System.out.println(best.get(n));
        }
    }
}
