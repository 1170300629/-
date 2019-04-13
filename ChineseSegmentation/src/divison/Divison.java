package divison;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Divison {
	private static Set<String> dic = new HashSet<String>();
	private static int maxlength = 0; 
	
	private static void getDictionary(String fileName) {
		String[] dicStrings = null;
		FileReader in = null;
		try {
			in = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			System.out.println("文件读入失败！");
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(in);
		try {
			for(String temps = reader.readLine();temps!=null;temps = reader.readLine()) {
				dicStrings = temps.split(" |1|2|3|4|5|6|7|8|9|0|\n");
				for (int i = 0; i < dicStrings.length; i++) {
					dic.add(dicStrings[i]);
					if(dicStrings[i].length() > maxlength) {
						maxlength = dicStrings[i].length();
					}
				}
			}
			in.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	private static void makeDivison(String filename) {
		FileReader in = null;
		String result = null;
		int i = 1;
		try {
			in = new FileReader(filename);
			BufferedWriter buf = new BufferedWriter(new FileWriter("src\\doc\\分词结果.txt"));
			BufferedReader reader = new BufferedReader(in);
			for(String temps = reader.readLine();temps!=null;temps = reader.readLine()) {
				System.out.println(temps);
				System.out.println("");
				result = i + "."+aMatch(dic, temps);
				System.out.println(result);
				System.out.println("");
				buf.write(result);
				buf.newLine();
				i++;
			}
			in.close();
			buf.close();	
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			System.out.println("文件读入失败！");
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	}
	private static String aMatch(Set<String> dic,String waitmatch) {
		int first = waitmatch.length() - maxlength;
		if (first < 0) {
			first = 0;
		}
		int last = waitmatch.length();
		String subtemp = null;
		String result = "";
		boolean success = false;
		while (last != 0) {
			while(first < last - 1){
				subtemp = waitmatch.substring(first, last);
				if (dic.contains(subtemp)) {
					success = true;
					result =subtemp + "\\" + result;
					break;
				}
				first++;
			}
			if (!success) {
				subtemp = waitmatch.substring(first, last);
				result =subtemp + "\\" + result;
			}
			last = first;
			success = false;
			if(last - maxlength >= 0) {
				first = last - maxlength;
			}
			else {
				first = 0;
			}
		}
		return result;
	}
	public static void main(String[] args) {
		String filename = "src\\doc\\未处理字典.txt";
		String filewait = "src\\doc\\测试文本100句.txt";
		getDictionary(filename);
		System.out.println("词典提取成功！"+"\n所提取词最长为："+maxlength+"\n词数总量为："+dic.size());
		makeDivison(filewait);
		System.out.println("分词成功！");
	}
}
