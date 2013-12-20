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
	String color = (String)request.getAttribute("postColor");
	HashMap<String,HashMap<String,String>> WebPage=(HashMap)session.getAttribute("WebPage");
	HashMap<String,ArrayList> ClustResult = (HashMap)session.getAttribute("ClustResult");
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

System.out.println(color);
if(color==null||color.matches("#999999")){
	for (String url:WebPage.keySet()){
		ViewUrl.add(url);
	}
}else{
	int num=colorlist.indexOf(color);
	int x=0;
	for (String label:ClustResult.keySet()){
		System.out.println(x+"=="+num);
		if(x==num){
			System.out.println(x+"=="+num);
			ViewUrl=new ArrayList(ClustResult.get(label));
			System.out.println(ViewUrl);
		}
		x++;
	}
}
for (int i=0;i<ViewUrl.size();i++){
	String url = ViewUrl.get(i);
	for(String title:WebPage.get(url).keySet()){
		
%>
<tr>
	<td>
		<a href="<%=url %>"><%=title%></a><br>
		<%=WebPage.get(url).get(title)%>
	</td>
</tr>
<%
	}
		
}
%>
</table>
</font>
</body>
</html>