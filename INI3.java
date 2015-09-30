package com.zwx.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Hashtable;
import java.util.Map;

public class INI3 {
	Map<String, String> iniHash =null;

	public void hashINI(String iniFilePath) {
		String Section = "", Key = "", Value = "";
		if (iniHash == null)
			iniHash = new Hashtable<String, String>();
		if (!isEmpty())
			iniHash.clear();
		try {
			BufferedReader bufReader = new BufferedReader(new FileReader(
					iniFilePath));
			String strLine = "";
			int firstLeftSquareBrackets = 0, firstRightSquareBrackets = 0;
			while ((strLine = bufReader.readLine()) != null) {
				strLine = strLine.trim();
				firstLeftSquareBrackets = strLine.indexOf("[");
				firstRightSquareBrackets = strLine.indexOf("]");
				if (firstLeftSquareBrackets >= 0
						&& firstRightSquareBrackets >= 0
						&& firstLeftSquareBrackets < firstRightSquareBrackets) {
					Section = strLine.substring(firstLeftSquareBrackets + 1,
							firstRightSquareBrackets);
				} else {
					int index = 0;
					index = strLine.indexOf("=");
					Key = strLine.substring(0, index).trim();
					Value = strLine.substring(index + 1).trim();
					String hashKey = "";
					hashKey = Section + "." + Key;
					iniHash.put(hashKey.toLowerCase(), Value);
				}
			}
			bufReader.close();
		} catch (Exception e) {
			System.out.println("读取配置文件发生错误。" + e.getMessage());
		}
	}

	public boolean isEmpty() {
		if (iniHash == null)
			return true;
		try {
			return iniHash.isEmpty();
		} catch (NullPointerException e) {
			return true;
		}
	}

	public String getHashValue(String Section, String Key,String iniFilePath) {
		if (isEmpty())
			hashINI(iniFilePath);
		if (isEmpty())
			return "";
		String hashKey = Section + "." + Key;
		String Value = (String) iniHash.get(hashKey.toLowerCase());
		return (Value == null) ? "" : Value;
	}
}
