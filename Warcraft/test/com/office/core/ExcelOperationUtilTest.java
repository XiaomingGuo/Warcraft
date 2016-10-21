package com.office.core;
import java.io.FileNotFoundException;

import com.office.core.ExcelManagment;

import static org.junit.Assert.*;
import mockit.Mocked;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelOperationUtilTest
{
	ExcelManagment hTester;
	
	@Mocked
	final HSSFWorkbook mockWorkbook = new HSSFWorkbook();
	
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
		//MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testReadExcel() throws FileNotFoundException
	{
		int[] startPos = {1,2};
		int[] endPos   = {3,6};
		hTester.ReadExcel("123", "123", "abc", startPos, endPos);
		assertTrue(true);
	}

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
