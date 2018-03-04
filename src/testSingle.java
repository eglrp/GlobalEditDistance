/**
 * Created by T430u on 2017/3/31.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class testSingle {
    public static void main(String[] args) throws IOException {
        //每个对应单词输出单个单词
        GlobalEditDistance globalEditDistance = new GlobalEditDistance(0);
        List list1 = globalEditDistance.readTxtFile("E:\\work\\finish33\\GlobalEditDistance\\src\\test.txt");
        List list2 = globalEditDistance.readTxtFile("E:\\work\\finish33\\GlobalEditDistance\\src\\names.txt");

        List<List<String>> result = new ArrayList();
        List<String> persianString = new ArrayList();
        List<String> persianLowerString = new ArrayList();
        List<String> latinString = new ArrayList();
        List<String> accuracyList = new ArrayList();
        List<String> precisionList = new ArrayList();
        List<String> recallList = new ArrayList();
        List<String> out = new ArrayList();
        for (int k = 0; k < list1.size(); k++) {
            //String str = list1.get(k).toString().substring(0,list1.get(k).toString().indexOf("???")).replace(" ", "");
            String str = list1.get(k).toString();
            String str1 = str.split("\\s+")[0];
            String strLower = str1.toLowerCase();
            persianString.add(str1);
            persianLowerString.add(strLower);
            latinString.add(str.split("\\s+")[1]);
        }
		globalEditDistance.setMatrixR(-0.2, 4, 0);
        //test的测试
        for (int k = 0; k < list1.size(); k++) {
            List<Double> data0 = new ArrayList<Double>();
            for (int j = 0; j < list2.size(); j++) {
                data0.add(globalEditDistance.similar(persianLowerString.get(k), list2.get(j).toString()));
            }
            List<Integer> bestId = globalEditDistance.getBest(data0);
            List best = new ArrayList();
            for (int n = 0; n < bestId.size(); n++)
                best.add(list2.get(bestId.get(n)));
            result.add(best);
            //System.out.println(k);
        }
        for (int k = 0; k < result.size(); k++) {
            System.out.println(persianString.get(k));
            for (int n = 0; n < 1; n++) {
                System.out.println(result.get(k).get(n));
                out.add(persianString.get(k) + " " + result.get(k).get(n));
            }
            System.out.print("\n");
        }
        globalEditDistance.writeTxtFile("testResultSingle_-1.0.txt", out);
    }
}
