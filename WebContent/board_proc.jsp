<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="bean" class="index.boardBean"/>
<jsp:useBean id="mgr" class="index.boardMgr"/>
<jsp:setProperty name="bean" property="*"/>
<%
	System.out.println(bean.getWriter());
	System.out.println(bean.getWriter());
	mgr.insertReple(bean);
	response.sendRedirect("./detail.jsp?index="+bean.getParent());
%>