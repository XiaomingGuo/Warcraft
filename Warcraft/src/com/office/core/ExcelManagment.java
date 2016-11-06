package com.office.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.Warcraft.Interface.IExcelExecute;

public class ExcelManagment extends MSExcel
{
	private IExcelExecute hExcelHandle;
	public ExcelManagment(IExcelExecute hExcelHandle)
	{
		this.hExcelHandle = hExcelHandle;
		CheckAndCreatePath(this.hExcelHandle.getPath());
		this.hExcelHandle.setWorkBook();
	}
	
	private List<String> getARowContent(int startCol, int endCol)
	{
		List<String> rtnRst = new ArrayList<String>();
		for (int iCol = startCol-1; iCol < endCol; iCol++)
		{
			hExcelHandle.setWorkCell(iCol);
			String tempValue = hExcelHandle.getCellValue();
			if(tempValue != null)
			{
				rtnRst.add(tempValue);
			}
			else
			{
				rtnRst.add("");
			}
		}
		return rtnRst;
	}
	
	public List<List<String>> execReadExcelBlock(String sheet, int[] startCell, int[] EndCell)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		try
		{
			hExcelHandle.setWorkSheet(sheet);
			for (int iRow = startCell[0]-1; iRow < EndCell[0]; iRow++)
			{
				hExcelHandle.setWorkRow(iRow);
				rtnRst.add(getARowContent(startCell[1], EndCell[1]));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			CleanExcelHandle();
		}
		return rtnRst;
	}
	
	private void CleanExcelHandle()
	{
		try
		{
			hExcelHandle.closeWorkBook();
			hExcelHandle.closeFile();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}

	public List<List<String>> ReadExcel(String path, String fileName, String sheet, int[] startCell, int[] EndCell) throws FileNotFoundException
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		try
		{
			FileInputStream file = new FileInputStream(new File(path + "\\" + fileName));
			HSSFWorkbook hWorkBook = new HSSFWorkbook(file);
			HSSFSheet hWorkSheet = hWorkBook.getSheet(sheet);
			for (int iRow = startCell[0]-1; iRow < EndCell[0]; iRow++)
			{
				List<String> rowList = new ArrayList<String>();
				HSSFRow hWorkRow = hWorkSheet.getRow(iRow);
				for (int iCol = startCell[1]-1; iCol < EndCell[1]; iCol++)
				{
					HSSFCell hWorkCell = hWorkRow.getCell(iCol);
					if (hWorkCell != null)
					{
						String tempValue = "";
						switch(hWorkCell.getCellType())
						{
						case HSSFCell.CELL_TYPE_STRING:
							tempValue = hWorkCell.getStringCellValue();
							if(tempValue != null)
							{
								rowList.add(tempValue);
							}
							else
							{
								rowList.add("");
							}
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							tempValue = String.valueOf(hWorkCell.getNumericCellValue());
							if(tempValue != null)
							{
								rowList.add(tempValue);
							}
							else
							{
								rowList.add("");
							}
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							rowList.add("");
							break;
						default:
							break;
						}
					}
				}
				rtnRst.add(rowList);
			}
			hWorkBook.close();
			file.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			;
		}
		return rtnRst;
	}
	
	public boolean execWriteExcelBlock(List<List<String>> contentList, int splitSheetCol)
	{
		boolean rtnRst = true;
	    try
		{
			for (int iRow = 1; iRow < contentList.size(); iRow++)
			{
				String sheetName = contentList.get(iRow).get(splitSheetCol);
				if(hExcelHandle.setWorkSheet(sheetName))
				{
					hExcelHandle.setWorkRow(0);
					for(int iCol=0; iCol < contentList.get(0).size(); iCol++)
					{
						hExcelHandle.setWorkCell(iCol);
						hExcelHandle.setCellValue(contentList.get(0).get(iCol));
					}
				}
				hExcelHandle.setWorkRow(0);
				for(int iCol=0; iCol < contentList.get(iRow).size(); iCol++)
				{
					hExcelHandle.setWorkCell(iCol);
					hExcelHandle.setCellValue(contentList.get(iRow).get(iCol));
				}
			}
			hExcelHandle.saveToFile();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			rtnRst = false;
		} 
		finally
		{
			CleanExcelHandle();
		}
		return rtnRst;
	}
	
