/**
 * 
 */
package com.jsp.support;

import static org.mockito.Mockito.*;

import org.junit.*;

import com.jsp.support.PersonalMenu.SummarizeCheckInTime;
import com.Warcraft.Interface.*;
import com.Warcraft.SupportUnit.DBTableParent;

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
	 * Test method for {@link com.jsp.support.PersonalMenu.SummarizeCheckInTime#GetAllUserName()}.
	 */
	@Test
	public void testGetAllUserRecordByName()
	{
		IRecordsQueryUtil mockHandle = mock(IRecordsQueryUtil.class);
		hTestHandle.setQueryHandle(mockHandle);
		hTestHandle.GetAllUserRecordByName("AllRecord", "name");
		verify(mockHandle).setDBHandle((DBTableParent) any());
		verify(mockHandle).GetTableContentByKeyWord("name", "AllRecord", "name");
	}
	
	@Test
	public void testGetAllUserRecordByCheckInId()
	{
		IRecordsQueryUtil mockHandle = mock(IRecordsQueryUtil.class);
		hTestHandle.setQueryHandle(mockHandle);
		hTestHandle.GetAllUserRecordByCheckInId("12301", "name");
		verify(mockHandle).setDBHandle((DBTableParent) any());
		verify(mockHandle).GetTableContentByKeyWord("check_in_id", "12301", "name");
	}
	
	/**
	 * Test method for {@link com.jsp.support.PersonalMenu.SummarizeCheckInTime#GenerateReturnString(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGenerateReturnString()
	{
		SummarizeCheckInTime mockHandle = mock(SummarizeCheckInTime.class);
	}

}
