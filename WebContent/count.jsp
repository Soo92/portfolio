<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:useBean id="mgr" class="index.IndexMgr"/>
<%
	mgr.Count(Integer.parseInt(request.getParameter("index")));
%>
<script>
	self.close();
</script>