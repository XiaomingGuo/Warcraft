package com.DB.operation;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.DB.operation.DiscardMaterialRecord;
import com.Hibernate.Util.HibernateSessionFactory;
import com.Warcraft.Interface.IDiscard_Material_Record;

public class Discard_Material_Record implements IDiscard_Material_Record
{
	Session session = null;
	Transaction tx = null;
	
	public Discard_Material_Record()
	{
		
	}
	
	@Override
	public void addANewRecord(int id, String strOrderName, String strBarcode,
			String strBatchLot, int iQty, String strReason)
	{
		DiscardMaterialRecord tempRecord = new DiscardMaterialRecord();
		tempRecord.setId(id);
		tempRecord.setOrderName(strOrderName);
		tempRecord.setBarCode(strOrderName);
		tempRecord.setBatchLot(strBatchLot);
		tempRecord.setQty(iQty);
		tempRecord.setReason(strReason);
		try
		{
			session = HibernateSessionFactory.getSession();		//获取Session
			tx = session.beginTransaction();			//开启事物
			session.save(tempRecord);							//保存User到数据库
			tx.commit();								//提交事物
		}
		catch (Exception e)
		{
			e.printStackTrace();						//打印异常信息
			tx.rollback();								//回滚事物
		}
		finally
		{
			HibernateSessionFactory.closeSession();		//关闭Session
		}
	}
	
	public void DeleteRecord(int id)
	{
		try
		{
			session=HibernateSessionFactory.getSessionFactory().openSession();
			tx=session.beginTransaction();
			DiscardMaterialRecord emp=(DiscardMaterialRecord)session.load(DiscardMaterialRecord.class, id);
			session.delete(emp);
			tx.commit();
			session.clear();
		}
		catch (Exception e)
		{
			e.printStackTrace();						//打印异常信息
			tx.rollback();								//回滚事物
		}
		finally
		{
			HibernateSessionFactory.closeSession();		//关闭Session
		}

	}
	
	public void getRecord()
	{
		try
		{
			session = HibernateSessionFactory.getSession();
			DiscardMaterialRecord tempRecord = (DiscardMaterialRecord)session.get(DiscardMaterialRecord.class, new Integer(1));
			if (null != tempRecord)
			{
				String str1 = tempRecord.getBarCode();
				String str2 = tempRecord.getBatchLot();
				String str3 = tempRecord.getOrderName();
				String str4 = tempRecord.getReason();
				int str5 = tempRecord.getId();
				int str6 = tempRecord.getQty();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();						//打印异常信息
		}
		finally
		{
			HibernateSessionFactory.closeSession();
		}
	}
	
	public void updateRecord(int id)
	{
		try
		{
			session = HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			DiscardMaterialRecord tempRecord = (DiscardMaterialRecord)session.get(DiscardMaterialRecord.class, id);
			if (null != tempRecord)
			{
				tempRecord.setReason("报废");
				session.update(tempRecord);
				tx.commit();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();						//打印异常信息
			tx.rollback();
		}
		finally
		{
			HibernateSessionFactory.closeSession();
		}
	}
	
	public List<DiscardMaterialRecord> QueryRecord()
	{
		List<DiscardMaterialRecord> tempRecord = null;
		try
		{
			session = HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			tempRecord = session.createQuery("from DiscardMaterialRecord").list();
			tx.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();						//打印异常信息
			tx.rollback();
		}
		finally
		{
			HibernateSessionFactory.closeSession();
		}
		return tempRecord;
	}
}
