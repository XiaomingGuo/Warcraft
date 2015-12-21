package com.office.operation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.Warcraft.Interface.IExcelExecute;

public class ExcelWrite implements IExcelExecute
{
	private String path, fileName;
	private FileInputStream fileInStream = null;
	private FileOutputStream fileOutStream = null;
	private POIFSFileSystem fileSys = null;
	private HSSFWorkbook hWorkBook = null;
	private HSSFSheet hWorkSheet = null;
	private HSSFRow hWorkRow = null;
	private HSSFCell hWorkCell = null;

	public ExcelWrite(String path, String fileName)
	{
		this.path = path;
		this.fileName = fileName;
	}

	@Override
	public void setWorkBook()
	{
		try
		{
			fileInStream = new FileInputStream(path + "\\" + fileName);
			fileSys = new POIFSFileSystem(fileInStream);
			hWorkBook = new HSSFWorkbook(fileSys);
		}
		catch (FileNotFoundException e)
		{
			closeFile();
			e.printStackTrace();
		}
		catch (IOException e)
		{
			closeWorkBook();
			closeFile();
			e.printStackTrace();
		}
	}
	
	@Override
	public String getPath()
	{
		return this.path;
	}

	@Override
	public String getFileName()
	{
		return this.fileName;
	}

	@Override
	public void closeFile()
	{
		try
		{
			if (null != fileInStream)
			{
				fileInStream.close();
				fileInStream = null;
			}
		}
		catch (IOException e)
		{
			fileInStream = null;
			e.printStackTrace();
		}
		try
		{
			if(null != fileOutStream)
			{
				fileOutStream.close();
				fileOutStream = null;
			}
		}
		catch (IOException e)
		{
			fileOutStream = null;
			e.printStackTrace();
		}

	}

	@Override
	public void closeWorkBook()
	{
		try
		{
			if (null != hWorkBook)
			{
				hWorkBook.close();
				hWorkBook = null;
			}
		}
		catch (IOException e)
		{
			hWorkBook = null;
			e.printStackTrace();
		}
	}

	@Override
	public boolean setWorkSheet(String sheet)
	{
		boolean rtnRst = false;
		if (null != hWorkBook)
		{
			hWorkSheet = hWorkBook.getSheet(sheet);
			rtnRst = true;
		}
		else
		{
			hWorkSheet = null;
		}
		return rtnRst;
	}

	@Override
	public void setWorkRow(int iRow)
	{
		if (null != hWorkSheet)
		{
			hWorkRow = hWorkSheet.getRow(iRow);
		}
		else
		{
			hWorkRow = null;
		}
	}
	
	@Override
	public void createWorkRow(int iRow)
	{
		if (null != hWorkSheet)
		{
			hWorkRow = hWorkSheet.createRow(iRow);
		}
		else
		{
			hWorkRow = null;
		}
	}

	@Override
	public void setWorkCell(int iCol)
	{
		if (null != hWorkRow)
		{
			hWorkCell = hWorkRow.createCell(iCol);
		}
		else
		{
			hWorkCell = null;
		}
	}
	
	@Override
	public void setWorkCellWithBorder(int iCol)
	{
		if (null != hWorkRow)
		{
			HSSFCellStyle cellStyle = hWorkBook.createCellStyle();
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			hWorkCell = hWorkRow.createCell(iCol);
		}
		else
		{
			hWorkCell = null;
		}
	}
	
	@Override
	public String getCellValue()
	{
		return null;
	}

	@Override
	public void setCellValue(String setVal)
	{
		if(null != hWorkCell)
		{
			hWorkCell.setCellValue(setVal);
		}
	}

	@Override
	public void saveToFile()
	{
		try
		{
			fileOutStream = new FileOutputStream(path + "\\" + fileName);
			fileOutStream.flush();
			hWorkBook.write(fileOutStream);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeFile();
			closeWorkBook();
		}
	}
}
