/**
 * 
 */
package com.jsp.support;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;
import com.jsp.support.SummarizeCheckInTime;
import com.Warcraft.Interface.*;

/**
 * @author Wallace.Guo
 *
 */
public class TestSummarizeCheckInTime
{
	SummarizeCheckInTime hTestHandle;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		hTestHandle = new SummarizeCheckInTime();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
	}
	
	/**
	 * Test method for {@link com.jsp.support.SummarizeCheckInTime#GetAllUserName()}.
	 */
	@Test
	public void testGetAllUserRecordByName()
	{
		IRecordsQueryUtil mockHandle = mock(IRecordsQueryUtil.class);
		hTestHandle.setQueryHandle(mockHandle);
		hTestHandle.GetAllUserRecordByName("AllRecord", "name");
		verify(mockHandle).setTableHandle((ITableInterface) any());
		verify(mockHandle).GetTableContentByKeyWord("name", "AllRecord", "name");
	}
	
	@Test
	public void testGetAllUserRecordByCheckInId()
	{
		IRecordsQueryUtil mockHandle = mock(IRecordsQueryUtil.class);
		hTestHandle.setQueryHandle(mockHandle);
		hTestHandle.GetAllUserRecordByCheckInId("12301", "name");
		verify(mockHandle).setTableHandle((ITableInterface) any());
		verify(mockHandle).GetTableContentByKeyWord("check_in_id", "12301", "name");
	}
	
	/**
	 * Test method for {@link com.jsp.support.SummarizeCheckInTime#GenerateReturnString(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGenerateReturnString()
	{
		SummarizeCheckInTime mockHandle = mock(SummarizeCheckInTime.class);
		mockHandle.GenerateReturnString("AllRecord", "20161102");
		//mockHandle.
		//fail("Not yet implemented");
	}

}
