package com.DB.operation;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.Hibernate.Util.HibernateSessionFactory;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.Interface.IEQManagement;

public class EarthquakeManagement implements IEQManagement
{
	private Session session = null;
	private Transaction tx = null;
	private ITableInterface hTableHandle = null;
	
	public EarthquakeManagement(){}
	
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
			session.save(hTableHandle.getAWriteRecord());	//����User����ݿ�
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
	
	public boolean DeleteAndUpdateRecord(String hql)
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

	@Override
	public boolean EQQueryWithLimit(String hql, int iStart, int iCount)
	{
		boolean rtnRst = true;
		try
		{
			session = HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			Query tempQuery = session.createQuery(hql);
			tempQuery.setFirstResult(iStart);
			tempQuery.setMaxResults(iCount);
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
}
