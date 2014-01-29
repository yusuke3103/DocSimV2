<%@page import="java.util.Collection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<%
	ArrayList<String> colorlist=new ArrayList();
	ArrayList<String> ViewUrl=new ArrayList(); 
	colorlist.add("#C71585");
	colorlist.add("#556b2f");
	colorlist.add("#191970");
	colorlist.add("#dc143c");
	colorlist.add("#9932cc");
	
	request.setCharacterEncoding("utf-8");
	String color = (String)request.getAttribute("postColor");
	String lab = (String)request.getAttribute("lab");
	System.out.println(lab);
	HashMap<String,HashMap<String,String>> WebPage=(HashMap)session.getAttribute("WebPage");
	HashMap<String,ArrayList> ClustResult = (HashMap)session.getAttribute("ClustResult");
	HashMap<String,ArrayList> DocLabels = (HashMap)session.getAttribute("DocLabels");
%>
<script type="text/javascript">
function right(){
	if(<%=color%>==null){
		parent.right.location.href="./right.jsp";
	}
}
</script>
<meta charset="utf-8">
</head>
<body onload="right();">
<font size="2px">
<table>
<%
for (String url:WebPage.keySet()){
	for (String title:WebPage.get(url).keySet()){
%>
		<tr><td>
		<a href=<%=url%> target=_blank><%=title%></a><br>
		<%=WebPage.get(url).get(title)%>
		</td></tr>
<%		
	}
}
%>
</table>
</font>
</body>
</html>