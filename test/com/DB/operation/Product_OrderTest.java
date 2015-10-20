package com.DB.operation;

import static org.junit.Assert.*;
import mockit.Injectable;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Tested;
import mockit.Verifications;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.DB.operation.Product_Order;
import com.DB.support.EarthquakeManagement;

public class Product_OrderTest
{
	@Tested
	Product_Order hTestHandle;
	
	@Injectable
	EarthquakeManagement EQMHandle;
	
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
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testProduct_Order()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testGetDBRecordList() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetResult() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetResultList() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetResultList() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOrderNameByStatus()
	{
		new NonStrictExpectations()
		{
		};
		hTestHandle.GetOrderNameByStatus(1);
		new Verifications()
		{
		};
	}

	@Test
	public void testDBTableParent() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEQMHandle() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetEQMHandle() {
		fail("Not yet implemented");
	}

}
