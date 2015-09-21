package com.DB.operation;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.DB.operation.DiscardMaterialRecord;
import com.Hibernate.Util.HibernateSessionFactory;

public class Discard_Material_Record
{
	Session session = null;
	Transaction tx = null;
	
	public void getRecord()
	{
		try
		{
			session = HibernateSessionFactory.getSession();
			DiscardMaterialRecord tempRecord = (DiscardMaterialRecord)session.get(DiscardMaterialRecord.class, new Integer(1));
			String str1 = tempRecord.getBarCode();
			String str2 = tempRecord.getBatchLot();
			String str3 = tempRecord.getOrderName();
			String str4 = tempRecord.getReason();
			int str5 = tempRecord.getId();
			int str6 = tempRecord.getQty();
		}
		catch (Exception e)
		{
			e.printStackTrace();						//打印异常信息
			tx.rollback();
		}
	}
}