	private void setARowContent(int iRow, List<String> rowContentList, List<Integer> ignoreColList)
	{
		hExcelHandle.setWorkRow(iRow);
		int setCol = 0;
		for(int iCol=0; iCol < rowContentList.size(); iCol++)
		{
			if(!ignoreColList.contains(iCol))
			{
				hExcelHandle.setWorkCell(setCol);
				hExcelHandle.setCellValue(rowContentList.get(iCol));
				setCol++;
			}
		}
	}
	
	public boolean execWriteExcelWithIgnoreList(List<List<String>> contentList, List<List<Integer>> ignoreList)
	{
		boolean rtnRst = true;
		List<String> sheetNameList = Arrays.asList("总进货报表", "总消耗报表", "总库存报表");
	    try
		{
	    	for(int iSheetIdx = 0; iSheetIdx < ignoreList.size(); iSheetIdx++)
	    	{
				String sheetName = sheetNameList.get(iSheetIdx);
				if(hExcelHandle.setWorkSheet(sheetName))
				{
					ignoreList.get(iSheetIdx);
					setARowContent(0, contentList.get(0), ignoreList.get(iSheetIdx));
					
					for (int iRow = 1; iRow < contentList.size(); iRow++)
					{
						setARowContent(iRow, contentList.get(iRow), ignoreList.get(iSheetIdx));
					}
				}
	    	}
			hExcelHandle.saveToFile();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			rtnRst = false;
		} 
		finally
		{
			CleanExcelHandle();
		}
		return rtnRst;
	}
	
	public boolean WriteDataToExcelCol(String sheetName, List<List<String>> contentList, int[] splitSheetCol)
	{
		boolean rtnRst = true;
	    try
		{
			for (int iRow = 0; iRow < contentList.get(0).size(); iRow++)
			{
				if(hExcelHandle.setWorkSheet(sheetName))
				{
					hExcelHandle.setWorkRow(splitSheetCol[0]+iRow);
					for(int iCol=0; iCol < contentList.size(); iCol++)
					{
						hExcelHandle.setWorkCell(splitSheetCol[1]+iCol);
						hExcelHandle.setCellValue(contentList.get(iCol).get(iRow));
					}
				}
			}
			hExcelHandle.saveToFile();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			rtnRst = false;
		} 
		finally
		{
			CleanExcelHandle();
		}
		return rtnRst;
	}
	
	public boolean WriteDataToExcelBlock(String sheetName, List<List<String>> contentList, int[] splitSheetCol)
	{
		boolean rtnRst = true;
	    try
		{
			for (int iRow = 0; iRow < contentList.size(); iRow++)
			{
				if(hExcelHandle.setWorkSheet(sheetName))
				{
					hExcelHandle.createWorkRow(splitSheetCol[0]+iRow);
					for(int iCol=0; iCol < contentList.get(iRow).size(); iCol++)
					{
						hExcelHandle.setWorkCellWithBorder(splitSheetCol[1]+iCol);
						hExcelHandle.setCellValue(contentList.get(iRow).get(iCol));
					}
				}
			}
			hExcelHandle.saveToFile();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			rtnRst = false;
		} 
		finally
		{
			CleanExcelHandle();
		}
		return rtnRst;
	}
	
	public boolean CreateExcelFile(String filePath,String fileName)
	{
		try
		{
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			workbook.setSheetName(0, "Sheet1");
			HSSFRow row = sheet.createRow(0);
			HSSFCell label_num = row.createCell(0);
			label_num.setCellValue("圆周率");
			HSSFCell label_date = row.createCell(1);
			label_date.setCellValue("值");
			HSSFCell label_bool = row.createCell(2);
			label_bool.setCellValue("数据");
			HSSFRow row2 = sheet.createRow(1);
			HSSFCell num_cell = row2.createCell(0);
			num_cell.setCellValue(3.1415926);
			HSSFCell date_cell = row2.createCell(1);
			date_cell.setCellValue(Calendar.getInstance());
			HSSFCell bool_cell = row2.createCell(2);
			bool_cell.setCellValue(false);
			File xlsFile = new File(filePath,fileName);
			FileOutputStream fos = new FileOutputStream(xlsFile);
			workbook.write(fos);
			fos.close();
			workbook.close();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		} 
	}
}
