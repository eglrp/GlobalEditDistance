/**
 * Created by T430u on 2017/3/31.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class train {
    public static void main(String[] args) throws IOException {
        int numSize=1000;
        GlobalEditDistance globalEditDistance = new GlobalEditDistance(0);
        List list1 = globalEditDistance.readTxtFile("E:\\work\\finish33\\GlobalEditDistance\\src\\train.txt");
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

        //train的参数调整,Evaluation
        List<Integer> most = new ArrayList();
        //r参数设置，这里只能运行两次
        double[] rList=new double[2];
        rList[0]=-1.0;
        rList[1]=-0.2;
        for (int x = 0; x <2; x++) {
            //globalEditDistance.r = -(double)x/10.0;
            //globalEditDistance.setMatrixR(-(double)x/10.0);
            //globalEditDistance.d = x;
            //globalEditDistance.i = x;

            //计算每个波斯名字
            for (int k = 0; k < numSize; k++) {
                List<Double> data0 = new ArrayList<Double>();
                for (int j = 0; j < list2.size(); j++) {
                    //计算每个波斯名字和每个拉丁名字的匹配得分
                    data0.add(globalEditDistance.similar(persianLowerString.get(k), list2.get(j).toString()));
                }
                //获取这个波斯名字的最佳匹配
                List<Integer> bestId = globalEditDistance.getBest(data0);
                List best = new ArrayList();
                for (int n = 0; n < bestId.size(); n++)
                    best.add(list2.get(bestId.get(n)));
                //写入结果
                result.add(best);
                //System.out.println(k);
            }
            //计算变换最多的某个字符到某个字符
            if (x == 0)
                most = globalEditDistance.getMostmatrixRCount();

            //输出当前r值
            System.out.print("r: ");
            System.out.println(globalEditDistance.matrixR[4][0]);
            //将当前r值输出到文本
            out.add(Double.toString(globalEditDistance.matrixR[4][0]));
//				System.out.println(most.get(1));
//				System.out.println(most.get(0));
            //计算当前r值的三个评价指标
            List evaluationList = globalEditDistance.Evaluation(latinString, result);
            //将评价指标写入文本
//            out.add(most.get(1).toString());
//            out.add(most.get(0).toString());
            out.add(evaluationList.get(0).toString());
            out.add(evaluationList.get(1).toString());
            out.add(evaluationList.get(2).toString());
            out.add(" ");
            accuracyList.add(evaluationList.get(0).toString());
            precisionList.add(evaluationList.get(1).toString());
            recallList.add(evaluationList.get(1).toString());

            //清空数据
            result.clear();
            globalEditDistance.cleanMatrixRCount();
            //改变这个字符到另一个字符的r值
            if(x<1)
                //第一个为r值，第二个为替换后的，第三个为被替换的，0-27对应1-z,'
                globalEditDistance.setMatrixR(rList[x+1], 4, 0);
        }

        globalEditDistance.writeTxtFile("out.txt", out);
    }
}
