<%@ page language="java" import="java.util.* ,java.io.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.jspsmart.upload.*" %>
<%@ page import="com.office.core.ExcelManagment" %>
<%@ page import="com.office.operation.ExcelRead" %>
<%@ page import="com.DB.factory.DatabaseStore" %>
<%@ page import="com.DB.operation.Product_Info" %>
<%@ page import="com.DB.operation.Product_Type" %>
<%@ page import="com.DB.operation.Storeroom_Name" %>
<%
    request.setCharacterEncoding("UTF-8");
    String filePath = request.getParameter("filePath").replace(" ", "");
    String fileName = request.getParameter("fileName").replace(" ", "");
    ExcelManagment excelUtil = new ExcelManagment(new ExcelRead(filePath, fileName));
    int[] startCell, endCell;
    if (fileName.contains("Summary-other"))
    {
        startCell = new int[] {2,1};
        endCell = new int[] {261,6};
    }
    else
    {
        startCell = new int[] {2,1};
        endCell = new int[] {1237,6};
    }
    List<List<String>> res = excelUtil.execReadExcelBlock("Sheet1", startCell, endCell);
    if(res.size() > 0)
    {
    	DatabaseStore hPIHandle = new DatabaseStore("Product_Info");
    	DatabaseStore hPTHandle = new DatabaseStore("Product_Type");
    	DatabaseStore hSNHandle = new DatabaseStore("Storeroom_Name");
        for(int iRow = 0; iRow < res.size(); iRow++)
        {
            String storename = res.get(iRow).get(0);
            String product_type = res.get(iRow).get(1);
            String product_name = res.get(iRow).get(2);
            String Barcode = res.get(iRow).get(3);
            String weight = res.get(iRow).get(4);
            String description = res.get(iRow).get(5);
            hSNHandle.QueryRecordByFilterKeyList(Arrays.asList("name"), Arrays.asList(storename));
            if (hSNHandle.getTableInstance().RecordDBCount() == 0)
                ((Storeroom_Name)hSNHandle.getTableInstance()).AddARecord(storename);
            
            hPTHandle.QueryRecordByFilterKeyList(Arrays.asList("name", "storeroom"), Arrays.asList(product_type, storename));
            if (hPTHandle.getTableInstance().RecordDBCount() == 0)
                ((Product_Type)hPTHandle.getTableInstance()).AddARecord(product_type, storename);
            
            hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(Barcode));
            if(hPIHandle.getTableInstance().RecordDBCount() > 0)
            {
                hPIHandle.UpdateRecordByKeyList("name", product_name, Arrays.asList("Bar_Code"), Arrays.asList(Barcode));
                hPIHandle.UpdateRecordByKeyList("product_type", product_type, Arrays.asList("Bar_Code"), Arrays.asList(Barcode));
                hPIHandle.UpdateRecordByKeyList("weight", weight, Arrays.asList("Bar_Code"), Arrays.asList(Barcode));
                hPIHandle.UpdateRecordByKeyList("description", description, Arrays.asList("Bar_Code"), Arrays.asList(Barcode));
            }
            else
            {
                hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "name", "product_type"), Arrays.asList(Barcode, product_name, product_type));
                if(hPIHandle.getTableInstance().RecordDBCount() == 0)
                    ((Product_Info)hPIHandle.getTableInstance()).AddARecord(Barcode, product_name, product_type, weight, "","","", "", description);
            }
        }
    }
    response.setCharacterEncoding("utf-8");
    String fileFullPath = filePath + fileName;
    fileFullPath = new String(fileFullPath.getBytes("iso-8859-1"));
    SmartUpload su = new SmartUpload(); // 新建一个smartupload对象     
    su.initialize(pageContext);         // 初始化准备操作  

    // 设定contentdisposition为null以禁止浏览器自动打开文件， 
    //保证点击链接后是下载文件。若不设定，则下载的文件扩展名为 
    //doc时，浏览器将自动用word打开它。扩展名为pdf时， 
    //浏览器将用acrobat打开。 
    su.setContentDisposition(null);
     
    su.downloadFile(fileFullPath); // 下载文件
    out.clear();
    out=pageContext.pushBody();
    out.println("<script>alert('创建成功！');window.location.href = 'index.jsp';</script>");
%>