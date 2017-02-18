<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.apache.commons.io.output.*" %>
<%
    File file;
    int maxFileSize = 5000 * 1024;
    int maxMemSize = 5000 * 1024;
    //FileItemIterator fii = sfu.getItemIterator(request);//解析request 请求,并返回FileItemIterator集合  
    //while(fii.hasNext()){  
    //    FileItemStream fis = fii.next();

     // Verify the content type
    //    String contentType = request.getContentType();
    //}
%>