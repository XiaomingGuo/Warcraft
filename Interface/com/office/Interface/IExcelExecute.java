package com.office.Interface;

import java.util.List;

public interface IExcelExecute
{
	public String getPath();
	public String getFileName();
	public boolean closeExcel();
	
	public List<List<String>> ReadExcel(String path, String fileName, String sheet);
	public boolean writeExcel(String path, String fileName, String sheet);
}
