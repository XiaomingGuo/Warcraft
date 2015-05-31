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
	
	public boolean execUpate(String sql)
	{
		try
		{
			if (InitConnect())
			{
				Stmt.executeUpdate(sql);
			}
			this.CloseDatabase();
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
			if(Stmt != null)
			{
				Stmt.close();
				Stmt = null;
			}
			if(Conn != null)
			{
				Conn.close();
				Conn = null;
			}
		}
		catch (SQLException e)
		{
			ErrorMsg = "Error in CloseDatabase Detail info:" + e.getMessage();
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
	
	public int GetSingleInt(String keyWord)
	{
		int rtnRst = 0;
		try
		{
			result.last();
			if (result.getRow() > 0)
			{
				result.first();
				rtnRst = result.getInt(keyWord);
			}
			this.CloseDatabase();
		}
		catch(SQLException e)
		{
			ErrorMsg = "Error in GetSingleValue Detail info:" + e.getMessage();
			this.CloseDatabase();
			return 0;
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
				String tempString = result.getString(colName);
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
	
	public List<List<String>> GetAllDBColumnsByList(String[] colNames)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		try
		{
			result.last();
			if (result.getRow() > 0)
			{
				for(int i = 0; i < colNames.length; i++)
				{
					List<String> tempList = new ArrayList<String>();
					result.first();
					do
					{
						String tempString = result.getString(colNames[i]);
						tempList.add(tempString);
					} while (result.next());
					rtnRst.add(tempList);
				}
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
	
	public int GetRecordCount()
	{
		int rtnRst = 0;
		try
		{
			result.last();
			rtnRst = result.getRow();
		}
		catch(SQLException e)
		{
			ErrorMsg = "Error in GetSingleValue Detail info:" + e.getMessage();
			this.CloseDatabase();
			return 0;
		}
		return rtnRst;
	}
	
	public int GetRepertoryByBarCode(String barcode)
	{
		return GetIN_QTYByBarCode(barcode) - GetOUT_QTYByBarCode(barcode);
	}

	public int GetIN_QTYByBarCode(String barcode)
	{
		int rtnRst = 0;
		String sql = "select IN_QTY from material_storage where Bar_Code=\"" + barcode +"\"";
		if (QueryDataBase(sql))
		{
			if (GetRecordCount() > 0)
			{
				List<String> in_Qty_List = GetAllStringValue("IN_QTY");
				for (int i = 0; i < in_Qty_List.size(); i++)
				{
					rtnRst += Integer.parseInt(in_Qty_List.get(i));
				}
			}
		}
		return rtnRst;
	}
	
	public int GetOUT_QTYByBarCode(String barcode)
	{
		int rtnRst = 0;
		String sql = "select OUT_QTY from material_storage where Bar_Code=\"" + barcode +"\"";
		if (QueryDataBase(sql))
		{
			if (GetRecordCount() > 0)
			{
				List<String> out_Qty_List = GetAllStringValue("OUT_QTY");
				for (int i = 0; i < out_Qty_List.size(); i++)
				{
					rtnRst += Integer.parseInt(out_Qty_List.get(i));
				}
			}
		}
		return rtnRst;
	}
	
	public String GetNameByBarcode(String barcode)
	{
		String rtnRst = "";
		String sql = "select name from product_info where Bar_Code=\"" + barcode +"\"";
		if (QueryDataBase(sql))
		{
			if (GetRecordCount() > 0)
			{
				rtnRst = GetSingleString("name");
			}
		}
		return rtnRst;
	}

}
