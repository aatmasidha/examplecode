package com.ai.sample.common.dto;

import java.util.HashMap;

public class IntegrationFileFormatDTO {
	private String integrationName;
	private String fileType;
	private String delimiter;
	private HashMap<String, String> columnHeader = new HashMap<String, String>();
	
	private int headerStartLine ;
	private int dataStartLine;
	private int ignoreEndLines;
	
	public IntegrationFileFormatDTO(String integrationName,  String fileType, String delimiter,
			HashMap<String, String> columnHeader, int headerStartLine,
			int dataStartLine, int ignoreEndLines) {
		super();
		this.integrationName = integrationName;
		this.fileType = fileType;
		this.delimiter = delimiter;
		this.columnHeader = columnHeader;
		this.headerStartLine = headerStartLine;
		this.dataStartLine = dataStartLine;
		this.ignoreEndLines = ignoreEndLines;
	}
	
	public String getIntegrationName() {
		return integrationName;
	}
	public void setIntegrationName(String integrationName) {
		this.integrationName = integrationName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public HashMap<String, String> getColumnHeader() {
		return columnHeader;
	}
	public void setColumnHeader(HashMap<String, String> columnHeader) {
		this.columnHeader = columnHeader;
	}
	public int getHeaderStartLine() {
		return headerStartLine;
	}
	public void setHeaderStartLine(int headerStartLine) {
		this.headerStartLine = headerStartLine;
	}
	public int getDataStartLine() {
		return dataStartLine;
	}
	public void setDataStartLine(int dataStartLine) {
		this.dataStartLine = dataStartLine;
	}
	public int getIgnoreEndLines() {
		return ignoreEndLines;
	}
	public void setIgnoreEndLines(int ignoreEndLines) {
		this.ignoreEndLines = ignoreEndLines;
	}
	
	
	@Override
	public String toString() {
		return "IntegrationFileFormatDTO [integrationName=" + integrationName
				+ ", fileType=" + fileType + ", delimiter=" + delimiter
				+ ", columnHeader=" + columnHeader + ", headerStartLine="
				+ headerStartLine + ", dataStartLine=" + dataStartLine
				+ ", ignoreEndLines=" + ignoreEndLines + "]";
	}

}
