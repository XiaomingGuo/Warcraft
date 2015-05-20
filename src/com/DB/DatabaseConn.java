package com.DB;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConn
{
	private static String url = "jdbc:mysql://localhost:3306/earthquake?characterEncoding=UTF-8";
	private static String userName = "root";
	private static String password = "gxm4907806";
	private Connection Conn;
	private Statement Stmt;
	private String ErrorMsg;
	private ResultSet result;
	
	//Perpare Database connection
	public void SetDatabaseAddr(String tUrl)
	{
		this.url = tUrl;
	}
	
	public void SetUserName(String tUserName)
	{
		this.userName = tUserName;
	}
	
	public void SetPassword(String tPassword)
	{
		this.password = tPassword;
	}
	//End Prepare Database connection
	
	public boolean InitConnect()
	{
		try
		{
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Conn = DriverManager.getConnection(this.url, this.userName, this.password);
		
			Stmt = Conn.createStatement();
			
		}
		catch(SQLException e)
		{
			ErrorMsg = "Error in InitConnect Detail information:" + e.getMessage();
			this.CloseDatabase();
			return false;
		}
		return true;
	}
	
	public List<String> GetProductInfo(String str)
	{
		//product_info Database query
		List<String> product_info = null;
		String sql = "select * from product_info where product_type=\"" + str +"\"";
		if (QueryDataBase(sql))
		{
			product_info = GetAllStringValue("name");
		}
		return product_info;
	}
	
	public boolean QueryDataBase(String sql)
	{
		try
		{
			if (InitConnect())
			{
				result = Stmt.executeQuery(sql);
			}
		}
		catch(SQLException e)
		{
			ErrorMsg = "Error in QueryDataBase Detail info:" + e.getMessage();
			this.CloseDatabase();
			return false;
		}
		
		return true;
	}
	
	public void CloseDatabase()
	{
		try
		{
			if(Stmt != null)	Stmt.close();
			if(Conn != null)	Conn.close();
		}
		catch (SQLException e)
		{
			ErrorMsg = "Error in QueryDataBase Detail info:" + e.getMessage();
		}
	}
	
	public String GetSingleString(String keyWord)
	{
		String rtnRst = null;
		try
		{
			result.last();
			if (result.getRow() > 0)
			{
				result.first();
				rtnRst = result.getString(keyWord);
			}
			this.CloseDatabase();
		}
		catch(SQLException e)
		{
			ErrorMsg = "Error in GetSingleValue Detail info:" + e.getMessage();
			this.CloseDatabase();
			return null;
		}
		return rtnRst;
	}
	
	public List<String> GetAllStringValue(String colName)
	{
		List<String> rtnRst = new ArrayList<String>();
		try
		{
			result.first();
			do
			{
				String tempString = result.getString("name");
				rtnRst.add(tempString);
			} while (result.next());
			this.CloseDatabase();
		}
		catch(SQLException e)
		{
			ErrorMsg = "Error in GetSingleValue Detail info:" + e.getMessage();
			this.CloseDatabase();
			return null;
		}
		return rtnRst;
	}
}