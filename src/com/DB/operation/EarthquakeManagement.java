package com.DB.operation;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.Hibernate.Util.HibernateSessionFactory;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.Interface.IDBTablePublic;
import com.Warcraft.Interface.IEQManagement;

public class EarthquakeManagement implements IEQManagement
{
	private Session session = null;
	private Transaction tx = null;
	private ITableInterface hTableHandle = null;
	
	public EarthquakeManagement(){	}
	
	public boolean EQQuery(String hql)
	{
		boolean rtnRst = true;
		try
		{
			session = HibernateSessionFactory.getSession();
			tx=session.beginTransaction();
			hTableHandle.setResult(session.createQuery(hql));
			tx.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();						//打印异常信息
			tx.rollback();
			rtnRst = false;
		}
		finally
		{
			HibernateSessionFactory.closeSession();
		}
		
		return rtnRst;
	}

	@Override
	public void setTableHandle(ITableInterface hTableHandle)
	{
		this.hTableHandle = hTableHandle;
	}

}
