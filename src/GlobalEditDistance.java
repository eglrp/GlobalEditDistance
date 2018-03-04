//package com.test;

import com.sun.javafx.scene.control.skin.VirtualFlow;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class GlobalEditDistance {

	double i, d, r, m;
	int method;
	double matrixR[][] = new double[27][27];//a-z,'	//97-122 39
	int matrixRCount[][] = new int[27][27];//a-z,'	//97-122 39

	public GlobalEditDistance(int _method) {
		// TODO Auto-generated constructor stub
		method=_method;
		if(method==0) {
			i = -1;
			d = -1;
			m = +1;
			r = -1;
			for(int k=0;k<27;k++){
				for(int n=0;n<27;n++){
					if(k==n)
						matrixR[k][n]=+1;
					else
						matrixR[k][n]=r;
				}
			}

		}
		else{//Levenshtein Distance (true distance)
			i = +1;
			d = +1;
			m = 0;
			r = +1;

			for(int k=0;k<27;k++){
				for(int n=0;n<27;n++){
					if(k==n)
						matrixR[k][n]=0;
					else
						matrixR[k][n]=r;
				}
			}
		}
		for(int k=0;k<27;k++){
			for(int n=0;n<27;n++){
				matrixRCount[k][n]=0;
			}
		}
	}
	public void setAllMatrixR(double _r)
	{
		for(int k=0;k<27;k++){
			for(int n=0;n<27;n++){
				if(k==n)
					if(method==0)
						matrixR[k][n]=+1;
					else
						matrixR[k][n]=0;
				else
					matrixR[k][n]=_r;
			}
		}
	}
	public void setMatrixR(double _r,int a,int b)
	{
		matrixR[a][b]=_r;
	}
	public void cleanMatrixRCount()
	{
		for(int k=0;k<27;k++){
			for(int n=0;n<27;n++){
				matrixRCount[k][n]=0;
			}
		}
	}
	public List<Integer> getMostmatrixRCount()
	{
		int max=0;
		int maxi=0,maxj=0;
		for(int k=0;k<27;k++){
			for(int n=0;n<27;n++){
				if(matrixRCount[k][n]>max){
					max=matrixRCount[k][n];
					maxi=k;
					maxj=n;
				}
			}
		}
//		System.out.print(maxi);
//		System.out.print(maxj);
//		System.out.println(matrixRCount[maxi][maxj]);
//		System.out.println();
		List<Integer> maxList=new ArrayList();
		maxList.add(maxi);
		maxList.add(maxj);
		return maxList;
	}
	private double max3(double a, double b, double c) {
		// TODO Auto-generated method stub
		return Math.max(Math.max(a, b), c);
	}
	private double min3(double a, double b, double c) {
		// TODO Auto-generated method stub
		return Math.min(Math.min(a, b), c);
	}

	// @Override
	// public boolean equals(Object obj) {
	// // TODO Auto-generated method stub
	//
	// return super.equals(obj);
	// }
	/**
	 * calculate edit distance
	 *
	 * @param f
	 * @param t
	 * @return edit distance
	 */
	public double editDistance(String f, String t) {
		int lf, lt;
		lf = f.length();
		lt = t.length();
		double A[][] = new double[lt + 1][lf + 1];
		int j, k;
		A[0][0]=0.0;
		for (j = 1; j <= lt; j++)
			A[j][0] = j * i;
		for (k = 1; k <= lf; k++)
			A[0][k] = k * d;

		for (j = 1; j <= lt; j++)
			for (k = 1; k <= lf; k++) {
				if (method == 0) {
					A[j][k] = max3( // Or min3 if m<i,d,r
							A[j][k - 1] + d, // Deletion
							A[j - 1][k] + i, // Insertion
							A[j - 1][k - 1] + ((f.charAt(k - 1)==t.charAt(j - 1))? m :
									((int)f.charAt(k - 1)==39?matrixR[(int)t.charAt(j - 1)-97][26]:matrixR[(int)t.charAt(j - 1)-97][(int)f.charAt(k - 1)-97]))
					);// Replace
					// or
					if(f.charAt(k - 1)!=t.charAt(j - 1)){
						if((int)f.charAt(k - 1)==39){
							matrixRCount[(int)t.charAt(j - 1)-97][26]++;
						}
						else{
							matrixRCount[(int)t.charAt(j - 1)-97][(int)f.charAt(k - 1)-97]++;
						}
					}
				}                                                                              // match
				else{//Levenshtein Distance
					A[j][k] = min3( // Or min3 if m<i,d,r
							A[j][k - 1] + d, // Deletion
							A[j - 1][k] + i, // Insertion
							A[j - 1][k - 1] + ((f.charAt(k - 1)==t.charAt(j - 1))? m :
									((int)f.charAt(k - 1)==39?matrixR[(int)t.charAt(j - 1)-97][26]:matrixR[(int)t.charAt(j - 1)-97][(int)f.charAt(k - 1)-97]))
					);																		// or
					if(f.charAt(k - 1)!=t.charAt(j - 1)){
						if((int)f.charAt(k - 1)==39){
							matrixRCount[(int)t.charAt(j - 1)-97][26]++;
						}
						else{
							matrixRCount[(int)t.charAt(j - 1)-97][(int)f.charAt(k - 1)-97]++;
						}
					}
				}
			}

		return A[lt][lf];
	}

	/**
	 * Calculate similarity
	 *
	 * @param f
	 * @param t
	 * @return
	 */
	public double similar(String f, String t) {
		double ed = editDistance(f, t);	//越小越好
		return 1 - (double) ed / (double)Math.max(f.length(), t.length());	//越大越好，所以字符串越长越好
	}



	public List readTxtFile(String filename) {
		List list = new ArrayList();
		try {
			File file = new File(filename);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s = null;
			while ((s = br.readLine()) != null) {
				list.add(s);
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}

	public List getBest(List<Double> list){
		double max=0;
		double min=999999;
		List<Integer> maxId = new ArrayList();
		List<Integer> minId = new ArrayList();
		for(int k=0;k<list.size();k++){
			if(list.get(k) > max){
				max=list.get(k);
			}
			if(list.get(k) < min){
				min=list.get(k);
			}
		}
		for(int k=0;k<list.size();k++){
			if(list.get(k)==max){
				maxId.add(k);
			}
		}
		for(int k=0;k<list.size();k++){
			if(list.get(k)==min){
				minId.add(k);
			}
		}
		//return minId;
		if(method==0)
		    return minId;
        else
		    return maxId;
	}

	public void writeTxtFile(String fileName,List<String> buf) throws IOException {
		File file = new File(fileName);
		//FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		for(int k=0;k<buf.size();k++){
			bw.write(buf.get(k)+"\n");
		}
		bw.close();
	}
	public List Evaluation(List<String> latinString,List<List<String>> result)
	{
		double accuracy,precision,recall;
		int numSingleResult=0,accSingleResult=0;
		List<Double> evaluationResult=new ArrayList();
//		for(int k=0;k<result.size();k++){
//			System.out.println(latinString.get(k));
//			for(int n=0;n<result.get(k).size();n++){
//				System.out.println(result.get(k).get(n));
//			}
//			System.out.print("\n");
//		}
		for(int k=0;k<result.size();k++) {
			if(result.get(k).size()==1) {
				numSingleResult++;
				if(result.get(k).get(0).equals(latinString.get(k)))
					accSingleResult++;
			}
		}
		accuracy=(double)accSingleResult/(double)numSingleResult;
		int allPre=0,accPre=0;
		for(int k=0;k<result.size();k++) {
			allPre+=result.get(k).size();
			for(int n=0;n<result.get(k).size();n++) {
				if(result.get(k).get(n).equals(latinString.get(k))){
					accPre++;
					break;
				}
			}
		}
		precision=(double)accPre/(double)allPre;
		recall=(double)accPre/(double)latinString.size();

		System.out.print("accuracy: ");
		System.out.println(accuracy);
		System.out.print("precision: ");
		System.out.println(precision);
		System.out.print("recall: ");
		System.out.println(recall);
		System.out.println("\n");
		evaluationResult.add(accuracy);
		evaluationResult.add(precision);
		evaluationResult.add(recall);
		return evaluationResult;
	}
	
	public static void main(String[] args) throws IOException {
	}
}

