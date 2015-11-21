package com.jsp.support;

public class PageParentClass
{
	public int CalcOrderQty(String po_Num, String percent)
	{
		return Integer.parseInt(po_Num) * (100 + Integer.parseInt(percent))/100;
	}
	
	public int CalcOrderQty(int po_Num, String percent)
	{
		return po_Num * (100 + Integer.parseInt(percent))/100;
	}
	
	public int CalcOrderQty(String po_Num, int percent)
	{
		return Integer.parseInt(po_Num) * (100 + percent)/100;
	}
	
	public int CalcOrderQty(int po_Num, int percent)
	{
		return po_Num * (100 + percent)/100;
	}
	
	public String GetStorageNameByBarCode(String Bar_Code, boolean isExhausted)
	{
		String rtnRst = "";
		int barcode = Integer.parseInt(Bar_Code);
		if(barcode >= 50000000 && barcode < 60000000) {
			rtnRst = isExhausted?"ExhaustedMaterial":"MaterialStorage";
		}
		else if(barcode >= 60000000 && barcode < 70000000) {
			rtnRst = isExhausted?"ExhaustedProduct":"ProductStorage";
		}
		else if(barcode >= 70000000 && barcode < 80000000) {
			rtnRst = isExhausted?"":"";
		}
		else {
			rtnRst = isExhausted?"ExhaustedOther":"OtherStorage";
		}
		return rtnRst;
	}
}
