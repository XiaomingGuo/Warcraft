package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.DB.support.DiscardMaterialRecord;
import com.Hibernate.Util.HibernateSessionFactory;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.Interface.IEQManagement;

public class EarthquakeManagement implements IEQManagement
{
	private Session session = null;
	private Transaction tx = null;
	private ITableInterface hTableHandle = null;
	
	public EarthquakeManagement(){	}
	
	@Override
	public void setTableHandle(ITableInterface hTableHandle)
	{
		this.hTableHandle = hTableHandle;
	}

	@Override
	public boolean EQQuery(String hql)
	{
		boolean rtnRst = true;
		try
		{
			session = HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			Query tempQuery = session.createQuery(hql);
			hTableHandle.setResultList(tempQuery);
			tx.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();						//��ӡ�쳣��Ϣ
			tx.rollback();
			rtnRst = false;
		}
		finally
		{
			HibernateSessionFactory.closeSession();
		}
		
		return rtnRst;
	}
	
	public void addANewRecord()
	{
		try
		{
			session = HibernateSessionFactory.getSession();	//��ȡSession
			tx = session.beginTransaction();				//��������
			session.save(hTableHandle.getAWriteRecord());	//����User�����ݿ�
			tx.commit();									//�ύ����
		}
		catch (Exception e)
		{
			e.printStackTrace();						//��ӡ�쳣��Ϣ
			tx.rollback();								//�ع�����
		}
		finally
		{
			HibernateSessionFactory.closeSession();		//�ر�Session
		}
	}
	
	public boolean DeleteRecord(String hql)
	{
		boolean rtnRst = true;
		try
		{
			session = HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			Query tempQuery = session.createQuery(hql);
			tempQuery.executeUpdate();
			tx.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();						//��ӡ�쳣��Ϣ
			tx.rollback();
			rtnRst = false;
		}
		finally
		{
			HibernateSessionFactory.closeSession();
		}
		
		return rtnRst;
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
			e.printStackTrace();						//��ӡ�쳣��Ϣ
		}
		finally
		{
			HibernateSessionFactory.closeSession();
		}
	}
	
	public void updateRecord(String hql)
	{
		try
		{
			session = HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			Query tempQuery = session.createQuery(hql);
			tempQuery.executeUpdate();
			tx.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();						//��ӡ�쳣��Ϣ
			tx.rollback();
		}
		finally
		{
			HibernateSessionFactory.closeSession();
		}
	}
	
	public List<String> QueryARecord(String hql)
	{
		List<String> tempRecord = new ArrayList<String>();
		try
		{
			session = HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			List<DiscardMaterialRecord> tempQ = session.createQuery(hql).list();
			for(Iterator<DiscardMaterialRecord> idx=tempQ.iterator(); idx.hasNext();)
			{
				tempRecord.add(idx.next().getOrderName());
			}
			tx.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();						//��ӡ�쳣��Ϣ
			tx.rollback();
		}
		finally
		{
			HibernateSessionFactory.closeSession();
		}
		return tempRecord;
	}
}
