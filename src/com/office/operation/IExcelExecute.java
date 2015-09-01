package com.office.operation;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public interface IExcelExecute
{
	public String getPath();
	public String getFileName();
	public HSSFWorkbook getWorkBook();
	public void setWorkBook();
	public void setWorkSheet(String sheet);
	public void setWorkRow(int iRow);
	public void setWorkCell(int iCol);
	public String getCellValue();
}
