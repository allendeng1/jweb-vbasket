package com.jweb.common.file.txt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.jweb.common.exception.FileException;
import com.jweb.common.exception.MyException;
import com.jweb.common.file.txt.TxtFile.LineInfo;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class TxtFileUtil {

	/**
	 * 读取文件
	 * @param filePathName 文件名路径
	 * @return
	 * @throws MyException
	 */
	public static TxtFile read(String filePathName) throws MyException{
		BufferedReader reader;
		StringBuilder sb = new StringBuilder();
		int lineCount = 0;
		int blankLineCount = 0;
		long charLength = 0;
		List<LineInfo> lis = new ArrayList<LineInfo>();
	    try {
	        reader = new BufferedReader(new FileReader(filePathName));
	        String line = reader.readLine();
	        while (line != null) {
	        	lineCount++;
	        	charLength += line.length();
	        	sb.append(line).append("\r\n");
	        	 
	        	LineInfo li = new LineInfo();
	        	li.setLineNum(lineCount);
	        	li.setBlankRow(line.length() == 0 || line.matches("\\s+"));
	        	li.setCharLength(line.length());
	        	li.setContent(line);
	        	if(line.length() == 0 || line.matches("\\s+")) {
	        		li.setBlankRow(true);
	        		blankLineCount++;
	        	}
	        	
	        	lis.add(li);
	        	
	            line = reader.readLine();
	        }
	        reader.close();
	        TxtFile tf = new TxtFile();
	        tf.setLineCount(lineCount);
	        tf.setBlankLineCount(blankLineCount);
	        tf.setCharLength(charLength);
	        tf.setContent(sb.toString());
	        tf.setLineInfos(lis);
	        return tf;
	    } catch (IOException e) {
	       log.error("读取文件失败：{}", filePathName, e);
	       FileException.obtainFileStreamError();
	    }
		return null;
	}
	
	/**
	 * 写入文件
	 * @param filePathName 文件名路径
	 * @param content      写入内容
	 * @param isAppend     写入方式：true-追加，false-覆盖
	 * @throws MyException
	 */
	public static void write(String filePathName, String content, boolean isAppend)throws MyException {
		BufferedWriter bw = null;
		try {
			File file = new File(filePathName);
			if(!file.exists()){
				String filePath = filePathName.substring(0, filePathName.lastIndexOf(File.separator));
				File dfile = new File(filePath);
				if(!dfile.exists()) {
					dfile.mkdirs();
				}
			    file.createNewFile();
			}
			bw = new BufferedWriter(new FileWriter(file, isAppend));
			bw.write(content);
			bw.flush();
		} catch (IOException e) {
			log.error("写入文件失败：{}", filePathName, e);
		    FileException.obtainFileStreamError();
		}finally {
			try {
				if(bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				log.error("写入文件失败：{}", filePathName, e);
			}
		}
	}
	/**
	 * 写入文件
	 * @param filePathName 文件名路径
	 * @param content      写入内容
	 * @param isAppend     写入方式：true-追加，false-覆盖
	 * @throws MyException
	 */
	public static void write(String filePathName, List<String> contents, boolean isAppend)throws MyException {
		FileOutputStream fos =  null;
		PrintWriter pw = null;
		try {
			File file = new File(filePathName);
			if(!file.exists()){
				String filePath = filePathName.substring(0, filePathName.lastIndexOf(File.separator));
				File dfile = new File(filePath);
				if(!dfile.exists()) {
					dfile.mkdirs();
				}
			    file.createNewFile();
			}
	        fos = new FileOutputStream(filePathName, isAppend);
	        pw = new PrintWriter(fos);
	        for(String content : contents) {
	            pw.println(content);
	        }
	        pw.flush();
		} catch (IOException e) {
			log.error("写入文件失败：{}", filePathName, e);
		    FileException.obtainFileStreamError();
		}finally {
			try {
				if(fos != null) {
					fos.close();
				}
				if(pw != null) {
					pw.close();
				}
			} catch (IOException e) {
				log.error("写入文件失败：{}", filePathName, e);
			}
		}
	}
}
