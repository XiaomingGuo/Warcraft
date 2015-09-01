package com.office.operation;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelRead implements IExcelExecute
{
	String path, fileName;
	public ExcelRead(String path, String fileName)
	{
		this.path = path;
		this.fileName = fileName;
	}

	@Override
	public String getPath()
	{
		return path;
	}

	@Override
	public String getFileName()
	{
		return fileName;
	}

	@Override
	public HSSFWorkbook getWorkBook()
	{
		return null;
	}

	@Override
	public void setWorkBook()
	{
		
	}

	@Override
	public void setWorkSheet(String sheet)
	{
		
	}

	@Override
	public void setWorkRow(int iRow)
	{
		
	}

	@Override
	public void setWorkCell(int iCol) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCellValue() {
		// TODO Auto-generated method stub
		return null;
	}
}
