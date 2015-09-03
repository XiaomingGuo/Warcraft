package com.office.operation;

public interface IExcelExecute
{
	//Public function
	public String getPath();
	public String getFileName();
	public void closeFile();
	public void closeWorkBook();
	
	//For Read function
	public void setWorkBook();
	public boolean setWorkSheet(String sheet);
	public void setWorkRow(int iRow);
	public void setWorkCell(int iCol);
	public String getCellValue();
	
	//For Write function
	public void setCellValue(String setVal);
	public void saveToFile();
}
