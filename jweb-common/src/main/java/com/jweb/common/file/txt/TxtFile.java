package com.jweb.common.file.txt;

import java.util.List;

import lombok.Data;

@Data
public class TxtFile {
	
	public int lineCount;
	public int blankLineCount;
	private long charLength;
	private String content;
	private List<LineInfo> lineInfos;
	
	@Data
	public static class LineInfo {
		private int lineNum;
		private boolean isBlankRow;
		private long charLength;
		private String content;
	}
}
