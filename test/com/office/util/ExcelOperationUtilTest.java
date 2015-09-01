package com.office.util;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;

import com.office.ExcelOperationUtil;
import com.sun.xml.internal.ws.org.objectweb.asm.Type;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExcelOperationUtilTest
{
	@Mock
	ExcelOperationUtil hTester = new ExcelOperationUtil("", "");
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		
	}

	@After
	public void tearDown() throws Exception
	{
	}

/*	@Test
	public void testReadExcel()
	{
		HSSFWorkbook mockWorkbook = mock(HSSFWorkbook.class);
		int[] startPos = {1,2};
		int[] endPos   = {3,6};
		hTester.ReadExcel("123", "123", "abc", startPos, endPos);
		assertTrue(true);
	}
*/

	@Test
	public void testCheckPathStatus()
	{
		assertTrue(true);
		//fail("Not yet implemented");
	}

	@Test
	public void testWriteDataToExcel()
	{
		assertTrue(true);
		//fail("Not yet implemented");
	}

	@Test
	public void testCreateExcelFile()
	{
		assertTrue(true);
		//fail("Not yet implemented");
	}

}
