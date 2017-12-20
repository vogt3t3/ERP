package com.aiiju.util.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadJsonData {
	//读JSON文件,返回字符串
	public static String readFile(String path){
	    File file = new File(path);
	    BufferedReader reader = null;
	    String jsonStr = "";
	    try {
		    reader = new BufferedReader(new FileReader(file));
		    String tempStr = null;
		    while ((tempStr = reader.readLine()) != null) {
		    	//去除字符串中的所有空格
		    	tempStr = tempStr.replaceAll("\\s*", "");
		    	jsonStr = jsonStr + tempStr;
		    }
		    reader.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } finally {
		    if (reader != null) {
			    try {
			    	reader.close();
			    } catch (IOException e) {
			    	e.printStackTrace();
			    }
		    }
	    }
	    return jsonStr;
	}
}  
